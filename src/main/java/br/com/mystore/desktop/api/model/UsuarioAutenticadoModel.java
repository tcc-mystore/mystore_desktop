package br.com.mystore.desktop.api.model;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAutenticadoModel extends AplicacaoAutenticadaModel {

	private String refresh_token;
	private Long usuarios_id;
	private String[] empresas;
	private String nome_completo;

	@Override
	public String toString() {
		return "UsuarioAutenticadoModel [refresh_token=" + refresh_token + ", usuarios_id=" + usuarios_id + ", empresas="
				+ Arrays.toString(empresas) + ", nome_completo=" + nome_completo + ", getAccess_token()="
				+ getAccess_token() + ", getToken_type()=" + getToken_type() + ", getExpires_in()=" + getExpires_in()
				+ ", getScope()=" + getScope() + ", getJti()=" + getJti() + "]";
	}

}
