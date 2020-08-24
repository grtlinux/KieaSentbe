package org.tain.utils;

public class StringTools {

	// text.replaceAll("(?<=^.{47}).*$", "...");

	public static String truncate(String line, int maxLength) {
		if (line.length() < maxLength)
			return line;
		int pos = line.lastIndexOf(" ", maxLength - 3);
		if (pos <= 0)
			pos = maxLength - 3; // no spaces, so just cut anyway
		return line.substring(0, pos) + "...";
	}

	public static String smartSubstring(String str, int maxLength) {
		String subStr = str.substring(0);
		if (maxLength == 0) {
			return "";
		} else if (str.length() <= maxLength) {
			return str;
		} else {
			int i = maxLength;
			while (i >= 0) {
				while (str.length() < i) {
					i--;
				}
				if (str.charAt(i) == ' ') {
					subStr = str.substring(0, i);
					break;
				}
				i--;
			}
			return subStr;
		}
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	private final static String NON_THIN = "[^iIl1\\.,']";

	private static int textWidth(String str) {
		return (int) (str.length() - str.replaceAll(NON_THIN, "").length() / 2);
	}

	public static String ellipsize(String text, int max) {

		if (textWidth(text) <= max)
			return text;

		// Start by chopping off at the word before max
		// This is an over-approximation due to thin-characters...
		int end = text.lastIndexOf(' ', max - 3);

		// Just one long word. Chop it off.
		if (end == -1)
			return text.substring(0, max - 3) + "...";

		// Step forward as long as textWidth allows.
		int newEnd = end;
		do {
			end = newEnd;
			newEnd = text.indexOf(' ', end + 1);

			// No more spaces.
			if (newEnd == -1)
				newEnd = text.length();

		} while (textWidth(text.substring(0, newEnd) + "...") < max);

		return text.substring(0, end) + "...";
	}
}
