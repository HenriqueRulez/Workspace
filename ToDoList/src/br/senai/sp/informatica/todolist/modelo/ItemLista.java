package br.senai.sp.informatica.todolist.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Ignore a propriedade. Ignora a propriedade para não entrar em looping infinito
@JsonIgnoreProperties("lista")
@Entity
public class ItemLista {

	// SEMPRE que for um campo ID, utilizar esta regra
	// Não utilizar tipo primitivo, utilizar uma classe LETRA MAIUSCULA
	// (TIPO PRIMITIVO NÃO CONSEGUE COMPARAR COM NULO)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private boolean feito;
	
	//MappedBy "lista". Está falando na  classe LISTA que este é o mapeamento. Essa é a chave estrangeira.
	@ManyToOne
	@JoinColumn(name="lista_id")
	private Lista lista;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isFeito() {
		return feito;
	}

	public void setFeito(boolean feito) {
		this.feito = feito;
	}

	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}

}