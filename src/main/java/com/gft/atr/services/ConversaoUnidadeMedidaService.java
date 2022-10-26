package com.gft.atr.services;

import org.springframework.stereotype.Service;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.entities.UnidadeMedida;
import com.gft.atr.enums.TipoReferencia;
import com.gft.atr.enums.UnidadeReferencia;
import com.gft.atr.exceptions.ImpossivelCalcularFatorConversaoException;

@Service
public class ConversaoUnidadeMedidaService {
	
	public double converterMedidas(Ingrediente ingrediente
																	, UnidadeMedida unidade1
                            			, double quantidadeUnidadeMedida1
                            			, UnidadeMedida unidade2)
                     throws ImpossivelCalcularFatorConversaoException {
		
		TipoReferencia tipoUnidade1 = unidade1.getUnidadeReferencia().getTipoReferencia();
		TipoReferencia tipoUnidade2 = unidade2.getUnidadeReferencia().getTipoReferencia();

    if (tipoUnidade1.equals(TipoReferencia.GERAL)
    			|| tipoUnidade2.equals(TipoReferencia.GERAL))
    	throw new ImpossivelCalcularFatorConversaoException(TipoReferencia.GERAL);
    
    if (ingrediente == null) {
    	return converterMedidas(unidade1, quantidadeUnidadeMedida1, unidade2);
    }
    
    if (!tipoUnidade1.equals(tipoUnidade2)) {
    	
    	double densidade = ingrediente.getDensidade();
    	
    	double conversao = 0;
    	
    	if(tipoUnidade1.equals(TipoReferencia.VOLUME)) {
    		conversao = densidade * UnidadeReferencia.CENTIMETRO_CUBICO.getFator().calculaFatorConversao(unidade1);
    	} else {
    		conversao = UnidadeReferencia.GRAMA.getFator().calculaFatorConversao(unidade1) / densidade;
    	}
    		
    	return unidade1.getQuantidadeUnidadeReferencia() * conversao * quantidadeUnidadeMedida1
    						/ unidade2.getQuantidadeUnidadeReferencia();
    
    }
    
    return converterMedidas(unidade1, quantidadeUnidadeMedida1, unidade2);
  
	}

  public double converterMedidas(UnidadeMedida unidade1
                              		, double quantidadeUnidadeMedida1
                              		, UnidadeMedida unidade2)
                              throws ImpossivelCalcularFatorConversaoException {
  
    double fator = unidade2.getUnidadeReferencia().getFator().calculaFatorConversao(unidade1);
    
    return (unidade1.getQuantidadeUnidadeReferencia() * fator * quantidadeUnidadeMedida1)
    						/ unidade2.getQuantidadeUnidadeReferencia();
  
  }

}
