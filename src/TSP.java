
import java.util.ArrayList;

public class TSP {
	public static void main(String args[])
	{
		//requirements:
		//1.every driver has pre-established start(home) and end points(warehouse)
		//3. the cost is represented by the time it takes a driver to get from one location to another
		//4. every location has a time interval in which the driver needs to arrive in
		//5. each driver won't pass trough an already visited node
		
		
		//input:
		// timeCosts[][] will have the cost of travelling(in minutes) between two location (colums and rows) 
		//- the first diagonally will be 0 (distance from same shop to same shop)
		

		int nDrivers = 2;//eg 2 drivers
		int nShops = 3;//eg 3 shops
		int nNodes = nDrivers + nShops + 1 ;//nShops + nDrivers(each driver has a different starting point) + 1(the warehouse)
		int numberOfIterations = 300000;
		
		int warehouseNode = 5;
			
		double timeCosts[][] = {{0, 30, 20, 35, 43, 70}, 
							 {30, 0, 23, 28, 33, 17},
							 {20, 23, 0, 15, 20, 35},
							 {35, 28, 15, 0, 19, 29},
							 {43, 33, 20, 19, 0, 9 },
							 {70, 17, 35, 29, 9, 0 }};//in minutes (multiply by 0.016666 to get hours)
		
		//t0 = 8am- when drivers stars 
		
		//driverHomes[driverIndex] == home node
		//driverHomes[0] = 2; //node 2 is driver 0's starting point
		//driverHomes[1] = 4;
		 ArrayList<Integer> driverHomes = new ArrayList<Integer>();
		 driverHomes.add(2);
		 driverHomes.add(4);
		 
		 ArrayList<ArrayList<Integer>> nodes;
		 ArrayList<ArrayList<Integer>> optNodes;
		 
		//double shopPickupTimeIntervals[] = {10, 16, 8, 16, 8, 16};  //superior limit of the pickup/delivery times; 
		// 16 should be the time for all that shops that don't have a defined pickup time
		 
		 ArrayList<Double> shopPickupTimeIntervals = new ArrayList<Double>();
		 shopPickupTimeIntervals.add((double) 10);
		 shopPickupTimeIntervals.add((double) 16);
		 shopPickupTimeIntervals.add((double) 8);
		 shopPickupTimeIntervals.add((double) 16);
		 shopPickupTimeIntervals.add((double) 8);
		 shopPickupTimeIntervals.add((double) 16);
		
		//optNodes= optimisedTSP.HC(timeCosts, driverHomes, warehouseNode, shopPickupTimeIntervals, nNodes, nDrivers);
		//optimisedTSP.PrintFinalNodes(optNodes);
		//optimisedTSP.globalFitness(100,timeCosts, driverHomes, warehouseNode, shopPickupTimeIntervals, nNodes, nDrivers);
		
		optimisedTSP.globalFitness(numberOfIterations,timeCosts, driverHomes, warehouseNode, shopPickupTimeIntervals, nNodes, nDrivers);
	
	}
	
}
