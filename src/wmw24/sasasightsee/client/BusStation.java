
package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;

public class BusStation extends JavaScriptObject
{
   protected BusStation()
   {
   }

   public final native String getName() /*-{
		return this.ORT_NAME;
   }-*/;

}
