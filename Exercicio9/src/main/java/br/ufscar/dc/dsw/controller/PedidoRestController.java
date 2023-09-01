package br.ufscar.dc.dsw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.domain.Pedido;
import br.ufscar.dc.dsw.service.spec.IPedidoService;

@RestController
public class PedidoRestController {

	@Autowired
	private IPedidoService service;
	
	@GetMapping(path = "/pedidos/usuarios/{id}")
	public ResponseEntity<List<Pedido>> lista(@PathVariable("id") long id) {
		List<Pedido> lista = service.buscarTodosPorUsuario(id);
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}
}
