/*
 * DB-SVG Copyright 2009 Derrick Bowen
 *
 * This file is part of DB-SVG.
 *
 *   DB-SVG is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   DB-SVG is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with DB-SVG.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package DBViewer.services;

import java.util.*;
import DBViewer.objects.view.*;
import DBViewer.objects.model.*;
import DBViewer.models.*;
import java.io.Serializable;

/**
 *
 * Uses a version of the Spring/Force-Based Algorithm to calculate the best positions for the tables.
 *
 * Explanation & pseudocode taken from Wikipedia:
 * Pseudocode
 *
 * Each node has x,y position and dx,dy velocity and mass m. There is usually a spring constant, s,
 * and damping: 0 < damping < 1. The force toward and away from nodes is calculated according
 * to Hooke's Law and Coulomb's law or similar as discussed above.
 * 

     set up initial node velocities to (0,0)
     set up initial node positions randomly // make sure no 2 nodes are in exactly the same position
     loop
         total_kinetic_energy := 0 // running sum of total kinetic energy over all particles
         for each node
             net-force := (0, 0) // running sum of total force on this particular node

             for each other node
                 net-force := net-force + Coulomb_repulsion( this_node, other_node )
             next node

             for each spring connected to this node
                 net-force := net-force + Hooke_attraction( this_node, spring )
             next spring

             // without damping, it moves forever
             this_node.velocity := (this_node.velocity + timestep * net-force) * damping
             this_node.position := this_node.position + timestep * this_node.velocity
             total_kinetic_energy := total_kinetic_energy + this_node.mass * (this_node.velocity)^2
         next node
     until total_kinetic_energy is less than some small number  //the simulation has stopped moving

 *
 * @author horizon
 */
public class TableViewSpringSorter implements Serializable, ITablePageSorter {

    private double[][] relativeAttraction;
    InternalDataDAO iDAO;

    private static double NODE_1_STRENGTH = 2;
    private static double NODE_2_STRENGTH = 1;
    private static double NODE_3_STRENGTH = .5;
    private static double NODE_4_STRENGTH = .1;
    private static double UNLINKED_STRENGTH = 0;

    private static double COULOMBS_CONSTANT = 8.9875517873681764 * .00000001;
    private static int NODE_MASS = 1;
    private static double DAMPING = .5;
    private static double TIME_STEP = .1;
    private static int OPTIMAL_DISTANCE = 50;
    private static int CENTER_X = 550;
    private static int CENTER_Y = 100;

    ///////////////////  Singletoning it  ///////////////////
   private static TableViewSpringSorter instance = null;

   /**
    * private constructor
    */
   public TableViewSpringSorter(InternalDataDAO iDAO) {
        this.iDAO = iDAO;
    }
//
////Was causing the program to load an incorrenct internal DAO database
////   /**
////    * private constructor
////    */
////   private TableViewSorter() {
////       this.iDAO = iDAO.getInstance("/test.db");
////    }
//
//   /**
//    * returns an instance of the Controller.
//    * @return
//    */
//   public static TableViewSpringSorter getInstance(InternalDataDAO iDAO) {
//      if (instance == null) {
//         instance = new TableViewSpringSorter(iDAO);
//      } else {
//          instance.setIDAO(iDAO);
//      }
//      return instance;
//   }

//    Was causing the program to load an incorrenct internal DAO database
//    /**
//    * returns an instance of the Controller.
//    * @return
//    */
//   public static TableViewSorter getInstance() {
//      if (instance == null) {
//         instance = new TableViewSorter();
//      }
//      return instance;
//   }

