/**
 * 
 */
package wmw24.sasasightsee.client;

import java.util.HashMap;
import java.util.Map;

/**
 * Weather information for a certain day
 * 
 * @author mdw
 *
 */
public class Weather
{
	/**
	 * Description
	 */
	private Map<String, String> description = new HashMap<String, String>();
	
	/**
	 * Weather image URL
	 */
	private Map<String, String> imageURL = new HashMap<String, String>();
	
	/**
	 * Minimal temperature
	 */
	private Map<String, Integer> tempMin = new HashMap<String, Integer>();
	
	/**
	 * Maximum temperature
	 */
	private Map<String, Integer> tempMax = new HashMap<String, Integer>();

	/**
	 * @return the description
	 */
	public String getDescription(String key) {
		return description.containsKey(key) ? description.get(key) : description.get(null);
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String key, String description) {
		this.description.put(key, description);
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL(String key) {
		return imageURL.containsKey(key) ? imageURL.get(key) : imageURL.get(null);
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String key, String imageURL) {
		this.imageURL.put(key, imageURL);
	}

	/**
	 * @return the tempMin
	 */
	public Integer getTempMin(String key) {
		return tempMin.containsKey(key) ? tempMin.get(key) : tempMin.get(null);
	}

	/**
	 * @param tempMin the tempMin to set
	 */
	public void setTempMin(String key, Integer tempMin) {
		this.tempMin.put(key, tempMin);
	}

	/**
	 * @return the tempMax
	 */
	public Integer getTempMax(String key) {
		return tempMax.containsKey(key) ? tempMax.get(key) : tempMax.get(null);
	}

	/**
	 * @param tempMax the tempMax to set
	 */
	public void setTempMax(String key, Integer tempMax) {
		this.tempMax.put(key, tempMax);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Weather [description=" + description + ", imageURL=" + imageURL
				+ ", tempMin=" + tempMin + ", tempMax=" + tempMax + "]";
	}
}
