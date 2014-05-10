package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class OSMResponse extends JavaScriptObject
{

	protected OSMResponse()
	{

	}

	public final native JsArray<OSMNode> getElements() /*-{
		return this.elements;
	}-*/;

}
