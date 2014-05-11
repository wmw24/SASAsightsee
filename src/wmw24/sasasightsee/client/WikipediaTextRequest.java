package wmw24.sasasightsee.client;

import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class WikipediaTextRequest
{

	public final String WIKI_URL = "http://de.wikipedia.org/w/api.php?action=parse&prop=text&format=json&section=0&page=";

	String site;

	String wikitext;

	String wikititle;

	DetailOverlay callback;

	public WikipediaTextRequest(String site, DetailOverlay callback)
	{
		this.site = site;
		this.callback = callback;

		String url = WIKI_URL + site;

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setCallbackParam("callback");

		jsonp.requestObject(url, new AsyncCallback<WikipediaText>()
		{

			@Override
			public void onSuccess(WikipediaText response)
			{
				wikitext = response.getText();
				wikititle = response.getTitle();
				WikipediaTextRequest.this.callback.wikipediaReady();
			}

			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub
				return;
			}
		});

	}

	public String getWikitext()
	{
		return this.wikitext;
	}

	public String getWikititle()
	{
		return this.wikititle;
	}
}
