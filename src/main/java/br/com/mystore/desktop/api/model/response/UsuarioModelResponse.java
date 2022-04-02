package br.com.mystore.desktop.api.model.response;

import br.com.mystore.desktop.api.model.HeteoasModel;
import br.com.mystore.desktop.api.model.UsuarioModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModelResponse {

	private Embedded _embedded;
	private HeteoasModel _links;

	@Getter
	@Setter
	public class Embedded {
		private UsuarioModel[] usuarios;
	}
}
