package br.com.mavs.Converter;

import br.com.mavs.Modal.SloginUsers;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "userConverter")
public class UserConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (SloginUsers) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof SloginUsers) {
            SloginUsers sLoginUser = (SloginUsers) value;
            if (sLoginUser != null && sLoginUser instanceof SloginUsers && sLoginUser.getId() != null) {
                uiComponent.getAttributes().put(String.valueOf(sLoginUser.getId()), sLoginUser);
                return String.valueOf(sLoginUser.getId());
            }
        }
        return "";
    }
}
