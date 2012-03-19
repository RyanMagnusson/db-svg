package com.dbsvg.services.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbsvg.objects.view.Vertex;
import com.dbsvg.objects.view.VertexSet;

/**
 * Uses a version of the Spring/Force-Based Algorithm to calculate the best
 * positions for the tables.
 * 
 * Explanation & pseudocode taken from Wikipedia:
 * http://en.wikipedia.org/wiki/Force-based_algorithms_(graph_drawing)
 * 
 * see also: http://blog.ivank.net/force-based-graph-drawing-in-as3.html
 * http://g.ivank.net/
 * 
 * Each node has x,y position and dx,dy velocity and mass m. There is usually a
 * spring constant, s, and damping: 0 < damping < 1. The force toward and away
 * from nodes is calculated according to Hooke's Law and Coulomb's law or
 * similar as discussed above.
 * 
 * 
 * set up initial node velocities to (0,0) set up initial node positions
 * randomly
 * 
 * make sure no 2 nodes are in exactly the same position loop
 * total_kinetic_energy := 0
 * 
 * running sum of total kinetic energy over all particles for each node
 * net-force := (0, 0)
 * 
 * running sum of total force on this particular node
 * 
 * for each other node net-force := net-force + Coulomb_repulsion( this_node,
 * other_node ) next node
 * 
 * for each spring connected to this node net-force := net-force +
 * Hooke_attraction( this_node, spring ) next spring
 * 
 * without damping, it moves forever
 * 
 * this_node.velocity := (this_node.velocity + timestep * net-force) * damping
 * 
 * this_node.position := this_node.position + timestep * this_node.velocity
 * 
 * total_kinetic_energy := total_kinetic_energy + this_node.mass *
 * (this_node.velocity)^2
 * 
 * next node until total_kinetic_energy is less than some small number ( the
 * simulation has stopped moving )
 * 
 */
@Service
public class VertexSpringSorter {

	@Autowired
	InitialPositionDistributionStrategy initialDistributionStrategy;

	protected static final Logger LOG = LoggerFactory.getLogger(VertexSpringSorter.class);

	public final static int REPULSION_CONSTANT = 250;
	public final static int OVERLAP_CONSTANT = 2500;
	public final static double ATTRACTION_CONSTANT = 0.015;
	public final static double VELOCITY_DAMPENING = 0.85;

	public void sort(List<? extends Vertex> vertices) {
		initialDistributionStrategy.distributeVertices(vertices);
		List<VertexSet> sets = splitIntoContiguousSets(vertices);
		for (VertexSet set : sets) {
			springContiguousSet(set.getVertices());
		}
		prepareVertexSetsForSort(sets);
		springContiguousSet(sets);
		for (VertexSet set : sets) {
			set.translateVerticesPositionsSinceSnapshot();
		}
	}

	protected void prepareVertexSetsForSort(List<VertexSet> sets) {
		boolean first = true;
		for (VertexSet set : sets) {
			set.calcCenterAndRadius();
			set.snapshotPosition();
			if (first) {
				for (int i = 1; i < sets.size(); i++) {
					set.addReference(sets.get(i));
				}
				first = false;
			} else {
				set.addReference(sets.get(0));
			}
		}
	}

	/**
	 * 
	 * @param vertices
	 * @return a list of VertexSets that each contain all of the nodes connected
	 *         to each other
	 */
	protected List<VertexSet> splitIntoContiguousSets(List<? extends Vertex> vertices) {
		List<VertexSet> sets = new ArrayList<VertexSet>();
		Set<Vertex> allocatedToASet = new HashSet<Vertex>();
		for (Vertex v : vertices) {
			if (!allocatedToASet.contains(v)) {
				VertexSet set = new VertexSet();
				set.addVertex(v);
				allocatedToASet.add(v);
				recursiveCheckReferences(allocatedToASet, v, set);
				sets.add(set);
			}
		}
		return sets;
	}

	private void recursiveCheckReferences(Set<Vertex> allocatedToASet, Vertex v, VertexSet set) {
		for (Vertex u : v.getReferences()) {
			if (!allocatedToASet.contains(u)) {
				set.addVertex(u);
				allocatedToASet.add(u);
				recursiveCheckReferences(allocatedToASet, u, set);
			}
		}
	}

	/**
	 * Run the Spring sort on a contiguous set, meaning all of the nodes are
	 * attached to each other in some way. If the set is not contiguous,
	 * equilibrium will never be achieved.
	 * 
	 * @param vertices
	 *            to sort
	 */
	protected void springContiguousSet(Collection<? extends Vertex> vertices) {
		int n = 0;
		double total_kinetic_energy = 0; // running sum of total kinetic
		// energy over all particles
		if (vertices.size() <= 1) {
			return;
		}

		do {
			n++;
			total_kinetic_energy = doOneSpringIteration(vertices);
			LOG.debug("Spring Force iteration {} completed with KE {}", n, total_kinetic_energy);

			if (hasGoneTooLongWithNoHopeOfTranquility(vertices, n, total_kinetic_energy)) {
				LOG.warn("Spring Sort has run a long time without coming below KE limit: Aborting (Current KE {})", total_kinetic_energy);
				break;
			}

		} while (hasDoneLessThan10Iterations(n) || isAboveMaximumKE(vertices, total_kinetic_energy));

		for (Vertex v : vertices) {
			v.setSorted();
		}
	}

