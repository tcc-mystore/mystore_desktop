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
import br.com.mystore.api.model.EstadoModel;
import br.com.mystore.api.model.response.EstadoModelResponse;
import br.com.mystore.core.AccessConfig;

public class EstadoController extends AuthorizationController {

	private static final String RESOURCE_PATH = "/v1/estados";

	private RestTemplate restTemplate;

	public static String TOKEN = null;

	public EstadoController() {
		this.restTemplate = new RestTemplate();
	}

	public List<EstadoModel> todosEstados(String token) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			var responseCidadeModel = restTemplate
					.exchange(resourceUri, HttpMethod.GET, httpEntity, EstadoModelResponse.class).getBody();

			return Arrays.asList(responseCidadeModel.get_embedded().getEstados());

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

}
