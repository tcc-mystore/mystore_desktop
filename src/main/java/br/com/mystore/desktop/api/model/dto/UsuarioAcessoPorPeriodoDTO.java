package br.com.mystore.desktop.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioAcessoPorPeriodoDTO {

	private String nome;
	private Integer quantidadeDeAcessos;

}
