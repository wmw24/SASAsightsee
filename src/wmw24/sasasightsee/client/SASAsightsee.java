
package wmw24.sasasightsee.client;

import java.util.ArrayList;
import bz.davide.dmweb.client.leaflet.EventListener;
import bz.davide.dmweb.client.leaflet.Icon;
import bz.davide.dmweb.client.leaflet.IconOptions;
import bz.davide.dmweb.client.leaflet.LatLng;
import bz.davide.dmweb.client.leaflet.Map;
import bz.davide.dmweb.client.leaflet.Marker;
import bz.davide.dmweb.client.leaflet.MarkerOptions;
import bz.davide.dmweb.client.leaflet.OSMLayer;
import bz.davide.dmweb.shared.view.SpanView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SASAsightsee implements EntryPoint
{

   @Override
   public void onModuleLoad()
   {
      osmRequest();
   }

   private static void osmRequest()
   {
      final Map map = new Map((com.google.gwt.user.client.Element) Document.get().getElementById("map"));
      map.addLayer(new OSMLayer());
      map.setView(new LatLng(46.5733, 11.2321), 10);

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
            //Window.alert(elements.length() + " Object read from OSM");
            for (int i = 0; i < elements.length(); i++)
            {
               OSMObject object = elements.get(i);
               if (object.getType().equals("node") && object.getTags() != null)
               {
                  Poi poi = new Poi();
                  poi.setLat(object.getLat());
                  poi.setLon(object.getLon());
                  poi.setName(object.getTags().getName());
                  poi.setAmenity(object.getTags().getAmenity());
                  poilist.add(poi);
               }
            }
            onOSMReady(map, poilist);
         }

         @Override
         public void onFailure(Throwable caught)
         {
            // TODO Auto-generated method stub
            return;
         }
      });
   }

   private static void onOSMReady(final Map map, ArrayList<Poi> poilist)
   {

      for (int i = 0; i < poilist.size(); i++)
      {
         final Poi object = poilist.get(i);
         //Window.alert(object.getType());
         IconOptions iconOptions = new IconOptions();
         //iconSize: [32, 37],
         //iconAnchor: [16, 37],
         //popupAnchor: [0, -33],
         iconOptions.setIconSize(32, 37);
         iconOptions.setIconAnchor(16, 37);
         iconOptions.setPopupAnchor(0, -33);
         iconOptions.setIconUrl("images/historical_museum.png");
         Icon icon = new Icon(iconOptions);
         MarkerOptions markerOptions = new MarkerOptions();
         markerOptions.setIcon(icon);
         final LatLng latLng = new LatLng(object.getLat(), object.getLon());
         Marker marker = new Marker(latLng, markerOptions);
         map.addLayer(marker);

         marker.addClickEventListener(new EventListener()
         {
            @Override
            public void onEvent()
            {
               SpanView spanView = new SpanView("" + object.getName());
               map.openPopup(spanView.getElement(), latLng);
            }
         });

      }
   }
}
