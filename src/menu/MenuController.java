/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * FXML Controller class
 *
 * @author gabri
 */
public class MenuController implements Initializable {

    @FXML
    private AnchorPane anchor_pane_menu;
     
    @FXML
    private Pane area_menu;  
    
    @FXML
    private Label label_info;
    
    /**
     * Initializes the controller class.
     */

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        Info();
    }     
    
    public void Info(){
        String s = System.getProperty("user.name");
        
        ZonedDateTime dateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        label_info.setText("Usuário: " + s + " - " + "Data: " + dateTime.format( formatter));
    }

    @FXML
    private void Minimizar(MouseEvent event) {
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @FXML
    private void Sair(MouseEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void menu(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/Menu.fxml"));
        anchor_pane_menu.getChildren().removeAll();
        anchor_pane_menu.getChildren().setAll(fxml);  
    }   

    @FXML
    private void graficos(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/VendasGraficos.fxml"));
        area_menu.getChildren().removeAll();
        area_menu.getChildren().setAll(fxml);  
    }   
    
    @FXML
    private void clientes(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/Clientes.fxml"));
        area_menu.getChildren().removeAll();
        area_menu.getChildren().setAll(fxml);  
    }

    @FXML
    private void fornecedores(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/Fornecedores.fxml"));
        area_menu.getChildren().removeAll();
        area_menu.getChildren().setAll(fxml);  
    }

    @FXML
    private void produtos(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/Produtos.fxml"));
        area_menu.getChildren().removeAll();
        area_menu.getChildren().setAll(fxml);  
    }   
    
    @FXML
    private void estoque(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/Estoque.fxml"));
        area_menu.getChildren().removeAll();
        area_menu.getChildren().setAll(fxml);  
    }

    @FXML
    private void vendas(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/Vendas.fxml"));
        area_menu.getChildren().removeAll();
        area_menu.getChildren().setAll(fxml);  
    }      
}
