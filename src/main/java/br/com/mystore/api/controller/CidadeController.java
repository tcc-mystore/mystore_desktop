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
import br.com.mystore.api.model.CidadeBacicoModel;
import br.com.mystore.api.model.response.CidadeModelResponse;
import br.com.mystore.core.AccessConfig;

public class CidadeController {

	private static final String RESOURCE_PATH = "/v1/cidades";

	private RestTemplate restTemplate;

	public static String TOKEN = null;

	public CidadeController() {
		this.restTemplate = new RestTemplate();
	}

	private HttpHeaders createHeaders(String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);

		return headers;
	}

	public List<CidadeBacicoModel> todasCidades(String token) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var headers = createHeaders(token);

			var httpEntity = new HttpEntity<Object>(headers);

			var responseCidadeBacicoModel = restTemplate.exchange(resourceUri, HttpMethod.GET, httpEntity, CidadeModelResponse.class).getBody();

			var cidadeBacicoModels = Arrays.asList(responseCidadeBacicoModel.get_embedded().getCidades());

			return cidadeBacicoModels;

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

}
