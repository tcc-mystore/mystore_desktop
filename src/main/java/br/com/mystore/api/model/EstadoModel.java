package br.com.mystore.api.model;

import lombok.Data;

@Data
public class EstadoModel {
	private Integer id;
	private String nome;

	@Override
	public String toString() {
		return this.nome;
	}

}
