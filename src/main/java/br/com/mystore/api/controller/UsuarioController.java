package br.com.mystore.api.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import br.com.mystore.api.model.UsuarioAutenticadoModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsuarioController {
	private static final String RESOURCE_PATH = "/usuarios";

	private RestTemplate restTemplate;
	private String url;

	public List<UsuarioAutenticadoModel> listar() {
		URI resourceUri = URI.create(url + RESOURCE_PATH);

		UsuarioAutenticadoModel[] empresas = restTemplate.getForObject(resourceUri, UsuarioAutenticadoModel[].class);

		return Arrays.asList(empresas);
	}
}
