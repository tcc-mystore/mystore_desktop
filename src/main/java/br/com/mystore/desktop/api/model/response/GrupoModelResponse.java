package br.com.mystore.desktop.api.model.response;

import br.com.mystore.desktop.api.model.GrupoModel;
import br.com.mystore.desktop.api.model.HeteoasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoModelResponse {

	private Embedded _embedded;
	private HeteoasModel _links;

	@Getter
	@Setter
	public class Embedded {
		private GrupoModel[] grupos;
	}
}
