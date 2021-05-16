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
import org.springframework.web.bind.annotation.PostMapping;
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
	/**
	 * Metodo para obtener datos de todas las clases y sus participantes
	 * @return un JSON con los datos requeridos
	 */
	@GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
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
	/**
	 * Metodo que recibe una peticion para obtener datos de una clase dado una id
	 * @return un JSON con los datos relacionados
	 */
	@GetMapping(path = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
	public String obtenClasePorId(HttpServletRequest request) {
		int id = -1;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {

		}
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
	/**
	 * Metodo que obtiene datos de una o varias clase dado un nombre
	 * @return un JSON con los datos relacionados
	 */
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
	
	/**
	 * Metodo que agrega una clase
	 * @return un JSON con el id de la clase agregada
	 */
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
	/**
	 * Metodo que elimina una clase y sus participantes relacionados dado un id
	 * @return un JSON con el id de la clase eliminada y los id's de los participantes eliminados
	 */
	@DeleteMapping(path = "/elimina", produces = MediaType.APPLICATION_JSON_VALUE)
	public String eliminaClase(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		if (!clase_bd.existsById(id)) {
			return "{\"id\":-1}";
		}
		Clase c = clase_bd.findById(id).get();
		LinkedList<Participante> participantes = new LinkedList<>(c.getParticipantes());
		JSONArray p2 = new JSONArray();
		for (Participante p : participantes) {
			participante_bd.delete(p);
			p2.put(p.getId());
		}
		clase_bd.delete(c);
		JSONObject respuesta = new JSONObject();
		respuesta.put("id", c.getId());
		respuesta.put("participantes", p2);
		// participante_bd.delete(p);
		return respuesta.toString();

	}

	/**
	 * Metodo que edita una clase
	 * 
	 * @return un JSON con el id del participante agregado
	 */
	@PostMapping(path = "/edita", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String postClase(HttpServletRequest request) {
		try {
			InputStream body = request.getInputStream();
			byte[] bytes = body.readAllBytes();
			String string = new String(bytes, StandardCharsets.UTF_8);
			JSONObject json = new JSONObject(string);

			int id = -1;
			if(!json.has("id")) {
				return "{\"id\":-1}";
			}
			id = json.getInt("id");
			if(!clase_bd.existsById(id)) {
				return "{\"id\":-1}";
			}			
			Clase editar = clase_bd.findById(id).get();
			if(json.has("descripcion")) {
				editar.setDescripcion(json.getString("descripcion"));
			}
			if(json.has("tipo")) {
				editar.setTipo(json.getString("tipo"));
			}

			JSONObject resp = new JSONObject();
			resp.put("id",editar.getId());
			clase_bd.save(editar);
			return resp.toString();

		} catch (IOException e) {
			System.out.println("IOException: " + e);
			return "{\"id\":-1}";
		} catch (Exception f) {
			System.out.println("Exception: " + f);
			return "{\"id\":-1}";
		}

	}

}
