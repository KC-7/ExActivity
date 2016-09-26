package com.github.kc_7.snakeplus;

import java.awt.Color;
import java.awt.Font;
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

	private static final int DOT_COUNT = 900;
	private static final int DIRECTION_UP = 0, DIRECTION_DOWN = 1, DIRECTION_LEFT = 2, DIRECTION_RIGHT = 3;
	private static final int x[] = new int[DOT_COUNT], y[] = new int[DOT_COUNT];

	private final String PATH_IMG = "res/img/";
	private final Font standard = new Font("Serif", Font.PLAIN, 12);
	private final int DOT_SIZE = 10, DOT_RANDOM = 29, INITIAL_DOTS = 3;

	private int dots, apple_x, apple_y, direction;
	private Image apple, head, dot;

	static {
		PULSE_RATE = 125;
		PULSE_DELAY = 1000;
		WIDTH = 300;
		HEIGHT = 300;
		COLOR = Color.BLACK;
	}

	@Override
	protected void load() {

		apple = new ImageIcon(PATH_IMG + "apple.png").getImage();
		head = new ImageIcon(PATH_IMG + "head.png").getImage();
		dot = new ImageIcon(PATH_IMG + "dot.png").getImage();
	}

	@Override
	protected void start() {

		dots = INITIAL_DOTS;
		double r = Math.random();
		if (r < 0.5) {
			direction = DIRECTION_RIGHT;
		} else {
			direction = DIRECTION_LEFT;
		}
		
		for (int z = 0; z < 1; z++) {
			x[z] = 150 - z * DOT_SIZE;
			y[z] = 150;
		}
		
		locateApple();
	}
	
	@Override
	protected void stop() {
		return;
	}

	@Override
	protected void pulseProcessor() {
		
		checkApple(false);
		checkCollision();
		move();
	}

	private void locateApple() {

		int r = (int) (Math.random() * DOT_RANDOM);
		apple_x = ((r * DOT_SIZE));

		r = (int) (Math.random() * DOT_RANDOM);
		apple_y = ((r * DOT_SIZE));	
	}

	// Checks if the snake has hit apple or if overridden through parameter
	private void checkApple(boolean b) {
		boolean snakeApple = (x[0] == apple_x) && (y[0] == apple_y);
		if (b || snakeApple) {
			dots++;
			if (snakeApple) {
				locateApple();
			}
		}
	}

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

		g.drawImage(apple, apple_x, apple_y, this);
		g.setColor(Color.YELLOW);
		g.drawString("Score: " + dots, 5, 15);

		for (int z = 0; z < dots; z++) {
			if (z == 0) {
				g.drawImage(head, x[z], y[z], this);
			} else {
				g.drawImage(dot, x[z], y[z], this);
			}
		}

		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	protected void drawInactive(Graphics g) {

		String msg = "Game Over";
		FontMetrics metr = getFontMetrics(standard);
		g.setColor(Color.WHITE);
		g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
	}

	@Override
	protected void handleKey(Set<Integer> pressedKeys) {
		
		if (active) {
			
			keyDirection(pressedKeys);
			
			if (keyDebug(pressedKeys)) {
				keyScore(pressedKeys);
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
		
		if (pressedKeys.contains(KeyEvent.VK_SPACE) && !active) {
			
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

}
