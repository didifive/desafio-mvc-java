package com.gft.atr.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gft.atr.entities.Receita;
import com.gft.atr.exceptions.ImagemComFormatoIncompativelException;
import com.gft.atr.exceptions.ImagemComTamanhoMaiorQuePermitidoException;
import com.gft.atr.exceptions.ReceitaNaoEncontradaException;
import com.gft.atr.exceptions.UsuarioNaoEncontradoException;
import com.gft.atr.repositories.ReceitaRepository;

@Service
public class ReceitaService {
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
	/**
	 * CRUD - Create / Update
	 * @param Receita
	 * @return Receita
	 */
	public Receita salvarReceita(Receita receita) {
		
		if(receita.getId()==null) {		
			receita.getIngredientes().forEach(i -> i.getReceitaIngredientePK().setReceita(receita));
			receita.getModosPreparo().forEach(m -> m.setReceita(receita));
		}
		
		return receitaRepository.save(receita);
		
	}
	
	/**
	 * CRUD - Read
	 * @param String 
	 * @return List<Receita>
	 * @throws UsuarioNaoEncontradoException 
	 */
	
	public List<Receita> listarReceitas(String nome, String nomeIngrediente, Long idUsuario) throws UsuarioNaoEncontradoException {
		
		boolean haveReceita = (nome!=null && !nome.isBlank());
		boolean haveIngrediente = (nomeIngrediente!=null && !nomeIngrediente.isBlank());
		boolean haveUsuario = (idUsuario!=null && idUsuario!=0);
		
		if(haveReceita && !haveIngrediente && !haveUsuario)
			return listarReceitasPorNome(nome);
		
		if(haveUsuario && !haveIngrediente )
			return listarReceitasPorNomeEUsuario(nome,idUsuario);
		
		if(haveIngrediente && !haveUsuario) 
			return listarReceitasPorNomeEIngrediente(nome, nomeIngrediente);
		
		if(haveIngrediente && haveUsuario) 
			return listarReceitasPorNomeEIngredienteEUsuario(nome, nomeIngrediente, idUsuario);
		
		return listarTodasReceitas();
	
	}
	
	private List<Receita> listarReceitasPorNome(String nome) {
		
		return receitaRepository.findAllByNomeContains(nome);
		
	}
	
	private List<Receita> listarReceitasPorNomeEUsuario(String nome, Long idUsuario) throws UsuarioNaoEncontradoException {
		
		return receitaRepository.findAllByNomeContainsAndUsuarioId(nome, idUsuario);
		
	}
	
	private List<Receita> listarReceitasPorNomeEIngrediente(String nome, String nomeIngrediente) {
		
		return receitaRepository.findAllByNomeContainsAndIngredientesReceitaIngredientePKIngredienteNomeContains(nome, nomeIngrediente);
		
	}
	
	private List<Receita> listarReceitasPorNomeEIngredienteEUsuario(String nome, String nomeIngrediente, Long idUsuario) throws UsuarioNaoEncontradoException {
		
		return receitaRepository
						.findAllByNomeContainsAndIngredientesReceitaIngredientePKIngredienteNomeContainsAndUsuarioId(nome,nomeIngrediente,idUsuario);
		
	}
	
	
	private List<Receita> listarTodasReceitas() {
		
		return receitaRepository.findAll();
	
	}
	
	/**
	 * CRUD - Read
	 * @param Long
	 * @return Receita
	 * @throws ReceitaNaoEncontradaException
	 */
	public Receita obterReceita(Long id) throws ReceitaNaoEncontradaException {
		
		Optional<Receita> receita = receitaRepository.findById(id);
		if(receita.isEmpty()) {
			throw new ReceitaNaoEncontradaException(id);
		}
		
		receita.get().getModosPreparo().sort(Comparator.comparing(m -> m.getOrdem()));
		
		return receita.get();
		
	}

	/**
	 * CRUD - Delete
	 * @param Long
	 * @throws ReceitaNaoEncontradaException
	 */
	public void excluirReceita(Long id) throws ReceitaNaoEncontradaException {
		
		obterReceita(id);

		receitaRepository.deleteById(id);
		
	}
	
	
	public void adicionarImagemURL(Receita receita, String imagemUrl)	throws ImagemComFormatoIncompativelException {

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
			receita.setImagem(imagemUrl);
		} else {
			throw new ImagemComFormatoIncompativelException();
		}
	}
	
	public void adicionarImagemArquivo(Receita receita, MultipartFile imagem)
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
				
				receita.setImagem("data:image/png;base64,"+imagemEncodada);
				
			} else if (!"No file selected.".equals(imagem.getName())){
				throw new ImagemComFormatoIncompativelException();
			}
		}
	
	}

}
