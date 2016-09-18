import java.awt.Image;
import javax.swing.ImageIcon;

import net.kc7.activityframe.Activity;

public class ExPanel extends Activity {

	private static final long serialVersionUID = 1L;
	
	private final static int ACTIVITY_WIDTH = 300;
	private final static int ACTIVITY_HEIGHT = 300;
	private final static int ACTIVITY_FRAMERATE = 1000;
	
	private final int PIXEL_SIZE = 10;
	private final int PIXEL_COUNT = 900;
	private final int PIXEL_RANDOM = 29;
	private final int x[] = new int[PIXEL_COUNT];
	private final int y[] = new int[PIXEL_COUNT];
	
	private int dots, apple_x, apple_y;
	
	private final static String PATH_IMG = "res/img/";
	
	public ExPanel() {
		super(ACTIVITY_WIDTH, ACTIVITY_HEIGHT, ACTIVITY_FRAMERATE);
	}
	
	@Override
	protected boolean loadResources() {
		Image apple = new ImageIcon(PATH_IMG + "apple.png").getImage();
		Image head = new ImageIcon(PATH_IMG + "head.png").getImage();
		Image dot = new ImageIcon(PATH_IMG + "dot.png").getImage();
		
		return super.loadResources();
	}

	@Override
	protected boolean startActivity() {
		
		dots = 3;
		for (int z = 0; z < dots; z++) {
			x[z] = 50 - z * PIXEL_SIZE;
			y[z] = 50;
		}
		locateApple();
		
		return super.startActivity();
	}

	private void locateApple() {
		
		int r = (int) (Math.random() * PIXEL_RANDOM);
		apple_x = ((r * PIXEL_SIZE));

		r = (int) (Math.random() * PIXEL_RANDOM);
		apple_y = ((r * PIXEL_SIZE));	
		
	}
	
}
