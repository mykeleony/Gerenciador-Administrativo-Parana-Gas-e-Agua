/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Administrador;
import model.Clientes;
import model.Fornecedores;
import model.ItemDeVenda;
import model.Produtos;
import model.Vendas;

/**
 *
 * @author i16633i
 */
public class crud {
        
    private final Connection connection;
    public boolean resultado;
    
    Alert alert = new Alert(Alert.AlertType.WARNING);   
    
    public crud(){
        this.connection = new conexao().getConnection();
    }
   
    public void autenticar(Administrador admin) throws IOException{
        String user = admin.getUsuario();
        String senha = admin.getSenha();
        
        try{
            String sql = "SELECT user, senha FROM administrador WHERE user ='"+user+"' AND senha = '"+senha+"'";
            PreparedStatement stmt = connection.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();           
            
            if(user.equals(rs.getString("user")) || senha.equals(rs.getString("senha"))){
               Stage s1 = new Stage();
               Parent root = FXMLLoader.load(getClass().getResource("/menu/Menu.fxml"));
               Scene scene = new Scene(root);
               s1.initStyle(StageStyle.UNDECORATED);
               s1.setScene(scene);
               s1.show(); 
               resultado = true;
            }
        }
        
        catch(SQLException e){
            alert.setTitle("Alerta");
            alert.setHeaderText("Não foi possível efetuar o login!");
            alert.setContentText("Usuário ou senha incorretos!");
            alert.showAndWait();       
            resultado = false;
        }
    }   
    
