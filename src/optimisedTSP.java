
import java.util.ArrayList;
import java.util.Random;

//Pseudo Code:

//main(number of iterations, a matrix with the time costs to get from point a to b, an array specifying which nodes are the driver homes, the warehouse node, an array with the shops pickup time superior limit)
  //initialize current iteration to 0
  //intitalize the best Fitness so far
  //WHILE number oF iterations is larger than current iteration
	//run HC()
	//IF the last HC() 's fitness is smaller than the best Fitness so far
	  //best fitness so far gets the value of HC()
	//ENDIF
  //ENDWHILE
  //print the best solution so far

//HC()
  //intialize the nodes as being not visited
  //initialize the driver's homes and set them as visited
  //initialize the already-tested-for-validity-places as being the same as the visited places for start 

  //WHILE there are unvisited places
  //    travel()
  // ENDWHILE
  //take drivers to warehouse
  //return solution

//travel()
  //  IF there are unvisited places and all of them are tested
  //      END with a no solution result
  //  ELSE
  //      choose a random driver as current driver
  //      choose a random unvisited place as current place
  //      IF current place is in already-tested-for-validity-place
  //          travel()
  //          set the already-tested-for-validity-places equal to visited places
  //      ENDIF

  //      IF current place is valid and not in already-tested-for-validity-place 
  //          add node to driver's route
  //          add current location to visited places
  //      ENDIF
  //  ENDIF

public class optimisedTSP {
	
	static double [][] timeCosts;
	static int warehouseNode;
	static int nNodes;
	static ArrayList<Integer> testedNodes = new ArrayList<Integer>();
	static int nDrivers;
	static boolean isSolution = true;
	
	static ArrayList<Integer> driverHomes = new ArrayList<Integer>();
	static ArrayList<Double> shopPickupTimeIntervals = new ArrayList<Double>();
	static ArrayList<Integer> visitTestedForDriver = new ArrayList<Integer>();
	static ArrayList<Integer> visited = new ArrayList<Integer>();
	static ArrayList<ArrayList<Integer>> driversNodes = new ArrayList<ArrayList<Integer>>();

	
	public static ArrayList<ArrayList<Integer>> HC (double [][] timeC, ArrayList<Integer> drvHomes, int wHouse, ArrayList<Double> pickupIntervals, int nodes, int drivers) {
		
		
		visited.clear();
		
		
		timeCosts = timeC;
		driverHomes = drvHomes;
		warehouseNode = wHouse;
		shopPickupTimeIntervals = pickupIntervals;
		nNodes = nodes-1; // nodes without warehouse
		nDrivers = drivers;
		
		
		setVisited();
		setDriversHomePoints();
		testedNodes = visited;
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
		
		boolean untestedLoc = areAnyMoreUntestedLoc();
		
		if(untestedLoc == false) { //this is saying while there are still unvisited places but all of them are tested, this is not a solution
			//System.out.println("This is not a solution");
			isSolution = false;
			return;
		} else  {
			int drvr = chooseRandomDriver(nDrivers);
			int randomLoc = randomUnvisitedLocation();
			if(!isValidLocation(drvr,randomLoc) && !areAnyMoreUntestedLoc()) { //if this driver can't go to the currently tested location, another driver will try to go to a random location
					testedNodes = visited;
					travel();
			}
 
			if(randomLoc != -1) { //if the current location is valid and untested, the driver travels there
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp = driversNodes.get(drvr);			
				temp.add(randomLoc);
				driversNodes.set(drvr, temp);
				visited.set(randomLoc,1);
				testedNodes = visited;
			}
		}		
	}
	
	public static boolean areAnyMoreUntestedLoc() {
		for(int i=0; i<testedNodes.size(); i++) {
			if(testedNodes.get(i) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean areThereAnyUnivistedPlacesLeft() {
		
		for(int i=0; i<visited.size(); i++) {
			if(visited.get(i) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static void setVisited() {
		for (int i=0; i<nNodes; i++) {
			visited.add(0);
		}
	}
	
	public static void setDriversHomePoints() {
		
		for (int drvr = 0; drvr < driverHomes.size(); drvr++ ) {
			 
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(driverHomes.get(drvr));
			driversNodes.add(drvr,temp);
			
			visited.set(driverHomes.get(drvr),1); 
		}	
	}
	
	public static Integer chooseRandomDriver(int drivers) {
		
		Random rand = new Random();;
		int max = drivers-1;
	    int randomNum = rand.nextInt((max - 0) + 1) + 0;

	    return randomNum;
	}
	
	public static int getRandom(ArrayList<Integer> array) {
		
	    int rnd = new Random().nextInt(array.size());
	    return array.get(rnd);
	}
	
	public static int randomUnvisitedLocation() {
		
		int loc = -1;
		boolean thereAreUnivisted = false;
		ArrayList<Integer> remaining = new ArrayList<Integer>();
		remaining.clear();
		//remaining = (ArrayList<Integer>) null;
		
		for (int i=0; i<visited.size(); i++) {
			
			if( visited.get(i) == 0 ) {
				remaining.add(i); // populate raiming[] with the univisted nodes
				thereAreUnivisted = true;
			}
		}
		
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
	
//	Print result
	public static void PrintFinalNodes(ArrayList<ArrayList<Integer>> nodes) {
		for (int i=0; i< nodes.size(); i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp = nodes.get(i);
			System.out.println("Driver "+ i);
			for(int j = 0; j < temp.size(); j++ ) {
				System.out.print(temp.get(j) + "    ");
			}
			System.out.println();
		}
		nodes.clear();
	}
	
	public static double fitness() {
		double driverTime = 0;
		double maxTime = 0;
		for(int i=0; i<driversNodes.size(); i++) {
			driverTime =timeElapsedByDriver(i);
			if(driverTime > maxTime) {
				maxTime = driverTime;
			}
		}
		return maxTime;
	}
	
	public static void globalFitness(int numberOfIterations, double [][] timeC, ArrayList<Integer> drvHomes, int wHouse, ArrayList<Double> pickupIntervals, int nodes, int drivers) {
		
		ArrayList<ArrayList<Integer>> currentSolution = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> mostEfficientSolution = new ArrayList<ArrayList<Integer>>();
		
		double currentFitness;
		double lowestFitness = Double.POSITIVE_INFINITY;
		
		for(int i=0; i< numberOfIterations; i++) {
			
			currentSolution = HC(timeC, drvHomes, wHouse, pickupIntervals, nodes, drivers);			
			currentFitness = fitness();
			if(isSolution == true && currentFitness < lowestFitness) {
				lowestFitness = currentFitness;
				mostEfficientSolution.clear();
				mostEfficientSolution= (ArrayList<ArrayList<Integer>>) currentSolution.clone();
				//PrintFinalNodes(mostEfficientSolution);
			}
			
			currentSolution.clear();
		}
		System.out.println("The most efficient solution is:");
		if(mostEfficientSolution.size() > 0) {
			PrintFinalNodes(mostEfficientSolution);
		}
		else {
			System.out.println("No solution was found for the given data set");
		}
	}
}
