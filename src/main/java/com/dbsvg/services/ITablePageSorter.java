/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.services;

import java.util.*;

import com.dbsvg.models.*;
import com.dbsvg.objects.model.*;
import com.dbsvg.objects.view.*;

/**
 *
 * @author derrick.bowen
 */
public interface ITablePageSorter {

    public List<TableView> SortPage(SchemaPage currentPage, boolean resort);
    public List<LinkLine> calcLines(SchemaPage page);
}
