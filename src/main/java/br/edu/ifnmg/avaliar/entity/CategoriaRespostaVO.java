package br.edu.ifnmg.avaliar.entity;

import java.util.List;

public class CategoriaRespostaVO {

    private Categoria categoria;
    private List<Resposta> respostas;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }
    
    
    
}
