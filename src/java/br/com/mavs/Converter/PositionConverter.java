package br.com.mavs.Converter;

import br.com.mavs.Modal.SpositionsOffense;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "positionConverter") 
public class PositionConverter implements Converter {  
  
   @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (SpositionsOffense) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof SpositionsOffense) {
            SpositionsOffense sPosition = (SpositionsOffense) value;
            if (sPosition != null && sPosition instanceof SpositionsOffense && sPosition.getPosition()!= null) {
                uiComponent.getAttributes().put(sPosition.getPosition(), sPosition);
                return sPosition.getPosition();
            }
        }
        return "";
    }
}
