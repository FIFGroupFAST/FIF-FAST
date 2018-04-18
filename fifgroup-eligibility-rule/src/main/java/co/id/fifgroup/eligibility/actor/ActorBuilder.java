package co.id.fifgroup.eligibility.actor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import akka.actor.Actor;
import akka.actor.UntypedActorFactory;

public class ActorBuilder implements ApplicationContextAware, UntypedActorFactory{

	private static final long serialVersionUID = 1L;
	
	private ApplicationContext applicationContext;
	private String actorBeanId;
	
	public ActorBuilder(final String actorBeanId) {
		this.actorBeanId = actorBeanId;
	}

	@Override
	public final Actor create() throws Exception {
		return (Actor) applicationContext.getBean(actorBeanId);
	}

	@Override
	public final void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext =  applicationContext;
	}

}
