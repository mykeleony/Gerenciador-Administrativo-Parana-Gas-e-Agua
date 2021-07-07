/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.crud;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Fornecedores;
import model.Produtos;
import model.TextFieldFormatter;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class ProdutosController implements Initializable {

    
    @FXML
    private Pane area_cadastro3;  
    
    @FXML
    private AnchorPane anchor_pane3;

    @FXML
    private Label label_info;  

    @FXML
    private JFXTextField codigo;

    @FXML
    private JFXTextField descricao;

    @FXML
    private JFXTextField marca;

    @FXML
    private JFXComboBox<Fornecedores> fornecedor;
    
    @FXML
    private JFXTextField preco_custo;

    @FXML
    private JFXTextField preco_venda;
    
    @FXML
    private JFXTextField quantidade;

    @FXML
    private JFXTextField data_cadastro;

    @FXML
    void data_cadastro_mascara(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##/##/####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(data_cadastro);
        tff.formatter();
    }

    /**
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {                    
        Info();
                  
        List<Fornecedores> data = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tcc_final","root","");
          
            // Execute query and store result in a resultset
            ResultSet rs = conn.createStatement().executeQuery("SELECT nome FROM fornecedores;");
            while (rs.next()) {
                //get string from db,whichever way 
                data.add(new Fornecedores(rs.getString("nome")));
            }           
        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
        ObservableList<Fornecedores> obsdata = FXCollections.observableArrayList(data);        
        fornecedor.setItems(obsdata);
        
        quantidade.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    quantidade.setText(oldValue);
                }
            }
        });   
    } 
    
    public void Info(){
        String s = System.getProperty("user.name");
        
        ZonedDateTime dateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        label_info.setText("Usuário: " + s + " - " + "Data: " + dateTime.format( formatter));
    }
    
    Alert alert = new Alert(Alert.AlertType.WARNING);
    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
    
    @FXML
    void cadastrar(MouseEvent event) throws IOException {

        String errorMessage = "";
        
        Produtos produto = new Produtos(descricao.getText(), marca.getText(), String.valueOf(fornecedor.getValue()), Float.parseFloat(preco_custo.getText()), Float.parseFloat(preco_venda.getText()), Integer.parseInt(quantidade.getText()), data_cadastro.getText());
        
        if (descricao.getText().isEmpty()) {
            errorMessage += "Descrição inválida!\n";
        }
        if (marca.getText().isEmpty()) {
            errorMessage += "Marca inválida!\n";
        }
        if (fornecedor.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Fornecedor inválido!\n";
        }
        if (preco_custo.getText().isEmpty()) {
            errorMessage += "Preço de custo inválido!\n";
        } 
        if (preco_venda.getText().isEmpty()) {
            errorMessage += "Preço de venda inválido!\n";
        }     
        if (quantidade.getText().isEmpty()) {
            errorMessage += "Quantidade inválida!\n";
        }  
        if (data_cadastro.getText().isEmpty()) {
            errorMessage += "Data de Cadastro inválida!\n";
        }     
        if (errorMessage.length() == 0) {          
            crud dao = new crud();

            dao.ValidarProdutos(produto);
            if (dao.resultado) {
                alert.setTitle("Alerta");
                alert.setHeaderText("Não foi possível realizar o cadastro!");
                alert.setContentText("Produto já existente!");
                alert.showAndWait();
            } else {
                dao.InserirProdutos(produto);
                alert2.setTitle("Confirmação");
                alert2.setHeaderText("Cadastro realizado com sucesso!");
                alert2.showAndWait();
            }      
            descricao.setText("");
            marca.setText("");
            fornecedor.setValue(null);
            preco_custo.setText("");
            preco_venda.setText("");
            quantidade.setText("");
            data_cadastro.setText("");
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
        }  
    }
    
    @FXML
    void limpar(MouseEvent event) throws IOException {
        descricao.setText("");
        marca.setText("");
        fornecedor.setValue(null);
        preco_custo.setText("");
        preco_venda.setText("");
        quantidade.setText("");
        data_cadastro.setText("");
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
    private void adicionar_produtos(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/Produtos.fxml"));
        anchor_pane3.getChildren().removeAll();
        anchor_pane3.getChildren().setAll(fxml);  
    }
    
    @FXML
    private void tabela_produtos(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/TabelaProdutos.fxml"));
        area_cadastro3.getChildren().removeAll();
        area_cadastro3.getChildren().setAll(fxml);  
    }
    
}
