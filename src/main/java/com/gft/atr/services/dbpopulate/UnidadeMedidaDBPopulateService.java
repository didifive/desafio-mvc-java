package com.gft.atr.services.dbpopulate;

import static com.gft.atr.enums.UnidadeReferencia.GRAMA;
import static com.gft.atr.enums.UnidadeReferencia.KILOGRAMA;
import static com.gft.atr.enums.UnidadeReferencia.LITRO;
import static com.gft.atr.enums.UnidadeReferencia.MILILITRO;
import static com.gft.atr.enums.UnidadeReferencia.UNIDADE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.entities.builders.UnidadeMedidaBuilder;
import com.gft.atr.services.UnidadeMedidaService;

@Component
public class UnidadeMedidaDBPopulateService {
	
	@Autowired
	UnidadeMedidaService unidadeMedidaService;

	public List<UnidadeMedida> popularUnidadeMedida() {
		List<UnidadeMedida> unidadesMedida = new ArrayList<>();
		
		unidadesMedida.add(new UnidadeMedidaBuilder()
															.withId(1L)
															.withNome("Xícara")
															.withAbreciacao("xic.")
															.withUnidadeReferencia(MILILITRO)
															.withQuantidadeUnidadeRerefencia(240)
															.build());
		unidadesMedida.add(new UnidadeMedidaBuilder()
                      				.withId(2L)
                      				.withNome("Copo")
                      				.withAbreciacao("copo")
                      				.withUnidadeReferencia(MILILITRO)
                      				.withQuantidadeUnidadeRerefencia(200)
                      				.build());
		unidadesMedida.add(new UnidadeMedidaBuilder()
                      				.withId(3L)
                      				.withNome("Colher de Sopa")
                      				.withAbreciacao("c. sopa")
                      				.withUnidadeReferencia(MILILITRO)
                      				.withQuantidadeUnidadeRerefencia(15)
                      				.build());
		unidadesMedida.add(new UnidadeMedidaBuilder()
                      				.withId(4L)
                      				.withNome("Colher de Café")
                      				.withAbreciacao("c. café")
                      				.withUnidadeReferencia(MILILITRO)
                      				.withQuantidadeUnidadeRerefencia(2.5)
                      				.build());
		unidadesMedida.add(new UnidadeMedidaBuilder()
                      				.withId(5L)
                      				.withNome("mililitro")
                      				.withAbreciacao("ml")
                      				.withUnidadeReferencia(MILILITRO)
                      				.withQuantidadeUnidadeRerefencia(1)
                      				.build());
		unidadesMedida.add(new UnidadeMedidaBuilder()
                      				.withId(6L)
                      				.withNome("Litro")
                      				.withAbreciacao("l")
                      				.withUnidadeReferencia(LITRO)
                      				.withQuantidadeUnidadeRerefencia(1)
                      				.build());
		unidadesMedida.add(new UnidadeMedidaBuilder()
                      				.withId(7L)
                      				.withNome("grama")
                      				.withAbreciacao("g")
                      				.withUnidadeReferencia(GRAMA)
                      				.withQuantidadeUnidadeRerefencia(1)
                      				.build());
		unidadesMedida.add(new UnidadeMedidaBuilder()
                      				.withId(8L)
                      				.withNome("Kilograma")
                      				.withAbreciacao("Kg")
                      				.withUnidadeReferencia(KILOGRAMA)
                      				.withQuantidadeUnidadeRerefencia(1)
                      				.build());
		unidadesMedida.add(new UnidadeMedidaBuilder()
                      				.withId(9L)
                      				.withNome("Unidade")
                      				.withAbreciacao("unid")
                      				.withUnidadeReferencia(UNIDADE)
                      				.withQuantidadeUnidadeRerefencia(1)
                      				.build());
		unidadesMedida.add(new UnidadeMedidaBuilder()
                      				.withId(10L)
                      				.withNome("Caixa")
                      				.withAbreciacao("cx")
                      				.withUnidadeReferencia(UNIDADE)
                      				.withQuantidadeUnidadeRerefencia(1)
                      				.build());
		
		unidadesMedida.forEach(u -> unidadeMedidaService.salvarUnidadeMedida(u));
		
		return unidadeMedidaService.listarUnidadesMedida(null);
		
	}	

}
