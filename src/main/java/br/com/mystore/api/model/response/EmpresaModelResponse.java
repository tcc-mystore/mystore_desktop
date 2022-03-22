package br.com.mystore.api.model.response;

import br.com.mystore.api.model.EmpresaBasicoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaModelResponse {

	private Embedded _embedded;

	@Getter
	@Setter
	public class Embedded {
		private EmpresaBasicoModel[] empresas;
	}
}
