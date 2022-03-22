package br.com.mystore.api.model.response;

import br.com.mystore.api.model.CidadeBacicoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModelResponse {

	private Embedded _embedded;

	@Getter
	@Setter
	public class Embedded {
		private CidadeBacicoModel[] cidades;
	}
}
