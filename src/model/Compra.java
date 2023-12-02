package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Compra {
    
    private int idCompra;
    private int clienteId;
    private LocalDateTime dataHoraCompra;


    public Compra() {
    }

    public Compra(int idCompra, int clienteId, String dataHoraCompra) {
        this.idCompra = idCompra;
        this.clienteId = clienteId;
        this.dataHoraCompra = LocalDateTime.parse(dataHoraCompra, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getDataHoraCompra() {
        if (this.dataHoraCompra != null) {
            return this.dataHoraCompra.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        } else {
            return "Data e hora da compra não disponíveis.";
        }
    }


    public void setDataHoraCompra(Timestamp timestamp) {
    	 this.dataHoraCompra = LocalDateTime.now();    }
}

