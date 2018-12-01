package br.com.mavs.View;

import br.com.mavs.Controller.ControlNotificacao;
import br.com.mavs.Controller.ControlPedido;
import br.com.mavs.Controller.ControlUser;
import br.com.mavs.Modal.Sitem;
import br.com.mavs.Modal.SitemPedido;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Snotificacao;
import br.com.mavs.Modal.Spedido;
import br.com.mavs.Modal.Sstatus;
import br.com.mavs.Modal.Usuario;
import br.com.mavs.Utils.ProxyAuthenticator;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "beanPedido")
@ViewScoped
public class BeanPedido {

    private List<Spedido> _pedidos;
    private List<SitemPedido> _itens_pedido;
    private SitemPedido item_pedido;
    private Spedido _pedido;
    private Spedido _pedido_selecionado;
    private Spedido _novo_pedido;
    private Usuario _user_facebook = null;
    private SloginUsers _user;
    private String _erro = "";
    private List<Sitem> _itens;
    private List<Sitem> _itens_selecionados;
    private Sitem _item;

    public BeanPedido() throws IOException {

        this._pedidos = new ArrayList<>();
        this._itens = new ArrayList<>();
        this._itens_selecionados = new ArrayList<>();
        this._itens_pedido = new ArrayList<>();

        this._pedido_selecionado = new Spedido();
        this._novo_pedido = new Spedido();
        this._novo_pedido.setData(new Date());
        this._item = new Sitem();
        this.item_pedido = new SitemPedido();

        this._user_facebook = (Usuario) ProxyAuthenticator.getFromSession("FACEBOOK_USER");

        if (this._user_facebook == null) {
            ProxyAuthenticator.logoff();
        }

        this._user = new ControlUser().findUser(_user_facebook);

        this._pedidos = new ControlPedido().buscaPedidos(this._user);
        this._itens = new ControlPedido().buscaItens();

    }

    /**
     * Retorna a data como String
     *
     * @param data
     * @return
     */
    public String formatDate(Date data) {
        if (data == null) {
            return "";
        } else {
            return new SimpleDateFormat("dd/MM/yyyy").format(data);
        }

    }

    /**
     *
     * @param valor
     * @return
     */
    public String formatCurrency(double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

    public void salvaPedido() {
        FacesMessage msg = null;

        try {

            if (!_itens_pedido.isEmpty()) {
                this._novo_pedido.setIdStatus(new Sstatus(1));

                this._novo_pedido.setIdUsuario(this._user);

                float valor = 0;

                for (int i = 0; i < _itens_pedido.size(); i++) {

                    valor = valor + (_itens_pedido.get(i).getIdItem().getValor() * _itens_pedido.get(i).getQuantidade());

                }

                this._novo_pedido.setValor(valor);

                this._novo_pedido.setId(new ControlPedido().salvaPedido(this._novo_pedido));

                if (this._novo_pedido.getId() != null) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro inserido com sucesso!");

                    for (int i = 0; i < _itens_pedido.size(); i++) {
                        SitemPedido item_pedido = _itens_pedido.get(i);
                        item_pedido.setIdPedido(this._novo_pedido);

                        this._erro = new ControlPedido().salvaItemPedido(item_pedido);

                    }
                    
                    Snotificacao notif = new Snotificacao();
                    
                    notif.setIdUsuario(_user);
                    notif.setData(new Date());
                    notif.setLida("N");
                    notif.setCriador(this._user.getUsuario());
                    notif.setMensagem("Seu pedido foi criado com sucesso!");
                    
                    new ControlNotificacao().criaNotificacao(notif);

                    this._pedidos = new ControlPedido().buscaPedidos(this._user);

                    this._itens = new ControlPedido().buscaItens();

                    this._itens_pedido = new ArrayList<>();
                    this._novo_pedido = new Spedido();
                    this._novo_pedido.setData(new Date());
                    this._itens_selecionados = new ArrayList<>();

                    //RequestContext.getCurrentInstance().execute(":frm_adiciona_calendario:eventDialog.hide()");
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", "É obrigatório pelo menos 1 item.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }

        this._pedido_selecionado = new Spedido();
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean statusPedido() {
        if (this._pedido_selecionado == null || this._pedido_selecionado.getId() == null ) {
            return false;
        } else {
            return this._pedido_selecionado.getIdStatus().getId() <= 3;
        }
    }

    public void removePedido() {
        FacesMessage msg = null;

        try {

            this._erro = new ControlPedido().deletePedido(this._pedido_selecionado);

            if (this._erro.equals("")) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Registro deletado com sucesso!");
                this._pedidos.remove(this._pedido_selecionado);
                //RequestContext.getCurrentInstance().execute(":frm_adiciona_calendario:eventDialog.hide()");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", this._erro);
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção", e.getMessage());
        }

        this._pedido_selecionado = new Spedido();
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cancelaPedidoSelecionado() {
        this._pedido_selecionado = new Spedido();
        this._itens_pedido = new ArrayList<>();
        this._novo_pedido = new Spedido();
        this._novo_pedido.setData(new Date());
        this._itens_selecionados = new ArrayList<>();
    }

    public void cancelaNovoPedidoSelecionado() {
        this._pedido_selecionado = new Spedido();
        this._itens_pedido = new ArrayList<>();
        this._novo_pedido = new Spedido();
        this._novo_pedido.setData(new Date());
        this._itens_selecionados = new ArrayList<>();
    }

    public void adicionaItem() {
        this._itens.remove(this._item);

        SitemPedido item_pedido = new SitemPedido();

        item_pedido.setIdItem(_item);

        this._itens_pedido.add(item_pedido);
    }

    public List<Spedido> getPedidos() {
        return _pedidos;
    }

    public void setPedidos(List<Spedido> _pedidos) {
        this._pedidos = _pedidos;
    }

    public Spedido getPedido() {
        return _pedido;
    }

    public void setPedido(Spedido _pedido) {
        this._pedido = _pedido;
    }

    public Spedido getPedido_selecionado() {
        return _pedido_selecionado;
    }

    public void setPedido_selecionado(Spedido _pedido_selecionado) {
        this._pedido_selecionado = _pedido_selecionado;
    }

    public Spedido getNovo_pedido() {
        return _novo_pedido;
    }

    public void setNovo_pedido(Spedido _novo_pedido) {
        this._novo_pedido = _novo_pedido;
    }

    public List<Sitem> getItens() {
        return _itens;
    }

    public void setItens(List<Sitem> _itens) {
        this._itens = _itens;
    }

    public Sitem getItem() {
        return _item;
    }

    public void setItem(Sitem _item) {
        this._item = _item;
    }

    public List<Sitem> getItens_selecionados() {
        return _itens_selecionados;
    }

    public void setItens_selecionados(List<Sitem> _itens_selecionados) {
        this._itens_selecionados = _itens_selecionados;
    }

    public SitemPedido getItem_pedido() {
        return item_pedido;
    }

    public void setItem_pedido(SitemPedido item_pedido) {
        this.item_pedido = item_pedido;
    }

    public List<SitemPedido> getItens_pedido() {
        return _itens_pedido;
    }

    public void setItens_pedido(List<SitemPedido> _itens_pedido) {
        this._itens_pedido = _itens_pedido;
    }
}
