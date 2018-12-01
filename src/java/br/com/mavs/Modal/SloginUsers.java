package br.com.mavs.Modal;

import br.com.mavs.Controller.ControlTreino;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author U0180463
 */
@Entity
@Table(name = "slogin_users")
@XmlRootElement
public class SloginUsers implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Snotificacao> snotificacaoList;
    @Column(name = "Number")
    private Integer number;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<Spedido> spedidoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idJogador")
    private Collection<SlogAcesso> slogAcessoCollection;
    @JoinColumn(name = "id_segmento", referencedColumnName = "id")
    @OneToOne(optional = false)
    private StimeSegmento idSegmento;

    @Transient
    @Basic(optional = true)
    private Spresenca presenca;
    @OneToMany(mappedBy = "idJogador")
    private List<SjogadorTreino> sjogadorTreinoList;
    @Column(name = "telefone")
    private String telefone;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = true)
    @Column(name = "Usuario")
    private String usuario;
    @Basic(optional = true)
    @Column(name = "Senha")
    private long senha;
    @Basic(optional = true)
    @Column(name = "Nome")
    private String nome;
    @Basic(optional = true)
    @Column(name = "jersey")
    private String jersey;
    @Basic(optional = true)
    @Column(name = "Data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = true)
    @Column(name = "Email")
    private String email;
    @Basic(optional = true)
    @Column(name = "admin")
    private int admin;
    @JoinColumn(name = "posicao", referencedColumnName = "ID")
    @OneToOne(optional = true)
    private SpositionsOffense posicao;

    public SloginUsers() {
    }

    public SloginUsers(Integer id) {
        this.id = id;
    }

    public SloginUsers(Integer id, String usuario, long senha, String nome, String jersey, Integer number, Date data, String email, int admin) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.nome = nome;
        this.jersey = jersey;
        this.number = number;
        this.data = data;
        this.email = email;
        this.admin = admin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public long getSenha() {
        return senha;
    }

    public void setSenha(long senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getJersey() {
        return jersey;
    }

    public void setJersey(String jersey) {
        this.jersey = jersey;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public SpositionsOffense getPosicao() {
        return posicao;
    }

    public void setPosicao(SpositionsOffense posicao) {
        this.posicao = posicao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SloginUsers)) {
            return false;
        }
        SloginUsers other = (SloginUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.SloginUsers[ id=" + id + " ]";
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    @XmlTransient
    public List<SjogadorTreino> getSjogadorTreinoList() {
        sjogadorTreinoList = new ControlTreino().findJogadorTreinoByUser(this);
        return this.sjogadorTreinoList;
    }

    public void setSjogadorTreinoList(List<SjogadorTreino> sjogadorTreinoList) {
        this.sjogadorTreinoList = sjogadorTreinoList;
    }

    public Spresenca getPresenca() {
        return presenca;
    }

    public void setPresenca(Spresenca presenca) {
        this.presenca = presenca;
    }

    public StimeSegmento getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(StimeSegmento idSegmento) {
        this.idSegmento = idSegmento;
    }

    public Collection<SlogAcesso> getSlogAcessoCollection() {
        return slogAcessoCollection;
    }

    public void setSlogAcessoCollection(Collection<SlogAcesso> slogAcessoCollection) {
        this.slogAcessoCollection = slogAcessoCollection;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @XmlTransient
    public Collection<Spedido> getSpedidoCollection() {
        return spedidoCollection;
    }

    public void setSpedidoCollection(Collection<Spedido> spedidoCollection) {
        this.spedidoCollection = spedidoCollection;
    }

    public void setSnotificacaoList(List<Snotificacao> snotificacaoList) {
        this.snotificacaoList = snotificacaoList;
    }
 }