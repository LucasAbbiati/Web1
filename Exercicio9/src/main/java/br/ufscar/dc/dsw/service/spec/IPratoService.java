package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Prato;

public interface IPratoService {

	Prato buscarPorId(Long id);
	
	List<Prato> buscarTodos();
	
	List<Prato> buscarPorNome(String nome);
	
	void salvar(Prato prato);
	
	void excluir(Long id);
	
}
