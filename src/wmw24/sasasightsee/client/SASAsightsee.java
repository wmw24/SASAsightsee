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

	public final String[] OSM_URL = {
			"(node[amenity~\"bar|restaurant|cafe|hospital\"](46.46,11.28,46.51,11.35);way[amenity~\"bar|restaurant|cafe|hospital\"](46.46,11.28,46.51,11.35);>);out;",
			"(node[amenity~\"bar|restaurant|cafe|hospital\"](46.65,11.13,46.68,11.18);way[amenity~\"bar|restaurant|cafe|hospital\"](46.65,11.13,46.68,11.18);>);out;" };

	private ArrayList<Poi> poilist = new ArrayList<Poi>();

	@Override
	public void onModuleLoad()
	{
		Window.alert("Hello from gwt");

	}

	private void osmRequest()
	{
		String url = "http://overpass-api.de/api/interpreter?data=[out:json];";

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setCallbackParam("jsonp");

		jsonp.requestObject(url, new AsyncCallback<OSMResponse>()
		{

			@Override
			public void onSuccess(OSMResponse response)
			{
				JsArray<OSMObject> elements = response.getElements();
				Window.alert(elements.length() + " Object read from OSM");
				for (int i = 0; i < elements.length(); i++)
				{
					OSMObject object = elements.get(i);
					if (object.getType().equals("node")
							&& object.getTags() != null)
					{
						Poi poi = new Poi();
						poi.setLat(object.getLat());
						poi.setLon(object.getLon());
						poi.setName(object.getTags().getName());
						poi.setAmenity(object.getTags().getAmenity());
						poilist.add(poi);
					}
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