    /**
     * Main action method, gets everything set up and runs the Force-Based sorting algorithm
     * @param tables
     * @return
     */
    public List<TableView> SortPage(SchemaPage currentPage, boolean resort) {
        int i = 0;
        relativeAttraction = new double[currentPage.getTableViews().size()][currentPage.getTableViews().size()];
        List<TableView> tableViewswDirtyValues = new ArrayList();
        List<TableView> tableViews = new ArrayList();
        
        tableViews = currentPage.getTableViews();
        if (tableViewswDirtyValues.size()>0 || resort) {

            List<TableView> tableViewstoSort = new ArrayList();
             if (resort) {
                 for (TableView tv : tableViews) {
                    randomPositions(tv, currentPage.getTableViews().size());
                 }
                 tableViewstoSort.addAll(tableViews);
             } else {
                tableViewstoSort.addAll(tableViewswDirtyValues);
             }
            // calculate relative attraction.
            // We only need to go through half of the tables on the inner iteration, so we shortcut it.
            List<TableView> TVToGoThrough = new ArrayList();
            TVToGoThrough.addAll(tableViews);
            for (TableView tv1 : tableViews) {
                TVToGoThrough.remove(tv1);
                for (TableView tv2 : TVToGoThrough) {
                    double attraction = calcRelativeForce(tv1, tv2);
                    relativeAttraction[tv1.getId()][tv2.getId()] = attraction;
                    relativeAttraction[tv2.getId()][tv1.getId()] = attraction;
                }
            }

//            for (TableView tv : tableViews) {
//                System.out.println("Table:"+tv.getTable().getName() + " "+tv.getX()+","+tv.getY());
//            }

    //        if (tableViews.size()>0)
    //        return tableViews;

            int n = 0;
            long total_kinetic_energy = 0; // running sum of total kinetic energy over all particles
            do {
                n++;
//                System.out.println("Spring Iteration: "+n);
                 total_kinetic_energy = 0; // reset to 0 each round.
                 for (TableView tv1 : tableViewstoSort) {
                     long[] net_force = {0, 0}; // running sum of total force on this particular node - (x, y)

                     boolean hitBoundary = false;

                     for (TableView tv2 : tableViews) {
                         if (tv1 != tv2) {
    //                         System.out.println("Dist to "+tv2.getTable().getName()+": "+tv1.calcDistanceWRadius(tv2)+" θ:"+tv1.calcAngle(tv2));
    //                         System.out.println("bf: "+calcBoundaryForce(tv1,tv2));
                             long energy = 0;
    //                         energy += calcForce(tv1,tv2);//net-force := net-force + Coulomb_repulsion( this_node, other_node )
                             energy += calcHookeForce(tv1,tv2);//net-force := net-force + Hooke_attraction( this_node, spring )
    //                         energy += calcCoulombForce(tv1,tv2);
                             hitBoundary = calcBoundaryForce(tv1,tv2);

                             double angle = tv1.calcAngle(tv2);

                             net_force[0] += energy * Math.cos(angle);
                             net_force[1] += energy * Math.sin(angle);

                             if (hitBoundary) {
                                 tv1.setVelocityX(tv1.getVelocityX() * -0.35);
                                 tv1.setVelocityY(tv1.getVelocityY() * -0.35);
                                 net_force[0] = (long).5 * net_force[0];
                                 net_force[1] = (long).5 * net_force[1];
                             }
                         }
                     }


                     net_force[0] += (hitBoundary ? 0.4 : 1) * calcGravity(tv1.getX()-CENTER_X);
                     net_force[1] += (hitBoundary ? 0.4 : 1) * calcGravity(tv1.getY()-CENTER_Y);
                     //System.out.println("G:"+net_force[1]);



                     // without damping, it moves forever
                     //this_node.velocity := (this_node.velocity + timestep * net-force) * damping
                     tv1.setVelocityX((tv1.getVelocityX() + TIME_STEP * net_force[0]) * DAMPING);
                     tv1.setVelocityY((tv1.getVelocityY() + TIME_STEP * net_force[1]) * DAMPING);

                     //this_node.position := this_node.position + timestep * this_node.velocity;
                     //x = r × cos( θ )
                     tv1.setX(tv1.getX() + TIME_STEP * tv1.getVelocityX());
                     //y = r × sin( θ )
                     tv1.setY(tv1.getY() + TIME_STEP * tv1.getVelocityY());

                     //total_kinetic_energy += NODE_MASS * (this_node.velocity)^2;
                     total_kinetic_energy += NODE_MASS * Math.pow(tv1.getVelocityX(),2)+Math.pow(tv1.getVelocityY(),2);

//                System.out.println("Table:"+tv1.getTable().getName() + " "+tv1.getX()+", "+tv1.getY()+" Vx:"+tv1.getVelocityX()+" Vy:"+tv1.getVelocityY());
                 }

//                System.out.println("  KE: "+total_kinetic_energy);
            } while ((n < 10) || (total_kinetic_energy > 1000 && n < tableViews.size() * 50));

            for (TableView tv : tableViews) {
                tv.setClean();
            }

        }

        /* PLAN: Spring algorithm
         *
         */
        currentPage.setSorted(true);
        return tableViews;
    }

