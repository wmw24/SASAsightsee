package wmw24.sasasightsee.client;

import it.bz.tis.sasabus.backend.shared.travelplanner.BasicStop;
import it.bz.tis.sasabus.backend.shared.travelplanner.ConRes;
import it.bz.tis.sasabus.backend.shared.travelplanner.ConSection;
import it.bz.tis.sasabus.backend.shared.travelplanner.Connection;
import bz.davide.dmweb.shared.view.DivView;
import bz.davide.dmweb.shared.view.SpanView;

public class RouteDetail extends DivView
{

   public RouteDetail(ConRes conRes)
   {
      super("route-result");

      Connection connection = conRes.getConnectionList().getConnections()[0];

      this.addStyleName("bus-trip-detail");
      for (ConSection conSection : connection.getConSectionList().getConSections())
      {
         DivView busName = new DivView("bus-name");
         if (conSection.getWalks().length > 0)
         {
            busName.appendChild(new SpanView("Walk: " +formatTime(conSection.getWalks()[0].getDuration().getTime())));
            this.appendChild(busName);
            continue;
         }
         busName.appendChild(new SpanView(conSection.getJourneys()[0].getBusLineNumber()));
         this.appendChild(busName);
         final BasicStop[] basicStop = conSection.getJourneys()[0].getPassList().getBasicStops();
         String time = "";
         if (basicStop[0].getArr() != null)
         {
            time = formatTime(basicStop[0].getArr().getTime());
         }
         this.appendChild(newRow(time, basicStop[0].getStation().getName()));


         time = "";
         if (basicStop[basicStop.length - 1].getArr() != null)
         {
            time = formatTime(basicStop[basicStop.length - 1].getArr().getTime());
         }
         this.appendChild(newRow(time, basicStop[basicStop.length - 1].getStation().getName()));
      }
      DivView busName = new DivView("bus-name");
      //busName.appendChild(Icon.newRouteEndIcon());
      busName.appendChild(new SpanView("you_arrive"));
      this.appendChild(busName);
   }

   static DivView newRow(String time, String name)
   {
      DivView row = new DivView("row");
      SpanView timeLabel = new SpanView(time);
      timeLabel.setStyleName("time");
      row.appendChild(timeLabel);
      row.appendChild(new SpanView(name));
      return row;
   }

   static String formatTime(String time)
   {
      if (time.startsWith("00d"))
      {
         time = time.substring(3);
      }
      String[] timeParts = time.split(":");
      time = timeParts[0] + ":" + timeParts[1];
      return time;
   }
}
