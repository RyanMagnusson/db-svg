package com.dbsvg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MenuSpringController {

	@RequestMapping(value = "/menu")
	public String home() {
		return "menu.jsp";
	}

}
