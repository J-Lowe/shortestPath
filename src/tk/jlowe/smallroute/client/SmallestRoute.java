package tk.jlowe.smallroute.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SmallestRoute implements EntryPoint {
	
	public void onModuleLoad() {
		VerticalPanel mainPanel = new VerticalPanel();
		FileUpload fileUpload = new FileUpload();
		Button uploadButton = new Button
		mainPanel.add(fileUpload);
	}
}
