
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

   public final native int getId() /*-{
		return this.id;
   }-*/;

   public final native double getLat() /*-{
		return this.lat;
   }-*/;

   public final native double getLon() /*-{
		return this.lon;
   }-*/;

}
