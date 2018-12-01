package br.com.mavs.View;

import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jopaulo
 */
@ManagedBean(name = "viewUsuario")
@RequestScoped
public class BeanUser implements Serializable {

    private static final long serialVersionUID = -1611162265998907599L;

    public BeanUser() {

    }

//    public static String API_KEY = "690153531004979"; // Produção
    public static String API_KEY = "588204657972342"; // Teste

//    public static String SECRET = "8ed65c5171aa011415c99cb180a6d245"; // Produção
    public static String SECRET = "197949691e874eec77f827e2fb7420ad"; // Teste

    public String getFacebookUrlAuth() {
        String scope = "email,"
                + "public_profile,"
                + "user_birthday,"
                + "user_about_me,"
                + "user_activities,"
                + "user_events,"
                + "user_groups,"
                + "user_photos,"
                + "user_videos,"
                + "user_website,"
                + "user_hometown,"
                + "read_friendlists,";

        ProxyAuthenticator.retornUrlRedirect();

        HttpSession session
                = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String sessionId = session.getId();
        String returnValue = "https://www.facebook.com/dialog/oauth?client_id="
                + API_KEY + "&redirect_uri=" + ProxyAuthenticator.s_url_redirect + "index.sec"
                + "&scope=" + scope + "&state=" + sessionId;
        return returnValue;
    }
}
