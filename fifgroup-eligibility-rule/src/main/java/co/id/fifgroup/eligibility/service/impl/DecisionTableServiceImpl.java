package co.id.fifgroup.eligibility.service.impl;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.io.ResourceFactory;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatelessKnowledgeSession;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.Stream;
import com.google.common.collect.Lists;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.eligibility.constant.RetrievalMode;
import co.id.fifgroup.eligibility.dao.DecisionTableMapper;
import co.id.fifgroup.eligibility.dao.DecisionTableRowMapper;
import co.id.fifgroup.eligibility.domain.DecisionTable;
import co.id.fifgroup.eligibility.domain.DecisionTableRow;
import co.id.fifgroup.eligibility.domain.DecisionTableRowExample;
import co.id.fifgroup.eligibility.dto.DecisionTableDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableRowDTO;
import co.id.fifgroup.eligibility.dto.Fact;
import co.id.fifgroup.eligibility.dto.FactTypeDTO;
import co.id.fifgroup.eligibility.dto.Result;
import co.id.fifgroup.eligibility.dto.Results;
import co.id.fifgroup.eligibility.finder.DecisionTableFinder;
import co.id.fifgroup.eligibility.finder.FactTypeCacheSynchronizeMapper;
import co.id.fifgroup.eligibility.service.DecisionTableModelSetupService;
import co.id.fifgroup.eligibility.service.DecisionTableService;
import co.id.fifgroup.eligibility.service.FactTypeSetupService;
import co.id.fifgroup.eligibility.util.FactResolver;

@Service
public class DecisionTableServiceImpl implements DecisionTableService{
	
	private static final Logger logger = LoggerFactory.getLogger(DecisionTableServiceImpl.class);

	private static final Map<String, DecisionTableDTO> cachedDecisionTable = Collections.synchronizedMap(new HashMap<String, DecisionTableDTO>());
	private static final Map<String, StatelessKnowledgeSession> cachedSession = Collections.synchronizedMap(new HashMap<String, StatelessKnowledgeSession>());
	private static final Map<String, Fact> cachedFact = Collections.synchronizedMap(new HashMap<String, Fact>());
	private static final Map<String, Fact> payrollCache = Collections.synchronizedMap(new HashMap<String, Fact>());
	
	@Autowired
	private DecisionTableFinder decisionTableFinder;
	
	@Autowired
	private DecisionTableMapper dtMapper;
	
	@Autowired
	private FactTypeSetupService factTypeSetupService;
		
	@Autowired
	private DecisionTableRowMapper rowMapper;
	
	@Autowired
	private DecisionTableModelSetupService dtModelService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private FactResolver resolver;
	
	@Autowired
	private FactTypeCacheSynchronizeMapper factTypeCacheSynchronizeMapper;
	
