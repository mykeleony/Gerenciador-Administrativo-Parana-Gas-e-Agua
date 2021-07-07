/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.crud;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Administrador;
import static tcc.TCC.stage;

/**
 *
 * @author Pichau
 */
public class LoginController implements Initializable {
    
    @FXML
    private AnchorPane parent;

    @FXML
    private Pane area_registro;
    
    private double x = 0, y = 0;
    
    Boolean senha_visivel = false;

    @FXML
    private JFXTextField txt_usuario;

    @FXML
    private JFXPasswordField txt_senha;

    @FXML
    private JFXTextField txt_mostrar_senha;

    @FXML
    private MaterialDesignIconView icon_exibir;

    @FXML
    private JFXCheckBox lembrar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       arrastar();
       lembrar();
    }    
    
    @FXML
    private void Login() throws IOException{
        
        if(txt_mostrar_senha.isVisible()){       
            txt_senha.setText(txt_mostrar_senha.getText());
            txt_senha.setVisible(true);
            txt_mostrar_senha.setVisible(false);        
        }
        
        if(txt_usuario.getText().isEmpty() || txt_senha.getText().isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);   
            alert.setTitle("Alerta");
            alert.setHeaderText("Não foi possível efetuar o login!");
            alert.setContentText("Por favor, preencha todos os campos!");
            alert.showAndWait();           
        }
            
        else {
                       
            String user = txt_usuario.getText();
            String senha = txt_senha.getText();

            Administrador u = new Administrador(senha, user);
            
            crud crud1 = new crud();
            crud1.autenticar(u);
            if(crud1.resultado){
                Platform.setImplicitExit(false);
                stage.close();
                
                if(lembrar.isSelected()){
                    crud1.lembrar(u);   
                }
                else{
                   crud1.naolembrar(u);  
                }
                
            }  
            else {
                txt_usuario.setText("");
                txt_senha.setText(""); 
            }
        }
    }
    
    @FXML
    private void sair(){ 
        System.exit(0);
    }
    
    @FXML
    private void minimizar(){ 
        TCC.stage.setIconified(true);
    } 
    
    private void arrastar(){
        parent.setOnMousePressed((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        
        parent.setOnMouseDragged((event) -> {  
           TCC.stage.setX(event.getScreenX()-x);
           TCC.stage.setY(event.getScreenY()-y);
           TCC.stage.setOpacity(0.9f);
        });
        
        parent.setOnDragDone((event) -> {
            TCC.stage.setOpacity(1.0f);
        });
        
         parent.setOnMouseReleased((event) -> {
            TCC.stage.setOpacity(1.0f);
        });
    }
    
    @FXML
    private void registrar() throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/tcc/Registro.fxml"));
        area_registro.getChildren().removeAll();
        area_registro.getChildren().setAll(fxml);   
    }
    
    @FXML
    private void alterar_senha() throws IOException{
        Parent fxml = FXMLLoader.load(getClass().getResource("/tcc/Alterar_Senha.fxml"));
        area_registro.getChildren().removeAll();
        area_registro.getChildren().setAll(fxml);
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
    private void lembrar(){
        
        Administrador adm = new Administrador(null, null);
        
        crud dao = new crud();
        txt_usuario.setText(dao.preencherlembrar(adm));
    }
}
