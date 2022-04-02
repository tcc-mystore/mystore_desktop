package br.com.mystore.desktop.api.model.response;

import br.com.mystore.desktop.api.model.EstadoModel;
import br.com.mystore.desktop.api.model.HeteoasModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoModelResponse {

	private Embedded _embedded;
	private HeteoasModel _links;

	@Getter
	@Setter
	public class Embedded {
		private EstadoModel[] estados;
	}
}
