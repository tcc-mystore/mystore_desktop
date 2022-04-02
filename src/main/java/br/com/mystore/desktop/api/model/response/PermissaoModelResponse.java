package br.com.mystore.desktop.api.model.response;

import br.com.mystore.desktop.api.model.HeteoasModel;
import br.com.mystore.desktop.api.model.PermissaoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoModelResponse {

	private Embedded _embedded;
	private HeteoasModel _links;

	@Getter
	@Setter
	public class Embedded {
		private PermissaoModel[] permissoes;
	}
}
