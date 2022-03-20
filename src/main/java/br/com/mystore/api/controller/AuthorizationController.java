package br.com.mystore.api.controller;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mystore.api.exception.ApiException;
import br.com.mystore.api.model.AplicacaoAutenticadaModel;
import br.com.mystore.api.model.UsuarioAutenticadoModel;
import br.com.mystore.core.AccessConfig;

public class AuthorizationController {

	private static final String RESOURCE_PATH = "/oauth/token";

	private RestTemplate restTemplate;

	public static String TOKEN = null;

	public AuthorizationController() {
		this.restTemplate = new RestTemplate();
		;
	}

	private HttpHeaders createHeaders(String user, String password) {

		String plainCreds = String.format("%s:%s", user, password);

		byte[] plainCredsBytes = plainCreds.getBytes(StandardCharsets.UTF_8);

		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);

		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Basic " + base64Creds);

		return headers;
	}

	public AplicacaoAutenticadaModel tokenAplicacao() {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var body = new LinkedMultiValueMap<String, String>();
			body.add("grant_type", "client_credentials");

			var headers = createHeaders(AccessConfig.USER_MANAGER.getValor(), AccessConfig.PASSWORD_MANAGER.getValor());

			var httpEntity = new HttpEntity<Object>(body, headers);

			return restTemplate.postForObject(resourceUri, httpEntity, AplicacaoAutenticadaModel.class);

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public UsuarioAutenticadoModel tokenUsuario(String username, String password) {

		try {
			var builder = UriComponentsBuilder.fromUriString(AccessConfig.URL.getValor() + RESOURCE_PATH);
			var resourceUri = builder.buildAndExpand().toUri();

			var params = new LinkedMultiValueMap<String, String>();
			params.add("username", username);
			params.add("password", password);
			params.add("grant_type", "password");

			var headers = createHeaders(AccessConfig.USER.getValor(), AccessConfig.PASSWORD.getValor());

			var httpEntity = new HttpEntity<Object>(params, headers);

			return restTemplate.postForObject(resourceUri, httpEntity, UsuarioAutenticadoModel.class);

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (BadRequest e) {
			throw new ApiException(400, e);
		} catch (Unauthorized e) {
			throw new ApiException(401, e);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

}
