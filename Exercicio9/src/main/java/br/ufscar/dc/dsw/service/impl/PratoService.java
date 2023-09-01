package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IPratoDAO;
import br.ufscar.dc.dsw.domain.Prato;
import br.ufscar.dc.dsw.service.spec.IPratoService;

@Service
@Transactional(readOnly = false)
public class PratoService implements IPratoService {

	@Autowired
	IPratoDAO dao;
	
	public void salvar(Prato prato) {
		dao.save(prato);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Prato buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Prato> buscarTodos() {
		return dao.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Prato> buscarPorNome(String nome) {
		return dao.findAllByNome(nome);
	}
}
