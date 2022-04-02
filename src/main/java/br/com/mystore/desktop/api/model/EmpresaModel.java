package br.com.mystore.desktop.api.model;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class EmpresaModel {
	private Integer id;
	private String nome;
	private String cpfCnpj;
	private String telefone;
	private Boolean ativo;
	private OffsetDateTime dataCadastro;
	private OffsetDateTime dataAtualizacao;
	private EnderecoModel endereco;
	private HeteoasModel _links;
}
