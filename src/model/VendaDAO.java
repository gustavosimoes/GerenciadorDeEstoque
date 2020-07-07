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
public class VendaDAO extends GeralDAO {

    
    /**
     * Esta função insere uma nova venda no banco.
     */
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

    /**
     * Esta função retorna um ArrayList de Vendas, com as vendas de uma data específica (dia).
     */
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
    
    /**
     * Esta função retorna um ArrayList de Vendas, com as vendas de uma data específica (mês).
     */
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
    
    public boolean atribuirVendaPrazo(Venda novaVenda, String nomeCliente){
        this.inserirNovaVenda(novaVenda);
        connectToDb();
        ClienteDAO daoCliente = new ClienteDAO();
        int idCliente = daoCliente.getIdCliente(nomeCliente);
        int idVenda = 0;
        String sqlVendaPrazo = "UPDATE VendaDiaria SET Cliente_idCliente = ? WHERE idVenda = ?";
        String sqlGetIdVenda = "SELECT * FROM VendaDiaria WHERE idVenda = (SELECT MAX(idVenda) FROM VendaDiaria)";

        try {
            st = con.createStatement();
            rs = st.executeQuery(sqlGetIdVenda);
            while (rs.next()) {
                idVenda = rs.getInt("idVenda");
            }
            
            pst = con.prepareStatement(sqlVendaPrazo);
            pst.setInt(1, idCliente);
            pst.setInt(2, idVenda);

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
    
}
