package br.com.mavs.Modal;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sjogador_mensalidade")
@XmlRootElement
public class SjogadorMensalidade implements Serializable {
    @Basic(optional = false)
    @Column(name = "pago")
    private String pago;
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")    
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_mensalidade", referencedColumnName = "id")
    @OneToOne
    private Smensalidade idMensalidade;
    @JoinColumn(name = "id_jogador", referencedColumnName = "ID")
    @ManyToOne
    private SloginUsers idJogador;

    public SjogadorMensalidade() {
    }

    public SjogadorMensalidade(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof SjogadorMensalidade)) {
            return false;
        }
        SjogadorMensalidade other = (SjogadorMensalidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.SjogadorMensalidade[ id=" + id + " ]";
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }
    
}
