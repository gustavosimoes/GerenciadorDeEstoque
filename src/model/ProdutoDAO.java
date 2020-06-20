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
    public boolean inserirEstoqueProduto(int addEstoque, int codigoProduto){
        
        connectToDb();
        
        int estoqueAtual = this.getEstoqueProduto(codigoProduto);
        int novoEstoque = estoqueAtual + addEstoque;
        
        String sqlUpdateEstoque = "UPDATE Produto SET qtdEstoque = ? WHERE codigo = ?";
        try{
            pst = con.prepareStatement(sqlUpdateEstoque);
            pst.setInt(1, novoEstoque);
            pst.setInt(2, codigoProduto);
            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Inserção feita com sucesso!");
            sucesso = true;
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
            sucesso = false;
        } finally{
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

//    /**
//     * Esta função retorna uma lista com os competidores da equipe selecionada.
//     */
//    public ArrayList<Competidor> listaCompetidores(String nomeEquipe) {
//        ArrayList<Competidor> listaCompetidores = new ArrayList<>();
//        connectToDb();
//
//        EquipeDAO daoEquipe = new EquipeDAO();
//
//        int idEquipe = daoEquipe.getIdEquipe(nomeEquipe);
//        int idCapitao = 0;
//
//        String sqlCapitao = "SELECT * FROM equipe WHERE idequipe = ?";
//        String sqlCompetidores = "SELECT * FROM competidor WHERE equipe_idequipe = ? ";
//        try {
//            pst = con.prepareStatement(sqlCapitao);
//            pst.setInt(1, idEquipe);
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//                idCapitao = rs.getInt("competidor_idcompetidor");
//            }
//
//            pst = con.prepareStatement(sqlCompetidores);
//            pst.setInt(1, idEquipe);
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//                Competidor competidorTemp = new Competidor(rs.getString("nome"), rs.getInt("idade"), rs.getString("sexo"));
//
//                if (rs.getInt("idcompetidor") == idCapitao) { //caso o competidor atual seja o capitão da equipe, atualizamos a variavel dele para true.
//                    competidorTemp.setCapitao(true);
//                }
//                listaCompetidores.add(competidorTemp);
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
//        } finally {
//            try {
//                con.close();
//                pst.close();
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
//            }
//        }
//
//        return listaCompetidores;
//    }
//
//    /**
//     * Esta função exclui o competidor selecionado.
//     */
//    public boolean deletarCompetidor(String nomeCompetidor) {
//
//        connectToDb(); //Conecta ao banco de dados
//        //Comando em SQL:
//
//        int idCompetidor = 0;
//
//        String sqlIdCompetidor = "SELECT * FROM competidor WHERE nome = ?";
//        String sqlApagaCompetidor = "DELETE FROM competidor WHERE idcompetidor = ?";
//        try {
//            pst = con.prepareStatement(sqlIdCompetidor);
//            pst.setString(1, nomeCompetidor);
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//                idCompetidor = rs.getInt("idcompetidor");
//            }
//
//            pst = con.prepareStatement(sqlApagaCompetidor);
//            pst.setInt(1, idCompetidor);
//
//            pst.execute();
//            sucesso = true;
//        } catch (SQLException ex) {
//            System.out.println("Erro = " + ex.getMessage());
//        } finally {
//            try {
//                con.close();
//                pst.close();
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(null, "Erro = " + ex.getMessage());
//            }
//        }
//
//        return sucesso;
//    }
}
