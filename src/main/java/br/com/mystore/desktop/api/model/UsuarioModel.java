package br.com.mystore.desktop.api.model;

import lombok.Data;

@Data
public class UsuarioModel {

	private Integer id;
	private String nome;
	private String email;
	private Boolean ativo;

	@Override
	public String toString() {
		return this.nome;
	}

}
