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
