# DESAFIO MVC - Projeto Palmirinha Onofre - Programa Start

## Description

Repositório para entrega do Desafio MVC (semana 20/06 ~ 01/07/2022) - Projeto Palmirinha Onofre - Programa GFT Start  
Objetivo: Aplicar os conceitos MVC aprendidos durante as semanas de estudo;  
Escopo: Criar um sistema no qual simule um site de receitas;  
Entrega básica:

- CRUD de receitas: Listagem com pesquisa por nome, página de cadastro e atualização e busca por ingrediente,
- CRUD de ingredientes: Listagem com pesquisa por nome e página de cadastro e atualização,
- CRUD de unidades de medidas: Listagem com pesquisa por nome e página de cadastro e atualização,
- Acessos de admin e usuário comum;  

Desafios bônus (Exceeds):

- Usar imagens nas receitas,
- Conversos de unidades,
- TDD/testes unitários,
- Histórico de receitas de usuários,
- Envio de email com confirmação de cadastro para o usuário,
- Funcionalidades extras com front-end.

### Awesome Tasty Recipes

#### O espetáculo do sabor

Para o desafio foi criado o sistema **Awesome Tasty Recipes - O espetáculo do sabor** para procurar trazer o projeto o mais para a realidade possível.  
Nele foi aplicado o solicitado da entrega básica e exceeds com detalhes abaixo relacionado:

