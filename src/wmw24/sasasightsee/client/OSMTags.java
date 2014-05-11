package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;

public class OSMTags extends JavaScriptObject
{
	protected OSMTags()
	{

	}

	public final native String getName() /*-{
		return this.name;
	}-*/;

	public final native String getAmenity() /*-{
		return this.amenity;
	}-*/;

	public final native String getTourism() /*-{
		return this.tourism;
	}-*/;

	public final native String getAddrCity() /*-{
		return this.addrcity;
	}-*/;

	public final native String getAddrCountry() /*-{
		return this.addrcountry;
	}-*/;

	public final native String getAddrHousename() /*-{
		return this.addrhousename;
	}-*/;

	public final native String getAddrHouseNameDe() /*-{
		return this.addrhousenamede;
	}-*/;

	public final native String getAddrHouseNameIt() /*-{
		return this.addrhousenameit;
	}-*/;

	public final native String getAddrHousenumber() /*-{
		return this.addrhousenumber;
	}-*/;

	public final native String getAddrPostcode() /*-{
		return this.addrpostcode;
	}-*/;

	public final native String getAddrStreet() /*-{
		return this.addrstreet;
	}-*/;

	public final native String getEmail() /*-{
		return this.email;
	}-*/;

	public final native String getFax() /*-{
		return this.fax;
	}-*/;

	public final native String getOperator() /*-{
		return this.operator;
	}-*/;

	public final native String getPhone() /*-{
		return this.phone;
	}-*/;

	public final native String getWebsite() /*-{
		return this.website;
	}-*/;

	public final native String getWikipedia() /*-{
		return this.wikipedia;
	}-*/;

	public final native String getWheelchair() /*-{
		return this.wheelchair;
	}-*/;

}
