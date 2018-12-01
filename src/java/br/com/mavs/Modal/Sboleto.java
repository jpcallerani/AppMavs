package br.com.mavs.Modal;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "sboleto")
@XmlRootElement
public class Sboleto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private BigInteger id;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @Column(name = "valor")
    private float valor;
    @JoinColumn(name = "id_mensalidade", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Smensalidade idMensalidade;
    @JoinColumn(name = "id_jogador", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private SloginUsers idJogador;

    public Sboleto() {
    }

    public Sboleto(BigInteger id) {
        this.id = id;
    }

    public Sboleto(BigInteger id, Date data, float valor) {
        this.id = id;
        this.data = data;
        this.valor = valor;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Smensalidade getIdMensalidade() {
        return idMensalidade;
    }

    public void setIdMensalidade(Smensalidade idMensalidade) {
        this.idMensalidade = idMensalidade;
    }

    public SloginUsers getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(SloginUsers idJogador) {
        this.idJogador = idJogador;
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
        if (!(object instanceof Sboleto)) {
            return false;
        }
        Sboleto other = (Sboleto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.Sboleto[ id=" + id + " ]";
    }
    
}
