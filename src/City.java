import java.util.LinkedList;

/**
* Contains information of each city with its transfer time and 
* its neighboring cities.
* 
* @author Ho Yee Hong, z3481830
* 		  4th May 2016
*/

public class City {
	/**
	 * Gives the city name
	 * @return String name of the city
	 */
	public String getCityName(){
		return cityName;
	}
	/**
	 * Gives the transfer time at the city
	 * @return Transfer time
	 */
	public int getTransferTime(){
		return transferTime;
	}
	/**
	 * Gets the list of neighbors
	 * @return List of neighbors
	 */
	public LinkedList<Trip> getCityNeighboursList(){
		return listOfNeighbours;
	}
	/**
	 * Try to find the neighbor city and connects both cities
	 * @param Arriving city
	 * @return Neighbor city found
	 */
	public Trip connects(City neighbour){
		Trip neighbourfound = null;
		for(Trip endCity: listOfNeighbours)
		{
			if(endCity.getToCity().equals(neighbour))
			{
				neighbourfound = endCity;
			}
		}		
		return neighbourfound;
	}
	@Override
	public String toString()
	{
		StringBuffer str = new StringBuffer();
		str.append(getCityName()+":\n");
		for(Trip neighbors: listOfNeighbours){
			str.append("\t->"+neighbors.getToCity().getCityName()+" (Travel Time:"+neighbors.getTravelTime()+")\n");
		}	
		return str.toString();
	}

	public City(String cityName, int transferTime){
		this.cityName = cityName;
		this.transferTime = transferTime;
		listOfNeighbours = new LinkedList<Trip>();
	}
	private String cityName;
	private int transferTime;
	private LinkedList<Trip> listOfNeighbours;
	
}
