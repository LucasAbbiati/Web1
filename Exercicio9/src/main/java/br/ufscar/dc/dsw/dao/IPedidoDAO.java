package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Pedido;
import br.ufscar.dc.dsw.domain.Usuario;

@SuppressWarnings("unchecked")
public interface IPedidoDAO extends CrudRepository<Pedido, Long>{

	Pedido findById(long id);

	List<Pedido> findAllByUsuario(Usuario u);
	
	@Query("select c from Pedido c where c.usuario.id = ?1")
	List<Pedido> findAllByUsuarioID(Long id);
	
	Pedido save(Pedido pedido);
}