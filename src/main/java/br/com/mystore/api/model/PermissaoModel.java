package br.com.mystore.api.model;

import lombok.Data;

@Data
public class PermissaoModel {

	private Integer id;
	private String nome;
	private String descricao;

	@Override
	public String toString() {
		return this.nome;
	}
}
