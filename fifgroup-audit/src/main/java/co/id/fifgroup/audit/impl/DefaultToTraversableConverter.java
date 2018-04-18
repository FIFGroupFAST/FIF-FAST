package co.id.fifgroup.audit.impl;

import co.id.fifgroup.audit.ToTraversableConverter;
import co.id.fifgroup.core.audit.Traversable;



/**
 * Default ToTraversableConverter (no cache, but depends on
 * {@link SimpleRecursiveProxyFactoryBean} provided cache.
 * @author kiton
 *
 */
public class DefaultToTraversableConverter implements ToTraversableConverter{
	private final SimpleRecursiveProxyFactoryBean proxyFactory = new SimpleRecursiveProxyFactoryBean();
	
	@Override
	public void clearCache(){
		//not implemented
	}
	
	@Override
	public Traversable toTraversable(Object o) {
		if (o == null){
			return null;
		}
		if (o instanceof Traversable){
			return (Traversable)o;
		}
		return (Traversable) proxyFactory.createProxy(o.getClass(), o, new Class<?>[]{Traversable.class});
	}
	
}
