package br.com.mavs.View;

import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Spresenca;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "beanConChamadaTreino")
@ViewScoped
public class BeanChamadaTreinoRelat {

    private List<SloginUsers> _jogadores;
    private Usuario _user_facebook = null;

    public BeanChamadaTreinoRelat() throws IOException {

        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");

        if (this._user_facebook == null) {
            this.logoff();
        }

        this._jogadores = new ControlUser().findAllUsers(this._user_facebook.getsSegmento());
        for (SloginUsers sloginUsers : _jogadores) {
            sloginUsers.setPresenca(new Spresenca(sloginUsers.getSjogadorTreinoList()));
        }
    }

    /**
     *
     * @param p_number
     * @return
     */
    public String formatNumber(double p_number) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(p_number);
    }

    public List<SloginUsers> getJogadores() {
        return _jogadores;
    }

    public void setJogadores(List<SloginUsers> _jogadores) {
        this._jogadores = _jogadores;
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
