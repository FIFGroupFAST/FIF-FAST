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

package co.id.fifgroup.avm.domain;

import java.io.Serializable;
import java.util.UUID;

import co.id.fifgroup.avm.audit.Traversable;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMAndTransactionMapping implements Serializable, Traversable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4792037353951607712L;
	private UUID avmId;
    private int trxType;
    private int avmStatus;
    private String mappingName;
        
    public UUID getAvmId() {
        return avmId;
    }

    public void setAvmId(UUID avmid) {
        this.avmId = avmid;
    }

    public int getTrxType() {
        return trxType;
    }

    public void setTrxType(int trxtype) {
        this.trxType = trxtype;
    }
    public int getAvmStatus() {
        return avmStatus;
    }

    public void setAvmStatus(int avmstatus) {
        this.avmStatus = avmstatus;
    }

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	public String getMappingName() {
		return mappingName;
	}

	@Override
	public Object getId() {
		return avmId.toString() + "-" + trxType;
	}
}
