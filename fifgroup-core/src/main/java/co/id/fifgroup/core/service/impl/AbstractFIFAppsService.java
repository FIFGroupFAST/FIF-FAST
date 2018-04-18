package co.id.fifgroup.core.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.constant.SupplierType;
import co.id.fifgroup.core.domain.interfacing.Approver;
import co.id.fifgroup.core.domain.interfacing.BankOdInfo;
import co.id.fifgroup.core.domain.interfacing.CVR;
import co.id.fifgroup.core.domain.interfacing.EmployeeBankInfo;
import co.id.fifgroup.core.domain.interfacing.FIFAppsInterfaceException;
import co.id.fifgroup.core.domain.interfacing.GlInterface;
import co.id.fifgroup.core.domain.interfacing.InvoiceInterface;
import co.id.fifgroup.core.domain.interfacing.InvoiceNumber;
import co.id.fifgroup.core.domain.interfacing.MutationInterface;
import co.id.fifgroup.core.domain.interfacing.PreInvoiceInterface;
import co.id.fifgroup.core.domain.interfacing.Prepayment;
import co.id.fifgroup.core.domain.interfacing.Rv;
import co.id.fifgroup.core.domain.interfacing.SupplierOpexInfo;
import co.id.fifgroup.core.dto.ModuleErrorLogDTO;
import co.id.fifgroup.core.finder.FIFAppsFinder;
import co.id.fifgroup.core.service.ErrorLogServiceAdapater;
import co.id.fifgroup.core.service.InvoiceNumberService;
import co.id.fifgroup.core.util.DateUtil;

@Transactional
public abstract class AbstractFIFAppsService{
	
	/**
	 * 
	 */
	@Autowired
	public FIFAppsFinder fifappsFinder;
	
	@Autowired
	protected InvoiceNumberService invoiceNumberService;
	
	@Autowired
	protected ErrorLogServiceAdapater errorLogService;
	
