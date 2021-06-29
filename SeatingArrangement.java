package prepration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * SeatingArrangement class is used to make seating arrangement 
 * for the passengers in airplane with available seats.
 * 
 * @author Sathish
 */
public class SeatingArrangement {
	
	public static void main(String[] args) {
		// Input as seats rows and columns in each bay
		//		int[][] seats = { { 3, 2 }, { 4, 3 }, { 2, 3 }, { 3, 4 } };
		int[][] seats = { { 2, 3 }, { 3, 4 }, { 3, 2 }, { 4, 3 } };

		// Input as No. of Passengers list,
		// For sample 30 passengers. Making a queue of 30 passengers.
		LinkedList<String> queue = new LinkedList<String>();
		for (int i = 1; i <= 30; i++) {
			queue.add("Passenger " + i);
		}
		
		SeatingArrangement sa = new SeatingArrangement();
		Map<String, Integer> seatCountMap = sa.getSeatCountBasedOnType(seats);
		System.out.println("-------- Seating Arrangement --------\n");
		
		sa.makeSeatingPlanWithPassengers(seats, queue.size());
		
		System.out.println("\n-------- Passenger number with Seat number and seat type --------");
		sa.printPassengersWithSeatsType(queue, seatCountMap);
	}


	/**
	 * makeSeatingPlanWithPassengers is used to print passenger number with seat type and location.
	 * 
	 * @param seats
	 * @param passengerCount
	 */
	private void makeSeatingPlanWithPassengers(int[][] seats, int passengerCount) {
		Map<String, Integer> seatCountMap = getSeatCountBasedOnType(seats);
		int asileSeatCount = seatCountMap.get("asile");
		int middleSeatCount = seatCountMap.get("middle");
		int windowSeatCount = seatCountMap.get("window");
		int maxWindowSeat = asileSeatCount+windowSeatCount;

		int winStart = asileSeatCount + 1;
		int midStart = asileSeatCount + windowSeatCount + 1;
		int asileStart = 1;

		seatCountMap.put("asile", asileStart);
		seatCountMap.put("middle", midStart);
		seatCountMap.put("window", winStart);
		seatingPlanofSeatswithPassengerNumber(seats, seatCountMap, asileSeatCount, maxWindowSeat, passengerCount);
		int index = 0;
		int allotedRowsCount = 0;
		while (index < seats.length && allotedRowsCount!=seats.length) {
			int rows = seats[index][0];
			int columns = seats[index][1];
			if( rows>0 ) {
				int[] temp = { rows - 1, columns };
				if(temp[0] == 0) {
					allotedRowsCount++;
				}
				seats[index] = temp;
			}

			if(index == seats.length-1 && allotedRowsCount!=seats.length) {
				seatingPlanofSeatswithPassengerNumber(seats, seatCountMap, asileSeatCount, maxWindowSeat, passengerCount);
				index=0;
			} else {
				index++;
			}
		}
	}

