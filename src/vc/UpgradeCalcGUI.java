package vc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class UpgradeCalcGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel jlFrom;
	private JLabel jlTo;
	private JComboBox<Integer> jcbFromLevel;
	private JComboBox<Integer> jcbToLevel;
	private JTextArea jtaResult;
	private JButton jbCalculate;
	private JButton jbAddCards;
	
	public UpgradeCalcGUI()  {
		setTitle("Upgrade Calculater");
		setLayout(new BorderLayout());
		setSize(350,300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel jpMain = new JPanel(new BorderLayout());
		
		initializeComponents();
		JPanel jpCenter = new JPanel(new BorderLayout());
		JPanel jpWest = new JPanel();
		jpWest.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpWest.setPreferredSize(new Dimension(135, (int)jpWest.getPreferredSize().getHeight()));
		
		
		JPanel jpFrom = new JPanel(new GridLayout(0,1));
		JPanel jpTo = new JPanel(new GridLayout(0,1));
		
		jpFrom.add(jlFrom);
		jpFrom.add(jcbFromLevel);
		jpTo.add(jlTo);
		jpTo.add(jcbToLevel);
		
		jpWest.add(jpFrom);
		jpWest.add(jpTo);
		jpWest.add(jbCalculate);
		jpWest.add(jbAddCards);
		
		
		jpCenter.add(jtaResult, BorderLayout.CENTER);
		
		jpMain.add(jpWest, BorderLayout.WEST);
		jpMain.add(jpCenter, BorderLayout.CENTER);
		
		getContentPane().add(jpMain, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	private void initializeComponents(){
	
		jlFrom = new JLabel("From level");
		jlFrom.setPreferredSize(new Dimension(90, jlFrom.getHeight()));
		jlTo = new JLabel("To level");
		jlTo.setPreferredSize(jlFrom.getPreferredSize());
		
		Integer[] lvls = new Integer[80];
		for(int i = 1; i <= lvls.length; i++){
			lvls[i-1] = i;
		}
		jcbFromLevel = new JComboBox<Integer>(lvls);
		jcbFromLevel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkValidSelection();
			}
		});
		jcbToLevel = new JComboBox<Integer>(lvls);
		jcbToLevel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkValidSelection();
			}
		});
		
		jtaResult = new JTextArea();
		jtaResult.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
		jtaResult.setMargin(new Insets(10,30,10,30));
		jtaResult.setBackground(Color.LIGHT_GRAY);
		
		jbCalculate = new JButton("Calculate");
		jbCalculate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calculate((int)jcbFromLevel.getSelectedItem(), (int)jcbToLevel.getSelectedItem());
				
			}
		});
		
		jbAddCards = new JButton("Cards to Use");
		jbAddCards.setVisible(false);
	}
	
	private void checkValidSelection(){
		if(jcbFromLevel.getSelectedIndex() > jcbToLevel.getSelectedIndex()){
			jcbToLevel.setSelectedIndex(jcbFromLevel.getSelectedIndex());
		}
	}

	private void calculate(int from, int to){
		int expNeeded = Card.getNeededExp(to) - Card.getNeededExp(from);
		int levelOfSlime = 30;
		
		int expOfSlime = Card.slimeExp(levelOfSlime);
		Card.slimeExp(30);
		int slimeNeeded = 0;
		int nCardNeeded = 0;
		
		int expCalc = expNeeded;
		
		while(expCalc > 0){
			expCalc -= 100;
			nCardNeeded++;
			
			if((nCardNeeded * 100) >= expOfSlime){
				slimeNeeded++;
				nCardNeeded = 0;
			}
		}
				
		jtaResult.setText("");
		jtaResult.append("Exp needed: " + expNeeded);
		jtaResult.append("\n\n");
		jtaResult.append(slimeNeeded + " [Lv.30 Slimes]\n");
		jtaResult.append(nCardNeeded + " [Lv.1 N Cards]\n");
		
		slimeNeeded = 0;
		expCalc = expNeeded;
		while(expCalc > 0){
			expCalc -= expOfSlime;
			slimeNeeded++;
		}
		jtaResult.append("\n     or\n\n");
		jtaResult.append(slimeNeeded + " [Lv.30 Slimes]\n");
	}
}
