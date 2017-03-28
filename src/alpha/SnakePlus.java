package alpha;

import java.awt.Dimension;
import java.awt.EventQueue;

public class SnakePlus {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> snake());
	}
	
	private static void snake() {
		final Application snakeApp = new Application(new Dimension(300, 300));
		snakeApp.setTitle("Snake");
		final Activity snakeActivity = new SnakeActivity(snakeApp);
		snakeActivity.setRate(SnakeActivity.RATE);
		snakeApp.pushActivity(snakeActivity);
	}

}