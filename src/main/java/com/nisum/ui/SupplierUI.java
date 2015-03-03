package com.nisum.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

public class SupplierUI extends UI {

	private static final long serialVersionUID = 1L;

	public static final String PERSISTENCE_UNIT = "supplier";

	@Override
	protected void init(VaadinRequest request) {
		setContent(new SupplierMainView());
	}

	@WebServlet(urlPatterns = {"/ui/supplier/*"}, asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = SupplierUI.class)
	public static class SupplierUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
	}
	
}
