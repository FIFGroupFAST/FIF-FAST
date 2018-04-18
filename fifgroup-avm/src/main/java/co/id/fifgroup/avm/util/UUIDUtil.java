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
 * Created on: Aug 25, 2010
 *
 * Author           Date         Version Description <BR>
 * ---------------- ------------ ------- --------------------------------- <BR>
 * 
 *  
 */

package co.id.fifgroup.avm.util;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author Felix Ngaserin
 * @version $Id$
 * @date $Date$
 *
 */
public final class UUIDUtil {

	private UUIDUtil(){}
	
	public static final UUID NULL = new UUID(0, 0);
	
	public static byte[] convertToBytes(UUID uuid) {
		byte[] bytes = new byte[16];
		for (int i = 7; i >= 0; i--) {
			bytes[7 - i] = (byte) (uuid.getMostSignificantBits() >> (i * 8));
		}

		for (int i = 7; i >= 0; i--) {
			bytes[15 - i] = (byte) (uuid.getLeastSignificantBits() >> (i * 8));
		}

		return bytes;
	}

	public static UUID convertToUUID(byte[] bytes) {
		long msb = 0;
		long lsb = 0;
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		msb = bb.getLong(0);
		lsb = bb.getLong(8);
		
		return new UUID(msb, lsb);
	}	

}
