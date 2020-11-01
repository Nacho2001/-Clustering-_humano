package visual;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.SwingConstants;

import controlador.CambiadorDeVentanas;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class VentanaInicial  {

	private JFrame frame;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public VentanaInicial() {
		//this.frame=frame;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Ventana Inicial");
		makeFrameFullSize (frame);
		
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setBounds(0, 0, 1285, 750);
		panelTitulo.setBorder(null);
		panelTitulo.setLayout(null);
		
		Font fuente = new Font("Showcard Gothic", java.awt.Font.PLAIN, 38); // para cambiar el tama�o de la letra es mejor hacerlo en la fuente !!
		createJLabel(panelTitulo, "Clustering Humano", Color.RED, fuente, 95, 81, 89, 27);
		 
		

		frame.getContentPane().add(panelTitulo);
		
		JButton btnNewButton = new JButton("Cargar Datos");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CambiadorDeVentanas.cambiarAVentanaDeCarga();
				
			}
		});
		
		
		btnNewButton.setBounds(438, 366, 138, 67);
		panelTitulo.add(btnNewButton);
		
		JButton btnClustering = new JButton("MATCH");
		btnClustering.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CambiadorDeVentanas.cambiarAVentanaGrafo();;
			}
		});
		
		btnClustering.setBounds(688, 366, 138, 67);
		panelTitulo.add(btnClustering);
		
		JPanel panelPersonasCargadas = new JPanel();
		panelPersonasCargadas.setBounds(43, 148, 328, 521);
		panelTitulo.add(panelPersonasCargadas);
		
		
		
	
		frame.setVisible(true);
		
		
	}
	
	protected void makeFrameFullSize(JFrame aFrame) {
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    aFrame.setSize(screenSize.width, screenSize.height);
	}
	
	public JLabel createJLabel(JPanel panel, String texto, Color color, Font fuente, int posX, int posY, int ancho,
			int alto) {
		JLabel titulo = new JLabel(texto);
		titulo.setForeground(color);
		titulo.setFont(fuente);
		titulo.setBounds(454, 30, 401, 94);
		panel.add(titulo);

		return titulo;
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