	/**
	 * seatingPlanofSeatswithPassengerNumber is used to print the clear seating plan aith passenger number.
	 * 
	 * @param seatsArray
	 * @param seatCountMap
	 * @param maxAsileSeat
	 * @param maxWindowSeat
	 * @param maxMidleSeat
	 */
	private void seatingPlanofSeatswithPassengerNumber(int[][] seatsArray, Map<String, Integer> seatCountMap, int maxAsileSeat, int maxWindowSeat, int maxMidleSeat) {
		int asileStart = seatCountMap.get("asile");
		int midStart = seatCountMap.get("middle");
		int winStart = seatCountMap.get("window");
		for (int bay = 0; bay < seatsArray.length; bay++) {
			int[] item = seatsArray[bay];
			int rows = item[0];
			int columns = item[1];

			if (rows != 0) {
				for (int column = 0; column < columns; column++) {
					if ((bay == 0 && column == 0) || (bay == seatsArray.length - 1 && column == columns - 1)) {
						if(winStart <= maxWindowSeat) {
							System.out.print(" W" + getAppendWithZero(winStart) + "");
							winStart++;
							seatCountMap.put("window", winStart);
						}else {
							System.out.print("  -  ");
						}
						if ((bay == seatsArray.length - 1 && column == columns - 1)) {
							System.out.println();
						}

					} else if (!(column == 0 || column == columns - 1) && columns > 2) {
						if(midStart <= maxMidleSeat) {
							System.out.print("  M" + getAppendWithZero(midStart) + "");
							midStart++;
						} else {
							System.out.print("  -  ");
						}
						seatCountMap.put("middle", midStart);
					} else if ((bay == 0 && column == columns - 1) || (bay == seatsArray.length - 1 && column == 0)
							|| (bay != 0 && bay != seatsArray.length - 1 && (column == 0 || column == columns - 1))) {
						if(asileStart <= maxAsileSeat) {
							System.out.print("  A" + getAppendWithZero(asileStart) + "");
							asileStart++;
							seatCountMap.put("asile", asileStart);
						}else {
							System.out.print("  -  ");
						}
					}

					if (bay != seatsArray.length - 1 && column == columns - 1) {
						System.out.print(",");
					}
				}
			} else {
				for(int  index = 0; index < columns; index++) {
					System.out.print("     ");
				}
			}
		}
	}

	/**
	 * getAppendWithZero is used to append zero with single didgit seat for more readable.
	 * 
	 * @param seatNumber
	 * @return
	 */
	private static String getAppendWithZero(int seatNumber) {
		String appendWithZero = String.valueOf(seatNumber);
		if(seatNumber<10) {
			appendWithZero = "0"+appendWithZero;
		}
		return appendWithZero;
	}


	/**
	 * printPassengersWithSeats is used to get passengers with alloted seat number
	 * and seat type.
	 * 
	 * @param queue
	 * @param seatCountMap
	 */
	private void printPassengersWithSeatsType(LinkedList<String> queue, Map<String, Integer> seatCountMap) {
		int asileSeatCount = seatCountMap.get("asile");
		int middleSeatCount = seatCountMap.get("middle");
		int windowSeatCount = seatCountMap.get("window");
		int seatNo = 1;
		while (!queue.isEmpty()) {
			String passengerName = queue.remove();
			if (asileSeatCount > 0) {
				System.out.println(passengerName + " : A" + seatNo);
				asileSeatCount--;
			} else if (windowSeatCount > 0) {
				System.out.println(passengerName + " : W" + seatNo);
				windowSeatCount--;
			} else if (middleSeatCount > 0) {
				System.out.println(passengerName + " : M" + seatNo);
				middleSeatCount--;
			} else {
				System.out.println(passengerName + " : No seats available");
			}
			seatNo++;
		}

	}

	/**
	 * getSeatCountBasedOnType is used to get total seat count with count on each
	 * seat type.
	 * 
	 * @param seats
	 * @return
	 */
	private static Map<String, Integer> getSeatCountBasedOnType(int[][] seats) {
		Map<String, Integer> seatMap = new HashMap<>();
		int asileSeatCount = 0;
		int middleSeatCount = 0;
		int windowSeatCount = 0;
		for (int i = 0; i < seats.length; i++) {
			int[] bay = seats[i];
			int rows = bay[0];
			int columns = bay[1];

			if (i == 0 || i == seats.length - 1) {
				asileSeatCount = asileSeatCount + rows;
			} else {
				asileSeatCount = asileSeatCount + (2 * rows);
			}

			if (i == 0 || i == seats.length - 1) {
				windowSeatCount += rows;
			}

			if (columns > 2) {
				middleSeatCount += (rows * (columns - 2));
			}

		}
		seatMap.put("asile", asileSeatCount);
		seatMap.put("middle", middleSeatCount);
		seatMap.put("window", windowSeatCount);
		return seatMap;
	}

}
