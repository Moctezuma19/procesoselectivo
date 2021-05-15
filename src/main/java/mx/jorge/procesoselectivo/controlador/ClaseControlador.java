package mx.jorge.procesoselectivo.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
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

@RestController
@RequestMapping("/clase")
public class ClaseControlador {
	@Autowired
	private ParticipanteRepositorio participante_bd;
	@Autowired
	private ClaseRepositorio clase_bd;

	@GetMapping("/todos")
	public String obtenTodosClases(HttpServletRequest request) {
		LinkedList<Clase> clases = new LinkedList<>(clase_bd.findAll());
		JSONArray respuesta = new JSONArray();
		if (clases.size() == 0) {
			JSONObject msg = new JSONObject();
			msg.put("id", -1);
			respuesta.put(msg);
			return respuesta.toString();
		}

		for (Clase c : clases) {
			JSONObject part = new JSONObject();
			part.put("id", c.getId());
			part.put("descripcion", c.getDescripcion());
			part.put("tipo", c.getTipo());
			LinkedList<Participante> participantes = new LinkedList<>(c.getParticipantes());
			if (participantes.size() == 0) {
				respuesta.put(part);
				continue;
			}
			JSONArray sub = new JSONArray();
			for (Participante p : participantes) {
				JSONObject aux = new JSONObject();
				aux.put("id", p.getId());
				aux.put("nombre", p.getNombre());
				aux.put("correo", p.getCorreo());
				aux.put("observaciones", p.getObservaciones());
				sub.put(aux);
			}
			part.put("participantes", sub);
			respuesta.put(part);
		}
		return respuesta.toString();
	}

	@GetMapping(path = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenClasePorId(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		JSONObject part = new JSONObject();
		if (!clase_bd.existsById(id)) {
			part.put("id", -1);
			return part.toString();
		}
		Clase c = clase_bd.findById(id).get();

		part.put("id", c.getId());
		part.put("descripcion", c.getDescripcion());
		part.put("tipo", c.getTipo());

		LinkedList<Participante> participantes = new LinkedList<>(c.getParticipantes());
		if (participantes.size() == 0) {
			return part.toString();
		}
		JSONArray sub = new JSONArray();
		for (Participante p : participantes) {
			JSONObject aux = new JSONObject();
			aux.put("id", p.getId());
			aux.put("nombre", p.getNombre());
			aux.put("correo", p.getCorreo());
			aux.put("observaciones", p.getObservaciones());
			sub.put(aux);
		}
		part.put("participantes", sub);
		return part.toString();
	}

	@GetMapping("/nombre")
	public String obtenClasePorNombre(HttpServletRequest request) {
		LinkedList<Clase> clases = new LinkedList<>(clase_bd.findByName(request.getParameter("nombre")));
		JSONArray respuesta = new JSONArray();
		if (clases.size() == 0) {
			JSONObject msg = new JSONObject();
			msg.put("id", -1);
			respuesta.put(msg);
			return respuesta.toString();
		}

		for (Clase c : clases) {
			JSONObject part = new JSONObject();
			part.put("id", c.getId());
			part.put("descripcion", c.getDescripcion());
			part.put("tipo", c.getTipo());
			LinkedList<Participante> participantes = new LinkedList<>(c.getParticipantes());
			if (participantes.size() == 0) {
				respuesta.put(part);
				continue;
			}
			JSONArray sub = new JSONArray();
			for (Participante p : participantes) {
				JSONObject aux = new JSONObject();
				aux.put("id", p.getId());
				aux.put("nombre", p.getNombre());
				aux.put("correo", p.getCorreo());
				aux.put("observaciones", p.getObservaciones());
				sub.put(aux);
			}
			part.put("participantes", sub);
			respuesta.put(part);
		}
		return respuesta.toString();
	}

	@PutMapping(path = "/agrega", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String agregaClase(HttpServletRequest request) {
		try {
			InputStream body = request.getInputStream();
			byte[] bytes = body.readAllBytes();
			String string = new String(bytes, StandardCharsets.UTF_8);
			JSONObject json = new JSONObject(string);
			Clase c = new Clase();
			c.setDescripcion(json.getString("descripcion"));
			c.setTipo(json.getString("tipo"));
			Clase c2 = clase_bd.save(c);
			return "{\"id\": " + c2.getId() + "}";
		} catch (IOException e) {
			System.out.println("IOException: " + e);
			return "{\"id\":-1}";
		} catch (Exception f) {
			System.out.println("Exception: " + f);
			return "{\"id\":-1}";
		}

	}
	
	@DeleteMapping(path = "/elimina", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String eliminaClase(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		if(!clase_bd.existsById(id)) {
			return "{\"id\":-1}";
		}
		Clase c = clase_bd.findById(id).get();
		LinkedList<Participante> participantes = new LinkedList<>(c.getParticipantes());
		JSONArray p2 = new JSONArray();
		for(Participante p: participantes) {
			participante_bd.delete(p);
			p2.put(p.getId());
		}
		JSONObject respuesta = new JSONObject();
		respuesta.put("id", c.getId());
		
		respuesta.put("participantes", p2);
		//participante_bd.delete(p);
		return respuesta.toString();

	}

}
