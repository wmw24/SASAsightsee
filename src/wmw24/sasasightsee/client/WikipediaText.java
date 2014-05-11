package wmw24.sasasightsee.client;

import com.google.gwt.core.client.JavaScriptObject;

public class WikipediaText extends JavaScriptObject
{
	protected WikipediaText()
	{

	}

	public final native String getTitle() /*-{
		return this.parse.title;
	}-*/;

	public final native String getText() /*-{
		return this.parse.text;
	}-*/;

}
