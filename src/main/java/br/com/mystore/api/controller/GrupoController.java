package br.com.mystore.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mystore.api.exception.ApiException;
import br.com.mystore.api.model.GrupoModel;
import br.com.mystore.api.model.input.GrupoInput;
import br.com.mystore.api.model.response.GrupoModelResponse;
import br.com.mystore.core.AccessConfig;

public class GrupoController extends AuthorizationController {

	private static final String RESOURCE_PATH = "/v1/grupos";

	private RestTemplate restTemplate;

	public GrupoController() {
		this.restTemplate = new RestTemplate();
	}

	public GrupoModel alterar(String token, GrupoInput grupoInput, String id) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(grupoInput, headers);

			return restTemplate.exchange(resourceUri, HttpMethod.PUT, httpEntity, GrupoModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public boolean apagarGrupoPorId(String token, String id) {
		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			return restTemplate.exchange(resourceUri, HttpMethod.DELETE, httpEntity, String.class).getStatusCode() == HttpStatus.NO_CONTENT;

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}

	}

	public GrupoModel cadastrar(String token, GrupoInput grupoInput) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(grupoInput, headers);

			return restTemplate.exchange(resourceUri, HttpMethod.POST, httpEntity, GrupoModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public List<GrupoModel> todasGrupos(String token) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			var responseGrupoModel = restTemplate
					.exchange(resourceUri, HttpMethod.GET, httpEntity, GrupoModelResponse.class).getBody();

			return Arrays.asList(responseGrupoModel.get_embedded().getGrupos());

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public GrupoModel grupoPorId(String token, String id) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			return restTemplate.exchange(resourceUri, HttpMethod.GET, httpEntity, GrupoModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}
}
