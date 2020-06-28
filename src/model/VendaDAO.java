/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Venda;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author gustavo
 */
public class VendaDAO {

    /**
     * *************** CONEXÃO COM O BANCO DE DADOS ***********************
     */
    // objeto responsável pela conexão com o servidor do banco de dados
    Connection con;
    // objeto responsável por preparar as consultas dinâmicas
    PreparedStatement pst;
    // objeto responsável por executar as consultas estáticas
    Statement st;
    // objeto responsável por referenciar a tabela resultante da busca
    ResultSet rs;

    // NOME DO BANCO DE DADOS
    String database = "GerenciadorDeEstoque";
    // URL: VERIFICAR QUAL A PORTA
    String url = "jdbc:Mysql://localhost:3306/" + database + "?useTimezone=true&serverTimezone=UTC&useSSL=false";
    // USUÁRIO
    String user = "root";
    // SENHA
    String password = "gustavo16";

    boolean sucesso = false;

    // Conectar ao banco de dados
    public void connectToDb() {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean inserirNovaVenda(Venda novaVenda) {

        connectToDb();

        String sqlInserirVenda = "INSERT INTO VendaDiaria (valor, dataVenda) values (?,?)";
        try {
            pst = con.prepareStatement(sqlInserirVenda);
            pst.setDouble(1, novaVenda.getValorVenda());
            pst.setDate(2, (Date) novaVenda.getDataVenda());

            pst.execute();
            sucesso = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            sucesso = false;
        } finally {
            try {   //Encerra a conexão
                con.close();
                pst.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            }
        }

        return sucesso;
    }

    public ArrayList<Venda> getVendasDiarias(Date dataVenda) {
        ArrayList<Venda> listaVendas = new ArrayList<>();
        Venda vendaTemp;
        int idVenda;
        double valorVenda;
        connectToDb();

        String sqlGetVendas = "SELECT * FROM VendaDiaria WHERE dataVenda = ?";
        try {
            pst = con.prepareStatement(sqlGetVendas);
            pst.setDate(1, dataVenda);

            rs = pst.executeQuery();

            while (rs.next()) {
                idVenda = rs.getInt("idVenda");
                valorVenda = rs.getDouble("valor");
                vendaTemp = new Venda(idVenda, valorVenda, dataVenda);
                listaVendas.add(vendaTemp);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            sucesso = false;
        } finally {
            try {   //Encerra a conexão
                con.close();
                pst.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            }
        }

        return listaVendas;
    }

    public ArrayList<Venda> getVendasMensais(int mes, int ano) {
        ArrayList<Venda> listaVendas = new ArrayList<>();
        Venda vendaTemp;
        int idVenda;
        double valorVenda;
        Date dataVenda;
        connectToDb();

        String sqlGetVendas = "SELECT * FROM VendaDiaria WHERE MONTH(dataVenda) = ? AND YEAR(dataVenda) = ?";
        try {
            pst = con.prepareStatement(sqlGetVendas);
            pst.setInt(1, mes);
            pst.setInt(2, ano);

            rs = pst.executeQuery();

            while (rs.next()) {
                idVenda = rs.getInt("idVenda");
                valorVenda = rs.getDouble("valor");
                dataVenda = rs.getDate("dataVenda");
                vendaTemp = new Venda(idVenda, valorVenda, dataVenda);
                listaVendas.add(vendaTemp);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            sucesso = false;
        } finally {
            try {   //Encerra a conexão
                con.close();
                pst.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            }
        }

        return listaVendas;
    }
}
