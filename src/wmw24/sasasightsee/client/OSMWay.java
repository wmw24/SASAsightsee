package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;

public class OSMWay extends JavaScriptObject
{
	protected OSMWay()
	{

	}

	public final native String getType() /*-{
		return this.type;
	}-*/;

	public final native double getId() /*-{
		return this.id;
	}-*/;

	public final native double getLat() /*-{
		return this.lat;
	}-*/;

	public final native double getLon() /*-{
		return this.lon;
	}-*/;

	public final native JsArrayNumber getNodes() /*-{
		return this.nodes;
	}-*/;

	public final native OSMTags getTags() /*-{
		return this.tags;
	}-*/;

}
