package com.gft.atr.utils;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.builders.IngredienteBuilder;

public class IngredienteUtils {
	
	private static final Long ID = 1L;
	private static final String NOME = "Farinha";
	private static final String IMAGEM = "";
	private static final double DENSIDADE = 1.5;
	
	
 public static Ingrediente createFakeEntity() {
		return new IngredienteBuilder()
							.withId(ID)
							.withNome(NOME)
							.withImagem(IMAGEM)
							.withDensidade(DENSIDADE)
							.build();
	}

	public static Ingrediente createFakeEntityWithoutId() {
		return new IngredienteBuilder()
				.withNome(NOME)
				.withImagem(IMAGEM)
				.withDensidade(DENSIDADE)
				.build();
	}
	
}
