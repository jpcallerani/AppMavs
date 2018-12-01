package br.com.mavs.View;

import br.com.mavs.Controller.ControlMensalidade;
import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Smensalidade;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "beanMensalidadeBoleto")
@ViewScoped
public class BeanMensalidadeBoleto {

    private Usuario _user_facebook = null;
    private SloginUsers _jogador;
    private Smensalidade _mensalidade;
    private List<Smensalidade> _mensalidade_source;
    private List<Smensalidade> _mensalidade_target;
    private DualListModel<Smensalidade> _mensalidades;
    private StreamedContent _streamedContent;
    private File arquivopdf;
    private String _erro = "";

    public BeanMensalidadeBoleto() {
        this._jogador = new SloginUsers();
        this._streamedContent = new DefaultStreamedContent();
    }

    @PostConstruct
    public void init() {
        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");
        //
        if (this._user_facebook == null) {
            try {
                this.logoff();
            } catch (IOException ex) {
                Logger.getLogger(BeanMensalidadeBoleto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        this._jogador = new ControlUser().findUser(_user_facebook);
        //
        this._mensalidade_source = new ControlMensalidade().findMensalidadeNaoPagasBoleto(this._jogador);
        //
        _mensalidade_target = new ArrayList<>();
        //
        this._mensalidades = new DualListModel<>(_mensalidade_source, _mensalidade_target);
    }

    /**
     *
     */
    public void geraBoleto() {

        FacesMessage msg;
        this._streamedContent = new DefaultStreamedContent();
        try {

            if (this._mensalidades.getTarget().isEmpty()) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "É necessário escolher pelo menos uma mensalidade!");
            } else {
                if (this._mensalidades.getTarget().size() > 1) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Impossível pagar mais do que uma mensalidade por vez!");
                } else {
                    _mensalidade = this._mensalidades.getTarget().get(0);
                    arquivopdf = new ControlMensalidade().geraBoleto(_jogador, _mensalidade);
                    if (arquivopdf != null) {
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Boleto gerado com sucesso!");
                        FileInputStream fis = new FileInputStream(arquivopdf);
                        _streamedContent = new DefaultStreamedContent(fis, "image/png", arquivopdf.getName());
                        RequestContext.getCurrentInstance().execute("PF('dlg_down').show()");
                        this._mensalidade_source.remove(_mensalidade);
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "Houve um erro na geração do boleto!");
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }

        this._mensalidades = new DualListModel<>(_mensalidade_source, _mensalidade_target);
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

    public Smensalidade getMensalidade() {
        return _mensalidade;
    }

    public void setMensalidade(Smensalidade _mensalidade) {
        this._mensalidade = _mensalidade;
    }

    public DualListModel<Smensalidade> getMensalidades() {
        return _mensalidades;
    }

    public void setMensalidades(DualListModel<Smensalidade> _mensalidades) {
        this._mensalidades = _mensalidades;
    }

    public StreamedContent getStreamedContent() {
        return _streamedContent;
    }

    public void setStreamedContent(StreamedContent _streamedContent) {
        this._streamedContent = _streamedContent;
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
