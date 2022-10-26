package com.gft.atr.services.dbpopulate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.Receita;
import com.gft.atr.entities.ReceitaIngredientePK;
import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.entities.Usuario;
import com.gft.atr.entities.builders.AvaliacaoBuilder;
import com.gft.atr.entities.builders.ModoPreparoBuilder;
import com.gft.atr.entities.builders.ReceitaBuilder;
import com.gft.atr.entities.builders.ReceitaIngredienteBuilder;
import com.gft.atr.services.ReceitaService;

@Service
public class ReceitaDBPopulateService {

	@Autowired
	ReceitaService receitaService;

	@Autowired
	IngredienteDBPopulateService ingredientePopulate;

	@Autowired
	UnidadeMedidaDBPopulateService unidadeMedidaPopulate;

	@Autowired
	UsuarioDBPopulateService usuarioMedidaPopulate;

	public void popularReceita() {

		List<Usuario> usuarios = usuarioMedidaPopulate.popularUsuario();
		
		List<UnidadeMedida> unidadesMedida = unidadeMedidaPopulate.popularUnidadeMedida();
		
		List<Ingrediente> ingredientes = ingredientePopulate.popularIngrediente();

		List<Receita> receitas = new ArrayList<>();

		Receita receita1 = new ReceitaBuilder().withId(1L).withNome("Bolo de Cenoura").withImagem("")
				.withDescricao("Um bolo muito gostoso e fácil de fazer.")
				.withTempoPreparo(50).withRendimento(20).withUsuario(usuarios.get(0)).build();
		receita1.setModosPreparo(Arrays.asList(
				new ModoPreparoBuilder().withId(1L).withOrdem(1).withDescricao("Jogar tudo no liquidificador e bater")
						.withReceita(receita1).build(),
				new ModoPreparoBuilder().withId(2L).withOrdem(2).withDescricao("Colocar na assadeira e assar por 30 minutos")
						.withReceita(receita1).build()));
		receita1.setAvaliacoes(Arrays.asList(
				new AvaliacaoBuilder().withId(1L).withNota(4.5).withReceita(receita1).withUsuario(usuarios.get(1)).build()));
		receita1.setIngredientes(Arrays.asList(
				new ReceitaIngredienteBuilder().withReceitaIngredientePK(new ReceitaIngredientePK(receita1, ingredientes.get(0)))
						.withUnidadeMedida(unidadesMedida.get(0)).withQuantidade(5).build(),
				new ReceitaIngredienteBuilder().withReceitaIngredientePK(new ReceitaIngredientePK(receita1, ingredientes.get(1)))
						.withUnidadeMedida(unidadesMedida.get(5)).withQuantidade(1).build(),
				new ReceitaIngredienteBuilder().withReceitaIngredientePK(new ReceitaIngredientePK(receita1, ingredientes.get(4)))
						.withUnidadeMedida(unidadesMedida.get(8)).withQuantidade(3).build()));
		
		Receita receita2 = new ReceitaBuilder().withId(2L).withNome("Bolo de Fubá").withImagem("")
				.withDescricao("Um bolo muito gostoso e fácil de fazer.")
				.withTempoPreparo(50).withRendimento(20).withUsuario(usuarios.get(1)).build();
		receita2.setModosPreparo(Arrays.asList(
				new ModoPreparoBuilder().withId(3L).withOrdem(1).withDescricao("Jogar tudo no liquidificador e bater com fé")
						.withReceita(receita2).build(),
				new ModoPreparoBuilder().withId(4L).withOrdem(2).withDescricao("Colocar na assadeira e assar com ódio por 30 minutos")
						.withReceita(receita2).build()));
		receita2.setAvaliacoes(Arrays.asList(
				new AvaliacaoBuilder().withId(2L).withNota(3.0).withReceita(receita2).withUsuario(usuarios.get(0)).build()));
		receita2.setIngredientes(Arrays.asList(
				new ReceitaIngredienteBuilder().withReceitaIngredientePK(new ReceitaIngredientePK(receita2, ingredientes.get(1)))
						.withUnidadeMedida(unidadesMedida.get(0)).withQuantidade(5).build(),
				new ReceitaIngredienteBuilder().withReceitaIngredientePK(new ReceitaIngredientePK(receita2, ingredientes.get(3)))
						.withUnidadeMedida(unidadesMedida.get(5)).withQuantidade(1).build(),
				new ReceitaIngredienteBuilder().withReceitaIngredientePK(new ReceitaIngredientePK(receita2, ingredientes.get(2)))
						.withUnidadeMedida(unidadesMedida.get(8)).withQuantidade(3).build()));
		
		Receita receita3 = new ReceitaBuilder().withId(3L).withNome("Café Cremoso").withImagem("")
				.withDescricao("Um café cremoso para começar bem o dia.")
				.withTempoPreparo(10).withRendimento(3).withUsuario(usuarios.get(1)).build();
		receita3.setModosPreparo(Arrays.asList(
				new ModoPreparoBuilder().withId(5L).withOrdem(1).withDescricao("Bater o creme de leite até ganhar consistência")
						.withReceita(receita3).build(),
				new ModoPreparoBuilder().withId(6L).withOrdem(2).withDescricao("Misturar o café")
						.withReceita(receita3).build()));
		receita3.setAvaliacoes(Arrays.asList(
				new AvaliacaoBuilder().withId(3L).withNota(4.0).withReceita(receita3).withUsuario(usuarios.get(0)).build()));
		receita3.setIngredientes(Arrays.asList(
				new ReceitaIngredienteBuilder().withReceitaIngredientePK(new ReceitaIngredientePK(receita3, ingredientes.get(0)))
						.withUnidadeMedida(unidadesMedida.get(0)).withQuantidade(5).build(),
				new ReceitaIngredienteBuilder().withReceitaIngredientePK(new ReceitaIngredientePK(receita3, ingredientes.get(4)))
						.withUnidadeMedida(unidadesMedida.get(8)).withQuantidade(3).build()));
		
		receitas.add(receita1);
		receitas.add(receita2);
		receitas.add(receita3);

		receitas.forEach(r -> receitaService.salvarReceita(r));

	}

}
