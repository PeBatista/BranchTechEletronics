package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import model.Funcionario;

public class FuncionarioDAO {

    static String url = "jdbc:mysql://127.0.0.1:3306/ado";
    static String login = "root";
    static String senha = "";
	public static boolean autenticar(String cpf) {
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet rs = null;

        try {
            conexao = DriverManager.getConnection(url, login, senha);
            comandoSQL = conexao.prepareStatement("SELECT * FROM t_funcionario WHERE cpf = ?");
            comandoSQL.setString(1, cpf);

            rs = comandoSQL.executeQuery();

            // Se houver um resultado, o funcionário existe
            return rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Certifique-se de fechar as conexões e recursos
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // Se algo deu errado, retorne falso
        return false;
    }
	
}
