package co.id.fifgroup.ssoa.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.validation.TaskExecutionException;
import co.id.fifgroup.ssoa.service.EbsToSsoaService;

@VariableResolver(DelegatingVariableResolver.class)
public class EbsToSsoaJob extends ExecutableTask {

	//@WireVariable(rewireOnActivate = true)
	
	
	
	@Override
	public void execute() throws TaskExecutionException {
		// TODO Auto-generated method stub
		//System.out.println("msk exec EBS JOB");
		EbsToSsoaService ebsToSsoaService = (EbsToSsoaService)getApplicationContext().getBean("ebsToSsoaService");
		System.out.println("ebsToSsoaService==" + ebsToSsoaService);
		//System.out.println("assetSynchronizingService==" + assetSynchronizingService);
		ebsToSsoaService.ebsToSsoaSynchronizing();
    	//System.out.println("msk done EBS JOB");
	}   
	

    /*private ILDAPSynchService getLDAPSynchService(JobExecutionContext context)  {
        ApplicationContext applicationContext = null;

        try {
            applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
        } catch (Exception e) {         
        }

        if(applicationContext == null) {
            applicationContext = ContextLoader.getCurrentWebApplicationContext();
        }


        return (ILDAPSynchService) applicationContext.getBean("ldapSynchService");
    }*/

	}