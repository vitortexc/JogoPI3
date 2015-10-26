/**
 * Classe principal do jogo, onde fica a imagem a ser impressa na janela
 * 
 * Metodos:
 * 		Construtor - Inicializa a classe
 * 		AddNotify - Inicializa a thread
 * 		Init - Iniciliza o necessario para o funcionamento
 **/
package funcional;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.swing.*;

import GameStates.GameStateManager;

@SuppressWarnings("serial")
public class Renderer extends JPanel implements Runnable, Mouse, KeyListener {
	// Dimensoes
	public static int WIDTH = 950;
	public static int HEIGHT = 650;

	// Thread
	private Thread thread;
	private boolean running;
	
	// Controle de FPS pelo thread
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	// Imagem (NAO MEXER)
	private BufferedImage image;
	private Graphics2D g;

	// Gerenciador do estado do jogo
	private GameStateManager gsm;
	
	// Construtor
	public Renderer() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Define o tamanho da tela
		setFocusable(true);
		requestFocus();
		setLayout(null); // Define que a posi�ao dos objeto nao eh predefinida
	}

	//Inicializa Thread
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			thread.start();
		}
	}

	// Cria o formato de imagem para impressao na tela
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true; // Define que o programa esta rodando

		gsm = new GameStateManager(); // Inizializa o gerenciador
	}

	/** Runnable Overides **/
	// 
	public void run() {

		init();

		long start;
		long elapsed;
		long wait;

		// Game Loop
		while (running) {

			start = System.nanoTime();

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			if (wait < 0)
				wait = 5;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// Atualiza as informa�oes que ir�o para tela
	private void update() {
		gsm.update();
	}
	
	// Desenha as informa��es na tela
	private void draw() {
		gsm.draw(g);
	}

	// Mostra a imagem gerada pelo draw()
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}
	
	/** Listeners **/
	public void keyTyped(KeyEvent key) {
	}

	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}

	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	
	public void mouseClicked(MouseEvent e) {
		gsm.mouseClicked(e);
	}
}
