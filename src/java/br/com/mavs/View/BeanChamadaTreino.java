package br.com.mavs.View;

import br.com.mavs.Controller.ControlTreino;
import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.SjogadorTreino;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Streino;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;

@ManagedBean(name = "chamadaTreinoBean")
@ViewScoped
public class BeanChamadaTreino implements Serializable {

    private List<Streino> _treinos;
    private List<SloginUsers> jogadores_source;
    private List<SloginUsers> jogadores_target;
    private Streino _treino;
    private DualListModel<SloginUsers> _jogadores;
    private boolean _habilita = true;
    private String _erro = "";
    private Usuario _user_facebook = null;

    public BeanChamadaTreino() throws IOException {
        this._treino = new Streino();

        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");

        if (this._user_facebook == null) {
            try {
                ProxyAuthenticator.logoff();
            } catch (IOException ex) {
                Logger.getLogger(BeanMensalidadeRelatJog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @PostConstruct
    public void init() {
        this._treinos = new ControlTreino().findTreinoPassado(this._user_facebook.getsSegmento());
        jogadores_source = new ControlUser().findAllUsers(this._user_facebook.getsSegmento());
        jogadores_target = new ArrayList<>();
        this._jogadores = new DualListModel<>(jogadores_source, jogadores_target);
    }

    /**
     * Adiciona a chamada na tabela.
     */
    public void addChamada() {
        FacesMessage msg = null;
        List<String> erros = null;

        try {
            erros = new ArrayList<>();

            if (this._jogadores.getTarget().isEmpty()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Escolha os jogadores presentes!");
            } else {
                //
                // Insert dos jogadores presentes ao treino.
                //
                for (SloginUsers sloginUsers : this._jogadores.getTarget()) {
                    SjogadorTreino jogador_treino = new SjogadorTreino();
                    jogador_treino.setIdJogador(sloginUsers);
                    jogador_treino.setIdTreino(_treino);
                    jogador_treino.setPresente("S");

                    this._erro = new ControlTreino().addChamadaTreino(jogador_treino);

                    if (!this._erro.equals("")) {
                        erros.add(_erro);
                        this._erro = "";
                    }
                }

                //
                // Insert dos jogadores que faltaram no treino.
                //                
                for (SloginUsers sloginUsers : this._jogadores.getSource()) {
                    SjogadorTreino jogador_treino = new SjogadorTreino();
                    jogador_treino.setIdJogador(sloginUsers);
                    jogador_treino.setIdTreino(_treino);
                    jogador_treino.setPresente("N");

                    this._erro = new ControlTreino().addChamadaTreino(jogador_treino);

                    if (!this._erro.equals("")) {
                        erros.add(_erro);
                        this._erro = "";
                    }
                }

                if (erros.isEmpty()) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
                    this._treinos.remove(_treino);
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
        this._jogadores = new DualListModel<>(jogadores_source, jogadores_target);
    }

    /**
     * Retorna a data como String
     *
     * @param data
     * @return
     */
    public String formatDate(Date data) {
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    public void habilitaJogadores() {
        this._habilita = false;
    }

    public boolean isHabilita() {
        return _habilita;
    }

    public void setHabilita(boolean _habilita) {
        this._habilita = _habilita;
    }

    public Streino getTreino() {
        return _treino;
    }

    public void setTreino(Streino _treino) {
        this._treino = _treino;
    }

    public List<Streino> getTreinos() {
        return _treinos;
    }

    public void setTreinos(List<Streino> _treinos) {
        this._treinos = _treinos;
    }

    public DualListModel<SloginUsers> getJogadores() {
        return _jogadores;
    }

    public void setJogadores(DualListModel<SloginUsers> _jogadores) {
        this._jogadores = _jogadores;
    }

}
