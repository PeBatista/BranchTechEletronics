package dao;

import model.Produto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDAO {

    static String url = "jdbc:mysql://127.0.0.1:3306/ado";
    static String login = "root";
    static String senha = "";

    public static boolean salvar(Produto novoProduto) {
        boolean retorno = false;
        try (Connection conexao = DriverManager.getConnection(url, login, senha)) {

            String sql = "INSERT INTO t_produto (nm_produto, ds_produto, vl_preco, ds_ativo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement comandoSQL = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                comandoSQL.setString(1, novoProduto.getNome());
                comandoSQL.setString(2, novoProduto.getDescricao());
                comandoSQL.setDouble(3, novoProduto.getPreco());
                comandoSQL.setBoolean(4, novoProduto.getAtivo());

                int linhasAfetadas = comandoSQL.executeUpdate();

                if (linhasAfetadas > 0) {
                    retorno = true;

                    try (ResultSet rs = comandoSQL.getGeneratedKeys()) {
                        if (rs.next()) {
                            int idGerado = rs.getInt(1);
                            System.out.println("ID gerado: " + idGerado);
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            retorno = false;
        }

        return retorno;
    }
    public static ArrayList<String> obterNomesProdutos() {
        ArrayList<String> nomesProdutos = new ArrayList<>();

        try (Connection conexao = DriverManager.getConnection(url, login, senha)) {
            String sql = "SELECT nm_produto FROM t_produto"; 
            try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
                try (ResultSet resultado = comandoSQL.executeQuery()) {
                    while (resultado.next()) {
                        String nomeProduto = resultado.getString("nm_produto");
                        nomesProdutos.add(nomeProduto);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Lida com a exceção de alguma maneira adequada ao seu aplicativo
        }

        return nomesProdutos;
    }


    public static int obterIdPeloNome(String nomeProduto) {
        int idProduto = -1;
        try (Connection conexao = DriverManager.getConnection(url, login, senha)) {

            String sql = "SELECT cd_produto FROM t_produto WHERE nm_produto = ?";
            try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
                comandoSQL.setString(1, nomeProduto);

                try (ResultSet rs = comandoSQL.executeQuery()) {
                    if (rs.next()) {
                        idProduto = rs.getInt("cd_produto");
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idProduto;
    }

    
    public static ArrayList<Produto> listarProdutos() {
        ArrayList<Produto> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url, login, senha)) {

            String sql = "SELECT * FROM t_produto";
            try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

                try (ResultSet rs = comandoSQL.executeQuery()) {
                    while (rs.next()) {
                        Produto produto = new Produto();
                        produto.setIdProduto(rs.getInt("cd_produto"));
                        produto.setNome(rs.getString("nm_produto"));
                        produto.setDescricao(rs.getString("ds_produto"));
                        produto.setPreco(rs.getDouble("vl_preco"));
                        produto.setAtivo(rs.getBoolean("ds_ativo"));
                        lista.add(produto);
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public static Produto buscarPorCodigo(int codigoProduto) {
        Produto produto = null;
        try (Connection conexao = DriverManager.getConnection(url, login, senha)) {
            String sql = "SELECT * FROM t_produto WHERE cd_produto = ?";
            try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
                comandoSQL.setInt(1, codigoProduto);

                try (ResultSet rs = comandoSQL.executeQuery()) {
                    if (rs.next()) {
                        produto = new Produto();
                        produto.setIdProduto(rs.getInt("cd_produto"));
                        produto.setNome(rs.getString("nm_produto"));
                        produto.setDescricao(rs.getString("ds_produto"));
                        produto.setPreco(rs.getDouble("vl_preco"));
                        produto.setAtivo(rs.getBoolean("ds_ativo"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produto;
    }

    public static boolean alterarProduto(Produto produtoAlterar) {
        boolean retorno = false;
        String url = "jdbc:mysql://127.0.0.1:3306/ado"; 
        String login = "root";
        String senha = "";

        try (Connection conexao = DriverManager.getConnection(url, login, senha)) {

            String sql = "UPDATE t_produto SET nm_produto = ?, ds_produto = ?, vl_preco = ?, ds_ativo = ? WHERE cd_produto = ?";
            try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

                comandoSQL.setString(1, produtoAlterar.getNome());
                comandoSQL.setString(2, produtoAlterar.getDescricao());
                comandoSQL.setDouble(3, produtoAlterar.getPreco());
                comandoSQL.setBoolean(4, produtoAlterar.getAtivo());
                comandoSQL.setInt(5, produtoAlterar.getIdProduto());

                int linhasAfetadas = comandoSQL.executeUpdate();

                if (linhasAfetadas > 0) {
                    retorno = true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean excluir(int idExcluir) {
        boolean retorno = false;
        try (Connection conexao = DriverManager.getConnection(url, login, senha)) {

            String sql = "DELETE FROM t_produto WHERE cd_produto = ?";
            try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
                comandoSQL.setInt(1, idExcluir);

                int linhasAfetadas = comandoSQL.executeUpdate();

                if (linhasAfetadas > 0) {
                    retorno = true;
                }
            }

        } catch (SQLException ex) {
            retorno = false;
        }

        return retorno;
    }
}
