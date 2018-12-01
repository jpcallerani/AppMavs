package br.com.mavs.View;

import br.com.mavs.Controller.ControlTreino;
import br.com.mavs.Modal.Streino;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.DefaultScheduleModel;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;

@ManagedBean(name = "viewTreinoBean")
@ViewScoped
public class BeanTreino implements Serializable {

    private DefaultScheduleModel _eventModel;
    private ScheduleEvent _event;
    private ScheduleEvent _new_event;
    private Streino _sTreino;
    private String _erro = "";
    private List<Streino> _sTreinos;
    private Date _iniDate;
    private Usuario _user_facebook = null;

    public BeanTreino() throws IOException {

        this._iniDate = new Date();

        this._sTreino = new Streino();
        this._event = new DefaultScheduleEvent();
        this._eventModel = new DefaultScheduleModel();
        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");

        if (this._user_facebook == null) {
            this.logoff();
        }

        this._sTreinos = new ControlTreino().findAllTreino(this._user_facebook.getsSegmento());

        for (Streino streino : _sTreinos) {
            DefaultScheduleEvent v_novo_evento = new DefaultScheduleEvent();
            v_novo_evento.setId(String.valueOf(streino.getId()));
            v_novo_evento.setTitle(streino.getTitulo());
            v_novo_evento.setDescription(streino.getLocal());
            v_novo_evento.setStartDate(streino.getData());
            v_novo_evento.setEndDate(streino.getData());
            this._eventModel.addEvent(v_novo_evento);
        }

    }

    /**
     * Pega a data selecionada para alteração.
     *
     * @param selectEvent
     */
    public void onDateSelect(SelectEvent selectEvent) {
        this._new_event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    /**
     * Pega o evento selecionado para alteração.
     *
     * @param selectEvent
     */
    public void onEventSelect(SelectEvent selectEvent) {
        _event = (DefaultScheduleEvent) selectEvent.getObject();
    }

    /**
     * Salva o treino na base.
     */
    public void addTreino() {
        this._new_event.getStartDate().setHours(this._new_event.getEndDate().getHours());
        this._new_event.getStartDate().setMinutes(this._new_event.getEndDate().getMinutes());

        FacesMessage msg = null;

        try {
            this._sTreino.setTitulo(this._new_event.getTitle());
            this._sTreino.setLocal(this._new_event.getDescription());
            this._sTreino.setData(this._new_event.getStartDate());
            this._sTreino.setIdSegmento(this._user_facebook.getsSegmento());

            this._iniDate = this._new_event.getStartDate();

            this._erro = new ControlTreino().addTreino(_sTreino);

            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
                this._sTreino = new ControlTreino().findTreino(_sTreino);
                this._new_event.setId(String.valueOf(this._sTreino.getId()));
                this._eventModel.addEvent(_new_event);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }

        _new_event = new DefaultScheduleEvent();
        FacesContext.getCurrentInstance()
                .addMessage(null, msg);

    }

    public void sendEmail() {
        System.out.println("Email enviado!!!!");
    }

    /**
     * Atualiza o treino na base de dados.
     */
    public void updateTreino() {

        this._event.getStartDate().setHours(this._event.getEndDate().getHours());
        this._event.getStartDate().setMinutes(this._event.getEndDate().getMinutes());

        FacesMessage msg = null;

        try {
            this._sTreino.setId(Integer.parseInt(this._event.getId()));
            this._sTreino.setTitulo(this._event.getTitle());
            this._sTreino.setLocal(this._event.getDescription());
            this._sTreino.setData(this._event.getStartDate());
            this._sTreino.setIdSegmento(this._user_facebook.getsSegmento());

            this._iniDate = this._event.getStartDate();

            this._erro = new ControlTreino().updateTreino(_sTreino);

            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro atualizado com sucesso!");
                this._eventModel.updateEvent(_event);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }

        _event = new DefaultScheduleEvent();
        FacesContext.getCurrentInstance()
                .addMessage(null, msg);
    }

    public void deleteEvent() {

        FacesMessage msg = null;

        try {
            this._sTreino.setId(Integer.parseInt(this._event.getId()));
            this._sTreino.setTitulo(this._event.getTitle());
            this._sTreino.setLocal(this._event.getDescription());
            this._sTreino.setData(this._event.getStartDate());

            this._iniDate = this._event.getStartDate();

            this._erro = new ControlTreino().deleteTreino(_sTreino);
            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                this._eventModel.deleteEvent(this._event);
                this._event = new DefaultScheduleEvent();
                //RequestContext.getCurrentInstance().execute(":frm_adiciona_calendario:eventDialog.hide()");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public DefaultScheduleModel getEventModel() {
        return _eventModel;
    }

    public void setEventModel(DefaultScheduleModel _eventModel) {
        this._eventModel = _eventModel;
    }

    public ScheduleEvent getEvent() {
        return _event;
    }

    public void setEvent(ScheduleEvent _event) {
        this._event = _event;
    }

    public ScheduleEvent getNew_event() {
        return _new_event;
    }

    public void setNew_event(ScheduleEvent _new_event) {
        this._new_event = _new_event;
    }

    public Streino getsTreino() {
        return _sTreino;
    }

    public void setsTreino(Streino _sTreino) {
        this._sTreino = _sTreino;
    }

    public List<Streino> getsTreinos() {
        return _sTreinos;
    }

    public void setsTreinos(List<Streino> _sTreinos) {
        this._sTreinos = _sTreinos;
    }

    public Date getIniDate() {
        return _iniDate;
    }

    public void setIniDate(Date _iniDate) {
        this._iniDate = _iniDate;
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
