package co.id.fifgroup.core.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.domain.interfacing.Approver;
import co.id.fifgroup.core.domain.interfacing.CVR;
import co.id.fifgroup.core.domain.interfacing.DepositHistoryInterface;
import co.id.fifgroup.core.domain.interfacing.DepositInterface;
import co.id.fifgroup.core.domain.interfacing.FIFAppsInterfaceException;
import co.id.fifgroup.core.domain.interfacing.GlInterface;
import co.id.fifgroup.core.domain.interfacing.HutangKoperasi;
import co.id.fifgroup.core.domain.interfacing.InvoiceDetail;
import co.id.fifgroup.core.domain.interfacing.InvoiceInterface;
import co.id.fifgroup.core.domain.interfacing.InvoiceNumber;
import co.id.fifgroup.core.domain.interfacing.IuranKoperasi;
import co.id.fifgroup.core.domain.interfacing.MutationInterface;
import co.id.fifgroup.core.domain.interfacing.PreInvoiceInterface;
import co.id.fifgroup.core.domain.interfacing.Prepayment;
import co.id.fifgroup.core.domain.interfacing.PrepaymentInterface;
import co.id.fifgroup.core.domain.interfacing.PrepaymentSettlement;
import co.id.fifgroup.core.domain.interfacing.Rv;
import co.id.fifgroup.core.domain.interfacing.RvInterface;
import co.id.fifgroup.core.domain.interfacing.SettlementInterface;
import co.id.fifgroup.core.domain.interfacing.TabunganKoperasi;
import co.id.fifgroup.core.domain.interfacing.TaxInterface;
import co.id.fifgroup.core.dto.BudgetActivityLogDTO;
import co.id.fifgroup.core.dto.ModuleErrorLogDTO;
import co.id.fifgroup.core.service.FIFAppsServiceAdapter;


@Service("fifappsService")
@Transactional
public class FIFAppsServiceAdapterImpl extends AbstractFIFAppsService implements FIFAppsServiceAdapter{
	
