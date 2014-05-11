package wmw24.sasasightsee.client;

import bz.davide.dmweb.shared.view.AbstractHtmlElementView;
import bz.davide.dmweb.shared.view.ButtonView;
import bz.davide.dmweb.shared.view.DMClickEvent;
import bz.davide.dmweb.shared.view.DMClickHandler;
import bz.davide.dmweb.shared.view.DivView;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class DetailOverlay extends DivView
{
   public DetailOverlay()
   {
      DivView background = new DivView("detailbackground");
      this.appendChild(background);
      DivView detail = new DivView("detail");
      this.appendChild(detail);
      ButtonView close = new ButtonView("X");
      close.setStyleName("detailclose");
      detail.appendChild(close);
      DMClickHandler closeHandler = new DMClickHandler()
      {
         @Override
         public void onClick(DMClickEvent event)
         {
            Element body = Document.get().getElementsByTagName("body").getItem(0);
            AbstractHtmlElementView.notifyDetachRecursive(DetailOverlay.this);
            com.google.gwt.user.client.Element myelement = DetailOverlay.this.getElement();
            body.removeChild(myelement);
         }
      };
      close.addClickHandler(closeHandler);
      background.addClickHandler(closeHandler);

      // Content!


   }
}
