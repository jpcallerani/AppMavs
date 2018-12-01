package br.com.mavs.View;

import br.com.mavs.Controller.ControlComentario;
import br.com.mavs.Controller.ControlLogAcesso;
import br.com.mavs.Controller.ControlNotificacao;
import br.com.mavs.Controller.ControlMensalidade;
import br.com.mavs.Controller.ControlNumber;
import br.com.mavs.Controller.ControlPosition;
import br.com.mavs.Controller.ControlTreino;
import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.SjogadorComentario;
import br.com.mavs.Modal.SjogadorTreino;
import br.com.mavs.Modal.SlogAcesso;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.SloginUsersTmp;
import br.com.mavs.Modal.Snotificacao;
import br.com.mavs.Modal.Smensalidade;
import br.com.mavs.Modal.Snumber;
import br.com.mavs.Modal.SpositionsOffense;
import br.com.mavs.Modal.Spresenca;
import br.com.mavs.Modal.StimeSegmento;
import br.com.mavs.Modal.Streino;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "viewHomeBean")
@ViewScoped
public class BeanHome implements Serializable {

    private Usuario _user_facebook = null;
    private SloginUsers _user;
    private SloginUsers _new_user;
    private List<SjogadorTreino> _jogador_treinos;
    private List<SpositionsOffense> _positions;
    private List<SloginUsersTmp> _jogadores_temp;
    private List<Snumber> _numbers;
    private List<SjogadorComentario> _comentarios;
    private SpositionsOffense _position;
    private SjogadorTreino _jogador_treino;
    private List<Streino> _treinos;
    private List<Smensalidade> _mensalidades;
    private Snumber _number;
    private Smensalidade _mensalidade;
    private SjogadorComentario _comentario;
    private SlogAcesso _log_acesso;
    private String _erro = "";
    private String novo_acesso = "N";
    private List<Snotificacao> _notificacoes;

