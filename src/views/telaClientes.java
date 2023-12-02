package views;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import dao.ClienteDAO;
import model.Cliente;

public class telaClientes extends JFrame {
    private static final long serialVersionUID = 1L;
    private telaInicial telaInicialReferencia;
    private JPanel contentPane;
    private JTextField nomeField;
    private JFormattedTextField cpfField;
    private JFormattedTextField dtnField;
    private JTable table;
    private JButton btnAdd;

    public telaClientes(telaInicial telaInicial) {
        this.telaInicialReferencia = telaInicial;
        initComponents();
    }

    public telaClientes() {
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

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"Código", "Nome", "CPF", "Data de Nascimento"}
        ));
        table.getColumnModel().getColumn(1).setPreferredWidth(118);
        table.getColumnModel().getColumn(2).setPreferredWidth(90);
        table.getColumnModel().getColumn(3).setPreferredWidth(105);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(122, 253, 546, 240);
        contentPane.add(scrollPane);

        JLabel lblClientes = new JLabel("Clientes");
        lblClientes.setBounds(313, 40, 136, 37);
        lblClientes.setFont(new Font("Montserrat Medium", Font.PLAIN, 29));
        contentPane.add(lblClientes);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        lblNome.setBounds(188, 128, 46, 14);
        contentPane.add(lblNome);

        nomeField = new JTextField();
        nomeField.setBackground(new Color(192, 192, 192));
        nomeField.setBounds(188, 144, 125, 20);
        contentPane.add(nomeField);
        nomeField.setColumns(10);

        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        lblCPF.setBounds(323, 128, 46, 14);
        contentPane.add(lblCPF);

        JLabel lblNascimento = new JLabel("Data de Nascimento:");
        lblNascimento.setFont(new Font("Montserrat Medium", Font.PLAIN, 13));
        lblNascimento.setBounds(458, 128, 141, 14);
        contentPane.add(lblNascimento);

        try {
            MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
            cpfFormatter.setPlaceholderCharacter(' ');
            cpfField = new JFormattedTextField(cpfFormatter);
            cpfField.setBounds(323, 144, 125, 20);
            contentPane.add(cpfField);

            MaskFormatter dtnFormatter = new MaskFormatter("##/##/####");
            dtnFormatter.setPlaceholderCharacter(' ');
            dtnField = new JFormattedTextField(dtnFormatter);
            dtnField.setBounds(458, 144, 125, 20);
            contentPane.add(dtnField);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnAdd = new JButton("Adicionar");
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
        
        
        
        
        

        atualizarTabela();
    }

    private void jButtonCadastrarActionPerformed(ActionEvent evt) {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String dataNascimento = dtnField.getText();

        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha pelo menos os campos de Nome e CPF.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cpfJaCadastrado(cpf)) {
            JOptionPane.showMessageDialog(this, "Este CPF já está cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(nome, cpf, dataNascimento);
        ClienteDAO clienteDao = new ClienteDAO();
        clienteDao.salvar(cliente);

        JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        nomeField.setText("");
        cpfField.setText("");
        dtnField.setText("");
        atualizarTabela();
    }

    private void jButtonEditarActionPerformed(ActionEvent evt) {
        int linhaSelecionada = table.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.");
        } else {
            int idCliente = (int) table.getValueAt(linhaSelecionada, 0);
            Cliente clienteParaEditar = ClienteDAO.buscarPorId(idCliente);

            if (clienteParaEditar != null) {
                TelaEdicaoCliente telaEdicaoCliente = new TelaEdicaoCliente(this, clienteParaEditar);

                telaEdicaoCliente.addOnCloseListener(changesSaved -> {
                    if (changesSaved) {
                        atualizarTabela();
                    }
                });

                telaEdicaoCliente.setVisible(true);
            }
        }
    }
    

    private void jButtonRemoverActionPerformed(ActionEvent evt) {
        int linhaSelecionada = table.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para remover.");
        } else {
            int codigoCliente = (int) table.getValueAt(linhaSelecionada, 0);

            int opcao = JOptionPane.showConfirmDialog(this, "Deseja remover este cliente?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                boolean removido = ClienteDAO.excluir(codigoCliente);

                if (removido) {
                    JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!");
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao remover o cliente.");
                }
            }
        }
    }

    private void voltarTelaInicial() {
        setVisible(false);
        telaInicialReferencia.setVisible(true);
    }

    private boolean cpfJaCadastrado(String cpf) {
        return ClienteDAO.verificarExistenciaCPF(cpf);
    }

    private void atualizarTabela() {
        ArrayList<Cliente> listaClientes = ClienteDAO.listarClientes();
        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"Código", "Nome", "CPF", "Data de Nascimento"});

        for (Cliente cliente : listaClientes) {
            model.addRow(new Object[] {cliente.getIdCliente(), cliente.getNome(), cliente.getCpfCnpjCliente(), cliente.getdtNascimentoCliente()});
        }

        this.table.setModel(model);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // Cliente selecionado, faça algo se necessário
            }
        });
    }
}
