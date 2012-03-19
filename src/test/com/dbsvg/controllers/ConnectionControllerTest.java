package com.dbsvg.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.models.InternalDataDAO;
import com.dbsvg.services.IDBViewerCache;

public class ConnectionControllerTest {

	private static final String VAL = ConnectionController.VAL;

	ConnectionController instance;

	@Mock
	Model model;

	@Mock
	HttpServletResponse response;
	@Mock
	HttpServletRequest request;
	@Mock
	HttpSession session;

	@Captor
	ArgumentCaptor<String> stringCaptor;
	@Captor
	ArgumentCaptor<ConnectionWrapper> cwCaptor;
	@Captor
	ArgumentCaptor<Map<String, ConnectionWrapper>> connectionsCaptor;

	// Auto injected component mocks
	@Mock
	JsonView jsonView;
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
		instance = new ConnectionController();
		instance.jsonView = jsonView;
		instance.iDAO = iDAO;
		instance.programCache = programCache;

		when(iDAO.getConnection()).thenReturn(iDAOconn);

		connections = new HashMap<String, ConnectionWrapper>();
		connections.put("test", new ConnectionWrapper());
		when(iDAO.readAllConnectionWrappers(iDAOconn)).thenReturn(connections);

		when(request.getSession()).thenReturn(session);
	}

	@Test
	public void listConnections() throws Exception {

		List<Map<String, Object>> displayList = new ArrayList<Map<String, Object>>();
		for (ConnectionWrapper cw : connections.values()) {
			Map<String, Object> display = new HashMap<String, Object>();
			display.put("id", cw.getId());
			display.put("title", cw.getTitle());
			displayList.add(display);
		}

		ModelAndView result = instance.listConnections(model, response);

		verify(iDAO).readAllConnectionWrappers(iDAOconn);

		verify(model).addAttribute(eq("connections"), eq(displayList));
		assertNull(result);
	}

	@Test
	public void addConnection() {

		final int expectedId = 10;

		String title = "title";
		String url = "url";
		String driver = "driver";
		String username = "username";
		String password = "password";

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				ConnectionWrapper cw = (ConnectionWrapper) args[0];
				cw.setId(expectedId);
				return "called with arguments: " + args;
			}
		}).when(iDAO).saveConnectionWrapperNewID(any(ConnectionWrapper.class),
				any(Connection.class));

		ModelAndView result = instance.addConnection(title, url, driver,
				username, password, model, response);

		verify(iDAO).saveConnectionWrapperNewID(cwCaptor.capture(),
				eq(iDAOconn));

		ConnectionWrapper cw = cwCaptor.getValue();
		assertNotNull(cw);
		assertEquals(expectedId, cw.getId());
		assertEquals(title, cw.getTitle());
		assertEquals(url, cw.getUrl());
		assertEquals(driver, cw.getDriver());
		assertEquals(username, cw.getUsername());
		assertEquals(password, cw.getPassword());

		verify(model).addAttribute(VAL, cw.getId());
		assertNull(result);
	}

	@Test
	public void saveConnection() {

		final int expectedId = 10;

		String dbi = "10";
		String title = "title";
		String url = "url";
		String driver = "driver";
		String username = "username";
		String password = "password";

		ModelAndView result = instance.saveConnection(dbi, title, url, driver,
				username, password, model, response, request);

		verify(iDAO).saveConnectionWrapper(cwCaptor.capture(), eq(iDAOconn));

		ConnectionWrapper cw = cwCaptor.getValue();
		assertNotNull(cw);
		assertEquals(expectedId, cw.getId());
		assertEquals(title, cw.getTitle());
		assertEquals(url, cw.getUrl());
		assertEquals(driver, cw.getDriver());
		assertEquals(username, cw.getUsername());
		assertEquals(password, cw.getPassword());

		verify(model).addAttribute(VAL, cw.getId());
		verify(session).setAttribute(eq("CurrentConn"), eq(cw));
		assertNull(result);
	}

	@Test
	public void addConnectionInvalidTitle() {

		String title = "";
		String url = "url";
		String driver = "driver";
		String username = "username";
		String password = "password";

		ModelAndView result = instance.addConnection(title, url, driver,
				username, password, model, response);

		verify(model).addAttribute(VAL, "0");

		assertNull(result);
	}

	@Test
	public void addConnectionInvalidUrl() {

		String title = "title";
		String url = "";
		String driver = "driver";
		String username = "username";
		String password = "password";

		ModelAndView result = instance.addConnection(title, url, driver,
				username, password, model, response);

		verify(model).addAttribute(VAL, "0");

		assertNull(result);
	}

	@Test
	public void addConnectionInvalidDriver() {

		String title = "title";
		String url = "url";
		String driver = "";
		String username = "username";
		String password = "password";

		ModelAndView result = instance.addConnection(title, url, driver,
				username, password, model, response);

		verify(model).addAttribute(VAL, "0");

		assertNull(result);
	}

	@Test
	public void addConnectionInvalidUsername() {

		String title = "title";
		String url = "url";
		String driver = "driver";
		String username = "";
		String password = "password";

		ModelAndView result = instance.addConnection(title, url, driver,
				username, password, model, response);

		verify(model).addAttribute(VAL, "0");

		assertNull(result);
	}

	@Test
	public void addConnectionInvalidPassword() {

		String title = "title";
		String url = "url";
		String driver = "driver";
		String username = "username";
		String password = "";

		ModelAndView result = instance.addConnection(title, url, driver,
				username, password, model, response);

		verify(model).addAttribute(VAL, "0");

		assertNull(result);
	}

	@Test
	public void removeConnection() {
		String id = "10";

		ModelAndView result = instance.removeConnection(id, model, response);

		verify(iDAO).deleteConnectionWrapper(id, iDAOconn);

		verify(iDAO).readAllConnectionWrappers(iDAOconn);
		verify(programCache).setAllConnections(connectionsCaptor.capture());

		assertEquals(connections, connectionsCaptor.getValue());

		verify(model).addAttribute(VAL, id);
		assertNull(result);
	}

	@Test
	public void removeConnectionInvalidId() {
		String id = "";

		ModelAndView result = instance.removeConnection(id, model, response);

		verify(iDAO, times(0)).deleteConnectionWrapper(id, iDAOconn);
		verify(iDAO, times(0)).readAllConnectionWrappers(iDAOconn);

		verify(model).addAttribute(VAL, "0");
		assertNull(result);
	}

}
