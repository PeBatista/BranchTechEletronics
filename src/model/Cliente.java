package model;

import java.util.Random;

public class Cliente {


    private static final String[] NOMES_FUNCIONARIOS = {"João", "Maria", "Carlos", "Ana", "Pedro", "Fernanda"};
    private String nmFuncionario;
    private int idCliente;
    private String nome;
    private String cpfCnpjCliente;
    private String dtNascimentoCliente;
    
    public Cliente() {
    	
    }
    
    public Cliente( String nome, String cpfCnpjCliente, String dtNascimentoCliente) {
        this.nome = nome;
        this.cpfCnpjCliente = cpfCnpjCliente;
        this.dtNascimentoCliente = dtNascimentoCliente;
        Random random = new Random();
        int indice = random.nextInt(NOMES_FUNCIONARIOS.length);
        this.nmFuncionario = NOMES_FUNCIONARIOS[indice]; 
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCpfCnpjCliente() {
        return cpfCnpjCliente;
    }

    public void setCpfCnpjCliente(String cpfCnpjCliente) {
        this.cpfCnpjCliente = cpfCnpjCliente;
    }

      public String getdtNascimentoCliente() {
        return dtNascimentoCliente;
    }

    public void setdtNascimentoCliente(String dataNascimentoCliente) {
        this.dtNascimentoCliente = dataNascimentoCliente;
    }

    public String getNmFuncionario() {
        return nmFuncionario;
    }

 public void setnmFuncionario(String nmFuncionario) {
	 this.nmFuncionario = nmFuncionario;
 }


}
