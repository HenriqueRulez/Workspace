package br.senai.sp.informatica.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.todolist.modelo.Lista;

@Repository
public class ListaDao {

	//Objeto EntityManager - Quem vai criar um EntityManager é o Spring, portanto não usaremos NEW
	//Não precisa dar NEW
	@PersistenceContext
	private EntityManager manager;
	
	public void inserir(Lista lista){
		//Ele persiste os dados do objeto. Faz tudo
		//SE NÃO USAR O CASCATETYPE.ALL DÁ PROBLEMA
		manager.persist(lista);
		
	}
}
