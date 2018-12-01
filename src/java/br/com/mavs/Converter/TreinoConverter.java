package br.com.mavs.Converter;

import br.com.mavs.Modal.Streino;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "treinoConverter")
public class TreinoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Streino) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Streino) {
            Streino sTreino = (Streino) value;
            if (sTreino != null && sTreino instanceof Streino && sTreino.getId() != null) {
                uiComponent.getAttributes().put(String.valueOf(sTreino.getId()), sTreino);
                return String.valueOf(sTreino.getId());
            }
        }
        return "";
    }
}
