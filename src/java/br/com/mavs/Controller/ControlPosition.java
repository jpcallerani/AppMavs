package br.com.mavs.Controller;

import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.SpositionsOffense;
import br.com.mavs.Modal.StimeSegmento;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ControlPosition {

    private List<SpositionsOffense> _positions;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlPosition() {
        this._positions = new ArrayList<>();
        this._arguments = new ArrayList<>();
    }

    /**
     * Busca pelas todas posições gravadas.
     *
     * @return
     */
    public List<SpositionsOffense> listPositions(StimeSegmento p_segmento) {
        this._arguments.add(Restrictions.eq("idSegmento", p_segmento));
        this._positions = new SysDao().listagem(SpositionsOffense.class, _arguments, _order, 0, true);
        return this._positions;
    }
}
