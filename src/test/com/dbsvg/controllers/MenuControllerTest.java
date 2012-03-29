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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.models.InternalDataDAO;
import com.dbsvg.services.IDBViewerCache;

public class MenuControllerTest {

	MenuController instance;

	@Mock
	Model model;

	@Mock
	HttpServletResponse response;

	@Captor
	ArgumentCaptor<Map<String, ConnectionWrapper>> connectionsCaptor;

	// Auto injected component mocks
	@Mock
	InternalDataDAO iDAO;
	@Mock
	IDBViewerCache programCache;

	@Mock
	Connection iDAOconn;

	Map<String, ConnectionWrapper> connections = null;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		instance = new MenuController();
		instance.iDAO = iDAO;
		instance.programCache = programCache;

		when(iDAO.getConnection()).thenReturn(iDAOconn);

		connections = new HashMap<String, ConnectionWrapper>();
		connections.put("test", new ConnectionWrapper());
		when(iDAO.readAllConnectionWrappers(iDAOconn)).thenReturn(connections);
	}

	@Test
	public void showMenu() throws Exception {
		String expected = "menu";

		String result = instance.showMenu();

		verify(iDAO).setUpInternalDB(iDAOconn);
		verify(iDAO).readAllConnectionWrappers(iDAOconn);
		verify(programCache).setAllConnections(connectionsCaptor.capture());

		assertEquals(expected, result);
		assertEquals(connections, connectionsCaptor.getValue());
	}

}
