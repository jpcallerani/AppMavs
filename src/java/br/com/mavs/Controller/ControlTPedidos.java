package br.com.mavs.Controller;

import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.Sitem;
import br.com.mavs.Modal.SitemPedido;
import br.com.mavs.Modal.Spedido;
import br.com.mavs.Modal.Sstatus;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ControlTPedidos {

    private List<Spedido> _pedidos;
    private List<Sstatus> _status;
    private List<Sitem> _itens;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlTPedidos() {

        this._arguments = new ArrayList<>();
        this._pedidos = new ArrayList<>();
        this._itens = new ArrayList<>();
        this._status = new ArrayList<>();

    }

    /**
     *
     * @param usuario
     * @return
     */
    public List<Spedido> buscaPedidos() {
        this._order = Order.asc("data");
        this._pedidos = new SysDao().listagem(Spedido.class, _arguments, _order, 0, true);
        return this._pedidos;
    }
    
    /**
     *
     * @param status
     * @return
     */
    public List<Sstatus> buscaStatus() {
        this._status = new SysDao().listagem(Sstatus.class, _arguments, _order, 0, true);
        return this._status;
    }

    /**
     *
     * @return
     */
    public List<Sitem> buscaItens() {
        this._arguments.add(Restrictions.eq("valido", "S"));
        this._itens = new SysDao().listagem(Sitem.class, _arguments, _order, 0, true);
        return this._itens;
    }

    /**
     *
     * @param p_pedido
     * @return
     */
    public Integer salvaPedido(Spedido p_pedido) {
        Integer id = new SysDao().savePedido(p_pedido);
        return id;
    }

    /**
     *
     * @param p_item_pedido
     * @return
     */
    public String salvaItemPedido(SitemPedido p_item_pedido) {
        this._error = new SysDao().save(p_item_pedido);
        return this._error;
    }

    /**
     * Exclui o pedido selecionado
     *
     * @param p_pedido
     * @return
     */
    public String deletePedido(Spedido p_pedido) {
        this._error = new SysDao().delete(p_pedido);
        return this._error;
    }

}
