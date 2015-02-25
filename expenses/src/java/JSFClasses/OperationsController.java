package JSFClasses;

import Classes.Operations;
import JSFClasses.util.JsfUtil;
import JSFClasses.util.JsfUtil.PersistAction;
import Beans.OperationsFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "operationsController")
@SessionScoped
public class OperationsController implements Serializable {

    @EJB
    private Beans.OperationsFacade ejbFacade;
    private List<Operations> items = null;
    private Operations selected;
    public String usersVisible;
    public String categoriesVisible;

    public String getCategoriesVisible() {
        FacesContext context = FacesContext.getCurrentInstance();
        String flags =  (String) context.getExternalContext().getSessionMap().get("flag");
              
            switch (flags) {
                case "0":
                    categoriesVisible="categories-hidden";
                    break;
                case "1":
                    categoriesVisible="categories-visible";
                    break;
            }
        return categoriesVisible;
    }

    public String getUsersVisible() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        String flags =  (String) context.getExternalContext().getSessionMap().get("flag");
              
            switch (flags) {
                case "0":
                    usersVisible="users-hidden";
                    break;
                case "1":
                    usersVisible="users-visible";
                    break;
            }
        return usersVisible;
    }

    public OperationsController() {
    }

    public Operations getSelected() {
        return selected;
    }

    public void setSelected(Operations selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OperationsFacade getFacade() {
        return ejbFacade;
    }

    public Operations prepareCreate() {
        selected = new Operations();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("OperationsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void goToUsers() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("../users/List.xhtml");
    } 
    
    public void goToCategories() throws IOException{
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("../categories/List.xhtml");
    } 

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("OperationsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("OperationsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void Logout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect("../Login.xhtml");
    }

    public List<Operations> getItems() {
        if (items == null) {
             FacesContext context = FacesContext.getCurrentInstance();
              String flags =  (String) context.getExternalContext().getSessionMap().get("flag");
              
            switch (flags) {
                case "0":
                    int userId = (int) context.getExternalContext().getSessionMap().get("userId");
                    items = getFacade().findByUser(userId);
                    break;
                case "1":
                    items = getFacade().findAll();
                    break;
            }
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Operations> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Operations> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Operations.class)
    public static class OperationsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OperationsController controller = (OperationsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "operationsController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Operations) {
                Operations o = (Operations) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Operations.class.getName()});
                return null;
            }
        }

    }

}
