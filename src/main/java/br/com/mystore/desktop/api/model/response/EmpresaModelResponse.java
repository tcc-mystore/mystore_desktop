package br.com.mystore.desktop.api.model.response;

import br.com.mystore.desktop.api.model.EmpresaModel;
import br.com.mystore.desktop.api.model.HeteoasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaModelResponse {

	private Embedded _embedded;
	private HeteoasModel _links;

	@Getter
	@Setter
	public class Embedded {
		private EmpresaModel[] empresas;
	}
}
