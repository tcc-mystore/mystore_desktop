package br.com.mystore.desktop.api.model.input;

public class EmpresaAtualizaInput extends EmpresaInput {

	private EnderecoAtualizaInput endereco;

	public EnderecoAtualizaInput getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoAtualizaInput endereco) {
		this.endereco = endereco;
	}

}
