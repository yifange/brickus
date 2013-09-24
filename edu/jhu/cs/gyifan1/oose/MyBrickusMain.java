package edu.jhu.cs.gyifan1.oose;

import edu.jhu.cs.oose.fall2013.brickus.iface.BrickusModel;
import edu.jhu.cs.oose.fall2013.brickus.ui.StandardBrickusFrame;

public class MyBrickusMain {

	/**
	 * @param args	The arguments passed to main.
	 */
	public static void main(String[] args) {
     MyBrickusFrame gui = new MyBrickusFrame();
//     gui.setResizable(false);
     
     gui.setLocationRelativeTo(null);
     gui.setVisible(true);
	}
}
