package com.br.sfb.service;

import java.sql.Date;
import java.time.LocalDateTime;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.br.sfb.model.PasswordResetToken;
import com.br.sfb.model.Usuario;
import com.br.sfb.repository.PasswordResetTokenRepository;


@Service
public class PasswordResetService {

	@Autowired
	private JavaMailSender javaMailSender;	
	@Autowired
	private PasswordResetTokenRepository pwsr;
	
	private LocalDateTime agora;

public void validationPasswordToken(PasswordResetToken pws){
	LocalDateTime agora = LocalDateTime.now();

	if(pws.getDataSolicitação().toString().equals(agora.toLocalDate().toString())){
		System.out.println("funcionou");
	}else
		System.out.println("falha");	
	
}




public void sends(Usuario usuario) {
 String texto = "<tbody><tr><td align=\"center\"><br><table id=\"\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\" bgcolor=\"white\" style=\"width:600px;border-collapse:collapse;border:1px solid #e2e2e2\"><tbody><tr><td colspan=\"3\" valign=\"middle\" bgcolor=\"#0669de\" height=\"96\" align=\"center\"><div><span style=\" font-size: 30px; font-weight: bold;text-shadow: 2px 2px 4px #000000;color: white;\">Rede Social_SFB-Projetos </span></div></td></tr><tr><td colspan=\"3\" height=\"44\">&nbsp;</td></tr>"+
         "<tr><td width=\"30\">&nbsp;</td><td align=\"center\" valign=\"top\"><h1 style=\"font-weight:lighter;font-size:30px;line-height:30px\">Olá "+ usuario.getNome() + "!</h1><p style=\"margin:1em 0;font-size:18px;line-height:24px\">"
                 +"Não consegue lembrar sua <span>senha</span>? Não tem problema, acontece com todos nós.Para criar uma nova <span>senha</span>, clique no link abaixo:</p><p style=\"margin:1em 0\">"
                 +"<a href=\"http://localhost:8080/login/usuario/alterarSenha?em="+usuario.getEmail()+"\" style=\"color:white;text-decoration:none;background-color:#0669de;display:inline-block;border-radius:3px;padding:0.75em;border-bottom:3px solid #0552ac;font-size:14px;width:270px;height:26px;line-height:26px;font-weight:bold;margin-top:24px;margin-bottom:10px\" target=\"_blank\" ><span>RECUPERAR</span> <span>SENHA</span></a>"
                 +"</p><p style=\"margin:1em 0;font-size:12px;color:#666666;font-weight:lighter\">Se tiver problemas, copie e cole o link abaixo em outra janela do seu navegador:<br>"
                  +"<br><a href=\"http://localhost:8080/login/usuario/alterarSenha?em="+usuario.getEmail()+"\" style=\"color:#0669de;text-decoration:none;font-size:12px\" target=\"_blank\" >http://localhost:8080/login/usuario/alterarSenha?em="+usuario.getEmail()+"</a>"
                 +"</p></td><td width=\"30\">&nbsp;</td></tr><tr><td colspan=\"3\" height=\"50\">&nbsp;</td></tr><tr><td width=\"30\">&nbsp;</td><td id=\"\" align=\"center\" height=\"46\" style=\"border-top:1px solid #e2e2e2\">"
                 +"<p style=\"margin:1em 0;font-size:10px;color:#666666\">EQUIPE <span style=\"color:#0669de;font-weight:800;text-transform:uppercase\">SFB-Projetos</span></p></td><td width=\"30\">&nbsp;</td></tr></tbody></table><p style=\"margin:1em 0;font-size:12px;color:#666666;font-weight:lighter\">Esta é uma mensagem automática, favor não responder este e-mail.</p></td></tr></tbody>";
  MimeMessage mail = javaMailSender.createMimeMessage();
  try {
      MimeMessageHelper helper = new MimeMessageHelper(mail, true);
      helper.setTo("fansubsbrasil@gmail.com");
      helper.setReplyTo("fansubsbrasil@gmail.com");
      helper.setFrom("fansubsbrasil@gmail.com");
      helper.setSubject("Recuperar Senha SFB");
      helper.setText("utf-8",texto);
  } catch (MessagingException e) {

  }
  javaMailSender.send(mail);
  //return helper;	
}


public String SalvarToken(Usuario usuario) {
	agora = LocalDateTime.now();
	try{
	PasswordResetToken psw = new PasswordResetToken();
	psw.setDataSolicitação(Date.valueOf(agora.toLocalDate()));
	psw.setExpiryDate((long) agora.getHour());
	psw.setUser(usuario);
	pwsr.save(psw);
	return "sucesso!";
	}catch(Exception e){
		
		return "Erro!";
	}
	
} 

}
