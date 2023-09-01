package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Restaurante;

@SuppressWarnings("unchecked")
public interface IRestauranteDAO extends CrudRepository<Restaurante, Long>{

	Restaurante findById(long id);
	
	Restaurante findByCNPJ (String CNPJ);

	List<Restaurante> findAll();
	
	Restaurante save(Restaurante restaurante);

	void deleteById(Long id);
}
