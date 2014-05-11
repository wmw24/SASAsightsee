package wmw24.sasasightsee.client;

import java.util.HashMap;
import java.util.Map;

public class Poi
{
	private double lat = 0;

	private double lon = 0;

	private String name = "";

	private String amenity = "";

	private Map<String, String> attr = new HashMap<String, String>();

	public double getLat()
	{
		return this.lat;
	}

	public void setLat(double lat)
	{
		this.lat = lat;
	}

	public double getLon()
	{
		return this.lon;
	}

	public void setLon(double lon)
	{
		this.lon = lon;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAttr(String key)
	{
		return this.attr.get(key);
	}

	public void setAttr(String key, String value)
	{
		this.attr.put(key, value);
	}

	public String getAmenity()
	{
		return this.amenity;
	}

	public void setAmenity(String amenity)
	{
		this.amenity = amenity;
	}

	public Map<String, String> getAttrs()
	{
	   return this.attr;
	}

}
