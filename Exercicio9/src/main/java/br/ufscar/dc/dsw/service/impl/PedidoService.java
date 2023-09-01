package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IPedidoDAO;
import br.ufscar.dc.dsw.domain.Pedido;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IPedidoService;

@Service
@Transactional(readOnly = false)
public class PedidoService implements IPedidoService {

	@Autowired
	IPedidoDAO dao;
	
	public void salvar(Pedido pedido) {
		dao.save(pedido);
	}

	@Transactional(readOnly = true)
	public Pedido buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Pedido> buscarTodosPorUsuario(Usuario u) {
		return dao.findAllByUsuario(u);
	}
	
	@Transactional(readOnly = true)
	public List<Pedido> buscarTodosPorUsuario(Long id) {
		return dao.findAllByUsuarioID(id);
	}
}
