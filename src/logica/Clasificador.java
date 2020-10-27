package logica;

import java.util.ArrayList;

public class Clasificador {
	Grafo grafo;
	ArrayList <Persona> personas = new ArrayList <Persona> ();
	
	//Agregamos a la persona a la lista de personas
	public void cargarPersonas (Persona persona) {
		if (!personas.contains(persona)) {
			grafo.agregarVertice(persona);	
		}
	}
	
	public void agregarPersonasAlGrafo() {
		grafo = new Grafo(personas.size());
		for (int i = 0; i < personas.size() - 1; i++) { // i termina en el ante ultimo
			for (int j = i + 1; j < personas.size(); j++) { // j termina en el ultimo
				grafo.agregarArista(i, j);
			}
		}

	}
	
	
}

