package br.com.mystore.desktop.api.model.input;

import lombok.Data;

@Data
public class CidadeInput {

	private String nome;
	private EstadoIdInput estado;
}
