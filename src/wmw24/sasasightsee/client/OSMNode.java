
package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;

public class OSMNode extends JavaScriptObject
{
   protected OSMNode()
   {

   }

   public final native String getType() /*-{
		return this.type;
   }-*/;

   public final native String getId() /*-{
		return this.id;
   }-*/;

   public final native double getLat() /*-{
		return this.lat;
   }-*/;

   public final native double getLon() /*-{
		return this.lon;
   }-*/;

   public final native OSMTags getTags() /*-{
		return this.tags;
   }-*/;

}
