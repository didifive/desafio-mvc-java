package com.gft.atr.services.dbpopulate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.builders.IngredienteBuilder;
import com.gft.atr.services.IngredienteService;

@Component
public class IngredienteDBPopulateService {
	
	@Autowired
	IngredienteService ingredienteService;
	
	public List<Ingrediente> popularIngrediente() {
		
		List<Ingrediente> ingredientes = new ArrayList<>();
		
		ingredientes.add(new IngredienteBuilder()
															.withId(1L)
															.withNome("Farinha")
															.withImagem("Foto")
															.withDensidade(0.8)
															.build());
		ingredientes.add(new IngredienteBuilder()
                      				.withId(2L)
                      				.withNome("Leite")
                      				.withImagem("Foto")
                      				.withDensidade(1.2)
                      				.build());
		ingredientes.add(new IngredienteBuilder()
                      				.withId(3L)
                      				.withNome("Fermento")
                      				.withImagem("Foto")
                      				.withDensidade(0.5)
                      				.build());
		ingredientes.add(new IngredienteBuilder()
                      				.withId(4L)
                      				.withNome("Ovo")
                      				.withImagem("Foto")
                      				.withDensidade(1.5)
                      				.build());
		ingredientes.add(new IngredienteBuilder()
                      				.withId(5L)
                      				.withNome("Cenoura")
                      				.withImagem("Foto")
                      				.withDensidade(1.7)
                      				.build());
		
		ingredientes.forEach(i -> ingredienteService.salvarIngrediente(i));
		
		return ingredienteService.listarIngredientes(null);
		
	}
	

}
