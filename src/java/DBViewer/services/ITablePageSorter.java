/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.services;

import java.util.*;
import DBViewer.objects.view.*;
import DBViewer.objects.model.*;
import DBViewer.models.*;

/**
 *
 * @author derrick.bowen
 */
public interface ITablePageSorter {

    public List<TableView> SortPage(SchemaPage currentPage, boolean resort);
    public List<LinkLine> calcLines(SchemaPage page);
}
