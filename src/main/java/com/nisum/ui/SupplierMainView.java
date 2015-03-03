package com.nisum.ui;

import com.nisum.model.Supplier;
import com.nisum.ui.SupplierEditor.EditorSavedEvent;
import com.nisum.ui.SupplierEditor.EditorSavedListener;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class SupplierMainView extends HorizontalSplitPanel implements ComponentContainer {

	private static final long serialVersionUID = 1L;

	private JPAContainer<Supplier> suppliers;
	
	private Table supplierTable;
	
	private Button newButton;
	private Button editButton;
	private Button deleteButton;
	
	private TextField searchField;
    private String textFilter;
    
	public SupplierMainView() {
		
		suppliers = JPAContainerFactory.make(Supplier.class, SupplierUI.PERSISTENCE_UNIT);
		
		buildListArea();
		buildDetailArea();
		
		setSplitPosition(60);
		
	}

	private void buildDetailArea() {
		
		VerticalLayout layout = new VerticalLayout();
		setSecondComponent(layout);
		
	}
	
	@SuppressWarnings("deprecation")
	private void buildListArea() {
		
		VerticalLayout verticalLayout = new VerticalLayout();
		setFirstComponent(verticalLayout);
	
		supplierTable = new Table(null, suppliers);
		
		supplierTable.setImmediate(true);
		supplierTable.setSelectable(true);
		
		supplierTable.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				boolean enabled = (event.getProperty().getValue() != null);
				editButton.setEnabled(enabled);
				deleteButton.setEnabled(enabled);
			}
		});
		
		supplierTable.setSizeFull();
		
		supplierTable.addListener(new ItemClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					supplierTable.select(event.getItemId());
				}
			}
		});
		
		supplierTable.setVisibleColumns(new Object[] { "name", "email", "phone" });
		
		HorizontalLayout toolbar = new HorizontalLayout();
		
		newButton = new Button("New");
		newButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				final BeanItem<Supplier> newSupplierItem = new BeanItem<Supplier>(new Supplier());
				SupplierEditor supplierEditor = new SupplierEditor(newSupplierItem);
				supplierEditor.addListener(new EditorSavedListener() {
					private static final long serialVersionUID = 1L;
					@Override
					public void editorSaved(EditorSavedEvent event) {
						suppliers.addEntity(newSupplierItem.getBean());
					}
				});
				UI.getCurrent().addWindow(supplierEditor);
			}
		});
		newButton.setEnabled(true);
		
		editButton = new Button("Edit");
		editButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(new SupplierEditor(supplierTable.getItem(supplierTable.getValue())));
			}
		});
		editButton.setEnabled(false);
		
		deleteButton = new Button("Delete");
		deleteButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				suppliers.removeItem(supplierTable.getValue());
			}
		});
		deleteButton.setEnabled(false);
		
		searchField = new TextField();
		searchField.setInputPrompt("Search by name");
		searchField.addTextChangeListener(new TextChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void textChange(TextChangeEvent event) {
				// TODO :: add filter functionality
				textFilter = event.getText();
                updateFilters();
			}
		});
		
		toolbar.addComponent(newButton);
		toolbar.addComponent(editButton);
		toolbar.addComponent(deleteButton);
		toolbar.addComponent(searchField);
		
		toolbar.setWidth("100%");
		toolbar.setExpandRatio(searchField, 1);
		toolbar.setComponentAlignment(searchField, Alignment.TOP_RIGHT);
		
		verticalLayout.addComponent(toolbar);
		verticalLayout.addComponent(supplierTable);
		verticalLayout.setExpandRatio(supplierTable, 1);
		verticalLayout.setSizeFull();
		
	}
	
    private void updateFilters() {
    	
        suppliers.setApplyFiltersImmediately(false);
        suppliers.removeAllContainerFilters();
        
        if (textFilter != null && !textFilter.equals("")) {
            Or or = new Or(new Like("name", textFilter + "%", false),
                    new Like("email", textFilter + "%", false));
            suppliers.addContainerFilter(or);
        }
        
        suppliers.applyFilters();
    }
}
