package utils;

public final class TextProcesser {
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
	public static String getStringOfLettersOnly(String s) {
	    //using a StringBuilder instead of concatenate Strings
	    StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < s.length(); i++) {
	        if (Character.isLetter(s.charAt(i))) {
	            //adding data into the StringBuilder
	            sb.append(s.charAt(i));
	        }
	    }
	    //return the String contained in the StringBuilder
	    return sb.toString();
	}
}
