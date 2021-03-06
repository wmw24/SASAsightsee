package wmw24.sasasightsee.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import bz.davide.dmweb.client.leaflet.IconOptions;
import bz.davide.dmweb.shared.view.AbstractHtmlElementView;
import bz.davide.dmweb.shared.view.ButtonView;
import bz.davide.dmweb.shared.view.DMClickEvent;
import bz.davide.dmweb.shared.view.DMClickHandler;
import bz.davide.dmweb.shared.view.DivView;
import bz.davide.dmweb.shared.view.SpanView;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;

public class Popup extends DivView
{
	IconOptions iconOptions;

	public Popup(final Poi poi, final ArrayList<BusStation> busStations,
			final Map<String, Weather> weather,	IconOptions iconOption)
	{
		this.setStyleName("popup");
		SpanView title = new SpanView(poi.getName());
		title.setStyleName("title");
		this.appendChild(title);
		this.iconOptions = iconOption;

		ButtonView more = new ButtonView("details");
		this.appendChild(more);
		more.addClickHandler(new DMClickHandler()
		{
			@Override
			public void onClick(DMClickEvent event)
			{
				Window.scrollTo(0, 0);
				Element body = Document.get().getElementsByTagName("body")
						.getItem(0);
				DetailOverlay detailOverlay = new DetailOverlay(poi,
						busStations, weather);
				com.google.gwt.user.client.Element element = detailOverlay
						.getElement();
				body.appendChild(element);
				AbstractHtmlElementView.notifyAttachRecursive(detailOverlay);

				Element head = Document.get().getElementsByTagName("head").getItem(0);
				Element script = Document.get().createElement("script");
				script.setAttribute("src", "http://platform.twitter.com/widgets.js");
				head.appendChild(script);

				detailOverlay.dateBox.getDateBox().getGwtDateBox()
						.addValueChangeHandler(new ValueChangeHandler<Date>()
						{
							@Override
							public void onValueChange(
									ValueChangeEvent<Date> event)
							{
								SASAsightsee.currDate = event.getValue();
							}
						});
			}
		});
	}


	// static String formatTime(String time)
	// {
	// if (time.startsWith("00d"))
	// {
	// time = time.substring(3);
	// }
	// String[] timeParts = time.split(":");
	// time = timeParts[0] + ":" + timeParts[1];
	// return time;
	// }
}
