package br.com.mavs.Controller;

import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.SjogadorComentario;
import br.com.mavs.Modal.StimeSegmento;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ControlComentario {

    private List<SjogadorComentario> _comentarios;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlComentario() {
        _comentarios = new ArrayList<>();
        _arguments = new ArrayList<>();
    }

   /**
    * 
    * @param p_segmento
    * @return 
    */
    public List<SjogadorComentario> listComentarios(StimeSegmento p_segmento) {
        this._arguments.add(Restrictions.eq("idSegmento", p_segmento));
        this._order = Order.desc("id");
        this._comentarios = new SysDao().listagem(SjogadorComentario.class, _arguments, _order, 0, false);
        return this._comentarios;
    }

    /**
     *
     * @param p_comentario
     * @return
     */
    public String addComentario(SjogadorComentario p_comentario) {
        this._error = new SysDao().save(p_comentario);
        return this._error;
    }

    /**
     * 
     * @param p_comentario
     * @return 
     */
    public String deleteComentario(SjogadorComentario p_comentario) {
        this._error = new SysDao().delete(p_comentario);
        return this._error;
    }
}
