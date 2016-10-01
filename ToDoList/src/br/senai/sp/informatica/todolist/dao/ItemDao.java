package br.senai.sp.informatica.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.informatica.todolist.modelo.ItemLista;
import br.senai.sp.informatica.todolist.modelo.Lista;

@Repository
public class ItemDao {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	public void marcarFeito(long idItem, boolean valor){
		
		/*
		 * Persist -> sempre tem que ter objeto anexado ao banco. (gerenciado ao banco). Sò pode usar persist se antes buscar no banco.
		 *
		 * Merge -> Não precisa ter buscado no banco anteriormente para realizar o merge.
		 * Como funciona: Atualiza o campo. Ele MESCLA com o banco. Caso tenha campos nulos, ele atualizará o campo com os campos nulos.
		*/
		
		ItemLista item = manager.find(ItemLista.class, idItem);
		
		item.setFeito(valor);
		manager.merge(item);
		
	}
	
	@Transactional
	public void inserir(long idLista, ItemLista item){
		
		item.setLista(manager.find(Lista.class, idLista));
		manager.persist(item);
	}
	
	//Exibe item da lista
	
	
	//DAO -> CONTROLLER
	
	//Listar item POR ID
	public List<ItemLista> listarItem(){
		
		TypedQuery<ItemLista> query = manager.createQuery("select item from Lista item", ItemLista.class);
		return query.getResultList();
	}
}
