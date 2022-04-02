package br.com.mystore.desktop.api.exception;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.client.RestClientResponseException;

import br.com.mystore.desktop.api.model.Problema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Getter
	private Problema problema;

	public ApiException(String message, RestClientResponseException cause) {
		super(message, cause);

		deserializeProblema(cause);
	}

	public ApiException(RestClientResponseException cause) {
		super(cause);
		log.warn("Erro encontrado: ", cause.getMessage());
	}

	public ApiException(Integer status, RestClientResponseException cause) {
		problema = new Problema();
		problema.setStatus(status);
		problema.setError("Atenção");

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String, Object> erroRetornado = null;
		if (cause != null) {
			erroRetornado = jsonParser.parseMap(cause.getResponseBodyAsString());

			if (erroRetornado.get("error_description") != null)
				problema.setUserMessage("Usuário ou Senha Inválido!");
		}

		switch (status) {
		case 400:
			problema.setTimestamp(OffsetDateTime.now());
			break;
		case 401:
			problema.setUserMessage("Acesso Não Authorizado!");
			problema.setTimestamp(OffsetDateTime.now());
			break;
		default:
			problema.setError("Erro");
			problema.setUserMessage("Erro desconhecido, contate o administrador do sistema!");
			problema.setTimestamp(OffsetDateTime.now());
			break;
		}
	}

	private void deserializeProblema(RestClientResponseException cause) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new JavaTimeModule());
		mapper.findAndRegisterModules();

		try {
			this.problema = mapper.readValue(cause.getResponseBodyAsString(), Problema.class);
		} catch (JsonProcessingException e) {
			log.warn("Não foi possível desserializar a resposta em um problema", e);
		}
	}

}
