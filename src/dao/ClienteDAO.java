package dao;

import model.Cliente;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class ClienteDAO {

	static String url = "jdbc:mysql://127.0.0.1:3306/ado";
	static String login = "root";
	static String senha = "";

	public static boolean salvar(Cliente novoCliente) {
	    boolean retorno = false;
	    Connection conexao = null;
	    PreparedStatement comandoSQL = null;
	    ResultSet rs = null;

	    try {
	        conexao = DriverManager.getConnection(url, login, senha);

	        String sql = "INSERT INTO t_cliente (nm_cliente, ds_cpf_cnpj_cliente, dt_nascimento_cliente,nm_funcionario) VALUES (?, ?, ?,?)";
	        comandoSQL = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

	        comandoSQL.setString(1, novoCliente.getNome());
	        comandoSQL.setString(2, novoCliente.getCpfCnpjCliente());

	        // Converta a data para o formato do banco de dados
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date dataNascimento = sdf.parse(novoCliente.getdtNascimentoCliente());
	        java.sql.Date sqlDate = new java.sql.Date(dataNascimento.getTime());

	        comandoSQL.setDate(3, sqlDate);
		comandoSQL.setString(4,novoCliente.getNmFuncionario());

	        int linhasAfetadas = comandoSQL.executeUpdate();

	        if (linhasAfetadas > 0) {
	            retorno = true;

	            rs = comandoSQL.getGeneratedKeys();
	            if (rs != null) {
	                while (rs.next()) {
	                    int idGerado = rs.getInt(1);
	                    System.out.println(rs);

	                    System.out.println("Cliente de número " + idGerado + " adicionado à base");

	                }
	            }
	        }

	    } catch (SQLException | ParseException ex) {
	        ex.printStackTrace();
	        retorno = false;
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

	    return retorno;
	}
	public static ArrayList<Cliente> obterClientes() {
		ArrayList<Cliente> listaClientes = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement comandoSQL = null;
		ResultSet rs = null;

		try {
			conexao = DriverManager.getConnection(url, login, senha);
			comandoSQL = conexao.prepareStatement("SELECT * FROM t_cliente");
			rs = comandoSQL.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("cd_cliente"));
				cliente.setNome(rs.getString("nm_cliente"));
				cliente.setCpfCnpjCliente(rs.getString("ds_cpf_cnpj_cliente"));
				cliente.setdtNascimentoCliente(rs.getString("dt_nascimento_cliente"));
				listaClientes.add(cliente);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
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

		return listaClientes;
	}


	public static ArrayList<Cliente> listarClientes() {
		ArrayList<Cliente> lista = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement comandoSQL = null;
		ResultSet rs = null;

		try {
			conexao = DriverManager.getConnection(url, login, senha);
			comandoSQL = conexao.prepareStatement("SELECT * FROM t_cliente");
			rs = comandoSQL.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("cd_cliente"));
				cliente.setNome(rs.getString("nm_cliente"));
				cliente.setCpfCnpjCliente(rs.getString("ds_cpf_cnpj_cliente"));
				cliente.setdtNascimentoCliente(rs.getString("dt_nascimento_cliente"));
				lista.add(cliente);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
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

	public static ArrayList<Cliente> buscarPorCodigo(int numeroBuscar) {
		ArrayList<Cliente> lista = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement comandoSQL = null;
		ResultSet rs = null;

		try {
			conexao = DriverManager.getConnection(url, login, senha);
			comandoSQL = conexao.prepareStatement("SELECT * FROM t_cliente WHERE cd_cliente = ?");
			comandoSQL.setInt(1, numeroBuscar);

			rs = comandoSQL.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Cliente cliente = new Cliente(login, login, login);
					cliente.setIdCliente(rs.getInt("cd_cliente"));
					cliente.setNome(rs.getString("nm_cliente"));
					cliente.setCpfCnpjCliente(rs.getString("ds_cpf_cnpj_cliente"));
					cliente.setdtNascimentoCliente(rs.getString("dt_nascimento_cliente"));
					lista.add(cliente);
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
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

	public static boolean alterarCliente(Cliente clienteAlterar) {
	    boolean retorno = false;
	    Connection conexao = null;
	    PreparedStatement comandoSQL = null;

	    try {
	        conexao = DriverManager.getConnection(url, login, senha);
	        String sql = "UPDATE t_cliente SET nm_cliente = ?, ds_cpf_cnpj_cliente = ?, dt_nascimento_cliente = ? WHERE cd_cliente = ?";
	        comandoSQL = conexao.prepareStatement(sql);

	        comandoSQL.setString(1, clienteAlterar.getNome());
	        comandoSQL.setString(2, clienteAlterar.getCpfCnpjCliente());
	        comandoSQL.setInt(4, clienteAlterar.getIdCliente());

	        // Converta a data para o formato do banco de dados
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date dataNascimento = sdf.parse(clienteAlterar.getdtNascimentoCliente());
	        java.sql.Date sqlDate = new java.sql.Date(dataNascimento.getTime());

	        comandoSQL.setDate(3, sqlDate);

	        int linhasAfetadas = comandoSQL.executeUpdate();

	        if (linhasAfetadas > 0) {
	            retorno = true;
	        }
	    } catch (SQLException | ParseException ex) {
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


	public static boolean excluir(int idExcluir) {

		boolean retorno = false;
		Connection conexao = null;
		PreparedStatement comandoSQL = null;

		try {
			conexao = DriverManager.getConnection(url, login, senha);
			comandoSQL = conexao.prepareStatement("DELETE FROM t_cliente WHERE cd_cliente = ?");
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
	
	public static Cliente buscarPorId(int id) {
        Cliente cliente = null;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet resultSet = null;

        try {
            conexao = DriverManager.getConnection(url, login, senha);
            String sql = "SELECT * FROM t_cliente WHERE cd_cliente = ?";
            comandoSQL = conexao.prepareStatement(sql);
            comandoSQL.setInt(1, id);
            resultSet = comandoSQL.executeQuery();

            if (resultSet.next()) {
                // Se o cliente com o ID fornecido for encontrado, crie uma instância do Cliente
                cliente = new Cliente();
                cliente.setIdCliente(resultSet.getInt("cd_cliente"));
                cliente.setNome(resultSet.getString("nm_cliente"));
                cliente.setCpfCnpjCliente(resultSet.getString("ds_cpf_cnpj_cliente"));
                cliente.setdtNascimentoCliente(resultSet.getString("dt_nascimento_cliente"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Feche os recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
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

        return cliente;
    }
	public static ArrayList<Cliente> buscarPorCpf(String cpfBuscar) {
		ArrayList<Cliente> lista = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement comandoSQL = null;
		ResultSet rs = null;

		try {
			conexao = DriverManager.getConnection(url, login, senha);
			comandoSQL = conexao.prepareStatement("SELECT * FROM t_cliente WHERE ds_cpf_cnpj_cliente = ?");
			comandoSQL.setString(1, cpfBuscar);

			rs = comandoSQL.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Cliente cliente = new Cliente();
					cliente.setIdCliente(rs.getInt("cd_cliente"));
					cliente.setNome(rs.getString("nm_cliente"));
					cliente.setCpfCnpjCliente(rs.getString("ds_cpf_cnpj_cliente"));
					cliente.setdtNascimentoCliente(rs.getString("dt_nascimento_cliente"));
					lista.add(cliente);
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
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

	public static boolean verificarExistenciaCPF(String cpf) {
		Connection conexao = null;
		PreparedStatement comandoSQL = null;
		ResultSet rs = null;

		try {
			conexao = DriverManager.getConnection(url, login, senha);
			comandoSQL = conexao.prepareStatement("SELECT COUNT(*) FROM t_cliente WHERE ds_cpf_cnpj_cliente = ?");
			comandoSQL.setString(1, cpf);

			rs = comandoSQL.executeQuery();

			if (rs != null && rs.next()) {
				int count = rs.getInt(1);
				return count > 0; // Se o count for maior que zero, significa que o CPF já está cadastrado
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
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

		return false;
	}


}
