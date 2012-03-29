package com.dbsvg.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

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

import com.dbsvg.models.InternalDataDAO;
import com.dbsvg.objects.model.Table;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.SortedSchema;
import com.dbsvg.objects.view.TableView;
import com.dbsvg.services.IDBViewerCache;

public class PageControllerTest {

	private static final String VAL = PageController.VAL;
	private static final String MESSAGE = PageController.MESSAGE;

	PageController instance;

	@Mock
	Model model;

	@Mock
	HttpServletResponse response;
	@Mock
	HttpServletRequest request;
	@Mock
	HttpSession session;

	@Mock
	SortedSchema currentSchema;
	@Mock
	Connection iDAOconn;
	@Mock
	SchemaPage schemaPage;
	@Mock
	Table table;
	@Mock
	TableView tableView;
	@Captor
	ArgumentCaptor<String> stringCaptor;
	@Captor
	ArgumentCaptor<SchemaPage> pageCaptor;

	// Auto injected component mocks
	@Mock
	JsonView jsonView;
	@Mock
	InternalDataDAO iDAO;
	@Mock
	IDBViewerCache programCache;

	int tv_id = 200;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		instance = new PageController();
		instance.jsonView = jsonView;
		instance.iDAO = iDAO;
		instance.programCache = programCache;

