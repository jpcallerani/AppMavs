package br.com.mavs.View;

import br.com.mavs.Controller.ControlLineup;
import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Spresenca;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "beanLineup")
@ViewScoped
public class BeanLineup {

    private Usuario _user_facebook = null;
    private List<SloginUsers> _jogadores;
    private SloginUsers _wr1;
    private SloginUsers _wr2;
    private SloginUsers _lta;
    private SloginUsers _lg;
    private SloginUsers _c;
    private SloginUsers _rg;
    private SloginUsers _rt;
    private SloginUsers _te;
    private SloginUsers _rb;
    private SloginUsers _fb;
    private SloginUsers _qb;

    public BeanLineup() throws IOException {

        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");

        if (this._user_facebook == null) {
            try {
                ProxyAuthenticator.logoff();
            } catch (IOException ex) {
                Logger.getLogger(BeanMensalidadeRelatJog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this._jogadores = new ControlUser().findAllUsers(this._user_facebook.getsSegmento());
        for (SloginUsers sloginUsers : _jogadores) {
            sloginUsers.setPresenca(new Spresenca(sloginUsers.getSjogadorTreinoList()));
        }

        this._wr1 = new ControlLineup().retornaMelhorColocado(_jogadores, "WR");
        this._jogadores.remove(this._wr1);
        this._wr2 = new ControlLineup().retornaMelhorColocado(_jogadores, "WR");
        this._jogadores.remove(this._wr2);
        this._lta = new ControlLineup().retornaMelhorColocado(_jogadores, "LT");
        this._jogadores.remove(this._lta);
        this._lg = new ControlLineup().retornaMelhorColocado(_jogadores, "LG");
        this._jogadores.remove(this._lg);
        this._c = new ControlLineup().retornaMelhorColocado(_jogadores, "C");
        this._jogadores.remove(this._c);
        this._rg = new ControlLineup().retornaMelhorColocado(_jogadores, "RG");
        this._jogadores.remove(this._rg);
        this._rt = new ControlLineup().retornaMelhorColocado(_jogadores, "RT");
        this._jogadores.remove(this._rt);
        this._qb = new ControlLineup().retornaMelhorColocado(_jogadores, "QB");
        this._jogadores.remove(this._qb);
        this._te = new ControlLineup().retornaMelhorColocado(_jogadores, "TE");
        this._jogadores.remove(this._te);
        this._rb = new ControlLineup().retornaMelhorColocado(_jogadores, "RB");
        this._jogadores.remove(this._rb);
        this._fb = new ControlLineup().retornaMelhorColocado(_jogadores, "FB");
        this._jogadores.remove(this._fb);
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

    public SloginUsers getWr1() {
        return _wr1;
    }

    public void setWr1(SloginUsers _wr1) {
        this._wr1 = _wr1;
    }

    public SloginUsers getWr2() {
        return _wr2;
    }

    public void setWr2(SloginUsers _wr2) {
        this._wr2 = _wr2;
    }

    public SloginUsers getLta() {
        return _lta;
    }

    public void setLta(SloginUsers _lt) {
        this._lta = _lt;
    }

    public SloginUsers getLg() {
        return _lg;
    }

    public void setLg(SloginUsers _lg) {
        this._lg = _lg;
    }

    public SloginUsers getC() {
        return _c;
    }

    public void setC(SloginUsers _c) {
        this._c = _c;
    }

    public SloginUsers getRg() {
        return _rg;
    }

    public void setRg(SloginUsers _rg) {
        this._rg = _rg;
    }

    public SloginUsers getRt() {
        return _rt;
    }

    public void setRt(SloginUsers _rt) {
        this._rt = _rt;
    }

    public SloginUsers getTe() {
        return _te;
    }

    public void setTe(SloginUsers _te) {
        this._te = _te;
    }

    public SloginUsers getRb() {
        return _rb;
    }

    public void setRb(SloginUsers _rb) {
        this._rb = _rb;
    }

    public SloginUsers getFb() {
        return _fb;
    }

    public void setFb(SloginUsers _fb) {
        this._fb = _fb;
    }

    public SloginUsers getQb() {
        return _qb;
    }

    public void setQb(SloginUsers _qb) {
        this._qb = _qb;
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
