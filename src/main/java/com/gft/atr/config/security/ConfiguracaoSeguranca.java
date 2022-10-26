package com.gft.atr.config.security;

import static com.gft.atr.enums.ModelAndViewFileObject.ACESSO_NEGADO;
import static com.gft.atr.enums.ModelAndViewFileObject.AUTH;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRAR_USUARIO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CADASTRO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_CONVERSOR;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_DETALHE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_EXCLUIR;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_INGREDIENTE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_INSTALACAO;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_LOGIN;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_LOGOUT;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_RECEITA;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_ROOT;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_SOBRE;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_UNIDADE_MEDIDA;
import static com.gft.atr.enums.ModelAndViewFileObject.PATH_USUARIO;
import static com.gft.atr.enums.ModelAndViewFileObject.PERFIL_ADMIN;
import static com.gft.atr.enums.ModelAndViewFileObject.PERFIL_USUARIO;
import static com.gft.atr.enums.ModelAndViewFileObject.ROOT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.gft.atr.repositories.UsuarioRepository;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired 
	private LoginSucesso loginSucesso;
	
	@Bean
	BCryptPasswordEncoder gerarCriptografia() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return new DetalheUsuarioServico(usuarioRepository);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
    		.antMatchers(PATH_ROOT).permitAll()
    		.antMatchers(PATH_ROOT+PATH_INSTALACAO).permitAll()
    		.antMatchers(PATH_ROOT+PATH_CONVERSOR).permitAll()
    		.antMatchers(PATH_ROOT+PATH_SOBRE).permitAll()
    		.antMatchers(PATH_ROOT+PATH_RECEITA).permitAll()
    		.antMatchers(PATH_ROOT+PATH_CADASTRAR_USUARIO).permitAll()
    		.antMatchers(PATH_ROOT+PATH_RECEITA+PATH_DETALHE).permitAll()
    		.antMatchers(PATH_ROOT+PATH_RECEITA+PATH_CADASTRO+"*").hasAnyAuthority(PERFIL_USUARIO)
    		.antMatchers(PATH_ROOT+PATH_RECEITA+PATH_EXCLUIR+"*").hasAnyAuthority(PERFIL_USUARIO)
    		.antMatchers(PATH_ROOT+PATH_UNIDADE_MEDIDA+"*").hasAnyAuthority(PERFIL_ADMIN)
    		.antMatchers(PATH_ROOT+PATH_INGREDIENTE+"*").hasAnyAuthority(PERFIL_ADMIN)
    		.antMatchers(PATH_ROOT+PATH_USUARIO+"*").hasAnyAuthority(PERFIL_ADMIN)
  		.and()
  			.exceptionHandling().accessDeniedPage(ROOT+AUTH+ACESSO_NEGADO)
  		.and()
    		.formLogin().successHandler(loginSucesso)
    		.loginPage(PATH_ROOT+PATH_LOGIN).permitAll()
  		.and()
    		.logout().logoutRequestMatcher(new AntPathRequestMatcher(PATH_ROOT+PATH_LOGOUT))
    		.logoutSuccessUrl(PATH_ROOT).permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// O objeto que vai obter os detalhes do usuário
		UserDetailsService detalheDoUsuario = userDetailsServiceBean();
		// Objeto para criptografia
		BCryptPasswordEncoder criptografia = gerarCriptografia();
		
		auth.userDetailsService(detalheDoUsuario).passwordEncoder(criptografia);
	}

}
