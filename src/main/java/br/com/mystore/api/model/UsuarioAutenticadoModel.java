package br.com.mystore.api.model;

public class UsuarioAutenticadoModel extends AplicacaoAutenticadaModel {

	private String refreshToken;
	private Long usuarioId;
	private String[] empresas;
	private String nomeCompleto;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String[] getEmpresas() {
		return empresas;
	}

	public void setEmpresas(String[] empresas) {
		this.empresas = empresas;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

}
