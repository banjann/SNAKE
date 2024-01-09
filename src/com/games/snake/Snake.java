package com.games.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener, KeyListener {
	
	private final int BOARD_WIDTH = 500;
	private final int BOARD_HEIGHT = 500;
	private final int DOT_SIZE = 20;
	
	private final String GAME_TITLE = "Snake";
	
	private int x_food;
	private int y_food;
	private int x_snake = 380;
	private int y_snake = 140;
	private int x_direction = 0;
	private int y_direction = 0;
	private int foodEaten_x = 1;
	private int foodEaten_y = 1;
	
	private Random random;
	private Timer timer;
	
	public Snake() {
		
	}
	
	public void playGame( ) {

		createPanel();
		createWindow();

		addKeyListener(this);
		setFocusable(true);

		random = new Random();
		randomFood();

		timer = new Timer(100, this);
		timer.start();
	}
	
	private void createWindow() {

		JFrame window = new JFrame();
		window.setTitle(GAME_TITLE);
		window.setSize(BOARD_WIDTH, BOARD_HEIGHT);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.pack();
		window.setVisible(true);
	}
	
	private void createPanel() {

		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setBackground(Color.black);
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		draw(g);
	}
	
	private void draw(Graphics g) {

		for (int i=1; i<BOARD_WIDTH/DOT_SIZE; i++) {

			g.drawLine(0, i*DOT_SIZE, BOARD_WIDTH, i*DOT_SIZE);
			g.drawLine(i*DOT_SIZE, 0, i*DOT_SIZE, BOARD_WIDTH);
		}

		g.setColor(Color.red);
		g.fillRect(x_food, y_food, DOT_SIZE, DOT_SIZE);

		g.setColor(Color.green);
		g.fillRect(x_snake, y_snake, DOT_SIZE, DOT_SIZE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		updateSnakePos();
		checkEaten();
		repaint();
	}

	private void randomFood() {

		x_food = random.nextInt(BOARD_WIDTH/DOT_SIZE) * DOT_SIZE;
		y_food = random.nextInt(BOARD_WIDTH/DOT_SIZE) * DOT_SIZE;
	}

	private void updateSnakePos() {

		x_snake += x_direction * DOT_SIZE;
		y_snake += y_direction * DOT_SIZE;
	}
	
	private void checkEaten() {

		if (x_food == x_snake && y_food == y_snake) {
			
			randomFood();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		switch (key) {

			case KeyEvent.VK_UP:
				y_direction = (y_direction != 1) ? -1 : 1;
				x_direction = 0;
				break;

			case KeyEvent.VK_DOWN:
				y_direction = (y_direction != -1) ? 1 : -1;
				x_direction = 0;
				break;

			case KeyEvent.VK_LEFT:
				x_direction = (x_direction != 1) ? -1 : 1;
				y_direction = 0;
				break;

			case KeyEvent.VK_RIGHT:
				x_direction = (x_direction != -1) ? 1 : -1;
				y_direction = 0;
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