    public BeanHome() throws IOException {
        //
        this._numbers = new ArrayList<>();
        this._notificacoes = new ArrayList<>();
        this._position = new SpositionsOffense();
        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");
        this._new_user = new SloginUsers();
        this._positions = new ArrayList();
        this._comentarios = new ArrayList();
        this._jogador_treinos = new ArrayList();
        this._jogador_treino = new SjogadorTreino();
        this._treinos = new ArrayList();
        this._comentario = new SjogadorComentario();
        this._log_acesso = new SlogAcesso();
        //
        if (this._user_facebook == null) {
            try {
                ProxyAuthenticator.logoff();
            } catch (IOException ex) {
                Logger.getLogger(BeanMensalidadeRelatJog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        this._user = new ControlUser().findUser(_user_facebook);
        //
        // Se não encontrar o usuário busca as posiçoes e os numeros para cadastro.
        if (this._user == null) {
            _positions = new ControlPosition().listPositions(_user_facebook.getsSegmento());
            //
            this._new_user.setEmail(_user_facebook.getEmail());
            this._new_user.setData(_user_facebook.getBirthdayAsDate());
        } else {
            if (this._user.getNumber() == 0) {
                this._new_user = this._user;
                _positions = new ControlPosition().listPositions(_user_facebook.getsSegmento());
            }
            //
            this._user.setPresenca(new Spresenca(_user.getSjogadorTreinoList()));
            this.getLastTreino();
            //
            this._mensalidade = new ControlMensalidade().findNextMensalidade(_user);
            this._mensalidades = new ControlMensalidade().findMensalNaoPagasVencidas(_user);
            //
            // Insere na tabela de log de acesso no sistema.
            novo_acesso = (String) ProxyAuthenticator.getFromSession("logado");
            if (novo_acesso.equals("N")) {
                this._log_acesso.setData(new Date());
                this._log_acesso.setIdJogador(_user);
                this._erro = new ControlLogAcesso().insereLogAcesso(_log_acesso);
            }
            //
        }
        //
        if (this._user.getAdmin() == 1) {
            this._jogadores_temp = new ControlUser().findAllUsersTmp(this._user.getIdSegmento());
        }
        this._notificacoes = new ControlNotificacao().buscaNotificacoes(_user);
        this._comentarios = new ControlComentario().listComentarios(_user_facebook.getsSegmento());
        this._treinos = new ControlTreino().findTreinoFuturo(_user_facebook.getsSegmento());
        //
    }

    public void atualizaNotificacao(Snotificacao notif) {
        FacesMessage msg;

        try {

            notif.setLida("S");
            
            this._erro = new ControlNotificacao().atualizaNotificacao(notif);

            if (this._erro.equals("")) {
                this._notificacoes.remove(notif);
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }
    }

    /**
     * Retorna a data como String
     *
     * @param data
     * @return
     */
    public String formatDate(Date data) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data);
    }

    /**
     * Retorna a data como String
     *
     * @param data
     * @return
     */
    public String formatOnlyDate(Date data) {
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    /**
     *
     */
    public void addComentario() {
        FacesMessage msg;

        try {

            this._comentario.setData(new Date());
            this._comentario.setIdJogador(this._user);
            this._comentario.setIdSegmento(this._user.getIdSegmento());

            this._erro = new ControlComentario().addComentario(this._comentario);

            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
                this._comentarios.add(0, _comentario);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }

        _comentario = new SjogadorComentario();
        _comentario.setComentario("");
        FacesContext.getCurrentInstance()
                .addMessage(null, msg);
    }

    /**
     *
     * @param data
     * @return
     */
    public String formatHour(Date data) {
        return new SimpleDateFormat("HH:mm").format(data);
    }

    /**
     *
     */
    private void getLastTreino() {
        try {
            this._jogador_treinos = this._user.getPresenca().getJogador_treinos();

            for (SjogadorTreino sjogadorTreino : _jogador_treinos) {
                if (sjogadorTreino.getPresente().equalsIgnoreCase("S")) {
                    if (this._jogador_treino.getIdTreino() == null) {
                        this._jogador_treino = sjogadorTreino;
                    } else {
                        if (this._jogador_treino.getIdTreino().getData().before(sjogadorTreino.getIdTreino().getData())) {
                            this._jogador_treino = sjogadorTreino;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * Salva o usuário de primeiro acesso na base.
     */
    public void addUser() {

        String erro = "";

        this._new_user.setUsuario(this._user_facebook.getName());
        this._new_user.setPosicao(this._position);
        this._new_user.setNumber(this._number.getNumber());
        this._new_user.setSenha(Long.parseLong(this._user_facebook.getId()));
        this._new_user.setIdSegmento(this._user_facebook.getsSegmento());
        this._new_user.setAdmin(0);

        FacesMessage msg = null;

        try {
            erro = new ControlUser().addUser(this._new_user);

            if (erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");

                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Home.xhtml?faces-redirect=true");
                this._user = _new_user;
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", erro);
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     * @param jogadores_temp
     */
    public void addUser(SloginUsersTmp jogadores_temp) {

        String erro = "";
        SloginUsers jogador = new SloginUsers();
        FacesMessage msg = null;

        if (new ControlUser().removeJogadorTemp(jogadores_temp).equalsIgnoreCase("")) {
            jogador.setAdmin(0);
            jogador.setData(jogadores_temp.getData());
            jogador.setEmail(jogadores_temp.getEmail());
            jogador.setIdSegmento(new StimeSegmento(jogadores_temp.getIdSegmento()));
            jogador.setNome(jogadores_temp.getNome());
            jogador.setSenha(jogadores_temp.getSenha());
            jogador.setUsuario(jogadores_temp.getUsuario());

            try {
                erro = new ControlUser().addUser(jogador);

                if (erro.equals("")) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
                    this._jogadores_temp.remove(jogadores_temp);
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", erro);
                }

            } catch (Exception e) {
                e.printStackTrace();
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Não foi possível aprovar o usuário!");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void showNumbers() {

        this._numbers = new ControlNumber().findNumbers(_position, _user_facebook.getsSegmento());

        //Sorting
        Collections.sort(this._numbers, new Comparator<Snumber>() {
            @Override
            public int compare(Snumber o1, Snumber o2) {
                return o1.getNumber().compareTo(o2.getNumber());
            }
        });
    }

    /**
     *
     * @param valor
     * @return
     */
    public String formatCurrency(double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

    /**
     *
     * @param comentario
     */
    public void deleteComentario(SjogadorComentario comentario) {
        FacesMessage msg = null;

        try {
            this._erro = new ControlComentario().deleteComentario(comentario);
            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                this._comentarios.remove(comentario);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }
        FacesContext.getCurrentInstance()
                .addMessage(null, msg);
    }

    /**
     *
     * @param jogador_temp
     */
    public void removeJogadorTemp(SloginUsersTmp jogador_temp) {
        FacesMessage msg = null;

        try {
            this._erro = new ControlUser().removeJogadorTemp(jogador_temp);
            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                this._jogadores_temp.remove(jogador_temp);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }
        FacesContext.getCurrentInstance()
                .addMessage(null, msg);
    }

    /**
     *
     * @return
     */
    public Usuario getUser_facebook() {
        return _user_facebook;
    }

    /**
     *
     * @param _user_facebook
     */
    public void setUser_facebook(Usuario _user_facebook) {
        this._user_facebook = _user_facebook;
    }

    public SloginUsers getUser() {
        return _user;
    }

    public void setUser(SloginUsers _user) {
        this._user = _user;
    }

    public SloginUsers getNew_user() {
        return _new_user;
    }

    public void setNovo_user(SloginUsers _new_user) {
        this._new_user = _new_user;
    }

    public List<SpositionsOffense> getPositions() {
        return _positions;
    }

    public void setPositions(List<SpositionsOffense> _positions) {
        this._positions = _positions;
    }

    public SpositionsOffense getPosition() {
        return _position;
    }

    public void setPosition(SpositionsOffense _position) {
        this._position = _position;
    }

    public List<Snumber> getNumbers() {
        return _numbers;
    }

    public void setNumbers(List<Snumber> _numbers) {
        this._numbers = _numbers;
    }

    public Snumber getNumber() {
        return _number;
    }

    public void setNumber(Snumber _number) {
        this._number = _number;
    }

    public SjogadorTreino getTreino() {
        return _jogador_treino;
    }

    public void setTreino(SjogadorTreino _jogador_treino) {
        this._jogador_treino = _jogador_treino;
    }

    public List<Streino> getTreinos() {
        return _treinos;
    }

    public void setTreinos(List<Streino> _treinos) {
        this._treinos = _treinos;
    }

    public Smensalidade getMensalidade() {
        return _mensalidade;
    }

    public void setensalidade(Smensalidade _mensalidade) {
        this._mensalidade = _mensalidade;
    }

    public List<Smensalidade> getMensalidades() {
        return _mensalidades;
    }

    public void setMensalidades(List<Smensalidade> _mensalidades) {
        this._mensalidades = _mensalidades;
    }

    public List<SjogadorComentario> getComentarios() {
        return _comentarios;
    }

    public void setComentarios(List<SjogadorComentario> _comentarios) {
        this._comentarios = _comentarios;
    }

    public SjogadorComentario getComentario() {
        return _comentario;
    }

    public void setComentario(SjogadorComentario _comentario) {
        this._comentario = _comentario;
    }

    /**
     *
     * @throws IOException
     */
    public void logoff() throws IOException {
        ProxyAuthenticator.logoff();
    }

    public List<SloginUsersTmp> getJogadores_temp() {
        return _jogadores_temp;
    }

    public void setJogadores_temp(List<SloginUsersTmp> _jogadores_temp) {
        this._jogadores_temp = _jogadores_temp;
    }

    public List<Snotificacao> getNotificacoes() {
        return _notificacoes;
    }

    public void setNotificacoes(List<Snotificacao> _notificacoes) {
        this._notificacoes = _notificacoes;
    }
}
