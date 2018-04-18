package co.id.fifgroup.audit.listtraversal;

//import jatis.audit.Traversable;

import java.io.Serializable;

import co.id.fifgroup.core.audit.Traversable;

/**
 * Example only
 * @author kiton
 *
 */
public class Contact implements Serializable, Traversable{
	private static final long serialVersionUID = 1503467165895317083L;
	private String name;
	private String phonenumber;
	private int id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	//@Override
	public Object getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
