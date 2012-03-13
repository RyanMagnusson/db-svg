package com.dbsvg.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application menu.
 */
@Controller
public class MenuSpringController {

	@RequestMapping(value = "/")
	public String menu() {
		return "home.jsp";
	}

}
