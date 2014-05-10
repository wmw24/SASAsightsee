package wmw24.sasasightsee.client;

import it.bz.tis.sasabus.html5.client.SASAbusDBClientImpl;

import java.util.ArrayList;

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
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SASAsightsee implements EntryPoint
{

	public static final String[] OSM_URL = {
			"(node[amenity~\"restaurant|hospital|parking\"](46.46,11.28,46.51,11.35)[name];way[amenity~\"restaurant|hospital|parking\"](46.46,11.28,46.51,11.35)[name];>);out;",
			"(node[amenity~\"restaurant|hospital|parking\"](46.65,11.13,46.68,11.18)[name];way[amenity~\"restaurant|hospital|parking\"](46.65,11.13,46.68,11.18)[name];>);out;",
			"(node[tourism~\"artwork|museum\"](46.65,11.13,46.68,11.18)[name];way[tourism~\"artwork|museum\"](46.65,11.13,46.68,11.18)[name];>);out;",
			"(node[tourism~\"artwork|museum\"](46.46,11.28,46.51,11.35)[name];way[tourism~\"artwork|museum\"](46.46,11.28,46.51,11.35)[name];>);out;" };

	public static int counter = 0;

	@Override
	public void onModuleLoad()
	{
		SASAbusDBClientImpl.singleton = new SASAbusDBClientImpl(
				"http://html5.sasabus.org/backend");

		final Map map = new Map((com.google.gwt.user.client.Element) Document
				.get().getElementById("map"));
		map.addLayer(new OSMLayer());
		map.setView(new LatLng(46.5733, 11.2321), 10);

		for (int i = 0; i < OSM_URL.length; ++i)
		{
			String url = OSM_URL[i];
			osmRequest(map, url);
		}

		/*
		 * try { weather(); } catch (RequestException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	private static void osmRequest(final Map map, String query)
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

				ArrayList<Poi> poilist = new ArrayList<Poi>();

				JsArray<OSMObject> elements = response.getElements();
				// Window.alert(elements.length() + " Object read from OSM");
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
				++counter;
				onOSMReady(map, poilist);

			}

		});

	}

	private static void onOSMReady(final Map map, final ArrayList<Poi> poilist)
	{
		++counter;
		if (counter != OSM_URL.length)
		{

		}

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
				onBusStationReady(map, poilist, busStations);
			}

			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub
				return;
			}
		});
	}

	static void onBusStationReady(final Map map, ArrayList<Poi> poilist,
			final ArrayList<BusStation> busStations)
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
					Popup popup = new Popup(object, busStations);
					map.openPopup(popup.getElement(), latLng);
					AbstractHtmlElementView.notifyAttachRecursive(popup);
				}
			});

		}
	}
}
