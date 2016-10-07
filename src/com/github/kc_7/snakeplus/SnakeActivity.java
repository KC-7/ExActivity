package com.github.kc_7.snakeplus;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Set;

import javax.swing.ImageIcon;

import com.github.kc_7.activityplus.Activity;

public class SnakeActivity extends Activity {

	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private static final int PULSE_RATE = 100;
	private static final Color COLOR = Color.BLACK;
	
	private static final int DOT_COUNT = 900;
	private static final int x[] = new int[DOT_COUNT];
	private static final int y[] = new int[DOT_COUNT];	

	private final String PATH_IMG = "res/img/";
	private final int DIRECTION_UP = 0;
	private final int DIRECTION_DOWN = 1;
	private final int DIRECTION_LEFT = 2; 
	private final int DIRECTION_RIGHT = 3;
	private final int DOT_SIZE = 10;
	private final int INITIAL_DOTS = 4;

	private int dots, apple_x, apple_y, direction;
	private Image apple, head, dot;

	public SnakeActivity() {
		super(WIDTH, HEIGHT, PULSE_RATE, COLOR);
	}

	@Override
	protected void load() {
		apple = new ImageIcon(PATH_IMG + "apple.png").getImage();
		head = new ImageIcon(PATH_IMG + "head.png").getImage();
		dot = new ImageIcon(PATH_IMG + "dot.png").getImage();
	}

	@Override
	protected void start() {
		
		locateApple();
		dots = INITIAL_DOTS;
		
		if (Math.random() < 0.5) {
			direction = DIRECTION_RIGHT;
		} else {
			direction = DIRECTION_LEFT;
		}

		for (int z = 0; z < 1; z++) {
			x[z] = 150 - z * DOT_SIZE;
			y[z] = 150;
		}	
	}

	@Override
	protected void stop() {
		// TODO Write data
		return;
	}

	@Override
	protected void pulseProcessor() {
		checkApple(false);
		checkCollision();
		move();
	}
	
	// Locates apples to random coords, rounded down to the dot size
	private void locateApple() {
		apple_x = ((int)(Math.random() * WIDTH) / DOT_SIZE) * DOT_SIZE;
		apple_y = ((int)(Math.random() * HEIGHT) / DOT_SIZE) * DOT_SIZE;
	}

	// Checks if the snake has hit apple or if overridden through parameter
	private void checkApple(boolean override) {

		final boolean snakeTouchingApple = (x[0] == apple_x) && (y[0] == apple_y);

		if (override || snakeTouchingApple) {
			dots++;

			if (snakeTouchingApple) {
				locateApple();
			}
		}
	}
	
	// Checks snake collision with self or borders and deactivates
	private void checkCollision() {

		boolean collision =  false;

		// Self-collision
		for (int z = dots; z > 0; z--) {
			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
				collision = true;
			}
		}

		// Right/down borders
		if (y[0] >= HEIGHT || x[0] >= WIDTH) {
			collision = true;
		}

		// Left/up borders
		if (y[0] < 0 || x[0] < 0) {
			collision = true;
		}

		if (collision) {
			deactivate();
		}
	}
	
	// Moves body and head
	private void move() {

		for (int z = dots; z > 0; z--) {
			x[z] = x[(z - 1)];
			y[z] = y[(z - 1)];
		}

		switch(direction) {
		case DIRECTION_LEFT:
			x[0] -= DOT_SIZE;
			break;
		case DIRECTION_RIGHT:
			x[0] += DOT_SIZE;
			break;
		case DIRECTION_UP:
			y[0] -= DOT_SIZE;
			break;
		case DIRECTION_DOWN:
			y[0] += DOT_SIZE;
			break;
		}
	}

	@Override
	protected void drawActive(Graphics g) {

		for (int z = 0; z < dots; z++) {
			if (z == 0) {
				g.drawImage(head, x[z], y[z], this);
			} else {
				g.drawImage(dot, x[z], y[z], this);
			}
		}

		g.drawImage(apple, apple_x, apple_y, this);
		g.setColor(Color.CYAN);
		g.drawString("Score: " + dots, 5, 15);

		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	protected void drawInactive(Graphics g) {

		final FontMetrics metr = getFontMetrics(g.getFont());
		final String msg = "Game Over";
		
		g.setColor(Color.CYAN);
		g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
	}

	@Override
	protected void handleKey(Set<Integer> pressedKeys) {

		if (active) {

			keyDirection(pressedKeys);

			if (keyDebug(pressedKeys)) {

				keyScore(pressedKeys);
				keyApple(pressedKeys);
			}

		} else {

			keyRestart(pressedKeys);	
		}
	}

	private void keyDirection(Set<Integer> pressedKeys) {

		if (pressedKeys.contains(KeyEvent.VK_UP) && direction != DIRECTION_DOWN) {
			direction = DIRECTION_UP;

		} else if (pressedKeys.contains(KeyEvent.VK_DOWN) && direction != DIRECTION_UP) {
			direction = DIRECTION_DOWN;

		} else if (pressedKeys.contains(KeyEvent.VK_LEFT) && direction != DIRECTION_RIGHT) {
			direction = DIRECTION_LEFT;

		} else if (pressedKeys.contains(KeyEvent.VK_RIGHT) && direction != DIRECTION_LEFT) {
			direction = DIRECTION_RIGHT;
		}
	}

	private void keyRestart(Set<Integer> pressedKeys) {	
		if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
			activate();
		}
	}

	private boolean keyDebug(Set<Integer> pressedKeys) {
		return pressedKeys.contains(KeyEvent.VK_SHIFT);
	}

	private void keyScore(Set<Integer> pressedKeys) {
		if (pressedKeys.contains(KeyEvent.VK_A)) {
			checkApple(true);
		}
	}

	private void keyApple(Set<Integer> pressedKeys) {
		if (pressedKeys.contains(KeyEvent.VK_S)) {
			locateApple();
		}
	}
}
