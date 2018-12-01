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
@Table(name = "sjogador_comentario")
@XmlRootElement
public class SjogadorComentario implements Serializable {
    @JoinColumn(name = "id_segmento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private StimeSegmento idSegmento;

    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "comentario")
    private String comentario;
    @JoinColumn(name = "id_jogador", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private SloginUsers idJogador;

    public SjogadorComentario() {
    }

    public SjogadorComentario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
        if (!(object instanceof SjogadorComentario)) {
            return false;
        }
        SjogadorComentario other = (SjogadorComentario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.SjogadorComentario[ id=" + id + " ]";
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public StimeSegmento getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(StimeSegmento idSegmento) {
        this.idSegmento = idSegmento;
    }

}
