package br.com.mystore.desktop.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mystore.desktop.api.exception.ApiException;
import br.com.mystore.desktop.api.model.UsuarioModel;
import br.com.mystore.desktop.api.model.dto.UsuarioAcessoPorPeriodoDTO;
import br.com.mystore.desktop.api.model.input.UsuarioInput;
import br.com.mystore.desktop.api.model.response.UsuarioModelResponse;
import br.com.mystore.desktop.core.AccessConfig;

public class UsuarioController extends AuthorizationController {

	private static final String RESOURCE_PATH = "/v1/usuarios";

	private RestTemplate restTemplate;

	public UsuarioController() {
		this.restTemplate = new RestTemplate();
	}

	public List<UsuarioAcessoPorPeriodoDTO> quantidadeDeAcessosPorPeriodo(String token) {

		try {
			List<UsuarioAcessoPorPeriodoDTO> acessos = new ArrayList<UsuarioAcessoPorPeriodoDTO>();
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Geverson", 100));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Verumia", 10));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Anderson", 200));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Nilson", 150));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("George", 50));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Jose", 300));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Maria", 50));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Alberto", 12));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Camila", 233));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Andrerssa", 800));

			return acessos;

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public UsuarioModel usuarioPorId(String token, Integer id) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			return restTemplate.exchange(resourceUri, HttpMethod.GET, httpEntity, UsuarioModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public List<UsuarioModel> todosUsuarios(String token) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			var responseUsuarioModel = restTemplate
					.exchange(resourceUri, HttpMethod.GET, httpEntity, UsuarioModelResponse.class).getBody();

			return Arrays.asList(responseUsuarioModel.get_embedded().getUsuarios());

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public boolean apagarUsuarioPorId(String token, Integer id) {
		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			return restTemplate.exchange(resourceUri, HttpMethod.DELETE, httpEntity, String.class)
					.getStatusCode() == HttpStatus.NO_CONTENT;

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}

	}

	public UsuarioModel cadastrar(String token, UsuarioInput usuarioInput) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(usuarioInput, headers);

			return restTemplate.exchange(resourceUri, HttpMethod.POST, httpEntity, UsuarioModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}
	
	public UsuarioModel alterar(String token, UsuarioInput usuarioInput, Integer id) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(usuarioInput, headers);

			return restTemplate.exchange(resourceUri, HttpMethod.PUT, httpEntity, UsuarioModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}
}
