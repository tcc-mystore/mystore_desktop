package br.com.mystore.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AplicacaoAutenticadaModel {

	private String access_token;
	private String token_type;
	private Long expires_in;
	private String scope;
	private String jti;

	@Override
	public String toString() {
		return "AplicacaoAutenticadaModel [access_token=" + access_token + ", token_type=" + token_type
				+ ", expires_in=" + expires_in + ", scope=" + scope + ", jti=" + jti + "]";
	}

}
