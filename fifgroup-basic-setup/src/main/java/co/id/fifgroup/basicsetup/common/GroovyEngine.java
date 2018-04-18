package co.id.fifgroup.basicsetup.common;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.basicsetup.service.ServiceModel;

public class GroovyEngine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5782081219815780067L;
	private static final Logger logger = LoggerFactory.getLogger(GroovyEngine.class);
	
	private final ThreadLocal<GroovyShell> groovyShellLocal = new ThreadLocal<GroovyShell>();
	
	private final String BUSINESS_GROUP_ID = "business_group_id";
	private final String COMPANY_ID = "company_id";
	private final String PERSON_ID = "person_id";
	private final String MODULE_TYPE = "module_type";
	private final String PROCESS_DATE = "process_date";
	private final String SERVICE_MODEL = "serviceModel";
	private final String ADDITIONAL_PARAM = "additionalParam";
	
	private static final ThreadLocal<Map<String, Script>> scriptCacheLocal = new ThreadLocal<Map<String, Script>>();
	//Penambahan method chace untuk performa payroll
	//ITSM 15111108145257 By:GMA
	private static Map<String, Script> cache(){
		Map<String, Script> c = scriptCacheLocal.get();
		if (c == null){
			c = new HashMap<String, Script>();
			scriptCacheLocal.set(c);
		}
		return c;
	}
	//end
	protected GroovyShell getGs(){
		GroovyShell gs = groovyShellLocal.get();
		if (gs == null){
			gs = new GroovyShell();
			groovyShellLocal.set(gs);
		}
		return gs;
	}
	
	//Penambahan method parseScript untuk performa payroll
	//ITSM 15111108145257 By:GMA
	protected Script parseScript(String script){
		Map<String, Script> c = cache();
		Script s = (Script)c.get(script); 
		if (s == null){
			s = getGs().parse(script);
			c.put(script, s);
		}
		return s;
	}
	//end
	
	public Object executeGroovyScriptFormulaParameter(String script, DefaultParameterFormula defaultParameterFormula, ServiceModel serviceGroovy, String formulaParameter) throws Exception {
		long startTime = System.currentTimeMillis(); 
		//dinon aktifkan karena memakai chace yang sudah dibuat untuk perfoma payroll
		//ITSM 15111108145257 By:GMA
		//GroovyShell groovyShell = getGs();
		//Binding binding = new Binding();
		//diganti :
		Script s = parseScript(script);
		Binding binding = s.getBinding();
		//end edited
		Object result = null;
		binding.setVariable(BUSINESS_GROUP_ID, defaultParameterFormula.getBusinessGroupId());
		binding.setVariable(COMPANY_ID, defaultParameterFormula.getCompanyId());
		binding.setVariable(PERSON_ID, defaultParameterFormula.getPersonId());
		binding.setVariable(MODULE_TYPE, defaultParameterFormula.getModuleType());
		binding.setVariable(PROCESS_DATE, defaultParameterFormula.getProcessDate());
		binding.setVariable(SERVICE_MODEL, serviceGroovy);
		binding.setVariable(ADDITIONAL_PARAM, defaultParameterFormula.getAdditionalParam());
		//result dirubah untuk menjalakan parseScript
		//ITSM 15111108145257 By:GMA
		//result  = getGs().evaluate(script);
		result  = s.run();
		//end edited
		logger.info(String.format("Time elapsed for formula parameter %s : %d ms", formulaParameter, System.currentTimeMillis() - startTime));
		//return groovyShell.evaluate(script);
	
		
		//groovyShell = null;
		
		return result;
		
	}

	public Object executeGroovyScriptFormula(String script, DefaultParameterFormula defaultParameterFormula, List<FormulaParameter> listFormulaParameter, ServiceModel serviceGroovy) throws Exception {
		//GroovyShell groovyShell = new GroovyShell(binding);
//      dinon aktifkan karena memakai chace yang sudah dibuat untuk perfoma payroll
//      ITSM 15111108145257 By:GMA
//		GroovyShell groovyShell = getGs();
//		Binding binding = groovyShell.getContext();
//		diganti :
		Script s = parseScript(script);
		Binding binding = s.getBinding();
//		end edited
		Object result = null;
		binding.setVariable(BUSINESS_GROUP_ID, defaultParameterFormula.getBusinessGroupId());
		binding.setVariable(COMPANY_ID, defaultParameterFormula.getCompanyId());
		binding.setVariable(PERSON_ID, defaultParameterFormula.getPersonId());
		binding.setVariable(MODULE_TYPE, defaultParameterFormula.getModuleType());
		binding.setVariable(PROCESS_DATE, defaultParameterFormula.getProcessDate());
		binding.setVariable(SERVICE_MODEL, serviceGroovy);
		binding.setVariable(ADDITIONAL_PARAM, defaultParameterFormula.getAdditionalParam());
		for(FormulaParameter formulaParameter : listFormulaParameter) {
			binding.setVariable(formulaParameter.getParameterName(), executeGroovyScriptFormulaParameter(formulaParameter.getParameterFunction(), defaultParameterFormula, serviceGroovy, formulaParameter.getParameterName()));
		}
//		result dirubah untuk menjalakan parseScript
//		ITSM 15111108145257 By:GMA
//		result = groovyShell.evaluate(script);
		result  = s.run();
//		end edited
				
		
		return result;
	}
	
}
