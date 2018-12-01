package br.com.mavs.View;

import br.com.mavs.Controller.ControlMensalidade;
import br.com.mavs.Modal.Smensalidade;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.DefaultScheduleModel;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;

@ManagedBean(name = "beanMensalidade")
@ViewScoped
public class BeanMensalidade {

    private DefaultScheduleModel _eventModel;
    private ScheduleEvent _event;
    private ScheduleEvent _new_event;
    private Smensalidade _sMensalidade;
    private String _erro = "";
    private List<Smensalidade> _sMensalidades;
    private Date _iniDate;
    private Usuario _user_facebook = null;

    public BeanMensalidade() {
        this._iniDate = new Date();

        this._sMensalidade = new Smensalidade();
        this._event = new DefaultScheduleEvent();
        this._eventModel = new DefaultScheduleModel();

        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");

        if (this._user_facebook == null) {
            try {
                ProxyAuthenticator.logoff();
            } catch (IOException ex) {
                Logger.getLogger(BeanMensalidadeRelatJog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this._sMensalidades = new ControlMensalidade().findAllMensalidade(this._user_facebook.getsSegmento());

        for (Smensalidade sMensalidade : _sMensalidades) {
            DefaultScheduleEvent v_novo_evento = new DefaultScheduleEvent();
            v_novo_evento.setId(String.valueOf(sMensalidade.getId()));
            v_novo_evento.setTitle(sMensalidade.getDescricao());
            v_novo_evento.setStartDate(sMensalidade.getData());
            v_novo_evento.setEndDate(sMensalidade.getData());
            v_novo_evento.setDescription(NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(sMensalidade.getValor()));
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
     *
     */
    public void addMensalidade() {
        FacesMessage msg;
        String valor;

        try {
            valor = this._new_event.getDescription().replace(".", "");
            valor = valor.replace(",", ".");

            this._sMensalidade.setDescricao(this._new_event.getTitle());
            this._sMensalidade.setData(this._new_event.getStartDate());
            this._sMensalidade.setValor(Float.parseFloat(valor));
            this._sMensalidade.setIdSegmento(this._user_facebook.getsSegmento());

            this._iniDate = this._new_event.getStartDate();

            this._erro = new ControlMensalidade().addMensalidade(_sMensalidade);

            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
                this._sMensalidade = new ControlMensalidade().findMensalidade(_sMensalidade);
                this._new_event.setId(String.valueOf(this._sMensalidade.getId()));
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
     *
     */
    public void updateMensalidade() {
        FacesMessage msg;
        String valor;

        try {
            valor = this._event.getDescription().replace(".", "");
            valor = valor.replace(",", ".");
            valor = valor.replace("R", "");
            valor = valor.replace("$", "");
            this._sMensalidade.setValor(Float.parseFloat(valor));
            this._sMensalidade.setId(Integer.parseInt(this._event.getId()));
            this._sMensalidade.setDescricao(this._event.getTitle());
            this._sMensalidade.setData(this._event.getStartDate());

            this._iniDate = this._event.getStartDate();

            this._erro = new ControlMensalidade().updateMensalidade(_sMensalidade);

            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro atualizado com sucesso!");
                this._eventModel.updateEvent(_event);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }
        } catch (NumberFormatException e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }

        _event = new DefaultScheduleEvent();
        FacesContext.getCurrentInstance()
                .addMessage(null, msg);
    }

    public void deleteEvent() {

        FacesMessage msg = null;

        try {
            this._sMensalidade.setId(Integer.parseInt(this._event.getId()));
            this._sMensalidade.setDescricao(this._event.getDescription());
            this._sMensalidade.setData(this._event.getStartDate());
            this._sMensalidade.setIdSegmento(this._user_facebook.getsSegmento());

            this._iniDate = this._event.getStartDate();

            this._erro = new ControlMensalidade().deleteMensalidade(_sMensalidade);
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

    public Smensalidade getsMensalidade() {
        return _sMensalidade;
    }

    public void setsMensalidade(Smensalidade _sMensalidade) {
        this._sMensalidade = _sMensalidade;
    }

    public List<Smensalidade> getsMensalidades() {
        return _sMensalidades;
    }

    public void setsMensalidades(List<Smensalidade> _sMensalidades) {
        this._sMensalidades = _sMensalidades;
    }

    public Date getIniDate() {
        return _iniDate;
    }

    public void setIniDate(Date _iniDate) {
        this._iniDate = _iniDate;
    }
}
