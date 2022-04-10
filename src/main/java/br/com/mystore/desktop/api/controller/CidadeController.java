package br.com.mystore.desktop.api.controller;

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
import br.com.mystore.desktop.api.model.CidadeModel;
import br.com.mystore.desktop.api.model.input.CidadeInput;
import br.com.mystore.desktop.api.model.response.CidadeModelResponse;
import br.com.mystore.desktop.core.AccessConfig;

public class CidadeController extends AuthorizationController {

	private static final String RESOURCE_PATH = "/v1/cidades";

	private RestTemplate restTemplate;

	public CidadeController() {
		this.restTemplate = new RestTemplate();
	}

	public CidadeModel alterar(String token, CidadeInput cidadeInput, Integer id) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(cidadeInput, headers);

			return restTemplate.exchange(resourceUri, HttpMethod.PUT, httpEntity, CidadeModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public boolean apagarCidadePorId(String token, Integer id) {
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

	public CidadeModel cadastrar(String token, CidadeInput cidadeInput) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(cidadeInput, headers);

			return restTemplate.exchange(resourceUri, HttpMethod.POST, httpEntity, CidadeModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public CidadeModel cidadePorId(String token, Integer id) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			return restTemplate.exchange(resourceUri, HttpMethod.GET, httpEntity, CidadeModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public List<CidadeModel> todasCidades(String token) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			var responseCidadeModel = restTemplate
					.exchange(resourceUri, HttpMethod.GET, httpEntity, CidadeModelResponse.class).getBody();

			return Arrays.asList(responseCidadeModel.get_embedded().getCidades());

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

}
