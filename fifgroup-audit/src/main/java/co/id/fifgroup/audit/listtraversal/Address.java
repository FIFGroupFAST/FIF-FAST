package co.id.fifgroup.audit.listtraversal;

import java.io.Serializable;

import co.id.fifgroup.core.audit.Traversable;

//import jatis.audit.Traversable;

/**
 * Example only
 * @author kiton
 *
 */
public class Address implements Serializable, Traversable{
	private static final long serialVersionUID = 1102565059063717364L;
	int id;
	private String literalAddress;
	@Override
	public Object getId() {
		return id;
	}
	public String getLiteralAddress() {
		return literalAddress;
	}
	public void setLiteralAddress(String literalAddress) {
		this.literalAddress = literalAddress;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
