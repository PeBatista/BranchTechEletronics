package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;

import model.Compra;

public class CompraDAO {

    static String url = "jdbc:mysql://127.0.0.1:3306/ado";
    static String login = "root";
    static String senha = "";

    public static boolean salvar(Compra novaCompra) {
        boolean sucesso = false;
        Connection conexao = null;
        PreparedStatement comandoSql = null;

        try {
            conexao = DriverManager.getConnection(url, login, senha);

            String sql = "INSERT INTO t_compra (t_cliente_cd_cliente, dt_hora_compra) VALUES (?, ?)";
            comandoSql = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            comandoSql.setInt(1, novaCompra.getClienteId());
            comandoSql.setObject(2, novaCompra.getDataHoraCompra());

            int linhasAfetadas = comandoSql.executeUpdate();

            if (linhasAfetadas > 0) {
                sucesso = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();  // Considere logar ou tratar de forma mais específica.
            sucesso = false;
        } finally {
            closeResources(conexao, comandoSql);
        }

        return sucesso;
    }

    public static int obterUltimoIdInserido() throws SQLException {
        int ultimoId = -1;
        Connection conexao = null;
        PreparedStatement comandoSql = null;
        ResultSet resultSet = null;

        try {
            conexao = DriverManager.getConnection(url, login, senha);

            // Utilizando a função LAST_INSERT_ID() do MySQL para obter o último ID inserido
            String sql = "SELECT LAST_INSERT_ID()";
            comandoSql = conexao.prepareStatement(sql);

            resultSet = comandoSql.executeQuery();

            if (resultSet.next()) {
                ultimoId = resultSet.getInt(1);
            }
        } finally {
            closeResources(conexao, comandoSql);
        }

        return ultimoId;
    }
    private static void closeResources(Connection conexao, PreparedStatement comandoSql) {
        try {
            if (comandoSql != null) {
                comandoSql.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Considere logar ou tratar de forma mais específica.
        }
    }

    public static ArrayList<Compra> listarCompras() {
        ArrayList<Compra> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet rs = null;

        try {
            conexao = DriverManager.getConnection(url, login, senha);
            comandoSQL = conexao.prepareStatement("SELECT * FROM t_compra");
            rs = comandoSQL.executeQuery();

            while (rs.next()) {
                Compra item = new Compra();
                item.setIdCompra(rs.getInt("cd_compra"));
                item.setClienteId(rs.getInt("t_cliente_cd_cliente"));
                item.getDataHoraCompra();
                lista.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (comandoSQL != null) {
                try {
                    comandoSQL.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return lista;
    }

    public static int buscarPorCodigo(int numeroBuscar) {

        int idCompra = -1;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet rs = null;

        try {
            conexao = DriverManager.getConnection(url, login, senha);
            comandoSQL = conexao.prepareStatement("SELECT cd_compra FROM t_compra WHERE t_cliente_cd_cliente = ?");
            comandoSQL.setInt(1, numeroBuscar);

            rs = comandoSQL.executeQuery();

            if (rs.next()) {
                idCompra = rs.getInt("cd_compra");
            }

        } catch (SQLException ex) {
            Logger.getLogger(CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return idCompra;
    }

    public static boolean alterarCompra(Compra notaAlterar) {
        boolean retorno = false;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = DriverManager.getConnection(url, login, senha);
            String sql = "UPDATE t_compra SET t_cliente_cd_cliente = ?, dt_hora_compra = ? WHERE cd_compra = ?";
            comandoSQL = conexao.prepareStatement(sql);

            comandoSQL.setInt(1, notaAlterar.getClienteId());
            comandoSQL.setObject(2, notaAlterar.getDataHoraCompra());
            comandoSQL.setInt(3, notaAlterar.getIdCompra());

            int linhasAfetadas = comandoSQL.executeUpdate();

            if (linhasAfetadas > 0) {
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (comandoSQL != null) {
                try {
                    comandoSQL.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return retorno;
    }

    public static boolean excluir(int idExcluir) {

        boolean retorno = false;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            conexao = DriverManager.getConnection(url, login, senha);
            comandoSQL = conexao.prepareStatement("DELETE FROM t_compra WHERE cd_compra = ?");
            comandoSQL.setInt(1, idExcluir);

            int linhasAfetadas = comandoSQL.executeUpdate();

            if (linhasAfetadas > 0) {
                retorno = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (comandoSQL != null) {
                try {
                    comandoSQL.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return retorno;
    }
}