    public void lembrar(Administrador adm) {

        String sql = "UPDATE lembrar SET user = ? WHERE cod =1;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql); 
            stmt.setString(1, adm.getUsuario());        
            stmt.execute();            
            stmt.close();  
        } catch (SQLException u) {  
            throw new RuntimeException(u);             
        }      
    }

        public void naolembrar(Administrador adm) {

        String sql = "UPDATE lembrar SET user = ? WHERE cod =1;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql); 
            stmt.setString(1, null);        
            stmt.execute();            
            stmt.close();  
        } catch (SQLException u) {  
            throw new RuntimeException(u);             
        }      
    }    
    
    public void validar(Administrador adm) throws IOException{
        String user = adm.getUsuario();
        String senha = adm.getSenha();
        
        try{
            String sql = "SELECT user, senha FROM administrador WHERE user ='"+user+"'";
            PreparedStatement stmt = connection.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();           
            
            if(user.equals(rs.getString("user"))){        
               resultado = true;
            }
        }
        catch(SQLException e){
            resultado = false;
        }
    }
    
    public void InserirAdm(Administrador adm){
        String sql = "insert into administrador(user, senha, confirmasenha) values (?,?,?)";
       
        try {              
            PreparedStatement stmt = connection.prepareStatement(sql); 
            stmt.setString(1, adm.getUsuario());             
            stmt.setString(2, adm.getSenha());
            stmt.setString(3, adm.getConfirmasenha());
            stmt.execute();            
            stmt.close();
        }   catch (SQLException u)      
        {  
            throw new RuntimeException(u);             
        }      
    }
    
    public String preencherlembrar(Administrador adm){
                
        try{
            String sql = "SELECT user FROM lembrar WHERE cod = 1";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            
            return rs.getString("user");
        }
        catch(SQLException e){
            
        }
        return null;
    }
    
    public void AlterarSenha(Administrador adm) {

        String sql = "UPDATE administrador SET senha = ?, confirmasenha = ? WHERE user= ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql); 
            
            stmt.setString(1, adm.getSenha());    
            stmt.setString(2, adm.getConfirmasenha());   
            stmt.setString(3, adm.getUsuario());   
            stmt.execute();            
            stmt.close();  
        } catch (SQLException u) {  
            throw new RuntimeException(u);             
        }      
    }
    
     public void ValidarClientes(Clientes cliente) throws IOException{
        String cpf = cliente.getCpf();
        
        try{
            String sql = "SELECT cpf FROM clientes WHERE cpf ='"+cpf+"'";
            PreparedStatement stmt = connection.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();           
            
            if(cpf.equals(rs.getString("cpf"))){
               resultado = true;
            }
        }
        
        catch(SQLException e){     
            resultado = false;
        }
    }     
     
     public void InserirClientes(Clientes cliente){
        String sql = "insert into clientes(cpf, nome, email, endereco, numero, complemento, bairro, cidade, telefone_fixo, telefone_celular, data_cadastro) values (?,?,?,?,?,?,?,?,?,?,?)";
       
        try {              
            PreparedStatement stmt = connection.prepareStatement(sql); 
            stmt.setString(1, cliente.getCpf());             
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getEndereco());             
            stmt.setString(5, cliente.getNumero());
            stmt.setString(6, cliente.getComplemento());
            stmt.setString(7, cliente.getBairro());             
            stmt.setString(8, cliente.getCidade());
            stmt.setString(9, cliente.getTelefone_fixo());
            stmt.setString(10, cliente.getTelefone_celular());  
            stmt.setString(11, cliente.getData_cadastro()); 

            stmt.execute();            
            stmt.close();
        }   catch (SQLException u)      
        {  
            throw new RuntimeException(u);             
        }      
    }
     
     public void DeletarClientes(Clientes cliente) {
        String sql = "delete from clientes where cpf =  ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getCpf());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
    
    public void AtualizarClientes(Clientes cliente) {

        String sql = "UPDATE clientes SET nome = ?, email  = ?, endereco = ?, numero =  ?, complemento =  ?, bairro =  ?, cidade =  ?, telefone_fixo =  ?, telefone_celular =  ?, data_cadastro = ? WHERE cpf = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql); 
            stmt.setString(1, cliente.getNome());  
            stmt.setString(2, cliente.getEmail());  
            stmt.setString(3, cliente.getEndereco());  
            stmt.setString(4, cliente.getNumero());  
            stmt.setString(5, cliente.getComplemento());  
            stmt.setString(6, cliente.getBairro());  
            stmt.setString(7, cliente.getCidade());  
            stmt.setString(8, cliente.getTelefone_fixo());  
            stmt.setString(9, cliente.getTelefone_celular());
            stmt.setString(10, cliente.getData_cadastro());
            stmt.setString(11, cliente.getCpf());             
            stmt.execute();            
            stmt.close();  
        } catch (SQLException u) {  
            throw new RuntimeException(u);             
        }      
    }  
    
    public void ValidarFornecedores(Fornecedores fornecedor) throws IOException{
        String cnpj = fornecedor.getCnpj();
        
        try{
            String sql = "SELECT cnpj FROM fornecedores WHERE cnpj ='"+cnpj+"'";
            PreparedStatement stmt = connection.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();           
            
            if(cnpj.equals(rs.getString("cnpj"))){
               resultado = true;
            }
        }
        
        catch(SQLException e){     
            resultado = false;
        }
    }     
    
     public void InserirFornecedores(Fornecedores fornecedor){
        String sql = "insert into fornecedores(cnpj, nome, email, endereco, numero, complemento, bairro, cidade, telefone_fixo, telefone_celular, data_cadastro) values (?,?,?,?,?,?,?,?,?,?,?)";
       
        try {              
            PreparedStatement stmt = connection.prepareStatement(sql); 
            stmt.setString(1, fornecedor.getCnpj());             
            stmt.setString(2, fornecedor.getNome());
            stmt.setString(3, fornecedor.getEmail());
            stmt.setString(4, fornecedor.getEndereco());             
            stmt.setString(5, fornecedor.getNumero());
            stmt.setString(6, fornecedor.getComplemento());
            stmt.setString(7, fornecedor.getBairro());             
            stmt.setString(8, fornecedor.getCidade());
            stmt.setString(9, fornecedor.getTelefone_fixo());
            stmt.setString(10, fornecedor.getTelefone_celular());    
            stmt.setString(11, fornecedor.getData_cadastro()); 
            stmt.execute();            
            stmt.close();
        }   catch (SQLException u)      
        {  
            throw new RuntimeException(u);             
        }      
    }
     
     public void DeletarFornecedores(Fornecedores fornecedor) {
        String sql = "delete from fornecedores where cnpj =  ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, fornecedor.getCnpj());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
    
    public void AtualizarFornecedores(Fornecedores fornecedor) {

        String sql = "UPDATE fornecedores SET nome = ?, email  = ?, endereco = ?, numero =  ?, complemento =  ?, bairro =  ?, cidade =  ?, telefone_fixo =  ?, telefone_celular =  ?, data_cadastro =  ? WHERE cnpj = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql); 
            stmt.setString(1, fornecedor.getNome());  
            stmt.setString(2, fornecedor.getEmail());  
            stmt.setString(3, fornecedor.getEndereco());  
            stmt.setString(4, fornecedor.getNumero());  
            stmt.setString(5, fornecedor.getComplemento());  
            stmt.setString(6, fornecedor.getBairro());  
            stmt.setString(7, fornecedor.getCidade());  
            stmt.setString(8, fornecedor.getTelefone_fixo());  
            stmt.setString(9, fornecedor.getTelefone_celular());
            stmt.setString(10, fornecedor.getData_cadastro()); 
            stmt.setString(11, fornecedor.getCnpj());             
            stmt.execute();            
            stmt.close();  
        } catch (SQLException u) {  
            throw new RuntimeException(u);             
        }      
    }
    
    public void ValidarProdutos(Produtos produto) throws IOException{
        int codigo = produto.getCodigo();
        
        try{
            String sql = "SELECT codigo FROM produtos WHERE codigo ='"+codigo+"'";
            PreparedStatement stmt = connection.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();           
            
            if(codigo == (rs.getInt("codigo"))){
               resultado = true;
            }
        }
        
        catch(SQLException e){     
            resultado = false;
        }
    } 

     public void InserirProdutos(Produtos produto){
        String sql = "insert into produtos(descricao, marca, fornecedor, preco_custo, preco_venda, quantidade, data_cadastro) values (?,?,?,?,?,?,?)";
       
        try {              
            PreparedStatement stmt = connection.prepareStatement(sql);       
            stmt.setString(1, produto.getDescricao());
            stmt.setString(2, produto.getMarca());  
            stmt.setString(3, produto.getFornecedor());  
            stmt.setFloat(4, produto.getPreco_custo());
            stmt.setFloat(5, produto.getPreco_venda());
            stmt.setInt(6, produto.getQuantidade());
            stmt.setString(7, produto.getData_cadastro());                  
            stmt.execute();            
            stmt.close();
        }   catch (SQLException u)      
        {  
            throw new RuntimeException(u);             
        }      
    }
     
    public void DeletarProdutos(Produtos produto) {
        String sql = "delete from produtos where codigo =  ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, produto.getCodigo());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
    
    public void AtualizarProdutos(Produtos produto) {

        String sql = "UPDATE produtos SET descricao = ?, marca  = ?, fornecedor = ?, preco_custo =  ?, preco_venda =  ?, quantidade = ?, data_cadastro =  ? WHERE codigo = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);             
            stmt.setString(1, produto.getDescricao());
            stmt.setString(2, produto.getMarca());        
            stmt.setString(3, produto.getFornecedor());  
            stmt.setFloat(4, produto.getPreco_custo());
            stmt.setFloat(5, produto.getPreco_venda());
            stmt.setInt(6, produto.getQuantidade());
            stmt.setString(7, produto.getData_cadastro());
            stmt.setInt(8, produto.getCodigo()); 
            stmt.execute();            
            stmt.close();  
        } catch (SQLException u) {  
            throw new RuntimeException(u);             
        }      
    }        
    
    //Lista os clientes e coloca na comboBox
    public List<Clientes> listar_clientes() {
        String sql = "SELECT * FROM clientes";
        List<Clientes> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Clientes cliente = new Clientes();
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setEndereco(resultado.getString("endereco"));
                cliente.setNumero(resultado.getString("numero"));
                cliente.setComplemento(resultado.getString("complemento"));
                cliente.setBairro(resultado.getString("bairro"));
                cliente.setCidade(resultado.getString("cidade"));
                cliente.setTelefone_fixo(resultado.getString("telefone_fixo"));
                cliente.setTelefone_celular(resultado.getString("telefone_celular"));
                cliente.setData_cadastro(resultado.getString("data_cadastro"));                
                retorno.add(cliente);
            }
        } catch (SQLException u) {
            throw new RuntimeException(u);  
        }
        return retorno;
    } 
    
    //Busca todos os clientes e coloca no vendas
    public Clientes buscar(Clientes cliente) {
        String sql = "SELECT * FROM clientes WHERE cpf=?";
        Clientes retorno = new Clientes();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getCpf());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                cliente.setNome(resultado.getString("nome"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setEndereco(resultado.getString("endereco"));
                cliente.setNumero(resultado.getString("numero"));
                cliente.setComplemento(resultado.getString("complemento"));
                cliente.setBairro(resultado.getString("bairro"));
                cliente.setCidade(resultado.getString("cidade"));
                cliente.setTelefone_fixo(resultado.getString("telefone_fixo"));
                cliente.setTelefone_celular(resultado.getString("telefone_celular"));
                cliente.setData_cadastro(resultado.getString("data_cadastro"));
                
                retorno = cliente;
            }
        } catch (SQLException u ) {
           throw new RuntimeException(u);   
        }
        return retorno;
    }
    
    //Lista os produtos e coloca na comboBox
    public List<Produtos> listar_produtos() {
        String sql = "SELECT * FROM produtos";
        List<Produtos> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Produtos produto = new Produtos();
                produto.setCodigo(resultado.getInt("codigo"));
                produto.setDescricao(resultado.getString("descricao"));
                produto.setMarca(resultado.getString("marca"));
                produto.setFornecedor(resultado.getString("fornecedor"));
                produto.setPreco_custo(resultado.getFloat("preco_custo"));
                produto.setPreco_venda(resultado.getFloat("preco_venda"));
                produto.setQuantidade(resultado.getInt("quantidade"));
                produto.setData_cadastro(resultado.getString("data_cadastro"));                
                retorno.add(produto);
            }
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return retorno;
    }
    public int search_produtos(Produtos produto) {
        String sql = "SELECT quantidade FROM produtos where codigo = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, produto.getCodigo());
            ResultSet resultado = stmt.executeQuery();
            int cod = 0;
            while (resultado.next()) {
                cod = resultado.getInt("quantidade");
            }
            return cod;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
    
    public boolean inserir_vendas(Vendas venda) {
        String sql = "INSERT INTO vendas(data, valor, cpf) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(venda.getData()));
            stmt.setDouble(2, venda.getValor());
            stmt.setString(3, venda.getCliente().getCpf());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(crud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //Lista todas as vendas - VendasController
    public List<Vendas> listar() {
        String sql = "SELECT * FROM vendas";
        List<Vendas> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Vendas venda = new Vendas();
                Clientes cliente = new Clientes();
                 List<ItemDeVenda> itensDeVenda = new ArrayList();

                venda.setCodigo_ven(resultado.getInt("codigo_ven"));
                venda.setData(resultado.getDate("data").toLocalDate());
                venda.setValor(resultado.getDouble("valor"));
                cliente.setCpf(resultado.getString("cpf"));    
                
                //Obtendo os dados completos do Cliente associado à Venda       
                cliente = buscar(cliente); 
                itensDeVenda = listarPorVenda(venda);
                
                venda.setCliente(cliente); 
                venda.setItensDeVenda(itensDeVenda);
                retorno.add(venda);
            }
        } catch (SQLException u) {
           throw new RuntimeException(u);           
        }
        return retorno;
    }
    
    public Vendas buscarUltimaVenda() {
        String sql = "SELECT MAX(codigo_ven) FROM vendas";
        Vendas retorno = new Vendas();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                retorno.setCodigo_ven(resultado.getInt("max(codigo_ven)"));
                return retorno;
            }
        } catch (SQLException ex) {
           Logger.getLogger(crud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public boolean inserir_itemdevenda(ItemDeVenda itemDeVenda) {
        String sql = "INSERT INTO itemdevenda(quantidade, valor, codigo, codigo_ven) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeVenda.getQuantidade());
            stmt.setDouble(2, itemDeVenda.getValor());
            stmt.setInt(3, itemDeVenda.getProduto().getCodigo());
            stmt.setInt(4, itemDeVenda.getVenda().getCodigo_ven());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(crud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean RemoverItemDeVenda(ItemDeVenda itemDeVenda) {
        String sql = "DELETE FROM itemdevenda WHERE codigo_item=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itemDeVenda.getCodigo_item());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(crud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean RemoverVenda(Vendas venda) {
        String sql = "DELETE FROM vendas WHERE codigo_ven=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, venda.getCodigo_ven());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(crud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<ItemDeVenda> listarPorVenda(Vendas venda) {
        String sql = "SELECT * FROM itemdevenda WHERE codigo_ven=?";
        List<ItemDeVenda> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, venda.getCodigo_ven());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ItemDeVenda itemDeVenda = new ItemDeVenda();
                Produtos produto = new Produtos();
                Vendas v = new Vendas();
                itemDeVenda.setCodigo_item(resultado.getInt("codigo_item"));
                itemDeVenda.setQuantidade(resultado.getInt("quantidade"));
                itemDeVenda.setValor(resultado.getDouble("valor"));                
                produto.setCodigo(resultado.getInt("codigo"));
                v.setCodigo_ven(resultado.getInt("codigo_ven"));
                
                produto = buscar_produtos(produto);
                
                itemDeVenda.setProduto(produto);
                itemDeVenda.setVenda(v);
                
                retorno.add(itemDeVenda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(crud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
     public Produtos buscar_produtos(Produtos produto) {
        String sql = "SELECT * FROM produtos WHERE codigo=?";
        Produtos retorno = new Produtos();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, produto.getCodigo());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setCodigo(resultado.getInt("codigo"));
                retorno.setDescricao(resultado.getString("descricao"));
                retorno.setMarca(resultado.getString("marca"));
                retorno.setFornecedor(resultado.getString("fornecedor"));
                retorno.setPreco_custo(resultado.getFloat("preco_custo"));
                retorno.setPreco_venda(resultado.getFloat("preco_venda"));
                retorno.setQuantidade(resultado.getInt("quantidade"));
                retorno.setData_cadastro(resultado.getString("data_cadastro"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(crud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
     
     public Map<Integer, ArrayList> listarQuantidadeVendasPorMes() {
        String sql = "select count(codigo_ven), extract(year from data) as ano, extract(month from data) as mes from vendas group by ano, mes order by ano, mes";
        Map<Integer, ArrayList> retorno = new HashMap();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getInt("ano")))
                {
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("count(codigo_ven)"));
                    retorno.put(resultado.getInt("ano"), linha);
                }else{
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("count(codigo_ven)"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(crud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}




