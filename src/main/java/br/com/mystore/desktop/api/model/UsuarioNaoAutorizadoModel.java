package br.com.mystore.desktop.api.model;

import java.time.OffsetDateTime;

public class UsuarioNaoAutorizadoModel extends AplicacaoAutenticadaModel {

	private OffsetDateTime timestamp;
	private Integer status;
	private String error;
	private String path;

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