	@Override
	@Transactional(readOnly=false)
	public void save(DecisionTableDTO subject) {
		if (null != subject && null != subject.getModel()) {
			Long userId = securityService.getSecurityProfile().getUserId();
			Date today = new Date();
			ModelMapper modelMapper = new ModelMapper();
			DecisionTable decisionTable = modelMapper.map(subject, DecisionTable.class);
			decisionTable.setVersionNumber(Long.valueOf(subject.getModel().getVersionNumber()));
			String drl = new DecisionTableCompiler().compile(subject);
			if (logger.isDebugEnabled())
				logger.debug(drl);
			decisionTable.setDrl(drl);
			subject.setDrl(drl);
			List<DecisionTableRow> validData = new ArrayList<DecisionTableRow>();
			if (null == subject.getId()) {
				decisionTable.setCreatedBy(userId);
				decisionTable.setCreationDate(today);
				decisionTable.setLastUpdatedBy(userId);
				decisionTable.setLastUpdateDate(today);
				dtMapper.insert(subject.getModulePrefix(), decisionTable);
				subject.setId(decisionTable.getId());
				int seq = 1;
				for (DecisionTableRowDTO rowDTO : subject.getRows()) {
					if(!isEmptyCondition(rowDTO)){
						DecisionTableRow row = modelMapper.map(rowDTO, DecisionTableRow.class);
						row.setSeqNo(seq++);
						row.setCreatedBy(userId);
						row.setCreationDate(today);
						row.setLastUpdatedBy(userId);
						row.setLastUpdateDate(today);
						row.setDecisionTableId(decisionTable.getId());
						/**
						 * [16022316000474] HCMS ph 2- CAM - performance upload career path
						 * Jika ingin insert data dalam jumlah banyak, lebih bagus menggunakan metode bulk
						 * ex: insert data per 500 records
						 * By Jatis (HS) 24/03/2016
						 */
						//rowMapper.insert(subject.getModulePrefix(), row);
						validData.add(row);
					}
				}
				List<List<DecisionTableRow>> partitionOriginal = Lists.partition(validData, 500);
				for (List<DecisionTableRow> list : partitionOriginal) {
					rowMapper.insertBulk(subject.getModulePrefix(), list);
				}
				//END 16022316000474
				
			} else {
				decisionTable.setLastUpdatedBy(userId);
				decisionTable.setLastUpdateDate(today);
				dtMapper.updateByPrimaryKeySelective(subject.getModulePrefix(), decisionTable);
				DecisionTableRowExample example = new DecisionTableRowExample();
				example.createCriteria().andDecisionTableIdEqualTo(decisionTable.getId());
				rowMapper.deleteByExample(subject.getModulePrefix(), example);
				int seq = 1;
				for (DecisionTableRowDTO rowDTO : subject.getRows()) {
					if(!isEmptyCondition(rowDTO)){
						DecisionTableRow row = modelMapper.map(rowDTO, DecisionTableRow.class);
						row.setSeqNo(seq++);
						row.setDecisionTableId(decisionTable.getId());
						row.setCreatedBy(userId);
						row.setCreationDate(today);
						row.setLastUpdatedBy(userId);
						row.setLastUpdateDate(today);
						/**
						 * [16022316000474] HCMS ph 2- CAM - performance upload career path
						 * Jika ingin insert data dalam jumlah banyak, lebih bagus menggunakan metode bulk
						 * ex: insert data per 500 records
						 * By Jatis (HS) 24/03/2016
						 */
						//rowMapper.insert(subject.getModulePrefix(), row);
						validData.add(row);
					}
				}
				List<List<DecisionTableRow>> partitionOriginal = Lists.partition(validData, 500);
				for (List<DecisionTableRow> list : partitionOriginal) {
					rowMapper.insertBulk(subject.getModulePrefix(), list);
				}
				//END 16022316000474
			}
			cachedDecisionTable.remove(subject.getModulePrefix() + subject.getId());
			cachedDecisionTable.put(subject.getModulePrefix() + subject.getId(), subject);
			cachedSession.remove(subject.getModulePrefix() + subject.getId());
		}
	}
	
	private boolean isEmptyCondition(DecisionTableRowDTO decisionTableRowDTO ){
		
		String[] conditions = decisionTableRowDTO.getConditions().split("\\|");
		for (String string : conditions) {
			if(string != null && !string.equals("") && decisionTableRowDTO.getConditions() != null && !decisionTableRowDTO.getConditions().equals(""))
				return false;
		}
		
		if(decisionTableRowDTO.getConditions() != null && !decisionTableRowDTO.getConditions().equals(""))
			return false;
		
		return true;
	}
		
	@Override
	@Transactional(readOnly=true)
	public DecisionTableDTO findDecisionTableById(String modulePrefix, Long id) {
		/**
		 * [16022316000474] HCMS ph 2- CAM - performance upload career path
		 * Jgn paksakan mybatis untuk melakukan group by hasil query.
		 * Lebih bagus query 2x untuk mendapatkan data row.
		 * By Jatis (HS) 24/03/2016
		 */
		//DecisionTableDTO result = decisionTableFinder.findById(modulePrefix, id);
		DecisionTableDTO result = decisionTableFinder.findHeaderById(modulePrefix, id);
		if (null != result){
			result.setRows(decisionTableFinder.findRowById(modulePrefix, id));
			result.setModel(dtModelService.findByIdAndVersionNumber(result.getModel().getId(), result.getModel().getVersionNumber()));
		}
		//END 16022316000474
		return result;
	}
	
