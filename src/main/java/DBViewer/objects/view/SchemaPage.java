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
package DBViewer.objects.view;

import java.util.*;
import java.io.Serializable;
import DBViewer.objects.model.*;
import DBViewer.services.*;
/**
 *
 * @author horizon
 */
public class SchemaPage implements Comparable<SchemaPage>,Serializable{

    List<TableView> tableViews = new ArrayList();
    String title;
    UUID id;
    int orderid;
    int width = 0;
    int height = 0;
    int transx = 0;
    int transy = 0;
    SortedSchema schema;
    List<LinkLine> lines = new ArrayList();
    ITablePageSorter pageSorter;
    Boolean sorted = false;

    public SchemaPage(){
        this.id = UUID.randomUUID();
    }

    public SchemaPage(UUID id){
        this.id = id;
    }

    public SchemaPage(String id){
        this.id = UUID.fromString(id);
    }
    
    /**
     * Calculates the schema height width and translation values based on the tableviews
     */
    public void calcDimensions() {
        double minx = 2000;
        double miny = 2000;
        double maxx = 0;
        double maxy = 0;

        for (TableView tv : tableViews) {
            double x = tv.getX();
            double y = tv.getY();

            if (x < minx) minx = x;
            if (y < miny) miny = y;
            if (x > maxx) maxx = x;
            if (y > maxy) maxy = y;
        }
        setWidth((int)(maxx-minx)+300);
        setHeight((int)(maxy-miny)+500);
        setTransx((int)(-1 * minx)+20);
        setTransy((int)(-1 * miny)+20);
    }

    public void setTableViewPosition(int i, String x_pos, String y_pos) {
        try {
            TableView t = tableViews.get(i);
            t.setX(Double.parseDouble(x_pos));
            t.setY(Double.parseDouble(y_pos));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TableView> getTableViews() {
        return tableViews;
    }

    public void setTableViews(List<TableView> tableViews) {
        this.tableViews = tableViews;
        sorted = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * sets the UUID by String
     * @param id
     */
    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTransx() {
        return transx;
    }

    public void setTransx(int transx) {
        this.transx = transx;
    }

    public int getTransy() {
        return transy;
    }

    public void setTransy(int transy) {
        this.transy = transy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public SortedSchema getSchema() {
        return schema;
    }

    public void setSchema(SortedSchema schema) {
        this.schema = schema;
    }

    public List<LinkLine> getLines() {
        return lines;
    }

    public void setLines(List<LinkLine> lines) {
        this.lines = lines;
    }

    public boolean areTableViewsClean() {
        boolean allClean = true;
        for(TableView tv : tableViews) {
            if (tv.isDirty())
                allClean = false;
        }
        return allClean;
    }

    public int compareTo(SchemaPage o) {
        if (this.orderid == o.getOrderid())
            return 0;
        else if (this.orderid > o.getOrderid())
            return 1;
        else
            return -1;
    }

    /**
     * Checks to see if the given table is in the schema page.
     * @param t
     * @return
     */
    public boolean contains(Table t){
            for (TableView tv : tableViews){
                if (tv.getTable().getId().compareTo(t.getId())==0) return true;
            }
        return false;
    }

    public Boolean isSorted() {
        return sorted;
    }

    public void setSorted(Boolean sorted) {
        this.sorted = sorted;
    }
    

}
