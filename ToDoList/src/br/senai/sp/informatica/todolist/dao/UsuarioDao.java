package br.senai.sp.informatica.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.informatica.todolist.modelo.Usuario;

@Repository
public class UsuarioDao {

	@PersistenceContext
	private EntityManager manager;
	
	
	@Transactional
	public void cadastrar(Usuario usuario){
		
		
		manager.persist(usuario);
		
	}
	
	
	public Usuario logar(Usuario usuario){
		
		// :login -> VARIAVEL - Parametro que irá ser substituido
		TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u where u.login = :login and u.senha = :senha ", Usuario.class);
		
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());
	
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		
		
		
		
	}
	
	
}