	/**
	 * [16022316000474] HCMS ph 2- CAM - performance upload career path
	 * By Jatis (HS) 24/03/2016
	 */
	@Override
	@Transactional(readOnly=true)
	public DecisionTableDTO findDecisionTableHeaderById(String modulePrefix, Long id) {
		return decisionTableFinder.findHeaderById(modulePrefix, id);
	}
	//END 16022316000474
	
	
	// used by housing for performance issue [15051914073236] - 28/05/2015 | PHI
	@Override
	@Transactional(readOnly=true)
	public DecisionTableDTO findDecisionTableByIdAndConditions(String modulePrefix, Long id, String conditions) {
		/* (Remarked) 15051913370470 - Improve performance for eligibility task runner | By : PHI
		DecisionTableDTO result = decisionTableFinder.findByIdAndConditions(modulePrefix, id, conditions);
		if (null != result)
			result.setModel(dtModelService.findByIdAndVersionNumber(result.getModel().getId(), result.getModel().getVersionNumber()));
			*/
		DecisionTableDTO result = decisionTableFinder.findHeaderById(modulePrefix, id);
		if (null != result) {
			result.setModel(dtModelService.findByIdAndVersionNumber(result.getModel().getId(), result.getModel().getVersionNumber()));
			result.setRows(decisionTableFinder.findRowsByIdAndConditions(modulePrefix, id, conditions));
		}
		return result;
	}
		
	@Override
	@Transactional(readOnly=true)
	public DecisionTableDTO findDecisionTableByIdRestructured(String modulePrefix, Long id) {
		DecisionTableDTO result = decisionTableFinder.findHeaderById(modulePrefix, id);
		if (null != result) {
			result.setModel(dtModelService.findByIdAndVersionNumber(result.getModel().getId(), result.getModel().getVersionNumber()));
			result.setRows(decisionTableFinder.findRowsByIdAndConditions(modulePrefix, id, null));
		}
		return result;
	}	
	// end used by housing for performance issue [15051914073236] - 28/05/2015 | PHI

	@Override
	public DecisionTableDTO findDecisionTableById(String modulePrefix, Long id, boolean cached) {
		if (!cached) {
			return findDecisionTableById(modulePrefix, id);
		} else if (cached 
				&& !cachedDecisionTable.containsKey(modulePrefix + id)) {
			cachedDecisionTable.put(modulePrefix + id, findDecisionTableById(modulePrefix, id));
		}
		return cachedDecisionTable.get(modulePrefix + id);
	}

