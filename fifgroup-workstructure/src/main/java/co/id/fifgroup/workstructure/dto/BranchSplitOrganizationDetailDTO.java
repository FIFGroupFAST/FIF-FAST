
package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;

import co.id.fifgroup.workstructure.domain.OrganizationTemplateDetail;

/**
 * @author fifuser
 *
 */
public class BranchSplitOrganizationDetailDTO extends OrganizationTemplateDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String levelName;
	private String orgHeadName;
	private String costCenterName;
	private String orgHeadCode;
	
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getOrgHeadName() {
		return orgHeadName;
	}
	public void setOrgHeadName(String orgHeadName) {
		this.orgHeadName = orgHeadName;
	}
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	public String getOrgHeadCode() {
		return orgHeadCode;
	}
	public void setOrgHeadCode(String orgHeadCode) {
		this.orgHeadCode = orgHeadCode;
	}
	
	
	

}
