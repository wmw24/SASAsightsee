
package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class BusStation extends JavaScriptObject
{
   protected BusStation()
   {
   }

   public final native String getName() /*-{
		return this.ORT_NAME;
   }-*/;

   public final native JsArray<BusStop> getBusStops() /*-{
		return this.busstops;
   }-*/;

   public final String getId()
   {
      String ret = ":";
      JsArray<BusStop> stops = this.getBusStops();
      for (int i = 0; i < stops.length(); i++)
      {
         BusStop busStop = stops.get(i);
         ret += String.valueOf(busStop.getORT_NR()) +":";
      }
      return ret;
   }

}
