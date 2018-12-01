package br.com.mavs.View;

import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "viewJogadoresBean")
@ViewScoped
public class BeanJogadores implements Serializable {

    private List<SloginUsers> _jogadores;
    private SloginUsers _jogadores_deletar;
    private List<SloginUsers> _filtroJogadores;
    private SloginUsers _user;
    private String _erro;
    private Usuario _user_facebook = null;

    public BeanJogadores() throws IOException {

        this._erro = "";
        _jogadores_deletar = new SloginUsers();
        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");

        if (this._user_facebook == null) {
            this.logoff();
        }

        this._user = new ControlUser().findUser(_user_facebook);

        this._filtroJogadores = new ArrayList<>();
        this._jogadores = new ControlUser().findAllUsers(this._user_facebook.getsSegmento());

    }

    /**
     *
     */
    public void deletaJogadores() {
        FacesMessage msg;

        try {
            if (this._jogadores_deletar == null) {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "É necessário selecionar um jogador.");
            } else {
                this._erro = new ControlUser().removeUser(this._jogadores_deletar);
                if (this._erro.equals("")) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                    this._jogadores.remove(this._jogadores_deletar);
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Um ou mais registros não foram deletados.");
                }
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }
        this._jogadores_deletar = new SloginUsers();
        FacesContext.getCurrentInstance()
                .addMessage(null, msg);
    }

    public List<SloginUsers> getJogadores() {
        return _jogadores;
    }

    public void setJogadores(List<SloginUsers> _jogadores) {
        this._jogadores = _jogadores;
    }

    public List<SloginUsers> getFiltroJogadores() {
        return _filtroJogadores;
    }

    public void setFiltroJogadores(List<SloginUsers> _filtroJogadores) {
        this._filtroJogadores = _filtroJogadores;
    }

    public SloginUsers getJogadores_deletar() {
        return _jogadores_deletar;
    }

    public void setJogadores_deletar(SloginUsers _jogadores_deletar) {
        this._jogadores_deletar = _jogadores_deletar;
    }

    public SloginUsers getUser() {
        return _user;
    }

    public void setUser(SloginUsers _user) {
        this._user = _user;
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
}
