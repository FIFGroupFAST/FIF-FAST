package co.id.fifgroup.core.finder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.interfacing.Approver;
import co.id.fifgroup.core.domain.interfacing.BankOdInfo;
import co.id.fifgroup.core.domain.interfacing.CVR;
import co.id.fifgroup.core.domain.interfacing.DepositHistoryInterface;
import co.id.fifgroup.core.domain.interfacing.DepositInterface;
import co.id.fifgroup.core.domain.interfacing.DepositNumber;
import co.id.fifgroup.core.domain.interfacing.EmployeeBankInfo;
import co.id.fifgroup.core.domain.interfacing.GlInterface;
import co.id.fifgroup.core.domain.interfacing.HutangKoperasi;
import co.id.fifgroup.core.domain.interfacing.InvoiceDetail;
import co.id.fifgroup.core.domain.interfacing.InvoiceInterface;
import co.id.fifgroup.core.domain.interfacing.InvoiceNumber;
import co.id.fifgroup.core.domain.interfacing.IuranKoperasi;
import co.id.fifgroup.core.domain.interfacing.JournalBalance;
import co.id.fifgroup.core.domain.interfacing.PreInvoiceInterface;
import co.id.fifgroup.core.domain.interfacing.Prepayment;
import co.id.fifgroup.core.domain.interfacing.PrepaymentSettlement;
import co.id.fifgroup.core.domain.interfacing.Rv;
import co.id.fifgroup.core.domain.interfacing.RvInterface;
import co.id.fifgroup.core.domain.interfacing.SupplierOpexInfo;
import co.id.fifgroup.core.domain.interfacing.TaxInterface;
import co.id.fifgroup.core.dto.BudgetActivityLogDTO;


public interface FIFAppsDummyFinder {
	
	public List<String> getSupplierTypeBySupplierCode(String supplierCode);
	/**
	 * Get active employee bank information from pea_bank_informations where employeeNumber = opexCode
	 * 
	 * @param opexCode
	 * @return
	 */
	public SupplierOpexInfo getSupplierOpexByOpexCodeAndOpexType(@Param("opexCode")String opexCode);
	
	/**
	 * check employee from ap.ap_mst_employee@fifapps.us.oracle.com
	 * 
	 * @param employeeCode
	 * @return
	 */
	public int countApMstEmployeeByEmployeeCode(@Param("employeeCode")String employeeCode);
	
	/**
	 * update 
	 * empl_bank_id, 
	 * empl_account_no, 
	 * empl_account_name 
	 * and empl_bank_name 
	 * from ap.ap_mst_employee@fifapps.us.oracle.com where empl_code = employeeBankInfo.getOpexCode()
	 * 
	 * @param employeeBankInfo
	 * @return
	 */
	public int updateApMstEmployeeByEmployeeCode(EmployeeBankInfo employeeBankInfo);
	
	
	/**
	 * insert into ap.ap_mst_employee@fifapps.us.oracle.com
	 * @param employeeBankInfo
	 * @return
	 */
	public int insertApMstEmployee(EmployeeBankInfo employeeBankInfo);
	
	/**
	 * select from ap.ap_bank_od_branch_v@fifapps.us.oracle.com
	 * 
	 * @param branchCode
	 * @param division
	 * @return
	 */
	public BankOdInfo getBankOdInfoByBranchAndDivision(@Param("branchCode")String branchCode, @Param("division")String division);
	
	/**
	 * count from fifapps.fs_mst_supplier_acct@fifapps.us.oracle.com 
	 * @param opexCode
	 * @param bankId
	 * @param accountNumber
	 * @return
	 */
	public int countSupplierAccount(@Param("opexCode")String opexCode, @Param("bankId")String bankId, @Param("accountNumber")String accountNumber);
	
	/**
	 * check from fs_fif_process_pkg.check_cvr_gl_notnull@fifapps.us.oracle.com
	 * 
	 * @param cvr
	 * @param outMsg
	 * @return 1 if success and 0 if failed
	 */
	public void checkCvrGlNotNull(CVR cvr);
	
