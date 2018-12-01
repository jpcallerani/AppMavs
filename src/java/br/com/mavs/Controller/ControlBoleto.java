package br.com.mavs.Controller;

import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.Sboleto;
import br.com.mavs.Modal.SjogadorMensalidade;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class ControlBoleto {

    private List<Sboleto> _boletos;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlBoleto() {
        _boletos = new ArrayList<>();
        _arguments = new ArrayList<>();
    }

    /**
     * Busca pelas todas posições gravadas.
     *
     * @return
     */
    public List<Sboleto> listBoletos() {
        this._boletos = new SysDao().listagem(Sboleto.class, _arguments, _order, 0, true);
        return this._boletos;
    }

    public String baixaBoleto(Sboleto p_boleto) {
        this._error = new SysDao().delete(p_boleto);
        if (this._error.equals("")) {
            SjogadorMensalidade jogador_mensalidade = new SjogadorMensalidade();
            jogador_mensalidade.setIdJogador(p_boleto.getIdJogador());
            jogador_mensalidade.setIdMensalidade(p_boleto.getIdMensalidade());
            jogador_mensalidade.setPago("S");
            this._error = new SysDao().updateJogadorMensalidade(jogador_mensalidade);
            if (!this._error.equals("")) {
                new SysDao().save(p_boleto);
            }
        }
        return this._error;
    }
    
    /**
     * 
     * @param p_boleto
     * @return 
     */
    public String deletaBoleto(Sboleto p_boleto) {
        this._error = new SysDao().delete(p_boleto);
        return this._error;
    }
}
