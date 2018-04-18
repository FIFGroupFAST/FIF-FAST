package co.id.fifgroup.core.upload;

import java.util.Map;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.ui.SerializableListItemRenderer;


public class PopupFileUploadComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private UploadArea composer;
	@Wire
	private Listbox lstMedia;
	ListModelList<Media> model;
	private boolean disabledRemove = false;
	public PopupFileUploadComposer() {
		super();
	}

	@Listen("onCreate=#lstMedia")
	public void createListMedia(){
		composer =(UploadArea) arg.get("composer");
		lstMedia.setModel(new ListModelList<Media>(composer.getMedias()));
		lstMedia.setItemRenderer(getItemRenderer());
		disabledRemove = (boolean) arg.get("disabledRemove");
	}
	
	public ListitemRenderer<Media> getItemRenderer() {
		ListitemRenderer<Media> listitemRenderer = new SerializableListItemRenderer<Media>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final Media data, final int index)
					throws Exception {
				A link = new A(data.getName());
				link.setStyle("text-decoration:underline ;color:blue");
				link.addEventListener("onClick", new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event arg0) throws Exception {
						Filedownload.save(data.getStreamData(), "application/file", data.getName());
					}
				});
				
				Listcell lc = new Listcell();
				link.setParent(lc);
				lc.setParent(item);	
										
				Listcell lc2 = new Listcell();
				Button btnRemove = new Button("remove");
				btnRemove.addEventListener("onClick", new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event arg0) throws Exception {
						composer.removeMedia(data);		
						lstMedia.setModel(new ListModelList<>(composer.getMedias()));
					}
				});
				btnRemove.setDisabled(disabledRemove);
				btnRemove.setParent(lc2);
				lc2.setParent(item);
			}
		};		
		return listitemRenderer;
	}
	
	@Listen("onRemove=#winFileAttachment")
	public void removeMedia(Event event){
		Media media = (Media)event.getData();
		composer.removeMedia(media);		
		lstMedia.setModel(new ListModelList<>(composer.getMedias()));
	}
	
	@Listen("onDownload = #lstMedia")
	public void onDownload(ForwardEvent evt) {
		Media media = (Media) evt.getData();
		Filedownload.save(media.getStreamData(), "application/file", media.getName());
	}

}
