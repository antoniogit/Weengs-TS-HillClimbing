import java.util.Random;

public class hcTest {
	
static int driversNodes[][] = null;
	
	static double [][] timeCosts; 
	static int[] driverHomes; 
	static int warehouseNode;
	static double [] shopPickupTimeIntervals;
	static int[] visited;
	static int nNodes;
	
	public static int[][] HC (double [][] timeC, int[] drvHomes, int wHouse, double pickupIntervals[], int nodes) {
		
		timeCosts = timeC;
		driverHomes = drvHomes;
		warehouseNode = wHouse;
		shopPickupTimeIntervals = pickupIntervals;
		nNodes = nodes; // nodes without warehouse
		
		
		setDriversHomePoints();
		
		//TODO: go trough drivers array and choose a random point(closest as time requirements and delivery)->fitnessDrive to go if it fits in the time deadlines
		
		
		return driversNodes;
	}
	
	public static void setVisited() {
		
		for (int i=0; i<nNodes; i++) {
			visited[i] = 0;
		}
	}
	
	public static void setDriversHomePoints() {
		
		for (int drvr = 0; drvr < driverHomes.length; drvr++ ) {
			
			driversNodes[drvr][0]  =  driverHomes[drvr];
			visited[driverHomes[drvr]] = 1; 
		}		
	}
	
	public static boolean travel(int driverId) {
		
		//choose random unvisited location
		int loc = 0;
		int k = 0;
		int[] remaining = null;
		
		for (int i=0; i<visited.length; i++) {
			
			if( visited[i] == 0 ) {
				remaining[k++] = i; // populate raiming[] with the univisted nodes
			}
		}
		
		for(int i=0; i<remaining.length; i++) {
			
			loc = remaining[i];
			if (isValidFitness(driverId, loc) ) {
				
				driversNodes[driverId][(driversNodes[driverId].length)] = loc;
				visited[loc] = 1; 
				return true;
			}
		}
		
		return false;
		//randomUnvisitedLocation();
		//TODO: will the driver get there in time for the pickup time slot?
		
	}
	
	public static int getRandom(int[] array) {
		
	    int rnd = new Random().nextInt(array.length);
	    return array[rnd];
	}
	
	public static int randomUnvisitedLocation() {
		
		int loc = 0;
		int k = 0;
		int[] remaining = null;
		int[] remainingTested;
		
		for (int i=0; i<visited.length; i++) {
			
			if( visited[i] == 0 ) {
				remaining[k++] = i; // populate raiming[] with the univisted nodes
			}
		}
		
		loc = getRandom(remaining);
		
		return loc;
	}
	
	public static boolean isValidFitness(int driverId, int PossibleLocation) {
		
		if(shopPickupTimeIntervals[PossibleLocation] == 16 ) {
			return true; 
		}  else {
			double startTime = shopPickupTimeIntervals[driverHomes[driverId]];
			double elapsedTime = timeElapsedByDriver(driverId);
			double elapsedTimeHours = elapsedTime * 0.016666;
			if(elapsedTimeHours >= (shopPickupTimeIntervals[PossibleLocation] - 1) && elapsedTimeHours <= (shopPickupTimeIntervals[PossibleLocation]) ) {
				return true;
			}
			return false;
		}
		
	}
	
	
	public static double timeElapsedByDriver (int driverId) {//the time the driver has spent until this point
		int time = 0;
		for(int i=1; i<driversNodes[driverId].length; i++) {
			
			time += timeCosts[i-1][i];
		}
		return time;
	}
}
