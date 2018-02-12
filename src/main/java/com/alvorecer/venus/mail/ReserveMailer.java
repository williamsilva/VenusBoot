package com.alvorecer.venus.mail;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.alvorecer.venus.model.Reserve;

@Component
public class ReserveMailer {

	private static Logger logger = LoggerFactory.getLogger(ReserveMailer.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine thymeleaf;

	@Async
	public void enviar(Reserve reserve) {

		Context context = new Context(new Locale("pt", "BR"));
		context.setVariable("reserve", reserve);
		context.setVariable("logo", "logo");

		try {
			String email = thymeleaf.process("formSystem/mail/ResumoReserve", context);
			FileSystemResource file = new FileSystemResource(new File("Boleto (4).pdf").getCanonicalPath());

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom("alvorecersolucoes@alvorecersolucoes.com.br");
			helper.setTo(reserve.getClient().getEmail());
			helper.setSubject("VOUCHER-ACQUAMANIA - Nº " + reserve.getVoucher());
			helper.setText(email, true);
			helper.addInline("logo", new ClassPathResource("static/images/alvorecer/logo-gray.png"));

			helper.addAttachment("Voucher 0201.pdf", file);

			mailSender.send(mimeMessage);

		} catch (MessagingException | MailSendException | IOException e) {
			logger.error("Erro enviando e-mail", e);
		}
	}
}
