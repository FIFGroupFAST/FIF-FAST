package co.id.fifgroup.avm.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.id.fifgroup.avm.AVMEngine;
import co.id.fifgroup.avm.exception.FifException;

public class AVMAndTransactionTest {

	ApplicationContext ctx = new ClassPathXmlApplicationContext("avm-context.xml");
	AVMEngine engine = (AVMEngine) ctx.getBean("avmEngine");
	
	@Test
	public void mapAVMAndTrxTypeList() throws FifException {
		UUID avmId = UUID.fromString("11111111-1111-1111-1111-111111111111");
		List<Integer> trxTypeList = new ArrayList<Integer>();
		trxTypeList.add(1);
		trxTypeList.add(2);
		engine.mapAVMAndTrxTypeList(avmId, trxTypeList);
	}
}
