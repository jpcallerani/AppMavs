package br.com.mavs.Modal;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "snotificacao")
@XmlRootElement
public class Snotificacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "mensagem")
    private String mensagem;
    @Basic(optional = false)
    @Column(name = "lida")
    private String lida;
    @Basic(optional = false)
    @Column(name = "criador")
    private String criador;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "id_usuario", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private SloginUsers idUsuario;

    public Snotificacao() {
    }

    public Snotificacao(Integer id) {
        this.id = id;
    }

    public Snotificacao(Integer id, String mensagem, String lida, String criador, Date data) {
        this.id = id;
        this.mensagem = mensagem;
        this.lida = lida;
        this.criador = criador;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getLida() {
        return lida;
    }

    public void setLida(String lida) {
        this.lida = lida;
    }

    public String getCriador() {
        return criador;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public SloginUsers getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(SloginUsers idUsuario) {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof Snotificacao)) {
            return false;
        }
        Snotificacao other = (Snotificacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.Snotificacao[ id=" + id + " ]";
    }

}
