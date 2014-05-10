
package wmw24.sasasightsee.client;

import java.util.ArrayList;
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
      this.osmRequest();
   }

   private void osmRequest()
   {
      String url = "http://overpass-api.de/api/interpreter?data=[out:json];%28node[amenity~%22bar|restaurant|cafe|hospital%22]%2846.46,11.28,46.51,11.35%29;way[amenity~%22bar|restaurant|cafe|hospital%22]%2846.46,11.28,46.51,11.35%29;node[amenity~%22bar|restaurant|cafe|hospital%22]%2846.65,11.13,46.68,11.18%29;way[amenity~%22bar|restaurant|cafe|hospital%22]%2846.65,11.13,46.68,11.18%29;%3E%29;out;";

      JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
      jsonp.setCallbackParam("jsonp");

      jsonp.requestObject(url, new AsyncCallback<OSMResponse>()
      {

         @Override
         public void onSuccess(OSMResponse response)
         {
            ArrayList<Poi> poilist = new ArrayList<Poi>();

            JsArray<OSMObject> elements = response.getElements();
            Window.alert(elements.length() + " Object read from OSM");
            for (int i = 0; i < elements.length(); i++)
            {
               OSMObject object = elements.get(i);
               if (object.getType() == "node" && object.getTags() != null)
               {
                  Poi poi = new Poi();
                  poi.setLat(object.getLat());
                  poi.setLon(object.getLon());
                  poi.setName(object.getTags().getName());
                  poi.setAmenity(object.getTags().getAmenity());
                  poilist.add(poi);
               }
            }
            SASAsightsee.this.onOSMReady(poilist);
         }

         @Override
         public void onFailure(Throwable caught)
         {
            // TODO Auto-generated method stub
            return;
         }
      });
   }

   private void onOSMReady(ArrayList<Poi> poilist)
   {

   }
}
