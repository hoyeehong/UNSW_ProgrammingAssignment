 import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
* TripGraph generates the graph that contains the cities and travel time between
* each city. Also, this helps the Trip Planner to make optimal use of travel time.
* 
* @author Ho Yee Hong, z3481830
* 		  4th May 2016
*/

public class TripGraph {
	/**
	 * This A* search takes in the heuristic calculated by the
	 * required trips requested and finds the optimal path.
	 * 
	 * @precondition Valid trips must be inputed. 
	 * @param A heuristic strategy
	 * @return The goal state
	 */
	protected CityState aStarSearch(Strategy calculateHeuristic) {
		numOfCityExpanded = 0;		
		int hCost = 0; 
		int oldH = 0;
		int timeToAdd = 0;
		
		LinkedList<CityState> statesVisited = new LinkedList<CityState>();
		CityState rootState = createState("London", null, timeToAdd, 0);
		CityState currentState = rootState, tempState;
		PriorityQueue<CityState> statesToVisit = new PriorityQueue<CityState>();
		statesToVisit.add(rootState);
		
		while(!isGoalState(currentState))
		{	
			currentState = statesToVisit.poll();
			statesVisited.add(currentState);
			numOfCityExpanded++;
			
			LinkedList<Trip> childList = currentState.cityNeighbors(); 
			oldH = currentState.getHeuristicCost();
			City childCity;
			LinkedList<Trip> tripsToAdd;
			
			for(Trip currentTrip: childList)
			{			
				childCity = currentTrip.getToCity();
				tripsToAdd = currentState.getPathSoFar();  
					
				if(tripsToAdd == null)
				{
					tripsToAdd = new LinkedList<Trip>();
				}
				tripsToAdd.add(currentTrip);
				timeToAdd = currentState.getTravelTimeSoFar() + currentTrip.getTravelTime(); 				
				tempState = new CityState(childCity, tripsToAdd, timeToAdd, oldH);
				
				if(!sameVisitedStates(statesVisited, tempState))
				{	
					hCost = calculateHeuristic.calcHeuristicCost(tempState);			
					tempState.setHCost(hCost);
						
					if(!isGoalState(tempState))
					{				
						tempState.addTransferTime(currentTrip.cityTransferTime());	
					}				
					statesToVisit.add(tempState);
				} 
			}		
		}
		return currentState; 
	}
	/**
	 * Creates a new state
	 * @param Name of current city 
	 * @param List of trips so far
	 * @param gCost: the trips's travel time so far
	 * @param hCost: the transfer time at the city
	 * @return State based on the parameters
	 */
	public CityState createState(String cityName, LinkedList<Trip> path, int gCost, int hCost)
	{
		City city = this.findCity(cityName);
		CityState newCityState = new CityState(city, path, gCost, hCost);
		return newCityState;
	}
	/**
	 * Checks if the polled CityState is the goal CityState
	 * @param CityState to be checked
	 * @return True if it covers all necessary trips. False otherwise
	 */
	private boolean sameVisitedStates(LinkedList<CityState> statesVisited, CityState stateToCheck)
	{
		for(CityState cs : statesVisited)
		{
			String city1 = cs.getCityObj().getCityName();
			String city2 = stateToCheck.getCityObj().getCityName();
			if(city1.equals(city2))
			{
				LinkedList<Trip> listOfStatesCovered = cs.tripsToCover(this.getRequiredTrips());
				LinkedList<Trip> listOfStatesVisited = stateToCheck.tripsToCover(this.getRequiredTrips());
				
				if(listOfStatesVisited != null && listOfStatesCovered != null)
				{			
					if(listOfStatesCovered.containsAll(listOfStatesVisited) && listOfStatesVisited.containsAll(listOfStatesCovered))
					{
						return true;
					}			
				}
			}
		}
		return false;
	}
	/**
	 * Checks if the polled State is the goal State
	 * @param State to be checked
	 * @return True if it covers all necessary trips. False otherwise
	 */
	private boolean isGoalState(CityState toCheck)
	{	
		int numOfTrips = this.getRequiredTrips().size();
		if(toCheck.numOfTripsCovered(getRequiredTrips()) == numOfTrips)
		{
			return true;
		} 
		return false;
	}
	
	/**
	 * Adds a city to the Linked List 
	 * @param String name of the city
	 * @param Transfer time at this city
	 */
	public void addNewCity(String cityName, int transferTime)
	{
		City newCity = new City(cityName, transferTime);
		listOfCities.add(newCity);
	}	
	/**
	 * Adds a link between two cities with the travel time
	 * @param String name of first city
	 * @param String name of second city
	 * @param Travel time between the two cities
	 */
	public void addLink(String city1, String city2, int travelTime)
	{
		City c1 = findCity(city1);
		City c2 = findCity(city2);
		
		if(c1 !=null && c2 != null)
		{
			Trip c1ToC2 = new Trip(c1, c2, travelTime);
			Trip c2ToC1 = new Trip(c2, c1, travelTime);
			
			c1.getCityNeighboursList().add(c1ToC2);
			c2.getCityNeighboursList().add(c2ToC1);		
		}	
	}
	/**
	 * Finds the city in the graph
	 * @param String name of the city
	 * @return City
	 */
	protected City findCity(String city)
	{
		City cityFound = null;
		Iterator<City> c = listOfCities.iterator();
		while(c.hasNext() && cityFound == null)
		{
			City currentCity = c.next();
			if(currentCity.getCityName().equals(city))
			{
				cityFound = currentCity;
			}
		}	
		return cityFound;
	}
	/**
	 * Adds a Trip to the list of required trips
	 * @param String name of city departing from
	 * @param String name of city arrives at
	 */
	public void addTrip(String fromCity, String toCity)
	{
		City from = findCity(fromCity);
		City to = findCity(toCity);	
		Trip trip = from.connects(to);
		requiredTrips.add(trip);
	}
	/**
	 * Gets the list of required trips
	 * @return List of required trips
	 */
	protected LinkedList<Trip> getRequiredTrips(){
		return requiredTrips;
	}
	/**
	 * Gets the number of cities expanded in the search
	 * @return Number of expanded cities
	 */
	protected int getNumOfCityExpanded()
	{
		return numOfCityExpanded;
	}
	
	public TripGraph()
	{
		listOfCities = new LinkedList<City>();
		requiredTrips = new LinkedList<Trip>();
		numOfCityExpanded = 0;
	}
	
	private LinkedList<City> listOfCities;
	private LinkedList<Trip> requiredTrips;
	private int numOfCityExpanded;
}
