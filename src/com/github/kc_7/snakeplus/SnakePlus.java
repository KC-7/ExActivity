package com.github.kc_7.snakeplus;

import java.awt.EventQueue;

import com.github.kc_7.activityplus.ActivityPlus;

public class SnakePlus extends ActivityPlus {

	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "Snake";
	
	static {
		
		activity = new SnakeActivity();
		launcher = new SnakeLauncher();
		
	}
	
	public SnakePlus(String title) {
		
		super(title);
		
	}
	
	public static void main(String[] args) {	
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {    
				
				new SnakePlus(TITLE);
				
			}	
			
		});
		
	}
	
}
