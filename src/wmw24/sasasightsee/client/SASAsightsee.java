package wmw24.sasasightsee.client;

import it.bz.tis.sasabus.html5.client.SASAbusDBClientImpl;
import java.util.ArrayList;
import java.util.HashMap;
import bz.davide.dmweb.client.leaflet.EventListener;
import bz.davide.dmweb.client.leaflet.Icon;
import bz.davide.dmweb.client.leaflet.IconOptions;
import bz.davide.dmweb.client.leaflet.LatLng;
import bz.davide.dmweb.client.leaflet.Map;
import bz.davide.dmweb.client.leaflet.Marker;
import bz.davide.dmweb.client.leaflet.MarkerOptions;
import bz.davide.dmweb.client.leaflet.OSMLayer;
import bz.davide.dmweb.shared.view.AbstractHtmlElementView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SASAsightsee implements EntryPoint
{

	public static final String[] OSM_URL = {
			"node[amenity~\"restaurant|hospital|parking\"](46.46,11.28,46.51,11.37)[name];out;",
			"node[amenity~\"restaurant|hospital|parking\"](46.65,11.13,46.68,11.18)[name];out;",
			"node[tourism~\"artwork|museum\"](46.65,11.13,46.68,11.18)[name];out;",
			"node[tourism~\"artwork|museum\"](46.46,11.28,46.51,11.37)[name];out;" };

	/*
	 * public static final String[] OSM_URL = {
	 * "(node[amenity~\"restaurant|hospital|parking\"](46.46,11.28,46.51,11.37)[name];way[amenity~\"restaurant|hospital|parking\"](46.46,11.28,46.51,11.37)[name];>);out;"
	 * ,
	 * "(node[amenity~\"restaurant|hospital|parking\"](46.65,11.13,46.68,11.18)[name];way[amenity~\"restaurant|hospital|parking\"](46.65,11.13,46.68,11.18)[name];>);out;"
	 * ,
	 * "(node[tourism~\"artwork|museum\"](46.65,11.13,46.68,11.18)[name];way[tourism~\"artwork|museum\"](46.65,11.13,46.68,11.18)[name];>);out;"
	 * ,
	 * "(node[tourism~\"artwork|museum\"](46.46,11.28,46.51,11.37)[name];way[tourism~\"artwork|museum\"](46.46,11.28,46.51,11.37)[name];>);out;"
	 * };
	 */
	public static int counter = 0;

	@Override
	public void onModuleLoad()
	{

		SASAbusDBClientImpl.singleton = new SASAbusDBClientImpl(
				"http://html5.sasabus.org/backend/");

		final Map map = new Map((com.google.gwt.user.client.Element) Document
				.get().getElementById("map"));
		map.addLayer(new OSMLayer());
		map.setView(new LatLng(46.5733, 11.2321), 10);

		try
		{
			weather(map);
		}
		catch (RequestException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void weather(final Map map) throws RequestException
	{
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				"weather.xml");
		builder.sendRequest("", new RequestCallback()
		{
			@Override
			public void onResponseReceived(Request request, Response response)
			{
				com.google.gwt.xml.client.Document xmldoc = XMLParser
						.parse(response.getText());
				java.util.Map<String, Weather> weatherMap = new HashMap<String, Weather>();

				ArrayList<Poi> poilist = new ArrayList<Poi>();

				Element today = (Element) xmldoc.getElementsByTagName("today")
						.item(0);

				for (int i = 0; i < OSM_URL.length; ++i)
				{
					String url = OSM_URL[i];
					osmRequest(map, url, weatherMap, poilist);
				}
			}

			@Override
			public void onError(Request request, Throwable exception)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	private static void osmRequest(final Map map, String query,
			final java.util.Map<String, Weather> weather,
			final ArrayList<Poi> poilist)
	{
		String url = "http://overpass-api.de/api/interpreter?data=[out:json];"
				+ query;

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setCallbackParam("jsonp");

		jsonp.requestObject(url, new AsyncCallback<OSMResponse>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(OSMResponse response)
			{

				JsArray<OSMNode> elements = response.getElements();
				// Window.alert(elements.length() + " Object read from OSM");
				for (int i = 0; i < elements.length(); i++)
				{
					OSMNode object = elements.get(i);
					if (object.getType().equals("node")
							&& object.getTags() != null)
					{
						Poi poi = new Poi();
						poi.setLat(object.getLat());
						poi.setLon(object.getLon());
						poi.setName(object.getTags().getName());
						poi.setAmenity(object.getTags().getAmenity());

						if (poi.getName().equals(
								"Messner Mountain Museum Firmian"))
						{

							poi.setAttr("addrCity", object.getTags()
									.getAddrCity());
							poi.setAttr("addrCountry", object.getTags()
									.getAddrCountry());
							poi.setAttr("addrHousename", object.getTags()
									.getAddrHousename());
							poi.setAttr("addrHousename:de", object.getTags()
									.getAddrHouseNameDe());
							poi.setAttr("addrHousename:it", object.getTags()
									.getAddrHouseNameIt());
							poi.setAttr("addrHousenumber", object.getTags()
									.getAddrHousenumber());
							poi.setAttr("addrPostcode", object.getTags()
									.getAddrPostcode());
							poi.setAttr("addrStreet", object.getTags()
									.getAddrStreet());
							poi.setAttr("email", object.getTags().getEmail());
							poi.setAttr("fax", object.getTags().getFax());
							poi.setAttr("operator", object.getTags()
									.getOperator());
							poi.setAttr("phone", object.getTags().getPhone());
							poi.setAttr("webseite", object.getTags()
									.getWebsite());
							poi.setAttr("wikipedia", object.getTags()
									.getWikipedia());
							poi.setAttr("wheelchair", object.getTags()
									.getWheelchair());
						}
						poilist.add(poi);
					}
				}
				++counter;
				if (counter == OSM_URL.length)
				{
					onOSMReady(map, poilist, weather);
				}

			}

		});

	}

	private static void onOSMReady(final Map map, final ArrayList<Poi> poilist,
			final java.util.Map<String, Weather> weather)
	{

		String url = "http://opensasa.info/SASAplandata/getData.php?type=REC_ORT";

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setCallbackParam("jsonp");

		final ArrayList<BusStation> busStations = new ArrayList<>();

		jsonp.requestObject(url, new AsyncCallback<JsArray<BusStation>>()
		{

			@Override
			public void onSuccess(JsArray<BusStation> response)
			{
				for (int i = 0; i < response.length(); i++)
				{
					busStations.add(response.get(i));
				}
				onBusStationReady(map, poilist, busStations, weather);
			}

			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub
				return;
			}
		});
	}

	private static void onBusStationReady(final Map map,
			ArrayList<Poi> poilist, final ArrayList<BusStation> busStations,
			final java.util.Map<String, Weather> weather)
	{
		for (int i = 0; i < poilist.size(); i++)
		{
			final Poi object = poilist.get(i);
			// Window.alert(object.getType());
			IconOptions iconOptions = new IconOptions();
			// iconSize: [32, 37],
			// iconAnchor: [16, 37],
			// popupAnchor: [0, -33],
			iconOptions.setIconSize(32, 37);
			iconOptions.setIconAnchor(16, 37);
			iconOptions.setPopupAnchor(0, -33);
			int randomInt = Random.nextInt(4);
			if (randomInt == 0)
         {
			   iconOptions.setIconUrl("images/historical_museum_sponsored.png");
         }
         else
         {
            iconOptions.setIconUrl("images/historical_museum.png");
         }
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
					Popup popup = new Popup(object, busStations, weather);
					map.openPopup(popup.getElement(), latLng);
					AbstractHtmlElementView.notifyAttachRecursive(popup);
				}
			});

		}
	}
}
