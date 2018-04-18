import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * TripPlanner will handle reading from inputs and generate output from the methods.
 * 
 * Analysis of heuristic:
 * This heuristic is based on finding the least travel times between cities while
 * excluding transfer times at each city and will return 0 once it reaches the goal
 * state. Hence, it is admissible as it will never overestimate.
 * 
 * From various inputs that were tested such as 1 Trip initially, the time taken took
 * about 0m0.061s which expanded 6 nodes eventually. Next on the sample input given
 * (4 Trips), it took about 0m0.109s which expanded 54 nodes. However, with testing on
 * 6 Trips, the nodes expansion greatly went up to 166 nodes with the time taken to solve at
 * around 0m0.224s.
 * 
 * Subsequently on the 8 Trips input, the performance slowed down to about 0m7.096s with
 * 2517 nodes being expanded. To avoid using too much memory, linked lists were used throughout
 * storing of the states instead of array lists.
 * 
 * @author Ho Yee Hong, z3481830
 * 		   4th May 2016
 */

public class TripPlanner {
	
	public static void main(String[] args)
	{
		Scanner sc = null;		
		try
		{		
			sc = new Scanner(new FileReader(args[0]));			
			String firstSpace = "";
			graph = new TripGraph();
			while(sc.hasNext())
			{
				firstSpace = sc.next();			
				if(firstSpace.equals("Transfer"))
				{
					int transferTime = Integer.parseInt(sc.next());
					String city1 = sc.next();
					graph.addNewCity(city1, transferTime);			
				}
				else if(firstSpace.equals("Time"))
				{
					int travelTime = Integer.parseInt(sc.next());
					String city1 = sc.next();
					String city2 = sc.next();
					graph.addLink(city1,city2,travelTime);			
				}
				else if (firstSpace.equals("Trip"))
				{
					String city1 = sc.next();
					String city2 = sc.next();
					graph.addTrip(city1, city2);
				} 	
			}
		}
		catch (FileNotFoundException e) {}
	    finally
	    {
	        if (sc != null) sc.close();
	    }	
		Strategy calculateHeuristic = new AStarHeuristic(graph.getRequiredTrips());
		CityState goal = graph.aStarSearch(calculateHeuristic);
			
		System.out.println(graph.getNumOfCityExpanded()+" nodes expanded");
		System.out.println("cost = "+goal.getTravelTimeSoFar());
		showRoute(goal);
	}
	
	/**
	 * Prints the goal state found in correct format
	 * @param optimum goal state 
	 */
	private static void showRoute(CityState goal) {
		LinkedList<Trip> route = goal.getPathSoFar();
		for(Trip t: route)
		{
			System.out.print("Trip "+t.getFromCity().getCityName()+" to "+t.getToCity().getCityName()+"\n");
		}
	}
	
	private static TripGraph graph;
}
