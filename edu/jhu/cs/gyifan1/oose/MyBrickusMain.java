package edu.jhu.cs.gyifan1.oose;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.ui.StandardBrickusFrame;

public class MyBrickusMain {

	/**
	 * @param args	The arguments passed to main.
	 */
	public static void main(String[] args) {
		 BrickusModel model = new edu.jhu.cs.oose.fall2013.brickus.model.StandardBrickusModel();
     MyBrickusFrame gui = new MyBrickusFrame(model);
//     gui.setResizable(false);
     
     gui.setLocationRelativeTo(null);
     gui.setVisible(true);
	}
}
