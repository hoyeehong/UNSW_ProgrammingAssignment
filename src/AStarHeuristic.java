import java.util.LinkedList;

/**
* Helps calculate the heuristic cost in the searching algorithm.
* 
* @author Ho Yee Hong, z3481830
* 		  4th May 2016
*/

public class AStarHeuristic implements Strategy{
		
	@Override
	public int calcHeuristicCost(CityState nodeChild)
	{
		int hCost = this.minTravelTime;
		LinkedList<Trip> toBeCovered = nodeChild.tripsToCover(this.minimumRoute);
		
		Trip lastTripElement = nodeChild.getLastTripFromList();
		if(toBeCovered != null)
		{
			if(nodeChild.getNumOfTrips() > 1)
			{
				hCost = nodeChild.getHeuristicCost();
			}		
			for(Trip t: toBeCovered)
			{
				if(t != null && t.sameTrip(lastTripElement))
				{			
					hCost -= lastTripElement.getTravelTime();
					break;
				}			
			}
		}
		if(hCost < 0)
		{
			return 0;
		}	
		return hCost;
	}

	public AStarHeuristic(LinkedList<Trip> requiredTrips)
	{
		this.minimumRoute = requiredTrips;
		this.minTravelTime = 0;
		for(Trip t : requiredTrips)
		{
			minTravelTime += t.getTravelTime();
		}
	}
	private int minTravelTime;
	private LinkedList<Trip> minimumRoute;
}
