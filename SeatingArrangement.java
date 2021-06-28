package prepration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import prepration.Tree.TreeNode;

public class SeatingArrangement {

	public static void main(String[] args) {
		
		//Input as seats rows and columns in each bay
		int[][] seats = {{3,2},{4,3},{2,3},{3,4}};
		
		//Input as No. of Passengers list, 
		//For sample 30 passengers.
		LinkedList<String> queue = new LinkedList<String>();
		for(int i=1 ; i<=100; i++) {
			queue.add("Passenger "+ i);
		}
		
		Map<String, Integer> seatCountMap = getSeatCountBasedOnType(seats);
		printPassengersWithSeats(queue, seatCountMap);
	}

	/**
	 * printPassengersWithSeats is used to get passengers with alloted seat number and seat type.
	 * 
	 * @param queue
	 * @param seatCountMap
	 */
	private static void printPassengersWithSeats(LinkedList<String> queue, Map<String, Integer> seatCountMap) {
		int asileSeatCount = seatCountMap.get("asile");
		int middleSeatCount = seatCountMap.get("middle");
		int windowSeatCount = seatCountMap.get("window");
		int seatNo = 1;
		while(!queue.isEmpty()) {
			String passengerName = queue.remove();
			if(asileSeatCount > 0) {
				System.out.println(passengerName+" : A"+seatNo);
				asileSeatCount--;
			} else if(windowSeatCount > 0) {
				System.out.println(passengerName+" : W"+seatNo);
				windowSeatCount--;
			} else if(middleSeatCount > 0){
				System.out.println(passengerName+" : M"+seatNo);
				middleSeatCount--;
			} else {
				System.out.println(passengerName+" : No seats available");
			}
		}
		
	}

	/**
	 * getSeatCountBasedOnType is used to get total seat count with count on each seat type.
	 * 
	 * @param seats
	 * @return
	 */
	private static Map<String, Integer> getSeatCountBasedOnType(int[][] seats) {
		Map<String,Integer> seatMap = new HashMap<>();
		int asileSeatCount = 0;
		int middleSeatCount = 0;
		int windowSeatCount = 0;
		for(int i=0; i<seats.length; i++) {
			int[] bay = seats[i];
			int rows = bay[0];
			int columns = bay[1];
			
			if(i==0 || i==seats.length-1) {
				asileSeatCount = asileSeatCount+rows;
			} else {
				asileSeatCount = asileSeatCount+(2*rows);
			}
			
			if(i==0 || i==seats.length-1) {
				windowSeatCount += rows;
			}
			
			if(columns > 2) {
				middleSeatCount += (columns * (columns-2));
			}
			
		}
		seatMap.put("asile", asileSeatCount);
		seatMap.put("middle", middleSeatCount);
		seatMap.put("window", windowSeatCount);
		return seatMap;
	}

}
