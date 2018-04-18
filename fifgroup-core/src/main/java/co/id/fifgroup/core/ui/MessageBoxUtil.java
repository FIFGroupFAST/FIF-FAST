package co.id.fifgroup.core.ui;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Messagebox;

public class MessageBoxUtil {

	public static void searchMightBeSlow(final SerializableEventListener<Event> listener) {
		Messagebox.show(Labels.getLabel("common.searchMightBeSlow"), Labels.getLabel("title.question"), Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if(status == Messagebox.OK)
							listener.onEvent(event);
					}
				});
	}
	
	public static void saveConfirmation(final SerializableEventListener<Event> listener) {
		Messagebox.show(Labels.getLabel("common.confirmationSave"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
				new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;
	
				@Override
				public void onEvent(Event event) throws Exception {
					int status = (int) event.getData();
					if(status == Messagebox.YES)
						listener.onEvent(event);
				}
			});
	}
	
	public static void deleteConfirmation(final SerializableEventListener<Event> listener) {
		Messagebox.show(Labels.getLabel("common.confirmationDelete"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 				
				new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;
	
				@Override
				public void onEvent(Event event) throws Exception {
					int status = (int) event.getData();
					if(status == Messagebox.YES)
						listener.onEvent(event);
				}
			});
	}
	
	public static void cancelConfirmation(final SerializableEventListener<Event> listener) {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("title.question"), Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, 
				new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;
	
				@Override
				public void onEvent(Event event) throws Exception {
					int status = (int) event.getData();
					if(status == Messagebox.OK)
						listener.onEvent(event);
				}
			});
	}
	
	public static void showError(String message) {
		Messagebox.show(message, Labels.getLabel("common.title.error"), Messagebox.OK, Messagebox.ERROR);
	}
	
	public static void closeBatchConfirmation(final String batchNumber, final SerializableEventListener<Event> listener) {
		Messagebox.show(Labels.getLabel("common.closeBatch", new Object[] {batchNumber}), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if(status == Messagebox.YES)
							listener.onEvent(event);
					}
				});
	}
	
	public static void cancelBatchConfirmation(final String batchNumber, final SerializableEventListener<Event> listener) {
		Messagebox.show(Labels.getLabel("common.cancelBatch", new Object[] {batchNumber}), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if(status == Messagebox.YES)
							listener.onEvent(event);
					}
				});
	}
	
	public static void submitTransaction() {
		Messagebox.show(Labels.getLabel("common.sucessSubmit"), Labels.getLabel("title.information"), Messagebox.OK , Messagebox.INFORMATION,
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {	}
				});
	
	}
	
}
