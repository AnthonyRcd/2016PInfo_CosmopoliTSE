package TSE.P_INFO.CosmopoliTse.InterfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

public class AliceListener implements ActionListener {

	InterfaceAlice alice = new InterfaceAlice();
	
	public AliceListener() {
		actionsAlice();
	}

	private void actionsAlice() {
		alice.search1.addActionListener(this);
		alice.search2.addActionListener(this);
		alice.search3.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton)
		{
			switch(((JButton) e.getSource()).getName())
			{
				case("story1"):
				{
					
					break;
				}
				case("story2"):
				{
					
					break;
				}
				case("story3"):
				{
					
					break;
				}
				
			}
		}

	}

}
