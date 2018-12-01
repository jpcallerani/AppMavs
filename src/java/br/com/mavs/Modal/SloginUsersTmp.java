package br.com.mavs.Modal;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "slogin_users_tmp")
@XmlRootElement
public class SloginUsersTmp implements Serializable {
    @Column(name = "Senha")
    private Long senha;
    @Column(name = "posicao")
    private Integer posicao;
    @Column(name = "Number")
    private Integer number;
    @Column(name = "admin")
    private Integer admin;
    @Column(name = "id_segmento")
    private Integer idSegmento;

    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Basic(optional = true)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = true)
    @Column(name = "Usuario")
    private String usuario;
    @Basic(optional = true)
    @Column(name = "Nome")
    private String nome;
    @Basic(optional = true)
    @Column(name = "jersey")
    private String jersey;
    @Basic(optional = true)
    @Column(name = "Data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = true)
    @Column(name = "Email")
    private String email;
    @Column(name = "telefone")
    private String telefone;

    public SloginUsersTmp() {
    }

    public SloginUsersTmp(Integer id) {
        this.id = id;
    }

    public SloginUsersTmp(Integer id, String usuario, long senha, String nome, String jersey, int posicao, int number, Date data, String email, int admin, int idSegmento) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
        this.nome = nome;
        this.jersey = jersey;
        this.posicao = posicao;
        this.number = number;
        this.data = data;
        this.email = email;
        this.admin = admin;
        this.idSegmento = idSegmento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getJersey() {
        return jersey;
    }

    public void setJersey(String jersey) {
        this.jersey = jersey;
    }


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(int idSegmento) {
        this.idSegmento = idSegmento;
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
        if (!(object instanceof SloginUsersTmp)) {
            return false;
        }
        SloginUsersTmp other = (SloginUsersTmp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mavs.Modal.SloginUsersTmp[ id=" + id + " ]";
    }

    public Long getSenha() {
        return senha;
    }

    public void setSenha(Long senha) {
        this.senha = senha;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public void setIdSegmento(Integer idSegmento) {
        this.idSegmento = idSegmento;
    }

}
