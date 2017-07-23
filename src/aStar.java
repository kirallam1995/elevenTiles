import java.util.*;

public class aStar{	
	
	public static String startData; //stores the initial data
	public static String goalData; //stores the goal data
	
	static class Pair implements Comparable<Pair>{
		
		private double rank;
		private LinkedList<String> route;
		
		public double getRank(){
			return rank; //gets the ranking value
		}
		
		public LinkedList<String> getRoute(){
			return route; //gets the route
		}
		
		Pair (double rank, LinkedList<String> route){
			this.rank = rank; //individual ranks
			this.route = route; //individual routes
		}
		
		public int compareTo(Pair pair){
			if(rank > pair.getRank())return 1;
			else if (rank<pair.getRank())return -1;
			else return 0;
		}
		
		public String toString() {
			return String.format("(" + "%.2f", rank) + "," + route + ")"; //generates debug texts
		}
	}
	
	//Main function
	public static void main(String[] args){
		getData(); //function to capture the input
		LinkedList<String> finalResult = aStarMethod(startData, goalData); //input aStar method with start and goal data into a list
		printFinal(finalResult); //print the list when done
	}

	private static void getData(){
				Scanner scanner = new Scanner(System.in); //Initialise a scanner
			    System.out.print("Copy in the data: "); //Request data input from user
			    String fullData = scanner.nextLine(); //Collect the data
			    scanner.close(); //closes the scanner
			    
			    startData = fullData.substring(0,16); //stores the initial data
			    goalData = fullData.substring(17,33); //stores the final data
	}
	
	//Majority of code in this method taken from Andy King (2017), module CO528 titled "Introduction to Intelligent System"
	private static LinkedList<String> aStarMethod(String start, String goal)
	{
		LinkedList<String> route = new LinkedList<String>();
		route.add(start);
		PriorityQueue<Pair> priorityQueue = new PriorityQueue<Pair>();
		PriorityQueue<Pair> pairs = priorityQueue;
		pairs.add(new Pair(estimateDistance(startData,goalData),route));
		
		while (true){
			//System.out.println(pairs); //for debug purposes
			if (pairs.size() ==0) return null;
			Pair pair = (Pair)pairs.poll();
			route = pair.getRoute();
			String last = route.getLast();
			if(last.equals(goal)) return route;
			LinkedList<String> nextPlace = nextConfigs(route.getLast());
			for(String next:nextPlace){
				if(!route.contains(next))
				{
					LinkedList<String> nextRoute = new LinkedList<String>(route);
					nextRoute.addLast(next);
					double distance = actualDistance(nextRoute);
					distance += estimateDistance(next,goal);
					pairs.add(new Pair(distance, nextRoute));
				}
			}
		}
	}

	private static void printFinal(LinkedList<String>route) {
		System.out.println("\n" + "solution found"); //print a notification to notify user when a solution is found
		
	    //To print the steps, begin with four individual strings
		String a = "";
		String b = "";
		String c = "";
		String d = "";
		
		//for each loop to go through individually stored steps in the final list
		for(String pconfig: route){
			//loop and store their corresponding strings among each step
			char[] pChar = pconfig.toCharArray();
			a += pChar[0] + "" + pChar[1] + "" + pChar[2] + "" + pChar[3] + " ";
			b += pChar[4] + "" + pChar[5] + "" + pChar[6] + "" + pChar[7] + " ";
			c += pChar[8] + "" + pChar[9] + "" + pChar[10] + "" + pChar[11] + " ";
			d += pChar[12] + "" + pChar[13] + "" + pChar[14] + "" + pChar[15] + " ";
		}
		System.out.println(a + "\n" + b + "\n" + c + "\n" + d); //print all the results
	}
		
	private static LinkedList<String> swapText(String locateNow, LinkedList<Integer> validList){
		//Brings over the list of valid moves
		LinkedList<Integer> moveNum = validList;
		LinkedList<String> newConfigList = new LinkedList<String>();
		
		//for each loop to attempt moving
		for(Integer makeMove:moveNum){
			String originalData = locateNow;
			char[] c = originalData.toCharArray();
			char temp = c[originalData.indexOf("_")];
			c[originalData.indexOf("_")] = c[originalData.indexOf("_") + makeMove];
			c[originalData.indexOf("_") + makeMove] = temp;

			originalData = new String(c);
			newConfigList.add(originalData);
		}
		return newConfigList;
	}
	
	private static LinkedList<String> nextConfigs(String locateNow)
	{
		LinkedList<Integer> validList = new LinkedList<Integer>();
		//checking the possibility to go left
		if (locateNow.indexOf("_") != 0 && locateNow.indexOf("_") != 4 && locateNow.indexOf("_") != 8 && locateNow.indexOf("_") != 12 && locateNow.charAt(locateNow.indexOf("_")-1) != '+'){
			validList.add(-1);
		}
		//checking the possibility to go right
		if(locateNow.indexOf("_") != 3 && locateNow.indexOf("_") != 7 && locateNow.indexOf("_") != 11 && locateNow.indexOf("_") != 15 && locateNow.charAt(locateNow.indexOf("_")+1) != '+'){
			validList.add(+1);
		}
		//checking the possibility to go up
		if(locateNow.indexOf("_") != 0 && locateNow.indexOf("_") != 1 && locateNow.indexOf("_") != 2 && locateNow.indexOf("_") != 3 && locateNow.charAt(locateNow.indexOf("_")-4) != '+'){
			validList.add(-4);
		}
		//checking the possibility to go down
		if(locateNow.indexOf("_") != 12 && locateNow.indexOf("_") != 13 && locateNow.indexOf("_") != 14 && locateNow.indexOf("_") != 15 && locateNow.charAt(locateNow.indexOf("_")+4) != '+'){
			validList.add(+4);
		}
		//returns the list of possible moves
		return swapText(locateNow, validList);
	}
	
	private static int actualDistance(LinkedList<String> route) {
		return route.size()-1; //returns the size of route
	}
	
	private static int estimateDistance(String start, String goal) {
		//Hamming method
		int numDiff = 0;
		for(int i=0;i<start.length();i++){
			if(start.charAt(i) != goal.charAt(i)){
				numDiff += 1;
			}
		}
		return numDiff; //stores the number of tiles out of place
	}
}