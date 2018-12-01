package br.com.mavs.View;

import br.com.mavs.Controller.ControlTopJogadores;
import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Spresenca;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "viewTopJogadores")
@RequestScoped
public class BeanTopJogadores {

    private List<SloginUsers> _top_jogadores;
    private Usuario _user_facebook = null;

    public BeanTopJogadores() throws IOException {

        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");

        if (this._user_facebook == null) {
            this.logoff();
        }

        this._top_jogadores = new ControlUser().findAllUsers(this._user_facebook.getsSegmento());
        for (SloginUsers sloginUsers : _top_jogadores) {
            sloginUsers.setPresenca(new Spresenca(sloginUsers.getSjogadorTreinoList()));
        }

        //Sorting
        Collections.sort(this._top_jogadores, new Comparator<SloginUsers>() {
            @Override
            public int compare(SloginUsers c1, SloginUsers c2) {
                return Double.compare(c2.getPresenca().getTreino_presentes_porcentagem(), c1.getPresenca().getTreino_presentes_porcentagem());
            }
        });

    }

    public List<SloginUsers> getTop_jogadores() {
        return _top_jogadores;
    }

    public void setTop_jogadores(List<SloginUsers> _top_jogadores) {
        this._top_jogadores = _top_jogadores;
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
