package br.com.mystore.api.controller;

import java.util.Collections;

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
import br.com.mystore.core.AccessConfig;

public class EmpresaController {

	private static final String RESOURCE_PATH = "/v1/empresas";

	private RestTemplate restTemplate;

	public EmpresaController() {
		this.restTemplate = new RestTemplate();
	}

	private HttpHeaders createHeaders(String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);

		return headers;
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

}
