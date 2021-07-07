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
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import javax.swing.JOptionPane;
import model.Produtos;
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
public class TabelaProdutosController implements Initializable {
    @FXML
    private TableView<Produtos> tabela_produtos;
 
    @FXML
    private TableColumn<Produtos, Integer> col_codigo;
 
    @FXML
    private TableColumn<Produtos, String> col_descricao;
 
    @FXML
    private TableColumn<Produtos, String> col_marca;
    
    @FXML
    private TableColumn<Produtos, String> col_fornecedor;
 
    @FXML
    private TableColumn<Produtos, Float> col_preco_custo;
    
    @FXML
    private TableColumn<Produtos, Float> col_preco_venda;
    
    @FXML
    private TableColumn<Produtos, Integer> col_quantidade;
  
    @FXML
    private TableColumn<Produtos, String> col_data_cadastro;
    
    @FXML
    private TextField pesquisa;
   
    Connection connection;
   
    ObservableList<Produtos> lista = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{         
            this.connection = new conexao().getConnection();  

            ResultSet rs = connection.createStatement().executeQuery("select * from produtos");
            
            while (rs.next()){
                lista.add(new Produtos(rs.getInt("codigo"), rs.getString("descricao"), rs.getString("marca"), rs.getString("fornecedor"), rs.getFloat("preco_custo"), rs.getFloat("preco_venda"), rs.getInt("quantidade"),rs.getString("data_cadastro")));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(TabelaProdutosController.class.getName()).log(Level.SEVERE, null,ex);
            JOptionPane.showMessageDialog(null,ex);               
        }
 
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col_descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        col_marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        col_fornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        col_preco_custo.setCellValueFactory(new PropertyValueFactory<>("preco_custo"));
        col_preco_venda.setCellValueFactory(new PropertyValueFactory<>("preco_venda"));
        col_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        col_data_cadastro.setCellValueFactory(new PropertyValueFactory<>("data_cadastro"));
        editableCols();
        tabela_produtos.setItems(lista);
        
        tabela_produtos.requestFocus();
        tabela_produtos.getSelectionModel().select(0);
        tabela_produtos.getFocusModel().focus(0);
        
        ObservableList data =  tabela_produtos.getItems();
        pesquisa.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tabela_produtos.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Produtos> subentries = FXCollections.observableArrayList();

            long count = tabela_produtos.getColumns().stream().count();
            for (int i = 0; i < tabela_produtos.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tabela_produtos.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tabela_produtos.getItems().get(i));
                        break;
                    }
                }
            }
            tabela_produtos.setItems(subentries);
        });
    }   
    
    private void editableCols(){                      
        col_descricao.setCellFactory(TextFieldTableCell.forTableColumn());
        
        col_descricao.setOnEditCommit(e -> {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setDescricao(e.getNewValue());
        });

        col_marca.setCellFactory(TextFieldTableCell.forTableColumn());

        col_marca.setOnEditCommit(e -> {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setMarca(e.getNewValue());
        });
        
        col_preco_custo.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        
        col_preco_custo.setOnEditCommit((TableColumn.CellEditEvent<Produtos, Float> e) -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPreco_custo(e.getNewValue());
        });
        
        col_preco_venda.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        
        col_preco_venda.setOnEditCommit((TableColumn.CellEditEvent<Produtos, Float> e) -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPreco_venda(e.getNewValue());
        });
        
        col_quantidade.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        col_quantidade.setOnEditCommit((TableColumn.CellEditEvent<Produtos, Integer> e) -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setQuantidade(e.getNewValue());
        });
        
        col_data_cadastro.setCellFactory(TextFieldTableCell.forTableColumn());

        col_data_cadastro.setOnEditCommit(e -> {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setData_cadastro(e.getNewValue());  
        });               
    }
    
    @FXML
    void Deletar() {               
        
        ObservableList<Produtos> items = tabela_produtos.getItems();

        if (items.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Não foi possível excluir!");
            alert.setContentText("Não há conteúdo na tabela!");
            alert.showAndWait();
        } 
        else {            
            Produtos produto = new Produtos(tabela_produtos.getSelectionModel().getSelectedItem().getCodigo());

            crud dao = new crud();

            dao.DeletarProdutos(produto);	

            ObservableList<Produtos> Alltabela_produtos, Singletabela_produtos;
            Alltabela_produtos = tabela_produtos.getItems();
            Singletabela_produtos = tabela_produtos.getSelectionModel().getSelectedItems();
            Singletabela_produtos.forEach(Alltabela_produtos::remove);	
        }
    }
    
    @FXML
    void Atualizar() { 
        ObservableList<Produtos> items = tabela_produtos.getItems();

        if (items.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Não foi possível atualizar!");
            alert.setContentText("Não há conteúdo na tabela!");
            alert.showAndWait();
        } 
        else {
            Produtos produto = new Produtos(tabela_produtos.getSelectionModel().getSelectedItem().getCodigo(), tabela_produtos.getSelectionModel().getSelectedItem().getDescricao(), 
                                            tabela_produtos.getSelectionModel().getSelectedItem().getMarca(), tabela_produtos.getSelectionModel().getSelectedItem().getFornecedor(), 
                                            tabela_produtos.getSelectionModel().getSelectedItem().getPreco_custo(), tabela_produtos.getSelectionModel().getSelectedItem().getPreco_venda(),
                                            tabela_produtos.getSelectionModel().getSelectedItem().getQuantidade(), tabela_produtos.getSelectionModel().getSelectedItem().getData_cadastro());

            crud dao = new crud();

            dao.AtualizarProdutos(produto); 
        }
        /*tabela_produtos.requestFocus();
        tabela_produtos.getSelectionModel().select(tabela_produtos.getSelectionModel().getSelectedIndex());
        tabela_produtos.getFocusModel().focus(tabela_produtos.getSelectionModel().getSelectedIndex());*/		    
    }
    
    public void handleImprimir() throws JRException{
        URL url = getClass().getResource("/relatorios/RelatorioProdutos.jasper");
        
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
        
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        
        jasperViewer.setVisible(true);
    }  
}
