package co.id.fifgroup.personneladmin.batch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.Stream;
import co.id.fifgroup.core.validation.TaskExecutionException;
import co.id.fifgroup.personneladmin.constant.EmploymentCategory;
import co.id.fifgroup.personneladmin.constant.InterfaceBufferStatus;
import co.id.fifgroup.personneladmin.constant.PeopleType;
import co.id.fifgroup.personneladmin.dto.CsvToLdap;
import co.id.fifgroup.personneladmin.dto.HousEmployeeDTO;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.InterfaceBufferService;
import co.id.fifgroup.personneladmin.service.PeopleTypeService;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.personneladmin.util.SupervisorUtil;

import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.personneladmin.domain.InterfaceBuffer;
import co.id.fifgroup.personneladmin.domain.InterfaceBufferExample;

public class CreateCsvFileToLDAP extends ExecutableTask {

	@Override
	public void execute() throws TaskExecutionException {
		Long taskCompanyId = Long.parseLong(getParameter().get(StaticParameterKey.COMPANY_ID.name()).toString());
		String folderCsv = GlobalVariable.getGlobalSettingServiceAdapter().getGlobalSettingValueByCode("FOLDER_INTERFACE_LDAP");
		
		debug("Executing Create CSV File To LDAP");
		debug("Getting list of interface buffer data..");
		InterfaceBufferExample example = new InterfaceBufferExample();
		example.createCriteria().andAssignmentEffectiveDateLessThanOrEqualTo(DateUtil.add(DateUtil.truncate(new Date()), Calendar.DATE, 1))
			.andStatusNotEqualTo(InterfaceBufferStatus.COMPLETED.name())
			.andEmploymentCategoryIsNotNull();
		List<InterfaceBuffer> interfaceBuffers = getInterfaceBufferService().selectByExample(example);
		debug("interface buffer data " + interfaceBuffers.size() + " found");
		
		List<CsvToLdap> csvToLdaps = new ArrayList<CsvToLdap>();
		try {
			for (InterfaceBuffer interfaceBuffer : interfaceBuffers) {
				Stream stream = new Stream();
				stream.start();
				Long companyId = interfaceBuffer.getCompanyId() != null ? interfaceBuffer.getCompanyId() : taskCompanyId;
				PersonDTO person = getPersonService().getPersonalData(interfaceBuffer.getPersonId(), companyId, interfaceBuffer.getAssignmentEffectiveDate());
				if (person != null) {
					try {
						if (interfaceBuffer.getCompanyId() == null) {
							interfaceBuffer.setCompanyId(companyId);
						}
						insertHousEmployee(interfaceBuffer, person.getPeopleType());
						CsvToLdap csvToLdap = new CsvToLdap();
						csvToLdap.setEmployeeNumber(person.getEmployeeNumber());
						int getFirstSpace = person.getFullName().indexOf(" ");
						csvToLdap.setFirstName(getFirstSpace == -1 ? WordUtils.capitalizeFully(person.getFullName()) : WordUtils.capitalizeFully(person.getFullName().substring(0, getFirstSpace)));
						csvToLdap.setLastName(getFirstSpace == -1 ? " " : WordUtils.capitalizeFully(person.getFullName().substring(getFirstSpace+1)));
						csvToLdap.setJobName(person.getPrimaryAssignmentDTO().getJobName());
						csvToLdap.setBranchName(person.getPrimaryAssignmentDTO().getBranchName());
						csvToLdap.setLocationCode(interfaceBuffer.getLocationCode());
						csvToLdap.setPhoneNumber(interfaceBuffer.getEmployeeHp());
						csvToLdap.setHireDate(person.getHireDate());
						csvToLdap.setTerminationDate(getPeopleTypeService().getMaxEffectiveStartDate(interfaceBuffer.getPersonId(), companyId, PeopleType.EX_EMPLOYEE));
						csvToLdap.setEmployeeStatus(person.getPeopleType().equalsIgnoreCase(PeopleType.EMPLOYEE.name()) ? "ACTIVE" : "INACTIVE");
						csvToLdap.setBranchCode(interfaceBuffer.getBranchCode());
						csvToLdap.setBirthDate(person.getBirthDate());
						Stream streamSupervisor = new Stream();
						streamSupervisor.start();
						List<PersonAssignmentDTO> supervisor = SupervisorUtil.getSupervisor(person.getPrimaryAssignmentDTO().getJobId(), person.getPrimaryAssignmentDTO().getOrganizationId(), companyId, 1);
						if (supervisor.size() > 0) {
							csvToLdap.setEmployeeNumberSpv(supervisor.get(0).getEmployeeNumber());
							csvToLdap.setFullNameSpv(WordUtils.capitalizeFully(supervisor.get(0).getFullName()));
						}
						debug(streamSupervisor.stop("get supervisor"));
						csvToLdap.setIsNew(interfaceBuffer.isNewEmployee() ? "Y" : "N");
						csvToLdap.setJobCodeHrms(interfaceBuffer.getEmployeeJobCode());
						if (interfaceBuffer.isNewEmployee()) {
							getInterfaceBufferService().insertUser(person.getEmployeeNumber(), person.getPersonId(), interfaceBuffer.getAssignmentEffectiveDate(), DateUtil.MAX_DATE);							
						}
						csvToLdaps.add(csvToLdap);
						interfaceBuffer.setStatus(InterfaceBufferStatus.COMPLETED.name());
					} catch (Exception e) {
						interfaceBuffer.setStatus(InterfaceBufferStatus.FAILED.name());
						debug(e.getMessage());
					}					
				} else {
					interfaceBuffer.setStatus(InterfaceBufferStatus.FAILED.name());
					debug("data employee not valid for " + interfaceBuffer.getEmployeeNumber());
				}
				getInterfaceBufferService().updateInterfaceBuffer(interfaceBuffer);
				debug("interface buffer employee " + interfaceBuffer.getEmployeeNumber() + " " + interfaceBuffer.getStatus());
				debug(stream.stop("elapsed time for employee number " + interfaceBuffer.getEmployeeNumber()));
			}
		} catch (Exception e) {
			debug(e.getMessage());
		}
		
		// generate text file for archive
		if (csvToLdaps.size() > 0) {
			debug("create file csv for valid data: "+ csvToLdaps.size() + " rows");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat date  = new SimpleDateFormat("dd-MMM-yyyy");
			ICsvMapWriter mapWriter = null;
			String fileName = File.separator + "CsvToLdap-"+ sdf.format(new Date()) +".csv";					
			try {
				File root = new File(folderCsv);

				if (!root.isDirectory()) {
					root.mkdir();
				}
				
				mapWriter = new CsvMapWriter(new FileWriter(folderCsv + fileName), 
						CsvPreference.STANDARD_PREFERENCE);
				
				List<String> fieldMapping = new ArrayList<String>();
				fieldMapping.add("Employee Number");
				fieldMapping.add("First Name");
				fieldMapping.add("Last Name");
				fieldMapping.add("Job Name");
				fieldMapping.add("Branch Name");
				fieldMapping.add("Location Code");
				fieldMapping.add("Phone Number");
				fieldMapping.add("Hire Date");
				fieldMapping.add("Termination Date");
				fieldMapping.add("Employee Status");
				fieldMapping.add("Branch Code");
				fieldMapping.add("Birth Date");
				fieldMapping.add("Employee Number Spv");
				fieldMapping.add("Full Name Spv");
				fieldMapping.add("Is New");
				fieldMapping.add("Job Code HRMS");
				String[] header = fieldMapping.toArray(new String[] {});
				
				List<CellProcessor> cellProcessors = new ArrayList<CellProcessor>();
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				CellProcessor[] processors = cellProcessors.toArray(new CellProcessor[] {});
				
				mapWriter.writeHeader(header);
				for (CsvToLdap csvToLdap : csvToLdaps) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(header[0], csvToLdap.getEmployeeNumber());
					map.put(header[1], csvToLdap.getFirstName());
					map.put(header[2], csvToLdap.getLastName());
					map.put(header[3], csvToLdap.getJobName());
					map.put(header[4], csvToLdap.getBranchName());
					map.put(header[5], csvToLdap.getLocationCode());
					map.put(header[6], csvToLdap.getPhoneNumber());
					map.put(header[7], csvToLdap.getHireDate() != null ? date.format(csvToLdap.getHireDate()) : "");
					map.put(header[8], csvToLdap.getTerminationDate() != null ? date.format(csvToLdap.getTerminationDate()) : "");
					map.put(header[9], csvToLdap.getEmployeeStatus());
					map.put(header[10], csvToLdap.getBranchCode());
					map.put(header[11], csvToLdap.getBirthDate() != null ? date.format(csvToLdap.getBirthDate()) : "");
					map.put(header[12], csvToLdap.getEmployeeNumberSpv());
					map.put(header[13], csvToLdap.getFullNameSpv());
					map.put(header[14], csvToLdap.getIsNew());
					map.put(header[15], csvToLdap.getJobCodeHrms());
					mapWriter.write(map, header, processors);
				}				
				debug("create file csv archived finished");
			} catch (IOException e) {
				debug(e.getMessage());
			} finally {
				if(mapWriter != null) {
					try {
						mapWriter.close();
					} catch (IOException e) {
						debug(e.getMessage());
					}
				}
			}
		}
		
