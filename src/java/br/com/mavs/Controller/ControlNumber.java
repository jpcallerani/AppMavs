package br.com.mavs.Controller;

import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Snumber;
import br.com.mavs.Modal.SpositionsOffense;
import br.com.mavs.Modal.StimeSegmento;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

public class ControlNumber {

    private List<Snumber> _numbers;
    private Snumber _user;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlNumber() {
        this._arguments = new ArrayList<>();
        this._numbers = new ArrayList<>();
        this._user = new Snumber();
    }

    /**
     * Busca os números da posição em questão
     *
     * @param p_position Posição selecionada na tela
     * @return Uma lista de números disponíveis.
     */
    public List<Snumber> findNumbers(SpositionsOffense p_position, StimeSegmento p_segmento) {

        //this._arguments.add(Restrictions.eq("idPos", p_position));
        this._arguments.add(Subqueries.propertyNotIn("number", DetachedCriteria.forClass(SloginUsers.class)
                .setProjection(Property.forName("number"))
                .add(Property.forName("idSegmento").eq(p_segmento))));
        
        this._numbers = new SysDao().listagem(Snumber.class, _arguments, _order, 0, true);
        return this._numbers;
    }
}