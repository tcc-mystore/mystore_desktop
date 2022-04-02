package br.com.mystore.desktop.api.model.input;

import lombok.Data;

@Data
public class EmpresaInput {
	private String nome;
	private String cpfCnpj;
	private String telefone;
	private EnderecoInput endereco;
}
