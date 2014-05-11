
package wmw24.sasasightsee.client;

import bz.davide.dmweb.shared.view.DMClickEvent;
import bz.davide.dmweb.shared.view.DMClickHandler;
import bz.davide.dmweb.shared.view.DivView;
import bz.davide.dmweb.shared.view.SpanView;

public class Menu extends DivView
{
   public Menu()
   {
      super("menu");
      this.appendChild(new SpanView("SASAsightsee (C) 2014 Markus Windegger, Davide Montesin, Matthias Dieter Wallnoefer"));
      this.addClickHandler(new DMClickHandler()
      {

         @Override
         public void onClick(DMClickEvent event)
         {

         }
      });
   }
}
