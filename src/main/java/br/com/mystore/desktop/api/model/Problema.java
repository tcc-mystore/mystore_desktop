package br.com.mystore.desktop.api.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problema {

	private Integer status;
	private OffsetDateTime timestamp;
	private String title;
	private String userMessage;
	private String error;

}
