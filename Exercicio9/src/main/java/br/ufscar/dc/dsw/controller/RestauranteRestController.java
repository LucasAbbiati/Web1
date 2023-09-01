package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.dsw.domain.Restaurante;
import br.ufscar.dc.dsw.service.spec.IRestauranteService;

@RestController
public class RestauranteRestController {

	@Autowired
	private IRestauranteService service;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private void parse(Restaurante restaurante, JSONObject json) {

		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				restaurante.setId(((Integer) id).longValue());
			} else {
				restaurante.setId((Long) id);
			}
		}

		restaurante.setCNPJ((String) json.get("cnpj"));
		restaurante.setNome((String) json.get("nome"));
	}

	@GetMapping(path = "/restaurantes")
	public ResponseEntity<List<Restaurante>> lista() {
		List<Restaurante> lista = service.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/restaurantes/{id}")
	public ResponseEntity<Restaurante> lista(@PathVariable("id") long id) {
		Restaurante restaurante = service.buscarPorId(id);
		if (restaurante == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(restaurante);
	}

	@PostMapping(path = "/restaurantes")
	@ResponseBody
	public ResponseEntity<Restaurante> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Restaurante restaurante = new Restaurante();
				parse(restaurante, json);
				service.salvar(restaurante);
				return ResponseEntity.ok(restaurante);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@PutMapping(path = "/restaurantes/{id}")
	public ResponseEntity<Restaurante> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Restaurante restaurante = service.buscarPorId(id);
				if (restaurante == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(restaurante, json);
					service.salvar(restaurante);
					return ResponseEntity.ok(restaurante);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/restaurantes/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Restaurante restaurante = service.buscarPorId(id);
		if (restaurante == null) {
			return ResponseEntity.notFound().build();
		} else {
			if (service.restauranteTemPratos(id)) {
				return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
			} else {
				service.excluir(id);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}
		}
	}
}