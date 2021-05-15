package mx.jorge.procesoselectivo.controlador;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mx.jorge.procesoselectivo.entidad.Clase;
import mx.jorge.procesoselectivo.entidad.Participante;
import mx.jorge.procesoselectivo.repositorio.ParticipanteRepositorio;

import org.json.JSONArray;
import org.json.JSONObject;

@Controller
@RequestMapping(value = "/participantes")
public class ParticipanteControlador {
	@Autowired
	private ParticipanteRepositorio participante_bd;
	//method = {RequestMethod.POST,RequestMethod.GET}
	@GetMapping("/todos")
	public String obtenTodosParticipantes(HttpServletRequest request) {
		LinkedList<Participante> participantes = new LinkedList<>(participante_bd.findAll());
		JSONArray respuesta = new JSONArray();
		if(participantes.size() == 0) {
			JSONObject msg = new JSONObject();
			msg.put("id", -1);
			respuesta.put(msg);
			return respuesta.toString();
		}
		
		for(Participante p: participantes) {
			JSONObject part = new JSONObject();
			part.put("id",p.getId());
			part.put("nombre", p.getNombre());
			part.put("observaciones", p.getObservaciones());
			JSONObject infoClase = new JSONObject();
			Clase c = p.getFk_id_clase();
			infoClase.put("id", c.getId());
			infoClase.put("descripcion", c.getDescripcion());
			infoClase.put("tipo", c.getTipo());
			part.put("clase",infoClase);
			respuesta.put(part);
		}	
		return respuesta.toString();
	}
	
	@GetMapping("/id")
	public String obtenParticipantePorId(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		Participante p = participante_bd.findById(id).get();
		JSONObject part = new JSONObject();
		if( p == null) {
			part.put("id", -1);
			return part.toString();
		}
		
		part.put("id",p.getId());
		part.put("nombre", p.getNombre());
		part.put("observaciones", p.getObservaciones());
		JSONObject infoClase = new JSONObject();
		Clase c = p.getFk_id_clase();
		infoClase.put("id", c.getId());
		infoClase.put("descripcion", c.getDescripcion());
		infoClase.put("tipo", c.getTipo());
		part.put("clase",infoClase);
		
		return part.toString();
	}
	
	@GetMapping("/nombre")
	public String obtenParticipantePorNombre(HttpServletRequest request) {
		
		return null;
	}
}
