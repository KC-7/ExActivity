package com.github.kc_7.snakeplus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

import com.github.kc_7.activityplus.Activity;

public class SnakeActivity extends Activity {

	private static final long serialVersionUID = 1L;
	
	private static final int PIXEL_COUNT = 900;
	private static final int DIRECTION_UP = 0, DIRECTION_DOWN = 1, DIRECTION_LEFT = 2, DIRECTION_RIGHT = 3;
	private static final int x[] = new int[PIXEL_COUNT], y[] = new int[PIXEL_COUNT];
	
	private final String PATH_IMG = "res/img/";
	private final Font standard = new Font("Serif", Font.PLAIN, 12);
	private final int PIXEL_SIZE = 10, PIXEL_RANDOM = 29, INITIAL_DOTS = 3;

	private int dots, apple_x, apple_y, direction;
	private Image apple, head, dot;
	
	static {
		PULSE_RATE = 125;
		PULSE_DELAY = 0;
		WIDTH = 300;
		HEIGHT = 300;
	}

	public SnakeActivity() {
		setBackground(Color.BLACK);
	}

	@Override
	protected void loadResources() {

		apple = new ImageIcon(PATH_IMG + "apple.png").getImage();
		head = new ImageIcon(PATH_IMG + "head.png").getImage();
		dot = new ImageIcon(PATH_IMG + "dot.png").getImage();
	}

	@Override
	protected void start() {

		dots = INITIAL_DOTS;
		direction = DIRECTION_RIGHT;

		for (int z = 0; z < 1; z++) {
			x[z] = 150 - z * PIXEL_SIZE;
			y[z] = 150;
		}

		locateApple();
	}

	@Override
	protected void pulseProcessor() {
		move();
		checkApple();
		checkCollision();
	}

	private void locateApple() {

		int r = (int) (Math.random() * PIXEL_RANDOM);
		apple_x = ((r * PIXEL_SIZE));

		r = (int) (Math.random() * PIXEL_RANDOM);
		apple_y = ((r * PIXEL_SIZE));	
	}

	private void checkApple() {

		if ((x[0] == apple_x) && (y[0] == apple_y)) {
			dots++;
			locateApple();
		}
	}

	private void checkCollision() {

		// Self-collision
		for (int z = dots; z > 0; z--) {
			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
				active = false;
			}
		}

		// Right/down borders
		if (y[0] >= HEIGHT || x[0] >= WIDTH) {
			active = false;
		}

		// Left/up borders
		if (y[0] < 0 || x[0] < 0) {
			active = false;
		}
	}

	private void move() {

		for (int z = dots; z > 0; z--) {
			x[z] = x[(z - 1)];
			y[z] = y[(z - 1)];
		}
		
		switch(direction) {
			case DIRECTION_LEFT:
				x[0] -= PIXEL_SIZE;
				break;
			case DIRECTION_RIGHT:
				x[0] += PIXEL_SIZE;
				break;
			case DIRECTION_UP:
				y[0] -= PIXEL_SIZE;
				break;
			case DIRECTION_DOWN:
				y[0] += PIXEL_SIZE;
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
	protected void handleKey(int key) {
		
		if ((key == KEY_UP) && (direction != DIRECTION_DOWN)) {
			
			direction = DIRECTION_UP;
			
		} else if ((key == KEY_DOWN) && (direction != DIRECTION_UP)) {
			
			direction = DIRECTION_DOWN;
			
		} else if ((key == KEY_LEFT) && (direction != DIRECTION_RIGHT)) {
			
			direction = DIRECTION_LEFT;
			
		} else if ((key == KEY_RIGHT) && (direction != DIRECTION_LEFT)) {
			
			direction = DIRECTION_RIGHT;
		}
	}

}
