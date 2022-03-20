package br.com.mystore.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCidadeBacicoModel {

	private Embedded _embedded;

	@Getter
	@Setter
	public class Embedded {
		private CidadeBacicoModel[] cidades;
	}
}
