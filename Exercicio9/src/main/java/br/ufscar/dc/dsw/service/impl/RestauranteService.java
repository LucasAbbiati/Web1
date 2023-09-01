package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IRestauranteDAO;
import br.ufscar.dc.dsw.domain.Restaurante;
import br.ufscar.dc.dsw.service.spec.IRestauranteService;

@Service
@Transactional(readOnly = false)
public class RestauranteService implements IRestauranteService {

	@Autowired
	IRestauranteDAO dao;
	
	public void salvar(Restaurante restaurante) {
		dao.save(restaurante);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Restaurante buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Restaurante> buscarTodos() {
		return dao.findAll();
	}
	
	@Transactional(readOnly = true)
	public boolean restauranteTemPratos(Long id) {
		return !dao.findById(id.longValue()).getPratos().isEmpty(); 
	}
}
