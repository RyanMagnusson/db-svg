/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.objects.model.Column;
import com.dbsvg.objects.model.ForeignKey;
import com.dbsvg.objects.model.Table;
import com.dbsvg.objects.view.LinkLine;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.TableView;
import com.dbsvg.services.sort.VertexSpringSorter;

/**
 * 
 * @author derrick.bowen
 */
public class TableViewSpringSorterTest {

	TableViewSpringSorter instance;

	@Mock
	VertexSpringSorter vertexSorter;

	@Mock
	SchemaPage page;
	UUID pageId = UUID.randomUUID();

	@Mock
	TableView tv;
	@Mock
	Table table;

	@Mock
	ForeignKey fk;
	@Mock
	Column fkColumn;
	@Mock
	Table table_ref_to;

	@Captor
	ArgumentCaptor<List<LinkLine>> lineCaptor;

	List<TableView> tableList;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		instance = new TableViewSpringSorter();
		instance.vertexSorter = vertexSorter;

		when(page.getId()).thenReturn(pageId);

		tableList = new ArrayList<TableView>();
		tableList.add(tv);
		when(page.getTableViews()).thenReturn(tableList);

		when(tv.getTable()).thenReturn(table);

		Map<String, ForeignKey> fkeys = new HashMap<String, ForeignKey>();
		fkeys.put("fk", fk);
		when(table.getForeignKeys()).thenReturn(fkeys);
		when(fk.getReference()).thenReturn(fkColumn);
		when(fkColumn.getTable()).thenReturn(table_ref_to);

		Map<UUID, TableView> refByTableViews = new HashMap<UUID, TableView>();
		refByTableViews.put(pageId, tv);
		when(table_ref_to.getTablePageViews()).thenReturn(refByTableViews);
	}

	/**
	 * Test of SortPage method, of class TableViewSpringSorter.
	 */
	@Test
	public void testSortPageResort() {
		boolean resort = true;
		instance.sortPage(page, resort);
		verify(vertexSorter).sort(tableList);
		verify(page).setSorted(true);
	}

	/**
	 * Test of SortPage method, of class TableViewSpringSorter.
	 */
	@Test
	public void testSortPageResortFalse() {
		boolean resort = false;
		instance.sortPage(page, resort);
		verify(vertexSorter, times(0)).sort(tableList);
		verify(page, times(0)).setSorted(true);
	}

	/**
	 * Test of calcLines method, of class TableViewSpringSorter.
	 */
	@Test
	public void testCalcLines() {
		instance.calcLines(page);
		verify(page).setLines(lineCaptor.capture());
		List<LinkLine> lines = lineCaptor.getValue();
		assertEquals(1, lines.size());
	}
}