package br.com.mavs.View;

import br.com.mavs.Controller.ControlBoleto;
import br.com.mavs.Modal.Sboleto;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "beanBoleto")
@ViewScoped
public class BeanBoleto {

    private List<Sboleto> _boletos;
    private Sboleto _selectedBoleto;
    private String _erro = "";

    public BeanBoleto() {
        this._selectedBoleto = new Sboleto();
        this._boletos = new ControlBoleto().listBoletos();
    }

    public void baixaBoleto(Sboleto boleto) {

        FacesMessage msg = null;

        try {
            this._erro = new ControlBoleto().baixaBoleto(boleto);
            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");
                this._boletos.remove(boleto);
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
     * @param boleto 
     */
    public void deletaBoleto(Sboleto boleto) {
        FacesMessage msg = null;

        try {
            this._erro = new ControlBoleto().deletaBoleto(boleto);
            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                this._boletos.remove(boleto);
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
     * @param valor
     * @return
     */
    public String formatCurrency(double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

    public List<Sboleto> getBoletos() {
        return _boletos;
    }

    public void setBoletos(List<Sboleto> _boletos) {
        this._boletos = _boletos;
    }

    public Sboleto getSelectedBoleto() {
        return _selectedBoleto;
    }

    public void setSelectedBoleto(Sboleto _selectedBoleto) {
        this._selectedBoleto = _selectedBoleto;
    }
}
