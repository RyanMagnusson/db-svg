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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

public class SetupControllerTest {

	SetupController instance;

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
	SchemaPage currentPage;
	@Mock
	SchemaPage resultPage;

	@Captor
	ArgumentCaptor<SortedSchema> schemaCaptor;

	String sessionPageId = null;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		instance = new SetupController();
		instance.programCache = programCache;
		instance.schemaService = schemaService;
		instance.iDAO = iDAO;

		when(programCache.isInitialized()).thenReturn(true);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("pageid")).thenReturn(sessionPageId);
		when(session.getAttribute("CurrentSchema")).thenReturn(currentSchema);
		when(session.getAttribute("CurrentConn")).thenReturn(currentConn);
		when(session.getAttribute("CurrentPage")).thenReturn(currentPage);

		when(
				schemaService.prepareSchema(any(SortedSchema.class),
						anyString(), anyString())).thenReturn(resultPage);
	}

	@Test
	public void showFrontPage() throws Exception {
		String expectedView = "setup";
		String dbi = "10";

		when(programCache.getConnection(dbi)).thenReturn(currentConn);
		when(currentSchema.getDbi()).thenReturn(dbi);

		String result = instance.showFrontPage(dbi, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi),
				(String) isNull());

		assertEquals(expectedView, result);
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test
	public void setupInfo() throws Exception {
		String expectedView = "setupInfo";
		String dbi = "10";

		when(programCache.getConnection(dbi)).thenReturn(currentConn);
		when(currentSchema.getDbi()).thenReturn(dbi);

		String result = instance.setupInfo(dbi, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi),
				(String) isNull());

		assertEquals(expectedView, result);
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test
	public void setupPages() throws Exception {
		String expectedView = "setupPages";
		String dbi = "10";

		when(programCache.getConnection(dbi)).thenReturn(currentConn);
		when(currentSchema.getDbi()).thenReturn(dbi);

		String result = instance.setupPages(dbi, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi),
				(String) isNull());

		assertEquals(expectedView, result);
		assertEquals(dbi, resultSchema.getDbi());
	}
}
