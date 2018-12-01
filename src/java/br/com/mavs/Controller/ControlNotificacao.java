package br.com.mavs.Controller;

import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Snotificacao;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ControlNotificacao {

    private List<Snotificacao> _mensagens;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlNotificacao() {
        this._arguments = new ArrayList<>();
    }

    /**
     *
     * @param usuario
     * @return
     */
    public List<Snotificacao> buscaNotificacoes(SloginUsers usuario) {
        this._order = Order.desc("data");
        this._arguments.add(Restrictions.eq("idUsuario", usuario));
        this._arguments.add(Restrictions.eq("lida", "N"));
        this._mensagens = new SysDao().listagem(Snotificacao.class, _arguments, _order, 0, true);
        return this._mensagens;
    }

    /**
     *
     * @param notif
     * @return
     */
    public String atualizaNotificacao(Snotificacao notif) {
        this._error = new SysDao().saveOrUpdate(notif);
        return this._error;
    }
    
    public String criaNotificacao(Snotificacao notif) {
        this._error = new SysDao().save(notif);
        return this._error;
    }    
}
