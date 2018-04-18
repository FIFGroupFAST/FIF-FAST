package co.id.fifgroup.audit.impl;

/**
 * Class name cleaner
 * @author kiton
 *$
 */
class ClassNameCleaner {
	
	/**
	 * Currently the value is $$EnhancerByCGLIB$$
	 */
	static final String CLS_ENHANCER_SUFFIX = "$$EnhancerByCGLIB$$";
	
	/**
	 * Cleanup class name
	 * @param clsName class name
	 * @return cleaned up name
	 */
	static String cleanupClassName(String clsName){
		if (clsName == null){
			return null;
		}
		int idx = clsName.indexOf(CLS_ENHANCER_SUFFIX);
		if (idx > 0){
			return clsName.substring(0, idx);
		}
		return clsName;
	}

}
