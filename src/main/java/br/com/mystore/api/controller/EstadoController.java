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
import br.com.mystore.api.model.EstadoBasicoModel;
import br.com.mystore.api.model.response.EstadoModelResponse;
import br.com.mystore.core.AccessConfig;

public class EstadoController {

	private static final String RESOURCE_PATH = "/v1/estados";

	private RestTemplate restTemplate;

	public static String TOKEN = null;

	public EstadoController() {
		this.restTemplate = new RestTemplate();
	}

	private HttpHeaders createHeaders(String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);

		return headers;
	}

	public List<EstadoBasicoModel> todosEstados(String token) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			var responseCidadeBacicoModel = restTemplate
					.exchange(resourceUri, HttpMethod.GET, httpEntity, EstadoModelResponse.class).getBody();

			var estadoBacicoModels = Arrays.asList(responseCidadeBacicoModel.get_embedded().getEstados());
			return estadoBacicoModels;

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

}