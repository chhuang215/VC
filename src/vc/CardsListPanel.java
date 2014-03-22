package vc;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CardsListPanel extends JPanel {
	private ArrayList<Card> cards;
	private JComboBox<String> jcbTypes;
	private JComboBox<Integer> jcbLevels;
	private JTextField jtQuantity;
	public CardsListPanel(){
		setLayout(new BorderLayout());
		
		initializeAttr();
		
		
		
	}
	private void initializeAttr() {
		cards = new ArrayList<Card>();
		jtQuantity = new JTextField();
		jtQuantity.setText("0");
		
		
	}
}