    /**
     * generates lines for the diagram.
     *
     * @param tableViews
     * @return
     */
    public List<LinkLine> calcLines(SchemaPage page) {
        List<LinkLine> lines = new ArrayList();
        for (TableView tv : page.getTableViews()) {
            for (ForeignKey fk : tv.getTable().getForeignKeys().values()){
                lines.add(new LinkLine(tv.getTable(),fk,page));
            }
        }
        page.setLines(lines);
        return lines;
    }

     /**
     * Coulomb and Hooke's Law's weren't working right in this model, so I made my own force equation
     *
     *  -1 * relative Attraction * distance
     *
     *
     * A positive force implies a repulsive interaction, while a negative force implies an attractive interaction.
     *
     * @return
     */
    private double calcForce(TableView tv1, TableView tv2) {
        double distance = tv1.calcDistanceWRadius(tv2);
        double attraction = relativeAttraction[tv1.getId()][tv2.getId()];
        double force = 0;
        
        force = -.1 * attraction * distance;
        //System.out.println(relativeAttraction[tv1.getId()][tv2.getId()]+" Force of "+tv2.getTable().getName()+" "+tv1.calcDistanceWRadius(tv2)+" away on "+tv1.getTable().getName()+": "+force);
        return force;
    }

     /**
     * Coulomb and Hooke's Law's weren't working right in this model, so I made my own force equation
     *
     * for negative values (means there is a collision) -3 * relative Attraction * distance^2
     * for positive values relative Attraction * distance^2
     *
     *
     * A positive force implies a repulsive interaction, while a negative force implies an attractive interaction.
     *
     * @return
     */
    private boolean calcBoundaryForce(TableView tv1, TableView tv2) {
        double distance = tv1.calcDistanceWRadius(tv2);
        double angle = tv1.calcAngle(tv2);

        boolean hitBoundary = false;
        if (distance < 0 ) {
            hitBoundary = true;
            tv1.setX(tv1.getX() + 4 * distance * Math.cos(angle));
            tv1.setY(tv1.getY() + 4 * distance * Math.sin(angle));
        }

        //System.out.println(relativeAttraction[tv1.getId()][tv2.getId()]+" Force of "+tv2.getTable().getName()+" "+tv1.calcDistanceWRadius(tv2)+" away on "+tv1.getTable().getName()+": "+force);
        return hitBoundary;
    }

     /**
     * Calculate gravity, so as the tables "fall" to the center.
      *
      * We don't want them too clumped, so gravity will be less near the center,
      * based on total schema size.
     *
     * gravity =
     * A positive force implies a repulsive interaction, while a negative force implies an attractive interaction.
     *
     * @return
     */
    private double calcGravity(double distance) {
        double force  = 0;
//        distance = Math.abs(distance);
        if (distance>0) {
            force = -9.8 * TIME_STEP * distance;
        } else {
            force = -980 * TIME_STEP * distance;
        }
//        force = -9.8 * TIME_STEP * distance;
        return force;
    }

