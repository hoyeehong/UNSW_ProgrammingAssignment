/**
* Contains relationships between two cities with its travel time 
* needed to travel from one city to another.
* 
* @author Ho Yee Hong, z3481830
* 		  4th May 2016
*/

public class Trip {
	/**
	 * Gives the City departed from
	 * @return City
	 */
	public City getFromCity()
	{
		return fromCity;
	}
	/**
	 * Gives the City destination
	 * @return City
	 */
	public City getToCity()
	{
		return toCity;
	}
	/**
	 * Gives travel time for this trip
	 * @return Travel time
	 */
	public int getTravelTime()
	{
		return travelTime;
	}
	/**
	 * Gives transfer time of the destination city
	 * @return Integer travel time
	 */
	public int cityTransferTime()
	{
		return getToCity().getTransferTime();
	}
	
	/**
	 * Checks if two trips are equal. They are equal if they both are duplicated trips.
	 * @param Trip to be compared
	 * @return True if they are equal. False otherwise
	 */
	public boolean sameTrip(Trip path)
	{	
		String departed = path.getFromCity().getCityName();
		String arrival = path.getToCity().getCityName();
	
		if(fromCity.getCityName().equals(departed) && toCity.getCityName().equals(arrival))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return fromCity.getCityName()+"-> "+toCity.getCityName();
	}

	public Trip(City fromCity, City toCity, int travelTime){
		this.fromCity = fromCity;
		this.toCity = toCity;
		this.travelTime = travelTime;
	}
	
	private City fromCity;
	private City toCity;
	private int travelTime;
}
