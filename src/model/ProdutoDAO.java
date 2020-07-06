//Nome do nosso pacote //                
package model;

//Classes necessárias para uso de Banco de dados //
import controller.Produto;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

//Início da classe de conexão//
public class ProdutoDAO extends GeralDAO {

    /**
     * Esta função insere um novo produto no estoque.
     */
    public boolean inserirNovoProduto(Produto novoProduto) {

        connectToDb();

        String sqlInserirProduto = "INSERT INTO Produto (codigo, nomeProduto, descricao, preco, precoDeCusto, qtdEstoque) values (?,?,?,?,?,?)";
        try {
            pst = con.prepareStatement(sqlInserirProduto);
            pst.setLong(1, novoProduto.getCodigo());
            pst.setString(2, novoProduto.getNomeProduto());
            pst.setString(3, novoProduto.getDescricao());
            pst.setDouble(4, novoProduto.getPreco());
            pst.setDouble(5, novoProduto.getPrecoDeCusto());
            pst.setInt(6, novoProduto.getQuantidadeEstoque());

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
     * Esta função insere a quantidade de produtos no estoque.
     */
    public boolean inserirEstoqueProduto(int addEstoque, long codigoProduto) {

        int estoqueAtual = this.getEstoqueProduto(codigoProduto);
        int novoEstoque = estoqueAtual + addEstoque;

        connectToDb();

        String sqlUpdateEstoque = "UPDATE Produto SET qtdEstoque = ? WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlUpdateEstoque);
            pst.setInt(1, novoEstoque);
            pst.setLong(2, codigoProduto);

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
     * Esta função remove a quantidade desejada do estoque.
     */
    public boolean removerEstoqueProduto(int delEstoque, long codigoProduto) {

        int estoqueAtual = this.getEstoqueProduto(codigoProduto);
        int novoEstoque = estoqueAtual - delEstoque;

        connectToDb();

        String sqlUpdateEstoque = "UPDATE Produto SET qtdEstoque = ? WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlUpdateEstoque);
            pst.setInt(1, novoEstoque);
            pst.setLong(2, codigoProduto);

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
     * Esta função retorna a quantidade de estoque do produto específico.
     */
    public int getEstoqueProduto(long codigoDeBarras) {

        connectToDb();

        int estoque = 0;

        String sqlGetEstoque = "SELECT qtdEstoque FROM Produto WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlGetEstoque);
            pst.setLong(1, codigoDeBarras);

            rs = pst.executeQuery();

            while (rs.next()) {
                estoque = rs.getInt("qtdEstoque");
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
        return estoque;

    }

    /**
     * Esta função retorna o nome do produto específico.
     */
    public String getNomeProduto(long codigoDeBarras) {

        connectToDb();

        String nome = null;

        String sqlGetNome = "SELECT nomeProduto FROM Produto WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlGetNome);
            pst.setLong(1, codigoDeBarras);

            rs = pst.executeQuery();

            while (rs.next()) {
                nome = rs.getString("nomeProduto");
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
        return nome;

    }

    /**
     * Esta função retorna a descrição do produto específico.
     */
    public String getDescricaoProduto(long codigoDeBarras) {

        connectToDb();

        String descricao = null;

        String sqlGetDescricao = "SELECT descricao FROM Produto WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlGetDescricao);
            pst.setLong(1, codigoDeBarras);

            rs = pst.executeQuery();

            while (rs.next()) {
                descricao = rs.getString("descricao");
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
        return descricao;

    }

    /**
     * Esta função retorna o preço do produto específico.
     */
    public double getPrecoProduto(long codigoDeBarras) {

        connectToDb();

        double preco = 0;

        String sqlGetEstoque = "SELECT preco FROM Produto WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlGetEstoque);
            pst.setLong(1, codigoDeBarras);

            rs = pst.executeQuery();

            while (rs.next()) {
                preco = rs.getDouble("preco");
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
        return preco;

    }

    /**
     * Esta função retorna o preço do produto específico.
     */
    public double getPrecoDeCustoProduto(long codigoDeBarras) {

        connectToDb();

        double precoDeCusto = 0;

        String sqlGetPrecoDeCusto = "SELECT precoDeCusto FROM Produto WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlGetPrecoDeCusto);
            pst.setLong(1, codigoDeBarras);

            rs = pst.executeQuery();

            while (rs.next()) {
                precoDeCusto = rs.getDouble("precoDeCusto");
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
        return precoDeCusto;

    }

    /**
     * Esta função retorna um Produto com as informações do código do produto
     * desejado.
     */
    public Produto getInfoProduto(long codigoProduto) {

        String nome = this.getNomeProduto(codigoProduto);
        String descricao = this.getDescricaoProduto(codigoProduto);
        double preco = this.getPrecoProduto(codigoProduto);
        double precoDeCusto = this.getPrecoDeCustoProduto(codigoProduto);
        int qtdEstoque = this.getEstoqueProduto(codigoProduto);
        Produto produtoTemp = new Produto(qtdEstoque, codigoProduto, nome, descricao, preco, precoDeCusto);

        return produtoTemp;
    }

    /**
     * Esta função retorna um ArrayList com todos os produtos.
     */
    public ArrayList<Produto> getProdutos() {

        ArrayList<Produto> listaProdutos = new ArrayList<>();
        Produto produtoTemp;
        int codigo, qtdEstoque;
        double preco, precoDeCusto;
        String nomeProduto, descricao;

        connectToDb();

        String sqlGetProdutos = "SELECT * FROM Produto";
        try {

            st = con.createStatement();
            rs = st.executeQuery(sqlGetProdutos);

            while (rs.next()) {
                codigo = rs.getInt("codigo");
                nomeProduto = rs.getString("nomeProduto");
                descricao = rs.getString("descricao");
                preco = rs.getDouble("preco");
                precoDeCusto = rs.getDouble("precoDeCusto");
                qtdEstoque = rs.getInt("qtdEstoque");

                produtoTemp = new Produto(qtdEstoque, codigo, nomeProduto, descricao, preco, precoDeCusto);
                listaProdutos.add(produtoTemp);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            sucesso = false;
        } finally {
            try {   //Encerra a conexão
                con.close();
                st.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            }
        }

        return listaProdutos;
    }
}
