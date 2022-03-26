package br.com.mystore.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {
	private Integer id;
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private CidadeModel cidade;
	private HeteoasModel _links;

	@Override
	public String toString() {
		return String.format("%s %s %s, %s, %s %s %s", this.logradouro, this.numero, this.complemento, this.bairro,
				this.cep, this.getCidade().getNome(), this.getCidade().getEstado().getNome());
	}

}
