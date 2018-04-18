package co.id.fifgroup.core.dto;

import java.io.Serializable;

/**
 * <p>this class is a substitute for decision table. To get the real value, query again with provided id and version number</p>
 * @author rnp
 * @see public HousingSetupDTO findHousingSetupByCompanyIdAndDate(Long id, Date date)
 * 
 *  
 */
public class DecisionTableDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long modelVersion;
	private String modelId;

	public Long getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(Long modelVersion) {
		this.modelVersion = modelVersion;
	}

	private String drl;

	private String modulePrefix;

	public DecisionTableDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDrl() {
		return drl;
	}

	public void setDrl(String drl) {
		this.drl = drl;
	}

	public String getModulePrefix() {
		return modulePrefix;
	}

	public void setModulePrefix(String modulePrefix) {
		this.modulePrefix = modulePrefix;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

}
