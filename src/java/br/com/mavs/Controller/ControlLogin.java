package br.com.mavs.Controller;

import br.com.mavs.Modal.Usuario;

public class ControlLogin {

    private String login;

    private String senha;

    private Usuario usuario;

    /**
     * 
     */
    public ControlLogin() {
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
