package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;

public class OSMTags extends JavaScriptObject
{
	protected OSMTags()
	{

	}

	public final native String getName() /*-{
		return this.name;
	}-*/;

	public final native String getAmenity() /*-{
		return this.amenity;
	}-*/;

}
