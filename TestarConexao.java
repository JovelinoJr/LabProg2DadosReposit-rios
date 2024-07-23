/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.jdbc;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 *
 * @author Thiago William
 */
public class TestarConexao {
    
    public static void main(String[] args) {
        try {
            new ConexaoBanco().pegarConexao();
            JOptionPane.showMessageDialog(null, "Conectado com Sucesso ao Banco de Dados");
        } catch (HeadlessException erro) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar se conectar com o banco de dados"+ erro.getMessage());
        }
          
    }
}
