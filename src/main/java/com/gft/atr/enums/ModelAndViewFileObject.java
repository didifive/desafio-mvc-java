package com.gft.atr.enums;

public class ModelAndViewFileObject {
	
	private ModelAndViewFileObject() {}

	/**
	 * MODELANDVIEW OBJECTS
	 */
	//BUSCA
	public static final String OBJETO_BUSCA_NOME = "nome";
	public static final String OBJETO_BUSCA_NOME_INGREDIENTE = "nomeIngrediente";
	public static final String OBJETO_BUSCA_USUARIO = "buscaUsuarioId";
	public static final String OBJETO_ERRO_UPLOAD_IMAGEM = "erroUploadImagem";
	//CONVERSOR MEDIDA
	
	public static final String OBJETO_INGREDIENTE_REFERENCIA = "ingredienteReferencia";
	public static final String OBJETO_UNIDADE_MEDIDA_CONVERTIDA = "unidadeMedidaConvertida";
	public static final String OBJETO_UNIDADE_MEDIDA_REFERENCIA = "unidadeMedidaReferencia";
	public static final String OBJETO_UNIDADE_MEDIDA_REFERENCIA_QUANTIDADE = "unidadeMedidaReferenciaQuantidade";
	public static final String OBJETO_VALOR_CONVERTIDO = "valorConvertido";
	//INGREDIENTE
	public static final String OBJETO_INGREDIENTE = "ingrediente";
	public static final String OBJETO_INGREDIENTE_LISTA = "listaIngredientes";
	public static final String OBJETO_INGREDIENTE_QUANTIDADE = "quantidadeIngredientes";
	//MENSAGEM
	public static final String OBJETO_MENSAGEM = "mensagem";
	public static final String OBJETO_MENSAGEM_ERRO = "mensagemErro";
	//PERMISSAO
	public static final String OBJETO_PERMISSAO_LISTA = "listaPermissoes";
	//RECEITA
	public static final String OBJETO_RECEITA = "receita";
	public static final String OBJETO_RECEITA_LISTA = "listaReceitas";
	public static final String OBJETO_RECEITA_MEDIA_AVALIACAO = "receitaMediaAvaliacao";
	public static final String OBJETO_RECEITA_QUANTIDADE = "quantidadeReceitas";
	//UNIDADE MEDIDA
	public static final String OBJETO_UNIDADE_MEDIDA = "unidadeMedida";
	public static final String OBJETO_UNIDADE_MEDIDA_LISTA = "listaUnidadesMedida";
	public static final String OBJETO_UNIDADE_MEDIDA_QUANTIDADE = "quantidadeUnidadesMedida";
	public static final String OBJETO_UNIDADE_REFERENCIA_LISTA = "listaUnidadesReferencia";
	//USUARIO
	public static final String OBJETO_USUARIO = "usuario";
	public static final String OBJETO_USUARIO_LISTA = "listaUsuarios";
	public static final String OBJETO_USUARIO_QUANTIDADE = "quantidadeUsuarios";

	/**
	 * MAPPING ROUTES
	 */
	public static final String REDIRECT = "redirect:/";
	public static final String FORWARD = "forward:/";
	
	//ROOT
	//NÍVEL 0
	public static final String PATH_ROOT = "/";
	
	//ASSETS
	//NÍVEL 1
	public static final String PATH_ASSETS = "assets/";
  	//NÍVEL 2
  	public static final String PATH_ASSETS_IMAGES = PATH_ASSETS + "images/";
  	public static final String PATH_ASSETS_JS = PATH_ASSETS + "js/";
	
	//INGREDIENTE
  //NÍVEL 1
	public static final String PATH_INGREDIENTE = "ingrediente";
	
	//CONVERSOR
	//NÍVEL 1
	public static final String PATH_CONVERSOR = "conversor";
	
	//INSTALAÇÃO
	//NÍVEL 1
	public static final String PATH_INSTALACAO = "instalacao";
	
	//LOGIN
	//NÍVEL 1
	public static final String PATH_CADASTRAR_USUARIO = "cadastrar-usuario";
	public static final String PATH_LOGIN = "login";
	public static final String PATH_LOGOUT = "logout";
	
	//RECEITA
	//NÍVEL 1
	public static final String PATH_RECEITA = "receita";
		//NÍVEL 2
		public static final String PATH_AVALIACAO = "avaliacao";
	
	//SOBRE
	//NÍVEL 1
	public static final String PATH_SOBRE = "sobre";
	
	//UNIDADE MEDIDA
	//NÍVEL 1
	public static final String PATH_UNIDADE_MEDIDA = "unidade-medida";
	
	//USUARIO
	//NÍVEL 1
	public static final String PATH_USUARIO = "usuario";
	
	//PATHS AÇÕES
  	//NÍVEL 2
  	public static final String PATH_CADASTRO = "/cadastro";
  	public static final String PATH_DETALHE = "/detalhe";
  	public static final String PATH_EXCLUIR = "/excluir";
	
	/**
	 * MODELANDVIEW FILES
	 */
	//PRINCIPAL
	public static final String INDEX = "/index.html";
	public static final String INSTALACAO = "/instalacao.html";
	public static final String SOBRE = "/sobre.html";
	public static final String CADASTRO_USUARIO = "/cadastro_usuario.html";
	public static final String CONVERSOR = "/conversor.html";
	
	//ARQUIVOS PADRÕES
	public static final String FORM = "/form.html";
	public static final String LISTA = "/lista.html";
	public static final String DETALHE = "/detalhe.html";
	
	//ARQUIVOS LOGIN
	public static final String LOGIN = "/login.html";
	public static final String ACESSO_NEGADO = "/auth_acesso_negado.html";
	
	//PASTAS
	//NÍVEL 0
	public static final String ROOT = "/";
  	//NÍVEL 1
	public static final String AUTH = "auth";
  	public static final String INGREDIENTE = "ingrediente";
  	public static final String RECEITA = "receita";
  	public static final String UNIDADE_MEDIDA = "unidade-medida";
  	public static final String USUARIO = "usuario";
	
	/**
	 * PERFILS
	 */
	public static final String PERFIL_ADMIN = "ROLE_ADMINISTRADOR";
	public static final String PERFIL_USUARIO = "ROLE_USUARIO";
	

}
