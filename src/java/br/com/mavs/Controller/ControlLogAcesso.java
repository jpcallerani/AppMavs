package br.com.mavs.Controller;

import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.SlogAcesso;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class ControlLogAcesso {

    private List<SlogAcesso> _log_acessos;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlLogAcesso() {
        this._arguments = new ArrayList<>();
        this._log_acessos = new ArrayList<>();
    }

    /**
     *
     * @param p_log_acesso
     * @return
     */
    public String insereLogAcesso(SlogAcesso p_log_acesso) {
        _error = new SysDao().save(p_log_acesso);
        ProxyAuthenticator.setFromSession("logado", "S");
        return _error;
    }

    /**
     * 
     * @return 
     */
    public List<SlogAcesso> listaLogAcesso() {
        this._order = Order.desc("data");
        this._log_acessos = new SysDao().listagem(SlogAcesso.class, _arguments, _order, 0, true);
        return this._log_acessos;
    }
}
