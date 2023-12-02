package model;

import java.util.Date;

public class ProdutoVendido {
    
    private int produtoId;
    private int compraId;
    private int quantidade;
    private String nmFuncionario;
    private String nmCliente;
    private String nmProduto;
    private double totalCompra;
    private double unitCompra;
    private Date dataHoraCompra;
    
    
    public ProdutoVendido() {
    }

    public ProdutoVendido(int produtoId, int compraId, int quantidade) {
        this.produtoId = produtoId;
        this.compraId = compraId;
        this.quantidade = quantidade;
        
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

	public String getnmFuncionario() {
		return nmFuncionario;
	}

	public void setnmFuncionario(String nomeFuncinario) {
		this.nmFuncionario = nomeFuncinario;
	}
	public String getnmCliente() {
		return nmCliente;
	}

	public void setnnmCliente(String nmCliente) {
		this.nmCliente = nmCliente;
	}
	
	public String getnmProduto() {
		return nmProduto;
	}

	public void setnmProduto(String nmProduto) {
		this.nmProduto = nmProduto;
	}

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    
    public Double gettotalCompra() {
        return totalCompra;
    }

    public void settotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }
    
    public Double getunitCompra() {
        return unitCompra;
    }

    public void setunitCompra(Double unitCompra) {
        this.unitCompra = unitCompra;
    }
    public Date getDataHoraCompra() {
        return dataHoraCompra;
    }

    public void setDataHoraCompra(Date dataHoraCompra) {
        this.dataHoraCompra = dataHoraCompra;
    }
}