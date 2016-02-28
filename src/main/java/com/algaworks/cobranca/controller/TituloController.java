package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.enums.StatusTituloEnum;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;
import com.algaworks.cobranca.repository.filter.TituloFilter;
import com.algaworks.cobranca.service.TituloService;

@Controller
@RequestMapping(value="/titulos")
public class TituloController {
	
	
	private String CADASTRO_TITULO="CadastroTitulo";
	
	@Autowired
	private Titulos titulos;
	
	@Autowired
	private TituloService tituloService;
	

	@RequestMapping("/novo")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(CADASTRO_TITULO);
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String salvar(@Validated Titulo titulo,Errors errors,RedirectAttributes attributes){
		if(errors.hasErrors()){
			return "CadastroTitulo";
		}
		try {
			tituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem","Título salvo com sucesso");
			return "redirect:/titulos/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento",null,e.getMessage());
			return CADASTRO_TITULO;
		}
	}
	
	@RequestMapping(value="{codigo}",method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo,RedirectAttributes attributes){
		tituloService.excluir(codigo);
		attributes.addFlashAttribute("mensagem","Título excluído com sucesso.");
		return "redirect:/titulos";
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro){
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		
		mv.addObject("titulos",tituloService.pesquisarTitulo(filtro));
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable Long codigo){
		Titulo titulo = titulos.findOne(codigo);
		ModelAndView mv = new ModelAndView(CADASTRO_TITULO);
		mv.addObject(titulo);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}/receber",method= RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo){
		return tituloService.receber(codigo);
	}
	
	@ModelAttribute("todosStatusTitulos")
	public List<StatusTituloEnum> todosStatusTitulo(){
		return Arrays.asList(StatusTituloEnum.values());
	}
	
	
}
