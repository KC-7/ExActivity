import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import net.kc7.activityframe.Activity;

public class ExPanel extends Activity {

	private static final long serialVersionUID = 1L;

	private final static int ACTIVITY_WIDTH = 300;
	private final static int ACTIVITY_HEIGHT = 300;
	private final static int ACTIVITY_FRAMERATE = 100;
	private final static String PATH_IMG = "res/img/";

	private final static int PIXEL_COUNT = 900;
	private final static int x[] = new int[PIXEL_COUNT];
	private final static int y[] = new int[PIXEL_COUNT];

	private final int PIXEL_SIZE = 10;
	private final int PIXEL_RANDOM = 29;

	private int dots, apple_x, apple_y, direction;
	private Image apple, head, dot;

	public ExPanel() {
		super(ACTIVITY_WIDTH, ACTIVITY_HEIGHT, ACTIVITY_FRAMERATE);
		setBackground(Color.BLACK);
	}

	@Override
	protected void loadResources() {

		apple = new ImageIcon(PATH_IMG + "apple.png").getImage();
		head = new ImageIcon(PATH_IMG + "head.png").getImage();
		dot = new ImageIcon(PATH_IMG + "dot.png").getImage();
	}

	@Override
	protected void startActivity() {

		dots = 4;
		direction = 1;

		for (int z = 0; z < 1; z++) {
			x[z] = 150 - z * PIXEL_SIZE;
			y[z] = 150;
		}

		locateApple();

		super.startActivity();
	}

	@Override
	protected void processInfo() {

		checkApple();
		checkCollision();
		move();
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
		if (y[0] >= ACTIVITY_HEIGHT || x[0] >= ACTIVITY_WIDTH) {
			active = false;
		}

		// Left/up borders
		if (y[0] < 0 || x[0] < 0) {
			active = false;
		}

		// If inactive, stop the timer
		if(!active) {
			timer.stop();
		}
	}

	private void move() {

		for (int z = dots; z > 0; z--) {
			x[z] = x[(z - 1)];
			y[z] = y[(z - 1)];
		}

		// Left
		if (direction == 0) {
			x[0] -= PIXEL_SIZE;
		}

		// Right
		if (direction == 1) {
			x[0] += PIXEL_SIZE;
		}

		// Up
		if (direction == 2) {
			y[0] -= PIXEL_SIZE;
		}

		// Down
		if (direction == 3) {
			y[0] += PIXEL_SIZE;
		}
	}

	@Override
	protected void drawView(Graphics g) {

		g.drawImage(apple, apple_x, apple_y, this);
		g.setColor(Color.YELLOW);
		g.drawString("Score: " + dots, 5, 15);

		for (int z = 0; z < dots; z++) {
			if (z == 0) g.drawImage(head, x[z], y[z], this);
			else g.drawImage(dot, x[z], y[z], this);
		}

		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	protected void drawVoid(Graphics g) {

		Font regular = new Font("Helvetica", Font.PLAIN, 14);
		Font bold = new Font("Helvetica", Font.BOLD, 14);

		String msg = "Game Over";
		FontMetrics metr = getFontMetrics(bold);
		g.setColor(Color.WHITE);
		g.setFont(bold);
		g.drawString(msg, (ACTIVITY_WIDTH - metr.stringWidth(msg)) / 2, ACTIVITY_HEIGHT / 2);

		g.setColor(Color.YELLOW);
		g.setFont(regular);
		g.drawString("Score: " + dots, 5, 15);
	}

	@Override
	protected void handleKey(int key) {
		
		if ((key == UP) && (direction != 3)) {
			direction = 2;
		} else if ((key == DOWN) && (direction != 2)) {
			direction = 3;
		} else if ((key == LEFT) && (direction != 1)) {
			direction = 0;
		} else if ((key == RIGHT) && (direction != 0)) {
			direction = 1;
		}
	}

}
