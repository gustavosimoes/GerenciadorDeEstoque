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
public class ProdutoDAO {

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

    /**
     * Esta função insere um novo produto no estoque.
     */
    public boolean inserirNovoProduto(Produto novoProduto) {

        connectToDb();

        String sqlInserirProduto = "INSERT INTO Produto (codigo, nomeProduto, descricao, preco, qtdEstoque) values (?,?,?,?,?)";
        try {
            pst = con.prepareStatement(sqlInserirProduto);
            pst.setInt(1, novoProduto.getCodigo());
            pst.setString(2, novoProduto.getNomeProduto());
            pst.setString(3, novoProduto.getDescricao());
            pst.setDouble(4, novoProduto.getPreco());
            pst.setInt(5, 0);

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
    public boolean inserirEstoqueProduto(int addEstoque, int codigoProduto) {

        int estoqueAtual = this.getEstoqueProduto(codigoProduto);
        int novoEstoque = estoqueAtual + addEstoque;

        connectToDb();

        String sqlUpdateEstoque = "UPDATE Produto SET qtdEstoque = ? WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlUpdateEstoque);
            pst.setInt(1, novoEstoque);
            pst.setInt(2, codigoProduto);

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

    public boolean removerEstoqueProduto(int delEstoque, int codigoProduto) {

        int estoqueAtual = this.getEstoqueProduto(codigoProduto);
        int novoEstoque = estoqueAtual - delEstoque;

        connectToDb();

        String sqlUpdateEstoque = "UPDATE Produto SET qtdEstoque = ? WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlUpdateEstoque);
            pst.setInt(1, novoEstoque);
            pst.setInt(2, codigoProduto);

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
    public int getEstoqueProduto(int codigoDeBarras) {

        connectToDb();

        int estoque = 0;

        String sqlGetEstoque = "SELECT qtdEstoque FROM Produto WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlGetEstoque);
            pst.setInt(1, codigoDeBarras);

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

    public String getNomeProduto(int codigoDeBarras) {

        connectToDb();

        String nome = null;

        String sqlGetNome = "SELECT nomeProduto FROM Produto WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlGetNome);
            pst.setInt(1, codigoDeBarras);

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

    public String getDescricaoProduto(int codigoDeBarras) {

        connectToDb();

        String descricao = null;

        String sqlGetDescricao = "SELECT descricao FROM Produto WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlGetDescricao);
            pst.setInt(1, codigoDeBarras);

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
     * Esta função retorna o preco do produto específico.
     */
    public double getPrecoProduto(int codigoDeBarras) {

        connectToDb();

        double preco = 0;

        String sqlGetEstoque = "SELECT preco FROM Produto WHERE codigo = ?";
        try {
            pst = con.prepareStatement(sqlGetEstoque);
            pst.setInt(1, codigoDeBarras);

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

    public Produto getInfoProduto(int codigoProduto) {

        String nome = this.getNomeProduto(codigoProduto);
        String descricao = this.getDescricaoProduto(codigoProduto);
        double preco = this.getPrecoProduto(codigoProduto);
        Produto produto = new Produto(codigoProduto, nome, descricao, preco);
        produto.setQuantidadeEstoque(this.getEstoqueProduto(codigoProduto));

        return produto;
    }

    public ArrayList<Produto> getProdutos() {

        ArrayList<Produto> listaProdutos = new ArrayList<>();
        Produto produtoTemp;
        int codigo, qtdEstoque;
        double preco;
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
                qtdEstoque = rs.getInt("qtdEstoque");

                produtoTemp = new Produto(codigo, nomeProduto, descricao, preco, qtdEstoque);
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
