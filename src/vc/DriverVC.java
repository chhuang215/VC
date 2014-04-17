package vc;

import javax.swing.SwingUtilities;

public class DriverVC {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				UpgradeCalcGUI vc = new UpgradeCalcGUI();
				vc.setVisible(true);
			}
		});

	}

}
