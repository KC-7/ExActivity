package com.github.kc_7.snakeplus;

import java.awt.EventQueue;

import com.github.kc_7.activityplus.Activity;
import com.github.kc_7.activityplus.ActivityPlus;
import com.github.kc_7.activityplus.Launcher;

public class SnakePlus extends ActivityPlus {

	private static final long serialVersionUID = 1L;
	
	public SnakePlus(Activity activity, Launcher launcher, String title) {
		
		super(activity, launcher, title);
		
	}
	
	public static void main(String[] args) {	
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {    
				
				new SnakePlus(new SnakeActivity(), new SnakeLauncher(),"Snake");
				
			}	
			
		});
		
	}
	
}
