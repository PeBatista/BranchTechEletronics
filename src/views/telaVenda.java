package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dao.ClienteDAO;
import dao.CompraDAO;
import dao.ProdutoDAO;
import dao.ProdutoVendidoDAO;
import model.Compra;
import model.ProdutoVendido;

public class telaVenda extends JFrame {
    private JTextField qtdField;
    private JTextField descontoField;
    private JTable tableProdutos;
    private int codigoCliente;
    private JComboBox<String> jComboBoxProdutos;

    public telaVenda() {
        initComponents();
    }

    public telaVenda(int codigoCliente) {
        this.codigoCliente = codigoCliente;
        initComponents();
        carregarProdutosNoComboBox();
    }

    public void onProdutoCadastrado() {
        carregarProdutosNoComboBox();
    }

    public void initComponents() {
        setTitle("Tela de Vendas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 559, 571);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(224, 224, 224));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnInserir = new JButton("Inserir Venda");
        btnInserir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	 String produtoSelecionado = (String) jComboBoxProdutos.getSelectedItem();
                 int quantidade = Integer.parseInt(qtdField.getText());
                 int idProduto = ProdutoDAO.obterIdPeloNome(produtoSelecionado);

                 if (produtoJaAdicionado(idProduto)) {
                    System.out.println("ERRO");
                     return; // Não faz nada se o produto já foi adicionado
                 }

                 Compra novaCompra = new Compra();
                 novaCompra.setClienteId(codigoCliente);
                 novaCompra.getDataHoraCompra();

                 CompraDAO.salvar(novaCompra);

                 int idCompra = CompraDAO.buscarPorCodigo(codigoCliente);

                 ProdutoVendido newProduto = new ProdutoVendido(idProduto, idCompra, quantidade);

                 ProdutoVendidoDAO.salvar(newProduto);

                 System.out.println("SUCESSO");

                 jComboBoxProdutos.setSelectedIndex(0);
                 qtdField.setText("");
             }
                
            
        });

      

        btnInserir.setBackground(new Color(106, 160, 106));
        btnInserir.setForeground(new Color(0, 0, 0));
        btnInserir.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        btnInserir.setBounds(20, 453, 193, 57);
        contentPane.add(btnInserir);

        Panel panelItem = new Panel();
        panelItem.setBackground(new Color(192, 192, 192));
        panelItem.setBounds(10, 10, 519, 145);
        contentPane.add(panelItem);
        panelItem.setLayout(null);

        jComboBoxProdutos = new JComboBox<>();
        jComboBoxProdutos.setBounds(10, 28, 355, 32);
        jComboBoxProdutos.setFont(new Font("Dialog", Font.BOLD, 16));
        panelItem.add(jComboBoxProdutos);

        JLabel lblNomeProduto = new JLabel("Selecione o Produto:");
        lblNomeProduto.setBounds(10, 11, 168, 15);
        lblNomeProduto.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        panelItem.add(lblNomeProduto);

        qtdField = new JTextField();
        qtdField.setForeground(Color.BLACK);
        qtdField.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        qtdField.setBackground(new Color(224, 224, 224));
        qtdField.setBounds(30, 107, 42, 22);
        panelItem.add(qtdField);

        JLabel lblQtd = new JLabel("Quantidade:");
        lblQtd.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        lblQtd.setBounds(21, 91, 168, 15);
        panelItem.add(lblQtd);

        descontoField = new JTextField();
 


        JButton btnCancelar = new JButton("Cancelar Venda");
        btnCancelar.setForeground(new Color(0, 0, 0));
        btnCancelar.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        btnCancelar.setBackground(new Color(255, 106, 106));
        btnCancelar.setBounds(302, 453, 193, 57);
        contentPane.add(btnCancelar);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                telaInicial telaInicial = new telaInicial();
                telaInicial.setVisible(true);
            }
        });

        tableProdutos = new JTable();
        tableProdutos.setBackground(new Color(255, 255, 255));
        tableProdutos.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Código", "Nome", "Valor Unitário", "Quantidade", "Desconto", "Valor Total"
                }
        ));
        tableProdutos.getColumnModel().getColumn(1).setPreferredWidth(101);
        tableProdutos.getColumnModel().getColumn(2).setPreferredWidth(97);
        tableProdutos.getColumnModel().getColumn(3).setPreferredWidth(106);
        tableProdutos.getColumnModel().getColumn(4).setPreferredWidth(96);
        tableProdutos.getColumnModel().getColumn(5).setPreferredWidth(105);
        tableProdutos.setBounds(10, 154, 519, 288);
        contentPane.add(tableProdutos);

        // ... (código anterior)

        // Removido o trecho duplicado onde jComboBoxProdutos é definido
    }

//    private void carregarClientesNoComboBox() {
//        ArrayList<String> nomesClientes = ClienteDAO.obterNomesClientes();
//        jComboBoxClientes.setModel(new DefaultComboBoxModel<>(nomesClientes.toArray(new String[0])));
//    }

    private void carregarProdutosNoComboBox() {
        jComboBoxProdutos.removeAllItems(); // Limpa os itens existentes no ComboBox

        ArrayList<String> listaProdutos = ProdutoDAO.obterNomesProdutos();

        // Adiciona os produtos ao ComboBox
        for (String produto : listaProdutos) {
            jComboBoxProdutos.addItem(produto);
        }
    }

    private void jButtonAdicionarActionPerformed(ActionEvent evt) {
        String produtoSelecionado = (String) jComboBoxProdutos.getSelectedItem();
        int quantidade = Integer.parseInt(qtdField.getText());
        int idProduto = ProdutoDAO.obterIdPeloNome(produtoSelecionado);

        if (produtoJaAdicionado(idProduto)) {
            JOptionPane.showMessageDialog(this, "Este produto já foi adicionado à compra.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return; // Não faz nada se o produto já foi adicionado
        }

        Compra novaCompra = new Compra();
        novaCompra.setClienteId(codigoCliente);
        novaCompra.getDataHoraCompra();

        CompraDAO.salvar(novaCompra);

        int idCompra = CompraDAO.buscarPorCodigo(codigoCliente);

        ProdutoVendido newProduto = new ProdutoVendido(idProduto, idCompra, quantidade);

        ProdutoVendidoDAO.salvar(newProduto);

       

        JOptionPane.showMessageDialog(this, "Produto adicionado à compra com sucesso!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

        // Não está claro onde jTextFieldQuantidade é definido, você pode precisar ajustar esse trecho
        jComboBoxProdutos.setSelectedIndex(0);
        qtdField.setText("");
    }

    private boolean produtoJaAdicionado(int idProduto) {
        // Lógica para verificar se o produto já está na lista de produtos da compra
        int idCompra = CompraDAO.buscarPorCodigo(codigoCliente);

        if (idCompra != -1) {
            return ProdutoVendidoDAO.produtoJaAdicionado(idProduto, idCompra);
        } else {
            return false;
        }
    }
}
