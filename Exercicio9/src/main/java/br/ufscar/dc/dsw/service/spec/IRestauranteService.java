package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Restaurante;

public interface IRestauranteService {

	Restaurante buscarPorId(Long id);

	List<Restaurante> buscarTodos();

	void salvar(Restaurante restaurante);

	void excluir(Long id);
	
	boolean restauranteTemPratos(Long id);
}
