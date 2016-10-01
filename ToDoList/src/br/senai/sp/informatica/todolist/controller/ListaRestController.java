package br.senai.sp.informatica.todolist.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import br.senai.sp.informatica.todolist.dao.ListaDao;
import br.senai.sp.informatica.todolist.modelo.ItemLista;
import br.senai.sp.informatica.todolist.modelo.Lista;

@RestController
public class ListaRestController {
	
	//Indicando que vai injetar o DAO. Inje��o de depencendia.
	@Autowired
	private ListaDao listaDao;
	
	//Quando for uma requisi��o para LISTA, dispara este m�todo.  MediaType (Spring)    -APPLICATION_JSON_UTF8_VALUE retornar *STRING*.
	@RequestMapping(value="/lista", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	
	//Mapeando o parametro RequestBody, vem no corpo da requisi��o
	public ResponseEntity<Lista> inserir(@RequestBody String strLista){
	
		try {
			
			JSONObject job = new JSONObject(strLista);
			Lista lista = new Lista();
			lista.setTitulo(job.getString("titulo"));
			List<ItemLista> itens = new ArrayList<>();
			JSONArray arrayItens = job.getJSONArray("itens");
			
			for (int i = 0; i < arrayItens.length(); i++) {
				ItemLista item = new ItemLista();
				item.setDescricao(arrayItens.getString(i));
				item.setLista(lista);
				itens.add(item);
			}
			//Adiciona itens na lista
			lista.setItens(itens);
			//
			listaDao.inserir(lista);
			//
			URI location = new URI("/lista/"+lista.getId());
			//
			return ResponseEntity.created(location).body(lista);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value="/lista", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Lista> listar(){
		
		return listaDao.listar();
		
	}
	
	@RequestMapping(value="/lista/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Lista> listarCriteria(@PathVariable("id") long idLista){
		
		return listaDao.listarCriteria(idLista);
		
	}
	
	//Como n�o vamos retornar nada, usaremos uma classe VOID. Sem espeficicar o ResponseEntity tamb�m funciona, mas apita warning
	//Valor entre {} � variavel
	@RequestMapping(value="/lista/{id}", method=RequestMethod.DELETE)
	//Se o nome do mapeamento for o mesmo do parametro, n�o precisa especificar variavel ("id")
	public ResponseEntity<Void> excluir(@PathVariable("id") long idLista){
		
		listaDao.excluir(idLista);
		//Atalho para n�o instanciar o NEW responseEntity
		//.build � para construir aquilo 
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/item/{idItem}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> excluirItem(@PathVariable long idItem){
		
		try {
			listaDao.excluirItem(idItem);
			return ResponseEntity.noContent().build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		//REALIZAR /LISTA/ID
		// /ITEM/ID
		
	}
	
	
	
}
