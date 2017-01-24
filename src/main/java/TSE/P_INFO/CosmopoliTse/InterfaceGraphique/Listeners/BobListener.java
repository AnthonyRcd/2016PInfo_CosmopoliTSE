package TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Listeners;

import java.net.URI;
import java.util.Vector;

import javax.swing.JPanel;

import TSE.P_INFO.CosmopoliTse.InterfaceGraphique.Containers.InterfaceBob;

public class BobListener {

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
		// TODO Auto-generated constructor stub
	}

	

}
