package br.com.mavs.View;

import br.com.mavs.Controller.ControlMensalidade;
import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Smensalidade;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "beanMensalidadeRelatJog")
@ViewScoped
public class BeanMensalidadeRelatJog implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Smensalidade> _mensalidades;
    private List<SloginUsers> _jogadores;
    private String _filtro;
    private SloginUsers _jogador;
    private Usuario _user_facebook = null;

    public BeanMensalidadeRelatJog() {

        this._mensalidades = new ArrayList<>();
        this._jogador = new SloginUsers();

    }

    /**
     *
     * @throws IOException
     */
    @PostConstruct
    public void init() {

        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");

        if (this._user_facebook == null) {
            try {
                ProxyAuthenticator.logoff();
            } catch (IOException ex) {
                Logger.getLogger(BeanMensalidadeRelatJog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this._jogadores = new ControlUser().findAllUsers(this._user_facebook.getsSegmento());
    }

    /**
     *
     * @param data
     * @return
     */
    public String formatDate(Date data) {
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    /**
     *
     */
    public void findMensalidadeJogador() {
        this._mensalidades = new ControlMensalidade().findMensalNaoPagasVencidas(_jogador);
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

    public List<Smensalidade> getMensalidades() {
        return _mensalidades;
    }

    public void setMensalidades(List<Smensalidade> _mensalidades) {
        this._mensalidades = _mensalidades;
    }

    public String getFiltro() {
        return _filtro;
    }

    public void setFiltro(String _filtro) {
        this._filtro = _filtro;
    }

    public SloginUsers getJogador() {
        return _jogador;
    }

    public void setJogador(SloginUsers _jogador) {
        this._jogador = _jogador;
    }
}
