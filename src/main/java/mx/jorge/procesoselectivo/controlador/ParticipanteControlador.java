package mx.jorge.procesoselectivo.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.jorge.procesoselectivo.entidad.Clase;
import mx.jorge.procesoselectivo.entidad.Participante;
import mx.jorge.procesoselectivo.repositorio.ClaseRepositorio;
import mx.jorge.procesoselectivo.repositorio.ParticipanteRepositorio;

import org.json.JSONArray;
import org.json.JSONObject;

@RestController
@RequestMapping(value = "/participantes")
public class ParticipanteControlador {
	@Autowired
	private ParticipanteRepositorio participante_bd;
	@Autowired
	private ClaseRepositorio clase_bd;

	// method = {RequestMethod.POST,RequestMethod.GET}
	@GetMapping("/todos")
	public String obtenTodosParticipantes(HttpServletRequest request) {
		LinkedList<Participante> participantes = new LinkedList<>(participante_bd.findAll());
		JSONArray respuesta = new JSONArray();
		if (participantes.size() == 0) {
			JSONObject msg = new JSONObject();
			msg.put("id", -1);
			respuesta.put(msg);
			return respuesta.toString();
		}

		for (Participante p : participantes) {
			JSONObject part = new JSONObject();
			part.put("id", p.getId());
			part.put("nombre", p.getNombre());
			part.put("observaciones", p.getObservaciones());
			JSONObject infoClase = new JSONObject();
			Clase c = p.getFk_id_clase();
			infoClase.put("id", c.getId());
			infoClase.put("descripcion", c.getDescripcion());
			infoClase.put("tipo", c.getTipo());
			part.put("clase", infoClase);
			respuesta.put(part);
		}
		return respuesta.toString();
	}

	@GetMapping(path = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenParticipantePorId(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		JSONObject part = new JSONObject();
		if (!participante_bd.existsById(id)) {
			part.put("id", -1);
			return part.toString();
		}
		Participante p = participante_bd.findById(id).get();

		part.put("id", p.getId());
		part.put("nombre", p.getNombre());
		part.put("observaciones", p.getObservaciones());
		JSONObject infoClase = new JSONObject();
		Clase c = p.getFk_id_clase();
		infoClase.put("id", c.getId());
		infoClase.put("descripcion", c.getDescripcion());
		infoClase.put("tipo", c.getTipo());
		part.put("clase", infoClase);

		return part.toString();
	}

	@GetMapping(path = "/nombre", produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenParticipantePorNombre(HttpServletRequest request) {
		LinkedList<Participante> participantes = new LinkedList<>(
				participante_bd.findByName(request.getParameter("nombre")));
		JSONArray respuesta = new JSONArray();
		if (participantes.size() == 0) {
			JSONObject msg = new JSONObject();
			msg.put("id", -1);
			respuesta.put(msg);
			return respuesta.toString();
		}

		for (Participante p : participantes) {
			JSONObject part = new JSONObject();
			part.put("id", p.getId());
			part.put("nombre", p.getNombre());
			part.put("observaciones", p.getObservaciones());
			JSONObject infoClase = new JSONObject();
			Clase c = p.getFk_id_clase();
			infoClase.put("id", c.getId());
			infoClase.put("descripcion", c.getDescripcion());
			infoClase.put("tipo", c.getTipo());
			part.put("clase", infoClase);
			respuesta.put(part);
		}
		return respuesta.toString();
	}

	@PutMapping(path = "/agrega", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String agregaParticipante(HttpServletRequest request) {
		try {
			InputStream body = request.getInputStream();
			byte[] bytes = body.readAllBytes();
			String string = new String(bytes, StandardCharsets.UTF_8);
			JSONObject json = new JSONObject(string);
			Participante p = new Participante();
			p.setNombre(json.getString("nombre"));
			p.setCorreo(json.getString("correo"));
			p.setObservaciones(json.getString("observaciones"));
			Clase c = clase_bd.findById(json.getInt("clase")).get();

			JSONObject resp = new JSONObject();
			if (c == null) {
				resp.put("id", -1);
				return resp.toString();
			}
			p.setFk_id_clase(c);
			Participante p2 = participante_bd.save(p);
			resp.put("id", p2.getId());
			return resp.toString();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
			return "{\"id\":-1}";
		} catch (Exception f) {
			System.out.println("Exception: " + f);
			return "{\"id\":-1}";
		}

	}

	@DeleteMapping(path = "/agrega", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String eliminaParticipante(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		if(!participante_bd.existsById(id)) {
			return "{\"id\":-1}";
		}
		Participante p = participante_bd.findById(id).get();
		participante_bd.delete(p);
		return "{ \"id\":" + p.getId() + "}";

	}

}