	public String createInvoice(InvoiceInterface invoiceInterface) {
		//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
				if(checkNullSegment2(invoiceInterface.getBranchDestination()) == null)
					throw new FIFAppsInterfaceException("GL Code was inactive");
				//and add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
		ModuleErrorLogDTO errorLog = new ModuleErrorLogDTO();
		errorLog.setLogTime(new Date());
		errorLog.setModuleName("FIFAPPS");
		
		//if(invoiceInterface.getInvoiceType() == null && invoiceInterface.getPaidByHo() == null){
			List<String> supplierTypes = fifappsFinder.getSupplierTypeBySupplierCode(invoiceInterface.getSupplierCode());
			String supplierType = "";
			
			if(!supplierTypes.isEmpty())
				supplierType = supplierTypes.get(0);
			
			if(supplierType.equals(SupplierType.K.toString())){
				
				checkApMstEmployee(supplierType, invoiceInterface.getSupplierCode(), invoiceInterface.getTransactionType(), invoiceInterface.getCompanyCode());
				
				if(invoiceInterface.getInvoiceType() == null)
					invoiceInterface.setInvoiceType("N");
				if(invoiceInterface.getPaidByHo() == null)
					invoiceInterface.setPaidByHo("Y");
				
			}else if(supplierType.equals(SupplierType.S.toString())){
				
				if(invoiceInterface.getSupplierAccountNumber() == null){
					
					BankOdInfo bankOdInfo = fifappsFinder.getBankOdInfoByBranchAndDivision(invoiceInterface.getBranchCode(), invoiceInterface.getCompanyCode());
					
					if(bankOdInfo.getBankId() == null) {
						String errorMessage = "Cannot find Bank OD, Branch/COY : "+invoiceInterface.getBranchCode()+"/"+invoiceInterface.getCompanyCode();
						
						errorLog.setErrorMessage(invoiceInterface.getTransactionType().concat(" || ").concat(invoiceInterface.getEmployeeNumber()).concat(" - ")
								.concat(errorMessage));
						errorLogService.insertErrorLog(errorLog);
						
						throw new FIFAppsInterfaceException(errorMessage);
					}
					else{
						invoiceInterface.setSupplierBankCode(bankOdInfo.getBankId());
						invoiceInterface.setSupplierAccountNumber(bankOdInfo.getAccountNumber());
						invoiceInterface.setSupplierAccountName(bankOdInfo.getAccountName());
					}
					
					if(invoiceInterface.getInvoiceType() == null)
						invoiceInterface.setInvoiceType("L");
					if(invoiceInterface.getPaidByHo() == null)
						invoiceInterface.setPaidByHo("N");
					
				}else{
					
					int count = fifappsFinder.countSupplierAccount(invoiceInterface.getSupplierCode(), invoiceInterface.getSupplierBankCode(), invoiceInterface.getSupplierAccountNumber());
					if(count == 0) {
						String errorMessage = "Supplier account is not found, supplier code : "+invoiceInterface.getSupplierCode()+" Bank Code : "+invoiceInterface.getSupplierBankCode()+" Account Number "+invoiceInterface.getSupplierAccountNumber();
						
						errorLog.setErrorMessage(invoiceInterface.getTransactionType().concat(" || ").concat(invoiceInterface.getEmployeeNumber()).concat(" - ")
								.concat(errorMessage));
						errorLogService.insertErrorLog(errorLog);
						
						throw new FIFAppsInterfaceException(errorMessage);
					}
				
					if(invoiceInterface.getInvoiceType() == null)
						invoiceInterface.setInvoiceType("N");
					if(invoiceInterface.getPaidByHo() == null)
						invoiceInterface.setPaidByHo("Y");
					
				}
				
			}else if(supplierType.equals(SupplierType.B.toString())){
				
				invoiceInterface.setSupplierCode(invoiceInterface.getEmployeeNumber());
				
				BankOdInfo bankOdInfo = fifappsFinder.getBankOdInfoByBranchAndDivision(invoiceInterface.getBranchCode(), invoiceInterface.getCompanyCode());
				if(invoiceInterface.getSupplierBankCode() == null) {
					String errorMessage = "Cannot find Bank OD, Branch/COY : "+invoiceInterface.getBranchCode()+"/"+invoiceInterface.getCompanyCode();
					
					errorLog.setErrorMessage(invoiceInterface.getTransactionType().concat(" || ").concat(invoiceInterface.getEmployeeNumber()).concat(" - ")
							.concat(errorMessage));
					errorLogService.insertErrorLog(errorLog);
					
					throw new FIFAppsInterfaceException(errorMessage);
				}
				else{
					invoiceInterface.setSupplierBankCode(bankOdInfo.getBankId());
					invoiceInterface.setSupplierAccountNumber(bankOdInfo.getAccountNumber());
					invoiceInterface.setSupplierAccountName(bankOdInfo.getAccountName());
				}
				
				if(invoiceInterface.getInvoiceType() == null)
					invoiceInterface.setInvoiceType("L");
				if(invoiceInterface.getPaidByHo() == null)
					invoiceInterface.setPaidByHo("N");
				
			}else {
				String errorMessage = "Supplier type is not Karyawan (K), Supplier (S) or BOD (B)";
				
				errorLog.setErrorMessage(invoiceInterface.getTransactionType().concat(" || ").concat(invoiceInterface.getEmployeeNumber()).concat(" - ")
						.concat(errorMessage));
				errorLogService.insertErrorLog(errorLog);
				
				throw new FIFAppsInterfaceException(errorMessage);
			}
		//}
		
		
		String cvrOutMessage = null;
		CVR cvr = new CVR();
		
		cvr.setSegment1(invoiceInterface.getCompanyGlCode());
		cvr.setSegment2(invoiceInterface.getBranchDestination());
		cvr.setSegment3(invoiceInterface.getCostCenter());
		cvr.setSegment4(invoiceInterface.getClassCode());
		cvr.setSegment5(invoiceInterface.getSegmentBank());
		cvr.setSegment6(invoiceInterface.getProductCode());
		cvr.setSegment7(invoiceInterface.getSegmentIb());
		cvr.setSegment8(invoiceInterface.getSegmentFuture());
		
		fifappsFinder.checkCvrGlNotNull(cvr);
		cvrOutMessage = cvr.getMessage();
		int cvrResult = cvr.getReturnValue();
		if(cvrResult != 0) {
			String errorMessage = "Cannot validate CVR "+cvr.getSegment1()+"-"+cvr.getSegment2()+"-"+cvr.getSegment3()+"-"+cvr.getSegment4()+"-"+cvr.getSegment5()+"-"+cvr.getSegment6()+"-"+cvr.getSegment7()+"-"+cvr.getSegment8()+" message out "+cvrOutMessage;
			
			errorLog.setErrorMessage(invoiceInterface.getTransactionType().concat(" || ").concat(invoiceInterface.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
		
		String invoiceNumber = null;
		
		if(invoiceInterface.getInvoiceNumber() != null){
			invoiceNumber = invoiceInterface.getInvoiceNumber();
		}else{
			InvoiceNumber invNo = new InvoiceNumber();
			invNo.setBranchCode(invoiceInterface.getBranchCode());
			invoiceNumberService.getInvoiceNumber(invNo);
			invoiceNumber = invNo.getInvoiceNumber();
		}
		
		invoiceInterface.setInvoiceNumber(invoiceNumber);
		if(!invoiceNumber.substring(0, 5).equals(invoiceInterface.getBranchCode())) {
			String errorMessage = "Error get Invoice no "+invoiceNumber;
			
			errorLog.setErrorMessage(invoiceInterface.getTransactionType().concat(" || ").concat(invoiceInterface.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
		fifappsFinder.insertInvoiceInterface(invoiceInterface);
		String invResult = invoiceInterface.getReturnValue();
		if(!invResult.equals(invoiceNumber)) {
			String errorMessage = "Error run interface invoice_interface "+invResult;
			
			errorLog.setErrorMessage(invoiceInterface.getTransactionType().concat(" || ").concat(invoiceInterface.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
		
		PreInvoiceInterface preInvoiceInterface = new PreInvoiceInterface();
		preInvoiceInterface.setInvoiceNumber(invoiceNumber);
		preInvoiceInterface.setSpsPrepayment(invoiceInterface.getSpsPrepayment());
		preInvoiceInterface.setForceApprover(invoiceInterface.getForceApprover());
		fifappsFinder.insertPreInvoiceInterface(preInvoiceInterface);
		String preInvResult = preInvoiceInterface.getReturnValue();
		if(!preInvResult.equals(invoiceNumber)) {
			String errorMessage = "Error interface insert_invoice_interface "+preInvResult;
			
			errorLog.setErrorMessage(invoiceInterface.getTransactionType().concat(" || ").concat(invoiceInterface.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
		
		Approver approver = new Approver();
		approver.setBranchCode(invoiceInterface.getBranchCode());
		approver.setInvoiceNumber(invoiceNumber);
		approver.setSequenceApproverNumber(invoiceInterface.getSequenceNumber());
		approver.setApprover(invoiceInterface.getCreatedBy());
		approver.setApproveDate(invoiceInterface.getDueDate());
		fifappsFinder.insertApprover(approver);
		
		return invoiceNumber;
	}
	
	public String createRV(Prepayment prepayment, String userId){
		Rv rv = new Rv();
		rv.setPrepayment(prepayment);
		rv.setCreatedBy(userId);
		fifappsFinder.createRv(rv);
		return rv.getRvNumber();
		
	}
	
	protected void checkApMstEmployee(String supplierType, String supplierCode, String transactionType, String companyCode) {
		
		ModuleErrorLogDTO errorLog = new ModuleErrorLogDTO();
		errorLog.setLogTime(new Date());
		errorLog.setModuleName("FIFAPPS");
		
		if(supplierType.equals(SupplierType.K.toString())){
			SupplierOpexInfo supplierOpexInfo = fifappsFinder.getSupplierOpexByOpexCodeAndOpexType(supplierCode, companyCode);
			
			if(supplierOpexInfo != null){
				
				EmployeeBankInfo employeeBankInfo = new EmployeeBankInfo();
				employeeBankInfo.setBankId(supplierOpexInfo.getOpexBankCode());
				employeeBankInfo.setAccountNumber(supplierOpexInfo.getOpexAccountNumber());
				employeeBankInfo.setAccountName(supplierOpexInfo.getOpexAccountName());
				employeeBankInfo.setBankName(supplierOpexInfo.getOpexBankName());
				employeeBankInfo.setEmployeeCode(supplierOpexInfo.getOpexCode());
				
				int result = fifappsFinder.countApMstEmployeeByEmployeeCode(supplierOpexInfo.getOpexCode());
				
				if(result > 0){
					try {
						fifappsFinder.updateApMstEmployeeByEmployeeCode(employeeBankInfo);
					} catch (Exception e) {
						String errorMessage = "Cannot validate ap_mst_emp, employee number "+supplierCode;
						
						errorLog.setErrorMessage(transactionType.concat(" || ").concat(supplierCode).concat(" - ")
								.concat(errorMessage));
						errorLogService.insertErrorLog(errorLog);
						
						throw new FIFAppsInterfaceException(errorMessage);
					}
					
					
				}else{
					
					employeeBankInfo.setAccountDescription("Karyawan");
					employeeBankInfo.setAccountStatus("A");
					try {
						fifappsFinder.insertApMstEmployee(employeeBankInfo);
					} catch (Exception e) {
						String errorMessage = "Cannot validate ap_mst_emp, employee number "+supplierCode;
						
						errorLog.setErrorMessage(transactionType.concat(" || ").concat(supplierCode).concat(" - ")
								.concat(errorMessage));
						errorLogService.insertErrorLog(errorLog);
						
						throw new FIFAppsInterfaceException(errorMessage);
					}
					
					
				}
				
			}else {
				String errorMessage = "Cannot validate ap_mst_emp, employee number "+supplierCode;
				
				errorLog.setErrorMessage(transactionType.concat(" || ").concat(supplierCode).concat(" - ")
						.concat(errorMessage));
				errorLogService.insertErrorLog(errorLog);
			
				throw new FIFAppsInterfaceException(errorMessage);
				
			}
		}
		
		
	}
	
	protected void validateMutasiInterface(MutationInterface mutationInterface) {
		if(mutationInterface.getPrepaymentAmount().compareTo(BigDecimal.ZERO) > 0){
			Long glBookId = fifappsFinder.getGLBookId();
			
			if(mutationInterface.getCompanyCodeNew().equals(mutationInterface.getCompanyCodeOld())){ //if companyOld == companyNew
				if(!mutationInterface.getLocationIdNewCode().equals(mutationInterface.getLocationIdOldCode())){ //if branch old != branch new
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
					
					
					//debet branch new
					GlInterface glInterfaceNew = new GlInterface();
					glInterfaceNew.setStatus("NEW");
					glInterfaceNew.setSetOfBookId(glBookId);
					glInterfaceNew.setAccountingDate(DateUtil.truncate(new Date()));
					glInterfaceNew.setTransactionDate(DateUtil.truncate(new Date()));
					glInterfaceNew.setPeriodName(DateUtil.format(new Date(), "MMM-yy").toString());
					glInterfaceNew.setCurrencyCode(mutationInterface.getCurrencyCode());
					glInterfaceNew.setDateCreated(new Date());
					glInterfaceNew.setCreatedBy(mutationInterface.getEmployeeNumber());
					glInterfaceNew.setActualFlag("A");
					glInterfaceNew.setUserJeCategoryName("Sentralisasi Opex");
					glInterfaceNew.setUserJeSourceName("FIFAPPS");
					glInterfaceNew.setSegment1(mutationInterface.getCompanyCodeNew());
					glInterfaceNew.setSegment2(mutationInterface.getLocationIdNewCode());
					glInterfaceNew.setSegment3(mutationInterface.getCostCenterNew());
					glInterfaceNew.setSegment4(mutationInterface.getClassCode());
					glInterfaceNew.setSegment5(mutationInterface.getSupplierBankCode());
					glInterfaceNew.setSegment6(mutationInterface.getProductCode());
					glInterfaceNew.setSegment7(mutationInterface.getSegmentIb());
					glInterfaceNew.setSegment8(mutationInterface.getSegmentFuture());
					glInterfaceNew.setEnteredDr(mutationInterface.getPrepaymentAmount());
					glInterfaceNew.setEnteredCr(BigDecimal.ZERO);
					glInterfaceNew.setAccountedDr(mutationInterface.getPrepaymentAmount());
					glInterfaceNew.setAccountedCr(BigDecimal.ZERO);
					glInterfaceNew.setReference1(mutationInterface.getLocationIdNewCode()+"/"+sdf.format(new Date()));

					glInterfaceNew.setReference4(mutationInterface.getClaimNumber());
					glInterfaceNew.setReference10("Mutasi Prepayment NPK "+mutationInterface.getEmployeeNumber()+"-"+mutationInterface.getEmployeeName()+"-"+mutationInterface.getClaimNumber());
					glInterfaceNew.setAttribute4(mutationInterface.getEmployeeNumber());
					glInterfaceNew.setAttribute5(mutationInterface.getLocationIdOldCode()+""+DateUtil.format(mutationInterface.getTransferDate(), "ddmmyyyy")+"TR"+mutationInterface.getEmployeeNumber());
					
					
					fifappsFinder.insertGlInterface(glInterfaceNew);
					
					
					//credit branch old
					GlInterface glInterfaceOld = new GlInterface();
					glInterfaceOld.setStatus("NEW");
					glInterfaceOld.setSetOfBookId(glBookId);
					glInterfaceOld.setAccountingDate(DateUtil.truncate(new Date()));
					glInterfaceOld.setTransactionDate(DateUtil.truncate(new Date()));
					glInterfaceOld.setPeriodName(DateUtil.format(new Date(), "MMM-yy").toString());
					glInterfaceOld.setCurrencyCode(mutationInterface.getCurrencyCode());
					glInterfaceOld.setDateCreated(new Date());
					glInterfaceOld.setCreatedBy(mutationInterface.getEmployeeNumber());
					glInterfaceOld.setActualFlag("A");
					glInterfaceOld.setUserJeCategoryName("Sentralisasi Opex");
					glInterfaceOld.setUserJeSourceName("FIFAPPS");
					glInterfaceOld.setSegment1(mutationInterface.getCompanyCodeOld());
					glInterfaceOld.setSegment2(mutationInterface.getLocationIdOldCode());
					glInterfaceOld.setSegment3(mutationInterface.getCostCenterOld());
					glInterfaceOld.setSegment4(mutationInterface.getClassCode());
					glInterfaceOld.setSegment5(mutationInterface.getSupplierBankCode());
					glInterfaceOld.setSegment6(mutationInterface.getProductCode());
					glInterfaceOld.setSegment7(mutationInterface.getSegmentIb());
					glInterfaceOld.setSegment8(mutationInterface.getSegmentFuture());
					glInterfaceOld.setEnteredDr(BigDecimal.ZERO);
					glInterfaceOld.setEnteredCr(mutationInterface.getPrepaymentAmount());
					glInterfaceOld.setAccountedDr(BigDecimal.ZERO);
					glInterfaceOld.setAccountedCr(mutationInterface.getPrepaymentAmount());
					glInterfaceOld.setReference1(mutationInterface.getLocationIdOldCode()+"/"+sdf.format(new Date()));
					glInterfaceOld.setReference4(mutationInterface.getClaimNumber());
					glInterfaceOld.setReference10("Mutasi Prepayment NPK "+mutationInterface.getEmployeeNumber()+"-"+mutationInterface.getEmployeeName()+"-"+mutationInterface.getClaimId()+""+mutationInterface.getClaimNumber());
					glInterfaceOld.setAttribute4(mutationInterface.getEmployeeNumber());
					glInterfaceOld.setAttribute5(mutationInterface.getLocationIdOldCode()+""+DateUtil.format(mutationInterface.getTransferDate(), "ddmmyyyy")+"TR"+mutationInterface.getEmployeeNumber());
					
					
					fifappsFinder.insertGlInterface(glInterfaceOld);
					
				}
			}
			
			/*if(!mutationInterface.getCompanyCodeNew().equals(mutationInterface.getCompanyCodeOld())){
				
				ModuleErrorLogDTO errorLog = new ModuleErrorLogDTO();
				errorLog.setLogTime(new Date());
				errorLog.setModuleName("FIFAPPS");
								
				//debet due from new company dr
				GlInterface glInterfaceNew = new GlInterface();
				fifappsFinder.insertGlInterface(glInterfaceNew);
				
				
				//Credit old branch
				GlInterface glInterfaceOld = new GlInterface();
				fifappsFinder.insertGlInterface(glInterfaceOld);
				
				//debet new branch
				GlInterface glInterfaceBranchNew = new GlInterface();
				fifappsFinder.insertGlInterface(glInterfaceBranchNew);
				
				//credit old branch
				GlInterface glInterfaceBranchOld = new GlInterface();
				fifappsFinder.insertGlInterface(glInterfaceBranchOld);
				
				//get invoice number
				String invoiceNumber = getInvoiceNumber(mutationInterface.getLocationIdNewCode());
				mutationInterface.setInvoiceNumber(invoiceNumber);
				
				if(!invoiceNumber.substring(0, 5).equals(mutationInterface.getLocationIdNewCode())) {
					String errorMessage = "Failed creating invoice number";
					
					errorLog.setErrorMessage(mutationInterface.getTransactionType().concat(" || ").concat(mutationInterface.getEmployeeNumber()).concat(" - ")
							.concat(errorMessage));
					errorLogService.insertErrorLog(errorLog);
					
					throw new FIFAppsInterfaceException(errorMessage);
				}
				
				InvoiceInterface invoiceInterface = new InvoiceInterface();
				
				//Insert invoice
				String invoiceResult = insertInvoiceInterface(invoiceInterface);
				if(!invoiceResult.equals(invoiceNumber)) {
					String errorMessage = "Failed creating invoice";
					
					errorLog.setErrorMessage(mutationInterface.getTransactionType().concat(" || ").concat(mutationInterface.getEmployeeNumber()).concat(" - ")
							.concat(errorMessage));
					errorLogService.insertErrorLog(errorLog);
					
					throw new FIFAppsInterfaceException(errorMessage);
				}
				
				String preInvResult = insertPreInvoiceInterface(invoiceNumber);
				if(!preInvResult.equals(invoiceNumber)) {
					String errorMessage = "Error insert preinvoice interface";
					
					errorLog.setErrorMessage(mutationInterface.getTransactionType().concat(" || ").concat(mutationInterface.getEmployeeNumber()).concat(" - ")
							.concat(errorMessage));
					errorLogService.insertErrorLog(errorLog);
					
					throw new FIFAppsInterfaceException(errorMessage);
				}
				
				insertApprover(mutationInterface.getLocationIdNewCode(), invoiceNumber, 1, mutationInterface.getEmployeeNumber(), DateUtil.truncate(new Date()));
				
				//check balance
				JournalBalance journalBalance = new JournalBalance();
				journalBalance.setInvoiceNumber(invoiceNumber);
				fifappsFinder.checkBalanceJurnal(journalBalance);
				int checkBalance = journalBalance.getResultValue();
				if(checkBalance == 1) {
					String errorMessage = "Error check balance";
					
					errorLog.setErrorMessage(mutationInterface.getTransactionType().concat(" || ").concat(mutationInterface.getEmployeeNumber()).concat(" - ")
							.concat(errorMessage));
					errorLogService.insertErrorLog(errorLog);
					
					throw new FIFAppsInterfaceException(errorMessage);
				}
				
				//update 
				fifappsFinder.updateApTrnSpcPrepayment(invoiceNumber, mutationInterface.getLocationIdNewCode(), mutationInterface.getCompanyCodeNew(), mutationInterface.getCompanyId());
			}*/
		}
	}
	
	protected String getInvoiceNumber(String branchCode){
		InvoiceNumber invNo = new InvoiceNumber();
		invNo.setBranchCode(branchCode);
		invoiceNumberService.getInvoiceNumber(invNo);
		return invNo.getInvoiceNumber();
	}
	
	protected String insertInvoiceInterface(InvoiceInterface invoiceInterface){
		fifappsFinder.insertInvoiceInterface(invoiceInterface);
		return invoiceInterface.getReturnValue();
	}
	
	protected String insertPreInvoiceInterface(String invoiceNumber){
		PreInvoiceInterface preInvoiceInterface = new PreInvoiceInterface();
		preInvoiceInterface.setInvoiceNumber(invoiceNumber);
		fifappsFinder.insertPreInvoiceInterface(preInvoiceInterface);
		return preInvoiceInterface.getReturnValue();
	}
	
	protected void insertApprover(String branchCode, String invoiceNumber, int sequenceApproverNum, String approver, Date approveDate){
		Approver ap = new Approver();
		ap.setBranchCode(branchCode);
		ap.setInvoiceNumber(invoiceNumber);
		ap.setSequenceApproverNumber(sequenceApproverNum);
		ap.setApprover(approver);
		ap.setApproveDate(approveDate);
		fifappsFinder.insertApprover(ap);
	}
	
	public void insertInvoice(InvoiceInterface invoice) {
		ModuleErrorLogDTO errorLog = new ModuleErrorLogDTO();
		errorLog.setLogTime(new Date());
		errorLog.setModuleName("FIFAPPS");

		if(invoice.getInvoiceType() == null && invoice.getPaidByHo() == null){
			
			List<String> supplierTypes = fifappsFinder.getSupplierTypeBySupplierCode(invoice.getSupplierCode());
			String supplierType = "";
			
			if(!supplierTypes.isEmpty())
				supplierType = supplierTypes.get(0);
			
			if(supplierType.equals(SupplierType.K.toString())){
				
				checkApMstEmployee(supplierType, invoice.getSupplierCode(), invoice.getTransactionType(), invoice.getCompanyCode());
				
				if(invoice.getInvoiceType() == null)
					invoice.setInvoiceType("N");
				if(invoice.getPaidByHo() == null)
					invoice.setPaidByHo("Y");
				
			}else if(supplierType.equals(SupplierType.S.toString())){
				
				if(invoice.getSupplierAccountNumber() == null){
					
					BankOdInfo bankOdInfo = fifappsFinder.getBankOdInfoByBranchAndDivision(invoice.getBranchCode(), invoice.getCompanyCode());
					
					if(bankOdInfo.getBankId() == null) {
						String errorMessage = "Cannot find Bank OD, Branch/COY : "+invoice.getBranchCode()+"/"+invoice.getCompanyCode();
						
						errorLog.setErrorMessage(invoice.getTransactionType().concat(" || ").concat(invoice.getEmployeeNumber()).concat(" - ")
								.concat(errorMessage));
						errorLogService.insertErrorLog(errorLog);
						
						throw new FIFAppsInterfaceException(errorMessage);
					}
					else{
						invoice.setSupplierBankCode(bankOdInfo.getBankId());
						invoice.setSupplierAccountNumber(bankOdInfo.getAccountNumber());
						invoice.setSupplierAccountName(bankOdInfo.getAccountName());
					}
					
					if(invoice.getInvoiceType() == null)
						invoice.setInvoiceType("L");
					if(invoice.getPaidByHo() == null)
						invoice.setPaidByHo("N");
					
				}else{
					
					int count = fifappsFinder.countSupplierAccount(invoice.getSupplierCode(), invoice.getSupplierBankCode(), invoice.getSupplierAccountNumber());
					if(count == 0) {
						String errorMessage = "Supplier account is not found, supplier code : "+invoice.getSupplierCode()+" Bank Code : "+invoice.getSupplierBankCode()+" Account Number "+invoice.getSupplierAccountNumber();
						
						errorLog.setErrorMessage(invoice.getTransactionType().concat(" || ").concat(invoice.getEmployeeNumber()).concat(" - ")
								.concat(errorMessage));
						errorLogService.insertErrorLog(errorLog);
						
						throw new FIFAppsInterfaceException(errorMessage);
					}
				
					if(invoice.getInvoiceType() == null)
						invoice.setInvoiceType("N");
					if(invoice.getPaidByHo() == null)
						invoice.setPaidByHo("Y");
					
				}
				
			}else if(supplierType.equals(SupplierType.B.toString())){
				
				invoice.setSupplierCode(invoice.getEmployeeNumber());
				
				BankOdInfo bankOdInfo = fifappsFinder.getBankOdInfoByBranchAndDivision(invoice.getBranchCode(), invoice.getCompanyCode());
				if(invoice.getSupplierBankCode() == null) {
					String errorMessage = "Cannot find Bank OD, Branch/COY : "+invoice.getBranchCode()+"/"+invoice.getCompanyCode();
					
					errorLog.setErrorMessage(invoice.getTransactionType().concat(" || ").concat(invoice.getEmployeeNumber()).concat(" - ")
							.concat(errorMessage));
					errorLogService.insertErrorLog(errorLog);
					
					throw new FIFAppsInterfaceException(errorMessage);
				}
				else{
					invoice.setSupplierBankCode(bankOdInfo.getBankId());
					invoice.setSupplierAccountNumber(bankOdInfo.getAccountNumber());
					invoice.setSupplierAccountName(bankOdInfo.getAccountName());
				}
				
				if(invoice.getInvoiceType() == null)
					invoice.setInvoiceType("L");
				if(invoice.getPaidByHo() == null)
					invoice.setPaidByHo("N");
				
			}else {
				String errorMessage = "Supplier type is not Karyawan (K), Supplier (S) or BOD (B)";
				
				errorLog.setErrorMessage(invoice.getTransactionType().concat(" || ").concat(invoice.getEmployeeNumber()).concat(" - ")
						.concat(errorMessage));
				errorLogService.insertErrorLog(errorLog);
				
				throw new FIFAppsInterfaceException(errorMessage);
			}
		}
		
		
		String cvrOutMessage = null;
		CVR cvr = new CVR();
		//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 14-July-2016
		if(checkNullSegment2(invoice.getBranchDestination()) == null)
			throw new FIFAppsInterfaceException("GL Code was inactive");
		//and add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 14-July-2016
		cvr.setSegment1(invoice.getCompanyGlCode());
		cvr.setSegment2(invoice.getBranchDestination());
		cvr.setSegment3(invoice.getCostCenter());
		cvr.setSegment4(invoice.getClassCode());
		cvr.setSegment5(invoice.getSegmentBank());
		cvr.setSegment6(invoice.getProductCode());
		cvr.setSegment7(invoice.getSegmentIb());
		cvr.setSegment8(invoice.getSegmentFuture());
		
		fifappsFinder.checkCvrGlNotNull(cvr);
		cvrOutMessage = cvr.getMessage();
		int cvrResult = cvr.getReturnValue();
		if(cvrResult != 0) {
			String errorMessage = "Cannot validate CVR "+cvr.getSegment1()+"-"+cvr.getSegment2()+"-"+cvr.getSegment3()+"-"+cvr.getSegment4()+"-"+cvr.getSegment5()+"-"+cvr.getSegment6()+"-"+cvr.getSegment7()+"-"+cvr.getSegment8()+" message out "+cvrOutMessage;
			
			errorLog.setErrorMessage(invoice.getTransactionType().concat(" || ").concat(invoice.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
		
		invoice.setInvoiceNumber(invoice.getInvoiceNumber());
		if(!invoice.getInvoiceNumber().substring(0, 5).equals(invoice.getBranchCode())) {
			String errorMessage = "Error get Invoice no "+invoice.getInvoiceNumber();
			
			errorLog.setErrorMessage(invoice.getTransactionType().concat(" || ").concat(invoice.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
		fifappsFinder.insertInvoiceInterface(invoice);
		String invResult = invoice.getReturnValue();
		if(!invResult.equals(invoice.getInvoiceNumber())) {
			String errorMessage = "Error run interface invoice_interface "+invResult;
			
			errorLog.setErrorMessage(invoice.getTransactionType().concat(" || ").concat(invoice.getEmployeeNumber()).concat(" - ")
					.concat(errorMessage));
			errorLogService.insertErrorLog(errorLog);
			
			throw new FIFAppsInterfaceException(errorMessage);
		}
	}
	
	//add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
	public String checkNullSegment2(String segment2){
		return fifappsFinder.checkNullSegment2(segment2);
	}
	//end add by PMW, ticket 16062418073379 - Perbaikan interface invoice untuk gl inactive, 13-July-2016
	
	protected void validateMutasiInterfaceTransfer(MutationInterface mutationInterface) {
		if(mutationInterface.getPrepaymentAmount().compareTo(BigDecimal.ZERO) > 0){
			Long glBookId = fifappsFinder.getGLBookId();
			
			if(mutationInterface.getCompanyCodeNew().equals(mutationInterface.getCompanyCodeOld())){ //if companyOld == companyNew
				if(!mutationInterface.getLocationIdNewCode().equals(mutationInterface.getLocationIdOldCode())){ //if branch old != branch new
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
					
					//debet branch new
					GlInterface glInterfaceNew = new GlInterface();
					glInterfaceNew.setStatus("NEW");
					glInterfaceNew.setSetOfBookId(glBookId);
					glInterfaceNew.setAccountingDate(DateUtil.truncate(new Date()));
					glInterfaceNew.setTransactionDate(DateUtil.truncate(new Date()));
					glInterfaceNew.setPeriodName(DateUtil.format(new Date(), "MMM-yy").toString());
					glInterfaceNew.setCurrencyCode(mutationInterface.getCurrencyCode());
					glInterfaceNew.setDateCreated(new Date());
					glInterfaceNew.setCreatedBy(mutationInterface.getEmployeeNumber());
					glInterfaceNew.setActualFlag("A");
					glInterfaceNew.setUserJeCategoryName("Sentralisasi Opex");
					glInterfaceNew.setUserJeSourceName("FIFAPPS");
					//BEGIN - 14091116521937 - [Bug Fixing] Perbaikan Journal mutation Promotion Transfer
					//remark by MRK
					//glInterfaceNew.setSegment1(mutationInterface.getCompanyCodeNew());
					//added by MRK
					glInterfaceNew.setSegment1(mutationInterface.getGlCodeNew()); //digit1=companycode, digit2:konventional/syariah
					//END - 14091116521937 - [Bug Fixing] Perbaikan Journal mutation Promotion Transfer
					glInterfaceNew.setSegment2(mutationInterface.getLocationIdNewCode());
					glInterfaceNew.setSegment3(mutationInterface.getCostCenterNew());
					glInterfaceNew.setSegment4(mutationInterface.getClassCode());
					glInterfaceNew.setSegment5(mutationInterface.getSupplierBankCode());
					glInterfaceNew.setSegment6(mutationInterface.getProductCode());
					glInterfaceNew.setSegment7(mutationInterface.getSegmentIb());
					glInterfaceNew.setSegment8(mutationInterface.getSegmentFuture());
					glInterfaceNew.setEnteredDr(mutationInterface.getPrepaymentAmount());
					glInterfaceNew.setEnteredCr(BigDecimal.ZERO);
					glInterfaceNew.setAccountedDr(mutationInterface.getPrepaymentAmount());
					glInterfaceNew.setAccountedCr(BigDecimal.ZERO);
					//BEGIN - 14091116521937 - [Bug Fixing] Perbaikan Journal mutation Promotion Transfer
					//remark by MRK
					/*glInterfaceNew.setReference1(mutationInterface.getLocationIdNewCode()+"/"+sdf.format(new Date()));
					glInterfaceNew.setReference4(mutationInterface.getInvoiceNumber());*/
					
					//added by MRK: reference1, reference4 harus sama antara debit dan credit
					glInterfaceNew.setReference1(mutationInterface.getLocationIdOldCode()+"/"+sdf.format(new Date()));
					glInterfaceNew.setReference4(mutationInterface.getLocationIdOldCode()+""+DateUtil.format(mutationInterface.getTransferDate(), "ddmmyyyy")+"TR"+mutationInterface.getEmployeeNumber());
					//END - 14091116521937 - [Bug Fixing] Perbaikan Journal mutation Promotion Transfer
					glInterfaceNew.setReference10("Mutasi Prepayment NPK "+mutationInterface.getEmployeeNumber()+"-"+mutationInterface.getEmployeeName()+"-"+mutationInterface.getClaimNumber());
					glInterfaceNew.setAttribute4(mutationInterface.getEmployeeNumber());
					glInterfaceNew.setAttribute5(mutationInterface.getLocationIdOldCode()+""+DateUtil.format(mutationInterface.getTransferDate(), "ddmmyyyy")+"TR"+mutationInterface.getEmployeeNumber());
					
					
					fifappsFinder.insertGlInterface(glInterfaceNew);
					
					//credit branch old
					GlInterface glInterfaceOld = new GlInterface();
					glInterfaceOld.setStatus("NEW");
					glInterfaceOld.setSetOfBookId(glBookId);
					glInterfaceOld.setAccountingDate(DateUtil.truncate(new Date()));
					glInterfaceOld.setTransactionDate(DateUtil.truncate(new Date()));
					glInterfaceOld.setPeriodName(DateUtil.format(new Date(), "MMM-yy").toString());
					glInterfaceOld.setCurrencyCode(mutationInterface.getCurrencyCode());
					glInterfaceOld.setDateCreated(new Date());
					glInterfaceOld.setCreatedBy(mutationInterface.getEmployeeNumber());
					glInterfaceOld.setActualFlag("A");
					glInterfaceOld.setUserJeCategoryName("Sentralisasi Opex");
					glInterfaceOld.setUserJeSourceName("FIFAPPS");
					//BEGIN - 14091116521937 - [Bug Fixing] Perbaikan Journal mutation Promotion Transfer - by MRK
					//remark by: MRK
					//glInterfaceOld.setSegment1(mutationInterface.getCompanyCodeOld());
					//added by: MRK
					glInterfaceOld.setSegment1(mutationInterface.getGlCodeOld());
					//END - 14091116521937 - [Bug Fixing] Perbaikan Journal mutation Promotion Transfer - by MRK
					
					glInterfaceOld.setSegment2(mutationInterface.getLocationIdOldCode());
					glInterfaceOld.setSegment3(mutationInterface.getCostCenterOld());
					glInterfaceOld.setSegment4(mutationInterface.getClassCode());
					glInterfaceOld.setSegment5(mutationInterface.getSupplierBankCode());
					glInterfaceOld.setSegment6(mutationInterface.getProductCode());
					glInterfaceOld.setSegment7(mutationInterface.getSegmentIb());
					glInterfaceOld.setSegment8(mutationInterface.getSegmentFuture());
					glInterfaceOld.setEnteredDr(BigDecimal.ZERO);
					glInterfaceOld.setEnteredCr(mutationInterface.getPrepaymentAmount());
					glInterfaceOld.setAccountedDr(BigDecimal.ZERO);
					glInterfaceOld.setAccountedCr(mutationInterface.getPrepaymentAmount());
					glInterfaceOld.setReference1(mutationInterface.getLocationIdOldCode()+"/"+sdf.format(new Date()));
					//BEGIN - 14091116521937 - [Bug Fixing] Perbaikan Journal mutation Promotion Transfer
					//remark by MRK
					//glInterfaceOld.setReference4(mutationInterface.getInvoiceNumber());
					//added by MRK: reference4 tidak menggunakan noInvoice request benefit lagi untuk menghindari kebingungan tim accounting.
					glInterfaceOld.setReference4(mutationInterface.getLocationIdOldCode()+""+DateUtil.format(mutationInterface.getTransferDate(), "ddmmyyyy")+"TR"+mutationInterface.getEmployeeNumber());
					//END - 14091116521937 - [Bug Fixing] Perbaikan Journal mutation Promotion Transfer
					glInterfaceOld.setReference10("Mutasi Prepayment NPK "+mutationInterface.getEmployeeNumber()+"-"+mutationInterface.getEmployeeName()+"-"+mutationInterface.getClaimId()+""+mutationInterface.getClaimNumber());
					glInterfaceOld.setAttribute4(mutationInterface.getEmployeeNumber());
					glInterfaceOld.setAttribute5(mutationInterface.getLocationIdOldCode()+""+DateUtil.format(mutationInterface.getTransferDate(), "ddmmyyyy")+"TR"+mutationInterface.getEmployeeNumber());
					
					
					fifappsFinder.insertGlInterface(glInterfaceOld);
					
					fifappsFinder.updateApTrnSpcPrepayment(mutationInterface.getInvoiceNumber(), mutationInterface.getLocationIdNewCode(), mutationInterface.getCompanyCodeNew(), mutationInterface.getCompanyCodeNew());
				}
			}
		}
	}
}
