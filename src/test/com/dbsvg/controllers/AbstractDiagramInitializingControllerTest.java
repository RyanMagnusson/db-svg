/*
 * DB-SVG Copyright 2012 Derrick Bowen
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
 *   @author Derrick Bowen derrickbowen@dbsvg.com
 */
package com.dbsvg.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.models.InternalDataDAO;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.SortedSchema;
import com.dbsvg.services.IDBViewerCache;
import com.dbsvg.services.SchemaService;

public class AbstractDiagramInitializingControllerTest {

	AbstractDiagramInitializingController instance;

	@Mock
	HttpServletRequest request;
	@Mock
	HttpSession session;

	// Auto injected component mocks
	@Mock
	IDBViewerCache programCache;
	@Mock
	SchemaService schemaService;
	@Mock
	InternalDataDAO iDAO;

	@Mock
	SortedSchema currentSchema;
	@Mock
	ConnectionWrapper currentConn;
	@Mock
	ConnectionWrapper resultConn;
	@Mock
	Connection iDAOconn;
	@Mock
	SchemaPage currentPage;
	@Mock
	SchemaPage resultPage;

	@Captor
	ArgumentCaptor<SortedSchema> schemaCaptor;
	@Captor
	ArgumentCaptor<Map<String, ConnectionWrapper>> connectionsCaptor;

	String sessionPageId = null;
	Map<String, ConnectionWrapper> connections = null;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		instance = new AbstractDiagramInitializingController() {
		};
		instance.programCache = programCache;
		instance.schemaService = schemaService;
		instance.iDAO = iDAO;

		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("pageid")).thenReturn(sessionPageId);
		when(session.getAttribute("CurrentSchema")).thenReturn(currentSchema);
		when(session.getAttribute("CurrentConn")).thenReturn(currentConn);
		when(session.getAttribute("CurrentPage")).thenReturn(currentPage);

		when(iDAO.getConnection()).thenReturn(iDAOconn);
		when(schemaService.prepareSchema(any(SortedSchema.class),
				anyString(), anyString())).thenReturn(resultPage);

		when(currentConn.getSchemaId()).thenReturn("2");
		when(resultConn.getSchemaId()).thenReturn("3");

		connections = new HashMap<String, ConnectionWrapper>();
		connections.put("test", new ConnectionWrapper());
		when(iDAO.readAllConnectionWrappers(iDAOconn)).thenReturn(connections);
	}

	@Test
	public void initializeNoSessionValues() throws Exception {
		String dbi = "10";
		String page = null;

		when(session.getAttribute("CurrentSchema")).thenReturn(null);
		when(session.getAttribute("CurrentConn")).thenReturn(null);
		when(session.getAttribute("CurrentPage")).thenReturn(null);

		when(programCache.getConnection(dbi)).thenReturn(resultConn);

		SchemaPage result = instance.initialize(dbi, page, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi),
				(String) isNull());

		assertEquals(resultPage, result);
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test
	public void initializeNoSessionValuesWithPage() throws Exception {
		String dbi = "10";
		String page = "20";

		when(session.getAttribute("CurrentSchema")).thenReturn(null);
		when(session.getAttribute("CurrentConn")).thenReturn(null);
		when(session.getAttribute("CurrentPage")).thenReturn(null);

		when(programCache.getConnection(dbi)).thenReturn(resultConn);

		SchemaPage result = instance.initialize(dbi, page, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi), eq(page));

		assertEquals(resultPage, result);
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test
	public void initializeSameSessionValues() throws Exception {
		String dbi = "10";
		String page = null;

		when(programCache.getConnection(dbi)).thenReturn(currentConn);
		when(currentSchema.getDbi()).thenReturn(dbi);

		SchemaPage result = instance.initialize(dbi, page, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi),
				(String) isNull());

		assertEquals(resultPage, result);
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test
	public void initializeSameSessionValuesWithPage() throws Exception {
		String dbi = "10";
		String page = "20";

		when(programCache.getConnection(dbi)).thenReturn(currentConn);
		when(currentSchema.getDbi()).thenReturn(dbi);

		SchemaPage result = instance.initialize(dbi, page, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi), eq(page));

		assertEquals(resultPage, result);
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test
	public void initializeDifferentSessionValues() throws Exception {
		String dbi = "10";
		String oldDbi = "20";
		String page = null;

		when(programCache.getConnection(dbi)).thenReturn(resultConn);
		when(currentSchema.getDbi()).thenReturn(oldDbi);

		SchemaPage result = instance.initialize(dbi, page, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi),
				(String) isNull());

		assertEquals(resultPage, result);
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test(expected = IllegalArgumentException.class)
	public void initializeNullConnectionWrapper() throws Exception {
		String dbi = "10";
		String oldDbi = "20";
		String page = null;

		when(programCache.getConnection(dbi)).thenReturn(null);
		when(currentSchema.getDbi()).thenReturn(oldDbi);

		SchemaPage result = instance.initialize(dbi, page, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi),
				(String) isNull());

		assertEquals(resultPage, result);
		assertNull(resultSchema.getId());
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test
	public void initializeProgramCache() throws Exception {
		String dbi = "10";
		String page = null;

		when(programCache.getConnection(dbi)).thenReturn(currentConn);
		when(currentSchema.getDbi()).thenReturn(dbi);

		instance.initialize(dbi, page, request);
		verify(iDAO).setUpInternalDB(iDAOconn);
		verify(iDAO).readAllConnectionWrappers(iDAOconn);
		verify(programCache).setAllConnections(connectionsCaptor.capture());

		assertEquals(connections, connectionsCaptor.getValue());
	}

	@Test
	public void donTReinitializeProgramCache() throws Exception {
		String dbi = "10";
		String page = null;
		when(programCache.isInitialized()).thenReturn(true);

		when(programCache.getConnection(dbi)).thenReturn(currentConn);
		when(currentSchema.getDbi()).thenReturn(dbi);

		instance.initialize(dbi, page, request);
		verify(iDAO, times(0)).setUpInternalDB(iDAOconn);
		verify(iDAO, times(0)).readAllConnectionWrappers(iDAOconn);
		verify(programCache, times(0)).setAllConnections(connectionsCaptor.capture());
	}

}
