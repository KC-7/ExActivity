package com.github.kc_7.snakeplus;

import java.awt.EventQueue;

import com.github.kc_7.activityplus.ActivityPlus;
import com.github.kc_7.activityplus.Launcher;

public class SnakePlus extends ActivityPlus {

	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "Snake";
	private static final Launcher LAUNCHER = new SnakeLauncher();
	
	static {
		activity = new SnakeActivity();
	}
	
	public SnakePlus(Launcher launcher, String title) {
		super(launcher, title);
	}
	
	public static void main(String[] args) {	
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {             
				new SnakePlus(LAUNCHER, TITLE);
			}			
		});
	}
}