	/**
	 * get invoice number from ap.get_invoice_no@fifapps.us.oracle.com
	 * @param branchCode
	 * @return substr(branchCode,1,5)
	 */
	public void getInvoiceNumber(InvoiceNumber invoiceNumber);
	
	/**
	 * get invoice number from ap.get_invoice_no@fifapps.us.oracle.com
	 * @param branchCode
	 * @return substr(branchCode,1,5)
	 */
	public void getDepositNumberFunction(DepositNumber depositNumber);
	
	/**
	 * call function ap.interface.invoice_interface@fifapps.us.oracle.com
	 * @param invoiceInterface
	 * @return
	 */
	public void insertInvoiceInterface(InvoiceInterface invoiceInterface);
	
	/**
	 * insert into ap.interface.insert_preinvoice_interface@fifapps.us.oracle.com
	 * 
	 * @param invoiceNo
	 * @return invoiceNo
	 */
	public void insertPreInvoiceInterface(PreInvoiceInterface preInvoiceInterface);
	
	/**
	 * insert into ap.ap_insert_approver@fifapps.us.oracle.com
	 * 
	 * @param branchCode
	 * @param invoiceNumber
	 * @param sequenceApproverNum
	 * @param approver
	 * @param approveDate
	 * @return
	 */
	public void insertApprover(Approver approver);
	
	/**
	 * select from ap.ap_trn_spc_prepay@fifapps.us.oracle.com where ap_invoice_no = invoiceNumber
	 * 
	 * @param claimNumber
	 * @param supplierCode
	 * @return
	 */
	public Prepayment getPrepaymentTransaction(@Param("invoiceNumber")String invoiceNumber);
	
	/**
	 * Select * from ap.ap_trn_spc_prepay where ap_reff_no = referenceNumber and ap_created_by = createdBy
	 * 
	 * @param referenceNumber
	 * @param createdBy
	 * @return
	 */
	public Prepayment getPrepaymentTransactionByReferenceNumber(@Param("referenceNumber")String referenceNumber, @Param("employeeNumber")String employeeNumber);
	
	/**
	 * call procedure ap.booking_rv_no@fifapps.us.oracle.com
	 * 
	 * @param branchCode
	 * @param userId
	 * @return
	 */
	public void bookRvNumber(Rv rv);
	
	/**
	 * call procedure ap.ap_spc_prepay.create_rv@fifapps.us.oracle.com
	 * @param prepaymentTransaction
	 * @param userId
	 * @param rvNumber
	 */
	public void createRv(Rv rv);
	
	/**
	 * Call function ap.ap_spc_prepay.settle_prepayment@fifapps.us.oracle.com
	 * @param prepInvoiceNumber
	 * @param prepInvoiceSeq
	 * @param invoiceNumber
	 * @param userId
	 * @param isCreateRv
	 * @param rvNumber
	 * @return
	 */
	public void settlePrepayment(PrepaymentSettlement prepaymentSettlement);
	
	/**
	 * select from fs_mst_param@fifapps.us.oracle.com where key = 'GL_BOOK_ID'
	 * @return
	 */
	public Long getGLBookId();
	
	/**
	 * insert into fifapps.gl_interface@fifapps.us.oracle.com
	 * @param mutationInterface
	 */
	public void insertGlInterface(GlInterface glInterface);
	
	/**
	 * Call function fifapps.fs_cek_bal_jurnal@fifapps.us.oracle.com
	 * 
	 * @param invoiceNumber
	 * @return 1 if error 0 sucess 
	 */
	public void checkBalanceJurnal(JournalBalance journalBalance);
	
	/**
	 * Update to ap.ap_trn_spc_prepay@fifapps.us.oracle.com
	 * @param invoiceNumber
	 * @param branchCodeNew
	 * @param companyCodeNew
	 * @param companyId
	 */
	public void updateApTrnSpcPrepayment(@Param("invoiceNumber")String invoiceNumber, @Param("branchCodeNew")String branchCodeNew, @Param("companyCodeNew")String companyCodeNew, @Param("companyId")Long companyId);
	
