
package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;
import java.util.List;

import co.id.fifgroup.workstructure.domain.OrganizationTemplate;

public class BranchSplitMasterDTO extends OrganizationTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<BranchSplitOrganizationDetailDTO> organizationDetails;

	public List<BranchSplitOrganizationDetailDTO> getOrganizationDetails() {
		return organizationDetails;
	}

	public void setOrganizationDetails(List<BranchSplitOrganizationDetailDTO> organizationDetails) {
		this.organizationDetails = organizationDetails;
	}

}
