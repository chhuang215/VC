package vc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

public class CardsListPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private LinkedList<Card> cards;
	private JComboBox<String> jcbTypes;
	private JComboBox<Integer> jcbLevels;
	private JTextField jtQuantity;
	private JLabel jlblLvl;
	private JButton jbAdd;
	private JButton jbRemove;
	private JButton jbRemoveAll;
	private JList<String> jlstCardsList;
	private DefaultListModel<String> dlmCardsList;
	
	public CardsListPanel(){
		setLayout(new BorderLayout());
		//setPreferredSize(new Dimension(130, this.getHeight()));;
		initializeAttr();
		JPanel jpNorth = new JPanel(new FlowLayout());
		JPanel jpSouth = new JPanel(new GridLayout(1,2,5,5));
		jpNorth.add(jtQuantity);
		jpNorth.add(jlblLvl);
		jpNorth.add(jcbLevels);
		jpNorth.add(jcbTypes);
		jpNorth.add(jbAdd);
		
		jpSouth.add(jbRemove);
		jpSouth.add(jbRemoveAll);
		
		this.add(jpNorth, BorderLayout.NORTH);
		this.add(new JScrollPane(jlstCardsList), BorderLayout.CENTER);
		this.add(jpSouth, BorderLayout.SOUTH);
	}
	private void initializeAttr() {
		cards = new LinkedList<Card>();
		jtQuantity = new JTextField();
		
		jlblLvl = new JLabel("x Lv.");
		
		jcbTypes = new JComboBox<String>(new String[]{"N", "HN", "R", "Slime", "M.Slime"});
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
		
		dlmCardsList = new DefaultListModel<String>();
		jlstCardsList = new JList<String>(dlmCardsList);
		jlstCardsList.setOpaque(false);
	
		jlstCardsList.setPrototypeCellValue("999 Lv.80 Metal Slime");
		jlstCardsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		jtQuantity.setColumns(3);
		jtQuantity.setFont(new Font(jtQuantity.getFont().getName(), Font.PLAIN, 15));
		jtQuantity.setHorizontalAlignment(JTextField.RIGHT);
		jtQuantity.setDocument(new PlainDocument(){
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
		
		jbAdd = new JButton("Add");
		jbAdd.addActionListener(new ActionListener() {
			
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
		
		jbRemoveAll = new JButton("Remove All");
		jbRemoveAll.addActionListener(new ActionListener() {
			
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
	
	public void refresh(){
		dlmCardsList.clear();
		for(int i = 0; i < cards.size(); i++){
			Card c = cards.get(i);
			String type = "";
			int exp = 0;
			switch(c.getType()){
				case Card.N: 
					type = "N";
					exp = Card.nExp(c.getLevel());
					break;
				case Card.HN:
					type = "HN";
					exp = Card.hnExp(c.getLevel());
					break;
				case Card.R:
					type = "R";
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
			dlmCardsList.addElement("Lv." + c.getLevel() + " " + type + " card         exp." + exp);
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
		int index = jlstCardsList.getSelectedIndex();
		if(index > -1){
			cards.remove(index);
			refresh();
		}
	}
	
	public LinkedList<Card> getCardList(){
		return cards;
	}
}
