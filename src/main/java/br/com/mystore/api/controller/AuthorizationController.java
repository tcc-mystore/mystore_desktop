package br.com.mystore.api.controller;

import java.net.URI;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import br.com.mystore.api.exception.ApiException;
import br.com.mystore.api.model.AplicacaoAutenticadaModel;
import br.com.mystore.api.model.UsuarioAutenticadoModel;
import br.com.mystore.core.AccessConfig;

public class AuthorizationController {
	private static final String RESOURCE_PATH = "/oauth/token";

	private RestTemplate restTemplate;

	public static String TOKEN = null;

	private HttpHeaders createHeaders(String user, String password) {
		String plainCreds = String.format("%s:%s", user, password);
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		return headers;
	}

	public ResponseEntity<AplicacaoAutenticadaModel> tokenAplicacao() {
		try {
			restTemplate = new RestTemplate();
			URI resourceUri = URI.create(AccessConfig.URL.getValor() + RESOURCE_PATH);
			MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
			body.add("grant_type", "client_credentials");
			HttpEntity<?> httpEntity = new HttpEntity<Object>(body,
					createHeaders(AccessConfig.USER_MANAGER.getValor(), AccessConfig.PASSWORD_MANAGER.getValor()));
			ResponseEntity<AplicacaoAutenticadaModel> aplicacaoAutenticadaModel = restTemplate.exchange(resourceUri,
					HttpMethod.POST, httpEntity, AplicacaoAutenticadaModel.class);

			return aplicacaoAutenticadaModel;
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

	public ResponseEntity<UsuarioAutenticadoModel> tokenUsuario(String username, String password) {
		try {
			restTemplate = new RestTemplate();
			URI resourceUri = URI.create(AccessConfig.URL.getValor() + RESOURCE_PATH);
			MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
			body.add("username", username);
			body.add("password", password);
			body.add("grant_type", "password");
			HttpEntity<?> httpEntity = new HttpEntity<Object>(body,
					createHeaders(AccessConfig.USER.getValor(), AccessConfig.PASSWORD.getValor()));
			ResponseEntity<UsuarioAutenticadoModel> usuarioAutenticadoModel = restTemplate.exchange(resourceUri,
					HttpMethod.POST, httpEntity, UsuarioAutenticadoModel.class);

			return usuarioAutenticadoModel;
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}
}
