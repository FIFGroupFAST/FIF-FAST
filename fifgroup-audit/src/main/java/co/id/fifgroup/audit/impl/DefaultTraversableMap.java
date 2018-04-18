package co.id.fifgroup.audit.impl;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.id.fifgroup.audit.TraversableMap;



public class DefaultTraversableMap extends HashMap<String, Boolean> implements TraversableMap{
	private static final long serialVersionUID = 1L;
	
	@Override
	public TraversableMap addList(List<Class<?>> traversableClasses) {
		if (traversableClasses == null){
			return this;
		}
		for (Class<?> s : traversableClasses){
			this.add(s);
		}
		return this;
	}

	@Override
	public TraversableMap add(Class<?> traversableClass) {
		if (traversableClass == null){
			return this;
		}
		this.put(traversableClass.getName(), true);
		return this;
	}

	@Override
	public boolean isAuditable(Object object) {
		if (object == null){
			return false;
		}
		Boolean b = this.get(object.getClass().getName());
		return b != null && b.booleanValue();
	}

	@Override
	public TraversableMap addAll(List<TraversableMap> listOfTraversableMaps) {
		if (listOfTraversableMaps == null){
			return this;
		}
		for (TraversableMap m : listOfTraversableMaps){
			if (m.getBackingMap() == null){
				continue;
			}
			this.putAll(m.getBackingMap());
		}
		return this;
	}

	@Override
	public Map<String, Boolean> getBackingMap() {
		return Collections.unmodifiableMap(this);
	}

	@Override
	public void setTraversables(List<String> traversableClasses) {
		for (String c : traversableClasses){
			this.put(c, true);
		}
	}

	@Override
	public void setTraversable(String traversable) {
		if (traversable == null){
			return;
		}
		this.put(traversable, true);
	}

	@Override
	public void setTraversableMaps(List<TraversableMap> listOfTraversableMaps) {
		this.addAll(listOfTraversableMaps);
	}
}
