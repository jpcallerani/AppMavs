package br.com.mavs.DAO;

import br.com.mavs.Modal.SjogadorMensalidade;
import br.com.mavs.Modal.SjogadorTreino;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

/**
 *
 * SET GLOBAL tx_isolation='READ-UNCOMMITTED';
 */
public class SysDao {

    private Session sessao;
    private List listagem;
    private Object object;
    private String _erro = "";

    public SysDao() {
        this.sessao = (Session) NewHibernateUtil.getSessionFactory().openSession();
    }

    /**
     *
     * @param obj
     * @return
     */
    public String save(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.save(obj);
            this.sessao.getTransaction().commit();
            this._erro = "";
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
        } catch (Exception e) {
            this._erro = e.getMessage();
            e.printStackTrace();
        } finally {
            this.sessao.close();
        }
        return this._erro;
    }

    /**
     *
     * @param obj
     * @return
     */
    public Integer savePedido(Object obj) {
        Integer id = null;
        try {
            this.sessao.beginTransaction();
            Serializable ser = this.sessao.save(obj);
            if (ser != null) {
                id = (Integer) ser;
            }
            this.sessao.getTransaction().commit();
            this._erro = "";
        } catch (JDBCException ex) {
            System.out.println(ex);
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            this.sessao.close();
        }
        return id;
    }

    /**
     *
     * @param obj
     * @return
     */
    public String saveOrUpdate(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.saveOrUpdate(obj);
            this.sessao.getTransaction().commit();
            this._erro = "";
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
        } catch (Exception e) {
            this._erro = e.getMessage();
            e.printStackTrace();
        } finally {
            this.sessao.close();
        }
        return this._erro;
    }

    /**
     *
     * @param obj
     * @return
     */
    public Object saveReturnObj(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.save(obj);
            this.sessao.getTransaction().commit();
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
        } catch (Exception e) {
            obj = null;
            e.printStackTrace();
        } finally {
            this.sessao.close();
        }
        return obj;
    }

    /**
     *
     * @param obj
     * @return
     */
    public String saveSemCommit(Object obj) {
        try {
            this.sessao.save(obj);
            this._erro = "";
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
            ex.printStackTrace();
        } catch (Exception e) {
            this._erro = e.getMessage();
            e.printStackTrace();
        }
        return this._erro;
    }

    /**
     *
     * @param obj
     * @return
     */
    public String update(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.update(obj);
            this.sessao.getTransaction().commit();
            this._erro = "";
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
        } finally {
            this.sessao.close();
        }
        return this._erro;
    }

    /**
     *
     * @param p_jogador_treino
     * @return
     */
    public String updateJogoTreino(SjogadorTreino p_jogador_treino) {
        try {
            this.sessao.beginTransaction();
            Query query = this.sessao.createQuery("update SjogadorTreino set presente = :presente "
                    + "where id_jogador = :id_jogador and id_treino = :id_treino");
            query.setParameter("presente", p_jogador_treino.getPresente());
            query.setParameter("id_jogador", p_jogador_treino.getIdJogador());
            query.setParameter("id_treino", p_jogador_treino.getIdTreino());
            int result = query.executeUpdate();
            this.commit();
        } catch (HibernateException ex) {
            this._erro = "Erro: " + ex.getMessage();
        } finally {
            this.sessao.close();
        }
        return this._erro;
    }

    /**
     *
     * @param p_jogador_mensal
     * @return
     */
    public String updateJogadorMensalidade(SjogadorMensalidade p_jogador_mensal) {
        try {
            this.sessao.beginTransaction();
            Query query = this.sessao.createQuery("update SjogadorMensalidade set "
                    + "pago = :pago "
                    + "where id_jogador = :id_jogador "
                    + "and id_mensalidade = :id_mensalidade");
            query.setParameter("pago", p_jogador_mensal.getPago());
            query.setParameter("id_jogador", p_jogador_mensal.getIdJogador());
            query.setParameter("id_mensalidade", p_jogador_mensal.getIdMensalidade());
            int result = query.executeUpdate();
            this.commit();
        } catch (HibernateException ex) {
            this._erro = "Erro: " + ex.getMessage();
        } finally {
            this.sessao.close();
        }
        return this._erro;
    }

    /**
     *
     * @param obj
     * @return
     */
    public String delete(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.delete(obj);
            this.sessao.getTransaction().commit();
            this._erro = "";
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
        } finally {
            this.sessao.close();
        }
        return this._erro;
    }

    /**
     *
     * @param obj
     * @return
     */
    public String deleteSemCommit(Object obj) {
        try {
            this.sessao.beginTransaction();
            this.sessao.delete(obj);
            this._erro = "";
        } catch (JDBCException ex) {
            this._erro = "Code: " + ex.getErrorCode() + "Erro: " + ex.getMessage();
        }
        return this._erro;
    }

    /**
     *
     * @param clazz
     * @param p_list_argumentos
     * @param order
     * @param rownum
     * @param distinct
     * @return
     */
    public List listagem(Class clazz, List<Criterion> p_list_argumentos, Order order, Integer rownum, boolean distinct) {
        try {
            Criteria cri = sessao.createCriteria(clazz);
            for (Criterion criterion : p_list_argumentos) {
                cri.add(criterion);
            }
            if (order != null) {
                cri.addOrder(order);
            }
            if (rownum > 0) {
                cri.setMaxResults(rownum);
            }
            if (distinct) {
                cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            }
            listagem = cri.list();
        } catch (JDBCException ex) {
            System.out.println(ex.getCause() + "------" + ex.getMessage());
            listagem = null;
        } finally {
            this.sessao.close();
        }
        return listagem;
    }

    /**
     *
     * @param clazz
     * @param p_list_argumentos
     * @param order
     * @param rownum
     * @return
     */
    public Object findObject(Class clazz, List<Criterion> p_list_argumentos, Order order, Integer rownum) {
        try {
            Criteria cri = sessao.createCriteria(clazz);
            for (Criterion criterion : p_list_argumentos) {
                cri.add(criterion);
            }
            if (order != null) {
                cri.addOrder(order);
            }
            if (rownum > 0) {
                cri.setMaxResults(rownum);
            }
            cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            object = cri.uniqueResult();
        } catch (JDBCException ex) {
            System.out.println(ex.getCause() + "------" + ex.getMessage());
            object = null;
        } finally {
            this.closeSession();
        }
        return object;
    }

    /**
     *
     * @param obj
     * @param query
     * @param value
     * @param fieldName
     * @return
     */
    public List busca(Object obj, String query, int value, String fieldName) {
        try {
            Query qy = sessao.getNamedQuery(query);
            qy.setInteger(fieldName, value);
            listagem = qy.list();
        } catch (Exception ex) {
            listagem = null;
        } finally {
            this.sessao.close();
        }
        return listagem;
    }

    /**
     *
     */
    public void commit() {
        this.sessao.getTransaction().commit();
    }

    /**
     *
     */
    public void rollback() {
        this.sessao.getTransaction().rollback();
    }

    /**
     *
     */
    public void closeSession() {
        this.sessao.close();
    }

    /**
     *
     */
    public void openSession() {
        this.sessao.beginTransaction();
    }
}
