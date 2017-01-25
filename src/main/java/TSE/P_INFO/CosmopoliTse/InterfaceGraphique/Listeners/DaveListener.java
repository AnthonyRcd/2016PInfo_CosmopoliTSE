package TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Listeners;

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
import javax.swing.JTextField;

import TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Containers.InterfaceDave;
import TSE.P_INFO.CosmopoliTse.UsersStories.DaveGUI;

public class DaveListener implements ActionListener, FocusListener, MouseListener {

	InterfaceDave dave = new InterfaceDave();
	Vector<URI> links = new Vector<URI>();
	
	public InterfaceDave getDave() {
		return dave;
	}

	public void setDave(InterfaceDave dave) {
		this.dave = dave;
	}

	public Vector<URI> getLinks() {
		return links;
	}

	public void setLinks(Vector<URI> links) {
		this.links = links;
	}
	
	public DaveListener() {
		actionsDave();
	}

	//Addition of the listener on each button and on the list
	public void actionsDave(){
		
		dave.getSearch1().addActionListener(this);
		dave.getSearch2().addActionListener(this);
		dave.getSearch3().addActionListener(this);
		dave.getSubject().addFocusListener(this);
		dave.getAnswerList().addMouseListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton)
		{
			switch(((JButton) e.getSource()).getName())
			{
				case("story1"):
				{
					dave.getErrorArea().setText("");dave.getErrorArea().setVisible(false); links.clear();
					setLinks(DaveGUI.firstStoryGUI(dave.getSubject().getText(),dave.getAnswerList(), dave.getErrorArea()));
					break;
				}
				case("story2"):
				{
					dave.getErrorArea().setText("");dave.getErrorArea().setVisible(false); links.clear();
					try {
						links.add(DaveGUI.secondStoryGUI(dave.getSubject().getText(),dave.getAnswerList(), dave.getErrorArea()));
					} catch (URISyntaxException e1) {}
					break;
				}
				case("story3"):
				{
					dave.getErrorArea().setText("");dave.getErrorArea().setVisible(false); links.clear();
					try {
						links.add(DaveGUI.thirdStoryGUI(dave.getSubject().getText().split(";"), dave.getAnswerList(), dave.getErrorArea()));
					} catch (URISyntaxException e1) {}
					break;
				}
			}
		}

	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() instanceof JTextField)
		{
			JTextField source = (JTextField) e.getSource();
			source.setForeground(Color.BLACK);
			if(source.getText().equals("ex: java (Story1) ou java;sql;c#;javascript (Story3)"))
				source.setText("");
		}		
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int user = dave.getAnswerList().getSelectedIndex();
		try {
			if(user!=0) Desktop.getDesktop().browse(links.get(user-1));
        } catch (IOException ex) {
            System.err.println("It looks like there's a problem");
        }
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}


}
