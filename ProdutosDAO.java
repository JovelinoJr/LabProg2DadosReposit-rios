/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.Clientes;
import br.com.sistema.model.Fornecedores;
import br.com.sistema.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Thiago William
 */
public class ProdutosDAO {
    private Connection conn;
    
    public ProdutosDAO() {
        this.conn = new ConexaoBanco().pegarConexao();
    } 
    
    
    public void Salvar(Produtos obj){
        try {
            //Criar o SQL
            String sql = "insert into tb_produtos (descricao,preco,qtd_estoque,for_id)"
                    + "values(?,?,?,?)";
            //Preparar a conexão sql para se conectar ao banco
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getFornecedores().getId());
            
            // Executar sql 
            stmt.execute();
            // Fechar conexão
            stmt.close();
            JOptionPane.showMessageDialog(null, "Produto salvo com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"Erro ao salvar o produto"+ erro);
        }
    }
     public void Editar(Produtos obj){
        try {
            //Criar o SQL
            String sql = "update tb_produtos set descricao=?,preco=?,qtd_estoque=?,for_id=? where id=?";
            //Preparar a conexão sql para se conectar ao banco
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            stmt.setInt(4, obj.getFornecedores().getId());
            stmt.setInt(5, obj.getId());
            
            // Executar sql 
            stmt.execute();
            // Fechar conexão
            stmt.close();
            JOptionPane.showMessageDialog(null, "Produto editado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"Erro ao editar o produto"+ erro);
        }
    }
     
    public void Excluir(Produtos obj) {
        try {
            String sql = "delete from tb_produtos where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            stmt.execute();
            stmt.close();
            JOptionPane.showConfirmDialog(null, "Produto excluido com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao excluir Produto!");
        }
    } 
     
    public Produtos BuscarProdutos(String nome){
        try {
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p inner join"
                    + " tb_fornecedores as f on(p.for_id=f.id) where p.descricao = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            Produtos obj = new Produtos();
            Fornecedores f = new Fornecedores();
            if(rs.next()) {
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
                
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedores(f);
                
            }
            return obj;
                        
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,"erro ao buscar Produto"+ erro);
        }
        return null;
    }
    public List<Produtos>Listar(){
    List<Produtos> lista = new ArrayList<>();
        try {
            String sql = "select p.id,p.descricap.p.preco,p.qtd_estoque,f.nome from tb_produtos as p inner join tb_fornecedores as f on(p.for_id=f.id)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedores(f);
                lista.add(obj);
            }
            return lista;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "erro ao criar a lista"+e);
        }        
        return null;
    }  
    
    public List<Produtos>Filtrar(String nome){
    List<Produtos> lista = new ArrayList<>();
        try {
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p inner join"
                    + " tb_fornecedores as f on(p.for_id=f.id) where p.descricao like ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedores(f);
                lista.add(obj);
            }
            return lista;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "erro ao criar a lista"+e);
        }        
        return null;
    }
    public void adicionarEstoque(int id, int qtd_nova) {
        try {
            String sql = "update tb_produtos set qtd_estoque=? where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null,"Adicioonado com sucesso ao estoque");
                       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar ao estoque!"+e);
        }
    }
               
    public void baixaEstoque(int id, int qtd_nova) {
        try {
            String sql = "update tb_produtos set qtd_estoque=? where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qtd_nova);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null,"Baixa no estoque com sucesso");
                       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao dar baixa no estoque!"+e);
        }
        
    }
    
}


    
  