package br.com.mavs.Modal;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Spresenca implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<SjogadorTreino> _jogador_treinos;
    private double _treino_presentes = 0;
    private double _treino_faltas = 0;
    private double _treino_total = 0;
    private double _treino_presentes_mes = 0;
    private double _treino_faltas_mes = 0;
    private double _treino_total_mes = 0;
    private double _treino_presentes_porcentagem = 0;
    private double _treino_faltas_porcentagem = 0;
    private double _treino_presentes_porcentagem_mes = 0;
    private double _treino_faltas_porcentagem_mes = 0;
    private String _treino_imagem = "";
    private String _treino_imagem_mes = "";

    public Spresenca(List<SjogadorTreino> p_jogador_treinos) {
        this._jogador_treinos = p_jogador_treinos;
        calcTreinos();
    }

    /**
     * Função para calculo de presença e faltas.
     */
    private void calcTreinos() {
        Calendar atual = new GregorianCalendar();
        atual.setTime(new Date());
        Calendar banco = new GregorianCalendar();
        try {
            for (SjogadorTreino sjogadorTreino : _jogador_treinos) {
                banco.setTime(sjogadorTreino.getIdTreino().getData());
                if (sjogadorTreino.getPresente().equalsIgnoreCase("S")) {
                    if (atual.get(Calendar.MONTH) == banco.get(Calendar.MONTH)) {
                        this._treino_presentes_mes++;
                    }
                    this._treino_presentes++;
                } else {
                    if (atual.get(Calendar.MONTH) == banco.get(Calendar.MONTH)) {
                        this._treino_faltas_mes++;
                    }
                    this._treino_faltas++;
                }
                if (atual.get(Calendar.MONTH) == banco.get(Calendar.MONTH)) {
                    this._treino_total_mes++;
                }
                this._treino_total++;
            }

            this._treino_presentes_porcentagem = (this._treino_presentes / this._treino_total) * 100;
            this._treino_faltas_porcentagem = (this._treino_faltas / this._treino_total) * 100;

            if (this._treino_presentes_porcentagem < 33) {
                this._treino_imagem = "resources/images/vermelha.png";
            } else if (this._treino_presentes_porcentagem > 33 && this._treino_presentes_porcentagem < 66) {
                this._treino_imagem = "resources/images/amarela.png";
            } else {
                this._treino_imagem = "resources/images/verde.png";
            }
            //
            this._treino_presentes_porcentagem_mes = (this._treino_presentes_mes / this._treino_total_mes) * 100;
            this._treino_faltas_porcentagem_mes = (this._treino_faltas_mes / this._treino_total_mes) * 100;

            if (this._treino_presentes_porcentagem_mes < 33) {
                this._treino_imagem_mes = "resources/images/vermelha.png";
            } else if (this._treino_presentes_porcentagem_mes > 33 && this._treino_presentes_porcentagem_mes < 66) {
                this._treino_imagem_mes = "resources/images/amarela.png";
            } else {
                this._treino_imagem_mes = "resources/images/verde.png";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<SjogadorTreino> getJogador_treinos() {
        return _jogador_treinos;
    }

    public void setJogador_treinos(List<SjogadorTreino> _jogador_treinos) {
        this._jogador_treinos = _jogador_treinos;
    }
    
    public double getTreino_presentes() {
        return _treino_presentes;
    }

    public void setTreino_presentes(double _treino_presentes) {
        this._treino_presentes = _treino_presentes;
    }

    public double getTreino_faltas() {
        return _treino_faltas;
    }

    public void setTreino_faltas(double _treino_faltas) {
        this._treino_faltas = _treino_faltas;
    }

    public double getTreino_total() {
        return _treino_total;
    }

    public void setTreino_total(double _treino_total) {
        this._treino_total = _treino_total;
    }

    public double getTreino_presentes_porcentagem() {
        return _treino_presentes_porcentagem;
    }

    public void setTreino_presentes_porcentagem(double _treino_presentes_porcentagem) {
        this._treino_presentes_porcentagem = _treino_presentes_porcentagem;
    }

    public double getTreino_faltas_porcentagem() {
        return _treino_faltas_porcentagem;
    }

    public void setTreino_faltas_porcentagem(double _treino_faltas_porcentagem) {
        this._treino_faltas_porcentagem = _treino_faltas_porcentagem;
    }

    public String getTreino_imagem() {
        return _treino_imagem;
    }

    public void setTreino_imagem(String _treino_imagem) {
        this._treino_imagem = _treino_imagem;
    }

    public double getTreino_presentes_mes() {
        return _treino_presentes_mes;
    }

    public void setTreino_presentes_mes(double _treino_presentes_mes) {
        this._treino_presentes_mes = _treino_presentes_mes;
    }

    public double getTreino_faltas_mes() {
        return _treino_faltas_mes;
    }

    public void setTreino_faltas_mes(double _treino_faltas_mes) {
        this._treino_faltas_mes = _treino_faltas_mes;
    }

    public double getTreino_total_mes() {
        return _treino_total_mes;
    }

    public void setTreino_total_mes(double _treino_total_mes) {
        this._treino_total_mes = _treino_total_mes;
    }

    public String getTreino_imagem_mes() {
        return _treino_imagem_mes;
    }

    public void setTreino_imagem_mes(String _treino_imagem_mes) {
        this._treino_imagem_mes = _treino_imagem_mes;
    }

    public double getTreino_presentes_porcentagem_mes() {
        return _treino_presentes_porcentagem_mes;
    }

    public void setTreino_presentes_porcentagem_mes(double _treino_presentes_porcentagem_mes) {
        this._treino_presentes_porcentagem_mes = _treino_presentes_porcentagem_mes;
    }

    public double getTreino_faltas_porcentagem_mes() {
        return _treino_faltas_porcentagem_mes;
    }

    public void setTreino_faltas_porcentagem_mes(double _treino_faltas_porcentagem_mes) {
        this._treino_faltas_porcentagem_mes = _treino_faltas_porcentagem_mes;
    }
}
