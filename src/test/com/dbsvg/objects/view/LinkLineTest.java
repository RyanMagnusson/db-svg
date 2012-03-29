/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.objects.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.objects.model.Column;
import com.dbsvg.objects.model.ForeignKey;
import com.dbsvg.objects.model.Table;

/**
 * 
 * @author derrick.bowen
 */
public class LinkLineTest {

	LinkLine instance;

	@Mock
	Table t;
	@Mock
	TableView tv;

	int t1_x = 10;
	int t1_y = 20;
	int t1_width = 30;
	int t1_height = 40;
	double t1_radius = 50.0;

	@Mock
	Table t_foreign;
	@Mock
	TableView tv_foreign;

	int t2_x = 60;
	int t2_y = 70;
	int t2_width = 80;
	int t2_height = 90;
	double t2_radius = 100.0;

	@Mock
	ForeignKey fk;
	@Mock
	Column fkColumn;
	@Mock
	SchemaPage page;
	UUID pageId = UUID.randomUUID();

	Map<UUID, TableView> tableViews = new HashMap<UUID, TableView>();
	Map<UUID, TableView> foreignTableViews = new HashMap<UUID, TableView>();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(page.getId()).thenReturn(pageId);

		when(fk.getReference()).thenReturn(fkColumn);
		when(fkColumn.getTable()).thenReturn(t_foreign);

		tableViews.put(pageId, tv);
		when(t.getTablePageViews()).thenReturn(tableViews);

		when(t.getHeight()).thenReturn(t1_height);
		when(t.getWidth()).thenReturn(t1_width);
		when(tv.getX()).thenReturn(t1_x);
		when(tv.getY()).thenReturn(t1_y);
		when(tv.getRadius()).thenReturn(t1_radius);

		foreignTableViews.put(pageId, tv_foreign);
		when(t_foreign.getTablePageViews()).thenReturn(foreignTableViews);

		when(t_foreign.getHeight()).thenReturn(t2_height);
		when(t_foreign.getWidth()).thenReturn(t2_width);
		when(tv_foreign.getX()).thenReturn(t2_x);
		when(tv_foreign.getY()).thenReturn(t2_y);
		when(tv_foreign.getRadius()).thenReturn(t2_radius);

		instance = new LinkLine(t, fk, page);
	}

	/**
	 * Test of getPage method, of class LinkLine.
	 */
	@Test
	public void testGetPage() {
		SchemaPage result = instance.getPage();
		assertEquals(page, result);
	}

	@Test
	public void getEndRadius_bothOnPage() {
		Double result = instance.getEndRadius();
		assertNotNull(result);
		assertEquals(t2_radius, result, 0.0);
	}

	@Test
	public void getEndRadius_notOnPage() {
		foreignTableViews.clear();

		Double result = instance.getEndRadius();
		assertNull(result);
	}

	@Test
	public void calculateLine_bothOnPage() {

		instance.calculateLine(t, fk);

		assertEquals(t2_radius, instance.getEndRadius(), 0.0);
		assertEquals(25, instance.getX1(), 0.0);
		assertEquals(100, instance.getX2(), 0.0);
		assertEquals(29, instance.getXa1(), 0.1);
		assertEquals(23, instance.getXa2(), 0.1);
		assertEquals(5, instance.getXa3(), 0.1);
		assertEquals(40, instance.getY1(), 0.0);
		assertEquals(115, instance.getY2(), 0.0);
		assertEquals(44, instance.getYa1(), 0.1);
		assertEquals(20, instance.getYa2(), 0.1);
		assertEquals(38, instance.getYa3(), 0.1);

	}

	@Test
	public void calculateLine_notOnPage() {
		foreignTableViews.clear();

		instance.calculateLine(t, fk);

		assertEquals(0, instance.getX1(), 0.0);
		assertEquals(0, instance.getXa1(), 0.1);
		assertEquals(0, instance.getXa2(), 0.1);
		assertEquals(0, instance.getXa3(), 0.1);
		assertEquals(0, instance.getY1(), 0.0);
		assertEquals(0, instance.getYa1(), 0.1);
		assertEquals(0, instance.getYa2(), 0.1);
		assertEquals(0, instance.getYa3(), 0.1);

	}

	@Test
	public void calculateLine_triangleCorrect() {

		when(t.getHeight()).thenReturn(75);
		when(t.getWidth()).thenReturn(176);
		when(tv.getX()).thenReturn(2164);
		when(tv.getY()).thenReturn(-1430);
		when(tv.getRadius()).thenReturn(191.314);

		when(t_foreign.getHeight()).thenReturn(120);
		when(t_foreign.getWidth()).thenReturn(211);
		when(tv_foreign.getX()).thenReturn(2593);
		when(tv_foreign.getY()).thenReturn(-1244);
		when(tv_foreign.getRadius()).thenReturn(242.74);

		instance.calculateLine(t, fk);

		assertEquals(2478, instance.getXa1(), 0.1);
		assertEquals(-1287, instance.getYa1(), 0.1);
		assertEquals(2464, instance.getXa2(), 0.1);
		assertEquals(-1307, instance.getYa2(), 0.1);
		assertEquals(2454, instance.getXa3(), 0.1);
		assertEquals(-1285, instance.getYa3(), 0.1);

	}

}