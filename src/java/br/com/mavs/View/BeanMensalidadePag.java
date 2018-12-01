package br.com.mavs.View;

import br.com.mavs.Controller.ControlMensalidade;
import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.SjogadorMensalidade;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Smensalidade;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@ManagedBean(name = "beanPagMensalidade")
@ViewScoped
public class BeanMensalidadePag implements Serializable {

    private List<SloginUsers> _jogadores;
    private SloginUsers _jogador;
    private Smensalidade _mensalidade;
    private List<Smensalidade> _mensalidade_source;
    private List<Smensalidade> _mensalidade_target;
    private DualListModel<Smensalidade> _mensalidades;
    private String _erro = "";
    private Usuario _user_facebook = null;

    public BeanMensalidadePag() {
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
        _mensalidade_source = new ArrayList<>();
        _mensalidade_target = new ArrayList<>();
        this._mensalidades = new DualListModel<>(_mensalidade_source, _mensalidade_target);
    }

    /**
     * Método para buscar mensalidades pagas e não pagas.
     */
    public void findMensalidadeJogadores() {
        this._mensalidade_source = new ArrayList<>();
        this._mensalidade_target = new ArrayList<>();

        this._mensalidade_source = new ControlMensalidade().findMensalidadeNaoPagas(this._jogador);
        this._mensalidade_target = new ControlMensalidade().findMensalidadePagas(this._jogador);

        this._mensalidades = new DualListModel<>(_mensalidade_source, _mensalidade_target);
    }

    public void alterMensal(TransferEvent event) {
        FacesMessage msg = null;
        List<String> erros;
        try {
            erros = new ArrayList<>();
            List<Smensalidade> mensalidade = (List<Smensalidade>) event.getItems();
            if (event.isAdd()) {
                for (Smensalidade smensalidade : mensalidade) {
                    SjogadorMensalidade jogador_mensalidade = new SjogadorMensalidade();
                    jogador_mensalidade.setIdJogador(this._jogador);
                    jogador_mensalidade.setIdMensalidade(smensalidade);
                    jogador_mensalidade.setPago("S");

                    this._erro = new ControlMensalidade().updateJogadorrMensalidade(jogador_mensalidade);

                    if (!this._erro.equals("")) {
                        erros.add(_erro);
                        this._erro = "";
                    }
                }
                if (erros.isEmpty()) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
                } else {
                    String erro = "";
                    for (String string : erros) {
                        erro += " " + string;
                    }
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", erro);
                }
            } else {
                for (Smensalidade smensalidade : mensalidade) {
                    SjogadorMensalidade jogador_mensalidade = new SjogadorMensalidade();
                    jogador_mensalidade.setIdJogador(this._jogador);
                    jogador_mensalidade.setIdMensalidade(smensalidade);
                    jogador_mensalidade.setPago("N");

                    this._erro = new ControlMensalidade().updateJogadorrMensalidade(jogador_mensalidade);

                    if (!this._erro.equals("")) {
                        erros.add(_erro);
                        this._erro = "";
                    }
                }
                if (erros.isEmpty()) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                } else {
                    String erro = "";
                    for (String string : erros) {
                        erro += " " + string;
                    }
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", erro);
                }
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }

        FacesContext.getCurrentInstance()
                .addMessage(null, msg);
    }

    /**
     *
     * @param valor
     * @return
     */
    public String formatCurrency(double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

    public SloginUsers getJogador() {
        return _jogador;
    }

    public void setJogador(SloginUsers _jogador) {
        this._jogador = _jogador;
    }

    public List<SloginUsers> getJogadores() {
        return _jogadores;
    }

    public void setJogadores(List<SloginUsers> _jogadores) {
        this._jogadores = _jogadores;
    }

    public DualListModel<Smensalidade> getMensalidades() {
        return _mensalidades;
    }

    public void setMensalidades(DualListModel<Smensalidade> _mensalidades) {
        this._mensalidades = _mensalidades;
    }

    public Smensalidade getMensalidade() {
        return _mensalidade;
    }

    public void setMensalidade(Smensalidade _mensalidade) {
        this._mensalidade = _mensalidade;
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
