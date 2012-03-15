package com.dbsvg.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class JsonView {

	@Autowired
	MappingJacksonHttpMessageConverter jsonConverter;

	public ModelAndView Render(Object model, HttpServletResponse response) {

		MediaType jsonMimeType = MediaType.APPLICATION_JSON;

		try {
			jsonConverter.write(model, jsonMimeType,
					new ServletServerHttpResponse(response));
		} catch (HttpMessageNotWritableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
