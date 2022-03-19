package br.com.mystore.api.model;

public class AplicacaoAutenticadaModel {

	private String access_token;
	private String token_type;
	private Long expires_in;
	private String scope;
	private String jti;

	public String getAccessToken() {
		return access_token;
	}

	public void setAccessToken(String access_token) {
		this.access_token = access_token;
	}

	public String getTokenType() {
		return token_type;
	}

	public void setTokenType(String token_type) {
		this.token_type = token_type;
	}

	public Long getExpiresIn() {
		return expires_in;
	}

	public void setExpiresIn(Long expires_in) {
		this.expires_in = expires_in;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

	@Override
	public String toString() {
		return "AplicacaoAutenticadaModel [access_token=" + access_token + ", token_type=" + token_type + ", expires_in="
				+ expires_in + ", scope=" + scope + ", jti=" + jti + "]";
	}

	
	
}
