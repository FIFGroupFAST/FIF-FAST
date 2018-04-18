package co.id.fifgroup.eligibility.actor;

import org.springframework.beans.factory.annotation.Autowired;

import akka.actor.UntypedActor;
import co.id.fifgroup.eligibility.service.DecisionTableService;

public class EligibilityActor extends UntypedActor{

	@Autowired
	private DecisionTableService decisionTableService;
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Message) {
			Message m = (Message) message;
			decisionTableService.execute(m.modulePrefix, m.decisionTableId, m.personId, m.effectiveOnDate, m.params);
		}
		
	}
	

}
