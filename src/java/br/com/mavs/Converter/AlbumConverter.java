package br.com.mavs.Converter;

import com.restfb.types.Album;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "albumConverter")
public class AlbumConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Album) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Album) {
            Album album = (Album) value;
            if (album != null && album instanceof Album && album.getId() != null) {
                uiComponent.getAttributes().put(String.valueOf(album.getId()), album);
                return String.valueOf(album.getId());
            }
        }
        return "";
    }
}
