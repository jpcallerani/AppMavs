package br.com.mavs.Controller;

import br.com.mavs.Modal.SloginUsers;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class ControlTopJogadores {

    private List<SloginUsers> _top_jogadores;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlTopJogadores() {
        this._arguments = new ArrayList<>();
        this._top_jogadores = new ArrayList<>();
    }
    
    public List<SloginUsers> retornaTopJogadores() {
        
        return _top_jogadores;
    }
    
}
