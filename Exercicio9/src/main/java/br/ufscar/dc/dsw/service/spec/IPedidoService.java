package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Pedido;
import br.ufscar.dc.dsw.domain.Usuario;

public interface IPedidoService {

	Pedido buscarPorId(Long id);

	List<Pedido> buscarTodosPorUsuario(Usuario u);
	
	List<Pedido> buscarTodosPorUsuario(Long id);
	
	void salvar(Pedido restaurante);
}