- A página inicial do site contém a lista de receitas acessível por todos (não necessita autenticação);
- A página de detalhes da receita também é acessível por todos (não necessita autenticação);
- Para cadastrar ou editar receitas, deve possuir no mínimo a permissão de usuário comum, e com essa permissão é possível somente editar a(s) própria(s) receita(s);
- Usuário com perfil administrador possui acesso total ao sistema e pode editar qualquer receita.
- Adição de avaliação para receita, mostrando a média de notas no index e nos detalhes de receitas. Para avaliar deve ser usuário cadastrado com no mínimo permissão de usuário comum, não sendo permitido usuário avaliar a própria receita, se tentar avaliar novamente, irá substituir a nota anterior;
- É possível enviar imagens para os cadastros de Ingredientes e Receitas, podendo ser por upload de imagens com até 512KB e extensões png, jpg e jpeg ou URL de imagem, também com extensão png, jpg ou jpeg, já disponível online na nuvem;
- Página com Conversor de Unidades de Medidas (não necessita autenticação);
- Página 'sobre' com resumo do site;
- Em relação ao envio de email quando é cadastrada uma nova conta, o Google interrompeu a autenticação de contas somente por usuário e senha de vez a partir de 30/05/2022 (<https://support.google.com/mail/?p=BadCredentials>);
- Para configurar conta de email, basta modificar as opções de `spring.mail` que estão no arquivo `application.properties`, e também alterar com email de remetente na variável `from` que está no médoto `sendEmailConfirmacaoCadastroTo` da classe `com.gft.atr.services.EmailSenderService`;
- Na segurança, a IDE avisou que `WebSecurityConfigurerAdapter` está depreciado, para sanar a situação, deve-se utilizar `org.springframework.security.web.SecurityFilterChain`, será feito em próxima atualização;
- Paginação para as listas de Unidades, Ingredientes e Receitas também poderão ser implementadas em próxima atualização.

## Configuration

O projeto foi feito utilizando:

- [IDE Eclipse] versão 2022-03 (4.23.0).
- Iniciado com [spring initializr], com as configurações e dependências:
  - Project: [Maven] Project
  - Language: Java
  - [Spring Boot] 2.7.0
  - Packaging: Jar
  - [Java 17]
  - Dependencies:
    - Spring Boot DevTools
    - Spring Web
    - Spring Data JPA
    - Validation
    - MySQL Driver
    - Thymeleaf
    - Spring Security
    - Java Mail Sender
- Dependências adicionadas manualmente ao [pom.xml]:
  - `thymeleaf-layout-dialect` para utilizar layout junto com thymeleaf;
  - `jacoco-maven-plugin` para gerar relatório de coverage quando o teste é executado diretamente pelo terminal. _É necessário também inserir plugin específico, verifique o [pom.xml] para checar o plugin `jacoco-maven-plugin` adicionado_;
  - `commons-codec` para encode e decode de imagens em base64;
  - `commons-io` para manipular file to byte[] para teste;
  - `thymeleaf-extras-springsecurity5` para poder utilizar recursos de segurança do spring security no thymeleaf.
- Outros recursos:
  - [Bootstrap] 5.0.2;
  - [JQuery] 3.6.0;
  - [Font Awesome] 3.6.0;
  - [jQuery Raty] para avaliar e mostrar notas das receitas.
- Montagem do diagrama de classes UML utilizando o site [Draw.io]:  
![Diagrama de Classes UML - Entidades](docs/AwesomeTastyUML-Entities.drawio.png?raw=true "Diagrama de Classes UML - Entidades")
![Diagrama de Classes UML - Repositórios e Serviços](docs/AwesomeTastyUML-RepositoriesAndServices.drawio.png?raw=true "Diagrama de Classes UML - Repositórios e Serviços")  

## Visuals

Spring Banner personalizado:  
![Spring Banner - GFT - Awesome Tasty Recipes](docs/spring_banner_gft_atr.png?raw=true "Spring Banner - GFT - Awesome Tasty Recipes")  
Logotipo do projeto Awesome Tasty Recipes usando o site [Canva]:  
![Logotipo - Awesome Tasty Recipes](src/main/resources/static/assets/images/logo.png?raw=true "Logotipo - Awesome Tasty Recipes")  
Página de erro personalizada (src/resources/templates/error.html):  
![Error Page](docs/error_page.png?raw=true "Error Page")  
No formulário de usuário, o usuário admin@gft.com não pode ser desativado:  
![Usuário admin@gft.com não pode ser desativado](docs/usuario_admin_naodesativa.PNG?raw=true "Usuário admin@gft.com não pode ser desativado")  
Em detalhes de usuário é mostrado as permissões e receitas vinculadas ao usuário detalhado:  
![Permissões e receitas vinculadas ao usuário](docs/usuario_quando_possui_permissao_ou_receita.PNG?raw=true "Permissões e receitas vinculadas ao usuário")  
Em detalhes de usuário, quando não existe vínculo de permissão ou receita mostra avisos:  
![Quando não existe vínculo](docs/usuario_quando_nao_possui_permissao_ou_receita.PNG?raw=true "quando não existe vínculo de permissão ou receita mostra avisos")  
Navbar pública:  
![Navbar pública](docs/navbar_publica.PNG?raw=true "Navbar pública")  
Navbar usuário:  
![Navbar usuário](docs/navbar_usuario.PNG?raw=true "Navbar usuário")  
Navbar administrador:  
![Navbar administrador](docs/navbar_admin.PNG?raw=true "Navbar administrador")  
Filtro de receita público ou usuário:  
![Filtro de receita público ou usuário](docs/filtro_lista_receita_quando_nao_possui_permissao_administrador.PNG?raw=true "Filtro de receita público ou usuário")  
Filtro de receita para administrador, pode pesquisar por usuário:  
![Filtro de receita para administrador](docs/filtro_lista_receita_quando_possui_permissao_administrador.PNG?raw=true "Filtro de receita para administrador")
Quando usuário é Usuário padrão, no index (home) habilita somente para editar própria receita:  
![Filtro de receita administrador](docs/index_receitas_editar_somente_proprio_usuario.PNG?raw=true "Filtro de receita administrador")  
Quando usuário é Administrador, no index (home) habilita editar para todas receitas:  
![Filtro de receita administrador](docs/index_receitas_editar_administrador.PNG?raw=true "Filtro de receita administrador")  
Conversor de Unidades de Medidas convertendo unidades de tipos iguais:  
![Conversor de Unidades de Medidas convertendo unidades de tipos iguais](docs/conversor_unidades_medida_mesmo_tipo.PNG?raw=true "Conversor de Unidades de Medidas convertendo unidades de tipos iguais")  
Conversor de Unidades de Medidas convertendo unidades de tipos iguais:  
![Conversor de Unidades de Medidas convertendo unidades de tipos diferentes](docs/conversor_unidades_medida_tipo_diferente.PNG?raw=true "Conversor de Unidades de Medidas convertendo unidades de tipos iguais")  
Resultado dos testes utilizando o comando `./mvnw clean test`:  
![Resultado dos testes utilizando o comando mvnw clean test](docs/resultados_testes_utilizando_mvnw_clean_test.PNG?raw=true "Resultado dos testes utilizando o comando mvnw clean test")  
Resultado do coverage pelo jacoco plugin:  
![Resultado do coverage pelo jacoco plugin](docs/resultados_testes_coverage_jacoco_plugin.PNG?raw=true "Resultado do coverage pelo jacoco plugin")  

## Installation

Para a abrir o projeto basta clonar o repositório ou realizar o download e após:

- Importar o projeto, preferencialmente na IDE Eclipse;
- Fazer atualizações das dependências do Maven (Alt+F5 no Eclipse);
- Possuir no mínimo JDK 17 LTS instalado, sugestão de JDK: [OpenJDK Zulu];
- Possuir acesso à internet para atualização de dependências e funcionamento correto de URLs públicas;
- Necessário possuir o banco de dados MySQL Server, caso não possua, pode visitar [MySQL Community Download] para download e instalação;
- Duplicar e renomear o arquivo `application.properties.sample`, localizado em "src/main/resources/", para `application.properties` e alterar os parâmetros conforme segue:
  - URL de conexão com o banco, está para conexão local com MySQL: `spring.datasource.url=jdbc:mysql://localhost:3306/gft_projetos?createDatabaseIfNotExist=True`
  - Usuário root do banco: `spring.datasource.username=dbrootuser` - O usuário root com permissão ao MySQL Server local para criar bancos e tabelas;
  - Senha root do banco: `spring.datasource.password=dbrootpass` - Senha do usuário, alterar para a senha do root utilizado localmente;
  - Instrução para manutenção automática do banco: `spring.jpa.hibernate.ddl-auto=update` - Use `create-drop` para que ao iniciar a aplicação o spring JPA possa faça a criação necessária e faça a limpeza total do banco ao encerrar (sempre iniciando um banco de dados zerado); Use `update` para que o spring JPA apenas atualize as tabelas e atributos sem apagar os dados já existes;
  - Dados de servidor de email, alterar os parâmetros `spring.mail.host`, `spring.mail.username`, `spring.mail.password` para dados com .

## Tests

Os testes foram feitos utilizando o [JUnit 5], Mockito e MockMVC.  
Para rodar os testes no terminal (PowerShell ou similiar) basta executar na pasta do projeto o comando abaixo:

```shell
./mvnw clean test
```

Após o teste finalizado, é possível verificar relatório de coverage em: target/site/jacoco/index.html

## Usage

Para iniciar o projeto, basta utilizar uma das opções abaixo:

- Utilizando a [IDE Eclipse], basta executar (Run) a classe `AwesomeTastyRecipesApplication` no pacote `com.gft.atr`.  
- Utilizando o terminal (PowerShell ou similiar), basta executar na pasta do projeto o comando abaixo:

    ```shell
    ./mvnw clean package spring-boot:run
    ```

Quando o projeto subir, a aplicação estará acessível pelo endereço: <http://localhost:8080>  
Ao acessar a aplicação pela primeira vez, sem possuir dados, será carregada tela para Instalação, que irá popular o banco de dados com **2 usuários padrões** com perfis _Administrador_ e _Usuário_ e alguns cadastros de **Unidades de Medida**, **Ingredientes** e **Receitas**. Abaixo segue lista dos usuários disponíveis após fazer a instalação:

| Nome de Usuário   | Senha        | Perfil        |
| -------           | -----        | -----         |
| admin@email.com   | admin@1234   | Administrador |
| usuario@email.com | usuario@1234 | Usuário       |

## Support and Contributing

Dúvidas, problemas ou sugestões: luisczdidi@gmail.com.  
Para feedbacks, correção de bugs ou sugestões: abrir Issue ou Merge Request.

## Authors and acknowledgment

Feito por [Luis Carlos Zancanela]  

## Project status

_Done_

[Java 17]: https://docs.oracle.com/en/java/javase/17/docs/api/index.html
[JUnit 5]: https://junit.org/junit5/docs/current/user-guide/index.html
[IDE Eclipse]: https://www.eclipse.org/ide/
[spring initializr]: https://start.spring.io/
[Spring Boot]: https://spring.io/projects/spring-boot
[Maven]: https://maven.apache.org/
[MySQL Community Download]: https://dev.mysql.com/downloads/
[OpenJDK Zulu]: https://www.azul.com/downloads/
[Draw.io]: https://app.diagrams.net/
[Canva]: https://www.canva.com/pt_br/
[Luis Carlos Zancanela]: https://git.gft.com/lsza

[Bootstrap]: https://getbootstrap.com/docs/5.0
[JQuery]: https://jquery.com/
[Font Awesome]: https://fontawesome.com/
[jQuery Raty]: https://www.wbotelhos.com/raty/

[pom.xml]: https://git.gft.com/lsza/desafio-mvc/-/blob/main/pom.xml
