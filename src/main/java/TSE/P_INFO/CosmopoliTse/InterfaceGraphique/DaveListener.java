package TSE.P_INFO.CosmopoliTse.InterfaceGraphique;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.swing.JButton;

import TSE.P_INFO.CosmopoliTse.UsersStories.DaveGUI;

public class DaveListener implements ActionListener, FocusListener, MouseListener{

	InterfaceDave dave = new InterfaceDave();
	Vector<URI> links = new Vector<URI>();
	
	public DaveListener() {
		actionsDave();
	}

	//Addition of the listener on each button
	public void actionsDave(){
		
		dave.search1.addActionListener(this);
		dave.search2.addActionListener(this);
		dave.search3.addActionListener(this);
		dave.subject.addFocusListener(this);
		dave.answerList.addMouseListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton)
		{
			switch(((JButton) e.getSource()).getName())
			{
				case("story1"):
				{
					dave.errorArea.setText("");dave.errorArea.setVisible(false); links.clear();
					links=DaveGUI.firstStoryGUI(dave.subject.getText(),dave.answerList, dave.errorArea);
					break;
				}
				case("story2"):
				{
					dave.errorArea.setText("");dave.errorArea.setVisible(false); links.clear();
					try {
						links.add(DaveGUI.secondStoryGUI(dave.subject.getText(),dave.answerList, dave.errorArea));
					} catch (URISyntaxException e1) {}
					break;
				}
				case("story3"):
				{
					dave.errorArea.setText("");dave.errorArea.setVisible(false); links.clear();
					try {
						links.add(DaveGUI.thirdStoryGUI(dave.subject.getText().split(";"), dave.answerList, dave.errorArea));
					} catch (URISyntaxException e1) {}
					break;
				}
			}
		}

	}

	@Override
	public void focusGained(FocusEvent e) {
		dave.subject.setForeground(Color.BLACK);
		if(dave.subject.getText().equals("ex: java (Story1) ou java;sql;c#;javascript (Story3)"))
			dave.subject.setText("");
		
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int user = dave.answerList.getSelectedIndex();
		try {
			if(user!=0) Desktop.getDesktop().browse(links.get(user-1));
        } catch (IOException ex) {
            System.out.println("It looks like there's a problem");
        }
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
