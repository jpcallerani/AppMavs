package br.com.mavs.Controller;

import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Emissor;
import br.com.caelum.stella.boleto.Sacado;
import br.com.caelum.stella.boleto.bancos.Itau;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoletoHTML;
import br.com.mavs.DAO.SysDao;
import br.com.mavs.Modal.Sboleto;
import br.com.mavs.Modal.SjogadorMensalidade;
import br.com.mavs.Modal.SloginUsers;
import br.com.mavs.Modal.Smensalidade;
import br.com.mavs.Modal.StimeSegmento;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

public class ControlMensalidade {

    private List<Smensalidade> _mensalidades;
    private List<SjogadorMensalidade> _jogador_mensalidade;
    private List<SloginUsers> _jogadores;
    private Smensalidade _mensalidade;
    private final List<Criterion> _arguments;
    private Order _order;
    private String _error = "";

    public ControlMensalidade() {
        this._arguments = new ArrayList<>();
        this._mensalidades = new ArrayList<>();
        this._jogadores = new ArrayList<>();
        this._jogador_mensalidade = new ArrayList<>();
    }

    /**
     *
     * @param p_jogador_mensalidade
     * @return
     */
    public String updateJogadorrMensalidade(SjogadorMensalidade p_jogador_mensalidade) {
        this._error = new SysDao().updateJogadorMensalidade(p_jogador_mensalidade);
        return this._error;
    }

    /**
     *
     * @param p_jogador_mensalidade
     * @return
     */
    public String deleteJogadorMensal(SjogadorMensalidade p_jogador_mensalidade) {
        this._error = new SysDao().updateJogadorMensalidade(p_jogador_mensalidade);
        return this._error;
    }

    /**
     * 
     * @param p_segmento
     * @return 
     */
    public List<Smensalidade> findAllMensalidade(StimeSegmento p_segmento) {
        this._arguments.add(Restrictions.eq("idSegmento", p_segmento));
        this._mensalidades = new SysDao().listagem(Smensalidade.class, _arguments, _order, 0, true);
        return this._mensalidades;
    }

    /**
     * 
     * @param p_segmento
     * @return 
     */
    public List<Smensalidade> findAllMensalidadePassadas(StimeSegmento p_segmento) {
        this._order = Order.desc("data");
        this._arguments.add(Restrictions.le("data", new Date()));
        this._arguments.add(Restrictions.eq("idSegmento", p_segmento));
        this._mensalidades = new SysDao().listagem(Smensalidade.class, _arguments, _order, 0, true);
        return this._mensalidades;
    }

    /**
     *
     * @return
     */
    public List<SjogadorMensalidade> findAllJogadorMensalidade() {
        this._jogador_mensalidade = new SysDao().listagem(SjogadorMensalidade.class, _arguments, _order, 0, true);
        return this._jogador_mensalidade;
    }

    /**
     *
     * @param p_mensalidade
     * @return
     */
    public String addMensalidade(Smensalidade p_mensalidade) {
        this._error = new SysDao().save(p_mensalidade);
        return this._error;
    }

    /**
     *
     * @param p_mensalidade
     * @return
     */
    public String updateMensalidade(Smensalidade p_mensalidade) {
        this._error = new SysDao().update(p_mensalidade);
        return this._error;
    }

    /**
     *
     * @param p_mensalidade
     * @return
     */
    public String deleteMensalidade(Smensalidade p_mensalidade) {
        this._error = new SysDao().delete(p_mensalidade);
        return this._error;
    }

    /**
     *
     * @param p_mensalidade
     * @return
     */
    public Smensalidade findMensalidade(Smensalidade p_mensalidade) {
        this._arguments.add(Restrictions.eq("id", p_mensalidade.getId()));
        this._mensalidade = (Smensalidade) new SysDao().findObject(Smensalidade.class, _arguments, _order, 0);
        return this._mensalidade;
    }

    /**
     *
     * @param p_user
     * @return
     */
    public Smensalidade findNextMensalidade(SloginUsers p_user) {
        this._order = Order.asc("data");
        this._arguments.add(Subqueries.propertyIn("id", DetachedCriteria.forClass(SjogadorMensalidade.class)
                .setProjection(Property.forName("idMensalidade"))
                .add(Property.forName("idJogador").eq(p_user))
                .add(Property.forName("pago").eq("N"))));
        this._arguments.add(Restrictions.ge("data", new Date()));
        this._arguments.add(Restrictions.eq("idSegmento", p_user.getIdSegmento()));
        this._mensalidade = (Smensalidade) new SysDao().findObject(Smensalidade.class, _arguments, _order, 1);
        return this._mensalidade;
    }

    /**
     *
     * @param p_mensalidade
     * @return
     */
    public List<SloginUsers> findJogadoresDevendo(Smensalidade p_mensalidade) {
        this._order = Order.asc("nome");
        this._arguments.add(Subqueries.propertyIn("id", DetachedCriteria.forClass(SjogadorMensalidade.class)
                .setProjection(Property.forName("idJogador"))
                .add(Property.forName("idMensalidade").eq(p_mensalidade))
                .add(Property.forName("pago").eq("N"))));
        this._jogadores = new SysDao().listagem(SloginUsers.class, _arguments, _order, 0, false);
        return this._jogadores;
    }

    /**
     *
     * @param p_user
     * @return
     */
    public List<Smensalidade> findMensalidadePagas(SloginUsers p_user) {
        this._order = Order.desc("data");
        this._arguments.add(Subqueries.propertyIn("id", DetachedCriteria.forClass(SjogadorMensalidade.class)
                .setProjection(Property.forName("idMensalidade"))
                .add(Property.forName("idJogador").eq(p_user))
                .add(Property.forName("pago").eq("S"))));
        this._mensalidades = new SysDao().listagem(Smensalidade.class, _arguments, _order, 0, true);
        return this._mensalidades;
    }

