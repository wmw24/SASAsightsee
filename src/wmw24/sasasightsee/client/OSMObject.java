package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;

public class OSMObject extends JavaScriptObject
{
	protected OSMObject()
	{

	}

	public final native String getType() /*-{
		return this.type;
	}-*/;

	public final native String getId() /*-{
		return this.id;
	}-*/;

	public final native Double getLat() /*-{
		return this.lat;
	}-*/;

	public final native Double getLon() /*-{
		return this.lon;
	}-*/;

	public final native OSMTags getTags() /*-{
		return this.tags;
	}-*/;

}
