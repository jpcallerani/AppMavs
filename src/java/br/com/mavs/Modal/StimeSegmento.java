/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.mavs.Modal;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author U0180463
 */
@Entity
@Table(name = "stime_segmento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StimeSegmento.findAll", query = "SELECT s FROM StimeSegmento s"),
    @NamedQuery(name = "StimeSegmento.findById", query = "SELECT s FROM StimeSegmento s WHERE s.id = :id"),
    @NamedQuery(name = "StimeSegmento.findByNomeSegmento", query = "SELECT s FROM StimeSegmento s WHERE s.nomeSegmento = :nomeSegmento")})
public class StimeSegmento implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSegmento")
    private Collection<Smensalidade> smensalidadeCollection;
    @OneToMany(mappedBy = "idSegmento")
    private Collection<Streino> streinoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSegmento")
    private Collection<SjogadorComentario> sjogadorComentarioCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome_segmento")
    private String nomeSegmento;
    @JoinColumn(name = "id_time", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stime idTime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSegmento")
    private Collection<SpositionsOffense> spositionsOffenseCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSegmento")
    private Collection<SloginUsers> sloginUsersCollection;

    public StimeSegmento() {
    }

    public StimeSegmento(Integer id) {
        this.id = id;
    }

    public StimeSegmento(Integer id, String nomeSegmento) {
        this.id = id;
        this.nomeSegmento = nomeSegmento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeSegmento() {
        return nomeSegmento;
    }

    public void setNomeSegmento(String nomeSegmento) {
        this.nomeSegmento = nomeSegmento;
    }

    public Stime getIdTime() {
        return idTime;
    }

    public void setIdTime(Stime idTime) {
        this.idTime = idTime;
    }

    @XmlTransient
    public Collection<SpositionsOffense> getSpositionsOffenseCollection() {
        return spositionsOffenseCollection;
    }

    public void setSpositionsOffenseCollection(Collection<SpositionsOffense> spositionsOffenseCollection) {
        this.spositionsOffenseCollection = spositionsOffenseCollection;
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
        if (!(object instanceof StimeSegmento)) {
            return false;
        }
        StimeSegmento other = (StimeSegmento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.StimeSegmento[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<SjogadorComentario> getSjogadorComentarioCollection() {
        return sjogadorComentarioCollection;
    }

    public void setSjogadorComentarioCollection(Collection<SjogadorComentario> sjogadorComentarioCollection) {
        this.sjogadorComentarioCollection = sjogadorComentarioCollection;
    }

    @XmlTransient
    public Collection<Streino> getStreinoCollection() {
        return streinoCollection;
    }

    public void setStreinoCollection(Collection<Streino> streinoCollection) {
        this.streinoCollection = streinoCollection;
    }

    @XmlTransient
    public Collection<Smensalidade> getSmensalidadeCollection() {
        return smensalidadeCollection;
    }

    public void setSmensalidadeCollection(Collection<Smensalidade> smensalidadeCollection) {
        this.smensalidadeCollection = smensalidadeCollection;
    }
    
}