	private Logger logger = LoggerFactory.getLogger(FIFAppsServiceAdapterImpl.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public String settleWithoutPrepayment(SettlementInterface settlementInterface) throws FIFAppsInterfaceException {
		return createInvoice(settlementInterface);
	}

	private String createInvoice(SettlementInterface settlementInterface) {
		//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
		if(checkNullSegment2(settlementInterface.getBranchDestination()) == null)
			throw new FIFAppsInterfaceException("GL Code was inactive");
		//and add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
		String invoiceNumber;
		InvoiceNumber invNo = new InvoiceNumber();
		invNo.setBranchCode(settlementInterface.getBranchCode());
		invoiceNumberService.getInvoiceNumber(invNo);
		invoiceNumber = invNo.getInvoiceNumber();
		settlementInterface.setInvoiceNumber(invoiceNumber);
		int seq = 1;
		for (InvoiceDetail detail : settlementInterface.getInvoiceDetails()) {
			settlementInterface.setProductCode(detail.getProductCode());
			settlementInterface.setAmount(detail.getAmount());
			settlementInterface.setSequenceNumber(seq);
			InvoiceInterface invoiceInterface = modelMapper.map(settlementInterface, InvoiceInterface.class);
			this.createInvoice(invoiceInterface);
			seq++;
		}
		
		return invoiceNumber;
	}

	@Override
	public String prepaymentProcess(PrepaymentInterface prepaymentInterface) throws FIFAppsInterfaceException {
		//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
				if(checkNullSegment2(prepaymentInterface.getBranchDestination()) == null)
					throw new FIFAppsInterfaceException("GL Code was inactive");
				//and add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
		prepaymentInterface.setInvoiceType("P");
		prepaymentInterface.setSpsPrepayment("Y");
		prepaymentInterface.setForceApprover("Y");
		String prepaymentNumber = createInvoice(prepaymentInterface);
	
		//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
		//if(checkNullSegment2(prepaymentInterface.getBranchDestination()) == null)
			//throw new FIFAppsInterfaceException("GL Code was inactive");
		//and add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
		return prepaymentNumber;
	}

	@Override
	public void createJournalMutation(MutationInterface mutationInterface)
			throws FIFAppsInterfaceException {
		validateMutasiInterface(mutationInterface);
	}

	@Override
	public boolean validateJournalMutation(String claimNumber,
			String employeeNumber, String companyCode, String branchCode) {
		Prepayment prepayment = fifappsFinder.getPrepaymentTransactionByReferenceNumber(claimNumber, employeeNumber);
		
		if(!prepayment.getSegment1().equals(companyCode) || !prepayment.getSegment2().equals(branchCode))
			return true;
		else
			return false;
	}

	@Override
	public String createInvoice(List<InvoiceInterface> invoiceInterfaces) throws FIFAppsInterfaceException {
		String invoiceNumber = null;
		//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
		if(checkNullSegment2(invoiceInterfaces.get(0).getBranchDestination()) == null)
			throw new FIFAppsInterfaceException("GL Code was inactive");
		//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
		
		if(!invoiceInterfaces.isEmpty()){
			InvoiceInterface invoiceInterface = invoiceInterfaces.get(0);
			
			invoiceInterface.setInvoiceNumber(getInvoiceNumber(invoiceInterface.getBranchCode()));
			invoiceNumber = invoiceInterface.getInvoiceNumber();
			int seq = 1;
			for (InvoiceInterface invoice : invoiceInterfaces) {
				invoice.setInvoiceNumber(invoiceInterface.getInvoiceNumber());
				invoice.setSequenceNumber(seq);
				insertInvoice(invoice);
				seq++;
			}
			
			insertPreInvoice(invoiceInterface);
			insertApprover(invoiceInterface);
			
		}
		
		
		return invoiceNumber;
	}
	
	// added by Jatis, 5 Jan 2016
	// create invoice dengan invoice detail yang memiliki branch code lebih dari satu
	@Override
	public String createInvoice(List<InvoiceInterface> invoiceInterfaces, String branchCode) throws FIFAppsInterfaceException {
		String invoiceNumber = null;
		
		//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
		if(checkNullSegment2(invoiceInterfaces.get(0).getBranchDestination()) == null)
			throw new FIFAppsInterfaceException("GL Code was inactive");
		//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
		
		if(!invoiceInterfaces.isEmpty()){
			InvoiceInterface invoiceInterface = (InvoiceInterface) SerializationUtils.clone(invoiceInterfaces.get(0));
			invoiceInterface.setBranchCode(branchCode);
			invoiceInterface.setInvoiceNumber(getInvoiceNumber(invoiceInterface.getBranchCode()));
			invoiceNumber = invoiceInterface.getInvoiceNumber();
			int seq = 1;
			for (InvoiceInterface invoice : invoiceInterfaces) {
				invoice.setInvoiceNumber(invoiceInterface.getInvoiceNumber());
				invoice.setSequenceNumber(seq);
				insertInvoice(invoice);
				seq++;
			}
			
			insertPreInvoice(invoiceInterface);
			insertApprover(invoiceInterface);
			
		}
		
		
		return invoiceNumber;
	}
	
	// update gl berdasarkan invoice number dan branch code
	@Override
	public void updateGlInterfaceAttribute1ByReference4AndSegment2(String attribute1, String reference4, String segment2) {
		fifappsFinder.updateGlInterfaceAttribute1ByReference4AndSegment2(attribute1, reference4, segment2);
	}
	
	// end added by Jatis, 5 Jan 2016
	
	public void insertApprover(InvoiceInterface invoiceInterface) throws FIFAppsInterfaceException{
		Approver approver = new Approver();
		approver.setBranchCode(invoiceInterface.getBranchCode());
		approver.setInvoiceNumber(invoiceInterface.getInvoiceNumber());
		approver.setSequenceApproverNumber(invoiceInterface.getSequenceNumber());
		approver.setApprover(invoiceInterface.getCreatedBy());
		approver.setApproveDate(invoiceInterface.getDueDate());
		fifappsFinder.insertApprover(approver);
	}
	
	public void insertPreInvoice(InvoiceInterface invoiceInterface) throws FIFAppsInterfaceException {
		PreInvoiceInterface preInvoiceInterface = new PreInvoiceInterface();
		preInvoiceInterface.setInvoiceNumber(invoiceInterface.getInvoiceNumber());
		preInvoiceInterface.setSpsPrepayment(invoiceInterface.getSpsPrepayment());
		preInvoiceInterface.setForceApprover(invoiceInterface.getForceApprover());
		fifappsFinder.insertPreInvoiceInterface(preInvoiceInterface);
		String preInvResult = preInvoiceInterface.getReturnValue();
		if(!preInvResult.equals(invoiceInterface.getInvoiceNumber())) {
			ModuleErrorLogDTO errorLog = new ModuleErrorLogDTO();
			errorLog.setLogTime(new Date());
			errorLog.setModuleName("FIFAPPS");
			String errorMessage = "Error interface insert_invoice_interface "+preInvResult;
			errorLog.setErrorMessage(invoiceInterface.getTransactionType().concat(" || ").concat(invoiceInterface.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
	}
	
	

	@Override
	public void createJournal(GlInterface glInterface)
			throws FIFAppsInterfaceException {
		fifappsFinder.insertGlInterface(glInterface);
	}

	@Override
	public Long getGlBookId() {
		return fifappsFinder.getGLBookId();
	}

	@Override
	public void createPPH(TaxInterface taxInterface)
			throws FIFAppsInterfaceException {
		fifappsFinder.insertApTrnPPH(taxInterface);
	}

	@Override
	public void updateTrnInvoiceDetail(InvoiceDetail invoiceDetail) {
		fifappsFinder.updateTrnInvoiceDtl(invoiceDetail);
	}

	@Override
	public String generateDepositNumber(String branchCode, Long moduleId) {
		return fifappsFinder.getDepositNumber(branchCode, moduleId);
	}

	@Override
	public void insertRvTrnDeposit(DepositInterface depositInterface) {
		fifappsFinder.insertTrnDeposit(depositInterface);
	}

	@Override
	public void insertRvTrnDepositHistory(
			DepositHistoryInterface depositHistoryInterface) {
		fifappsFinder.insertTrnDepositHistory(depositHistoryInterface);
		
	}

	@Override
	public void updateLoanEmployeeByEmployeeNumberAndContractNumber(
			String username, String employeeNumber, String contractNumber) {
		fifappsFinder.updateCifnEmplLoan(username, employeeNumber, contractNumber);
		
	}

	@Override
	public void updateInsentifByEmployeeNumber(Date processDate, String employeeNumber, String instfFlag) {
		fifappsFinder.updateIntensifByEmployeeNumber(processDate, employeeNumber, instfFlag);
	}

	@Override
	public BigDecimal getInsentifByEmployeeNumber(String employeeNumber) {
		
		return fifappsFinder.getInsentifByEmployeeNumber(employeeNumber);
	}

	@Override
	public List<IuranKoperasi> selectIuranKoperasiByCompanyId(Long companyId, Date processDate) {
		
		return fifappsFinder.selectIuranKoperasiByCompanyId(companyId, processDate);
	}

	@Override
	public void updateIuranKoperasi(String employeeNumber,String savProdTypeId,String userName, Integer savSequeance, String fifFlag) {
		fifappsFinder.updateIuranKoperasi(employeeNumber,savProdTypeId,userName, savSequeance, fifFlag);
		
	}

	@Override
	public void createRv(RvInterface rvInterface) throws FIFAppsInterfaceException {
		
		CVR cvr = new CVR();
		
		cvr.setSegment1(rvInterface.getSegment1());
		cvr.setSegment2(rvInterface.getSegment2());
		cvr.setSegment3(rvInterface.getSegment3());
		cvr.setSegment4(rvInterface.getSegment4());
		cvr.setSegment5(rvInterface.getSegment5());
		cvr.setSegment6(rvInterface.getSegment6());
		cvr.setSegment7(rvInterface.getSegment7());
		cvr.setSegment8(rvInterface.getSegment8());
	
		checkCVRGLNotNull(cvr);
		
		/*Rv rv = bookRVNumber(rvInterface.getBranchId(), rvInterface.getCreatedBy());
		
		rvInterface.setRvNumber(rv.getRvNumber());*/
		
		if(rvInterface.getRvNumber() == null)
			throw new FIFAppsInterfaceException("RV Number cannot be null");
		
		fifappsFinder.genRvInterface(rvInterface);
		
		if(!rvInterface.getReturnValue().equals("OK"))
			throw new FIFAppsInterfaceException("Error when find Generate RV : " + rvInterface.getReturnValue());
	
	}
	
	//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 01-July-2016
	@Override
	public String checkNullSegment2(String segment2){
		//return fifappsFinder.checkNullSegment2(segment2);
	/*	if(fifappsFinder.checkNullSegment2(segment2) == null)
			throw new FIFAppsInterfaceException("GL Code was inactive");*/
		
		return fifappsFinder.checkNullSegment2(segment2);
	}
	//end add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 01-July-2016

	
	private void checkCVRGLNotNull(CVR cvr) throws FIFAppsInterfaceException{
		fifappsFinder.checkCvrGlNotNull(cvr);
		String cvrOutMessage = cvr.getMessage();
		int cvrResult = cvr.getReturnValue();
		//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 01-July-2016
		if(checkNullSegment2(cvr.getSegment2()) == null)
			throw new FIFAppsInterfaceException("GL Code was inactive");
		//end add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 01-July-2016
		if(cvrResult != 0)
			throw new FIFAppsInterfaceException("Cannot validate CVR "+cvr.getSegment1()+"-"+cvr.getSegment2()+"-"+cvr.getSegment3()+"-"+cvr.getSegment4()+"-"+cvr.getSegment5()+"-"+cvr.getSegment6()+"-"+cvr.getSegment7()+"-"+cvr.getSegment8()+" message out "+cvrOutMessage);
	}

	@Override
	public SettlementInterface settlementProcess(SettlementInterface settlementInterface) throws FIFAppsInterfaceException {
		ModuleErrorLogDTO errorLog = new ModuleErrorLogDTO();
		errorLog.setLogTime(new Date());
		errorLog.setModuleName("FIFAPPS");
		
		//Validate Mutasi
		if(!settlementInterface.getCompanyGlCodeOld().equals(settlementInterface.getCompanyGlCode()) 
				|| !settlementInterface.getBranchDestinationOld().equals(settlementInterface.getBranchDestination())){
			
			//If organization code or company code in prepayment transaction are not equal with current company or branch code
			//Validate Mutasi Interface
			MutationInterface mutationInterface = new MutationInterface();
			
			mutationInterface.setPersonId(settlementInterface.getPersonId());
			mutationInterface.setTransferDate(new Date());
			
			mutationInterface.setLocationIdOldCode(settlementInterface.getBranchDestinationOld());
			mutationInterface.setLocationIdNewCode(settlementInterface.getBranchDestination());
			
			mutationInterface.setCompanyCodeOld(settlementInterface.getCompanyGlCodeOld());
			mutationInterface.setCompanyCodeNew(settlementInterface.getCompanyGlCode());
			
			mutationInterface.setCostCenterNew(settlementInterface.getCostCenter());
			mutationInterface.setCostCenterOld(settlementInterface.getCostCenterOld());
			
			mutationInterface.setTransactionType(settlementInterface.getTransactionType());
			mutationInterface.setPrepaymentAmount(settlementInterface.getPrepaymentAmount());
			mutationInterface.setClaimNumber(settlementInterface.getClaimNumber());
			mutationInterface.setClassCode(settlementInterface.getClassCode());
			mutationInterface.setEmployeeNumber(settlementInterface.getEmployeeNumber());
			mutationInterface.setEmployeeName(settlementInterface.getEmployeeName());
			mutationInterface.setCurrencyCode(settlementInterface.getCurrencyCode());
			mutationInterface.setSupplierBankCode(settlementInterface.getSegmentBank());
			mutationInterface.setProductCode(settlementInterface.getProductCode());
			mutationInterface.setSegmentIb(settlementInterface.getSegmentIb());
			
			validateMutasiInterface(mutationInterface);
		}
		
		Rv rv = bookRVNumber(settlementInterface.getBranchDestination(), settlementInterface.getCreatedBy());
		String rvNumber = rv.getRvNumber();
		
		if(rvNumber == null) {
			String errorMessage = "Cannot insert Rv Number : "+rvNumber+"; "+rv.getReturnValue();
			
			errorLog.setErrorMessage(settlementInterface.getTransactionType().concat(" || ").concat(settlementInterface.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
		
		settlementInterface.setInvoiceNumber(getInvoiceNumber(settlementInterface.getBranchCode()));
		int seq = 1;
		for (InvoiceDetail detail : settlementInterface.getInvoiceDetails()) {
			settlementInterface.setProductCode(detail.getProductCode());
			settlementInterface.setAmount(detail.getAmount());
			settlementInterface.setSequenceNumber(seq);
			InvoiceInterface invoiceInterface = modelMapper.map(settlementInterface, InvoiceInterface.class);
			insertInvoice(invoiceInterface);
			seq++;
		}
		
		insertPreInvoice(settlementInterface);
		insertApprover(settlementInterface);
		
		Prepayment prepayment = fifappsFinder.getPrepaymentTransaction(settlementInterface.getPrepaymentInvoiceNumber());
		
		PrepaymentSettlement prepaymentSettlement = new PrepaymentSettlement();
		prepaymentSettlement.setInvoiceNumber(settlementInterface.getInvoiceNumber());
		prepaymentSettlement.setInvoicePrepaymentNumber(settlementInterface.getPrepaymentInvoiceNumber());
		prepaymentSettlement.setPrepaymentSequenceNumber(prepayment.getInvoiceSequenceNumber());
		prepaymentSettlement.setCreatedBy(settlementInterface.getCreatedBy());
		prepaymentSettlement.setCreateRv("Y");
		prepaymentSettlement.setRvNumber(rvNumber);
		fifappsFinder.settlePrepayment(prepaymentSettlement);
		
		if(prepaymentSettlement.getReturnValue() == 1) {
			String errorMessage = "ERROR run ap_spc_prepay : "+settlementInterface.getInvoiceNumber()+" ; "+prepaymentSettlement.getOutMessage();
			
			errorLog.setErrorMessage(settlementInterface.getTransactionType().concat(" || ").concat(settlementInterface.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
		
		settlementInterface.setRvNumber(prepaymentSettlement.getRvNumber());
		settlementInterface.setSettlementInvoiceNumber(settlementInterface.getInvoiceNumber());
		
		return settlementInterface;
	}
	
	@Override
	public Rv bookRVNumber(String branchDest, String createdBy){
		Rv rv = new Rv();
		rv.setBranchCode(branchDest);
		rv.setCreatedBy(createdBy);
		fifappsFinder.bookRvNumber(rv);
		rv.setRvNumber(rv.getReturnValue());
		return rv;
	}

	@Override
	public void updateHutangKoperasiByNpkAndContractNumber(
			HutangKoperasi hutangKoperasi) {
		fifappsFinder.updateHutangKoperasiByNpkAndContractNumber(hutangKoperasi);
		
	}

	@Override
	public List<HutangKoperasi> selectPotonganKoperasiByCompanyId(Long companyId, Date processDate) {
		return fifappsFinder.selectPotonganKoperasiByCompanyId(companyId, processDate);
	}

	@Override
	public String createInvoiceWithoutSequence(List<InvoiceInterface> invoiceInterfaces) throws FIFAppsInterfaceException {
		String invoiceNumber = null;
		
		if(!invoiceInterfaces.isEmpty()){
			InvoiceInterface invoiceInterface = invoiceInterfaces.get(0);
			
			invoiceInterface.setInvoiceNumber(getInvoiceNumber(invoiceInterface.getBranchCode()));
			invoiceNumber = invoiceInterface.getInvoiceNumber();
			for (InvoiceInterface invoice : invoiceInterfaces) {
				invoice.setInvoiceNumber(invoiceInterface.getInvoiceNumber());
				insertInvoice(invoice);
			}
			
			insertPreInvoice(invoiceInterface);
			insertApprover(invoiceInterface);
			
		}
		return invoiceNumber;
	}

	@Override
	public void updateActivityCode(String activityCode, String invoiceNo) {
		fifappsFinder.updateActivityCode(activityCode, invoiceNo);
	}

	@Override
	public void insertBudgetActivityLog(BudgetActivityLogDTO budgetActivityLog) {
		fifappsFinder.insertBudgetActivityLog(budgetActivityLog);
	}

	@Override
	public void updateActivityCodeDetail(String activityCode, String invoiceNo,
			Integer sequence, BigDecimal amount) {
		fifappsFinder.updateActivityCodeDetail(activityCode, invoiceNo, sequence, amount);
	}

	@Override
	public void updateGlInterfaceAttribute1ByReference4(String attribute1, String reference4) {
		fifappsFinder.updateGlInterfaceAttribute1ByReference4(attribute1, reference4);
	}

	@Override
	public SettlementInterface settlementProcessWithoutMutation(
			SettlementInterface settlementInterface)
			throws FIFAppsInterfaceException {
		ModuleErrorLogDTO errorLog = new ModuleErrorLogDTO();
		errorLog.setLogTime(new Date());
		errorLog.setModuleName("FIFAPPS");
		
		Rv rv = bookRVNumber(settlementInterface.getBranchDestination(), settlementInterface.getCreatedBy());
		String rvNumber = rv.getRvNumber();
		
		if(rvNumber == null) {
			String errorMessage = "Cannot insert Rv Number : "+rvNumber+"; "+rv.getReturnValue();
			
			errorLog.setErrorMessage(settlementInterface.getTransactionType().concat(" || ").concat(settlementInterface.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
		
		settlementInterface.setInvoiceNumber(getInvoiceNumber(settlementInterface.getBranchCode()));
		int seq = 1;
		for (InvoiceDetail detail : settlementInterface.getInvoiceDetails()) {
			settlementInterface.setProductCode(detail.getProductCode());
			settlementInterface.setAmount(detail.getAmount());
			settlementInterface.setSequenceNumber(seq);
			InvoiceInterface invoiceInterface = modelMapper.map(settlementInterface, InvoiceInterface.class);
			insertInvoice(invoiceInterface);
			seq++;
		}
		
		insertPreInvoice(settlementInterface);
		insertApprover(settlementInterface);
		
		Prepayment prepayment = fifappsFinder.getPrepaymentTransaction(settlementInterface.getPrepaymentInvoiceNumber());
		
		PrepaymentSettlement prepaymentSettlement = new PrepaymentSettlement();
		prepaymentSettlement.setInvoiceNumber(settlementInterface.getInvoiceNumber());
		prepaymentSettlement.setInvoicePrepaymentNumber(settlementInterface.getPrepaymentInvoiceNumber());
		prepaymentSettlement.setPrepaymentSequenceNumber(prepayment.getInvoiceSequenceNumber());
		prepaymentSettlement.setCreatedBy(settlementInterface.getCreatedBy());
		prepaymentSettlement.setCreateRv("Y");
		prepaymentSettlement.setRvNumber(rvNumber);
		fifappsFinder.settlePrepayment(prepaymentSettlement);
		
		if(prepaymentSettlement.getReturnValue() == 1) {
			String errorMessage = "ERROR run ap_spc_prepay : "+settlementInterface.getInvoiceNumber()+" ; "+prepaymentSettlement.getOutMessage();
			
			errorLog.setErrorMessage(settlementInterface.getTransactionType().concat(" || ").concat(settlementInterface.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
		
		settlementInterface.setRvNumber(prepaymentSettlement.getRvNumber());
		settlementInterface.setSettlementInvoiceNumber(settlementInterface.getInvoiceNumber());
		
		return settlementInterface;
	}

	@Override
	public void createJournalMutationTransfer(MutationInterface mutationInterface) throws FIFAppsInterfaceException {
		validateMutasiInterfaceTransfer(mutationInterface);
	}

	//add by lisa,1-sep-2015,tiket - 14102810453648//
	@Override
	public List<TabunganKoperasi> selectTabunganKoperasiByCompanyId(
			Long companyId, Date processDate) {
		return fifappsFinder.selectTabunganKoperasiByCompanyId(companyId, processDate);
	}

	@Override
	public void updateTabunganKoperasiByNpkAndContractNumber(
			TabunganKoperasi tabunganKoperasi) {
			fifappsFinder.updateTabunganKoperasiByNpkAndContractNumber(tabunganKoperasi);
		
	}
	
	@Override
	public double getTaxPercentageByTaxCode(String taxCode) {
		return fifappsFinder.getTaxPercentageByTaxCode(taxCode);
	}

	@Override
	public String getTaxRemarksByTaxCode(String taxCode) {
		return fifappsFinder.getTaxRemarksByTaxCode(taxCode);
	}
	
	// start added by WLY, create prepayment dengan detail lebih dari satu, 29 Dec 2015
	public String prepaymentProcess(List<PrepaymentInterface> prepaymentInterfaces) throws FIFAppsInterfaceException{
		for (PrepaymentInterface prepaymentInterface : prepaymentInterfaces) {
			prepaymentInterface.setInvoiceType("P");
			prepaymentInterface.setSpsPrepayment("Y");
			prepaymentInterface.setForceApprover("Y");
		}
		String prepaymentNumber = createPrepayment(prepaymentInterfaces);
		
		return prepaymentNumber;
	}
	
	private String createPrepayment(List<PrepaymentInterface> invoiceInterfaces) throws FIFAppsInterfaceException {
		String invoiceNumber = null;
		
		if(!invoiceInterfaces.isEmpty()){
			InvoiceInterface invoiceInterface = invoiceInterfaces.get(0);
			
			invoiceInterface.setInvoiceNumber(getInvoiceNumber(invoiceInterface.getBranchCode()));
			invoiceNumber = invoiceInterface.getInvoiceNumber();
			int seq = 1;
			for (InvoiceInterface invoice : invoiceInterfaces) {
				invoice.setInvoiceNumber(invoiceInterface.getInvoiceNumber());
				invoice.setSequenceNumber(seq);
				insertInvoice(invoice);
				seq++;
			}
			
			insertPreInvoice(invoiceInterface);
			insertApprover(invoiceInterface);
			
		}
		return invoiceNumber;
	}
	// end added by WLY
}
