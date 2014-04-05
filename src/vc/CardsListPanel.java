package vc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private JButton jbRemove;
	private JButton jbClear;
	
	private JTable jtCardsTable;
	private JTable jtSelectedTable;
	private DefaultTableModel dtmCardsTable;
	private DefaultTableModel dtmSelectedTable;
	
	
	public CardsListPanel(){
		setLayout(new BorderLayout());
		initializeAttr();
		
		JPanel jpNorth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel jpSouth = new JPanel(new GridLayout(1,2,5,5));
		
		jpNorth.add(jcbTypes);
		//jpSouth.add(jbRemove);
		jpSouth.add(jbClear);
		
		JScrollPane jsTable = new JScrollPane(jtCardsTable);
		jsTable.setPreferredSize(jtCardsTable.getPreferredSize());

		JScrollPane jsSelectedTable = new JScrollPane(jtSelectedTable);
		jsSelectedTable.setPreferredSize(jtSelectedTable.getPreferredSize());
		
		this.add(jpNorth, BorderLayout.NORTH);
		this.add(jsTable, BorderLayout.CENTER);
		this.add(jsSelectedTable,BorderLayout.WEST);
		this.add(jpSouth, BorderLayout.SOUTH);
		
	}
	
	private void initializeAttr() {
		cards = new LinkedList<Card>();
				
		jcbTypes = new JComboBox<String>(new String[]{"N Card", "HN Card", "R Card", "Slime", "M.Slime"});
		jcbTypes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				showCards(jcbTypes.getSelectedIndex());

			}
		});
		
		jcbTypes.setPreferredSize(new Dimension(160, (int)jcbTypes.getPreferredSize().getHeight()));
		
		/***********CardsTable***********/
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
		jtCardsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked (MouseEvent arg0) {
				if(arg0.getClickCount() > 1){
					int row = jtCardsTable.getSelectedRow();
					Object[] rowData = new Object[3];
					for(int i = 0; i < 3; i++){
						rowData[i] = dtmCardsTable.getValueAt(row, i);
						
					}
					
					//dtmSelectedTable.addRow(rowData);
					addCard(row+1,(String)rowData[1]);
					
				}
			}
		});
		jtCardsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jtCardsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtCardsTable.setRowSelectionAllowed(true);
		jtCardsTable.setFocusable(false);
		jtCardsTable.setShowVerticalLines(false);
		
		
		Object[] titles1 = {"Level", "Type", "Quantity"};
		Object[][] data1 = {};
		dtmSelectedTable = new DefaultTableModel(data1,titles1){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		
		jtSelectedTable = new JTable(dtmSelectedTable);
		jtSelectedTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
					removeCard();
			}
		});
		jtSelectedTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jtSelectedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtSelectedTable.setRowSelectionAllowed(true);
		jtSelectedTable.setFocusable(false);
		jtSelectedTable.setShowVerticalLines(false);
		
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
		
		
		jcbTypes.setSelectedItem("Slime");
	}
	
	
	private void showCards(int type){
		dtmCardsTable.getDataVector().removeAllElements();
		dtmCardsTable.fireTableDataChanged();
		if(type == Card.SLIME){
			for(int i = 0; i < 30; i++){
				int lv = i+1;
				dtmCardsTable.addRow(new Object[]{"Lv." + lv, "Slime", Card.slimeExp(lv)});
			}
		}
		else if (type == Card.N){
			for(int i = 0; i < 30; i++){
				int lv = i+1;
				dtmCardsTable.addRow(new Object[]{"Lv." + lv, "N Card", Card.nExp(lv)});
			}
		}
		else if (type == Card.HN){
			for(int i = 0; i < 40; i++){
				int lv = i+1;
				dtmCardsTable.addRow(new Object[]{"Lv." + lv, "HN Card", Card.hnExp(lv)});
			}
		}
		else if (type == Card.R){
			for(int i = 0; i < 40; i++){
				int lv = i+1;
				dtmCardsTable.addRow(new Object[]{"Lv." + lv, "R Card", Card.rExp(lv)});
			}
		}
		else if (type == Card.METAL_SLIME){
			for(int i = 0; i < 40; i++){
				int lv = i+1;
				dtmCardsTable.addRow(new Object[]{"Lv." + lv, "M Slime", Card.mslimeExp(lv)});
			}
		}
	}
	
	private void refresh(){
		dtmSelectedTable.getDataVector().removeAllElements();
		dtmSelectedTable.fireTableDataChanged();
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
			dtmSelectedTable.addRow(new Object[]{"Lv." + c.getLevel(), type, exp});
		}
	}
	
	public void addCard(int lv, String type){
		
		
		cards.addFirst(new Card(lv, Card.getType(type)));
		
		refresh();
		
		
	}
	
	public void removeCard(){
		int index = jtSelectedTable.getSelectedRow();
		if(index > -1){
			cards.remove(index);
			refresh();
			if(jtSelectedTable.getRowCount() > 0){
				while(index >= jtSelectedTable.getRowCount()) index--;
				jtSelectedTable.setRowSelectionInterval(index, index);
			}
		}
	}
	
	public LinkedList<Card> getCardList(){
		return cards;
	}
}
