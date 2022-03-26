package br.com.mystore.api.model.response;

import br.com.mystore.api.model.EmpresaModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaModelResponse {

	private Embedded _embedded;

	@Getter
	@Setter
	public class Embedded {
		private EmpresaModel[] empresas;
	}
}
