package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class telaInicial extends JFrame {

    private JPanel contentPane;
    private int codigoCliente;  //cdCliente

    public telaInicial() {
        initComponents();
    }

    public telaInicial(int codigoCliente) {
        this.codigoCliente = codigoCliente;
        initComponents();
    }

    public void initComponents() {
        setTitle("Página Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 790, 571);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(224, 224, 224));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel imgLogo = new JLabel("");
        imgLogo.setBounds(72, 377, 578, 155);
        imgLogo.setIcon(new ImageIcon(telaInicial.class.getResource("/img/logopi.png")));
        contentPane.add(imgLogo);

        JButton btnRegistrarVenda = new JButton("Registrar Venda");
        btnRegistrarVenda.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        btnRegistrarVenda.setBackground(new Color(224, 224, 224));
        btnRegistrarVenda.setBounds(146, 91, 211, 61);
        btnRegistrarVenda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                telaVenda JanelaCompra = new telaVenda(codigoCliente);
                setVisible(false);
                JanelaCompra.setVisible(true);
            }
        });
        contentPane.add(btnRegistrarVenda);

        JButton btnProdutos = new JButton("Produtos");
        btnProdutos.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        btnProdutos.setBackground(new Color(224, 224, 224));
        btnProdutos.setBounds(406, 91, 211, 61);
        contentPane.add(btnProdutos);

        JButton btnClientes = new JButton("Clientes");
        btnClientes.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        btnClientes.setBackground(new Color(224, 224, 224));
        btnClientes.setBounds(274, 181, 211, 61);
        contentPane.add(btnClientes);

        JButton btnRelatorio = new JButton("Relatório de Vendas");
        btnRelatorio.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        btnRelatorio.setBackground(new Color(224, 224, 224));
        btnRelatorio.setBounds(146, 264, 211, 61);
        contentPane.add(btnRelatorio);

        JButton btnFechar = new JButton("Fechar Sistema");
        btnFechar.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        btnFechar.setBackground(new Color(224, 224, 224));
        btnFechar.setBounds(406, 264, 211, 61);
        contentPane.add(btnFechar);

        // Adiciona ação para cada botão
        btnProdutos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                telaProdutos telaProdutos = new telaProdutos();
				telaProdutos.setVisible(true);
            }
        });

        btnClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                telaClientes telaClientes = new telaClientes();
				telaClientes.setVisible(true);
            }
        });

        btnRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                telaRelatorio telaRelatorio = new telaRelatorio();
				telaRelatorio.setVisible(true);
            }
        });

        btnFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                dispose();
                
            }
        });
    }
}
