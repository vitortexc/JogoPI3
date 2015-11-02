package jogo;

import java.awt.*;

import javax.swing.JLabel;

public class HUD {
	private int recursos;
	private int wave;
	private int vidas;
	private String sRecursos;
	private String sWave;
	private String sVidas;
	private static HUD instancia;
	public JLabel labelWave;
	private boolean griding;
	private boolean gameOver;
	private Font font;
	
	private HUD() {
		wave = 1;
		recursos = 100;
		vidas = 20;
		font = new Font("Arial", Font.PLAIN, 25);	
		griding = true;	
	}
	
	public static HUD getInstancia() {
		if(instancia == null) {
			initInstancia();
		}
		return instancia;
	}
	
	private static synchronized void initInstancia() {
		if(instancia == null) {
			instancia = new HUD();
		}
	}
	
	public int getRecursos() {
		return recursos;
	}

	public void setRecursos(int recursos) {
		this.recursos = recursos;
	}
	
	public void addRecursos(int recursos) {
		this.recursos += recursos;
	}
	
	public void subRecursos(int recursos) {
		this.recursos -= recursos;
	}
	
	public void nullRecursos() {
		this.recursos = 0;
	}
	
	public int getWave() {
		return wave;
	}

	public void setWave(int wave) {
		this.wave = wave;
	}
	
	public void addWave() {
		wave++;
	}
	
	public int getVidas() {
		return vidas;
	}

	public void subVidas() {
		this.vidas--;
	}
	
	public void update() {
		sRecursos = "Recursos : " + String.format("%06d", recursos);
		sWave = "Wave : " + String.format("%03d", wave);
		sVidas = "Vidas : " + String.format("%02d", vidas);
	}
	
	public void draw(Graphics2D g) {
		if(griding) {
			g.setColor(Color.WHITE);
			for(int x = 50; x < 901; x += 50) {
				for(int y = 50; y < 601; y += 50) {
					g.drawLine(x, 50, x, 600);
					g.drawLine(50, y, 900, y);
				}
			}
		}
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(sRecursos, 5, font.getSize());
		g.drawString(sWave, 950/2 - font.getSize()*2, font.getSize());
		g.drawString(sVidas, 950 - ((sVidas.length()/2) * font.getSize()) - 5, font.getSize());		
	}
}
