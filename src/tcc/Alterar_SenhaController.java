/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.crud;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import model.Administrador;

/**
 * FXML Controller class
 *
 * @author i16633i
 */
public class Alterar_SenhaController implements Initializable {

    @FXML
    private JFXTextField txt_user;

    @FXML
    private JFXPasswordField txt_senha;

    @FXML
    private JFXPasswordField txt_confirmasenha;

    @FXML
    private JFXTextField txt_mostrar_senha;

    @FXML
    private JFXTextField txt_mostrar_confirmasenha;
    
    Boolean senha_visivel = false;
    
    Boolean confirmasenha_visivel = false;
    
    Alert alert = new Alert(Alert.AlertType.WARNING);
    
    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void sair() {
        System.exit(0);
    }

    @FXML
    private void minimizar() {
        TCC.stage.setIconified(true);
    }

    @FXML
    private void voltar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/tcc/Login.fxml"));
        TCC.stage.getScene().setRoot(root);
    }
    
    @FXML
    private void exibir_senha(){        
        if(senha_visivel == false){
            txt_mostrar_senha.setText(txt_senha.getText());
            txt_mostrar_senha.setVisible(true);
            txt_senha.setVisible(false);
            txt_mostrar_senha.requestFocus();
            txt_mostrar_senha.selectEnd();
            senha_visivel = true;
        }
        
        else if(senha_visivel == true){
            txt_senha.setText(txt_mostrar_senha.getText());
            txt_senha.setVisible(true);
            txt_mostrar_senha.setVisible(false);
            txt_senha.requestFocus();
            txt_senha.selectEnd();
            senha_visivel = false;
        }
    }
    
    @FXML
    private void exibir_confirmasenha(){        
        if(confirmasenha_visivel == false){
            txt_mostrar_confirmasenha.setText(txt_confirmasenha.getText());
            txt_mostrar_confirmasenha.setVisible(true);
            txt_confirmasenha.setVisible(false);
            txt_mostrar_confirmasenha.requestFocus();
            txt_mostrar_confirmasenha.selectEnd();
            confirmasenha_visivel = true;
        }
        
        else if(confirmasenha_visivel == true){
            txt_confirmasenha.setText(txt_mostrar_confirmasenha.getText());
            txt_confirmasenha.setVisible(true);
            txt_mostrar_confirmasenha.setVisible(false);
            txt_confirmasenha.requestFocus();
            txt_confirmasenha.selectEnd();
            confirmasenha_visivel = false;
        }
    }
    
    @FXML
    private void alterar() throws IOException{        
        if(txt_mostrar_senha.isVisible()){
            txt_senha.setText(txt_mostrar_senha.getText());
            txt_senha.setVisible(true);
            txt_mostrar_senha.setVisible(false);      
        }
        
        if(txt_mostrar_confirmasenha.isVisible()){
            txt_confirmasenha.setText(txt_mostrar_confirmasenha.getText());
            txt_confirmasenha.setVisible(true);
            txt_mostrar_confirmasenha.setVisible(false);      
        }                

        if (txt_user.getText().isEmpty() || txt_senha.getText().isEmpty() || txt_confirmasenha.getText().isEmpty()) {
            alert.setTitle("Alerta");
            alert.setHeaderText("Não foi possível efetuar o cadastro!");
            alert.setContentText("Por favor, preencha todos os campos!");
            alert.showAndWait();
        }   
        
        else if (txt_senha.getText().equals(txt_confirmasenha.getText())) {
            Administrador adm = new Administrador(txt_senha.getText(), txt_user.getText());
            adm.setConfirmasenha(txt_confirmasenha.getText());

            crud dao = new crud();

            dao.validar(adm);
            
            if (dao.resultado) {
                dao.AlterarSenha(adm);
                alert2.setTitle("Alerta");
                alert2.setHeaderText("Alteração de senha realizada com sucesso!");
                alert2.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/tcc/Login.fxml"));
                TCC.stage.getScene().setRoot(root);
            }   
            
            else {
                alert.setTitle("Alerta");
                alert.setHeaderText("Não foi possível realizar a alteração de senha!");
                alert.setContentText("Usuário inexistente!");
                alert.showAndWait();
            }
        }       
    }
}
