/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavs.Converter;

import br.com.mavs.Modal.Sstatus;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "statusConverter")
public class StatusConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Sstatus) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Sstatus) {
            Sstatus sStatus = (Sstatus) value;
            if (sStatus != null && sStatus instanceof Sstatus && sStatus.getNome() != null) {
                uiComponent.getAttributes().put(String.valueOf(sStatus.getNome()), sStatus);
                return String.valueOf(sStatus.getNome());
            }
        }
        return "";
    }
}
