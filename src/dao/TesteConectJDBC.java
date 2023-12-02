package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConectJDBC {
	static String url = "jdbc:mysql://127.0.0.1:3306/ado";

	static String login = "root";
	static String senha = "";

	public static void main(String[] args) {
		Connection conexao = null;
		   try {
	            // Carrega o driver JDBC
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Estabelece a conexão com o banco de dados
	            conexao = DriverManager.getConnection(url, login, senha);

	            // Verifica se a conexão foi bem-sucedida
	            if (conexao != null) {
	                System.out.println("Conexão estabelecida com sucesso!");
	            } else {
	                System.out.println("Falha ao estabelecer conexão.");
	            }

	        } catch (ClassNotFoundException e) {
	            System.out.println("Driver JDBC não encontrado.");
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.out.println("Erro ao conectar ao banco de dados.");
	            e.printStackTrace();
	        } finally {
	            // Fecha a conexão no bloco finally para garantir que seja fechada, independentemente do que aconteça
	            try {
	                if (conexao != null && !conexao.isClosed()) {
	                    conexao.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	}

}
