package br.ufscar.dc.dsw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Restaurante;
import br.ufscar.dc.dsw.domain.Prato;
import br.ufscar.dc.dsw.service.spec.IRestauranteService;
import br.ufscar.dc.dsw.service.spec.IPratoService;

@Controller
@RequestMapping("/pratos")
public class PratoController {

	@Autowired
	private IPratoService pratoService;

	@Autowired
	private IRestauranteService restauranteService;

	@GetMapping("/cadastrar")
	public String cadastrar(Prato prato) {
		return "prato/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("pratos", pratoService.buscarTodos());
		return "prato/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Prato prato, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "prato/cadastro";
		}

		pratoService.salvar(prato);
		attr.addFlashAttribute("sucess", "prato.create.sucess");
		return "redirect:/pratos/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("prato", pratoService.buscarPorId(id));
		return "prato/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Prato prato, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "prato/cadastro";
		}

		pratoService.salvar(prato);
		attr.addFlashAttribute("sucess", "prato.edit.sucess");
		return "redirect:/pratos/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		pratoService.excluir(id);
		attr.addFlashAttribute("sucess", "prato.delete.sucess");
		return "redirect:/pratos/listar";
	}

	@ModelAttribute("restaurantes")
	public List<Restaurante> listaRestaurantes() {
		return restauranteService.buscarTodos();
	}
}
