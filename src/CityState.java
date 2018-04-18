import java.util.LinkedList;

/**
* CityState contains the information that the path has reached to a certain
* city with the current travel time cost and transfer time added up while
* comparing each state to eventually sorting the state with the least cost
* being at the top of the priority queue.
* 
* @author Ho Yee Hong, z3481830
* 		  4th May 2016
*/

public class CityState implements Comparable<CityState>{
	/**
	 * Finds trips which still need to be found.
	 * 
	 * @param List of the required trips
	 * @return List of required trips that are not covered by the path so far
	 */
	public LinkedList<Trip> tripsToCover(LinkedList<Trip> trips)
	{
		LinkedList<Trip> clone = this.getPathSoFar();
		Trip path;
		Trip[] pathSoFar;
		LinkedList<Trip> tripListToReturn = new LinkedList<Trip>();
		
		if(clone != null)
		{
			pathSoFar = new Trip[clone.size()];
			clone.toArray(pathSoFar);
			
			for(Trip required: trips)
			{
				for(int i=0; i<clone.size(); i++)
				{
					path = pathSoFar[i];
					if(path != null && required.sameTrip(path))
					{
						tripListToReturn.add(path);
						pathSoFar[i] = null;					
						break;
					}
				}
			}		
			return tripListToReturn;
		}
		return null;
	}
	
	/**
	 * Gives number of trips which are covered in the path so far
	 * If at initial state, it'll return zero
	 * @param Linked List of required trips
	 * @return integer
	 */
	public int numOfTripsCovered(LinkedList<Trip> trips)
	{
		LinkedList<Trip> clone = this.getPathSoFar();
		
		int numFound = 0;
		Trip path;
		Trip[] pathSoFar;
		
		if(clone != null)
		{
			pathSoFar = new Trip[clone.size()];
			clone.toArray(pathSoFar);		
			for(Trip required: trips)
			{
				for(int i=0; i<clone.size(); i++)
				{
					path = pathSoFar[i];
					if(path != null && required.sameTrip(path))
					{
						pathSoFar[i] = null;
						numFound++; 
						break;
					}
				}
			}
		}
		return numFound;
	}
	
	/**
	 * Gets the number of trips in path
	 * @return integer 
	 */
	public int getNumOfTrips()
	{
		return pathSoFar.size();
	}
	/**
	 * Updates the heuristic cost by adding the transfer time 
	 * at the current city
	 * @param integer transfer time
	 */
	public void addTransferTime(int transferTime)
	{
		totalTimeSoFar += transferTime;
	}
	/**
	 * Set the heuristic of the state
	 * @param integer for heuristic value 
	 */
	public void setHCost(int cost)
	{
		heuristicCost = cost;
	}
	
	/**
	 * Clones the path so far. No reference to original list.
	 * Gives NULL if at initial state
	 * @return A deep copy of the path so far
	 */
	public LinkedList<Trip> getPathSoFar()
	{	
		if(pathSoFar == null)
		{
			return null;
		}
		LinkedList<Trip> pathClone = new LinkedList<Trip>();
		for(Trip item: pathSoFar)
		{		
			pathClone.add(item);
		}
		return pathClone;
	}
	
	/**
	 * Gets City object
	 * @return City
	 */
	public City getCityObj()
	{
		return city;
	}
	/**
	 * Gives string representation of the current city object
	 * @return String
	 */
	public String getCityNamefrmState()
	{
		return city.getCityName();
	}
	/**
	 * Gets the current city's neighbors
	 * @return List of the city's neighbors 
	 */
	public LinkedList<Trip> cityNeighbors()
	{
		return city.getCityNeighboursList();
	}
	/**
	 * Gives the travel time of the path so far including transfer times
	 * @return total travel time so far
	 */
	public int getTravelTimeSoFar()
	{
		return totalTimeSoFar;
	}
	/**
	 * Gets the heuristic cost of the state
	 * @return heuristic cost
	 */
	public int getHeuristicCost()
	{
		return heuristicCost;
	}
	/**
	 * Gets the last trip element from the list
	 * @return Last trip element
	 */
	public Trip getLastTripFromList()
	{
		return pathSoFar.getLast();
	}

	@Override
	public int compareTo(CityState cityToCompare)
	{
		return this.calculateT() - cityToCompare.calculateT();
	}
	private int calculateT(){
		return heuristicCost + totalTimeSoFar;
	}
	
	public CityState(City cityNode, LinkedList<Trip> trip, int time, int h)
	{
		this.city = cityNode;
		this.pathSoFar = trip;
		this.totalTimeSoFar = time;
		this.heuristicCost = h;
	}
	private City city;
	private int totalTimeSoFar;
	private int heuristicCost;
	private LinkedList<Trip> pathSoFar;
}
