package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import dao.ProdutoDAO;
import model.Produto;

public class telaProdutos extends JFrame {
    private static final long serialVersionUID = 1L;
    private telaInicial telaInicialReferencia;
    private JPanel contentPane;
    private JTextField nomeField;
    private JTable table;
    private JTextField descField;
    private JTextField valorField;
    private JCheckBox jCheckBoxAtivo;
    private JButton btnAdd; // Adicionei a declaração do botão

    public telaProdutos(telaInicial telaInicial) {
        this.telaInicialReferencia = telaInicial;
        initComponents();
    }

    public telaProdutos() {
        initComponents();
    }

    public void setTelaInicial(telaInicial telaInicial) {
        this.telaInicialReferencia = telaInicial;
    }

    public void initComponents() {
        contentPane = new JPanel();
        contentPane.setBackground(new Color(224, 224, 224));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 790, 571);

        // Inicializando a tabela
        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"Código", "Nome do Produto", "Descrição", "Valor Unitário"}
        ));
        table.getColumnModel().getColumn(1).setPreferredWidth(118);
        table.getColumnModel().getColumn(2).setPreferredWidth(90);
        table.getColumnModel().getColumn(3).setPreferredWidth(105);

        // Adicionando a tabela a um JScrollPane para suportar rolagem
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(122, 253, 546, 240);
        contentPane.add(scrollPane);

        JLabel lblProdutos = new JLabel("Produtos");
        lblProdutos.setBounds(313, 40, 136, 37);
        lblProdutos.setFont(new Font("Montserrat Medium", Font.PLAIN, 29));
        contentPane.add(lblProdutos);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        lblNome.setBounds(188, 128, 46, 14);
        contentPane.add(lblNome);

        nomeField = new JTextField();
        nomeField.setBackground(new Color(192, 192, 192));
        nomeField.setBounds(188, 144, 125, 20);
        contentPane.add(nomeField);
        nomeField.setColumns(10);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        lblDescricao.setBounds(323, 128, 68, 14);
        contentPane.add(lblDescricao);

        JLabel lblValorUnit = new JLabel("Valor Unitário:");
        lblValorUnit.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        lblValorUnit.setBounds(458, 128, 99, 14);
        contentPane.add(lblValorUnit);

        descField = new JTextField();
        descField.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        descField.setBackground(Color.LIGHT_GRAY);
        descField.setBounds(323, 144, 125, 20);
        contentPane.add(descField);

        valorField = new JTextField();
        valorField.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        valorField.setBackground(Color.LIGHT_GRAY);
        valorField.setBounds(458, 144, 125, 20);
        contentPane.add(valorField);

        // Adicionando a inicialização do jCheckBoxAtivo
        jCheckBoxAtivo = new JCheckBox("Ativo");
        jCheckBoxAtivo.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        jCheckBoxAtivo.setBounds(188, 170, 70, 20);
        contentPane.add(jCheckBoxAtivo);

        // Adicionando a chamada para inicializar a tabela
        atualizarTabela();

        btnAdd = new JButton("Adicionar"); // Corrigi a declaração do botão
        btnAdd.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        btnAdd.setBounds(205, 200, 93, 23);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonCadastrarActionPerformed(evt);
            }
        });
        contentPane.add(btnAdd);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        btnEditar.setBounds(345, 201, 93, 23);
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });
        contentPane.add(btnEditar);

        JButton btnRemover = new JButton("Remover");
        btnRemover.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        btnRemover.setBounds(475, 201, 93, 23);
        btnRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonRemoverActionPerformed(evt);
            }
        });
        contentPane.add(btnRemover);

        JButton btnVoltar = new JButton("VOLTAR");
        btnVoltar.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
        btnVoltar.setBounds(122, 50, 89, 23);
        contentPane.add(btnVoltar);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 setVisible(false);
                 telaInicial telaInicial = new telaInicial();
                 telaInicial.setVisible(true);
            }
        });
    }
   

    private void jButtonCadastrarActionPerformed(ActionEvent evt) {
        String nome = nomeField.getText();
        String descricao = descField.getText();
        double preco = Double.parseDouble(valorField.getText());
        boolean ativo = jCheckBoxAtivo.isSelected();

        Produto novoProduto = new Produto(nome, descricao, preco, ativo);

        boolean cadastrado = ProdutoDAO.salvar(novoProduto);

        if (cadastrado) {
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            nomeField.setText("");
            descField.setText("");
            valorField.setText("");
            jCheckBoxAtivo.setSelected(false);
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar o produto. Verifique os campos e tente novamente.");
        }
    }

    private void jButtonEditarActionPerformed(ActionEvent evt) {
        int linhaSelecionada = table.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
        } else {
            int codigoProduto = (int) table.getValueAt(linhaSelecionada, 0);

            // Buscar o produto pelo código
            Produto produtoParaEditar = ProdutoDAO.buscarPorCodigo(codigoProduto);
            if (produtoParaEditar != null) {
                // Abra uma nova tela para edição, passando o produtoParaEditar
                TelaEdicaoProduto telaEdicaoProduto = new TelaEdicaoProduto(this, produtoParaEditar);
                telaEdicaoProduto.setVisible(true);
            }
        }
    }

    private void jButtonRemoverActionPerformed(ActionEvent evt) {
        int linhaSelecionada = table.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.");
        } else {
            int codigoProduto = (int) table.getValueAt(linhaSelecionada, 0);

            int opcao = JOptionPane.showConfirmDialog(this, "Deseja remover este produto?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                // Chamar o método de exclusão do ProdutoDAO
                boolean removido = ProdutoDAO.excluir(codigoProduto);

                if (removido) {
                    JOptionPane.showMessageDialog(this, "Produto removido com sucesso!");
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao remover o produto.");
                }
            }
        }
    }

    private void voltarTelaInicial() {
        setVisible(false);
        telaInicialReferencia.setVisible(true);
    }

    void atualizarTabela() {
        ArrayList<Produto> listaProdutos = ProdutoDAO.listarProdutos();
        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"Código", "Nome do Produto", "Descrição", "Valor Unitário"});

        for (Produto produto : listaProdutos) {
            model.addRow(new Object[] {produto.getIdProduto(), produto.getNome(), produto.getDescricao(), produto.getPreco()});
        }

        this.table.setModel(model);

        // Adicione um ouvinte de seleção à tabela
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // Produto selecionado, faça algo se necessário
            }
        });
    }
}
