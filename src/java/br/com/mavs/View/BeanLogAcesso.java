package br.com.mavs.View;

import br.com.mavs.Controller.ControlLogAcesso;
import br.com.mavs.Modal.SlogAcesso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "viewLogAcesso")
@RequestScoped
public class BeanLogAcesso {

    private List<SlogAcesso> _log_acesso;

    public BeanLogAcesso() {
        this._log_acesso = new ArrayList();

        this._log_acesso = new ControlLogAcesso().listaLogAcesso();
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

    public List<SlogAcesso> getLog_acesso() {
        return _log_acesso;
    }

    public void setLog_acesso(List<SlogAcesso> _log_acesso) {
        this._log_acesso = _log_acesso;
    }
}
