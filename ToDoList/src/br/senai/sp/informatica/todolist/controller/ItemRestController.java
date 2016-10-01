package br.senai.sp.informatica.todolist.controller;

import java.net.URI;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.informatica.todolist.dao.ItemDao;
import br.senai.sp.informatica.todolist.modelo.ItemLista;

@RestController
public class ItemRestController {

	@Autowired
	private ItemDao itemDao;
	
	@RequestMapping(value="/item/{idItem}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	
	public ResponseEntity<Void> marcarFeito(@PathVariable long idItem, @RequestBody String strFeito){
		
		try {
			JSONObject job = new JSONObject(strFeito);
			itemDao.marcarFeito(idItem, job.getBoolean("feito"));
			
			//Padrão HEADER sem nd. vamos criar um			
			HttpHeaders responseHeader = new HttpHeaders();
			
			//Location			
			URI location = new URI("/item/"+idItem);
			
			//Colocar location no cabeçalho
			responseHeader.setLocation(location);
			
			return new ResponseEntity<>(responseHeader, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	//Como o JSON bate com os atributos da classe, o JSON vira o atributo da lista.
	@RequestMapping(value="/lista/{idLista}/item", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ItemLista> addItem(@PathVariable long idLista, @RequestBody ItemLista item){
		
		try {
			itemDao.inserir(idLista, item);
			URI location = new URI("/item/"+item.getId());
			
			return ResponseEntity.created(location).body(item);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
}
