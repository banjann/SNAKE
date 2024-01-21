package com.games.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener, KeyListener {

	private final int BOARD_WIDTH = 500;
	private final int BOARD_HEIGHT = 500;
	private final int DOT_SIZE = 20;

	private final String GAME_TITLE = "Suneku";

	private Unit food;
	private Unit snakeHead;

	private int x_direction = 0;
	private int y_direction = 0;

	private ArrayList<Unit> snakeBody = new ArrayList<Unit>();

	private Random random;
	private Timer timer;

	private boolean isGameover = false;

	class Unit {
		int x;
		int y;

		Unit(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public void playGame() {
		snakeHead = new Unit(380, 140);
		food = new Unit(0, 0);

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
		setBackground(new Color(117, 208, 154));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g) {
		g.setColor(new Color(180, 251, 209));
		for (int i = 1; i < BOARD_WIDTH / DOT_SIZE; i++) {
			g.drawLine(0, i * DOT_SIZE, BOARD_WIDTH, i * DOT_SIZE);
			g.drawLine(i * DOT_SIZE, 0, i * DOT_SIZE, BOARD_WIDTH);
		}

		g.setColor(new Color(239, 93, 76));
		g.fillRect(food.x, food.y, DOT_SIZE, DOT_SIZE);

		g.setColor(new Color(154, 117, 208));
		g.fillRect(snakeHead.x, snakeHead.y, DOT_SIZE, DOT_SIZE);

		for (Unit growth : snakeBody) {
			g.fillRect(growth.x, growth.y, DOT_SIZE, DOT_SIZE);
		}

		g.setColor(Color.black);
		g.drawString("Score: " + String.valueOf(snakeBody.size()), 8, 15);

		if (isGameover) {
			g.setColor(Color.red);
			g.drawString("GAME OVER!", 210, 210);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		checkGameover();
		whenEaten();
		moveBody();
		moveHead();
		repaint();
	}

	private void randomFood() {
		food.x = random.nextInt(BOARD_WIDTH / DOT_SIZE) * DOT_SIZE;
		food.y = random.nextInt(BOARD_WIDTH / DOT_SIZE) * DOT_SIZE;
	}

	private void moveBody() {
		for (int i = snakeBody.size() - 1; i >= 0; i--) {
			if (i == 0) {
				snakeBody.get(i).x = snakeHead.x;
				snakeBody.get(i).y = snakeHead.y;
			} else {
				snakeBody.get(i).x = snakeBody.get(i - 1).x;
				snakeBody.get(i).y = snakeBody.get(i - 1).y;
			}
		}
	}

	private void moveHead() {
		snakeHead.x += x_direction * DOT_SIZE;
		snakeHead.y += y_direction * DOT_SIZE;
	}

	private void whenEaten() {
		if (food.x == snakeHead.x && food.y == snakeHead.y) {
			growSnake();
			randomFood();
		}
	}

	private void checkGameover() {
		for (Unit unit : snakeBody) {
			if (unit.x == snakeHead.x && unit.y == snakeHead.y) {
				isGameover = true;
				timer.stop();
			}
		}

		if (snakeHead.x < 0 || snakeHead.x > BOARD_WIDTH || snakeHead.y < 0 || snakeHead.y > BOARD_HEIGHT) {
			isGameover = true;
			timer.stop();
		}
	}

	private void growSnake() {
		snakeBody.add(new Unit(food.x, food.y));
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
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
