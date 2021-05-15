package mx.jorge.procesoselectivo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.jorge.procesoselectivo.entidad.Clase;

public interface ClaseRepositorio extends JpaRepository<Clase, Integer> {
	@Query("SELECT c FROM Clase c")
	public List<Clase> findAll();

}
