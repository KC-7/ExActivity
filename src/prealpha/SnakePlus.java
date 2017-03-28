package prealpha;

import java.awt.EventQueue;

import prealpha.ActivityPlus;

public class SnakePlus {

	public static void main(String[] args) {	
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {    
				new ActivityPlus(new SnakeActivity(), new SnakeLauncher(), "Snake");
			}	
		});
	}
	
}
