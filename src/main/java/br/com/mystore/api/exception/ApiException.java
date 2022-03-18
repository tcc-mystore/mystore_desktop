package br.com.mystore.api.exception;

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
