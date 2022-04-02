package br.com.mystore.desktop.core;

public enum AccessConfig {

	URL("http://mystore-app.ddns.net:8181", "URL da API"),
	USER_MANAGER("mystore-manager", "Usuário de Gerenciamento dos Sistemas"),
	PASSWORD_MANAGER("123321", "Senha do Usuário de Gerenciamento dos Sistemas"),
	USER("mystore-desktop", "Usuário de Acesso ao Sistema"),
	PASSWORD("123321", "Senha do Usuário de Acesso ao Sistema");

	private String valor;
	private String descricao;

	AccessConfig(String valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}

	public String getValor() {
		return this.valor;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
