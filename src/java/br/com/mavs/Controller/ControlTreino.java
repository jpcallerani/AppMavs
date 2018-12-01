package br.com.mavs.Controller;

import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.SjogadorTreino;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Stime;
import br.com.mavs.Modal.StimeSegmento;
import br.com.mavs.Modal.Streino;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.IntegerType;

public class ControlTreino {

    private List<Streino> _treinos;
    private List<SjogadorTreino> _jogador_treinos;
    private Streino _treino;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlTreino() {
        this._arguments = new ArrayList<>();
        this._treinos = new ArrayList<>();
        this._jogador_treinos = new ArrayList<>();
    }

    /**
     * Método para gravar o treino na base de dados.
     *
     * @param p_treino
     * @return
     */
    public String addTreino(Streino p_treino) {
        this._error = new SysDao().save(p_treino);
        return this._error;
    }

    public String addChamadaTreino(SjogadorTreino p_jogador_treino) {
        this._error = new SysDao().save(p_jogador_treino);
        return this._error;
    }

    public String updateChamadaTreino(SjogadorTreino p_jogador_treino) {
        this._error = new SysDao().updateJogoTreino(p_jogador_treino);
        return this._error;
    }

    /**
     * Método para atualizar o treino na base.
     *
     * @param p_treino
     * @return
     */
    public String updateTreino(Streino p_treino) {
        this._error = new SysDao().update(p_treino);
        return this._error;
    }

    /**
     * Deleta o treino da base de dados.
     *
     * @param p_treino
     * @return
     */
    public String deleteTreino(Streino p_treino) {
        this._error = new SysDao().delete(p_treino);
        return this._error;
    }

    /**
     * Lista todos os treinos na base.
     *
     * @return
     */
    public List<Streino> findAllTreino(StimeSegmento p_segmento) {
        this._arguments.add(Restrictions.eq("idSegmento", p_segmento));
        this._treinos = new SysDao().listagem(Streino.class, _arguments, _order, 0, false);
        return this._treinos;
    }

    /**
     *
     * @param p_segmento
     * @return
     */
    public List<Streino> findTreinoPassado(StimeSegmento p_segmento) {
        this._order = Order.asc("data");
        this._arguments.add(Restrictions.le("data", new Date()));
        this._arguments.add(Subqueries.propertyNotIn("id", DetachedCriteria.forClass(SjogadorTreino.class)
                .setProjection(Property.forName("idTreino"))));
        this._arguments.add(Restrictions.eq("idSegmento", p_segmento));
        this._treinos = new SysDao().listagem(Streino.class, _arguments, _order, 0, false);
        return this._treinos;
    }

    /**
     * Método para buscar os treinos que já foram dadas as presenças.
     *
     * @return
     */
    public List<Streino> findTreinoPresença(StimeSegmento p_segSegmento) {
        this._arguments.add(Restrictions.le("data", new Date()));
        this._arguments.add(Restrictions.eq("idSegmento", p_segSegmento));
        this._arguments.add(Subqueries.propertyIn("id", DetachedCriteria.forClass(SjogadorTreino.class)
                .setProjection(Property.forName("idTreino"))));
        this._order = Order.desc("data");
        this._treinos = new SysDao().listagem(Streino.class, _arguments, _order, 0, false);
        return this._treinos;
    }

    /**
     * Método para buscar os treinos que ainda não foram.
     *
     * @return
     */
    public List<Streino> findTreinoFuturo(StimeSegmento p_segmento) {
        this._order = Order.asc("data");
        this._arguments.add(Restrictions.ge("data", new Date()));
        this._arguments.add(Restrictions.eq("idSegmento", p_segmento));
        this._arguments.add(Subqueries.propertyNotIn("id", DetachedCriteria.forClass(SjogadorTreino.class)
                .setProjection(Property.forName("idTreino"))));
        this._treinos = new SysDao().listagem(Streino.class, _arguments, _order, 5, false);
        return this._treinos;
    }

    /**
     * Busca o treino pelo ID
     *
     * @param p_treino
     * @return
     */
    public Streino findTreino(Streino p_treino) {
        this._arguments.add(Restrictions.eq("id", p_treino.getId()));
        this._treino = (Streino) new SysDao().findObject(Streino.class, _arguments, _order, 0);
        return this._treino;
    }

    /**
     *
     * @param p_treino
     * @return
     */
    public List<SjogadorTreino> findJogadorTreino(Streino p_treino) {
        this._arguments.add(Restrictions.eq("idTreino", p_treino));
        this._jogador_treinos = new SysDao().listagem(SjogadorTreino.class, _arguments, _order, 0, true);
        return this._jogador_treinos;
    }

    /**
     *
     * @param p_treino
     * @return
     */
    public List<SjogadorTreino> findJogadorTreinoByUser(SloginUsers p_user) {
        this._arguments.add(Restrictions.eq("idJogador", p_user));
        this._arguments.add(Subqueries.propertyIn("idTreino", DetachedCriteria.forClass(Streino.class)
                .setProjection(Property.forName("id"))
                .add(Restrictions.sqlRestriction("DATE_FORMAT(data,'%Y') = ? ", Calendar.getInstance().get(Calendar.YEAR), IntegerType.INSTANCE))));
        this._jogador_treinos = new SysDao().listagem(SjogadorTreino.class, _arguments, _order, 0, true);
        return this._jogador_treinos;
    }
}
