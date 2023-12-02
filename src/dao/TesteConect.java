package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConect {

    public static void main(String[] args) {
        // Configurações de conexão
        String url = "jdbc:mysql://127.0.0.1:3306/ado";
        String login = "root";
        String senha = "";

        // Objeto de conexão
        Connection conexao = null;

        try {
            // Estabelecendo a conexão
            conexao = DriverManager.getConnection(url, login, senha);

            // Verificando se a conexão foi estabelecida com sucesso
            if (conexao != null) {
                System.out.println("Conexão bem-sucedida!");
            } else {
                System.out.println("Falha ao estabelecer a conexão.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        } finally {
            // Fechando a conexão no bloco finally para garantir que seja fechada, independentemente do que aconteça
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
