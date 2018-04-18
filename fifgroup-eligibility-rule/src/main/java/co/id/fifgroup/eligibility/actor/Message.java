package co.id.fifgroup.eligibility.actor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public final String modulePrefix;
	public final Long decisionTableId;
	public final Long personId;
	public final Date effectiveOnDate;
	public final Map<String, Object> params;
	
	public Message(String modulePrefix, Long decisionTableId, Long personId, Date effectiveOnDate,
			Map<String, Object> params) {
		super();
		this.modulePrefix = modulePrefix;
		this.decisionTableId = decisionTableId;
		this.personId = personId;
		this.effectiveOnDate = effectiveOnDate;
		this.params = params;
	}

}
