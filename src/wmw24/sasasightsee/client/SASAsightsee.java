package wmw24.sasasightsee.client;

import it.bz.tis.sasabus.html5.client.SASAbusDBClientImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.XMLParser;
import com.mouchel.gwt.xpath.client.XPath;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SASAsightsee implements EntryPoint
{

	public static final String[] OSM_NODE_URL = {
			"node[amenity~\"restaurant|hospital\"](46.46,11.28,46.51,11.37)[name];out;",
			"node[amenity~\"restaurant|hospital\"](46.65,11.13,46.68,11.18)[name];out;",
			"node[tourism~\"artwork|museum|attraction\"](46.65,11.13,46.68,11.18)[name];out;",
			"node[tourism~\"artwork|museum|attraction\"](46.46,11.28,46.51,11.37)[name];out;" };

	public static final String[] OSM_WAY_URL = {
			"(way[tourism~\"artwork|museum|attraction\"](46.65,11.13,46.68,11.18)[name];>);out;",
			"(way[tourism~\"artwork|museum|attraction\"](46.46,11.28,46.51,11.37)[name];>);out;",
			"(way[amenity~\"hospital\"](46.65,11.13,46.68,11.18)[name];>);out;",
			"(way[amenity~\"hospital\"](46.46,11.28,46.51,11.37)[name];>);out;" };

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

	// Bz position (from http://tools.wmflabs.org/geohack/geohack.php) - for
	// weather
	public static final double BZ_LAT = 46.497978, BZ_LON = 11.354783;
	// Me position (from http://tools.wmflabs.org/geohack/geohack.php) - for
	// weather
	public static final double ME_LAT = 46.666667, ME_LON = 11.166667;

	@Override
	public void onModuleLoad()
	{

		SASAbusDBClientImpl.singleton = new SASAbusDBClientImpl(
				"http://html5.sasabus.org/backend/");

		final Map map = new Map((com.google.gwt.user.client.Element) Document
				.get().getElementById("map"));
		map.addLayer(new OSMLayer());
		map.setView(new LatLng(46.5733, 11.2321), 10);

		Menu menu = new Menu();
		Element body = Document.get().getElementsByTagName("body").getItem(0);
      body.appendChild(menu.getElement());
      AbstractHtmlElementView.notifyAttachRecursive(menu);

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
		// TODO language?
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				"weather.xml");
		builder.sendRequest("", new RequestCallback()
		{
			private void fetchWeatherTodayTomorrow(
					com.google.gwt.xml.client.Document xmldoc, String node,
					java.util.Map<String, Weather> weatherMap)
			{
				// fetch weather for Me
				String wDescription = XPath
						.evaluate(
								xmldoc,
								"//"
										+ node
										+ "/stationData[Id=2]/symbol/description/text()")
						.toString();
				String wImageURL = XPath.evaluate(
						xmldoc,
						"//" + node
								+ "/stationData[Id=2]/symbol/imageURL/text()")
						.toString();
				String wTempMax = XPath.evaluate(
						xmldoc,
						"//" + node
								+ "/stationData[Id=2]/temperature/max/text()")
						.toString();
				String wTempMin = XPath.evaluate(
						xmldoc,
						"//" + node
								+ "/stationData[Id=2]/temperature/min/text()")
						.toString();
				Weather weather = new Weather();

				weather.setDescription("2",
						wDescription.substring(1, wDescription.length() - 1));
				weather.setImageURL("2",
						wImageURL.substring(1, wImageURL.length() - 1));
				weather.setTempMax(
						"2",
						Integer.parseInt(wTempMax.substring(1,
								wTempMax.length() - 1)));
				weather.setTempMin(
						"2",
						Integer.parseInt(wTempMin.substring(1,
								wTempMin.length() - 1)));
				// fetch weather for Bz

				wDescription = XPath
						.evaluate(
								xmldoc,
								"//"
										+ node
										+ "/stationData[Id=3]/symbol/description/text()")
						.toString();
				wImageURL = XPath.evaluate(
						xmldoc,
						"//" + node
								+ "/stationData[Id=3]/symbol/imageURL/text()")
						.toString();
				wTempMax = XPath.evaluate(
						xmldoc,
						"//" + node
								+ "/stationData[Id=3]/temperature/max/text()")
						.toString();
				wTempMin = XPath.evaluate(
						xmldoc,
						"//" + node
								+ "/stationData[Id=3]/temperature/min/text()")
						.toString();

				weather.setDescription("3",
						wDescription.substring(1, wDescription.length() - 1));
				weather.setImageURL("3",
						wImageURL.substring(1, wImageURL.length() - 1));
				weather.setTempMax(
						"3",
						Integer.parseInt(wTempMax.substring(1,
								wTempMax.length() - 1)));
				weather.setTempMin(
						"3",
						Integer.parseInt(wTempMin.substring(1,
								wTempMin.length() - 1)));
				String wDate = XPath
						.evaluate(xmldoc, "//" + node + "/date/text()")
						.toString().substring(1).split("T")[0];
				weatherMap.put(wDate, weather);
			}

			private void fetchWeatherForecast(com.google.gwt.xml.client.Document xmldoc,
					java.util.Map<String, Weather> weatherMap)
			{
				List<com.google.gwt.xml.client.Node> forecasts = XPath.evaluate(xmldoc, "//dayForecast");
				for (int i = 1; i <= forecasts.size(); i++) {
					String wDescription = XPath.evaluate(xmldoc, "//dayForecast[" + i + "]/symbol/description/text()")
							.toString();
					String wImageURL = XPath.evaluate(xmldoc, "//dayForecast[" + i + "]/symbol/imageURL/text()")
							.toString();
					String wTempMax = XPath.evaluate(xmldoc, "//dayForecast[" + i + "]/tempMax/max/text()")
							.toString();
					String wTempMin = XPath.evaluate(xmldoc, "//dayForecast[" + i + "]/tempMin/max/text()")
							.toString();

					Weather weather = new Weather();
					weather.setDescription(null,
							wDescription.substring(1, wDescription.length() - 1));
					weather.setImageURL(null,
							wImageURL.substring(1, wImageURL.length() - 1));
					weather.setTempMax(
							null,
							Integer.parseInt(wTempMax.substring(1,
									wTempMax.length() - 1)));
					weather.setTempMin(
							null,
							Integer.parseInt(wTempMin.substring(1,
									wTempMin.length() - 1)));

					String wDate = XPath.evaluate(xmldoc, "//dayForecast[" + i + "]/date/text()")
							.toString().substring(1).split("T")[0];
					weatherMap.put(wDate, weather);
				}
			}

			@Override
			public void onResponseReceived(Request request, Response response)
			{
				com.google.gwt.xml.client.Document xmldoc = XMLParser
						.parse(response.getText());

				java.util.Map<String, Weather> weatherMap = new HashMap<String, Weather>();
// FOR TESTING: use date from weather service
//				String currDateStr = XPath.evaluate(xmldoc, "//date/text()")
//						.toString().substring(1).split("T")[0];
//				Date currDate = DateTimeFormat.getFormat("yyyy-MM-dd").parse(
//						currDateStr);
				Date currDate = new Date();

				this.fetchWeatherTodayTomorrow(xmldoc, "today", weatherMap);
				this.fetchWeatherTodayTomorrow(xmldoc, "tomorrow", weatherMap);
				this.fetchWeatherForecast(xmldoc, weatherMap);

				ArrayList<Poi> poilist = new ArrayList<Poi>();

				for (int i = 0; i < OSM_NODE_URL.length; ++i)
				{
					String url = OSM_NODE_URL[i];
					osmNodeRequest(map, url, weatherMap, currDate, poilist);
				}
				for (int i = 0; i < OSM_WAY_URL.length; ++i)
				{
					String url = OSM_WAY_URL[i];
					osmWayRequest(map, url, weatherMap, currDate, poilist);

				}
			}

			@Override
			public void onError(Request request, Throwable exception)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	private static void osmNodeRequest(final Map map, String query,
			final java.util.Map<String, Weather> weather, final Date currDate,
			final ArrayList<Poi> poilist)
	{
		String url = "http://overpass-api.de/api/interpreter?data=[out:json];"
				+ query;

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setCallbackParam("jsonp");

		jsonp.requestObject(url, new AsyncCallback<OSMNodeResponse>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(OSMNodeResponse response)
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
						if (object.getTags().getAmenity() == null
								|| object.getTags().getAmenity().toString()
										.equals("undefined"))
						{
							poi.setAmenity(object.getTags().getTourism());
						}
						else
						{
							poi.setAmenity(object.getTags().getAmenity());
						}

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
				if (counter == OSM_WAY_URL.length + OSM_NODE_URL.length)
				{
					onOSMReady(map, poilist, weather, currDate);
				}

			}

		});

	}

	private static void osmWayRequest(final Map map, String query,
			final java.util.Map<String, Weather> weather, final Date currDate,
			final ArrayList<Poi> poilist)
	{
		String url = "http://overpass-api.de/api/interpreter?data=[out:json];"
				+ query;

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setCallbackParam("jsonp");

		jsonp.requestObject(url, new AsyncCallback<OSMWayResponse>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(OSMWayResponse response)
			{

				JsArray<OSMWay> elements = response.getElements();
				// Window.alert(elements.length() + " Object read from OSM");
				for (int i = 0; i < elements.length(); i++)
				{
					OSMWay object = elements.get(i);
					if (object.getType().equals("way")
							&& object.getTags() != null)
					{
						Poi poi = new Poi();
						poi.setName(object.getTags().getName());
						JsArrayNumber nodelist = object.getNodes();
						double sumlat = 0, sumlon = 0;
						int count = 0;
						for (int j = 0; j < nodelist.length(); ++j)
						{
							double nodeid = nodelist.get(j);
							for (int m = 0; m < elements.length(); ++m)
							{
								OSMWay nodeelement = elements.get(m);
								if (nodeelement.getType().equals("node")
										&& nodeid == nodeelement.getId())
								{
									sumlat += nodeelement.getLat();
									sumlon += nodeelement.getLon();
									++count;
									break;
								}
							}

						}
						poi.setLat(sumlat / count);
						poi.setLon(sumlon / count);
						if (object.getTags().getAmenity() == null
								|| object.getTags().getAmenity().toString()
										.equals("undefined"))
						{
							poi.setAmenity(object.getTags().getTourism());
						}
						else
						{
							poi.setAmenity(object.getTags().getAmenity());
						}

						poilist.add(poi);
					}
				}
				++counter;
				if (counter == OSM_WAY_URL.length + OSM_NODE_URL.length)
				{
					onOSMReady(map, poilist, weather, currDate);
				}

			}

		});
	}

	private static void onOSMReady(final Map map, final ArrayList<Poi> poilist,
			final java.util.Map<String, Weather> weather, final Date currDate)
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
				onBusStationReady(map, poilist, busStations, weather, currDate);
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
			final java.util.Map<String, Weather> weather, final Date currDate)
	{
		for (int i = 0; i < poilist.size(); i++)
		{
			final Poi object = poilist.get(i);
			// Window.alert(object.getType());
			final IconOptions iconOptions = new IconOptions();
			// iconSize: [32, 37],
			// iconAnchor: [16, 37],
			// popupAnchor: [0, -33],
			iconOptions.setIconSize(32, 37);
			iconOptions.setIconAnchor(16, 37);
			iconOptions.setPopupAnchor(0, -33);
			int randomInt = Random.nextInt(15);
			if (randomInt == 0)
			{
				iconOptions
						.setIconUrl("images/historical_museum_sponsored.png");
			}
			else
			{
				iconOptions
						.setIconUrl("images/" + object.getAmenity() + ".png");
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
					Popup popup = new Popup(object, busStations, weather,
							currDate, iconOptions);
					map.openPopup(popup.getElement(), latLng);
					AbstractHtmlElementView.notifyAttachRecursive(popup);
				}
			});

		}
	}
}
