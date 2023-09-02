package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Prato;

@SuppressWarnings("unchecked")
public interface IPratoDAO extends CrudRepository<Prato, Long>{
	Prato findById(long id);
	List<Prato> findAll();
	Prato save(Prato prato);
	void deleteById(Long id);

	@Query("select l from Prato l where l.nome like %?1%")
	List<Prato> findAllByNome(String nome);

}