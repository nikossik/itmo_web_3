package web.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("navigationBean")
@RequestScoped
public class NavigationBean {

    public String toMainPage() {
        return "main?faces-redirect=true";
    }

    public String toIndexPage() {
        return "index?faces-redirect=true";
    }
}