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

public class Popup extends DivView
{
	IconOptions iconOptions;

	public Popup(final Poi poi, final ArrayList<BusStation> busStations,
			final Map<String, Weather> weather, final Date currDate,
			IconOptions iconOption)
	{
		this.setStyleName("popup");
		this.appendChild(new SpanView(poi.getName()));
		this.iconOptions = iconOption;

		ButtonView more = new ButtonView("more");
		this.appendChild(more);
		more.addClickHandler(new DMClickHandler()
      {
         @Override
         public void onClick(DMClickEvent event)
         {
            Element body = Document.get().getElementsByTagName("body").getItem(0);
            DetailOverlay detailOverlay = new DetailOverlay(poi, busStations, weather,
            		currDate);
            com.google.gwt.user.client.Element element = detailOverlay.getElement();
            body.appendChild(element);
            AbstractHtmlElementView.notifyAttachRecursive(detailOverlay);
         }
      });

	}

//	static String formatTime(String time)
//	{
//		if (time.startsWith("00d"))
//		{
//			time = time.substring(3);
//		}
//		String[] timeParts = time.split(":");
//		time = timeParts[0] + ":" + timeParts[1];
//		return time;
//	}	
}
