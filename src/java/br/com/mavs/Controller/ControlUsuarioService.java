/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavs.Controller;

import br.com.mavs.Modal.Usuario;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

/**
 *
 * @author jopaulo
 */
public class ControlUsuarioService {

    public void authFacebookLogin(String accessToken, int expires) {

        try {
            FacebookClient fb = new DefaultFacebookClient(accessToken);
            Usuario user = fb.fetchObject("me", Usuario.class);
            System.out.println(user);
        } catch (Throwable ex) {
            throw new RuntimeException(
                    "failed login", ex
            );
        }
    }
}
