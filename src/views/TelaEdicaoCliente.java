package views;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import dao.ClienteDAO;
import model.Cliente;

public class TelaEdicaoCliente extends JFrame {
    private JPanel contentPane;
    private JTextField nomeField;
    private JFormattedTextField cpfField;
    private JFormattedTextField dtnField;
    private Cliente cliente;

    private final List<Consumer<Boolean>> onCloseListeners = new ArrayList<>();

    public TelaEdicaoCliente(JFrame parent, Cliente cliente) {
    	setAlwaysOnTop(true);
        this.cliente = cliente;
        initComponents();
        setLocationRelativeTo(parent);
    }

    public void addOnCloseListener(Consumer<Boolean> listener) {
        onCloseListeners.add(listener);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 534, 397);
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblNome = new JLabel("Nome:");
        contentPane.add(lblNome);

        nomeField = new JTextField();
        nomeField.setText(cliente.getNome());
        contentPane.add(nomeField);

        JLabel lblCPF = new JLabel("CPF:");
        contentPane.add(lblCPF);

        try {
            MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
            cpfFormatter.setPlaceholderCharacter(' ');
            cpfField = new JFormattedTextField(cpfFormatter);
            cpfField.setText(cliente.getCpfCnpjCliente());
            contentPane.add(cpfField);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblNascimento = new JLabel("Data de Nascimento:");
        contentPane.add(lblNascimento);

        try {
            MaskFormatter dtnFormatter = new MaskFormatter("##/##/####");
            dtnFormatter.setPlaceholderCharacter(' ');
            dtnField = new JFormattedTextField(dtnFormatter);
            dtnField.setText(cliente.getdtNascimentoCliente());
            contentPane.add(dtnField);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarAlteracoes();
            }
        });
        contentPane.add(btnSave);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                disposeAndNotifyListeners(false);
            }
        });
        contentPane.add(btnCancel);
    }

    private void salvarAlteracoes() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String dataNascimento = dtnField.getText();

        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha pelo menos os campos de Nome e CPF.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        cliente.setNome(nome);
        cliente.setCpfCnpjCliente(cpf);
        cliente.setdtNascimentoCliente(dataNascimento);

        boolean sucesso = ClienteDAO.alterarCliente(cliente);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso!");
            disposeAndNotifyListeners(true);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar as alterações. Verifique os campos e tente novamente.");
        }
    }

    private void disposeAndNotifyListeners(boolean changesSaved) {
        dispose();
        onCloseListeners.forEach(listener -> listener.accept(changesSaved));
    }
}
