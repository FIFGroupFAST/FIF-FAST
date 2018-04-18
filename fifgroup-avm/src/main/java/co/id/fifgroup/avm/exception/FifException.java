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
package co.id.fifgroup.avm.exception;

/**
 * @author $Author: andeka.putra $
 * @version $Revision: 122 $
 * @date $Date: 2010-07-20 16:26:30 +0800 (Tue, 20 Jul 2010) $
 * 
 */
public class FifException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int code;

	public FifException(int code) {
		super();

		this.code = code;
	}

	public FifException(int code, String message) {
		super(message);

		this.code = code;
	}

	public FifException(int code, Throwable cause) {
		super(cause);

		this.code = code;
	}

	public FifException(int code, String message, Throwable cause) {
		super(message, cause);

		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
