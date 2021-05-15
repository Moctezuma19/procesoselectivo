package mx.jorge.procesoselectivo.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name = "Participante")
public class Participante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nombre", nullable = false, length = 25)
	private String nombre;
	
	@Column(name = "correo", nullable = false, length = 25)
	private String correo;
	
	@Column(name = "observaciones", nullable = true, length = 1024)
	private String observaciones;
	
	@ManyToOne(targetEntity = Clase.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_usuario", referencedColumnName = "id", nullable = false)
	private Clase fk_id_clase;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Clase getFk_id_clase() {
		return fk_id_clase;
	}

	public void setFk_id_clase(Clase fk_id_clase) {
		this.fk_id_clase = fk_id_clase;
	}
	
	
}