	private boolean hasDoneLessThan10Iterations(int n) {
		return (n < 10);
	}

	private boolean isAboveMaximumKE(Collection<? extends Vertex> vertices, double total_kinetic_energy) {
		return total_kinetic_energy > 25 * vertices.size();
	}

	private boolean hasGoneTooLongWithNoHopeOfTranquility(Collection<? extends Vertex> vertices, int n, double total_kinetic_energy) {
		return total_kinetic_energy > 100 * vertices.size() && n > vertices.size() * 50;
	}

	/**
	 * Runs a single interation of the Spring physics simulation
	 * 
	 * @param vertices
	 * @return the total kinetic energy of the run
	 */
	protected double doOneSpringIteration(Collection<? extends Vertex> vertices) {
		double total_kinetic_energy = 0; // reset to 0 each round.
		// calculate velocity for this round
		for (Vertex v : vertices) {

			checkVertexForNaNv(v);
			v.netForce().setX((double) 0);
			v.netForce().setY((double) 0);
			for (Vertex u : vertices) {
				if (!v.equals(u)) {
					double distance = v.calcDistance(u);

					if (distance == 0) {
						v.setX(v.getX() + 10);
						v.setY(v.getY() + 10);
						LOG.warn("two Vertexes with 0 distance detected. vXYR:{},{}|{} uXYR:{},{}|{} dist:{} ",
								new Object[] { v.getX(), v.getY(), v.getRadius(), u.getX(), u.getY(), u.getRadius(), distance });
					} else {

						double rsq = Math.pow(distance, 2);
						// If the vertices overlap, apply a stronger net force
						if (distance < u.getRadius() + v.getRadius()) {
							v.netForce().setX(v.netForce().getX() + OVERLAP_CONSTANT * (v.getX() - u.getX()) / rsq);
							v.netForce().setY(v.netForce().getY() + OVERLAP_CONSTANT * (v.getY() - u.getY()) / rsq);
							LOG.warn("Vertices Overlap v:{} u:{}", v, u);
						} else {
							v.netForce().setX(v.netForce().getX() + REPULSION_CONSTANT * (v.getX() - u.getX()) / rsq);
							v.netForce().setY(v.netForce().getY() + REPULSION_CONSTANT * (v.getY() - u.getY()) / rsq);
						}
						if (checkVertexForNaNnf(v))
							LOG.warn("NaN NetForce detected. vXYR:{},{}|{} uXYR:{},{}|{} dist:{} rsq:{}",
									new Object[] { v.getX(), v.getY(), v.getRadius(), u.getX(), u.getY(), u.getRadius(), distance, rsq });
					}
				}
			}
			LOG.debug("found {} references", v.getReferences().size());
			for (Vertex w : v.getReferences()) {
				v.netForce().setX(v.netForce().getX() + ATTRACTION_CONSTANT * (w.getX() - v.getX()));
				v.netForce().setY(v.netForce().getY() + ATTRACTION_CONSTANT * (w.getY() - v.getY()));
			}

			v.velocity().setX((v.velocity().getX() + v.netForce().getX()) * VELOCITY_DAMPENING);
			v.velocity().setY((v.velocity().getY() + v.netForce().getY()) * VELOCITY_DAMPENING);
		}
		// set new positions
		int i = 0;
		for (Vertex v : vertices) {
			i++;
			LOG.debug("{} {} moves to x:{}, y:{} velocity:{} net force:{}", new Object[] { i, v.toString(), (int) (v.getX() + v.velocity().getX()),
					(int) (v.getY() + v.velocity().getY()), v.velocity(), v.netForce() });
			v.setX(v.getX() + v.velocity().getX());
			v.setY(v.getY() + v.velocity().getY());

			// total_kinetic_energy += NODE_MASS * (this_node.velocity)^2;
			total_kinetic_energy += 1 * (Math.pow(v.velocity().getX(), 2)
					+ Math.pow(v.velocity().getY(), 2));
		}
		return total_kinetic_energy;
	}

	private boolean checkVertexForNaNv(Vertex v) {
		boolean found = false;
		if (Double.isNaN(v.velocity().getX())) {
			LOG.warn("X was NaN");
			v.velocity().setX((double) 0);
			found = true;
		}
		if (Double.isNaN(v.velocity().getY())) {
			LOG.warn("Y was NaN");
			v.velocity().setY((double) 0);
			found = true;
		}
		return found;
	}

	private boolean checkVertexForNaNnf(Vertex v) {
		return Double.isNaN(v.netForce().getX()) || Double.isNaN(v.netForce().getY());

	}

}