	/**
	 * call function ap.rv_interface_pkg.gen_rv_interface@fifapps.us.oracle.com
	 * 
	 * @param rvInterface
	 * @return
	 */
	//public String genRvInterface(RvInterface rvInterface);
	
	/**
	 * Insert into ap.ap_trn_spc_prepay
	 * 
	 * @param prepayment
	 */
	public void insertPrepayment(Prepayment prepayment);
	
	/**
	 * Update ap.ap_trn_spc_prepay set ap_amt_credit = prepaymentAmount, ap_updated_by = createdBy, ap_created_date = createdDate where ap_invoice_no = invoiceNumber
	 * 
	 * @param invoiceNumber
	 * @param prepaymentAmount
	 * @param createdBy
	 * @param createdDate
	 */
	public void creditApTrnSpcPrepayByInvoiceNumber(@Param("invoiceNumber")String invoiceNumber, @Param("prepaymentAmount")BigDecimal prepaymentAmount, @Param("createdBy")String createdBy, @Param("createdDate")Date createdDate);
	
	/**
	 * Insert into rv_trn_interface
	 * @param rvInterface
	 */
	public void genRvInterface(RvInterface rvInterface);
	
	/**
	 * 
	 * INSERT INTO ap.ap_trn_pph@fifapps.us.oracle.com
	 * 
	 */
	public void insertApTrnPPH(TaxInterface taxInterface);
	
	/**
	 * 
	 */
	public void updateTrnInvoiceDtl(InvoiceDetail invoiceDetail);
	
	/**
	 * ap.get_deposit_no@fifapps.us.oracle.com (branch_code, 5);

	 */
	public String getDepositNumber(@Param("branchCode")String branchCode, @Param("moduleId")Long moduleId); 
	
	/**
	 * insert into ap.rv_trn_deposit@fifapps
	 * 
	 * @param depositInterface
	 */
	public void insertTrnDeposit(DepositInterface depositInterface);
	
	/**
	 * Insert into ap.rv_trn_deposit_hist@fifapps
	 * @param depositHistoryInterface
	 */
	public void insertTrnDepositHistory(DepositHistoryInterface depositHistoryInterface);
	
	/**
	 * UPDATE cifn_empl_loan@fifapps
	 * 
	 * @param username
	 * @param employeeNumber
	 * @param contractNumber
	 */
	public void updateCifnEmplLoan(@Param("username")String username, @Param("employeeNumber")String employeeNumber, @Param("contractNumber")String contractNumber);
	
	/**
	 * UPDATE arec_insentif_temp@fifapps
	 * 
	 * @param processDate
	 * @param employeeNumber
	 */
	public void updateIntensifByEmployeeNumber(@Param("processDate")Date processDate, @Param("employeeNumber")String employeeNumber);
	
	public BigDecimal getInsentifByEmployeeNumber(@Param("employeeNumber") String employeeNumber);
	
	public List<IuranKoperasi> selectIuranKoperasiByCompanyId(@Param("companyId") Long companyId);
	
	public void updateIuranKoperasi(@Param("employeeNumber")String employeeNumber,@Param("savProdTypeId")String savProdTypeId,@Param("updatedBy")String updatedBy, @Param("savSequence")Integer savSequence, @Param("flagFif")String flagFif);
	
	public void updateHutangKoperasiByNpkAndContractNumber(HutangKoperasi hutangKoperasi);
	
	public List<HutangKoperasi> selectPotonganKoperasiByCompanyId(@Param("companyId") Long companyId);
	
	public void updateActivityCode(@Param("activityCode") String activityCode, @Param("invoiceNo") String invoiceNo);
	
	public void insertBudgetActivityLog(BudgetActivityLogDTO budgetActivityLog);
	
	public int getInvoiceSequenceByInvoiceNoAndBranch(@Param("invoiceNumber")String invoiceNumber, @Param("branchDestination")String branchDestination);
	
	public void updateActivityCodeDetail(@Param("activityCode") String activityCode, @Param("invoiceNo") String invoiceNo, @Param("seq") Integer seq, @Param("amount") BigDecimal amount);
}
