

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;

/**
 * An application with a basic interactive map. You can zoom and pan the map.
 */
public class HelloUnfoldingWorld extends PApplet {

	UnfoldingMap map;

	public void setup() {
		size(1200, 1200, OPENGL);

		map = new UnfoldingMap(this);
                map.setTweening(true);
                Location berlinLocation = new Location(52.5, 13.4);
                Location dublinLocation = new Location(53.35, -6.26);

                // Create point markers for locations
                SimplePointMarker berlinMarker = new SimplePointMarker(berlinLocation);
                SimplePointMarker dublinMarker = new SimplePointMarker(dublinLocation);

                // Add markers to the map
                map.addMarkers(berlinMarker, dublinMarker);
                //map.setZoomRange(12, 15);
		//map.zoomAndPanTo(10, new Location(52.5f, 13.4f));
		MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {
		map.draw();
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { HelloUnfoldingWorld.class.getName() });
	}
}
