package br.com.mystore.api.model;

import br.com.mystore.api.model.input.EnderecoInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoBasicoModel extends EnderecoInput {
	private Integer id;
}
