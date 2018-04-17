/**
 * 
 */
package com.ubs.opsit.interviews.impl;

import com.ubs.opsit.interviews.TimeConverter;

/**
 * This class contains the implementation for Berlin clock algorithm.
 * 
 * @author varsha.s.gaikwad
 *
 */
public class TimeConverterImpl implements TimeConverter {

	private static final String BLANK_TIME_ERROR = "No time provided";
	private static final String INVALID_TIME_ERROR = "Invalid time provided.";
	private static final String NUMERIC_TIME_ERROR = "Time values must be numeric.";
	private static final String HOURS_OUT_OF_BOUNDS = "Hours out of bounds.";
	private static final String MINUTES_OUT_OF_BOUNDS = "Minutes out of bounds.";
	private static final String SECONDS_OUT_OF_BOUNDS = "Seconds out of bounds.";
	private static final String NEWLINE_CHAR = System.getProperty("line.separator");
	private static final String RED_LAMP = "R";
	private static final String YELLOW_LAMP = "Y";
	private static final String OFF_FOUR_LAMPS = "OOOO";
	private static final String OFF_ELEVEN_LAMPS = "OOOOOOOOOOO";

	/*
	 * This method is the entry point for this service class. It will accept
	 * time in HH24:MI:SS format & will return the string in below format-
	 * X\nXXXX\nXXXX\nXXXXXXXXXXX\nXXXX
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ubs.opsit.interviews.TimeConverter#convertTime(java.lang.String)
	 */
	@Override
	public String convertTime(String aTime) {
		if (aTime == null)
			throw new IllegalArgumentException(BLANK_TIME_ERROR);

		String[] times = aTime.split(":");

		if (times.length != 3)
			throw new IllegalArgumentException(INVALID_TIME_ERROR);

		int hours = 0;
		int minutes = 0;
		int seconds = 0;

		try {
			hours = Integer.parseInt(times[0]);
			minutes = Integer.parseInt(times[1]);
			seconds = Integer.parseInt(times[2]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(NUMERIC_TIME_ERROR);
		}

		if ((hours < 0 || hours > 23) && (minutes != 0 && seconds != 0))
			throw new IllegalArgumentException(HOURS_OUT_OF_BOUNDS);
		if (minutes < 0 || minutes > 59)
			throw new IllegalArgumentException(MINUTES_OUT_OF_BOUNDS);
		if (seconds < 0 || seconds > 59)
			throw new IllegalArgumentException(SECONDS_OUT_OF_BOUNDS);

		return getBerlinTime(hours, minutes, seconds);
	}

	/**
	 * This method will accept the time in individual hour, minute, second
	 * fields and will process accordingly. Algorithm: There will be total five
	 * lines in Berlin clock. Line 1: Represents second. Y for even & O of odd
	 * number. Line 2: Has 4 characters. Each char will represent 5 hours & will
	 * be displayed as R Line 3: Has 4 characters. Each char will represent 1
	 * hour & will be displayed as R Line 4: Has 11 characters. Each will
	 * represent 5 minutes & will be displayed as Y. 3rd, 6th & 9th char will be
	 * represented as R to show quarter of the hour. Line 5: has 4 characters.
	 * Each will represent single minute & will be displayed as Y.
	 * 
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	private String getBerlinTime(int hours, int minutes, int seconds) {
		String[] lines = new String[5];
		lines[0] = (seconds % 2 == 0) ? YELLOW_LAMP : "O";
		lines[1] = processLine(hours / 5, 4, RED_LAMP);
		lines[2] = processLine(hours % 5, 4, RED_LAMP);
		lines[3] = processLine(minutes / 5, 11, YELLOW_LAMP);
		lines[4] = processLine(minutes % 5, 4, YELLOW_LAMP);

		return String.join(NEWLINE_CHAR, lines);
	}

	/**
	 * This method handles logic for creating line 2,3,4 & 5. Based on the value
	 * of totalLamps, the function will process line number 4 separately.
	 * 
	 * @param onLamps
	 * @param totalLamps
	 * @param lampType
	 * @return
	 */
	private String processLine(int onLamps, int totalLamps, String lampType) {
		StringBuffer lamps = (totalLamps == 11) ? new StringBuffer(OFF_ELEVEN_LAMPS) : new StringBuffer(OFF_FOUR_LAMPS);
		for (int i = 0; i < onLamps; i++) {

			if (totalLamps == 11 && (i + 1) % 3 == 0) {
				lamps.replace(i, i + 1, RED_LAMP);
			} else {
				lamps.replace(i, i + 1, lampType);
			}
		}

		return String.valueOf(lamps);
	}

}
