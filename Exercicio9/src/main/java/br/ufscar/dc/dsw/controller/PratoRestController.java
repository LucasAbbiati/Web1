package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
import br.ufscar.dc.dsw.domain.Prato;
import br.ufscar.dc.dsw.service.spec.IPratoService;

@RestController
public class PratoRestController {

	@Autowired
	private IPratoService service;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private void parse(Restaurante restaurante, JSONObject json) {

		Map<String, Object> map = (Map<String, Object>) json.get("restaurante");

		Object id = map.get("id");
		if (id instanceof Integer) {
			restaurante.setId(((Integer) id).longValue());
		} else {
			restaurante.setId(((Long) id));
		}

		restaurante.setCNPJ((String) map.get("cnpj"));
		restaurante.setNome((String) map.get("nome"));
	}

	private void parse(Prato prato, JSONObject json) {

		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				prato.setId(((Integer) id).longValue());
			} else {
				prato.setId(((Long) id));
			}
		}

		prato.setNome((String) json.get("nome"));
		prato.setDescricao((String) json.get("descricao"));
		Double value = (Double) json.get("preco");
		prato.setPreco(BigDecimal.valueOf(value));

		Restaurante restaurante = new Restaurante();
		parse(restaurante, json);
		prato.setRestaurante(restaurante);
	}

	@GetMapping(path = "/pratos")
	public ResponseEntity<List<Prato>> lista() {
		List<Prato> lista = service.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/pratos/{id}")
	public ResponseEntity<Prato> lista(@PathVariable("id") long id) {
		Prato prato = service.buscarPorId(id);
		if (prato == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(prato);
	}

	@GetMapping(path = "/pratos/nomes/{nome}")
	public ResponseEntity<List<Prato>> listaPorNome(@PathVariable("nome") String nome) {
		List<Prato> lista = service.buscarPorNome(nome);
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping(path = "/pratos")
	@ResponseBody
	public ResponseEntity<Prato> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Prato prato = new Prato();
				parse(prato, json);
				service.salvar(prato);
				return ResponseEntity.ok(prato);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@PutMapping(path = "/pratos/{id}")
	public ResponseEntity<Prato> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Prato prato = service.buscarPorId(id);
				if (prato == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(prato, json);
					service.salvar(prato);
					return ResponseEntity.ok(prato);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/pratos/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Prato prato = service.buscarPorId(id);
		if (prato == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.excluir(id);
			return ResponseEntity.noContent().build();
		}
	}
}