    /**
     *
     * @param p_user
     * @return
     */
    public List<Smensalidade> findMensalidadeNaoPagas(SloginUsers p_user) {
        this._order = Order.asc("data");
        this._arguments.add(Subqueries.propertyIn("id", DetachedCriteria.forClass(SjogadorMensalidade.class)
                .setProjection(Property.forName("idMensalidade"))
                .add(Property.forName("idJogador").eq(p_user))
                .add(Property.forName("pago").eq("N"))));
        this._mensalidades = new SysDao().listagem(Smensalidade.class, _arguments, _order, 0, true);
        return this._mensalidades;
    }

    /**
     *
     * @param p_user
     * @return
     */
    public List<Smensalidade> findMensalidadeNaoPagasBoleto(SloginUsers p_user) {
        this._order = Order.asc("data");
        this._arguments.add(Subqueries.propertyIn("id", DetachedCriteria.forClass(SjogadorMensalidade.class)
                .setProjection(Property.forName("idMensalidade"))
                .add(Property.forName("idJogador").eq(p_user))
                .add(Property.forName("pago").eq("N"))));
        this._arguments.add(Subqueries.propertyNotIn("id", DetachedCriteria.forClass(Sboleto.class)
                .setProjection(Property.forName("idMensalidade"))));
        this._mensalidades = new SysDao().listagem(Smensalidade.class, _arguments, _order, 0, true);
        return this._mensalidades;
    }

    /**
     *
     * @param p_user
     * @return
     */
    public List<Smensalidade> findMensalNaoPagasVencidas(SloginUsers p_user) {
        this._order = Order.asc("data");
        this._arguments.add(Subqueries.propertyIn("id", DetachedCriteria.forClass(SjogadorMensalidade.class)
                .setProjection(Property.forName("idMensalidade"))
                .add(Property.forName("idJogador").eq(p_user))
                .add(Property.forName("pago").eq("N"))));
        this._arguments.add(Restrictions.lt("data", new Date()));
        this._mensalidades = new SysDao().listagem(Smensalidade.class, _arguments, _order, 0, true);
        return this._mensalidades;
    }

    /**
     *
     * @param p_user
     * @param mensalidades
     * @return
     */
    public File geraBoleto(SloginUsers p_user, Smensalidade mensalidade) {

        String nossoNumero = new SimpleDateFormat("MMddHHss").format(new Date());
        float valor = BigDecimal.valueOf(0.50).floatValue();

        Emissor emissor = Emissor.novoEmissor();
        emissor.comCedente("João Paulo da Silva");
        emissor.comAgencia("2964");
        emissor.comCarteira("178");
        emissor.comContaCorrente("32555");
        emissor.comDigitoContaCorrente("5");
        emissor.comNossoNumero(nossoNumero);
        emissor.comDigitoNossoNumero("7");
        emissor.comEndereco("Rua João Ferraz 51, Recanto do Lago");

        Sacado sacado = Sacado.novoSacado();
        sacado.comNome(p_user.getNome());
        sacado.comCpf("");

        Boleto boleto = Boleto.novoBoleto();
        boleto.comDatas(Datas.novasDatas().comVencimento(this.geraDataVencimento()));
        boleto.comNumeroDoDocumento(this.geraNumeroDocumento(mensalidade));
        boleto.comEmissor(emissor);
        boleto.comBanco(new Itau());
        boleto.comSacado(sacado);
        boleto.comValorBoleto(BigDecimal.valueOf(valor));

        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        File arquivoPdf = new File(realPath + "/Boletos/" + p_user.getJersey() + "_"
                + p_user.getPosicao().getPosition() + "_"
                + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".png");
        GeradorDeBoletoHTML boletoHTML = new GeradorDeBoletoHTML(boleto);
        boletoHTML.geraPNG(arquivoPdf);

        // Insere o boleto na base.
        Sboleto sboleto = new Sboleto();
        sboleto.setId(new BigInteger("178" + nossoNumero + "6"));
        sboleto.setData(this.geraDataVencimento().getTime());
        sboleto.setIdJogador(p_user);
        sboleto.setIdMensalidade(mensalidade);
        sboleto.setValor(valor);

        new SysDao().save(sboleto);

        return arquivoPdf;
    }

    /**
     *
     * @return
     */
    public Calendar geraDataVencimento() {
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT-3"));
        System.out.println("Data: " + sd.format(c.getTime()));
        c.add(Calendar.DAY_OF_MONTH, 3);
        return c;
    }

    /**
     *
     * @param p_user
     * @return
     */
    public String geraNossoNumero(SloginUsers p_user) {

        String longString = String.valueOf(p_user.getSenha()); //transforma seu long em uma String
        String nossoNumero;

        if (longString.length() >= 8) {
            int posicaoUltimoCaractere = longString.length() - 8; //pega a posicao do ultimo caractere da string
            //nesse caso a posicao seria 11
            int tamanhoString = longString.length(); //pega o tamanho da string, nesse caso, seria 12

            //extrai o ultimo caractere da string utilizando as variaveis acima
            nossoNumero = longString.substring(posicaoUltimoCaractere, tamanhoString);
        } else {
            nossoNumero = String.format("%08d", Long.parseLong(longString));
        }
        return nossoNumero;
    }

    /**
     *
     * @param mensalidades
     * @return
     */
    public String geraNumeroDocumento(Smensalidade mensalidade) {
        String numeroDocumento = "";
        numeroDocumento += new SimpleDateFormat("MMyy").format(mensalidade.getData());
        numeroDocumento = String.format("%016d", Long.parseLong(numeroDocumento));
        return numeroDocumento;
    }
}
