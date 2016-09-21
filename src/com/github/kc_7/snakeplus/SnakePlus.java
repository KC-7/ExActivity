package com.github.kc_7.snakeplus;
import java.awt.EventQueue;

import com.github.kc_7.activityplus.Activity;
import com.github.kc_7.activityplus.ActivityPlus;

public class SnakePlus extends ActivityPlus {

	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "SnakePlus";
	private static final Activity ACTIVITY = new SnakeActivity();
	
	public SnakePlus(Activity activity, String title) {
		super(activity, title);
	}
	
	public static void main(String[] args) {	
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {             
				new SnakePlus(ACTIVITY, TITLE);
			}			
		});
	}

}
