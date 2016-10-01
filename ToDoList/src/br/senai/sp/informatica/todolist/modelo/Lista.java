package br.senai.sp.informatica.todolist.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

//Usaremos  sempre javax.persistence, sempre utilizar a especificação não o PRODUTO (HIBERNATE)
@Entity
public class Lista {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100)
	private String titulo;

	//Mapeada pela variavel lista
	//Cascade = A forma que você quer que seja propagado.
	@OneToMany(mappedBy="lista", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	// Lista genérica de ItemLista -> LISTA é uma INTERFACE (java.util)
	private List<ItemLista> itens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<ItemLista> getItens() {
		return itens;
	}

	public void setItens(List<ItemLista> itens) {
		this.itens = itens;
	}

	//Pode ter o nome do atributo com um nome, e o JSON ver com outro
	@JsonProperty("feito")
	//Boolean sempre utiliza prefixo IS
	public boolean isRealizada(){
		for (ItemLista item : itens) {
			if(!item.isFeito()){
				return false;
			}
		}
		return true;
	}
	
}
