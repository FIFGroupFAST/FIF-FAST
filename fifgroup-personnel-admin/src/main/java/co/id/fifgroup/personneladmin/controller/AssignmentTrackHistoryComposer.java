package co.id.fifgroup.personneladmin.controller;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.personneladmin.service.AssignmentTrackHistoryService;

import co.id.fifgroup.personneladmin.domain.AssignmentTrackHistory;

/**
 * @author Rachmad Agus D.
 * @date 20140625
 * @ticket 14040808522990_CR HCMS – Personal Admin_RAH
 * 
 */
public class AssignmentTrackHistoryComposer extends SelectorComposer<Component> {
	private static final long serialVersionUID = 8859492038321915914L;

	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient AssignmentTrackHistoryService assignmentTrackHistoryServiceImpl = (AssignmentTrackHistoryService) SpringUtil
			.getBean("assignmentTrackHistoryServiceImpl");
	@Wire
	private Listbox lbxAssignments;
	@Wire
	private Window winAssTrackHistory;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		lbxAssignments.setModel(new ListModelList<AssignmentTrackHistory>(
				assignmentTrackHistoryServiceImpl.getAssTrackHistory((Long) arg
						.get("personId"))));
	}

	@Listen("onClick = #btnOk")
	public void onOk() {
		winAssTrackHistory.detach();
	}
}
