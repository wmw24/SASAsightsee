
package wmw24.sasasightsee.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SASAsightsee implements EntryPoint
{
   @Override
   public void onModuleLoad()
   {
      Window.alert("Hello from gwt");

      String url = "http://overpass-api.de/api/interpreter?data=[out:json];%28node[amenity~%22bar|restaurant|cafe|hospital%22]%2846.46,11.28,46.51,11.35%29;way[amenity~%22bar|restaurant|cafe|hospital%22]%2846.46,11.28,46.51,11.35%29;node[amenity~%22bar|restaurant|cafe|hospital%22]%2846.65,11.13,46.68,11.18%29;way[amenity~%22bar|restaurant|cafe|hospital%22]%2846.65,11.13,46.68,11.18%29;%3E%29;out;";

      JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
      jsonp.setCallbackParam("jsonp");

      jsonp.requestObject(url, new AsyncCallback<OSMResponse>()
      {

         @Override
         public void onSuccess(OSMResponse response)
         {
            JsArray<OSMObject> entries = response.getElements();
            Window.alert(entries.length() + " Object read from OSM");
            for (int i = 0; i < entries.length() && i < 3; i++)
            {
               OSMObject object = entries.get(i);
               Window.alert(object.getType());
            }
         }

         @Override
         public void onFailure(Throwable caught)
         {
            // TODO Auto-generated method stub
            return;
         }
      });

   }
}
