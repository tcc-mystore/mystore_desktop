package br.com.mystore.desktop.api.model;

import lombok.Data;

@Data
public class GrupoModel {
	private Integer id;
	private String nome;

	@Override
	public String toString() {
		return this.nome;
	}

}
