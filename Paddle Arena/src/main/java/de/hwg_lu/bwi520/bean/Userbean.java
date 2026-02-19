package de.hwg_lu.bwi520.bean;

import de.hwg_lu.bwi520.jdbc.Userjdbc;
import de.hwg_lu.bwi520.modell.User;

public class Userbean {

	private User user;
	
	private Userjdbc connection;
	
	public Userbean() {
		
		this.user =null;
		
		this.connection= new Userjdbc(null);
	}
	
}
