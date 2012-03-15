/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.services;

import java.util.List;

import com.dbsvg.objects.view.LinkLine;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.TableView;

/**
 * 
 * @author derrick.bowen
 */
public interface ITablePageSorter {

	public List<TableView> SortPage(SchemaPage currentPage, boolean resort);

	public List<LinkLine> calcLines(SchemaPage page);
}
