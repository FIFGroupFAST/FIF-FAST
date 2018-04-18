package co.id.fifgroup.systemadmin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDetailDTO;

public class LookupTaskComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	private SerializableSearchDelegate<Object> searchDelegate;
	@Wire
	private Listbox listbox;
	private Bandbox source;
	private Textbox desc;
	private TaskRunnerDetailDTO data;
	@Wire
	private Window winGenericLov;
	@Wire
	private Label lblSearchText;
	@Wire
	private Listheader listHeadLabel;
	@Wire
	private Searchtextbox txtSearchCriteria;
	@Wire
	private Paging pgListOfValue;
	
	
	private String title;
	private String searchText;
	private String headerLabel;
	private TaskRunnerDetailComposer composer;
	
	private String searchCriteria;
	
	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		searchDelegate = arg.get("searchDelegate") == null ? null : (SerializableSearchDelegate<Object>) arg.get("searchDelegate");
		source = arg.get("source") == null ? null : (Bandbox) arg.get("source");
		composer = arg.get("composer") == null ? null : (TaskRunnerDetailComposer) arg.get("composer");
		desc = arg.get("desc") == null ? null : (Textbox) arg.get("desc");
		data = arg.get("data") == null ? null : (TaskRunnerDetailDTO) arg.get("data");
		title = arg.get("title") == null ? "" : arg.get("title").toString();
		searchText = arg.get("searchText") == null ? "" : arg.get("searchText").toString();
		headerLabel = arg.get("headerLabel") == null ? "" : arg.get("headerLabel").toString();
		if(title == null || title.equals(""))
			winGenericLov.setTitle("List of Value");
		else
			winGenericLov.setTitle(title);
		
		if(searchText == null || searchText.equals(""))
			lblSearchText.setValue("Name");
		else
			lblSearchText.setValue(searchText);
		
		if(headerLabel == null || headerLabel.equals(""))
			listHeadLabel.setLabel("Value");
		else
			listHeadLabel.setLabel(headerLabel);
	}

	@Listen("onSelect=#listbox")
	public void select() {
		KeyValue selectedData = (KeyValue) listbox.getSelectedItem().getValue();
		if(source != null) {
			Task task = (Task) selectedData.getKey();
			source.setValue(task.getTaskName());
			if(data.getMainTask() == null){
				data.setMainTask(new TaskDTO());
			}
			data.getMainTask().setTaskName(task.getTaskName());
			data.setTaskId(task.getId());
			data.getMainTask().setDescription(task.getDescription());
			desc.setValue(task.getDescription());
			composer.rebuildTabularEntryOnChangeTaskCollection();
		}
		getSelf().detach();
	}
	
	private void loadListbox() {
		if(searchDelegate != null) {
			pgListOfValue.setTotalSize(searchDelegate.countBySearchCriteria(searchCriteria));
			pgListOfValue.setPageSize(GlobalVariable.getMaxRowPerPage());
			List<Object> list = (List<Object>) searchDelegate.findBySearchCriteria(searchCriteria, pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
			List<KeyValue> kvList = new ArrayList<KeyValue>();
			for(Object data : list) {
				KeyValue keyValue = new KeyValue();
				searchDelegate.mapper(keyValue, data);
				kvList.add(keyValue);
			}
			ListModelList<KeyValue> model = new ListModelList<KeyValue>(kvList);
			listbox.setModel(model);
		}
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		searchCriteria = txtSearchCriteria.getValue();
		loadListbox();
	}

	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		loadListbox();
	}
}
