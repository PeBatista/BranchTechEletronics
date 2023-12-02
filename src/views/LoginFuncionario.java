package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import java.util.ArrayList;
import dao.ClienteDAO;
import model.Cliente;

public class LoginFuncionario extends JFrame {

	private JPanel contentPane;
	private JTextField cpfField;
	private telaInicial telaInicialReferencia;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		LoginFuncionario frame = new LoginFuncionario();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public LoginFuncionario() {
		setTitle("Tela de Cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 575, 359);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 224, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("Bem-vindo, Funcionario!");
		lblTitulo.setForeground(new Color(0, 0, 0));
		lblTitulo.setFont(new Font("Montserrat Medium", Font.PLAIN, 24));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(73, 29, 414, 30);
		contentPane.add(lblTitulo);

		JLabel lblCPF = new JLabel("Informe o CPF do cliente:");
		lblCPF.setBackground(new Color(0, 0, 0));
		lblCPF.setForeground(new Color(0, 0, 0));
		lblCPF.setFont(new Font("Montserrat Medium", Font.PLAIN, 14));
		lblCPF.setBounds(184, 108, 169, 20);
		contentPane.add(lblCPF);

		try {
			MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
			cpfFormatter.setPlaceholderCharacter(' ');
			cpfField = new JFormattedTextField(cpfFormatter);
			cpfField.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}

		cpfField.setBackground(new Color(192, 192, 192));
		cpfField.setForeground(new Color(0, 0, 0));
		cpfField.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
		cpfField.setBounds(184, 132, 179, 30);
		contentPane.add(cpfField);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBackground(new Color(192, 192, 192));
		btnEntrar.setForeground(new Color(0, 0, 0));
		btnEntrar.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
		btnEntrar.setBounds(229, 200, 89, 30);
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonLoginActionPerformed(evt);
			}
		});

		contentPane.add(btnEntrar);

		JButton btnCadastrarCliente = new JButton("Cadastrar cliente");


	
		btnCadastrarCliente.setForeground(Color.BLACK);
		btnCadastrarCliente.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnCadastrarCliente.setBackground(Color.LIGHT_GRAY);
		btnCadastrarCliente.setBounds(198, 241, 147, 30);
		btnCadastrarCliente.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	            jButtonLoginActionPerforme(evt);
	            
	        }
	});
		contentPane.add(btnCadastrarCliente);
	}
	 private void jButtonLoginActionPerforme(ActionEvent evt) {
           
           telaClientes telacliente = new telaClientes();
           this.setVisible(false);
           telacliente.setVisible(true);
   }

	private void jButtonLoginActionPerformed(ActionEvent evt) {
		String cpf = cpfField.getText();

		// Verificando se este cpf existe
		ArrayList<Cliente> lista = ClienteDAO.buscarPorCpf(cpf);
		if (lista != null) {
			JOptionPane.showMessageDialog(rootPane, "Sucesso!");

			int codigoCliente = lista.get(0).getIdCliente();

			telaInicial JanelaCompra = new telaInicial(codigoCliente);
			System.out.println(codigoCliente);
			this.setVisible(false);
			JanelaCompra.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(rootPane, "O CPF não existe!");
		}

	}
}
