package br.com.mystore.api.model.response;

import br.com.mystore.api.model.EstadoBasicoModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoModelResponse {
	private Embedded _embedded;

	@Getter
	@Setter
	public class Embedded {
		private EstadoBasicoModel[] estados;
	}
}
