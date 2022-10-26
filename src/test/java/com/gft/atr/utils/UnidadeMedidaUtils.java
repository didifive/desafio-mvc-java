package com.gft.atr.utils;

import static com.gft.atr.enums.UnidadeReferencia.MILILITRO;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.entities.builders.UnidadeMedidaBuilder;
import com.gft.atr.enums.UnidadeReferencia;

public class UnidadeMedidaUtils {
	
	private static final Long ID = 1L;
	private static final String NOME = "Xícara";
	private static final String ABREVIACAO= "xic.";
	private static final UnidadeReferencia UNIDADE_DE_REFERENCIA = MILILITRO;
	private static final double QUANTIDADE_UNIDADE_REFERENCIA = 240;
	
	public static UnidadeMedida createFakeEntity() {
		return new UnidadeMedidaBuilder()
							.withId(ID)
							.withNome(NOME)
							.withAbreciacao(ABREVIACAO)
							.withUnidadeReferencia(UNIDADE_DE_REFERENCIA)
							.withQuantidadeUnidadeRerefencia(QUANTIDADE_UNIDADE_REFERENCIA)
							.build();
	}

	public static UnidadeMedida createFakeEntityWithoutId() {
		return new UnidadeMedidaBuilder()
				.withNome(NOME)
				.withAbreciacao(ABREVIACAO)
				.withUnidadeReferencia(UNIDADE_DE_REFERENCIA)
				.withQuantidadeUnidadeRerefencia(QUANTIDADE_UNIDADE_REFERENCIA)
				.build();
	}
	
}
