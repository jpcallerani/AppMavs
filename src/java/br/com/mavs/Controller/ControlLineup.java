package br.com.mavs.Controller;

import br.com.mavs.Modal.SloginUsers;
import java.util.List;

public class ControlLineup {

    /**
     * 
     * @param p_usuarios
     * @param p_posicao
     * @return 
     */
    public SloginUsers retornaMelhorColocado(List<SloginUsers> p_usuarios, String p_posicao) {
        SloginUsers melhor_usuario = null;
        for (SloginUsers usuario : p_usuarios) {
            if (usuario.getPosicao().getPosition().equalsIgnoreCase(p_posicao)) {
                if (melhor_usuario == null) {
                    melhor_usuario = usuario;
                } else {
                    // Verifica se o usuário possui porcentagem de presença maior.
                    if (usuario.getPresenca().getTreino_presentes_porcentagem() > melhor_usuario.getPresenca().getTreino_presentes_porcentagem()) {
                        if (usuario.getPresenca().getTreino_presentes() >= 8) {
                            melhor_usuario = usuario;
                        } else {
                            if (melhor_usuario.getPresenca().getTreino_presentes() < 8) {
                                if (usuario.getPresenca().getTreino_presentes() > melhor_usuario.getPresenca().getTreino_presentes()) {
                                    melhor_usuario = usuario;
                                }
                            }
                        }
                    } else if (usuario.getPresenca().getTreino_presentes_porcentagem() == melhor_usuario.getPresenca().getTreino_presentes_porcentagem()) {
                        if (usuario.getPresenca().getTreino_presentes_porcentagem_mes() > melhor_usuario.getPresenca().getTreino_presentes_porcentagem_mes()) {
                            melhor_usuario = usuario;
                        }
                    }
                }
            }
        }
        return melhor_usuario;
    }
}
