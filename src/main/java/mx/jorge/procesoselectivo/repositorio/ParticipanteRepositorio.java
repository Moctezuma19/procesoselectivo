package mx.jorge.procesoselectivo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.jorge.procesoselectivo.entidad.Participante;

public interface ParticipanteRepositorio extends JpaRepository<Participante, Integer> {
	@Query("SELECT c FROM Participante c")
	public List<Participante> findAll();
}
