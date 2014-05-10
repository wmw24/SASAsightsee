
package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;

public class BusStop extends JavaScriptObject
{
   protected BusStop()
   {
      // TODO Auto-generated constructor stub
   }

   public final native int getORT_NR() /*-{
		return this.ORT_NR;
   }-*/;

   public final native double getLat() /*-{
		return this.ORT_POS_BREITE;
   }-*/;

   public final native double getLon() /*-{
		return this.ORT_POS_LAENGE;
   }-*/;

   //ORT_NR":1489,"ORT_POS_BREITE":46.58766,"ORT_POS_LAENGE
}
