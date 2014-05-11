package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class OSMNodeResponse extends JavaScriptObject
{

	protected OSMNodeResponse()
	{

	}

	public final native JsArray<OSMNode> getElements() /*-{
		return this.elements;
	}-*/;

}
