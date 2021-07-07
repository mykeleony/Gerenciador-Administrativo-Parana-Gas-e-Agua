/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author gabri
 */
public class Produtos {
      
    int codigo, quantidade;
    
    String descricao, marca, fornecedor, data_cadastro;
    
    float preco_custo, preco_venda;

    public Produtos(){
        
    }
    
    public Produtos(int codigo) {
        this.codigo = codigo;
    }

    public Produtos(int codigo, String descricao, String marca, String fornecedor, float preco_custo, float preco_venda, int quantidade, String data_cadastro) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.marca = marca;
        this.fornecedor = fornecedor;
        this.preco_custo = preco_custo;
        this.preco_venda = preco_venda;
        this.quantidade = quantidade;
        this.data_cadastro = data_cadastro;
    }

    public Produtos(String descricao, String marca, String fornecedor, float preco_custo, float preco_venda, int quantidade, String data_cadastro) {        
        this.descricao = descricao;
        this.marca = marca;
        this.fornecedor = fornecedor;
        this.preco_custo = preco_custo;
        this.preco_venda = preco_venda;
        this.quantidade = quantidade;
        this.data_cadastro = data_cadastro;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    } 

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public float getPreco_custo() {
        return preco_custo;
    }

    public void setPreco_custo(float preco_custo) {
        this.preco_custo = preco_custo;
    }

    public float getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(float preco_venda) {
        this.preco_venda = preco_venda;
    }

    public String getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(String data_cadastro) {
        this.data_cadastro = data_cadastro;
    }  

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return getDescricao();
    }    
}
