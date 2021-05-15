package mx.jorge.procesoselectivo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.jorge.procesoselectivo.entidad.Participante;

public interface ParticipanteRepositorio extends JpaRepository<Participante, Integer> {
	@Query("SELECT c FROM Participante c")
	public List<Participante> findAll();
	
	@Query("SELECT c FROM Participante c WHERE c.nombre LIKE CONCAT('%',:nombre,'%')")
	public List<Participante> findByName(@Param("nombre") String nombre);
}
