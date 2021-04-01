package com.mindtree.EMandi.configuration;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine springTempEngine = new SpringTemplateEngine();
		springTempEngine.addTemplateResolver(htmlTemplateResolver());
		return springTempEngine;
	}

	@Bean
	public SpringResourceTemplateResolver htmlTemplateResolver() {
		SpringResourceTemplateResolver sRTR = new SpringResourceTemplateResolver();
		sRTR.setPrefix("classpath:/templates/");
		sRTR.setSuffix(".html");
		sRTR.setTemplateMode(TemplateMode.HTML.name());
		sRTR.setCharacterEncoding(StandardCharsets.UTF_8.name());
		return sRTR;
	}
}
