package TSE.P_INFO.CosmopoliTse.InterfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import TSE.P_INFO.CosmopoliTse.UsersStories.DaveGUI;

public class DaveListener implements ActionListener {

	InterfaceDave dave = new InterfaceDave();
	
	public DaveListener() {
		actionsDave();
		// TODO Auto-generated constructor stub
	}

	//Addition of the listener on each button
	public void actionsDave(){
		
		dave.search1.addActionListener(this);
		dave.search2.addActionListener(this);
		dave.search3.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton)
		{
			switch(((JButton) e.getSource()).getName())
			{
				case("story1"):
				{
					try {
						dave.errorArea.setVisible(false);
						DaveGUI.firstStoryGUI(dave.subject.getText(), dave.answerArea);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						dave.answerArea.setSelectedTextColor(Color.red);
						dave.answerArea.setText("Veuillez entrer un sujet.");
					}
					break;
				}
				case("story2"):
				{
					try {
						dave.errorArea.setVisible(false);
						DaveGUI.secondStoryGUI(dave.subject.getText(),	dave.answerArea);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						dave.answerArea.setSelectedTextColor(Color.red);
						dave.answerArea.setText("Veuillez entrer un sujet.");
					}
					break;
				}
				case("story3"):
				{
					 dave.errorArea.setEditable(false);
					try {
						StringBuilder sb = DaveGUI.thirdStoryGUI(dave.subject.getText().split(";"), dave.answerArea);
						if(sb.length()!=0)
						{
							dave.errorArea.setVisible(true);
							dave.errorArea.setText(sb.toString());
							dave.add(dave.errorArea,BorderLayout.LINE_END);
							
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						dave.answerArea.setText("Veuillez entrer une série de tags.");
					}
					break;
				}
				
			}
		}

	}

}
