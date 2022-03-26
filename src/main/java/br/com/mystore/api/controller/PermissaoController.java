package br.com.mystore.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mystore.api.exception.ApiException;
import br.com.mystore.api.model.PermissaoModel;
import br.com.mystore.api.model.response.PermissaoModelResponse;
import br.com.mystore.core.AccessConfig;

public class PermissaoController extends AuthorizationController {

	private static final String RESOURCE_PATH = "/v1/permissoes";

	private RestTemplate restTemplate;

	public static String TOKEN = null;

	public PermissaoController() {
		this.restTemplate = new RestTemplate();
	}

	public List<PermissaoModel> todasPermissoes(String token) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			var responsePermissaoModel = restTemplate
					.exchange(resourceUri, HttpMethod.GET, httpEntity, PermissaoModelResponse.class).getBody();

			return Arrays.asList(responsePermissaoModel.get_embedded().getPermissoes());

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

}
