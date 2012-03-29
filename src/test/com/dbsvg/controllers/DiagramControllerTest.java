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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.models.InternalDataDAO;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.SortedSchema;
import com.dbsvg.services.IDBViewerCache;
import com.dbsvg.services.SchemaService;

public class DiagramControllerTest {

	public static final String MESSAGE = DiagramController.MESSAGE;

	DiagramController instance;

	@Mock
	HttpServletRequest request;
	@Mock
	HttpSession session;
	@Mock
	Model model;
	@Mock
	HttpServletResponse response;

	// Auto injected component mocks
	@Mock
	JsonView jsonView;
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
	SchemaPage currentPage;

	@Captor
	ArgumentCaptor<SortedSchema> schemaCaptor;

	String sessionPageId = null;
	String dbi = "10";

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		instance = new DiagramController();
		instance.programCache = programCache;
		instance.schemaService = schemaService;
		instance.jsonView = jsonView;
		instance.iDAO = iDAO;

		when(programCache.isInitialized()).thenReturn(true);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("pageid")).thenReturn(sessionPageId);
		when(session.getAttribute("CurrentSchema")).thenReturn(currentSchema);
		when(session.getAttribute("CurrentConn")).thenReturn(currentConn);
		when(session.getAttribute("CurrentPage")).thenReturn(currentPage);

		when(programCache.getConnection(dbi)).thenReturn(currentConn);
		when(currentSchema.getDbi()).thenReturn(dbi);

		when(schemaService.prepareSchema(any(SortedSchema.class),
				anyString(), anyString())).thenReturn(currentPage);
	}

	@Test
	public void initialize() throws Exception {
		String expectedView = "diagram";
		String page = null;

		String result = instance.showDiagram(dbi, page, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi),
				(String) isNull());

		assertEquals(expectedView, result);
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test
	public void initializeWithPage() throws Exception {
		String expectedView = "diagram";
		String dbi = "10";
		String page = "20";

		String result = instance.showDiagram(dbi, page, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi), eq(page));

		assertEquals(expectedView, result);
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test
	public void downloadDiagram() throws Exception {
		String expectedView = "schema.svg";
		String dbi = "10";
		String page = "20";

		String result = instance.downloadDiagram(dbi, page, request);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi), eq(page));

		assertEquals(expectedView, result);
		assertEquals(dbi, resultSchema.getDbi());
	}

	@Test
	public void refreshDiagram() throws Exception {
		String dbi = "10";
		String page = "20";

		ModelAndView result = instance.refreshDiagram(dbi, page, request, response, model);

		verify(session).setAttribute(eq("CurrentSchema"),
				schemaCaptor.capture());
		SortedSchema resultSchema = schemaCaptor.getValue();

		verify(schemaService).prepareSchema(eq(resultSchema), eq(dbi), eq(page));
		verify(schemaService).resortTableViews(eq(currentPage));

		assertNull(result);
		assertEquals(dbi, resultSchema.getDbi());

		verify(model).addAttribute(MESSAGE, "View Resorted");
	}

	@Test
	public void setTablePosition() throws Exception {
		int tableid = 10;
		double x_pos = 200;
		double y_pos = 100;
		ModelAndView result = instance.setTablePosition(tableid, x_pos, y_pos, request, response, model);

		verify(currentPage).setTableViewPosition(tableid, x_pos, y_pos);
		verify(model).addAttribute(MESSAGE, "Table position set");
		assertNull(result);
	}

	@Test
	public void saveDiagram() throws Exception {
		String tableid = "10";
		String page = null;
		ModelAndView result = instance.saveDiagram(tableid, page, request, response, model);

		// verify(currentPage).setTableViewPosition(tableid, x_pos, y_pos);
		verify(schemaService).saveTablePositions(currentPage);
		verify(model).addAttribute(MESSAGE, "Table Positions Saved.");
		assertNull(result);
	}

}
