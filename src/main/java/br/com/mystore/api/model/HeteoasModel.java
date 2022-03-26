package br.com.mystore.api.model;

import lombok.Data;

@Data
public class HeteoasModel {
	private HeteoasLink self;
	private HeteoasLink empresas;
	private HeteoasLink inativar;
	private HeteoasLink produtos;
	private HeteoasLink formasPagamento;

	@Data
	public class HeteoasLink {
		private String href;
		private Boolean templated;
	}
}
