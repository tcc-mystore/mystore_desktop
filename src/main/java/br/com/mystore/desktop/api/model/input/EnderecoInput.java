package br.com.mystore.desktop.api.model.input;

import lombok.Data;

@Data
public class EnderecoInput {
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private CidadeInput cidade;
}
