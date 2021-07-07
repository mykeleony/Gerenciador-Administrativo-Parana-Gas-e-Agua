/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import controller.conexao;
import controller.crud;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Clientes;
import model.ItemDeVenda;
import model.Produtos;
import model.Vendas;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class VendasDialogController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXDatePicker datePickerData;

    @FXML
    private JFXComboBox comboBoxCliente;

    @FXML
    private TableView<ItemDeVenda> tableViewItensDeVenda;

    @FXML
    private TableColumn<Produtos, Produtos> col_produtos;

    @FXML
    private TableColumn<ItemDeVenda, Integer> col_quantidade;

    @FXML
    private TableColumn<ItemDeVenda, Double> col_valor;

    @FXML
    private JFXComboBox comboBoxProduto;

    @FXML
    private TextField textFieldQuantidade;
    
    @FXML
    private JFXTextField textFieldValor;

    @FXML
    private Button buttonAdicionar;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private Button buttonCancelar;
    
    @FXML
    private MaterialDesignIconView close;

    private List<Clientes> listClientes;
    private ObservableList<Clientes> observableListClientes;    
    private List<Produtos> listProdutos;
    private ObservableList<Produtos> observableListProdutos;    
    private ObservableList<ItemDeVenda> observableListItemDeVenda;
    
    private Stage dialogStage;   
    private boolean buttonConfirmarClicked = false;
    private Vendas venda;
    
    //
    crud dao = new crud();
    
    Connection connection;    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.connection = new conexao().getConnection();
        carregarComboBoxClientes();
        carregarComboBoxProdutos();
        col_produtos.setCellValueFactory(new PropertyValueFactory<>("produto"));
        col_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        col_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        
        textFieldQuantidade.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    textFieldQuantidade.setText(oldValue);
                }
            }
        });   
    }    

    public void carregarComboBoxClientes() {
        listClientes = dao.listar_clientes();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxCliente.setItems(observableListClientes);
    }

    public void carregarComboBoxProdutos() {
        listProdutos = dao.listar_produtos();
        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        comboBoxProduto.setItems(observableListProdutos);
    }
    
    @FXML
    public void handleButtonAdicionar() {
       
       String errorMessage = "";
        
        if (textFieldQuantidade.getText().isEmpty()){            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Problemas com a escolha do produto!");
            alert.setContentText("Quantidade inválida!");
            alert.show();
            textFieldQuantidade.setText("");                    
       } 
       else {
            Produtos produto;
            ItemDeVenda itemDeVenda = new ItemDeVenda();
            if(comboBoxProduto.getSelectionModel().getSelectedItem() != null) {
                produto = (Produtos) comboBoxProduto.getSelectionModel().getSelectedItem();
                if(produto.getQuantidade() >= Integer.parseInt(textFieldQuantidade.getText())) {
                    itemDeVenda.setProduto((Produtos) comboBoxProduto.getSelectionModel().getSelectedItem());
                    itemDeVenda.setQuantidade(Integer.parseInt(textFieldQuantidade.getText()));
                    itemDeVenda.setValor(itemDeVenda.getProduto().getPreco_venda() * itemDeVenda.getQuantidade());
                    venda.getItensDeVenda().add(itemDeVenda);
                    venda.setValor(venda.getValor() + itemDeVenda.getValor());
                    observableListItemDeVenda = FXCollections.observableArrayList(venda.getItensDeVenda());
                    tableViewItensDeVenda.setItems(observableListItemDeVenda);
                    textFieldValor.setText(String.format("%.2f", venda.getValor()));
                }
                else{

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Problemas com a escolha do produto!");
                    alert.setContentText("Não existe a quantidade de produtos disponíveis no estoque!");
                    alert.show();
                     textFieldQuantidade.setText("");               
                }           
            }        
        }
    }
    
    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            venda.setCliente((Clientes) comboBoxCliente.getSelectionModel().getSelectedItem());
            venda.setData(datePickerData.getValue());
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }
    
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        if (comboBoxCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente inválido!\n";
        }
        if (datePickerData.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        if (comboBoxProduto.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Produto inválido!\n";
        }
        if (textFieldQuantidade.getText().isEmpty()) {
            errorMessage += "Quantidade inválida!\n";
        }
        if (observableListItemDeVenda == null) {
            errorMessage += "Itens de Venda inválidos!\n";
        }       
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
    @FXML
    private void Minimizar(MouseEvent event) {
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @FXML
    private void Sair(MouseEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();    
        stage.close();
    }    

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public Vendas getVenda() {
        return venda;
    }

    public void setVenda(Vendas venda) {
        this.venda = venda;
    }
    
    
}