		when(iDAO.getConnection()).thenReturn(iDAOconn);

		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("CurrentSchema")).thenReturn(currentSchema);
		when(currentSchema.getNumTables()).thenReturn(10);
		when(schemaPage.makeViewForTable(table)).thenReturn(tableView);
		when(schemaPage.removeViewForTable(table)).thenReturn(tableView);
	}

	@Test
	public void addPage() throws SQLException {

		String dbi = "10";
		String title = "title";
		when(currentSchema.getDbi()).thenReturn(dbi);

		ModelAndView result = instance.addPage(dbi, title, model, response, request);

		verify(iDAO).saveSchemaPage(pageCaptor.capture(), eq(iDAOconn));
		SchemaPage page = pageCaptor.getValue();

		assertNotNull(page);
		assertEquals(title, page.getTitle());
		assertEquals(currentSchema, page.getSchema());

		verify(model).addAttribute(VAL, page.getId());
		assertNull(result);
	}

	@Test
	public void removePage() throws SQLException {

		String dbi = "10";
		String pageName = "4e801310-b40f-42e3-8569-30c25071041d";
		when(currentSchema.getDbi()).thenReturn(dbi);
		when(currentSchema.getPage(pageName)).thenReturn(schemaPage);
		when(schemaPage.getId()).thenReturn(UUID.fromString(pageName));
		when(currentSchema.getNumPages()).thenReturn(2);

		ModelAndView result = instance.removePage(dbi, pageName, model, response, request);

		verify(iDAO).deleteSchemaPage(schemaPage, iDAOconn);
		verify(currentSchema).removePage(UUID.fromString(pageName));

		verify(model).addAttribute(VAL, pageName);
		assertNull(result);
	}

	@Test
	public void removePage_lastpage() throws SQLException {

		String dbi = "10";
		String pageName = "4e801310-b40f-42e3-8569-30c25071041d";
		when(currentSchema.getDbi()).thenReturn(dbi);
		when(currentSchema.getPage(pageName)).thenReturn(schemaPage);
		when(schemaPage.getId()).thenReturn(UUID.fromString(pageName));
		when(currentSchema.getNumPages()).thenReturn(1);

		ModelAndView result = instance.removePage(dbi, pageName, model, response, request);

		verify(iDAO, times(0)).deleteSchemaPage(schemaPage, iDAOconn);
		verify(currentSchema, times(0)).removePage(UUID.fromString(pageName));

		verify(model).addAttribute(VAL, "0");
		verify(model).addAttribute(MESSAGE, "Cannot remove last page");
		assertNull(result);
	}

	@Test
	public void setPageForTable_checked() throws SQLException {

		String dbi = "10";
		String pageName = "4e801310-b40f-42e3-8569-30c25071041d";
		String tableName = "TEST";
		boolean checked = true;

		when(currentSchema.getDbi()).thenReturn(dbi);
		when(currentSchema.getPage(pageName)).thenReturn(schemaPage);
		when(currentSchema.getTable(tableName)).thenReturn(table);
		when(schemaPage.getId()).thenReturn(UUID.fromString(pageName));

		ModelAndView result = instance.setPageForTable(dbi, pageName, tableName, checked, model, response, request);

		verify(currentSchema).getPage(pageName);
		verify(currentSchema).getTable(tableName);
		verify(schemaPage).makeViewForTable(table);
		verify(schemaPage, times(0)).removeViewForTable(table);
		verify(tableView).randomInitialize(10);

		verify(iDAO).saveTablePosition(tableView, iDAOconn);

		verify(model).addAttribute(VAL, UUID.fromString(pageName));
		assertNull(result);

	}

	@Test
	public void setPageForTable_unchecked() throws SQLException {

		String dbi = "10";
		String pageName = "4e801310-b40f-42e3-8569-30c25071041d";
		String tableName = "TEST";
		boolean checked = false;

		when(currentSchema.getDbi()).thenReturn(dbi);
		when(currentSchema.getPage(pageName)).thenReturn(schemaPage);
		when(currentSchema.getTable(tableName)).thenReturn(table);
		when(schemaPage.getId()).thenReturn(UUID.fromString(pageName));

		ModelAndView result = instance.setPageForTable(dbi, pageName, tableName, checked, model, response, request);

		verify(currentSchema).getPage(pageName);
		verify(currentSchema).getTable(tableName);
		verify(schemaPage, times(0)).makeViewForTable(table);
		verify(schemaPage).removeViewForTable(table);

		verify(iDAO).deleteTablePosition(tableView, iDAOconn);

		verify(model).addAttribute(VAL, UUID.fromString(pageName));
		assertNull(result);

	}

	@Test
	public void savePage() throws SQLException {

		String dbi = "10";
		String pageName = "4e801310-b40f-42e3-8569-30c25071041d";
		String title = "TEST";

		when(currentSchema.getDbi()).thenReturn(dbi);
		when(currentSchema.getPage(pageName)).thenReturn(schemaPage);
		when(currentSchema.getTable(title)).thenReturn(table);
		when(schemaPage.getId()).thenReturn(UUID.fromString(pageName));

		ModelAndView result = instance.savePage(dbi, pageName, title, model, response, request);

		verify(currentSchema).getPage(pageName);
		verify(schemaPage).setTitle(title);

		verify(iDAO).saveSchemaPage(schemaPage, iDAOconn);

		verify(model).addAttribute(VAL, UUID.fromString(pageName));
		assertNull(result);

	}

	@Test
	public void selectAll() throws SQLException {
		String dbi = "10";
		String pageName = "4e801310-b40f-42e3-8569-30c25071041d";
		String title = "TEST";

		when(currentSchema.getDbi()).thenReturn(dbi);
		when(currentSchema.getPage(pageName)).thenReturn(schemaPage);
		when(currentSchema.getTable(title)).thenReturn(table);
		when(schemaPage.getId()).thenReturn(UUID.fromString(pageName));

		ModelAndView result = instance.selectAll(dbi, pageName, model, response, request);

		verify(iDAO).addViewsForAllTables(schemaPage, iDAOconn);
		assertNull(result);
	}

	@Test
	public void deselectAll() throws SQLException {
		String dbi = "10";
		String pageName = "4e801310-b40f-42e3-8569-30c25071041d";
		String title = "TEST";

		when(currentSchema.getDbi()).thenReturn(dbi);
		when(currentSchema.getPage(pageName)).thenReturn(schemaPage);
		when(currentSchema.getTable(title)).thenReturn(table);
		when(schemaPage.getId()).thenReturn(UUID.fromString(pageName));

		ModelAndView result = instance.deselectAll(dbi, pageName, model, response, request);

		verify(iDAO).removeViewsForAllTables(schemaPage, iDAOconn);
		assertNull(result);

	}
}
