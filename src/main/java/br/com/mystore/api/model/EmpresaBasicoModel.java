package br.com.mystore.api.model;

import java.time.OffsetDateTime;

import br.com.mystore.api.model.input.EmpresaInput;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpresaBasicoModel extends EmpresaInput {
	private Integer id;
	private OffsetDateTime dataCadastro;
	private OffsetDateTime dataAtualizacao;
	private Boolean ativo;
	private EnderecoBasicoModel endereco;

}
