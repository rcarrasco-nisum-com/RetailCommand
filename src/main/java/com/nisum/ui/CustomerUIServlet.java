package com.nisum.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(urlPatterns = {"/ui/*", "/VAADIN/*"}, asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = CustomerUI.class)
public class CustomerUIServlet extends VaadinServlet {
	
	private static final long serialVersionUID = 1L;
	
}
