/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import com.jfoenix.controls.JFXTextField;
import controller.crud;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.TextFieldFormatter;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class FornecedoresController implements Initializable {

    @FXML
    private Pane area_cadastro2;
    
    @FXML
    private AnchorPane anchor_pane2;
    
    @FXML
    private Label label_info;

    @FXML
    private JFXTextField cnpj;

    @FXML
    private JFXTextField nome;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField endereco;

    @FXML
    private JFXTextField numero;

    @FXML
    private JFXTextField complemento;

    @FXML
    private JFXTextField bairro;

    @FXML
    private JFXTextField cidade;

    @FXML
    private JFXTextField telefone_fixo;

    @FXML
    private JFXTextField telefone_celular;
    
    @FXML
    private JFXTextField data_cadastro;
     
    @FXML
    void telefone_celular_mascara(KeyEvent event) {        
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("(##)#####-####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(telefone_celular);
        tff.formatter();
    }
    
    @FXML
    void telefone_fixo_mascara(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("(##)####-####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(telefone_fixo);
        tff.formatter();
    }
    
    @FXML
    void cnpj_mascara(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##.###.###/####-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(cnpj);
        tff.formatter();
    }
    
    @FXML
    void data_cadastro_mascara(KeyEvent event) {
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##/##/####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(data_cadastro);
        tff.formatter();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Info();
        
        numero.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    numero.setText(oldValue);
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
        
        Fornecedores fornecedor = new Fornecedores(cnpj.getText(), nome.getText(), email.getText(), endereco.getText(), numero.getText(), complemento.getText(), bairro.getText(), cidade.getText(), telefone_fixo.getText(), telefone_celular.getText(), data_cadastro.getText());
        
        if (cnpj.getText().isEmpty()) {
            errorMessage += "CNPJ inválido!\n";
        }
        if (nome.getText().isEmpty()) {
            errorMessage += "Nome inválido!\n";
        }
        if (endereco.getText().isEmpty()) {
            errorMessage += "Endereço inválido!\n";
        }
        if (numero.getText().isEmpty()) {
            errorMessage += "Número inválido!\n";
        } 
        if (bairro.getText().isEmpty()) {
            errorMessage += "Bairro inválido!\n";
        } 
        if (cidade.getText().isEmpty()) {
            errorMessage += "Cidade inválida!\n";
        } 
        if (telefone_celular.getText().isEmpty()) {
            errorMessage += "Telefone Celular inválido!\n";
        }
        if (data_cadastro.getText().isEmpty()) {
            errorMessage += "Data de Cadastro inválida!\n";
        }     
        if (errorMessage.length() == 0) {          
            crud dao = new crud();

            dao.ValidarFornecedores(fornecedor);
            if (dao.resultado) {
                alert.setTitle("Alerta");
                alert.setHeaderText("Não foi possível realizar o cadastro!");
                alert.setContentText("Fornecedor já existente!");
                alert.showAndWait();
            } else {
                dao.InserirFornecedores(fornecedor);
                alert2.setTitle("Confirmação");
                alert2.setHeaderText("Cadastro realizado com sucesso!");
                alert2.showAndWait();
            }     
            cnpj.setText("");
            nome.setText("");
            email.setText("");
            endereco.setText("");
            numero.setText("");
            complemento.setText("");
            bairro.setText("");
            cidade.setText("");
            telefone_fixo.setText("");
            telefone_celular.setText("");     
            data_cadastro.setText("");
        }
        else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
        }  
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
    void limpar(MouseEvent event) throws IOException {
        cnpj.setText("");
        nome.setText("");
        email.setText("");
        endereco.setText("");
        numero.setText("");
        complemento.setText("");
        bairro.setText("");
        cidade.setText("");
        telefone_fixo.setText("");
        telefone_celular.setText("");     
        data_cadastro.setText("");
    }
    
    @FXML
    private void adicionar_fornecedores(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/Fornecedores.fxml"));
        anchor_pane2.getChildren().removeAll();
        anchor_pane2.getChildren().setAll(fxml);  
    }
    
    @FXML
    private void tabela_fornecedores(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/menu/TabelaFornecedores.fxml"));
        area_cadastro2.getChildren().removeAll();
        area_cadastro2.getChildren().setAll(fxml);  
    }
}
