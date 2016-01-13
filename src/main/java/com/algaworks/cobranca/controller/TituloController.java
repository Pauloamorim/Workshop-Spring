package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.cobranca.enums.StatusTituloEnum;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

@Controller
@RequestMapping(value="/titulos")
public class TituloController {
	
	@Autowired
	private Titulos titulos;
	

	@RequestMapping("/novo")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView salvar(Titulo titulo){
		titulos.save(titulo);
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		mv.addObject("mensagem","Título salvo com sucesso");
		return mv;
	}
	
	@RequestMapping
	public ModelAndView pesquisar(){
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		List<Titulo> todosTitulos =  titulos.findAll();
		mv.addObject("titulos",todosTitulos);
		return mv;
	}
	
	@ModelAttribute("todosStatusTitulos")
	public List<StatusTituloEnum> todosStatusTitulo(){
		return Arrays.asList(StatusTituloEnum.values());
	}
	
	
}
