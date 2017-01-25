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
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;

import TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Containers.InterfaceBob;
import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods;
import TSE.P_INFO.CosmopoliTse.UsefulMethods.Methods.BadIdException;
import TSE.P_INFO.CosmopoliTse.UsersStories.BobGUI;

public class BobListener implements ActionListener, MouseListener, FocusListener{

	InterfaceBob bob = new InterfaceBob();
	Vector<URI> links = new Vector<URI>();
	
	public InterfaceBob getBob() {
		return bob;
	}

	public void setBob(InterfaceBob bob) {
		this.bob = bob;
	}

	public Vector<URI> getLinks() {
		return links;
	}

	public void setLinks(Vector<URI> links) {
		this.links = links;
	}
	
	public BobListener() {
		actionsBob();
	}

	private void actionsBob() {
		bob.getSearch1().addActionListener(this);
		bob.getSearch2().addActionListener(this);
		bob.getSearch3().addActionListener(this);
		bob.getSearch4().addActionListener(this);
		bob.getAnswerList().addMouseListener(this);
		bob.getSubject().addFocusListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof JButton)
		{
			switch(((JButton)arg0.getSource()).getName())
			{
				case("story1"):
					bob.getAnswerList().setVisible(true);
					bob.getErrorArea().setText("");bob.getErrorArea().setVisible(false); 
					try{
						if(bob.getSubject().getText().equals(""))
							throw new Methods.EmptyFieldException();
						setLinks(BobGUI.firstStoryGUI(bob.getSubject().getText(), bob.getAnswerList(), bob.getErrorArea()));
					}catch(Exception e){
						bob.getAnswerList().setVisible(false);
						System.err.println(e);
						bob.getErrorArea().setVisible(true);
						if(e instanceof Methods.EmptyFieldException)
							bob.getErrorArea().setText("Veuillez saisir une question.");
						else
							bob.getErrorArea().setText("Aucune correspondance trouvée, veuillez resaisir une question.");
					}
					break;
				case("story2"):
					bob.getAnswerList().setVisible(true);
					bob.getErrorArea().setText("");bob.getErrorArea().setVisible(false); 
					try{
						if(bob.getSubject().getText().equals(""))
							throw new Methods.EmptyFieldException();
						boolean success = BobGUI.secondStoryGUI(bob.getSubject().getText(), bob.getAnswerList(), bob.getErrorArea());
						if(!success)
						{
							bob.getAnswerList().setVisible(false);
							bob.getErrorArea().setVisible(true);
							bob.getErrorArea().setText("Veuillez saisir une question valide.");
						}
					}catch(Exception e){
						bob.getAnswerList().setVisible(false);
						System.err.println(e);
						bob.getErrorArea().setVisible(true);
						if(e instanceof Methods.EmptyFieldException)
							bob.getErrorArea().setText("Veuillez saisir une question.");
						else
							bob.getErrorArea().setText("Aucun tag existant ne convient à votre question.");
					}
					break;
				case("story3"):
					bob.getAnswerList().setVisible(true);
					bob.getErrorArea().setText("");bob.getErrorArea().setVisible(false);
					try{
						setLinks(BobGUI.thirdStoryGUI(Integer.parseInt(bob.getSubject().getText()), bob.getAnswerList(), bob.getErrorArea()));
					}catch(Exception e){
						bob.getAnswerList().setVisible(false);
						System.err.println(e);
						bob.getErrorArea().setVisible(true);
						if(bob.getSubject().getText().equals(""))
							bob.getErrorArea().setText("Veuillez saisir un identifiant.");
						else if(e instanceof BadIdException ||e instanceof NumberFormatException )
							bob.getErrorArea().setText("Veuillez saisir un identifiant valide.");
						else
							bob.getErrorArea().setText("Une erreur est survenue, veuillez refaire votre recherche (voir console pour plus de détails).");
					}
					break;
				case("story4"):
					bob.getAnswerList().setVisible(true);
					bob.getErrorArea().setText("");bob.getErrorArea().setVisible(false);
					try{
						setLinks(BobGUI.fourthStoryGUI(Integer.parseInt(bob.getSubject().getText()), bob.getAnswerList(), bob.getErrorArea()));
					}catch(Exception e){
						bob.getAnswerList().setVisible(false);
						//System.err.println(e);
						bob.getErrorArea().setVisible(true);
						if(bob.getSubject().getText().equals(""))
							bob.getErrorArea().setText("Veuillez saisir un identifiant.");
						else if(e instanceof BadIdException ||e instanceof NumberFormatException )
							bob.getErrorArea().setText("Veuillez saisir un identifiant valide.");
						else
							bob.getErrorArea().setText("Une erreur est survenue, veuillez refaire votre recherche (voir console pour plus de détails).");
					}
					break;
				default:
					break;
			}
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		if(arg0.getSource() instanceof JTextField)
		{
			JTextField source = (JTextField) arg0.getSource();
			source.setForeground(Color.BLACK);
			if(source.getText().equals("ex: How to use regex in java?"))
				source.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(!links.isEmpty())
		{
			int user = bob.getAnswerList().getSelectedIndex();
			try {
				if(user!=0) Desktop.getDesktop().browse(links.get(user-1));
	        } catch (IOException ex) {
	            System.err.println("It looks like there's a problem");
	        }
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
