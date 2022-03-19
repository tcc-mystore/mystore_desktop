package br.com.mystore.api.model;

import java.util.Arrays;

public class UsuarioAutenticadoModel extends AplicacaoAutenticadaModel {

	private String refresh_token;
	private Long usuario_id;
	private String[] empresas;
	private String nome_completo;

	public String getRefreshToken() {
		return refresh_token;
	}

	public void setRefreshToken(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public Long getUsuarioId() {
		return usuario_id;
	}

	public void setUsuarioId(Long usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String[] getEmpresas() {
		return empresas;
	}

	public void setEmpresas(String[] empresas) {
		this.empresas = empresas;
	}

	public String getNomeCompleto() {
		return nome_completo;
	}

	public void setNomeCompleto(String nome_completo) {
		this.nome_completo = nome_completo;
	}

	@Override
	public String toString() {
		return "UsuarioAutenticadoModel [refresh_token=" + refresh_token + ", usuario_id=" + usuario_id + ", empresas="
				+ Arrays.toString(empresas) + ", nome_completo=" + nome_completo + ", getAccessToken()="
				+ getAccessToken() + ", getTokenType()=" + getTokenType() + ", getExpiresIn()=" + getExpiresIn()
				+ ", getScope()=" + getScope() + ", getJti()=" + getJti() + "]";
	}

	
	
}
