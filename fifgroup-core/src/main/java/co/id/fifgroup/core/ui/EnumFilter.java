package co.id.fifgroup.core.ui;

import java.io.Serializable;

public interface EnumFilter<E extends Enum<E>> extends Serializable{

	boolean apply(E enumeration);
}
