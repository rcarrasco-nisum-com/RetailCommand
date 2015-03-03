package com.nisum.ui;

import java.util.List;

import com.nisum.model.Customer;
import com.nisum.service.CustomerService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Title("CustomerUI")
@Theme("valo")
public class CustomerUI extends UI {
	
	private static final long serialVersionUID = 1L;

	/* User interface components are stored in session. */
	private Table customerList = new Table();
	private TextField searchField = new TextField();
	private Button addNewCustomerButton = new Button("New");
	private Button removeCustomerButton = new Button("Delete");
	private Button saveCustomerButton = new Button("Save");
	private FormLayout editorLayout = new FormLayout();
	private FieldGroup editorFields = new FieldGroup();
	
	/* customer data model */
	private static final String NAME = "Name";
	private static final String EMAIL = "Email";
	private static final String PHONE = "Phone";
	
	private static final String[] fieldNames = new String[] { NAME, EMAIL, PHONE };
	
	/*
	 * Any component can be bound to an external data source. This example uses
	 * just a dummy in-memory list, but there are many more practical
	 * implementations.
	 */
	private IndexedContainer customerContainer;
	
	// private CustomerDao dao;
	private CustomerService service;
	
	@Override
	protected void init(VaadinRequest request) {
		
		SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
		
		// dao = (CustomerDao) helper.getBean("custDao");
		service = (CustomerService) helper.getBean("custService");
		
		customerContainer = createDummyDatasource();

		initLayout();
		initContactList();
		initEditor();
		initSearch();
		initAddRemoveSaveButtons();
		
	}

	private void initLayout() {

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		setContent(splitPanel);

		// HorizontalLayout menuLayout = new HorizontalLayout();
		// menuLayout.addComponent(new Label("Menú"));
		// splitPanel.addComponent(menuLayout);
		
		VerticalLayout leftLayout = new VerticalLayout();
		splitPanel.addComponent(leftLayout);
		
		splitPanel.addComponent(editorLayout);
		leftLayout.addComponent(customerList);
		
		HorizontalLayout bottomLeftLayout = new HorizontalLayout();
		leftLayout.addComponent(bottomLeftLayout);
		
		bottomLeftLayout.addComponent(searchField);
		bottomLeftLayout.addComponent(addNewCustomerButton);

		leftLayout.setSizeFull();

		leftLayout.setExpandRatio(customerList, 1);
		customerList.setSizeFull();

		// menuLayout.setWidth("100%");
		bottomLeftLayout.setWidth("100%");
		searchField.setWidth("100%");
		bottomLeftLayout.setExpandRatio(searchField, 1);

		// menuLayout.setMargin(true);
		editorLayout.setMargin(true);
		editorLayout.setVisible(false);
		
	}

	private void initContactList() {
		
		customerList.setContainerDataSource(customerContainer);
		
		customerList.setVisibleColumns((Object[]) new String[] { NAME, EMAIL, PHONE });
		customerList.setSelectable(true);
		customerList.setImmediate(true);

		customerList.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			public void valueChange(ValueChangeEvent event) {
				Object customer = customerList.getValue();
				if (customer != null) {
					editorFields.setItemDataSource(customerList.getItem(customer));
				}
				editorLayout.setVisible(customer != null);
			}
		});
	}
	
	/*
	 * Generate some in-memory example data to play with. In a real application
	 * we could be using SQLContainer, JPAContainer or some other to persist the
	 * data.
	 */
	private IndexedContainer createDummyDatasource() {
		
		IndexedContainer ic = new IndexedContainer();

		for (String p : fieldNames) {
			ic.addContainerProperty(p, String.class, "");
		}

		// List<Customer> customers = dao.getAll();
		List<Customer> customers = service.getAll();
		
		for (Customer customer : customers) {
			Object id = ic.addItem();
			ic.getContainerProperty(id, NAME).setValue(customer.getName());
			ic.getContainerProperty(id, EMAIL).setValue(customer.getEmail());
			ic.getContainerProperty(id, PHONE).setValue(customer.getPhone());
		}
		
		return ic;
		
	}

	private void initEditor() {

		editorLayout.addComponent(new Label("Customer"));
		
		for (String fieldName : fieldNames) {
			TextField field = new TextField(fieldName);
			editorLayout.addComponent(field);
			field.setWidth("100%");
			editorFields.bind(field, fieldName);
		}

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		
		buttonsLayout.addComponent(saveCustomerButton);
		buttonsLayout.addComponent(removeCustomerButton);
		buttonsLayout.setWidth("100%");
		
		editorLayout.addComponent(buttonsLayout);
		
		/*
		 * Data can be buffered in the user interface. When doing so, commit()
		 * writes the changes to the data source. Here we choose to write the
		 * changes automatically without calling commit().
		 */
		editorFields.setBuffered(false);
		
	}

	private void initSearch() {

		searchField.setInputPrompt("Search customers");

		searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);
		// You may choose to use separate controllers (in MVC) or presenters (in MVP) instead. 
		searchField.addTextChangeListener(new TextChangeListener() {
			private static final long serialVersionUID = 1L;
			public void textChange(final TextChangeEvent event) {
				customerContainer.removeAllContainerFilters();
				customerContainer.addContainerFilter(new CustomerFilter(event.getText()));
			}
		});
		
	}
	
	private void initAddRemoveSaveButtons() {
		
		// add new customer
		addNewCustomerButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				customerContainer.removeAllContainerFilters();
				Object contactId = customerContainer.addItemAt(0);
				customerList.getContainerProperty(contactId, NAME).setValue("New Name");
				customerList.getContainerProperty(contactId, EMAIL).setValue("New Email");
				customerList.getContainerProperty(contactId, PHONE).setValue("New Phone");
				customerList.select(contactId);
			}
		});

		// remove customer
		removeCustomerButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				Object customerId = customerList.getValue();
				Item item = customerList.getItem(customerId);
				if (item != null) {
					String name = item.getItemProperty(NAME).getValue().toString();
					Customer dbCustomer = service.get(name);
					service.delete(dbCustomer);
				}
				customerList.removeItem(customerId);
			}
		});
		
		// save customer
		saveCustomerButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				Object customerId = customerList.getValue();
				if (customerId != null) {
					Item item = customerList.getItem(customerId);
					if (item != null) {
						String name = item.getItemProperty(NAME).getValue().toString();
						String email = item.getItemProperty(EMAIL).getValue().toString();
						String phone = item.getItemProperty(PHONE).getValue().toString();
						System.out.printf(">> SAVE >> name: %s email: %s phone: %s", name, email, phone);
						Customer dbCustomer = service.get(name);
						if (dbCustomer != null) {
							dbCustomer.setEmail(email);
							dbCustomer.setPhone(phone);
							service.update(dbCustomer);
						}else {
							Customer uiCustomer = new Customer(name, email, phone);
							service.save(uiCustomer);
						}
					}
				}
			}
		});
		
	}
	
	/*
	 * A custom filter for searching names and companies in the
	 * contactContainer.
	 */
	private class CustomerFilter implements Filter {
		private static final long serialVersionUID = 1L;
		private String needle;
		public CustomerFilter(String needle) {
			this.needle = needle.toLowerCase();
		}
		public boolean passesFilter(Object itemId, Item item) {
			String haystack = ("" + item.getItemProperty(NAME).getValue()
					+ item.getItemProperty(EMAIL).getValue() + item
					.getItemProperty(PHONE).getValue()).toLowerCase();
			return haystack.contains(needle);
		}
		public boolean appliesToProperty(Object id) {
			return true;
		}
	}
}
