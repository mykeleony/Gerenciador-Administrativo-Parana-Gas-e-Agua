/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import controller.conexao;
import controller.crud;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javax.swing.JOptionPane;
import model.Clientes;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class TabelaClientesController implements Initializable {
    @FXML
    private TableView<Clientes> tabela_clientes;
 
    @FXML
    private TableColumn<Clientes, String> col_cpf;
 
    @FXML
    private TableColumn<Clientes, String> col_nome;
 
    @FXML
    private TableColumn<Clientes, String> col_email;
 
    @FXML
    private TableColumn<Clientes, String> col_endereco;
 
    @FXML
    private TableColumn<Clientes, String> col_numero;
 
    @FXML
    private TableColumn<Clientes, String> col_complemento;
 
    @FXML
    private TableColumn<Clientes, String> col_bairro;
 
    @FXML
    private TableColumn<Clientes, String> col_cidade;
 
    @FXML
    private TableColumn<Clientes, String> col_telefone_fixo;
 
    @FXML
    private TableColumn<Clientes, String> col_telefone_celular;
    
    @FXML
    private TableColumn<Clientes, String> col_data_cadastro;
    
    @FXML
    private TextField pesquisa;
   
    Connection connection;
   
    ObservableList<Clientes> lista = FXCollections.observableArrayList();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {                               
        try{                    
            this.connection = new conexao().getConnection();  

            ResultSet rs = connection.createStatement().executeQuery("select * from clientes");
            
            while (rs.next()){
                lista.add(new Clientes(rs.getString("cpf"), rs.getString("nome"), rs.getString("email"), rs.getString("endereco"), rs.getString("numero"), rs.getString("complemento"), rs.getString("bairro"), rs.getString("cidade"), rs.getString("telefone_fixo"), rs.getString("telefone_celular"), rs.getString("data_cadastro")));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(TabelaClientesController.class.getName()).log(Level.SEVERE, null,ex);
            JOptionPane.showMessageDialog(null,ex);               
        }
 
        col_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        col_numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        col_complemento.setCellValueFactory(new PropertyValueFactory<>("complemento"));
        col_bairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        col_cidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        col_telefone_fixo.setCellValueFactory(new PropertyValueFactory<>("telefone_fixo"));
        col_telefone_celular.setCellValueFactory(new PropertyValueFactory<>("telefone_celular"));
        col_data_cadastro.setCellValueFactory(new PropertyValueFactory<>("data_cadastro"));
        editableCols();
        tabela_clientes.setItems(lista);
        
        tabela_clientes.requestFocus();
        tabela_clientes.getSelectionModel().select(0);
        tabela_clientes.getFocusModel().focus(0);       
        
        ObservableList data =  tabela_clientes.getItems();
        pesquisa.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tabela_clientes.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Clientes> subentries = FXCollections.observableArrayList();

            long count = tabela_clientes.getColumns().stream().count();
            for (int i = 0; i < tabela_clientes.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tabela_clientes.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tabela_clientes.getItems().get(i));
                        break;
                    }
                }
            }
            tabela_clientes.setItems(subentries);
        });
    } 

    private void editableCols(){                
        col_nome.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_nome.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setNome(e.getNewValue());
        });
        
        col_email.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_email.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
        });
        
        col_endereco.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_endereco.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEndereco(e.getNewValue());
        });
        
        col_numero.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_numero.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setNumero(e.getNewValue());
        });
        
        col_complemento.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_complemento.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setComplemento(e.getNewValue());
        });
        
        col_bairro.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_bairro.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setBairro(e.getNewValue());
        });
        
        col_cidade.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_cidade.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCidade(e.getNewValue());
        });
        
        col_telefone_fixo.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_telefone_fixo.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTelefone_fixo(e.getNewValue());
        });
        
        col_telefone_celular.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_telefone_celular.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTelefone_celular(e.getNewValue());  
        });
        
        col_data_cadastro.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_data_cadastro.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setData_cadastro(e.getNewValue());  
        });                
    }
    
    @FXML
    void Deletar() {   
        
        ObservableList<Clientes> items = tabela_clientes.getItems();

        if (items.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Não foi possível excluir!");
            alert.setContentText("Não há conteúdo na tabela!");
            alert.showAndWait();
        } 
        else {
            Clientes cliente = new Clientes(tabela_clientes.getSelectionModel().getSelectedItem().getCpf(), null, null, null, null, null, null, null, null, null, null);
        
            crud dao = new crud();

            dao.DeletarClientes(cliente);

            ObservableList<Clientes> Alltabela_clientes, Singletabela_clientes;
            Alltabela_clientes = tabela_clientes.getItems();
            Singletabela_clientes = tabela_clientes.getSelectionModel().getSelectedItems();
            Singletabela_clientes.forEach(Alltabela_clientes::remove);
        }
    }
    
    @FXML
    void Atualizar() {  

        ObservableList<Clientes> items = tabela_clientes.getItems();

        if (items.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Não foi possível atualizar!");
            alert.setContentText("Não há conteúdo na tabela!");
            alert.showAndWait();
        } 
        else {
            Clientes cliente = new Clientes(tabela_clientes.getSelectionModel().getSelectedItem().getCpf(), tabela_clientes.getSelectionModel().getSelectedItem().getNome(), 
                                            tabela_clientes.getSelectionModel().getSelectedItem().getEmail(), tabela_clientes.getSelectionModel().getSelectedItem().getEndereco(), 
                                            tabela_clientes.getSelectionModel().getSelectedItem().getNumero(), tabela_clientes.getSelectionModel().getSelectedItem().getComplemento(), 
                                            tabela_clientes.getSelectionModel().getSelectedItem().getBairro(), tabela_clientes.getSelectionModel().getSelectedItem().getCidade(), 
                                            tabela_clientes.getSelectionModel().getSelectedItem().getTelefone_fixo(), tabela_clientes.getSelectionModel().getSelectedItem().getTelefone_celular(), 
                                            tabela_clientes.getSelectionModel().getSelectedItem().getData_cadastro());

            crud dao = new crud();

            dao.AtualizarClientes(cliente);         
        }                
        /*tabela_clientes.requestFocus();
        tabela_clientes.getSelectionModel().select(tabela_clientes.getSelectionModel().getSelectedIndex());
        tabela_clientes.getFocusModel().focus(tabela_clientes.getSelectionModel().getSelectedIndex());*/        
    }   
    
    public void handleImprimir() throws JRException{
        URL url = getClass().getResource("/relatorios/RelatorioClientes.jasper");
        
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
        
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        
        jasperViewer.setVisible(true);
    }
}

