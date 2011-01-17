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
import DBViewer.objects.model.*;
import java.io.Serializable;

/**
 * An Object Graph / View object that represents all of the tables in a schema (possibly
 * divided into multiple pages) and the lines connecting them.
 * 
 * @author horizon
 */
public class SortedSchema implements Serializable{

    int width = 0;
    int height = 0;
    int transx = 0;
    int transy = 0;
    Map<UUID,SchemaPage> pages = new LinkedHashMap();
    Map<String, Table> tables = new HashMap<String, Table>();
    String name = "";
    String dbi;

    boolean tablesSorted = false;

    /**
     * 
     */
    public SortedSchema() {
    }
    
/**
 * Convenience method.
 * @param i
 * @param x_pos
 * @param y_pos
 * @param p
 */
    public void setTableViewPosition(int i, String x_pos, String y_pos, SchemaPage p) {
        p.setTableViewPosition(i, x_pos, y_pos);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public SchemaPage getFirstPage() {
        if (pages.size()>0){
            List<SchemaPage> pageList= new ArrayList();
            pageList.addAll(pages.values());
            Collections.sort(pageList);
            return pageList.get(0);
        } 
        return null;
    }

    public List<TableView> getDefaultTableViews() {
        SchemaPage page = getFirstPage();
        if (page!=null)
            return page.getTableViews();
        return new ArrayList();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Menu Database Index
     * @return 
     */
    public String getDbi() {
        return dbi;
    }
    
    /**
     * Menu Database Index
     * @return 
     */
    public void setDbi(String dbi) {
        this.dbi = dbi;
    }

    public Map<UUID, SchemaPage> getPages() {
        return pages;
    }

    public void setPages(Map<UUID, SchemaPage> pages) {
        this.pages = pages;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }

    public boolean isTablesSorted() {
        return tablesSorted;
    }

    public void setTablesSorted(boolean tablesSorted) {
        this.tablesSorted = tablesSorted;
    }
    
    public int getNumTables() {
        return tables.size();
    }

}
