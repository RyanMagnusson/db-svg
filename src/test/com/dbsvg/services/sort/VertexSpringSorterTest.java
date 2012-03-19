package com.dbsvg.services.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.models.JdbcMainDAO;
import com.dbsvg.objects.model.Column;
import com.dbsvg.objects.model.ForeignKey;
import com.dbsvg.objects.model.PrimaryKey;
import com.dbsvg.objects.model.PrimaryKeyObject;
import com.dbsvg.objects.model.Table;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.TableView;
import com.dbsvg.objects.view.Vertex;
import com.dbsvg.objects.view.VertexSet;

public class VertexSpringSorterTest {
	VertexSpringSorter instance;

	Vertex v1 = null;
	Vertex v2 = null;
	Vertex v3 = null;
	Vertex v4 = null;

	double r1 = 20;
	double r2 = 30;
	double r3 = 10;
	double r4 = 2.5;

	List<Vertex> vertices;

	@Mock
	SchemaPage page;
	UUID pageId = UUID.randomUUID();

	@Mock
	InitialPositionDistributionStrategy initialDistributionStrategy;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(page.getId()).thenReturn(pageId);
		instance = new VertexSpringSorter();
		instance.initialDistributionStrategy = initialDistributionStrategy;
		v1 = new Vertex();
		v1.setRadius(r1);
		v1.setSorted();
		v2 = new Vertex();
		v2.setRadius(r2);
		v2.setSorted();
		v3 = new Vertex();
		v3.setRadius(r3);
		v3.setSorted();
		v4 = new Vertex();
		v4.setRadius(r4);
		v4.setSorted();
		v1.addReference(v2);
		v2.addReference(v1);
		v1.addReference(v3);
		v3.addReference(v1);
		v1.addReference(v4);
		v4.addReference(v1);

