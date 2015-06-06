package utils;

public class TextProcesser {
	public static String processTitle(String rawTitle) {
		String processedTitle = null;
		int spaceIndex = rawTitle.indexOf(":");
		if (spaceIndex != -1) {
			processedTitle = rawTitle.substring(0, spaceIndex);
		}

		spaceIndex = processedTitle.indexOf("(");
		if (spaceIndex != -1) {
			processedTitle = processedTitle.substring(0, spaceIndex);
		}
		return processedTitle;
	}
}
