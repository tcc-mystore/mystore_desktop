package br.com.mystore.desktop.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import br.com.mystore.desktop.api.exception.ApiException;
import br.com.mystore.desktop.api.model.dto.UsuarioAcessoPorPeriodoDTO;

public class UsuarioController extends AuthorizationController {

	public UsuarioController() {
	}

	public List<UsuarioAcessoPorPeriodoDTO> quantidadeDeAcessosPorPeriodo(String token) {

		try {
			List<UsuarioAcessoPorPeriodoDTO> acessos = new ArrayList<UsuarioAcessoPorPeriodoDTO>();
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Geverson", 100));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Verumia", 10));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Anderson", 200));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Nilson", 150));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("George", 50));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Jose", 300));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Maria", 50));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Alberto", 12));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Camila", 233));
			acessos.add(new UsuarioAcessoPorPeriodoDTO("Andrerssa", 800));

			return acessos;

		} catch (ResourceAccessException e) {
			throw new ApiException(500, null);
		} catch (RestClientResponseException e) {
			throw new ApiException(e.getMessage(), e);
		}
	}

}
