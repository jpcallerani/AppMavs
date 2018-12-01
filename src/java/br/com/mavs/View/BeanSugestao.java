package br.com.mavs.View;

import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.EmailException;

@ManagedBean(name = "beanSugestao")
@ViewScoped
public class BeanSugestao implements Serializable{

    private final Usuario _user_facebook;
    private String _mensagem = "";

    public BeanSugestao() {
        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");
    }

    /**
     * Método para envio de email.
     */
    public void sendEmail() {
        FacesMessage msg = null;
        try {
            ProxyAuthenticator.sendEmail(_user_facebook, _mensagem);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Email enviado com sucesso!");
        } catch (EmailException e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }
        this._mensagem = "";
        FacesContext.getCurrentInstance()
                .addMessage(null, msg);
    }

    public String getMensagem() {
        return _mensagem;
    }

    public void setMensagem(String _mensagem) {
        this._mensagem = _mensagem;
    }
}
