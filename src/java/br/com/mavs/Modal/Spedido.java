/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavs.Modal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author JP
 */
@Entity
@Table(name = "spedido")
@XmlRootElement
public class Spedido implements Serializable {
    @Basic(optional = false)
    @Column(name = "valor")
    private float valor;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "observacao")
    private String observacao;
    @JoinColumn(name = "id_status", referencedColumnName = "id")
    @ManyToOne
    private Sstatus idStatus;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "idPedido")
    private List<SitemPedido> sitemPedidoList;
    @JoinColumn(name = "id_usuario", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private SloginUsers idUsuario;

    public Spedido() {
    }

    public Spedido(Integer id) {
        this.id = id;
    }

    public Spedido(Integer id, Date data) {
        this.id = id;
        this.data = data;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Sstatus getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Sstatus idStatus) {
        this.idStatus = idStatus;
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
        if (!(object instanceof Spedido)) {
            return false;
        }
        Spedido other = (Spedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.Spedido[ id=" + id + " ]";
    }

    public List<SitemPedido> getSitemPedidoList() {
        return sitemPedidoList;
    }

    public void setSitemPedidoList(List<SitemPedido> sitemPedidoList) {
        this.sitemPedidoList = sitemPedidoList;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

}
