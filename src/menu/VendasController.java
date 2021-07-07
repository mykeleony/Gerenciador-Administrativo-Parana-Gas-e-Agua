/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import controller.conexao;
import controller.crud;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ItemDeVenda;
import model.Produtos;
import model.Vendas;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class VendasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label label_info;

    @FXML
    private Pane area_cadastro4;

    @FXML
    private TableView<Vendas> tabela_vendas;

    @FXML
    private TableColumn<Vendas, Integer> col_codigo;

    @FXML
    private TableColumn<Vendas, LocalDate> col_data;

    @FXML
    private TableColumn<Vendas, Vendas> col_cliente;

    @FXML
    private Label lbl_codigo_ven;

    @FXML
    private Label lbl_data;

    @FXML
    private Label lbl_valor;

    @FXML
    private Label lbl_cliente;

    @FXML
    private Button btn_inserir;

    @FXML
    private Button btn_alterar;

    @FXML
    private Button btn_excluir;

    private List<Vendas> listVendas;
    
    private ObservableList<Vendas> observableListVendas;
    
    //
    crud dao = new crud();
    
    Connection connection;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.connection = new conexao().getConnection();
        
        Info();    
        
        carregarTableViewVendas();
        
        selecionarItemTableViewVendas(null);
        
        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tabela_vendas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVendas(newValue));
    } 
    
     public void Info(){
        String s = System.getProperty("user.name");
        
        ZonedDateTime dateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        label_info.setText("Usuário: " + s + " - " + "Data: " + dateTime.format( formatter));
    }
   
     public void selecionarItemTableViewVendas(Vendas venda) {
        if (venda != null) {
            lbl_codigo_ven.setText(String.valueOf(venda.getCodigo_ven()));
            lbl_data.setText(String.valueOf(venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            lbl_valor.setText(String.format("%.2f", venda.getValor()));
            lbl_cliente.setText(venda.getCliente().toString());
        } else {
            lbl_codigo_ven.setText("");
            lbl_data.setText("");
            lbl_valor.setText("");
            lbl_cliente.setText("");
        }
    }
   
    public void carregarTableViewVendas(){
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("codigo_ven"));
        col_data.setCellValueFactory(new PropertyValueFactory<>("data"));
        col_cliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        
        listVendas = dao.listar();
        
        observableListVendas = FXCollections.observableArrayList(listVendas);
        
        tabela_vendas.setItems(observableListVendas);
    }
    
    @FXML
    public void handleButtonInserir() throws IOException, SQLException{
        Vendas venda = new Vendas();
        List<ItemDeVenda> listItensDeVenda = new ArrayList<>();
        venda.setItensDeVenda(listItensDeVenda);
        boolean buttonConfirmarClicked = showVendasDialog(venda);
        if (buttonConfirmarClicked) {
            try {
                connection.setAutoCommit(false);
                dao.inserir_vendas(venda);
                for (ItemDeVenda listItemDeVenda : venda.getItensDeVenda()) {
                    Produtos produto = listItemDeVenda.getProduto();
                    listItemDeVenda.setVenda(dao.buscarUltimaVenda());
                    dao.inserir_itemdevenda(listItemDeVenda);
                    produto.setQuantidade(produto.getQuantidade() - listItemDeVenda.getQuantidade());
                    dao.AtualizarProdutos(produto);
                }
                connection.commit();
                carregarTableViewVendas();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(VendasController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(VendasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     
    }
    
    @FXML
    public void handleButtonRemover() throws IOException, SQLException {
        Vendas venda = tabela_vendas.getSelectionModel().getSelectedItem();           
        if (venda != null) {
            connection.setAutoCommit(false);
            venda.setItensDeVenda(dao.listarPorVenda(venda));
            for (ItemDeVenda listItemDeVenda : venda.getItensDeVenda()) {
                Produtos produto = listItemDeVenda.getProduto();
                produto.setQuantidade(dao.search_produtos(produto));
                produto.setQuantidade(produto.getQuantidade() + listItemDeVenda.getQuantidade());
                dao.AtualizarProdutos(produto);
                dao.RemoverItemDeVenda(listItemDeVenda);                
            }
            dao.RemoverVenda(venda);
            connection.commit();
            carregarTableViewVendas();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma venda na tabela!");
            alert.show();
        }
    }
      
     public boolean showVendasDialog(Vendas venda) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(VendasDialogController.class.getResource("/menu/VendasDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Registro de Vendas");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.initStyle(StageStyle.UNDECORATED);
        // Setando a Venda no Controller.
        VendasDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVenda(venda);
        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();
        return controller.isButtonConfirmarClicked();
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
    
}