		vertices = new ArrayList<Vertex>();
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
	}

	@Test
	public void doOneSpringIteration() {
		double expectedKE = 6395.89;
		v1.setX(50);
		v1.setY(50);
		v2.setX(10);
		v2.setY(50);
		v3.setX(50);
		v3.setY(10);
		v4.setX(10);
		v4.setY(10);
		double ke = instance.doOneSpringIteration(vertices);
		assertEquals(104, (int) v1.getX());
		assertEquals(56, (int) v1.getY());
		assertEquals(-45, (int) v2.getX());
		assertEquals(57, (int) v2.getY());
		assertEquals(57, (int) v3.getX());
		assertEquals(2, (int) v3.getY());
		assertEquals(2, (int) v4.getX());
		assertEquals(2, (int) v4.getY());
		assertEquals(expectedKE, ke, 0.1);
	}

	@Test
	public void doOneSpringIterationWith0Distance() {
		double expectedKE = 20571.48;
		v1.setX(50);
		v1.setY(50);
		v2.setX(50);
		v2.setY(50);
		v3.setX(50);
		v3.setY(10);
		v4.setX(10);
		v4.setY(10);
		double ke = instance.doOneSpringIteration(vertices);
		assertEquals(62, (int) v1.getX());
		assertEquals(64, (int) v1.getY());
		assertEquals(-53, (int) v2.getX());
		assertEquals(-48, (int) v2.getY());
		assertEquals(54, (int) v3.getX());
		assertEquals(1, (int) v3.getY());
		assertEquals(0, (int) v4.getX());
		assertEquals(5, (int) v4.getY());
		assertEquals(expectedKE, ke, 0.1);
	}

	@Test
	public void do100SpringIterations() {
		double expectedKE = 1.889;
		v1.setX(50);
		v1.setY(50);
		v2.setX(10);
		v2.setY(50);
		v3.setX(50);
		v3.setY(10);
		v4.setX(10);
		v4.setY(10);
		double ke = 0;
		for (int i = 1; i <= 100; i++) {
			System.out.println(i);
			ke = instance.doOneSpringIteration(vertices);
		}
		assertEquals(25, (int) v1.getX());
		assertEquals(23, (int) v1.getY());
		assertEquals(-81, (int) v2.getX());
		assertEquals(171, (int) v2.getY());
		assertEquals(203, (int) v3.getX());
		assertEquals(39, (int) v3.getY());
		assertEquals(-37, (int) v4.getX());
		assertEquals(-142, (int) v4.getY());
		assertEquals(expectedKE, ke, 0.1);
	}

	@Test
	public void springContiguousSet() {
		v1.setX(50);
		v1.setY(50);
		v2.setX(10);
		v2.setY(50);
		v3.setX(50);
		v3.setY(10);
		v4.setX(10);
		v4.setY(10);
		instance.springContiguousSet(vertices);
		assertEquals(-14, (int) v1.getX());
		assertEquals(63, (int) v1.getY());
		assertFalse(v1.needsResort());
		assertEquals(-141, (int) v2.getX());
		assertEquals(143, (int) v2.getY());
		assertFalse(v2.needsResort());
		assertEquals(259, (int) v3.getX());
		assertEquals(-46, (int) v3.getY());
		assertFalse(v3.needsResort());
		assertEquals(8, (int) v4.getX());
		assertEquals(-47, (int) v4.getY());
		assertFalse(v4.needsResort());
	}

	@Test
	public void runSpringSort() {
		vertices.clear();
		v1.setX(50);
		v1.setY(50);
		v1.setRadius(200);
		v2.setX(50);
		v2.setY(49);
		v2.setRadius(200);
		v3.setX(48);
		v3.setY(48);
		v3.setRadius(200);
		v4.setX(49);
		v4.setY(50);
		v4.setReferences(new ArrayList<Vertex>());
		v1.getReferences().remove(v4);
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
		instance.sort(vertices);
		verify(initialDistributionStrategy).distributeVertices(vertices);
		assertEquals(-12, (int) v1.getX());
		assertEquals(79, (int) v1.getY());
		assertFalse(v1.needsResort());
		assertEquals(259, (int) v2.getX());
		assertEquals(261, (int) v2.getY());
		assertFalse(v2.needsResort());
		assertEquals(-459, (int) v3.getX());
		assertEquals(48, (int) v3.getY());
		assertFalse(v3.needsResort());
		assertEquals(166, (int) v4.getX());
		assertEquals(-32, (int) v4.getY());
		assertFalse(v4.needsResort());
	}

	@Test
	public void springNonContiguousSetWithOverlap() {
		vertices.clear();
		v1.setX(50);
		v1.setY(50);
		v1.setRadius(200000);
		v2.setX(50);
		v2.setY(49);
		v2.setRadius(200000);
		v3.setX(48);
		v3.setY(48);
		v3.setRadius(200000);
		v4.setX(49);
		v4.setY(50);
		v4.setReferences(new ArrayList<Vertex>());
		v1.getReferences().remove(v4);
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
		instance.springContiguousSet(vertices);
		assertEquals(5956, (int) v1.getX());
		assertEquals(-4144, (int) v1.getY());
		assertFalse(v1.needsResort());
		assertEquals(5945, (int) v2.getX());
		assertEquals(-4485, (int) v2.getY());
		assertFalse(v2.needsResort());
		assertEquals(6665, (int) v3.getX());
		assertEquals(-3953, (int) v3.getY());
		assertFalse(v3.needsResort());
		assertEquals(-18411, (int) v4.getX());
		assertEquals(12826, (int) v4.getY());
		assertFalse(v4.needsResort());
	}

	@Test
	public void springContiguousSet2tableMock() {
		Table table1 = new Table("table1");
		PrimaryKey t1id = new PrimaryKeyObject("id");
		Map<String, Column> t1col = new HashMap<String, Column>();
		t1col.put(t1id.getName(), t1id);
		table1.setColumns(t1col);
		int maxWidth = (int) (table1.getName().length() * 1.5);
		table1.setWidth(JdbcMainDAO.CHAR_WIDTH * maxWidth + JdbcMainDAO.PAD_WIDTH);
		table1.setHeight(JdbcMainDAO.CHAR_HEIGHT * table1.getColumns().size() + JdbcMainDAO.PAD_HEIGHT);

		Table table2 = new Table("table1");
		ForeignKey fk1 = new ForeignKey("t1id_fk");
		fk1.setReference(t1id);
		Map<String, Column> t2col = new HashMap<String, Column>();
		t2col.put(fk1.getName(), fk1);
		table1.getReferencingTables().put(table2.getName(), table2);
		maxWidth = (int) (table2.getName().length() * 1.5);
		table2.setWidth(JdbcMainDAO.CHAR_WIDTH * maxWidth + JdbcMainDAO.PAD_WIDTH);
		table2.setHeight(JdbcMainDAO.CHAR_HEIGHT * table2.getColumns().size() + JdbcMainDAO.PAD_HEIGHT);

		TableView tv1 = new TableView(table1, page);// 608, 439,
		// 85.61117);
		tv1.setX(608);
		tv1.setY(439);
		tv1.calcLinksAndRadius();

		TableView tv2 = new TableView(table2, page);// (331, 369,
		// 122.63396);
		tv2.setX(331);
		tv2.setY(369);
		tv2.calcLinksAndRadius();

		List<Vertex> tableViews = new ArrayList<Vertex>();
		tableViews.add(tv1);
		tableViews.add(tv2);
		instance.springContiguousSet(tableViews);

		assertEquals(628, (int) tv1.getX());
		assertEquals(439, (int) tv1.getY());
		assertFalse(tv1.needsResort());
		assertEquals(301, (int) tv2.getX());
		assertEquals(359, (int) tv2.getY());
		assertFalse(tv2.needsResort());
	}

	@Test
	public void splitIntoContiguousSets() {
		Vertex v5 = new Vertex();
		vertices.add(v5);
		Vertex v6 = new Vertex();
		v6.addReference(v3);
		v3.addReference(v6);
		v6.addReference(v1);
		vertices.add(v6);

		List<VertexSet> sets = instance.splitIntoContiguousSets(vertices);

		assertEquals(2, sets.size());

		assertTrue(sets.get(0).contains(v1));
		assertTrue(sets.get(0).contains(v2));
		assertTrue(sets.get(0).contains(v3));
		assertTrue(sets.get(0).contains(v4));
		assertFalse(sets.get(0).contains(v5));
		assertTrue(sets.get(0).contains(v6));

		assertFalse(sets.get(1).contains(v1));
		assertFalse(sets.get(1).contains(v2));
		assertFalse(sets.get(1).contains(v3));
		assertFalse(sets.get(1).contains(v4));
		assertTrue(sets.get(1).contains(v5));
		assertFalse(sets.get(1).contains(v6));
	}

	@Test
	public void prepareVertexSetsForSort() {

		v1.setX(50);
		v1.setY(50);
		v2.setX(10);
		v2.setY(50);
		v3.setX(50);
		v3.setY(10);
		v4.setX(10);
		v4.setY(10);
		VertexSet set1 = new VertexSet();
		set1.addVertices(vertices);

		List<Vertex> vertices2 = new ArrayList<Vertex>();
		Vertex v5 = new Vertex();
		v5.setX(550);
		v5.setY(550);
		v5.setRadius(10);
		vertices2.add(v5);
		Vertex v6 = new Vertex();
		v6.setX(575);
		v6.setY(550);
		v6.setRadius(10);
		v6.addReference(v5);
		v5.addReference(v6);
		vertices2.add(v6);
		VertexSet set2 = new VertexSet();
		set2.addVertices(vertices2);

		List<VertexSet> sets = new ArrayList<VertexSet>();
		sets.add(set1);
		sets.add(set2);
		instance.prepareVertexSetsForSort(sets);
		assertEquals(1, set1.getReferences().size());
		assertEquals(set2.toString(), set1.getReferences().get(0).toString());
		assertEquals(1, set2.getReferences().size());
		assertEquals(set1.toString(), set2.getReferences().get(0).toString());
		assertEquals(21, (int) set1.getX());
		assertEquals(35, (int) set1.getY());
		assertEquals(21, (int) set1.getSnapshot().getX());
		assertEquals(35, (int) set1.getSnapshot().getY());
		assertEquals(562, (int) set2.getX());
		assertEquals(550, (int) set2.getY());
		assertEquals(562, (int) set2.getSnapshot().getX());
		assertEquals(550, (int) set2.getSnapshot().getY());

	}
}
