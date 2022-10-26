package com.gft.atr.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gft.atr.entities.Ingrediente;
import com.gft.atr.exceptions.ImagemComFormatoIncompativelException;
import com.gft.atr.exceptions.ImagemComTamanhoMaiorQuePermitidoException;
import com.gft.atr.exceptions.IngredienteNaoEncontradoException;
import com.gft.atr.repositories.IngredienteRepository;

@Service
public class IngredienteService {
	
	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	/**
	 * CRUD - Create / Update
	 * @param Ingrediente
	 * @return Ingrediente
	 */
	public Ingrediente salvarIngrediente(Ingrediente ingrediente) {
		return ingredienteRepository.save(ingrediente);
		
	}
	
	/**
	 * CRUD - Read
	 * @param String 
	 * @return List<Ingrediente>
	 */
	public List<Ingrediente> listarIngredientes(String nome) {
		
		if(nome!=null)
			return listarIngredientesPorNome(nome);

		return listarTodosIngredientes();
	
	}
	
	private List<Ingrediente> listarIngredientesPorNome(String nome) {
		
		return ingredienteRepository.findByNomeContains(nome);
		
	}
	
	private List<Ingrediente> listarTodosIngredientes() {
		
		return ingredienteRepository.findAll();
	
	}
	
	/**
	 * CRUD - Read
	 * @param Long
	 * @return Ingrediente
	 * @throws IngredienteNaoEncontradoException
	 */
	public Ingrediente obterIngrediente(Long id) throws IngredienteNaoEncontradoException {
		
		Optional<Ingrediente> ingrediente = ingredienteRepository.findById(id);
		if(ingrediente.isEmpty()) {
			throw new IngredienteNaoEncontradoException(id);
		}
		
		return ingrediente.get();
		
	}

	/**
	 * CRUD - Delete
	 * @param Long
	 * @throws IngredienteNaoEncontradoException
	 */
	public void excluirIngrediente(Long id) throws IngredienteNaoEncontradoException {
		
		obterIngrediente(id);

		ingredienteRepository.deleteById(id);
		
	}
	
	public void adicionarImagemURL(Ingrediente ingrediente, String imagemUrl)	throws ImagemComFormatoIncompativelException {

		String tipoArquivo="";
		HttpURLConnection  httpConnection = null;
		try {
			URL urlObj = new URL(imagemUrl);                                    
			httpConnection = (HttpURLConnection)urlObj.openConnection();
			tipoArquivo = httpConnection.getContentType();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImagemComFormatoIncompativelException();
		} finally {
			if(httpConnection != null) httpConnection.disconnect();
		}
		
		if(tipoArquivo.equals("image/png") || tipoArquivo.equals("image/jpeg")){			
			ingrediente.setImagem(imagemUrl);
		} else {
			throw new ImagemComFormatoIncompativelException();
		}
	}
	
	public void adicionarImagemArquivo(Ingrediente ingrediente, MultipartFile imagem)
			throws ImagemComTamanhoMaiorQuePermitidoException
							, ImagemComFormatoIncompativelException
	{
		if(imagem.getSize() > 524288) throw new ImagemComTamanhoMaiorQuePermitidoException();
		
		String tipoArquivo="";
		try {
			tipoArquivo = imagem.getContentType();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(tipoArquivo !=null) {
			if(tipoArquivo.equals("image/png") || tipoArquivo.equals("image/jpeg")) {
				
				String imagemEncodada = "";
				
				try {
					imagemEncodada = Base64.encodeBase64String(imagem.getBytes());
				} catch (IOException e2) {
					e2.printStackTrace();
					throw new ImagemComFormatoIncompativelException();
				}
				
				ingrediente.setImagem("data:image/png;base64,"+imagemEncodada);
				
			} else if (!"No file selected.".equals(imagem.getName())){
				throw new ImagemComFormatoIncompativelException();
			}
		}
	
	}

}
