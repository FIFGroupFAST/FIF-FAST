/**
 * Copyright (c) 2012 FIF. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of FIF.
 * ("Confidential Information"). <BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall only be
 * used in accordance with the terms of the license agreement entered into with
 * FIF; other than in accordance with the written permission of FIF. <BR>
 * 
 * Created on: Jun 22, 2010
 *
 * Author           Date         Version Description <BR>
 * ---------------- ------------ ------- --------------------------------- <BR>
 * 
 *  
 */

package co.id.fifgroup.avm.manager;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.dao.AVMAndTransactionMappingDAO;
import co.id.fifgroup.avm.dao.AVMApproverDAO;
import co.id.fifgroup.avm.dao.AVMDAO;
import co.id.fifgroup.avm.dao.AVMLevelDAO;
import co.id.fifgroup.avm.dao.AVMRuleMappingDAO;
import co.id.fifgroup.avm.dao.AVMVersionDAO;
import co.id.fifgroup.avm.dao.mybatis.MyBatisAVMAndTransactionMappingDAO;
import co.id.fifgroup.avm.dao.mybatis.MyBatisAVMApproverDAO;
import co.id.fifgroup.avm.dao.mybatis.MyBatisAVMDAO;
import co.id.fifgroup.avm.dao.mybatis.MyBatisAVMLevelDAO;
import co.id.fifgroup.avm.dao.mybatis.MyBatisAVMRuleMappingDAO;
import co.id.fifgroup.avm.dao.mybatis.MyBatisAVMVersionDAO;
import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMAndTransactionMapping;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMRuleLookupDetail;
import co.id.fifgroup.avm.domain.AVMRuleMapping;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;


