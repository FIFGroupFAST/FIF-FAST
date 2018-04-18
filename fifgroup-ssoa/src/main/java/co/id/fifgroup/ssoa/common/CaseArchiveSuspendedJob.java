package co.id.fifgroup.ssoa.common;

import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.validation.TaskExecutionException;

public class CaseArchiveSuspendedJob extends ExecutableTask {

	@Override
	public void execute() throws TaskExecutionException {
		// TODO Auto-generated method stub
		System.out.println("msk exec");
	}

    /*@Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
       /* List<Attributes> currentUsersAttributes = LDAPManager.getManager().getCurrentUsersAttributes();
        List<Attributes> pastUsersAttributes = LDAPManager.getManager().getPastUsersAttributes();       
        getLDAPSynchService(context).synchronizeUser(currentUsersAttributes, pastUsersAttributes);*/
    	//System.out.println("msk exec");
    //}   

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