package br.com.mystore.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoBasicoModel {
	private Integer id;
	private String nome;

	public EstadoBasicoModel() {
	}

	public EstadoBasicoModel(Integer id, String descricao) {
		this.id = id;
		this.nome = descricao;
	}

	@Override
	public String toString() {
		return id + " - " + nome;
	}
}
