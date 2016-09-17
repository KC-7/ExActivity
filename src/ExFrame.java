import java.awt.EventQueue;

import net.kc7.activityframe.Activity;
import net.kc7.activityframe.Frame;

public class ExFrame extends Frame {

	private static final long serialVersionUID = 1L;
	
	private static final String title = "ExFrame";
	private static final Activity activity = new ExPanel();
	
	public ExFrame(Activity activity, String title) {
		super(activity, title);
	}
	
	public static void main(String[] args) {	
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {             
				new ExFrame(activity, title);
			}			
		});
	}

}