    /**
     * Calculates the amount of force according to Coulomb's Law:
     *
     * F = ke*((q1*q2)/r2)
     *
     * We approximate q1*q2 using the value from the relativeAttraction Table
     *
     * A positive force implies a repulsive interaction, while a negative force implies an attractive interaction.
     *
     * @return
     */
    private double calcCoulombForce(TableView tv1, TableView tv2) {
        double ccf =  COULOMBS_CONSTANT * TIME_STEP * ((relativeAttraction[tv1.getId()][tv2.getId()])/(tv1.calcDistanceWRadius(tv2)*1000));
        //System.out.println("CC: "+ccf);
        return ccf;
    }

    /**
     * Calculates the amount of force due accoding to Hooke's Law:
     *
     * F = -kx
     *
     * we approximate the spring constant from the relativeAttraction Table
     *
     * The distance x includes the radius of the table, so overlapping tables will repel each other.
     *
     * the force constant should never be zero or negative, so we replace those values with .1
     *
     * A positive force implies a repulsive interaction, while a negative force implies an attractive interaction.
     * 
     * @param tv1
     * @param tv2
     * @return
     */
    private double calcHookeForce(TableView tv1, TableView tv2) {
        double attraction = relativeAttraction[tv1.getId()][tv2.getId()];
        //System.out.println("HKa:"+attraction);
        double hookeForce =  -.1 * attraction * (tv1.calcDistanceWRadius(tv2) - OPTIMAL_DISTANCE);
        //System.out.println("HKe:"+hooke);
        return hookeForce;
    }

    private void randomPositions(TableView tv, int numTables){
        tv.setX((Math.random()) * 2 * numTables*200);
        tv.setY((Math.random()) * 2 * numTables*50+300);
        tv.calcLinksAndRadius();
        tv.setDirty();
    }

    /**
     * Calculates the relatative attractive force of two tables.  If we are comparing
     * the table with itself, the attraction is 0.  The further away a node is linked,
     * the lesser the attraction.  if no link is found within 4 nodes,
     * the attraction is negative.
     * 
     * @param tv1
     * @param tv2
     * @return
     */
    private double calcRelativeForce(TableView tv1, TableView tv2) {
        if (tv1 == tv2) return 0.0;

        double relativeForce = UNLINKED_STRENGTH;
        String table1name = tv1.getTable().getName();
        int level = 1;
        int nodeDistance = linkStrength(level, table1name, tv2);
        
        if (nodeDistance==1) {
            relativeForce = NODE_1_STRENGTH;
        } else if (nodeDistance==2) {
            relativeForce = NODE_2_STRENGTH;
        }else if (nodeDistance==3) {
            relativeForce = NODE_3_STRENGTH;
        } else if (nodeDistance==4) {
            relativeForce = NODE_4_STRENGTH;
        }
        return relativeForce;
    }

    /**
     * Recursive function that determines if the second table is within 4 links
     * of the first table. Uses some short-cutting to cut down on unnessary checking
     *
     * @param level
     * @param tableNameToFind
     * @param tv
     * @return
     */
    private int linkStrength (int level, String tableNameToFind, TableView tv){
        if (level > 4) return 0;
        //if the table links on this level, return it
        for (TableView tv0 : tv.getReferences()) {
            if (tv0.getTable().getName().equals(tableNameToFind)) return level;
        }
        int nodeDistance = 0;
        for (TableView tv0 : tv.getReferences()) {
            int recurs = linkStrength(level+1, tableNameToFind, tv0);
            if (recurs>0 && recurs<nodeDistance) nodeDistance = recurs;
            // level+1 is the best it could be, so no need to search further
            if (nodeDistance == level+1) return nodeDistance;
        }
        return nodeDistance;
    }

    public InternalDataDAO getIDAO() {
        return iDAO;
    }

    public void setIDAO(InternalDataDAO iDAO) {
        this.iDAO = iDAO;
    }

}
