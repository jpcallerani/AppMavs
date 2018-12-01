package br.com.mavs.Converter;

import br.com.mavs.Modal.Smensalidade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "mensalConverter1")
public class MensalConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Smensalidade) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Smensalidade) {
            Smensalidade sMensalidade = (Smensalidade) value;
            if (sMensalidade != null && sMensalidade instanceof Smensalidade && sMensalidade.getId() != null) {
                uiComponent.getAttributes().put(String.valueOf(sMensalidade.getId()), sMensalidade);
                return String.valueOf(sMensalidade.getId());
            }
        }
        return "";
    }
}
