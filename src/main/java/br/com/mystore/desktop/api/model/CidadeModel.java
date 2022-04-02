package br.com.mystore.desktop.api.model;

import lombok.Data;

@Data
public class CidadeModel {

	private Integer id;
	private String nome;
	private EstadoModel estado;

	@Override
	public String toString() {
		return this.nome;
	}
}
