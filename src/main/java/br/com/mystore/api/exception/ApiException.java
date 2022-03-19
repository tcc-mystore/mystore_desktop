package br.com.mystore.api.exception;

import java.time.OffsetDateTime;

import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.mystore.api.model.Problema;
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
	
	public ApiException(Integer status) {
		problema = new Problema();
		problema.setStatus(status);
		switch (status) {
		case 400:
			problema.setUserMessage("Requisicao mal formada!");
			problema.setTimestamp(OffsetDateTime.now());
			break;
		case 401:
			problema.setUserMessage("Usuário ou Senha Inválido!");
			problema.setTimestamp(OffsetDateTime.now());
			break;
		default:
			problema.setUserMessage("Erro desconhecido!");
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
