package br.com.mavs.View;

import br.com.mavs.Controller.ControlNotificacao;
import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Snotificacao;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "beanNotificacao")
@RequestScoped
public class BeanNotificacao implements Serializable {

    private SloginUsers _user;
    private String _erro;
    private Usuario _user_facebook = null;
    private Snotificacao _notificacao;
    private List<SloginUsers> _users;

    public BeanNotificacao() throws IOException {
        this._erro = "";
        this._user = new SloginUsers();
        this._notificacao = new Snotificacao();
        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");

        if (this._user_facebook == null) {
            this.logoff();
        }

        this._users = new ControlUser().findAllUsers(this._user_facebook.getsSegmento());

    }

    public void insereNotificacao() {
        FacesMessage msg = null;

        try {
            
            this._notificacao.setIdUsuario(_user);
            this._notificacao.setData(new Date());
            this._notificacao.setCriador(this._user_facebook.getName());
            this._notificacao.setLida("N");
            
            this._erro = new ControlNotificacao().criaNotificacao(_notificacao);

            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }
        this._user = new SloginUsers();
        this._notificacao = new Snotificacao();
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void reset() {
        this._user = new SloginUsers();
        this._notificacao = new Snotificacao();
    }

    /**
     *
     * @throws IOException
     */
    public void logoff() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        fc.getExternalContext().redirect("index.xhtml?faces-redirect=true");
    }

    public SloginUsers getUser() {
        return _user;
    }

    public void setUser(SloginUsers _user) {
        this._user = _user;
    }

    public Snotificacao getNotificacao() {
        return _notificacao;
    }

    public void setNotificacao(Snotificacao _notificacao) {
        this._notificacao = _notificacao;
    }

    public List<SloginUsers> getUsers() {
        return _users;
    }

    public void setUsers(List<SloginUsers> _users) {
        this._users = _users;
    }
}
