package br.ufscar.dc.dsw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Restaurante;
import br.ufscar.dc.dsw.service.spec.IRestauranteService;

@Controller
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private IRestauranteService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Restaurante restaurante) {
		return "restaurante/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("restaurantes",service.buscarTodos());
		return "restaurante/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Restaurante restaurante, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "restaurante/cadastro";
		}
		
		service.salvar(restaurante);
		attr.addFlashAttribute("sucess", "restaurante.create.sucess");
		return "redirect:/restaurantes/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("restaurante", service.buscarPorId(id));
		return "restaurante/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Restaurante restaurante, BindingResult result, RedirectAttributes attr) {
		
		if (result.getFieldErrorCount() > 1 || result.getFieldError("CNPJ") == null) {
			return "restaurante/cadastro";
		}

		service.salvar(restaurante);
		attr.addFlashAttribute("sucess", "restaurante.edit.sucess");
		return "redirect:/restaurantes/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		if (service.restauranteTemPratos(id)) {
			model.addAttribute("fail", "restaurante.delete.fail");
		} else {
			service.excluir(id);
			model.addAttribute("sucess", "restaurante.delete.sucess");
		}
		return listar(model);
	}
}