package br.com.mavs.Controller;

import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.SloginUsersTmp;
import br.com.mavs.Modal.StimeSegmento;
import br.com.mavs.Modal.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ControlUser {

    private List<SloginUsers> _users;
    private List<SloginUsersTmp> _users_temp;
    private SloginUsers _user;
    private SloginUsersTmp _user_tmp;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlUser() {
        this._users = new ArrayList<>();
        this._users_temp = new ArrayList<>();
        this._arguments = new ArrayList<>();
        this._user_tmp = new SloginUsersTmp();
    }

    /**
     * Verifica se o usuário do facebook existe na tabela.
     *
     * @param p_user
     * @return
     */
    public SloginUsers findUser(Usuario p_user) {
        this._arguments.add(Restrictions.eq("senha", Long.parseLong(p_user.getId())));
        this._user = (SloginUsers) new SysDao().findObject(SloginUsers.class, _arguments, _order, 0);
        return this._user;
    }

    /**
     * Método para gravar o usuário no banco
     *
     * @param p_user Usuário a ser salvo
     * @return o Erro
     */
    public String addUser(SloginUsers p_user) {
        this._error = new SysDao().saveOrUpdate(p_user);
        return _error;
    }

    /**
     * Método para gravar o usuário no banco
     *
     * @param p_user Usuário a ser salvo
     * @return o Erro
     */
    public String addUserTmp(Usuario p_user) {
        this._user_tmp.setAdmin(0);
        this._user_tmp.setData(p_user.getBirthdayAsDate());
        this._user_tmp.setEmail(p_user.getEmail());
        if (p_user.getGender().equalsIgnoreCase("male") || p_user.getGender().equalsIgnoreCase("masculino")) {
            this._user_tmp.setIdSegmento(1);
        } else {
            this._user_tmp.setIdSegmento(3);
        }
        this._user_tmp.setNome(p_user.getFirstName() + " " + p_user.getLastName());
        this._user_tmp.setNumber(0);
        this._user_tmp.setSenha(Long.valueOf(p_user.getId()));
        this._user_tmp.setUsuario(p_user.getName());

        this._error = new SysDao().saveOrUpdate(this._user_tmp);
        return _error;
    }

    public List<SloginUsers> findAllUsers(StimeSegmento p_segmento) {
        this._order = Order.asc("nome");
        this._arguments.add(Restrictions.eq("idSegmento", p_segmento));
        this._users = new SysDao().listagem(SloginUsers.class, _arguments, _order, 0, true);
        return this._users;
    }
    
    /**
     * 
     * @param p_segmento
     * @return 
     */
       public List<SloginUsersTmp> findAllUsersTmp(StimeSegmento p_segmento) {
        this._order = Order.asc("nome");
        this._arguments.add(Restrictions.eq("idSegmento", p_segmento.getId()));
        this._users_temp = new SysDao().listagem(SloginUsersTmp.class, _arguments, _order, 0, true);
        return this._users_temp;
    }

    /**
     *
     * @param user
     * @return
     */
    public String removeUser(SloginUsers user) {
        this._error = new SysDao().delete(user);
        return this._error;
    }
    
    
      /**
     *
     * @param user
     * @return
     */
    public String removeJogadorTemp(SloginUsersTmp user) {
        this._error = new SysDao().delete(user);
        return this._error;
    }

    /**
     *
     * @param p_user
     */
    public void addLogAcesso(SloginUsers p_user) {

    }
}
