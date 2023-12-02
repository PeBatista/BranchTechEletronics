package dao;

import model.ProdutoVendido;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoVendidoDAO {
	static String url = "jdbc:mysql://127.0.0.1:3306/ado";
	static String login = "root";
	static String senha = "";

	public static boolean salvar(ProdutoVendido produtoVendido) {
		boolean retorno = false;

		try (Connection conexao = DriverManager.getConnection(url, login, senha)) {
			String sql = "INSERT INTO t_produto_vendido (t_produto_cd_produto, t_compra_cd_compra, ds_quantidade) VALUES (?, ?, ?)";
			try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
				comandoSQL.setInt(1, produtoVendido.getProdutoId());
				comandoSQL.setInt(2, produtoVendido.getCompraId());
				comandoSQL.setInt(3, produtoVendido.getQuantidade());

				int linhasAfetadas = comandoSQL.executeUpdate();

				if (linhasAfetadas > 0) {
					retorno = true;
				}
			}

		} catch (SQLException ex) {
			Logger.getLogger(ProdutoVendidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return retorno;
	}

	public static boolean produtoJaAdicionado(int idProduto, int idCompra) {
		boolean jaAdicionado = false;

		try (Connection conexao = DriverManager.getConnection(url, login, senha)) {
			String sql = "SELECT * FROM t_produto_vendido WHERE t_produto_cd_produto = ? AND t_compra_cd_compra = ?";
			try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
				comandoSQL.setInt(1, idProduto);
				comandoSQL.setInt(2, idCompra);

				try (ResultSet rs = comandoSQL.executeQuery()) {
					jaAdicionado = rs.next(); // Se houver uma próxima linha, significa que o produto foi adicionado
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return jaAdicionado;
	}

	public static ArrayList<ProdutoVendido> listarProdutosVendidos() {
		ArrayList<ProdutoVendido> lista = new ArrayList<>();

		try (Connection conexao = DriverManager.getConnection(url, login, senha)) {
			String sql = "SELECT * FROM t_produto_vendido";
			try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
				try (ResultSet rs = comandoSQL.executeQuery()) {
					while (rs.next()) {
						ProdutoVendido produtoVendido = new ProdutoVendido();
						produtoVendido.setProdutoId(rs.getInt("t_produto_cd_produto"));
						produtoVendido.setCompraId(rs.getInt("t_compra_cd_compra"));
						produtoVendido.setQuantidade(rs.getInt("ds_quantidade"));
						lista.add(produtoVendido);
					}
				}
			}

		} catch (SQLException ex) {
			Logger.getLogger(ProdutoVendidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return lista;
	}

	public static boolean alterarProdutoVendido(ProdutoVendido produtoVendido) {
		boolean retorno = false;

		try (Connection conexao = DriverManager.getConnection(url, login, senha)) {
			String sql = "UPDATE t_produto_vendido SET ds_quantidade = ? WHERE t_produto_cd_produto = ? AND t_compra_cd_compra = ?";
			try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
				comandoSQL.setInt(1, produtoVendido.getQuantidade());
				comandoSQL.setInt(2, produtoVendido.getProdutoId());
				comandoSQL.setInt(3, produtoVendido.getCompraId());

				int linhasAfetadas = comandoSQL.executeUpdate();

				if (linhasAfetadas > 0) {
					retorno = true;
				}
			}

		} catch (SQLException ex) {
			Logger.getLogger(ProdutoVendidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return retorno;
	}

	public static boolean excluirProdutoVendido(int idProduto, int idCompra) {
		boolean retorno = false;

		try (Connection conexao = DriverManager.getConnection(url, login, senha)) {
			String sql = "DELETE FROM t_produto_vendido WHERE t_produto_cd_produto = ? AND t_compra_cd_compra = ?";
			try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
				comandoSQL.setInt(1, idProduto);
				comandoSQL.setInt(2, idCompra);

				int linhasAfetadas = comandoSQL.executeUpdate();

				if (linhasAfetadas > 0) {
					retorno = true;
				}
			}

		} catch (SQLException ex) {
			Logger.getLogger(ProdutoVendidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return retorno;
	}

	public static ProdutoVendido buscarPorCodigo(int idProduto, int idCompra) {
		ProdutoVendido produtoVendido = null;

		try (Connection conexao = DriverManager.getConnection(url, login, senha)) {
			String sql = "SELECT * FROM t_produto_vendido WHERE t_produto_cd_produto = ? AND t_compra_cd_compra = ?";
			try (PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {
				comandoSQL.setInt(1, idProduto);
				comandoSQL.setInt(2, idCompra);

				try (ResultSet rs = comandoSQL.executeQuery()) {
					if (rs.next()) {
						produtoVendido = new ProdutoVendido();
						produtoVendido.setProdutoId(rs.getInt("t_produto_cd_produto"));
						produtoVendido.setCompraId(rs.getInt("t_compra_cd_compra"));
						produtoVendido.setQuantidade(rs.getInt("ds_quantidade"));
					}
				}
			}

		} catch (SQLException ex) {
			Logger.getLogger(ProdutoVendidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

		return produtoVendido;
	}
	public static ArrayList<ProdutoVendido> listarProdutosVendidosPorCliente(String cpfCliente) {

	    ArrayList<ProdutoVendido> lista = new ArrayList<>();
	    Connection conexao = null;
	    PreparedStatement comandoSQL = null;
	    ResultSet rs = null;

	    try {
	        conexao = DriverManager.getConnection(url, login, senha);
	        // Adicionando a cláusula WHERE para filtrar por código de cliente
	        comandoSQL = conexao.prepareStatement(
	        	    "SELECT c.nm_cliente AS NomeCliente, co.cd_compra AS CodigoCompra, " +
	        	    "pr.nm_produto AS NomeProduto, pv.ds_quantidade AS Quantidade, " +
	        	    "pr.vl_preco AS ValorUnitario, SUM(pv.ds_quantidade * pr.vl_preco) AS vl_total_compra " +
	        	    "FROM t_cliente c " +
	        	    "JOIN t_compra co ON c.cd_cliente = co.t_cliente_cd_cliente " +
	        	    "JOIN t_produto_vendido pv ON co.cd_compra = pv.t_compra_cd_compra " +
	        	    "JOIN t_produto pr ON pv.t_produto_cd_produto = pr.cd_produto " +
	        	    "WHERE c.ds_cpf_cnpj_cliente = ? " +
	        	    "GROUP BY c.nm_cliente, co.cd_compra, pr.nm_produto"
	        	);
	        comandoSQL.setString(1, cpfCliente); // Substituindo o parâmetro na consulta
	        rs = comandoSQL.executeQuery();
	        

	        while (rs.next()) {
	            ProdutoVendido produtoVendido = new ProdutoVendido();
	            produtoVendido.setnnmCliente(rs.getString("NomeCLiente"));
	            produtoVendido.setnmProduto(rs.getString("NomeProduto"));
	            produtoVendido.setQuantidade(rs.getInt("Quantidade"));
	            produtoVendido.setunitCompra(rs.getDouble("ValorUnitario"));
	            produtoVendido.settotalCompra(rs.getDouble("vl_total_compra"));
	            //produtoVendido.setDataHoraCompra(rs.getDate("DataHoraCompra"));
	            

	            System.out.println(produtoVendido.getnmCliente());
	            System.out.println(produtoVendido.getnmProduto());
	            System.out.println(produtoVendido.getQuantidade());
	            System.out.println(produtoVendido.getunitCompra());;
	            System.out.println(produtoVendido.gettotalCompra());
	            
	            lista.add(produtoVendido);
	        }

	    } catch (SQLException ex) {
	        Logger.getLogger(ProdutoVendidoDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        if (conexao != null) {
	            try {
	                conexao.close();
	            } catch (SQLException ex) {
	                Logger.getLogger(ProdutoVendidoDAO.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }

	    return lista;
	}

}
