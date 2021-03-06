package prealpha;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Set;

import prealpha.Launcher;

public class SnakeLauncher extends Launcher {

	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private static final Color COLOR = Color.BLACK;
	
	public SnakeLauncher() {
		super(WIDTH, HEIGHT, COLOR);
	}

	@Override
	protected void keyPress(Set<Integer> keys) {
		
		// If key list contains the space key, call Launcher's launch
		if (keys.contains(KeyEvent.VK_SPACE)) {
			launch();
		}
		
	}

	@Override
	protected void drawLauncher(Graphics g) {
		
		final FontMetrics metr = getFontMetrics(g.getFont());
		final String msg = "Snake -- Press SPACE to begin";

		g.setColor(Color.GREEN);
		g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
		
	}
	
	@Override
	protected void drawEnded(Graphics g) {
		
		final FontMetrics metr = getFontMetrics(g.getFont());
		final String msg = "Game Over -- Press SPACE to restart";
		
		g.setColor(Color.CYAN);
		g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
		
	}
	
}
