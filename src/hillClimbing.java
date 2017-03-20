import java.util.ArrayList;
import java.util.Random;

public class hillClimbing {
	
	//static int driversNodes[][];
	
	static double [][] timeCosts;
	
	//static int[] driverHomes; 
	static int warehouseNode;
	//static double [] shopPickupTimeIntervals;
	//static int visited[];
	static int nNodes;
	//static int[] visitTestedForDriver;
	static ArrayList<Integer> testedNodes;
	static int nDrivers;
	
	static ArrayList<Integer> driverHomes = new ArrayList<Integer>();
	static ArrayList<Double> shopPickupTimeIntervals = new ArrayList<Double>();
	static ArrayList<Integer> visitTestedForDriver = new ArrayList<Integer>();
	static ArrayList<Integer> visited = new ArrayList<Integer>();
	static ArrayList<ArrayList<Integer>> driversNodes = new ArrayList<ArrayList<Integer>>();


	
	public static ArrayList<ArrayList<Integer>> HC (double [][] timeC, ArrayList<Integer> drvHomes, int wHouse, ArrayList<Double> pickupIntervals, int nodes, int drivers) {
		
		timeCosts = timeC;
		driverHomes = drvHomes;
		warehouseNode = wHouse;
		shopPickupTimeIntervals = pickupIntervals;
		nNodes = nodes-1; // nodes without warehouse
		nDrivers = drivers;
		
		
		
		setVisited();
		setDriversHomePoints();
		//loop through the drivers and if it is the first iteration assign a random first location to go to; else loop trough the rest and choose the closest location to him
		
		while(areThereAnyUnivistedPlacesLeft()) {

			travel();
		}
		takeDriversToWarehouse();
		
		return driversNodes;
	}
	
	public static void takeDriversToWarehouse() {
		for(int drvr=0; drvr< nDrivers; drvr++) { //last driver is already at the Warehouse 
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp = driversNodes.get(drvr);
			temp.add(warehouseNode);
			driversNodes.set(drvr, temp);
		}
	}
	
	public static void travel() {
		int iter = 0;
		
		for(int drvr=0; drvr< nDrivers; drvr++) // we use driverHomes to get the number of drivers 
		{
			//System.out.println("Iteration "+ (iter++));
			testedNodes = visited;
			boolean untestedLoc = areAnyMoreUntestedLoc();
			
			if(untestedLoc == false) {//TODO:driver should go to the warehouse
				
				//System.out.println("Iteration "+ (iter++) + " untestedLoc == false");
//				ArrayList<Integer> temp = new ArrayList<Integer>();
//				temp = driversNodes.get(drvr);
//				temp.add(warehouseNode);
//				driversNodes.set(drvr, temp);
//				
			} else  {
				int randomLoc = randomUnvisitedLocation();
				if(!isValidLocation(drvr,randomLoc) && !areAnyMoreUntestedLoc()) { // this is not a solution 
					System.out.println("This is not a solution");
					testedNodes = visited;
					travel();//recall this function
					//return;
				}
//				while(!isValidLocation(drvr,randomLoc) && untestedLoc) {
//					randomLoc = randomUnvisitedLocation();
//					untestedLoc = areAnyMoreUntestedLoc();
//				}
 
				if(randomLoc != -1) { // while there are more untested nodes for fitness
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp = driversNodes.get(drvr);
					
					temp.add(randomLoc);
					//printArrayList(temp);
					
					driversNodes.set(drvr, temp);
					visited.set(randomLoc,1);
					testedNodes = visited;
				}
//			}
				

			}		
		}

	}
	
	public static boolean areAnyMoreUntestedLoc() {
//		int sum = 0;
//		for(int i=0; i<testedNodes.size(); i++) {
//			sum += testedNodes.get(i);
//		}
//		
//		if(sum == nNodes-driverHomes.size()) {
//			return false;
//		}
		for(int i=0; i<testedNodes.size(); i++) {
			if(testedNodes.get(i) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static void setVisited() {
		//System.out.println("Set Visited:");
		for (int i=0; i<nNodes; i++) {
			visited.add(0);
			//System.out.print(visited.get(i)+"    ");
		}
	}
	
	public static void setDriversHomePoints() {
		
		for (int drvr = 0; drvr < driverHomes.size(); drvr++ ) {
			 
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(driverHomes.get(drvr));
			driversNodes.add(drvr,temp);
			
			visited.set(driverHomes.get(drvr),1); 
		}	
//		System.out.println();
//		System.out.println("set Homes:");
//		printArrayList(visited);
	}
	
	public static boolean areThereAnyUnivistedPlacesLeft() {
	
		for(int i=0; i<visited.size(); i++) {
			if(visited.get(i) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static int getRandom(ArrayList<Integer> array) {
		
	    int rnd = new Random().nextInt(array.size());
	    return array.get(rnd);
	}
	
	public static int randomUnvisitedLocation() {
		
		int loc = -1;
		boolean thereAreUnivisted = false;
		//int[] remaining = new int [nNodes];
		 ArrayList<Integer> remaining = new ArrayList<Integer>();
		
		for (int i=0; i<visited.size(); i++) {
		
			if( visited.get(i) == 0 ) {
				remaining.add(i); // populate raiming[] with the univisted nodes
				thereAreUnivisted = true;
			}
		}
//		System.out.println("Visited");
//		printArrayList(visited);
//		System.out.println();
//		System.out.println("Remaining");
//		printArrayList(remaining);
//		System.out.println();
		
		if(thereAreUnivisted) {
			loc = getRandom(remaining);
		}
		return loc;
	}
	
	public static boolean isValidLocation(int driverId, int PossibleLocation) {
		
		if(shopPickupTimeIntervals.get(PossibleLocation) == 16 ) {
			testedNodes = visited;
			return true; 
		}  else {
			testedNodes.set(PossibleLocation, 1);
			double startTime = shopPickupTimeIntervals.get(driverHomes.get(driverId)); 
			double elapsedTime = timeElapsedByDriver(driverId);
			double elapsedTimeHours = elapsedTime * 0.016666;
			if(elapsedTimeHours >= (shopPickupTimeIntervals.get(PossibleLocation) - 1) && elapsedTimeHours <= (shopPickupTimeIntervals.get(PossibleLocation)) ) {
				testedNodes = visited;
				return true;
			}
			return false;
		}
		
	}
	
	
	public static double timeElapsedByDriver (int driverId) {//the time the driver has spent until this point
		int time = 0;
		for(int i=1; i<driversNodes.get(driverId).size(); i++) {
			
			time += timeCosts[i-1][i];
		}
		return time;
	}
	
	static void printArrayList (ArrayList<Integer> temp) {
		for(int j = 0; j < temp.size(); j++ ) {
			System.out.print(temp.get(j) + "    ");
		}
	}
}
