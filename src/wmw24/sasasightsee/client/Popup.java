
package wmw24.sasasightsee.client;

import it.bz.tis.sasabus.backend.shared.SASAbusDBDataReady;
import it.bz.tis.sasabus.backend.shared.travelplanner.ConRes;
import it.bz.tis.sasabus.html5.client.SASAbusDBClientImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import bz.davide.dmweb.client.leaflet.DistanceCalculator;
import bz.davide.dmweb.shared.view.ButtonView;
import bz.davide.dmweb.shared.view.DMClickEvent;
import bz.davide.dmweb.shared.view.DMClickHandler;
import bz.davide.dmweb.shared.view.DivView;
import bz.davide.dmweb.shared.view.ImgView;
import bz.davide.dmweb.shared.view.SpanView;

import com.google.gwt.core.client.Callback;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Geolocation.PositionOptions;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public class Popup extends DivView
{
   ButtonView            buttonView;
   ArrayList<BusStation> busStations;

   BusStation            nearestToPoi;

   public Popup(Poi poi, ArrayList<BusStation> busStations, Map<String, Weather> weather,
		   Date currDate)
   {
      this.busStations = busStations;
      this.setStyleName("popup");
      this.appendChild(new SpanView(poi.getName()));

      String today = DateTimeFormat.getFormat("yyyy-MM-dd").format(currDate);
      Date date = (Date)currDate.clone();
      CalendarUtil.addDaysToDate(date, 1);
      String tomorrow = DateTimeFormat.getFormat("yyyy-MM-dd").format(date);      
      
      DivView weatherDiv = new DivView("weather");

      DivView todayWeather = new DivView("today");
      
      ImgView imageView = new ImgView(weather.get(today).getImageURL("2"));
      SpanView spanView = new SpanView(weather.get(today).getDescription("2")
    		  + " - " + weather.get(today).getTempMin("2") + "-"
    		  + weather.get(today).getTempMax("2") + "°");
      
      todayWeather.appendChild(imageView);
      todayWeather.appendChild(spanView);

      weatherDiv.appendChild(todayWeather);

      DivView tomorrowWeather = new DivView("tomorrow");

      imageView = new ImgView(weather.get(tomorrow).getImageURL("2"));
      spanView = new SpanView(weather.get(tomorrow).getDescription("2")
    		  + " - " + weather.get(tomorrow).getTempMin("2") + "-"
    		  + weather.get(tomorrow).getTempMax("2") + "°");

      tomorrowWeather.appendChild(imageView);
      tomorrowWeather.appendChild(spanView);

      weatherDiv.appendChild(tomorrowWeather);

      this.appendChild(weatherDiv);


      this.buttonView = new ButtonView("With bus here");
      this.appendChild(this.buttonView);
      this.buttonView.addClickHandler(new DMClickHandler()
      {

         @Override
         public void onClick(DMClickEvent event)
         {
            Popup.this.onButtonClick();
         }

      });


      this.nearestToPoi = this.nearest(poi.getLat(), poi.getLon());

   }

   private void onButtonClick()
   {
      this.appendChild(new SpanView("Nearest busStation: " + this.nearestToPoi.getName()));

      this.buttonView.setLabel("Searching your position ...");
      PositionOptions positionOptions = new PositionOptions();
      positionOptions.setHighAccuracyEnabled(true);
      positionOptions.setTimeout(10000);

      // http://openlayers.org/dev/examples/geolocation.html

      Geolocation geoloc = Geolocation.getIfSupported();
      if (geoloc != null)
      {
         geoloc.getCurrentPosition(new Callback<Position, PositionError>()
         {
            @Override
            public void onSuccess(Position result)
            {

               double lon = result.getCoordinates().getLongitude();
               double lat = result.getCoordinates().getLatitude();
               double accuracy = result.getCoordinates().getAccuracy();

               BusStation nearestToYou = Popup.this.nearest(lat, lon);
               Popup.this.appendChild(new SpanView("Nearest busStation: " + nearestToYou.getName()));

               try
               {
                  SASAbusDBClientImpl.singleton.calcRoute(nearestToYou.getId(), Popup.this.nearestToPoi.getId(), 201405090900L, new SASAbusDBDataReady<ConRes>()
                  {

                     @Override
                     public void ready(ConRes data)
                     {
                        Popup.this.displayRoute(data);
                     }
                  });
               }
               catch (Exception e)
               {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }

               //Window.alert("lat: " + lat + " lon: " + lon + " acc (meter): " + accuracy);

               //LatLng position = new LatLng(lat, lon);
               //SASAbusMapAttachHandler.this.map.leafletMap.setView(position, 16);
            }

            @Override
            public void onFailure(PositionError reason)
            {
               Window.alert("Failure: " + reason.getMessage());
            }
         },
                                   positionOptions);
      }
      else
      {
         Window.alert("Your browser does not support localization");
      }
   }

   private void displayRoute(ConRes conRes)
   {
      this.appendChild(new RouteDetail(conRes));
   }

   private BusStation nearest(double lat, double lon)
   {
      BusStation best = this.busStations.get(0);
      for (int i = 1; i < this.busStations.size(); i++)
      {
         BusStation busStation = this.busStations.get(i);

         double lat1 = best.getBusStops().get(0).getLat();
         double lon1 = best.getBusStops().get(0).getLon();

         double lat2 = busStation.getBusStops().get(0).getLat();
         double lon2 = busStation.getBusStops().get(0).getLon();

         double distance1 = DistanceCalculator.distanceMeter(lat1, lon1, lat, lon);
         double distance2 = DistanceCalculator.distanceMeter(lat2, lon2, lat, lon);

         if (distance2 < distance1)
         {
            best = busStation;
         }
      }
      return best;
   }

//   static String formatTime(String time)
//   {
//      if (time.startsWith("00d"))
//      {
//         time = time.substring(3);
//      }
//      String[] timeParts = time.split(":");
//      time = timeParts[0] + ":" + timeParts[1];
//      return time;
//   }
}
