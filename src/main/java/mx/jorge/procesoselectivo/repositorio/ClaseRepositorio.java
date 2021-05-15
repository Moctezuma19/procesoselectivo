package mx.jorge.procesoselectivo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.jorge.procesoselectivo.entidad.Clase;

public interface ClaseRepositorio extends JpaRepository<Clase, Integer> {
	@Query("SELECT c FROM Clase c")
	public List<Clase> findAll();
	
	@Query("SELECT c FROM Clase c WHERE c.nombre LIKE CONCAT('%',:nombre,'%')")
	public List<Clase> findByName(@Param("nombre") String nombre);

}
