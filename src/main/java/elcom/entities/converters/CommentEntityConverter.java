package elcom.entities.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.bean.RequestScoped;
import javax.faces.convert.Converter;
import javax.faces.bean.ManagedBean;
import elcom.ejbs.DataProvider;
import javax.ejb.EJB;

@ManagedBean
@RequestScoped
public class CommentEntityConverter implements Converter {
    @EJB
    DataProvider dp;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return (s != null)? dp.getCommentEntityByContent(s) : null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return (o != null)? o.toString() : null;
    }
}
