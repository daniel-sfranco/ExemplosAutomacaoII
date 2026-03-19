package main;

import java.time.LocalDate;

public class Fatura {
	
	private String codigo;
	private float valor;
	private LocalDate data;
	private Cliente cliente;
	
	public Fatura() {
		this.codigo = "";
		this.valor = 0f;
		this.data = LocalDate.now();
		this.cliente = new Cliente();
	}

	public Fatura(String codigo, float valor, LocalDate data, Cliente cliente) {
		super();
		this.codigo = codigo;
		this.valor = valor;
		this.data = data;
		this.cliente = cliente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}