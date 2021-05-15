package mx.jorge.procesoselectivo.entidad;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



@Entity
@Table(name = "Clase")
public class Clase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "descripcion", nullable = true, length = 1024)
	private String descripcion;
	
	@Column(name = "tipo", nullable = true, length = 25)
	private String tipo;

	@OneToMany(mappedBy = "fk_id_clase", targetEntity = Participante.class)
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Participante> participantes = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Participante> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Participante> contactos) {
		this.participantes = participantes;
	}
	
	
}
