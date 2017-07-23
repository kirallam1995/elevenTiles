import java.util.*;

public class elevenTiles{
	
	public static String startData; //stores the initial data
	public static String goalData; //stores the goal data
	public static String fullData; //stores the full data
	public static boolean hasSolution = false; //Boolean to determine whether a solution has been found yet
	public static LinkedList<String> finalList = new LinkedList<String>(); //List to store the steps
	
	//Main function
	public static void main(String[] args){
		getData(); //function to capture the input
		int depth = 1; //begins with depth 1
		while(!hasSolution){			
			System.out.println("current depth:" + depth); //printing the current depth number
			LinkedList<String> vistedNodes = new LinkedList<String>(); //initialise a list to store the steps taken 
			depthFirst(startData,goalData,vistedNodes,depth); //begins the depthFirst function with a new list and with the current number of depth allocated
			depth++; //once returned with no result, increase the depth
		}
		if(hasSolution){
			System.out.println("\n" + "solution found"); //print a notification to notify user when a solution is found
		    //To print the steps, begin with four individual strings
			String a = "";
			String b = "";
			String c = "";
			String d = "";
			//for each loop to go through individually stored steps in the final list
			for(String pconfig: finalList){
				//loop and store their corresponding strings among each step
				char[] pChar = pconfig.toCharArray();
				a += pChar[0] + "" + pChar[1] + "" + pChar[2] + "" + pChar[3] + " ";
				b += pChar[4] + "" + pChar[5] + "" + pChar[6] + "" + pChar[7] + " ";
				c += pChar[8] + "" + pChar[9] + "" + pChar[10] + "" + pChar[11] + " ";
				d += pChar[12] + "" + pChar[13] + "" + pChar[14] + "" + pChar[15] + " ";
			}
			System.out.println(a + "\n" + b + "\n" + c + "\n" + d); //print all the results
		}
	}
	
	private static LinkedList<String> depthFirst(String start, String goal, LinkedList<String> pastNodes, int depth){
		if (depth==0){ return null; } //when no solution when all depths allocated are explored
		//when the startData matches the goalData
		else if (start.equals(goal)){
			hasSolution = true; //solution is found and thus while loop would end
			finalList.add(start); //adds the final step to the final list
			LinkedList<String> route = new LinkedList<String>(); //creates a list with all the pastNodes
			route.add(start); //adds the overall steps
			return route; //return the list
		}
		else{
			//bring the result over and carry on
			LinkedList<String> result = nextConfigs(start); //initialise result list to store possible moves 
			for (String nextConfig:result){ //for each loop to process the possible moves
				LinkedList<String> route = depthFirst(nextConfig,goal, pastNodes,depth-1); //initialise a list for processing other moves
				if(route!=null){
					finalList.addFirst(start); //final list to add the start data in this function
					route.addFirst(start); //stores the result with the start data
					return route; //returns the result
				}
			}
			return null;
		}
	}
	
	private static void getData(){
		Scanner scanner = new Scanner(System.in); //Initialise a scanner
	    System.out.print("Copy in the data: "); //Request data input from user
	    fullData = scanner.nextLine(); //Collect the data
	    scanner.close(); //closes the scanner
	    startData = fullData.substring(0,16); //stores the initial data
	    goalData = fullData.substring(17,33); //stores the final data
	}
		
	private static LinkedList<String> swapText(String locateNow, LinkedList<Integer> validList){
		LinkedList<String> newConfigList = new LinkedList<String>(); //initialise a new list
		//for each loop to attempt and swap all possible moves
		for(Integer makeMove:validList){
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
		//initialise a list to store the number of possible moves
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
		return swapText(locateNow, validList); //returns the list of possible moves
	}
}