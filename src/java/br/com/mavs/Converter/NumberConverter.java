/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavs.Converter;

import br.com.mavs.Modal.Snumber;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "numberConverter")
public class NumberConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Snumber) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Snumber) {
            Snumber sNumber = (Snumber) value;
            if (sNumber != null && sNumber instanceof Snumber && sNumber.getNumber() != null) {
                uiComponent.getAttributes().put(String.valueOf(sNumber.getNumber()), sNumber);
                return String.valueOf(sNumber.getNumber());
            }
        }
        return "";
    }
}
