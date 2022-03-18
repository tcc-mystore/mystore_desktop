package br.com.mystore.api.model;

public class AuthorizationServerErroModel extends AplicacaoAutenticadaModel {

	private String error;
	private String errorDescription;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}