	@Override
	@Transactional(readOnly = true)
	public Results execute(String modulePrefix, Long decisionTableId, Long personId,
			Date effectiveOnDate, Map<String, Object> additionalParams) {
		return execute(modulePrefix,decisionTableId,personId,
				effectiveOnDate, additionalParams, false);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Results execute(String modulePrefix, Long decisionTableId, Long personId,
			Date effectiveOnDate, Map<String, Object> additionalParams, boolean useCache) {
		long totalTime = System.currentTimeMillis();
				
		DecisionTableDTO dt;
		
		if (additionalParams != null && additionalParams.containsKey("conditions"))
			dt = findDecisionTableByIdAndConditions(modulePrefix, decisionTableId, (String) additionalParams.get("conditions"));
		else
			dt = findDecisionTableById(modulePrefix, decisionTableId, true);
		
		if (dt == null)
			return new Results();
		
		StatelessKnowledgeSession session = getSession(dt);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		
		if (additionalParams == null)
			additionalParams = new HashMap<>();
		additionalParams.put("personId", personId);
		if (null != effectiveOnDate)
			additionalParams.put("effectiveOnDate", sdf.format(effectiveOnDate));
		
		List<Command<?>> commands = new ArrayList<>();
		commands.add(CommandFactory.newSetGlobal("results", new Results(), true));
		
		
		commands.add(CommandFactory.newInsertElements(getFacts(dt, additionalParams, useCache)));
		
		
		Results res = null;
		
		try {
			ExecutionResults results = 	session.execute(CommandFactory.newBatchExecution(commands));
			res = (Results) results.getValue("results");
			if (logger.isDebugEnabled() && res != null) {
				for (Result result : res.getElements()) {
					logger.debug(result.toString());
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		
		
		
		logger.info(String.format("DT Total Time for %d  : %d ms", decisionTableId, System.currentTimeMillis() - totalTime));
		return res;
	}

	@Transactional(readOnly=true)
	private List<Fact> getFacts(DecisionTableDTO decisionTable, Map<String, Object> params, boolean cache) {
		List<Fact> result = new ArrayList<>();
		long startTime = System.currentTimeMillis();
		DecisionTableModelDTO model = decisionTable.getModel();
		
		
		
		for (String factTypeId : model.getFactTypes()) {
			
			FactTypeDTO factType = factTypeSetupService.findById(factTypeId);
			
			if (factTypeId.equals("PAYROLL_FACT_TYPE") && !payrollCache.isEmpty()){
				logger.info("Fact Retrieved : " + payrollCache.get("" + params.get("personId") + params.get("effectiveOnDate")));
				result.add(payrollCache.get("" + params.get("personId") + params.get("effectiveOnDate")));
			}else if (factType.getRetrievalMode() == RetrievalMode.QUERY && params.get("personId") != null ) {
				String query = factType.getSqlQuery();
				params.put("query", query);
				if (cache && cachedFact.containsKey("" + params.get("personId") + factTypeId + params.get("effectiveOnDate"))) {
					logger.debug("get fact from cache " + factTypeId + params.get("personId") + params.get("effectiveOnDate"));
					result.add(cachedFact.get("" + params.get("personId") + factTypeId + params.get("effectiveOnDate")));
				}
				else {	
					List<Map<String, Object>> factProperties = resolver.resolveFact(params);
					if (factProperties.size() > 0) {
						Fact fact = new Fact(factTypeId);
						fact.setProperties(factProperties.get(0));
						Map<String, Object> properties = fact.getProperties();
						for (String key : properties.keySet()) {
							Object value = properties.get(key);
							if (value instanceof Timestamp) {
								properties.put(key, new Date(((Timestamp) value).getTime()));
							}
						}
						if (cache) {
							if (cachedFact.size() > 5000)
								cachedFact.clear();
							cachedFact.put("" + params.get("personId") + factTypeId + params.get("effectiveOnDate"), fact);
							logger.debug("caching fact for " + factTypeId + params.get("personId") + params.get("effectiveOnDate"));
						}
						result.add(fact);
					}
				}
			} else if (factType.getRetrievalMode() == RetrievalMode.SUPPLIED || params.get("personId") == null ) {
				Fact fact = new Fact(factTypeId);
				Map<String, Object> properties = new HashMap<>();
				for(String key : params.keySet()){
					if (!"query".equals(key))
						properties.put(key, params.get(key));
				}
				fact.setProperties(properties);
				result.add(fact);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug(result.toString());
			logger.debug(String.format("DT Fact Type for %d  : %d ms", decisionTable.getId(), System.currentTimeMillis() - startTime));
		}
		return result;
	}
	
	private StatelessKnowledgeSession getSession(DecisionTableDTO dt) {
		if (cachedSession.get(dt.getModulePrefix() + dt.getId()) == null) {
		
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			kbuilder.add(ResourceFactory.newInputStreamResource(new ByteArrayInputStream(dt.getDrl().getBytes()))
					, ResourceType.DRL);
			
			for (KnowledgeBuilderError error : kbuilder.getErrors()) {
	        	logger.error(error.getMessage());
	        }
			
			KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
			kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
			
			StatelessKnowledgeSession session = kbase.newStatelessKnowledgeSession();
			cachedSession.put(dt.getModulePrefix() + dt.getId(), session);
		}
		
		return cachedSession.get(dt.getModulePrefix() + dt.getId());
	}


	@Override
	@Transactional(readOnly=false)
	public void save(String modulePrefix, DecisionTableDTO subject) {
		subject.setModulePrefix(modulePrefix);
		save(subject);
		
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(DecisionTableDTO subject) {
		delete("ELR", subject);
	}
	

	@Override
	@Transactional(readOnly=false)
	public void delete(String modulePrefix, DecisionTableDTO subject) {
		if (null != subject) {
			DecisionTableRowExample example = new DecisionTableRowExample();
			example.or()
				.andDecisionTableIdEqualTo(subject.getId());
			rowMapper.deleteByExample(modulePrefix, example);
			dtMapper.deleteByPrimaryKey(modulePrefix, subject.getId());
		}
	}
	
	@Override
	public void flushFactTypeCache() {
		cachedFact.clear();
	}


	@Override
	@Transactional
	public void populateCacheTable(Long companyId) {
		Stream stream = new Stream();
		stream.start();
		factTypeCacheSynchronizeMapper.deleteFactTypeCache(companyId);
		factTypeCacheSynchronizeMapper.populateEmployee(companyId);
		factTypeCacheSynchronizeMapper.synchronizeCOP();
		factTypeCacheSynchronizeMapper.synchronizeCOP2();
		factTypeCacheSynchronizeMapper.synchronizeMOP();
		factTypeCacheSynchronizeMapper.synchronizeMOP2();
		factTypeCacheSynchronizeMapper.synchronizeCashable();
		factTypeCacheSynchronizeMapper.synchronizeOvertime();
		factTypeCacheSynchronizeMapper.synchronizeOPEX();
		factTypeCacheSynchronizeMapper.synchronizeOPEX2();
		factTypeCacheSynchronizeMapper.synchronizeOCOP();
		factTypeCacheSynchronizeMapper.synchronizeUnpaidLeave();
		factTypeCacheSynchronizeMapper.synchronizeUnpaidAbsent();
		stream.stop("Populate Cache Table");
		
	}
	
	@Override
	@Transactional
	public void populatePayrollCache(Long companyId) {
		payrollCache.clear();
		Date date = DateUtils.truncate(new Date(), Calendar.DATE);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String effectiveOnDate = sdf.format(date);
		String factTypeId = "PAYROLL_FACT_TYPE";
		FactTypeDTO factType = factTypeSetupService.findById("PAYROLL_FACT_TYPE");
		String query = factType.getSqlQuery();
		
		Map<String,Object> params = new HashMap<>();
		params.put("personId", null);
		params.put("effectiveOnDate", effectiveOnDate);		
		params.put("query", query);
		
		List<Map<String, Object>> factProperties = resolver.resolveFact(params);
		for(Map<String,Object> factProperty : factProperties) {
			Fact fact = new Fact(factTypeId);
			fact.setProperties(factProperty);
			Map<String, Object> properties = fact.getProperties();
			for (String key : properties.keySet()) {
				Object value = properties.get(key);
				if (value instanceof Timestamp) {
					properties.put(key, new Date(((Timestamp) value).getTime()));
				}
			}
			payrollCache.put("" + fact.getProperties().get("personId") + effectiveOnDate, fact);
		}
	}
	
	//start
	//added by jatis; SIT CAM
	// 28 oktober 2015
	@Override
	@Transactional(readOnly=true)
	public DecisionTableDTO findDecisionTableById(String modulePrefix, Long id, Long rowFrom, Long rowEnd) {
		DecisionTableDTO result = decisionTableFinder.findByIdAndRowBound(modulePrefix, id, rowFrom, rowEnd);
		if (null != result)
			result.setModel(dtModelService.findByIdAndVersionNumber(result.getModel().getId(), result.getModel().getVersionNumber()));
		return result;
	}
	
	@Override
	@Transactional(readOnly=true)
	public DecisionTableDTO findByIdWithoutDRL(String modulePrefix, Long id) {
		DecisionTableDTO result = decisionTableFinder.findByIdWithoutDRL(modulePrefix, id);
		if (null != result)
			result.setModel(dtModelService.findByIdAndVersionNumber(result.getModel().getId(), result.getModel().getVersionNumber()));
		return result;
	}
	
	@Override
	public DecisionTableDTO findByCriteriaWithoutDRL(Map<String, Object> criteria, String modulePrefix) {
		DecisionTableDTO result = decisionTableFinder.findByCriteriaWithoutDRL(modulePrefix,criteria);
		if (null != result)
			result.setModel(dtModelService.findByIdAndVersionNumber(result.getModel().getId(), result.getModel().getVersionNumber()));
		return result;
	}
	
	//end added by jatis; SIT CAM

	
	
}