/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMSetupManager {

	private static final Logger logger = LoggerFactory.getLogger(AVMSetupManager.class);
	public static final int THRESHOLD_TYPE = 1;
	public static final int DUEDATE_TYPE = 2;
	public static final int INITIAL_LEVEL_SEQUENCE = 0;
	public static final String DEFAULT_DOMAIN = "00000000-0000-0000-0000-000000000000";

	private AVMDAO avmDAO;
	private AVMLevelDAO avmLevelDAO;
	private AVMApproverDAO avmApproverDAO;
	private AVMAndTransactionMappingDAO avmAndTransactionMappingDAO;
	private AVMRuleMappingDAO avmRuleMappingDAO;
	private AVMVersionDAO avmVersionDAO;
	
	public AVMSetupManager(){
		super();
		avmDAO = new MyBatisAVMDAO();
		avmLevelDAO = new MyBatisAVMLevelDAO();
		avmApproverDAO = new MyBatisAVMApproverDAO();
		avmAndTransactionMappingDAO = new MyBatisAVMAndTransactionMappingDAO();
		avmRuleMappingDAO = new MyBatisAVMRuleMappingDAO();
		avmVersionDAO = new MyBatisAVMVersionDAO();
	}
	
	public AVMDAO getAvmDAO() {
		return avmDAO;
	}

	public void setAvmDAO(AVMDAO avmDAO) {
		this.avmDAO = avmDAO;
	}

	public AVMLevelDAO getAvmLevelDAO() {
		return avmLevelDAO;
	}

	public void setAvmLevelDAO(AVMLevelDAO avmLevelDAO) {
		this.avmLevelDAO = avmLevelDAO;
	}

	public AVMApproverDAO getAvmApproverDAO() {
		return avmApproverDAO;
	}

	public void setAvmApproverDAO(AVMApproverDAO avmApproverDAO) {
		this.avmApproverDAO = avmApproverDAO;
	}

	public AVMAndTransactionMappingDAO getAvmAndTransactionMappingDAO() {
		return avmAndTransactionMappingDAO;
	}

	public void setAvmAndTransactionMappingDAO(
			AVMAndTransactionMappingDAO avmAndTransactionMappingDAO) {
		this.avmAndTransactionMappingDAO = avmAndTransactionMappingDAO;
	}

	public AVMRuleMappingDAO getAvmRuleMappingDAO() {
		return avmRuleMappingDAO;
	}

	public void setAvmRuleMappingDAO(AVMRuleMappingDAO avmRuleMappingDAO) {
		this.avmRuleMappingDAO = avmRuleMappingDAO;
	}

	public AVMVersionDAO getAvmVersionDAO() {
		return avmVersionDAO;
	}

	public void setAvmVersionDAO(AVMVersionDAO avmVersionDAO) {
		this.avmVersionDAO = avmVersionDAO;
	}

	public void createAVMTemplate(AVM avm, List<AVMLevel> avmLevels) throws FifException {
		try {
			avmDAO.insertNewAVM(avm);
			avmLevelDAO.insertNewMultipleAVMLevels(avmLevels);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}

	public void updateAVM (AVM avm) throws FifException{
		try {
			avmDAO.updateAVM(avm);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
//	public void updateAVMApprovers (List<AVMApprover> avmApproverList) throws FifException {
//		try {
//			UUID avmId = avmApproverList.get(0).getAvmId();
//			avmApproverDAO.deleteAVMApprovers(avmId);
//			avmApproverDAO.insertMultipleNewAVMApprover(avmApproverList);
//		} 
//		catch (FifException e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		}
//		catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
//		}
//	}
	
	public void assignApprover(List<AVMApprover> approverList) throws FifException {
		try {
			avmApproverDAO.insertMultipleNewAVMApprover(approverList);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}

	public void mapAVMAndTransactionTypeList(UUID avmId, List<Integer> trxTypeList) throws FifException {
		try {
			avmAndTransactionMappingDAO.mapAVMAndTransactionTypeList(avmId, trxTypeList);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}

	public List<AVMApprover> getTopPriorityApproverList(UUID avmId, int levelSequence) throws FifException {
		try{
			return avmApproverDAO.getTopPriorityApproverList(avmId, levelSequence);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}

	public List<AVMApprover> getAVMApproversByLevelAndPriority(UUID avmId, int levelSequence, int priority) throws FifException {
		try{
			return avmApproverDAO.getApproverList(avmId, levelSequence, priority);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public List<AVMApprover> getAVMApproversByLevel(UUID avmId, int levelSequence) throws FifException {
		try{
			return avmApproverDAO.getApproverList(avmId, levelSequence);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public List<AVMApprover> getAVMApprovers(UUID avmId) throws FifException {
		try{
			return avmApproverDAO.getApproverList(avmId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}

	public List<AVMLevel> getAVMLevelList(UUID avmId) throws FifException {
		List<AVMLevel> avmLevels = null;
		try {
			avmLevels = avmLevelDAO.getAVMLevels(avmId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmLevels;
	}
	
	public List<String> getAllRuleAttributes() throws FifException {
		List<String> ruleAttributes = null;
		try {
			ruleAttributes = avmDAO.getAllRuleAttributes();
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return ruleAttributes;
	}
	
	public List<AVMRuleMapping> getAllRuleMappings() throws FifException {
		List<AVMRuleMapping> ruleMappings = null;
		try {
			ruleMappings = avmDAO.getAllRuleMappings();
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return ruleMappings;
	}
	
	public List<AVMRuleMapping> getRuleMappingsByRuleType(int ruleType) throws FifException {
		List<AVMRuleMapping> ruleMappings = null;
		try {
			ruleMappings = avmDAO.getRuleMappingsByRuleType(ruleType);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return ruleMappings;
	}
	
	public long countAVM(String avmName) throws FifException {
		long result = 0;
		try {
			result = avmDAO.countAVM(avmName);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return result;
	}
	
	public List<AVM> searchAVM(String avmName, int offset, int limit) throws FifException {
		List<AVM> avmList = null;
		try {
			avmList = avmDAO.searchAVM(avmName, offset, limit);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmList;
	}	
	
	public List<AVM> findAVM(String avmName) throws FifException {
		List<AVM> avmList = null;
		try {
			avmList = avmDAO.findAVM(avmName);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmList;
	}	
	
	public AVM getAVMById(UUID avmId) throws FifException {
		AVM avm = null;
		try {
			avm = avmDAO.getAVMById(avmId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avm;
	}
	
	public void deleteMappedAVMAndTrxTypeList(UUID avmId, List<Integer> trxTypeList) throws FifException {
		try{
			avmAndTransactionMappingDAO.deleteMappedAVMAndTrxTypeList(avmId, trxTypeList);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}

	public List<AVMRuleLookupDetail> getRuleLookupDetails(String lookupHeaderId) throws FifException {
		List<AVMRuleLookupDetail> ruleLookupDetails = null;
		try {
			ruleLookupDetails = avmRuleMappingDAO.getRuleLookupDetails(lookupHeaderId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return ruleLookupDetails;
	}
	
	public AVMRuleMapping getAVMRuleMapping(String attributeLabel) throws FifException {
		AVMRuleMapping avmRuleMapping = null;
		try {
			avmRuleMapping = avmRuleMappingDAO.getAVMRuleMapping(attributeLabel);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmRuleMapping;
	}
	
	public AVMRuleLookupDetail getAVMRuleLookupDetail(String lookupHeaderId, String lookupValue) throws FifException {
		AVMRuleLookupDetail ruleLookupDetail = null;
		try {
			ruleLookupDetail = avmRuleMappingDAO.getRuleLookupDetail(lookupHeaderId, lookupValue);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return ruleLookupDetail;
	}

	public List<Integer> getMappedTrxTypeList(UUID avmId) throws FifException {
		List <Integer> trxTypeList = null;
		try {
			avmAndTransactionMappingDAO.getMappedTrxTypeList(avmId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return trxTypeList;
	}
	
	// add yahya
	public List<AVMLevel> getAVMLevelList(UUID avmId, int avmVersionId) throws FifException {
		List<AVMLevel> avmLevels = null;
		try {
			avmLevels = avmLevelDAO.getAVMLevels(avmId, avmVersionId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmLevels;
	}
	
	public List<AVMApprover> getAVMApproversByLevel(UUID avmId, int levelSequence, int avmVersionId) throws FifException {
		try{
			return avmApproverDAO.getApproverList(avmId, levelSequence, avmVersionId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public List<AVMApprover> getAVMApproversByLevelAndPriority(UUID avmId, int levelSequence, int priority, int version) throws FifException {
		try{
			return avmApproverDAO.getApproverList(avmId, levelSequence, priority, version);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public void updateAVMApprovers (List<AVMApprover> avmApproverList, AVMVersions avmVersions) throws FifException {
		try {
			avmApproverDAO.deleteAVMApprovers(avmVersions.getAvmId(), avmVersions.getAvmVersionId());
			avmApproverDAO.insertMultipleNewAVMApprover(avmApproverList, avmVersions);
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public List<AVMApprover> getAVMApprovers(UUID avmId, int version) throws FifException {
		try{
			return avmApproverDAO.getApproverList(avmId, version);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public List<AVMApprover> getTopPriorityApproverList(UUID avmId, int levelSequence, int version) throws FifException {
		try{
			return avmApproverDAO.getTopPriorityApproverList(avmId, levelSequence, version);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public AVMAndTransactionMapping getAVMByTrxType(int trxType) throws FifException {
		AVMAndTransactionMapping avm = null;
		try {
			avm = avmAndTransactionMappingDAO.getAVMByTrxType(trxType);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avm;
	}
	
	public void updateMappedAVMAndTrxTypeList(UUID avmId, List<Integer> trxTypeList) throws FifException {
		try{
			avmAndTransactionMappingDAO.updateMappedAVMAndTrxTypeList(avmId, trxTypeList);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public void updateAVMLevels (List<AVMLevel> avmLevelList, AVMVersions avmVersions) throws FifException {
		try {
			avmLevelDAO.deleteAVMLevels(avmVersions.getAvmId(), avmVersions.getAvmVersionId());
			avmLevelDAO.insertNewMultipleAVMLevels(avmLevelList, avmVersions);
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public void createAVMTemplate(AVM avm, AVMVersions avmVersions, List<AVMLevel> avmLevels, List<AVMApprover> avmApprovers) throws FifException {
		try {
			avmDAO.insertNewAVM(avm);
			avmVersionDAO.insertNewVersion(avmVersions);
			avmLevelDAO.insertNewMultipleAVMLevels(avmLevels, avmVersions);
			avmApproverDAO.insertMultipleNewAVMApprover(avmApprovers, avmVersions);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public void updateAVMTemplate(AVMVersions avmVersions, List<AVMLevel> avmLevels, List<AVMApprover> avmApprovers) throws FifException {
		try {
			updateAVMVersions(avmVersions);
			updateAVMLevels(avmLevels, avmVersions);
			updateAVMApprovers(avmApprovers, avmVersions);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public int insertNewAVMLevel(AVMLevel avmLevel) throws FifException {
		try {
			return avmLevelDAO.insertNewAVMLevel(avmLevel);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	// avm version
	public List<AVMVersions> getAllAVMVersion(AVM avm) throws FifException {
		try {
			return avmVersionDAO.getAllAVMVersion(avm.getAvmId());
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public int getLastVersionNumber(UUID avmId) throws FifException {
		try {
			return avmVersionDAO.getLastVersionNumber(avmId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public AVMVersions getAVMVersionByNumberVersion(UUID avmId, int numberVersion) throws FifException {
		try {
			return avmVersionDAO.getAVMVersionByNumberVersion(avmId, numberVersion);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public void updateAVMVersions(AVMVersions avmVersions) throws FifException{
		try {
			avmVersionDAO.updateVersion(avmVersions);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public void createNewVersionAVM(AVMVersions avmVersions, List<AVMLevel> avmLevels, List<AVMApprover> avmApprovers) throws FifException {
		try {
			avmVersionDAO.insertNewVersion(avmVersions);
			avmLevelDAO.insertNewMultipleAVMLevels(avmLevels, avmVersions);
			avmApproverDAO.insertMultipleNewAVMApprover(avmApprovers, avmVersions);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public AVMVersions getPreviousVersion(AVMVersions avmVersions) throws FifException {
		try {
			return avmVersionDAO.getPreviousAVMVersion(avmVersions.getAvmId(), avmVersions.getVersionNumber() - 1);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public void deleteAVMTemplate(AVMVersions avmVersions) throws FifException {
		try {
			avmDAO.deleteAVM(avmVersions.getAvmId());
			deleteAVMVersions(avmVersions);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public void deleteAVMVersions(AVMVersions avmVersions) throws FifException {
		try {
			avmVersionDAO.deleteVersion(avmVersions);
			avmLevelDAO.deleteAVMLevels(avmVersions.getAvmId(), avmVersions.getAvmVersionId());
			avmApproverDAO.deleteAVMApprovers(avmVersions.getAvmId(), avmVersions.getAvmVersionId());
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public AVMVersions getCurrentActiveVersion(UUID avmId, Date currentDate) throws FifException {
		try {
			return avmVersionDAO.getCurrentActiveVersion(avmId, currentDate);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public int getTheLastLevelSequence(UUID avmId, int avmVersionId) throws FifException {
		try {
			return avmLevelDAO.getTheLastLevelSequence(avmId, avmVersionId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
}
