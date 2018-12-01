package br.com.mavs.Converter;

import br.com.mavs.Modal.Sitem;
import br.com.mavs.Modal.Streino;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "itemConverter")
public class ItemConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Sitem) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Sitem) {
            Sitem sItem = (Sitem) value;
            if (sItem != null && sItem instanceof Sitem && sItem.getId() != null) {
                uiComponent.getAttributes().put(String.valueOf(sItem.getId()), sItem);
                return String.valueOf(sItem.getId());
            }
        }
        return "";
    }
}
