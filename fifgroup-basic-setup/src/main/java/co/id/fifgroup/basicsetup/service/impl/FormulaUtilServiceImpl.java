package co.id.fifgroup.basicsetup.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.common.DefaultParameterFormula;
import co.id.fifgroup.basicsetup.common.GroovyEngine;
import co.id.fifgroup.basicsetup.dao.FormulaFinder;
import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.basicsetup.dto.FormulaVersionDTO;
import co.id.fifgroup.basicsetup.service.FormulaUtilService;
import co.id.fifgroup.basicsetup.service.ServiceModel;
import com.google.common.cache.Cache;

@Transactional
@Service
public class FormulaUtilServiceImpl implements FormulaUtilService {

	private static final Logger logger = LoggerFactory.getLogger(FormulaUtilServiceImpl.class);
	private static final GroovyEngine GROOVY_ENGINE = new GroovyEngine();
	@Autowired
	private ServiceModel serviceGroovyImpl;
	@Autowired
	private FormulaFinder formulaFinder;
	//menambahkan chace ketika getFormulaDto
	//ITSM 15111108145257 By:GMA
	//Start Implement
	private static final  Map<String, FormulaVersionDTO> cache = new ConcurrentHashMap<String, FormulaVersionDTO>();
	private static volatile boolean useCache = false;
	public static final void setUseCache(boolean b){
		useCache = b;
		if (!b){
			cache.clear();
		}
	}
	private FormulaVersionDTO getFormulaDto(String formulaName, Date effectiveOnDate){
		if (useCache){
			FormulaVersionDTO dto = cache.get(formulaName);
			if (dto == null){
				dto = formulaFinder.getFormulaVersionDtoByFormulaNameAndEffectiveOnDate(formulaName, DateUtils.truncate(effectiveOnDate, Calendar.DATE));
				cache.put(formulaName, dto);
			}
			return dto;
		}
		return formulaFinder.getFormulaVersionDtoByFormulaNameAndEffectiveOnDate(formulaName, DateUtils.truncate(effectiveOnDate, Calendar.DATE));
	}
	//end implement
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public <T> T executeFormula(String formulaName, Date effectiveOnDate, DefaultParameterFormula defaultParameterFormula, Class<T> clazz) throws Exception {
		try {
			long startTime = System.currentTimeMillis(); 
			//GroovyEngine groovyEngine = new GroovyEngine();
			//ITSM 15111108145257 By:GMA
			//dinon aktifkan supaya memanggil fromula yang sudah memakai chace
			//FormulaVersionDTO formulaVersionDto = formulaFinder.getFormulaVersionDtoByFormulaNameAndEffectiveOnDate(formulaName, DateUtils.truncate(effectiveOnDate, Calendar.DATE));
			FormulaVersionDTO formulaVersionDto = getFormulaDto(formulaName, effectiveOnDate);
			//end edited
			Object object = GROOVY_ENGINE.executeGroovyScriptFormula(formulaVersionDto.getFormula(), defaultParameterFormula, formulaVersionDto.getFormulaParameters(), serviceGroovyImpl);
			if(object == null) {
				return null;
			}
			/*if(clazz != object.getClass()) {
				throw new InvalidClassException("Return class not valid with parameter class");
			}*/
			logger.info(String.format("Time elapsed for formula %s : %d ms", formulaName, System.currentTimeMillis() - startTime));
			return (T) object;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public <T> T executeFormula(String formulaName, String script, List<FormulaParameter> formulaParameters, DefaultParameterFormula defaultParameterFormula, Class<T> clazz) throws Exception {
		try {
			long startTime = System.currentTimeMillis(); 
			//GroovyEngine groovyEngine = new GroovyEngine();
			Object object = GROOVY_ENGINE.executeGroovyScriptFormula(script, defaultParameterFormula, formulaParameters, serviceGroovyImpl);
			if(object == null) {
				return null;
			}
			logger.info(String.format("Time elapsed for formula %s : %d ms", formulaName, System.currentTimeMillis() - startTime));
			return (T) object;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}


}
