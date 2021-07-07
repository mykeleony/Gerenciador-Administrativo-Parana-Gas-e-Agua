/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author gabri
 */
public class Vendas {
    
    int codigo_ven;
    LocalDate data;
    double valor;
    private List<ItemDeVenda> itensDeVenda;
    Clientes cliente;

    public Vendas() {
        
    }
    
    public Vendas(int codigo_ven, LocalDate data, double valor) {
        this.codigo_ven = codigo_ven;
        this.data = data;
        this.valor = valor;
    }

    public int getCodigo_ven() {
        return codigo_ven;
    }

    public void setCodigo_ven(int codigo_ven) {
        this.codigo_ven = codigo_ven;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public List<ItemDeVenda> getItensDeVenda() {
        return itensDeVenda;
    }

    public void setItensDeVenda(List<ItemDeVenda> itensDeVenda) {
        this.itensDeVenda = itensDeVenda;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

}
