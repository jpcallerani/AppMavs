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
@Table(name = "stime")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stime.findAll", query = "SELECT s FROM Stime s"),
    @NamedQuery(name = "Stime.findById", query = "SELECT s FROM Stime s WHERE s.id = :id"),
    @NamedQuery(name = "Stime.findByNomeTime", query = "SELECT s FROM Stime s WHERE s.nomeTime = :nomeTime")})
public class Stime implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome_time")
    private String nomeTime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTime")
    private Collection<StimeSegmento> stimeSegmentoCollection;

    public Stime() {
    }

    public Stime(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeTime() {
        return nomeTime;
    }

    public void setNomeTime(String nomeTime) {
        this.nomeTime = nomeTime;
    }

    @XmlTransient
    public Collection<StimeSegmento> getStimeSegmentoCollection() {
        return stimeSegmentoCollection;
    }

    public void setStimeSegmentoCollection(Collection<StimeSegmento> stimeSegmentoCollection) {
        this.stimeSegmentoCollection = stimeSegmentoCollection;
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
        if (!(object instanceof Stime)) {
            return false;
        }
        Stime other = (Stime) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.Stime[ id=" + id + " ]";
    }
    
}
