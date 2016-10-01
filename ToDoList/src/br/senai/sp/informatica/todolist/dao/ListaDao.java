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
public class ListaDao {

	//Objeto EntityManager - Quem vai criar um EntityManager é o Spring, portanto não usaremos NEW
	//Não precisa dar NEW
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public void inserir(Lista lista){
		//Ele persiste os dados do objeto. Faz tudo
		//SE NÃO USAR O CASCATETYPE.ALL DÁ PROBLEMA
		manager.persist(lista);
		
		
	}
	
	//JPQL
	//Metodo para listar todas as listas
	//Typed query é melhor que a query
	public List<Lista> listar(){
		
		/*Exemplo de busca de um campo único, retornando apenas String
		
		SEMPRE A PROPRIEDADE DO OBJETO
		TypedQuery<String> query = manager.createQuery("select l.descricao from Lista l")
		
		*/
		
		//Precisa especificar o tipo de objeto a ser buscado. Busca o objeto completo
		TypedQuery<Lista> query = manager.createQuery("select l from Lista l", Lista.class);
		return query.getResultList();
	}
	
	@Transactional
	public void excluir(long idLista){
		
		//Busca o item e depois remove
		Lista lista = manager.find(Lista.class, idLista);
		
		manager.remove(lista);
		
		/* Tudo em uma linha só! UAU
		Lista lista = manager.remove(manager.find(Lista.class, idLista));
		 */
	}
	
	@Transactional
	public void excluirItem(long idItem){
		
		ItemLista item = manager.find(ItemLista.class, idItem);
		Lista lista = item.getLista();
		lista.getItens().remove(item);
		manager.merge(lista);
		
	}
	
	//Exibir lista
	public List<Lista> listarCriteria(Long idLista){
		TypedQuery<Lista> query = manager.createQuery("select l from Lista l where l.id = :id", Lista.class);
		query.setParameter("id", idLista);
		return query.getResultList();
	}
	

}
