package br.com.mavs.Modal;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Jp
 */
@Entity
@Table(name = "sjogador_treino")
@XmlRootElement
public class SjogadorTreino implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "presente")
    private String presente;
    @JoinColumn(name = "id_jogador", referencedColumnName = "ID")
    @ManyToOne
    private SloginUsers idJogador;
    @JoinColumn(name = "id_treino", referencedColumnName = "id")
    @ManyToOne
    private Streino idTreino;

    public SjogadorTreino() {
    }

    public SjogadorTreino(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPresente() {
        return presente;
    }

    public void setPresente(String presente) {
        this.presente = presente;
    }

    public SloginUsers getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(SloginUsers idJogador) {
        this.idJogador = idJogador;
    }

    public Streino getIdTreino() {
        return idTreino;
    }

    public void setIdTreino(Streino idTreino) {
        this.idTreino = idTreino;
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
        if (!(object instanceof SjogadorTreino)) {
            return false;
        }
        SjogadorTreino other = (SjogadorTreino) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.SjogadorTreino[ id=" + id + " ]";
    }

}
