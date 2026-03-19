package main;

import java.time.LocalDate;

public class Cliente {
	
	private String nome;
	private LocalDate dataInclusao;
	private String estado;
	
	public Cliente() {
		this.nome = "";
		this.dataInclusao = LocalDate.now();
		this.estado = "";
	}

	public Cliente(String nome, LocalDate dataInclusao, String estado) {
		super();
		this.nome = nome;
		this.dataInclusao = dataInclusao;
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(LocalDate dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}