package br.com.mystore.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpresaBasicoModel {
	private Integer id;
	private String nome;
	private String cpfCnpj;
	private Boolean ativo;
	private EnderecoBasicoModel endereco;
}
