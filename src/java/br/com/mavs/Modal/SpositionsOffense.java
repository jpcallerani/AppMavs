package br.com.mavs.Modal;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "spositions_offense")
@XmlRootElement
public class SpositionsOffense implements Serializable {
    @JoinColumn(name = "id_segmento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private StimeSegmento idSegmento;
    @OneToMany(mappedBy = "idPos")
    private List<Snumber> snumberCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "position")
    private String position;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "posicao")
    private Collection<SloginUsers> sloginUsersCollection;

    public SpositionsOffense() {
    }

    public SpositionsOffense(Integer id) {
        this.id = id;
    }

    public SpositionsOffense(Integer id, String position) {
        this.id = id;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @XmlTransient
    public Collection<SloginUsers> getSloginUsersCollection() {
        return sloginUsersCollection;
    }

    public void setSloginUsersCollection(Collection<SloginUsers> sloginUsersCollection) {
        this.sloginUsersCollection = sloginUsersCollection;
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
        if (!(object instanceof SpositionsOffense)) {
            return false;
        }
        SpositionsOffense other = (SpositionsOffense) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.SpositionsOffense[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Snumber> getSnumberList() {
        return snumberCollection;
    }

    public void setSnumberList(List<Snumber> snumberCollection) {
        this.snumberCollection = snumberCollection;
    }

    public StimeSegmento getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(StimeSegmento idSegmento) {
        this.idSegmento = idSegmento;
    }
    
}
