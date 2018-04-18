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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dipo Makka
 * @version $Id$
 * @date $Date$
 *
 */
public final class Serialization {

	private static final Logger logger = LoggerFactory.getLogger(Serialization.class);
	private Serialization(){}
	
	public static byte[] serializeObject(Object object) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oout = null;
		try {
			oout = new ObjectOutputStream(baos);
			oout.writeObject(object);
			oout.close();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return baos.toByteArray();
	}
	
	public static Object deserializedObject(byte[] serializedObject) {
		
		ObjectInputStream objectIn = null;		
		Object deserializedObject = null;
		
		try {			
			objectIn = new ObjectInputStream(
					new ByteArrayInputStream(serializedObject));
			deserializedObject = objectIn.readObject();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return deserializedObject;

	}

}
