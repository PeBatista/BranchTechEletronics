package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.ProdutoDAO;
import model.Produto;

public class TelaEdicaoProduto extends JFrame {
    private JTextField nomeFieldEdicao;
    private JTextField descFieldEdicao;
    private JTextField valorFieldEdicao;
    private JCheckBox jCheckBoxAtivoEdicao;
    private Produto produtoParaEditar;
    private telaProdutos telaProdutosReferencia; // Referência à telaProdutos para atualização da tabela

    public TelaEdicaoProduto(telaProdutos telaProdutos, Produto produto) {
    	setAlwaysOnTop(true);
        this.telaProdutosReferencia = telaProdutos;
        this.produtoParaEditar = produto;
        initComponents();
        preencherCampos();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Editar Produto");
        setBounds(100, 100, 245, 268);
        getContentPane().setLayout(new GridLayout(5, 2, 10, 10));

        // Campos de edição
        JLabel lblNome = new JLabel("Nome:");
        nomeFieldEdicao = new JTextField();

        JLabel lblDescricao = new JLabel("Descrição:");
        descFieldEdicao = new JTextField();

        JLabel lblValor = new JLabel("Valor:");
        valorFieldEdicao = new JTextField();

        JLabel lblAtivo = new JLabel("Ativo:");
        jCheckBoxAtivoEdicao = new JCheckBox();

        // Adicione os componentes ao layout
        getContentPane().add(lblNome);
        getContentPane().add(nomeFieldEdicao);
        getContentPane().add(lblDescricao);
        getContentPane().add(descFieldEdicao);
        getContentPane().add(lblValor);
        getContentPane().add(valorFieldEdicao);
        getContentPane().add(lblAtivo);
        getContentPane().add(jCheckBoxAtivoEdicao);

        // Botão para salvar as alterações
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        getContentPane().add(btnSalvar);

        pack();
        setLocationRelativeTo(null);
    }

    private void preencherCampos() {
        nomeFieldEdicao.setText(produtoParaEditar.getNome());
        descFieldEdicao.setText(produtoParaEditar.getDescricao());
        valorFieldEdicao.setText(String.valueOf(produtoParaEditar.getPreco()));
        jCheckBoxAtivoEdicao.setSelected(produtoParaEditar.getAtivo());
    }

    private void jButtonSalvarActionPerformed(ActionEvent evt) {
        // Atualize o produtoParaEditar com os novos valores dos campos
        produtoParaEditar.setNome(nomeFieldEdicao.getText());
        produtoParaEditar.setDescricao(descFieldEdicao.getText());
        produtoParaEditar.setPreco(Double.parseDouble(valorFieldEdicao.getText()));
        produtoParaEditar.setAtivo(jCheckBoxAtivoEdicao.isSelected());

        // Chame o método de alteração do ProdutoDAO
        boolean alterado = ProdutoDAO.alterarProduto(produtoParaEditar);

        if (alterado) {
            JOptionPane.showMessageDialog(this, "Produto alterado com sucesso!");
            dispose(); // Feche a tela de edição após a alteração
            telaProdutosReferencia.atualizarTabela(); // Atualize a tabela na telaProdutos
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar o produto.");
        }
    }
}
