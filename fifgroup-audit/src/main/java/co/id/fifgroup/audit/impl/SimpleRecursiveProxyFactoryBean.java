package co.id.fifgroup.audit.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.objectweb.asm.Type;
import org.springframework.beans.factory.FactoryBean;

/**
 * Simple (no custom interception features added) 
 * and recursive (if necessary, this factory creates child proxies for return objects inside a proxy) 
 * proxy object factory bean
 * @author kiton
 *
 */
public class SimpleRecursiveProxyFactoryBean implements FactoryBean<Object>{
	protected final Object mutex = new Object();
	protected String targetClass;
	/**
	 * Target object to-be-proxied
	 */
	protected Object target;
	
	/**
	 * Created proxy object
	 */
	protected Object object;
	protected String[] proxyInterfaceStrs;
	protected Class<?>[] proxyInterfaces;
	
	/**
	 * Convert array of strings to array of classes
	 * @param clsStrings
	 * @return
	 * @throws ClassNotFoundException
	 */
	protected Class<?>[] toClasses(String... clsStrings) throws ClassNotFoundException{
		LinkedList<Class<?>> l = new LinkedList<Class<?>>();
		for (String s : clsStrings){
			l.add(Class.forName(s));
		}
		return l.toArray(new Class<?>[l.size()]);
	}

	/**
	 * Create a proxy instance to a target object based on defined proxy interfaces
	 * @param targetClass target object's Class
	 * @param target target object to be proxied
	 * @param proxyInterfaces proxy interfaces
	 * @return a proxy instance
	 */
	public Object createProxy(Class<?> targetClass, Object target, Class<?>... proxyInterfaces){
		Object key = generateKey(targetClass, target, proxyInterfaces);
		Object proxy = proxyMap.get(generateKey(targetClass, target, proxyInterfaces));
		if (proxy != null){
			return proxy;
		}
		Enhancer enh = new Enhancer();
		enh.setSuperclass(targetClass);
		enh.setInterfaces(proxyInterfaces);
		enh.setCallback(new MethodInterceptorImpl(target, targetClass));
		proxy = enh.create();
		proxyMap.put(key, proxy);
		return proxy;
	}
	
	/**
	 * Proxy instances map
	 */
	protected final Map<Object, Object> proxyMap = Collections.synchronizedMap(new HashMap<Object, Object>());
	
	/**
	 * Generate a proxy instances key based on target Class, target instance, and proxy interfaces.
	 * @param targetClass target Class
	 * @param target target instance
	 * @param proxyInterfaces proxy interfaces
	 * @return a proxy instance key.
	 */
	protected Object generateKey(Class<?> targetClass, Object target, Class<?>...proxyInterfaces){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((targetClass == null) ? 0 : targetClass.hashCode());
		result = prime * result + Arrays.hashCode(proxyInterfaces);
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}
	
	public Object getObject() throws Exception {
		if (object == null){
			synchronized(mutex){
				this.proxyInterfaces = toClasses(this.proxyInterfaceStrs);
				object = createProxy(this.targetClass == null ?
						target.getClass() : Class.forName(this.targetClass), 
						target, proxyInterfaces);
			}
		}
		return object;
	}

	public Class<?> getObjectType() {
		return null;
	}

	public boolean isSingleton() {
		return true;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public String[] getProxyInterfaces() {
		return proxyInterfaceStrs;
	}

	public void setProxyInterfaces(String[] proxyInterfaceStrs) {
		this.proxyInterfaceStrs = proxyInterfaceStrs;
	}

	/**
	 * Method interceptor implementation
	 * @author kiton
	 */
	protected class MethodInterceptorImpl implements MethodInterceptor{
		private Object targetInstance;
		private FastClass fastClass;
		
		/**
		 * Method interceptor constructor
		 * @param target target object to-be-proxied
		 * @param targetClass target object's class
		 */
		MethodInterceptorImpl(Object target, Class<?> targetClass){
			this.fastClass = FastClass.create(targetClass);
			this.targetInstance = target;
		}
		public Object intercept(Object proxy, Method method, Object[] args,
				MethodProxy methodProxy) throws Throwable {
			final Signature s = methodProxy.getSignature();
			if ("getOriginal".equals(s.getName()) && (s.getArgumentTypes() == null || s.getArgumentTypes().length == 0)){
				return targetInstance;
			}
			try {
				return methodProxy.invoke(targetInstance, args);
			} catch (ClassCastException err){
				Class<?>[] clses = new Class[s.getArgumentTypes().length];
				int i = 0;
				for (Type t : s.getArgumentTypes()){
					clses[i] = Class.forName(t.getClassName());
					++i;
				}
				
				FastMethod fm = fastClass.getMethod(s.getName(), clses);
				Object rv = fm.invoke(targetInstance, args);
				
				if (Type.VOID_TYPE.equals(s.getReturnType()) || rv == null){
					return null;
				}
				
				Class<?> retClass = correctClass(s.getReturnType().getClassName());
				if (retClass.isAssignableFrom(fm.getReturnType())){
					return rv;
				} else {
					return createProxy(fm.getReturnType(), rv, retClass);
				}
			}
		}
	}
	
	private Class<?> correctClass(String name) throws ClassNotFoundException{
		if (name == null){
			return null;
		}
		if (name.length() > 0 && name.indexOf('.') > 0){
			return Class.forName(name);
		}
		if (name.startsWith("int")){
			return int.class;
		}
		if (name.startsWith("long")){
			return long.class;
		}
		if (name.startsWith("short")){
			return short.class;
		}
		if (name.startsWith("double")){
			return double.class;
		}
		if (name.startsWith("byte")){
			return byte.class;
		}
		if (name.startsWith("char")){
			return char.class;
		}
		return void.class;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}	
}
