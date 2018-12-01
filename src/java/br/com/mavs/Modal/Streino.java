package br.com.mavs.Modal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "streino")
@XmlRootElement
public class Streino implements Serializable {
    @JoinColumn(name = "id_segmento", referencedColumnName = "id")
    @ManyToOne
    private StimeSegmento idSegmento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTreino")
    private Collection<SjogadorTreino> sjogadorTreinoCollection;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Basic(optional = false)
    @Column(name = "local")
    private String local;

    public Streino() {
    }

    public Streino(Integer id) {
        this.id = id;
    }

    public Streino(Integer id, Date data, String local) {
        this.id = id;
        this.data = data;
        this.local = local;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
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
        if (!(object instanceof Streino)) {
            return false;
        }
        Streino other = (Streino) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.Streino[ id=" + id + " ]";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @XmlTransient
    public Collection<SjogadorTreino> getSjogadorTreinoCollection() {
        return sjogadorTreinoCollection;
    }

    public void setSjogadorTreinoCollection(Collection<SjogadorTreino> sjogadorTreinoCollection) {
        this.sjogadorTreinoCollection = sjogadorTreinoCollection;
    }

    public StimeSegmento getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(StimeSegmento idSegmento) {
        this.idSegmento = idSegmento;
    }

}