		// generate text file
		if (csvToLdaps.size() > 0) {
			debug("create file csv for valid data: "+ csvToLdaps.size() + " rows");
			SimpleDateFormat date  = new SimpleDateFormat("dd-MMM-yyyy");
			ICsvMapWriter mapWriter = null;
			String fileName = File.separator + "CsvToLdap.csv";					
			try {
				File root = new File(folderCsv);

				if (!root.isDirectory()) {
					root.mkdir();
				}
				
				mapWriter = new CsvMapWriter(new FileWriter(folderCsv + fileName), 
						CsvPreference.STANDARD_PREFERENCE);
				
				List<String> fieldMapping = new ArrayList<String>();
				fieldMapping.add("Employee Number");
				fieldMapping.add("First Name");
				fieldMapping.add("Last Name");
				fieldMapping.add("Job Name");
				fieldMapping.add("Branch Name");
				fieldMapping.add("Location Code");
				fieldMapping.add("Phone Number");
				fieldMapping.add("Hire Date");
				fieldMapping.add("Termination Date");
				fieldMapping.add("Employee Status");
				fieldMapping.add("Branch Code");
				fieldMapping.add("Birth Date");
				fieldMapping.add("Employee Number Spv");
				fieldMapping.add("Full Name Spv");
				fieldMapping.add("Is New");
				fieldMapping.add("Job Code HRMS");
				String[] header = fieldMapping.toArray(new String[] {});
				
				List<CellProcessor> cellProcessors = new ArrayList<CellProcessor>();
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				cellProcessors.add(new Optional());
				CellProcessor[] processors = cellProcessors.toArray(new CellProcessor[] {});
				
				mapWriter.writeHeader(header);
				for (CsvToLdap csvToLdap : csvToLdaps) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(header[0], csvToLdap.getEmployeeNumber());
					map.put(header[1], csvToLdap.getFirstName());
					map.put(header[2], csvToLdap.getLastName());
					map.put(header[3], csvToLdap.getJobName());
					map.put(header[4], csvToLdap.getBranchName());
					map.put(header[5], csvToLdap.getLocationCode());
					map.put(header[6], csvToLdap.getPhoneNumber());
					map.put(header[7], csvToLdap.getHireDate() != null ? date.format(csvToLdap.getHireDate()) : "");
					map.put(header[8], csvToLdap.getTerminationDate() != null ? date.format(csvToLdap.getTerminationDate()) : "");
					map.put(header[9], csvToLdap.getEmployeeStatus());
					map.put(header[10], csvToLdap.getBranchCode());
					map.put(header[11], csvToLdap.getBirthDate() != null ? date.format(csvToLdap.getBirthDate()) : "");
					map.put(header[12], csvToLdap.getEmployeeNumberSpv());
					map.put(header[13], csvToLdap.getFullNameSpv());
					map.put(header[14], csvToLdap.getIsNew());
					map.put(header[15], csvToLdap.getJobCodeHrms());
					mapWriter.write(map, header, processors);
				}				
				debug("create file csv finished");
			} catch (IOException e) {
				debug(e.getMessage());
			} finally {
				if(mapWriter != null) {
					try {
						mapWriter.close();
					} catch (IOException e) {
						debug(e.getMessage());
					}
				}
			}
		}
		debug("Finished..");
	}
	
	private void insertHousEmployee(InterfaceBuffer interfaceBuffer, String peopleType) {
		Stream stream = new Stream();
		stream.start();
		HousEmployeeDTO housEmployee = new HousEmployeeDTO();
		housEmployee.setEmployeeNumber(interfaceBuffer.getEmployeeNumber());
		housEmployee.setBranchCode(interfaceBuffer.getBranchCode());
		housEmployee.setPosCode(interfaceBuffer.getBranchCode());
		housEmployee.setEmployeeName(interfaceBuffer.getEmployeeName().length() > 30 ? interfaceBuffer.getEmployeeName().substring(0, 29) : interfaceBuffer.getEmployeeName());
		housEmployee.setJobCode("100");
		if (peopleType.equalsIgnoreCase(PeopleType.EMPLOYEE.name())) {
			if (interfaceBuffer.getEmploymentCategory().equalsIgnoreCase(EmploymentCategory.PROBATION.name())
					|| interfaceBuffer.getEmploymentCategory().equalsIgnoreCase(EmploymentCategory.TRAINEE.name())) {
				housEmployee.setEmplStatus("PB");
			} else if (interfaceBuffer.getEmploymentCategory().equalsIgnoreCase(EmploymentCategory.PERMANENT.name())) {
				housEmployee.setEmplStatus("PM");
			}
			housEmployee.setEmployeeFlag(interfaceBuffer.getEmployeeFlag());
		} else {
			housEmployee.setEmplStatus("RS");
			housEmployee.setEmployeeFlag("R");
		}
		housEmployee.setEmplArea("10000");
		housEmployee.setEmplUserid("13748");
		housEmployee.setAssignmentEffectiveDate(interfaceBuffer.getAssignmentEffectiveDate());
		if(interfaceBuffer.getEmployeeHp() != null)
			housEmployee.setEmployeeHp(interfaceBuffer.getEmployeeHp().length() > 15 ? interfaceBuffer.getEmployeeHp().substring(0, 14) : interfaceBuffer.getEmployeeHp());
		housEmployee.setEmployeeJobCode(interfaceBuffer.getEmployeeJobCode());
		housEmployee.setJobMutationCode(interfaceBuffer.getEmployeeJobCode());
		housEmployee.setOrganizationMutationCode(interfaceBuffer.getOrgMutationCode());
		housEmployee.setBranchMutationCode(interfaceBuffer.getBranchMutationCode());
		housEmployee.setEmployeeSmsFlag("Y");
		housEmployee.setEmployeeUpdatedDate(new Date());
		housEmployee.setEmployeeUpdatedBy("HCMS");
		housEmployee.setOrganization(interfaceBuffer.getBranchCode());
		getInterfaceBufferService().insertHousEmployee(housEmployee);
		debug(stream.stop("Finish interface hous employee"));
	}
	
	private InterfaceBufferService getInterfaceBufferService() {
		return (InterfaceBufferService) getApplicationContext().getBean("interfaceBufferServiceImpl");
	}
	
	private PersonService getPersonService() {
		return (PersonService) getApplicationContext().getBean("personService");
	}
	
	private PeopleTypeService getPeopleTypeService() {
		return (PeopleTypeService) getApplicationContext().getBean("peopleTypeService");
	}
}
