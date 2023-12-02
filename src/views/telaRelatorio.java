package views;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import dao.ProdutoVendidoDAO;
import model.ProdutoVendido;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
public class telaRelatorio extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField cpfField;
	private JTable table;
	private int codigoCliente;

	public telaRelatorio() {
        initComponents();
    }

    public telaRelatorio(int codigoCliente) {
        this.codigoCliente = codigoCliente;
        initComponents();
    }

	
	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 790, 571);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 224, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProdutos = new JLabel("Relatório");
		lblProdutos.setBounds(313, 40, 136, 37);
		lblProdutos.setFont(new Font("Montserrat Medium", Font.PLAIN, 29));
		contentPane.add(lblProdutos);
		
		JLabel lbCpf = new JLabel("Buscar compras por CPF");
		lbCpf.setBackground(new Color(0, 0, 0));
		lbCpf.setForeground(new Color(0, 0, 0));
		lbCpf.setFont(new Font("Dialog", Font.BOLD, 15));
		lbCpf.setBounds(277, 104, 179, 20);
		contentPane.add(lbCpf);
		
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
        cpfField.setBounds(277, 138, 179, 30);
        contentPane.add(cpfField);
	

		
		
        
        
		JButton btnAdd = new JButton("Procurar");
		btnAdd.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
		btnAdd.setBounds(300, 191, 106, 30);
		contentPane.add(btnAdd);

		btnAdd.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	 String cpfCliente = cpfField.getText();

		        // Chame o método e obtenha a lista de produtos vendidos
		        ArrayList<ProdutoVendido> produtos = ProdutoVendidoDAO.listarProdutosVendidosPorCliente(cpfCliente);

		        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"Cliente", "Produto", "Quantidade", "Valor Unitário", "Total da Compra"});
		        for (ProdutoVendido produtoVendido : produtos) {
		            model.addRow(new Object[] {
		                produtoVendido.getnmCliente(),
		                produtoVendido.getnmProduto(),
		                produtoVendido.getQuantidade(),
		                produtoVendido.getunitCompra(),
		                produtoVendido.gettotalCompra()
		            });
		        }

		        table.setModel(model);
		        
		        // Adicione o cabeçalho à tabela (se necessário)
		        JTableHeader header = table.getTableHeader();
		        header.setBackground(Color.GRAY); // Cor de fundo do cabeçalho
		        header.setForeground(Color.WHITE); // Cor do texto do cabeçalho
		    }
		});


	
				table = new JTable();		table.setModel(new DefaultTableModel(			new Object[][] {				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},				{null, null, null, null, null},			},			new String[] {				"C\u00F3digo", "Cliente", "Funcion\u00E1rio", "Valor da Compra", "Data"			}		));		table.getColumnModel().getColumn(0).setPreferredWidth(65);		table.getColumnModel().getColumn(1).setPreferredWidth(105);		table.getColumnModel().getColumn(2).setPreferredWidth(105);		table.getColumnModel().getColumn(3).setPreferredWidth(103);		table.getColumnModel().getColumn(4).setPreferredWidth(98);		table.setBounds(122, 253, 546, 240);		contentPane.add(table);
		
		JButton btnVoltar = new JButton("VOLTAR");
		btnVoltar.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
		btnVoltar.setBounds(122, 50, 89, 23);
		contentPane.add(btnVoltar);
		telaInicial telaInicial = new telaInicial();
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                    // Torna a tela clientes invisível
                    setVisible(false);

					// Torne a tela inicial visível
                    telaInicial.setVisible(true);
            	}
            }
        );
	}
}
