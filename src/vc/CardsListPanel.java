package vc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class CardsListPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private LinkedList<LinkedList<Card>> cardDeck;
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
	
	@SuppressWarnings("serial")
	private void initializeAttr() {
		cardDeck = new LinkedList<LinkedList<Card>>();		
		
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
				
				int click = arg0.getModifiers();
				int index = jtCardsTable.rowAtPoint(arg0.getPoint());
				
				jtCardsTable.setRowSelectionInterval(index,index);
				
				if(click == MouseEvent.BUTTON3_MASK || (click == MouseEvent.BUTTON1_MASK && arg0.getClickCount() % 2 == 0)){
					int row = jtCardsTable.getSelectedRow();
					Object[] rowData = new Object[3];
					int cardLv = row + 1;
					
					for(int i = 0; i < 3; i++){
						rowData[i] = dtmCardsTable.getValueAt(row, i);
						
					}
					
					int type = Card.getTypeInteger((String)rowData[1]);
					
					addCard(cardLv,type);
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
				
				int click = e.getModifiers();
				int index = jtCardsTable.rowAtPoint(e.getPoint());
				jtSelectedTable.setRowSelectionInterval(index,index);
				
				if(click == MouseEvent.BUTTON3_MASK || (click == MouseEvent.BUTTON1_MASK && e.getClickCount() % 2 == 0))
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
				cardDeck.clear();
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
		
		for (int i = 0; i < cardDeck.size(); i++){
			LinkedList<Card> anCardDeck = cardDeck.get(i);
			Card c = anCardDeck.get(0);
			String type = "";
			int quantity = anCardDeck.size();
			
			type = Card.getTypeString(c.getType());
			
			dtmSelectedTable.addRow(new Object[]{"Lv. " + c.getLevel(), type, quantity});
		}
	}
	
	public void addCard(int lv, int type){
		
		if(!cardDeck.isEmpty()){
			for(int i = 0; i < cardDeck.size(); i++){
				LinkedList<Card> tempCardDeck = cardDeck.get(i);
				Card c = tempCardDeck.get(0);
				if(c.getLevel() == lv && c.getType() == type){
					tempCardDeck.addFirst(new Card(lv, type));
					refresh();
					return;
				}
			}
		}
		
		LinkedList<Card> newCardDeck = new LinkedList<Card>();
		newCardDeck.addFirst(new Card(lv, type));
		cardDeck.addFirst(newCardDeck);
		refresh();
		
		
	}
	
	public void removeCard(){
		int index = jtSelectedTable.getSelectedRow();
		if(index > -1){
			
			cardDeck.get(index).removeLast();
			
			if(cardDeck.get(index).isEmpty()){
				cardDeck.remove(index);
			}
			
			refresh();
			if(jtSelectedTable.getRowCount() > 0){
				while(index >= jtSelectedTable.getRowCount()) index--;
				jtSelectedTable.setRowSelectionInterval(index, index);
			}
		}
	}
	
	public LinkedList<LinkedList<Card>> getCardList(){
		return cardDeck;
	}
}
