/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import controller.conexao;
import controller.crud;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Produtos;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class VendasGraficosController implements Initializable {

    @FXML
    private Label label_info;

    @FXML
    private Pane area_graficos;
    
    @FXML
    private BarChart<String, Integer> barChart;
    
    @FXML
    private CategoryAxis categoryAxis;
    
    @FXML
    private NumberAxis numberAxis;
    
    @FXML
    private MaterialDesignIconView close;
    
    @FXML
    private PieChart piechart;
    
    private ObservableList<String> observableListMeses = FXCollections.observableArrayList();
    
    private ObservableList<PieChart.Data> observableListDados = FXCollections.observableArrayList();
    
    crud dao = new crud();
    
    Connection connection;   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Info();
        
        // Obtém an array com nomes dos meses em Inglês.
        String[] arrayMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        // Converte o array em uma lista e adiciona em nossa ObservableList de meses.
        observableListMeses.addAll(Arrays.asList(arrayMeses));
        // Associa os nomes de mês como categorias para o eixo horizontal.
        categoryAxis.setCategories(observableListMeses);
        Map<Integer, ArrayList> dados = dao.listarQuantidadeVendasPorMes();
        for (Map.Entry<Integer, ArrayList> dadosItem : dados.entrySet()) {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(dadosItem.getKey().toString());
            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                String mes;
                Integer quantidade;
                mes = retornaNomeMes((int) dadosItem.getValue().get(i));
                quantidade = (Integer) dadosItem.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(mes, quantidade));
            }
            barChart.getData().add(series);
        }
        
        PreencherPieChart();
    }    
    
    public void Info(){
        String s = System.getProperty("user.name");
        
        ZonedDateTime dateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        label_info.setText("Usuário: " + s + " - " + "Data: " + dateTime.format( formatter));
    }
    
    public String retornaNomeMes(int mes) {
        switch (mes) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
            default:
                return "";
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
    private void PreencherPieChart(){
        this.connection = new conexao().getConnection();
        String sql = "SELECT * FROM produtos";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Produtos produto = new Produtos();    
                observableListDados.addAll(new PieChart.Data(resultado.getString("descricao"), resultado.getInt("quantidade")));
            }
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
       
        piechart.setData(observableListDados);
        piechart.setTitle("Estoque");
        piechart.setLegendSide(Side.BOTTOM);
        piechart.setLabelsVisible(true);
        
       
    }
    
}
