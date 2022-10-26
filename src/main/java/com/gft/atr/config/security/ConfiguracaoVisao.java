package com.gft.atr.config.security;

import static com.gft.atr.enums.ModelAndViewFileObject.ACESSO_NEGADO;
import static com.gft.atr.enums.ModelAndViewFileObject.AUTH;
import static com.gft.atr.enums.ModelAndViewFileObject.ROOT;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ConfiguracaoVisao implements WebMvcConfigurer {
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController(ROOT+AUTH+ACESSO_NEGADO).setViewName(ROOT+AUTH+ACESSO_NEGADO);
	}

}
