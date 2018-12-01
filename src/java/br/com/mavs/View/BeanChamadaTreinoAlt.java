package br.com.mavs.View;

import br.com.mavs.Controller.ControlTreino;
import br.com.mavs.Modal.SjogadorTreino;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Streino;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@ManagedBean(name = "altChamadaTreinoBean")
@ViewScoped
public class BeanChamadaTreinoAlt {

    private List<Streino> _treinos;
    private List<SjogadorTreino> _jogador_treinos;
    private List<SloginUsers> _jogadores_source;
    private List<SloginUsers> _jogadores_target;
    private Streino _treino;
    private DualListModel<SloginUsers> _jogadores;
    private String _erro = "";
    private Usuario _user_facebook = null;

    public BeanChamadaTreinoAlt() {
        this._treino = new Streino();
    }

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

        this._treinos = new ControlTreino().findTreinoPresença(this._user_facebook.getsSegmento()); 
        _jogadores_source = new ArrayList<>();
        _jogadores_target = new ArrayList<>();
        this._jogadores = new DualListModel<>(_jogadores_source, _jogadores_target);
    }

    /**
     * Método para buscar os jogadores de acordo com o dia do treino.
     */
    public void findJogadoresTreino() {
        this._jogadores_source = new ArrayList<>();
        this._jogadores_target = new ArrayList<>();
        this._jogador_treinos = new ControlTreino().findJogadorTreino(_treino);

        for (SjogadorTreino sjogadorTreino : this._jogador_treinos) {
            if (sjogadorTreino.getPresente().equalsIgnoreCase("S")) {
                this._jogadores_target.add(sjogadorTreino.getIdJogador());
            } else {
                this._jogadores_source.add(sjogadorTreino.getIdJogador());
            }
        }
        this._jogadores = new DualListModel<>(_jogadores_source, _jogadores_target);
    }

    /**
     * Adiciona a chamada na tabela.
     *
     * @param event
     */
    public void addChamada(TransferEvent event) {
        FacesMessage msg = null;
        List<String> erros;

        try {
            erros = new ArrayList<>();

            List<SloginUsers> jogadores = (List<SloginUsers>) event.getItems();

            //
            // Insert dos jogadores presentes ao treino.
            //
            for (SloginUsers sloginUsers : jogadores) {
                SjogadorTreino jogador_treino = new SjogadorTreino();
                jogador_treino.setIdJogador(sloginUsers);
                jogador_treino.setIdTreino(_treino);
                if (event.isAdd()) {
                    jogador_treino.setPresente("S");
                } else {
                    jogador_treino.setPresente("N");
                }

                this._erro = new ControlTreino().updateChamadaTreino(jogador_treino);

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
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }

        FacesContext.getCurrentInstance()
                .addMessage(null, msg);
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

    public List<Streino> getTreinos() {
        return _treinos;
    }

    public void setTreinos(List<Streino> _treinos) {
        this._treinos = _treinos;
    }

    public Streino getTreino() {
        return _treino;
    }

    public void setTreino(Streino _treino) {
        this._treino = _treino;
    }

    public DualListModel<SloginUsers> getJogadores() {
        return _jogadores;
    }

    public void setJogadores(DualListModel<SloginUsers> _jogadores) {
        this._jogadores = _jogadores;
    }

}
