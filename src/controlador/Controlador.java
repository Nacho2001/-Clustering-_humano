package controlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import logica.Clasificador;
import logica.Grafo;
import logica.Persona;
import logica.Punto;
import visual.Grafico;
import datos.GestorJSON;
import datos.PersonasJSON;

public class Controlador {

	static Clasificador clasificador = new Clasificador();

	public static void agregarPersona(String nombre, int musica, int deporte, int espectaculo, int ciencia, int arte) {
		clasificador.cargarPersona(nombre, musica, deporte, espectaculo, ciencia, arte);
		GestorJSON.agregarPersonas(new Persona(nombre, musica, deporte, espectaculo, ciencia, arte));

	}

	public static void cargarDatosEnClasificador(String nombreDelArchivo) {
		PersonasJSON personas = PersonasJSON.leerJSON(nombreDelArchivo);
		ArrayList<Persona> paraCargar = personas.getPersonas();
		for (Persona p : paraCargar) {
			clasificador.agregarPersonas(p);
		}
	}

	public static void cargarBase() {
		cargarDatosEnClasificador("PersonasPretty.JSON");
	}

	public static void cargarActual() {
		cargarDatosEnClasificador("usuarios.JSON");
	}

	public static void guardarPersona() {
		GestorJSON.guardarPersonas();
	}

	public static ArrayList<Set<Persona>> obtenerGrupos() {
		ArrayList<Set<Persona>> grupos = clasificador.agruparPersonas();
		return grupos;
	}

	public static int randomInt(int centro, int ancho) {
		return (int) (((Math.random() - 0.5) * ancho) + centro);
	}

	public static void graficarGrupos(Graphics g) {
		int centroX;
		int centroY = 300;
		int anchoX = 400;
		int anchoY = 200;
		int centroIzq = 350;
		int centroDer = 900;

		Grafo nuevo = clasificador.dividirGrafo();
		Set<Persona> grupo1 = obtenerGrupos().get(0);

		Font fuenteNombre = new Font("Sitka Banner", java.awt.Font.PLAIN, 30);
		g.setFont(fuenteNombre);
		g.setColor(Color.darkGray);

		ArrayList<Punto> puntos = new ArrayList<Punto>();
		Punto p;

		for (Persona persona : nuevo.vertices()) {

			centroX = grupo1.contains(persona) ? centroIzq : centroDer;

			p = obtenerPuntoValido(puntos, centroX, centroY, anchoX, anchoY);

			puntos.add(p);
			Grafico.agregarCirculo(p.getX(), p.getY(), g);
			g.drawString(persona.getNombre(), p.getX() - 10, p.getY() - 10);

		}

		for (int i = 0; i < nuevo.tamanio() - 1; i++) {
			for (int j = i + 1; j < nuevo.tamanio(); j++) {
				if (nuevo.existeArista(i, j)) {
					graficarArista(puntos.get(i), puntos.get(j), g);

				}
			}
		}
		g.drawString("Grupo 1: " + clasificador.getGrupo1().toString(), 700, 500);
		g.drawString("Grupo 2: " + clasificador.getGrupo2().toString(), 700, 550);
		g.drawString("Promedio similaridad grupo 1:  " + estadisticasGrupo1(), 700, 600);
		g.drawString("Promedio similaridad grupo 2:  " + estadisticasGrupo2(), 700, 650);

	}

	public static ArrayList<String> getDatosPersonas() {
		ArrayList<String> personasCargadas = PersonasJSON.leerJSON("usuarios.JSON").getPersonasString();
		ArrayList<String> personas = PersonasJSON.leerJSON("PersonasPretty.JSON").getPersonasString();
		for (String p : personasCargadas) {
			personas.add(p);
		}

		return personas;
	}

	public static DefaultListModel<String> recorrerDatosPersonas(ArrayList<String> datos) {
		DefaultListModel<String> listModel = new DefaultListModel<>();
		for (int i = 0; i < datos.size(); i++) {
			listModel.addElement(datos.get(i));
		}
		return listModel;

	}

	public static JList crearLista() {
		Font fuenteLista = new Font("Showcard Gothic", java.awt.Font.PLAIN, 20);
		Color foreGround = new java.awt.Color(250, 250, 210);
		JList list = new JList<>(recorrerDatosPersonas(getDatosPersonas()));
		list.setBounds(0, 0, 328, 521);

		list.setFont(fuenteLista);
		list.setBackground(foreGround);
		list.setBorder(new LineBorder(new Color(240, 230, 140), 2));

		return list;
	}

	public static void agregarLista(JPanel panel) {
		JList list = crearLista();
		panel.removeAll();
		panel.add(list);

	}

	public static boolean choca(Punto p, Punto p2) {
		return p.intersecan(p2);
	}

	public static boolean puedeAgregar(ArrayList<Punto> puntos, Punto punto) {
		if (puntos.size() == 0) {
			return true;
		}

		for (Punto p : puntos) {
			if (choca(punto, p)) {
				return false;
			}
		}
		return true;
	}

	public static Punto obtenerPuntoValido(ArrayList<Punto> puntos, int centroX, int centroY, int anchoX, int anchoY) {
		Punto p;
		p = new Punto(randomInt(centroX, anchoX), randomInt(centroY, anchoY));

		while (!puedeAgregar(puntos, p)) { // || p.getY() > 30 || p.getY() < 500
			p = new Punto(randomInt(centroX, anchoX), randomInt(centroY, anchoY));

		}
		return p;
	}

	public static void graficarArista(Punto p1, Punto p2, Graphics g) {

		Grafico.agregarLinea(p1.getX() + 20, p1.getY() + 20, p2.getX() + 20, p2.getY() + 20, g);
	}

	public static double estadisticasGrupo1() {
		ArrayList<Set<Persona>> grupo = obtenerGrupos();
		Set<Persona> grupo1 = grupo.get(0);
		Set<Persona> grupo2 = grupo.get(1);
		return promedioDeSimilaridad(grupo1);

	}

	public static double estadisticasGrupo2() {
		ArrayList<Set<Persona>> grupo = obtenerGrupos();
		Set<Persona> grupo1 = grupo.get(0);
		Set<Persona> grupo2 = grupo.get(1);
		return promedioDeSimilaridad(grupo2);

	}

	public static double promedioDeSimilaridad(Set<Persona> grup) {
		double prom = 0;
		for (Persona p : grup) {
			for (Persona otro : grup) {
				prom = prom + p.indiceDeSimilaridad(otro);
			}
		}
		return Math.floor(100 * (prom / grup.size()) / 2) / 100;
	}

}
