package com.nisum.ui;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.nisum.model.Supplier;
import com.vaadin.data.Item;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

@SuppressWarnings({"deprecation","rawtypes"})
public class SupplierEditor extends Window implements ClickListener, FormFieldFactory {

	private static final long serialVersionUID = 1L;

    private final Item supplierItem;
    private Form editorForm;
    private Button saveButton;
    private Button cancelButton;
    
	public SupplierEditor(Item supplierItem) {
    	
        this.supplierItem = supplierItem;
        
        editorForm = new Form();
        editorForm.setFormFieldFactory(this);
        editorForm.setBuffered(true);
        editorForm.setImmediate(true);
        editorForm.setItemDataSource(supplierItem, Arrays.asList("name",
                "email", "phone"));

        saveButton = new Button("Save", this);
        cancelButton = new Button("Cancel", this);

        editorForm.getFooter().addComponent(saveButton);
        editorForm.getFooter().addComponent(cancelButton);
        setSizeUndefined();
        setContent(editorForm);
        setCaption(buildCaption());
    }
    
    private String buildCaption() {
        return String.format("%s %s", supplierItem.getItemProperty("name")
                .getValue(), supplierItem.getItemProperty("email").getValue());
    }
    

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		
        Field field = DefaultFieldFactory.get().createField(item, propertyId, uiContext);
        
        if (field instanceof TextField) {
            ((TextField) field).setNullRepresentation("");
        }

        field.addValidator(new BeanValidator(Supplier.class, propertyId.toString()));

        return field;
        
    }

	@Override
	public void buttonClick(ClickEvent event) {
	
        if (event.getButton() == saveButton) {
            editorForm.commit();
            fireEvent(new EditorSavedEvent(this, supplierItem));
        } else if (event.getButton() == cancelButton) {
            editorForm.discard();
        }
        close();
        
    }

	public void addListener(EditorSavedListener listener) {
		try {
			Method method = EditorSavedListener.class.getDeclaredMethod(
					"editorSaved", new Class[] { EditorSavedEvent.class });
			addListener(EditorSavedEvent.class, listener, method);
		} catch (final java.lang.NoSuchMethodException e) {
			// This should never happen
			throw new java.lang.RuntimeException(
					"Internal error, editor saved method not found");
		}
	}

	public void removeListener(EditorSavedListener listener) {
		removeListener(EditorSavedEvent.class, listener);
	}

    public static class EditorSavedEvent extends Component.Event {

		private static final long serialVersionUID = 1L;
		
		private Item savedItem;

        public EditorSavedEvent(Component source, Item savedItem) {
            super(source);
            this.savedItem = savedItem;
        }

        public Item getSavedItem() {
            return savedItem;
        }
    }

    public interface EditorSavedListener extends Serializable {
        public void editorSaved(EditorSavedEvent event);
    }
    
}
