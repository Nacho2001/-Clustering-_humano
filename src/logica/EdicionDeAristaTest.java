package logica;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdicionDeAristaTest {
	Grafo grafo;
	Persona p1;
	Persona p2;
	Persona p3;
	Persona p4;
	Persona p5;

	@Before
	public void setUp() throws Exception {
		grafo = new Grafo();
		p1 = new Persona("A", 1, 1, 1, 1, 1,null);
		p2 = new Persona("B", 1, 1, 1, 1, 1,null);
		p3 = new Persona("C", 0, 0, 0, 0, 0,null);
		p4 = new Persona("D", 1, 1, 1, 1, 1,null);
		p5 = new Persona("E", 5, 5, 5, 5, 5,null);
		grafo.agregarVertice(p1);
		grafo.agregarVertice(p2);
		grafo.agregarVertice(p3);
		grafo.agregarVertice(p4);
		grafo.agregarVertice(p5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeNegativoTest() {
		grafo.agregarArista(-1, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeExcedidoTest() {
		grafo.agregarArista(5, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeNegativoTest() {
		grafo.agregarArista(1, -3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeExcedidoTest() {
		grafo.agregarArista(1, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarLoopsTest() {
		grafo.agregarArista(1, 1);
	}

	@Test
	public void testExisteArista() {
		grafo.agregarArista(2, 3);
		assertTrue(grafo.existeArista(2, 3));
		assertFalse(grafo.existeArista(0, 1));
	}

	@Test
	public void testExisteAristaOpuesta() {
		grafo.agregarArista(2, 3);
		assertTrue(grafo.existeArista(3, 2));
	}

	@Test
	public void testAristaInexistente() {
		grafo.agregarArista(2, 3);
		assertFalse(grafo.existeArista(1, 3));
	}

	@Test
	public void eliminarAristaExistente() {
		grafo.agregarArista(2, 4);
		grafo.eliminarArista(2, 4);
		assertFalse(grafo.existeArista(2, 4));
	}

	@Test
	public void eliminarAristaInexistente() {
		grafo.eliminarArista(2, 4);
		assertFalse(grafo.existeArista(2, 4));
	}

	@Test
	public void eliminarAristaDosVeces() {
		grafo.agregarArista(2, 4);
		grafo.eliminarArista(2, 4);
		grafo.eliminarArista(2, 4);
		assertFalse(grafo.existeArista(2, 4));
	}

	@Test
	public void agregarAristaDosVeces() {
		grafo.agregarArista(2, 4);
		grafo.agregarArista(2, 4);
		assertTrue(grafo.existeArista(2, 4));
	}

	@Test
	public void eliminarAristaMasPesada() {
		grafo.completarGrafo();

		grafo.eliminarNodoMasPesado();
		assertFalse(grafo.existeArista(4, 2));
		assertTrue(grafo.existeArista(1, 2));
	}

}
