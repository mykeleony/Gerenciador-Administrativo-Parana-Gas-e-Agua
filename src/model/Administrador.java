/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author i16633i
 */
public class Administrador {
    
    private String senha, usuario, confirmasenha;

    public Administrador(String senha, String usuario) {
        this.senha = senha;
        this.usuario = usuario;
    }

    public Administrador(String usuario) {
        this.usuario = usuario;
    }
        
    public String getConfirmasenha() {
        return confirmasenha;
    }

    public void setConfirmasenha(String confirmasenha) {
        this.confirmasenha = confirmasenha;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return getUsuario();
    } 
}
