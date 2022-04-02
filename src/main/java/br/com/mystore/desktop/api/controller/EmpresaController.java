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
import br.com.mystore.desktop.api.model.EmpresaModel;
import br.com.mystore.desktop.api.model.input.EmpresaInput;
import br.com.mystore.desktop.api.model.response.EmpresaModelResponse;
import br.com.mystore.desktop.core.AccessConfig;

public class EmpresaController extends AuthorizationController {

	private static final String RESOURCE_PATH = "/v1/empresas";

	private RestTemplate restTemplate;

	public EmpresaController() {
		this.restTemplate = new RestTemplate();
	}

	public EmpresaModel alterar(String token, EmpresaInput empresaInput, String id) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(empresaInput, headers);

			return restTemplate.exchange(resourceUri, HttpMethod.PUT, httpEntity, EmpresaModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public EmpresaModel cadastrar(String token, EmpresaInput empresaInput) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(empresaInput, headers);

			return restTemplate.exchange(resourceUri, HttpMethod.POST, httpEntity, EmpresaModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public List<EmpresaModel> todasEmpresas(String token) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			var responseEmpresaModel = restTemplate
					.exchange(resourceUri, HttpMethod.GET, httpEntity, EmpresaModelResponse.class).getBody();

			return Arrays.asList(responseEmpresaModel.get_embedded().getEmpresas());

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public EmpresaModel empresaPorId(String token, String id) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			return restTemplate.exchange(resourceUri, HttpMethod.GET, httpEntity, EmpresaModel.class).getBody();

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public Boolean ativarOuDesativar(String token, Integer id, boolean ativa) {
		try {
			var operacao = ativa ? "/ativar" : "/desativar";
			var builder = UriComponentsBuilder
					.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id + operacao);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);
			var response = restTemplate.exchange(resourceUri, HttpMethod.PUT, httpEntity, String.class);
			return response.getStatusCode() == HttpStatus.NO_CONTENT;
		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}
}
