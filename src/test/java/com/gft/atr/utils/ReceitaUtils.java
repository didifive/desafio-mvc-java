package com.gft.atr.utils;

import org.mockito.Mock;

import com.gft.atr.entities.Receita;
import com.gft.atr.entities.ReceitaIngrediente;
import com.gft.atr.entities.builders.ReceitaBuilder;

public class ReceitaUtils {
	
	@Mock
	private static ReceitaIngrediente receitaIngrediente;
	
	private static final Long ID = 1L;
	private static final String NOME = "Bolo de Cenoura";
	private static final String IMAGEM = "";
	private static final String DESCRICAO = "Um olo muito gostoso e fácil de fazer.";

	
	public static Receita createFakeEntity() {
		return new ReceitaBuilder()
							.withId(ID)
							.withNome(NOME)
							.withImagem(IMAGEM)
							.withDescricao(DESCRICAO)
							.build();
	}

	public static Receita createFakeEntityWithoutId() {
		return new ReceitaBuilder()
				.withNome(NOME)
				.withNome(NOME)
				.withImagem(IMAGEM)
				.withDescricao(DESCRICAO)
				.build();
	}
	
}
