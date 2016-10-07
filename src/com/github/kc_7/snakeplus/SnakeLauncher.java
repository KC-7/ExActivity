package com.github.kc_7.snakeplus;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Set;

import com.github.kc_7.activityplus.Launcher;

public class SnakeLauncher extends Launcher {

	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private static final Color COLOR = Color.BLACK;
	
	public SnakeLauncher() {
		super(WIDTH, HEIGHT, COLOR);
	}

	@Override
	protected void handleKey(Set<Integer> pressedKeys) {
		
		if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
			super.launch();
		}
	}

	@Override
	protected void drawLauncher(Graphics g) {
		final FontMetrics metr = getFontMetrics(g.getFont());
		final String msg = "Snake";

		g.setColor(Color.GREEN);
		g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
	}
}
