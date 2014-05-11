package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class OSMWayResponse extends JavaScriptObject
{

	protected OSMWayResponse()
	{

	}

	public final native JsArray<OSMWay> getElements() /*-{
		return this.elements;
	}-*/;

}
