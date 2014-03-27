package vc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CardsListPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private LinkedList<Card> cards;
	private JComboBox<String> jcbTypes;
	private JComboBox<Integer> jcbLevels;
	private JTextField jtQuantity;
	private JLabel jlblLvl;
	private JButton jbUse;
	private JButton jbRemove;
	private JButton jbClear;
	private JTable jtCardsTable;
	private DefaultTableModel dtmCardsTable;
	
	private Object[][] slimes;
	private Object[][] nCards;
	private Object[][] hnCards;
	private Object[][] rCards;
	private Object[][] mSlimes;
	
	public CardsListPanel(){
		setLayout(new BorderLayout());
		initializeAttr();
		JPanel jpNorth = new JPanel(new FlowLayout());
		JPanel jpSouth = new JPanel(new GridLayout(1,2,5,5));
		jpNorth.add(jtQuantity);
		jpNorth.add(jlblLvl);
		jpNorth.add(jcbLevels);
		jpNorth.add(jcbTypes);
		jpNorth.add(jbUse);
		
		jpSouth.add(jbRemove);
		jpSouth.add(jbClear);
		
		JScrollPane jsTable = new JScrollPane(jtCardsTable);
		jsTable.setPreferredSize(jtCardsTable.getPreferredSize());

		this.add(jpNorth, BorderLayout.NORTH);
		this.add(jsTable, BorderLayout.CENTER);
		this.add(jpSouth, BorderLayout.SOUTH);
	}
	
	private void initializeAttr() {
		cards = new LinkedList<Card>();
		jtQuantity = new JTextField();
		
		jlblLvl = new JLabel("กั Lv.");
		
		jcbTypes = new JComboBox<String>(new String[]{"N Card", "HN Card", "R Card", "Slime", "M.Slime"});
		jcbTypes.setSelectedItem("Slime");
		jcbTypes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkValidSelection();
				
			}
		});
		
		jcbLevels = new JComboBox<Integer>(Card.getLevelsArray(40));
		jcbLevels.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkValidSelection();
			}
		});
		
		Object[] titles = {"Level", "Type", "Exp"};
		Object[][] data = {};
		dtmCardsTable = new DefaultTableModel(data,titles){
			private static final long serialVersionUID = -1518928776476609615L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		
		jtCardsTable = new JTable(dtmCardsTable);
		jtCardsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jtCardsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtCardsTable.setRowSelectionAllowed(true);
		jtCardsTable.setFocusable(false);
		jtCardsTable.setShowVerticalLines(false);
		
		jtQuantity.setColumns(3);
		jtQuantity.setFont(new Font(jtQuantity.getFont().getName(), Font.PLAIN, 15));
		jtQuantity.setHorizontalAlignment(JTextField.RIGHT);
		jtQuantity.setDocument(new PlainDocument(){
			/**
			 * 
			 */
			private static final long serialVersionUID = -9014996937601801768L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

				if(getLength() + str.length() > 3){
					str = str.substring(0, 3 - getLength());
				}
				
				try{
					Integer.parseInt(str);
					super.insertString(offs, str, a);
				} catch (NumberFormatException e){
					
				}
			}
		});
		
		jtQuantity.setText("1");
		
		jbUse = new JButton("Use");
		jbUse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addCard();
				
			}
		});
		
		jbRemove = new JButton("Remove");
		jbRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeCard();
				
			}
		});
		
		jbClear = new JButton("Clear");
		jbClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cards.clear();
				refresh();
			}
		});
	}
	
	private void checkValidSelection(){
		int index = jcbTypes.getSelectedIndex();
		if((index == Card.N || index == Card.SLIME || index == Card.HN) && jcbLevels.getSelectedIndex() > 29){
			jcbLevels.setSelectedIndex(29);
		}	
	}
	
	private void refresh(){
		dtmCardsTable.getDataVector().removeAllElements();
		dtmCardsTable.fireTableDataChanged();
		for(int i = 0; i < cards.size(); i++){
			Card c = cards.get(i);
			String type = "";
			int exp = 0;
			switch(c.getType()){
				case Card.N: 
					type = "N Card";
					exp = Card.nExp(c.getLevel());
					break;
				case Card.HN:
					type = "HN Card";
					exp = Card.hnExp(c.getLevel());
					break;
				case Card.R:
					type = "R Card";
					exp = Card.rExp(c.getLevel());
					break;
				case Card.SLIME:
					type = "Slime";
					exp = Card.slimeExp(c.getLevel());
					break;
				case Card.METAL_SLIME:
					type = "Metel Slime";
					exp = Card.mslimeExp(c.getLevel());
					break;
			}
			dtmCardsTable.addRow(new Object[]{"Lv." + c.getLevel(), type, exp});
		}
	}
	
	public void addCard(){
		if(!jtQuantity.getText().isEmpty()){
			int q = Integer.parseInt(jtQuantity.getText());
			int lv = (int)jcbLevels.getSelectedItem();
			int t = jcbTypes.getSelectedIndex();
			
			while(q > 0){
				cards.addFirst(new Card(lv, t));
				q--;
			}
			refresh();
			jtQuantity.setText("1");
		}
	}
	
	public void removeCard(){
		int index = jtCardsTable.getSelectedRow();
		if(index > -1){
			cards.remove(index);
			refresh();
			if(jtCardsTable.getRowCount() > 0){
				while(index >= jtCardsTable.getRowCount()) index--;
				jtCardsTable.setRowSelectionInterval(index, index);
			}
		}
	}
	
	public LinkedList<Card> getCardList(){
		return cards;
	}
}
