/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author i16633i
 */
public class conexao {
    
     public Connection getConnection()  
     {   
         //Tratamento de erros
         try  
         {    
            //Retorna a conexão do banco através do mysql-connector
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/tcc_final","root","");           
         }  
         //Tratamento de erros
         catch(SQLException excecao)  
         {              
             throw new RuntimeException(excecao);          
         }      
     } 
}
