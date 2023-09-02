package br.ufscar.dc.dsw.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Pedido;
import br.ufscar.dc.dsw.domain.Prato;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.service.spec.IPedidoService;
import br.ufscar.dc.dsw.service.spec.IPratoService;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private IPedidoService service;
	
	@Autowired
	private IPratoService pratoService;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Pedido pedido) {
		String data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		pedido.setUsuario(this.getUsuario());
		pedido.setData(data);
		return "pedido/cadastro";
	}
	
	private Usuario getUsuario() {
		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuarioDetails.getUsuario();
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
					
		model.addAttribute("pedidos",service.buscarTodosPorUsuario(this.getUsuario()));
		
		return "pedido/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Pedido pedido, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "pedido/cadastro";
		}
		
		service.salvar(pedido);
		attr.addFlashAttribute("sucess", "pedido inserida com sucesso.");
		return "redirect:/pedidos/listar";
	}
	
	@ModelAttribute("pratos")
	public List<Prato> listaPratos() {
		return pratoService.buscarTodos();
	}
}
