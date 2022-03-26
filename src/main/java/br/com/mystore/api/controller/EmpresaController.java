package br.com.mystore.api.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mystore.api.exception.ApiException;
import br.com.mystore.api.model.EmpresaBasicoModel;
import br.com.mystore.api.model.input.EmpresaInput;
import br.com.mystore.api.model.response.EmpresaModelResponse;
import br.com.mystore.core.AccessConfig;

public class EmpresaController {

	private static final String RESOURCE_PATH = "/v1/empresas";

	private RestTemplate restTemplate;

	public EmpresaController() {
		this.restTemplate = new RestTemplate();
	}

	public EmpresaBasicoModel alterar(String token, EmpresaInput empresaInput, String id) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(empresaInput, headers);

			var responseEmpresaBasicoModel = restTemplate
					.exchange(resourceUri, HttpMethod.PUT, httpEntity, EmpresaBasicoModel.class).getBody();

			return responseEmpresaBasicoModel;

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public EmpresaBasicoModel cadastrar(String token, EmpresaInput empresaInput) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(empresaInput, headers);

			var responseEmpresaBasicoModel = restTemplate
					.exchange(resourceUri, HttpMethod.POST, httpEntity, EmpresaBasicoModel.class).getBody();

			return responseEmpresaBasicoModel;

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	private HttpHeaders createHeaders(String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);

		return headers;
	}

	public List<EmpresaBasicoModel> todasEmpresas(String token) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			var responseEmpresaBacicoModel = restTemplate
					.exchange(resourceUri, HttpMethod.GET, httpEntity, EmpresaModelResponse.class).getBody();

			var empresaBacicoModels = Arrays.asList(responseEmpresaBacicoModel.get_embedded().getEmpresas());
			return empresaBacicoModels;

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public EmpresaBasicoModel empresaPorId(String token, String id) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH + "/" + id);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			var empresaBasicoModel = restTemplate
					.exchange(resourceUri, HttpMethod.GET, httpEntity, EmpresaBasicoModel.class).getBody();

			return empresaBasicoModel;

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}
}
