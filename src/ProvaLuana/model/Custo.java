/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProvaLuana.model;



/**
 *
 * @author Luana Mora
 */
public class Custo {
    private int codigo;
    private int codigoDestino;
    private String descricao;
    private int tipo;
    private float valor;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public  int getCodigoDestino() {
        return codigoDestino;
    }

    public void setCodigoDestino(int codigoDescricao) {
        this.codigoDestino = codigoDescricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
                
    
}
