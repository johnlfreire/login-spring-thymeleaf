package com.br.sfb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.br.sfb.exception.EmailUsuarioJaCadastradoException;

import com.br.sfb.model.Usuario;
import com.br.sfb.repository.Usuarios;
import com.br.sfb.service.CadastroUsuarioServico;
import com.br.sfb.service.PasswordResetService;

@Controller
@RequestMapping("/usuario")
public class UsuarioControler {
	
	@Autowired
	private CadastroUsuarioServico cadastroService;
	@Autowired
	private Usuarios usuarioRepository;


	@Autowired
	private PasswordResetService psd;
	
	
	@RequestMapping("/cadastrar")
	public ModelAndView cadastro(Usuario usuario) {
		ModelAndView mv = new ModelAndView("novoCadastro");		
		return mv;

	}
	
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView loginp(@Valid Usuario usuario,BindingResult result, RedirectAttributes attributes, Model model) {

		if (result.hasErrors()) {
			return  new ModelAndView("redirect:/login/cadastrar");
		}
	  
	try {		
		BCryptPasswordEncoder encore = new BCryptPasswordEncoder();
		usuario.setSenha(encore.encode(usuario.getSenha()));
		cadastroService.salvar(usuario);	
	}catch (EmailUsuarioJaCadastradoException e) {
				result.rejectValue("email", e.getMessage(), e.getMessage());
				return  cadastro(usuario);
			}
	attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso");
	return  new ModelAndView("redirect:/login");
	}
	
	 
	
	@RequestMapping("/rec")
	public ModelAndView recuperar(Usuario usuario) {
		ModelAndView mv = new ModelAndView("recuperarsenha");		
		return mv;

	}

	@RequestMapping(value = "/recuperarSenha", method = RequestMethod.POST)
	public ModelAndView ResetarSenha(@RequestParam(value = "srm", required = false) String srm, RedirectAttributes attributes,BindingResult result) {
		 if(usuarioRepository.findByEmail(srm)!= null){
				psd.SalvarToken(usuarioRepository.findByEmail(srm).get());
				psd.sends(usuarioRepository.findByEmail(srm).get());
				attributes.addFlashAttribute("mensagem", "Em breve você receberá um e-mail para redefinir sua senha.");

				}else
					result.addError(new ObjectError("Erro","erro"));
				//attributes.addFlashAttribute("mensagem", "O e-mail informado não está cadastrado em nosso site.");
		 
				return new ModelAndView("redirect:/login");
			}


	
	
	@RequestMapping(value = "/alterarSenha", method = RequestMethod.GET)
public String AlterarSenha(@RequestParam(value = "em", required = false) String srm) {
System.out.println(srm);
			return "Login"; 
			}
	
	
}