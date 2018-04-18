package co.id.fifgroup.eligibility.util;


public class IdGenerator {

	public final static String generateFrom(String name) {
		return name.toUpperCase().replace(" ", "_");
	}
}
