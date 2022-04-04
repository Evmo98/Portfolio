import java.util.Stack;

public class StringOps {

	/**
	 * Compares strings s1 and s2, and returns true if they are identical, false if
	 * different.
	 * 
	 * @param s1 First string
	 * @param s2 Second string
	 * @return Whether s1 and s2 are identical
	 */
	public static boolean equals(String s1, String s2) {
		if (s1.length() != s2.length()) return false;
		for (int i = 0; i < s1.length(); i+=1) {
			if (s1.charAt(i) != s2.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether string s is in valid HTML format. Valid HTML is composed of
	 * nested HTML tags. In this method, we only check for <b> tags and <i> tags.
	 * Specifically if each <b> tag is matched by a corresponding </b> tag and each
	 * <i> tag is matched by a corresponding </i> tag.
	 * 
	 * @param s String containing HTML page
	 * @return Whether s is in valid HTML format (with matching <b> and <i> tags)
	 */
	public static boolean isValidHTML(String s) {
		Stack<String> stack = new Stack<String>();
		for (int i = 0; i < s.length(); i++) {
			if (s.startsWith("<b>", i) || s.startsWith("<i>", i)) {
				stack.push(s.substring(i, i + 3));
			} else if (s.startsWith("</b>", i) || s.startsWith("</i>", i)) {
				if (stack.empty()) {
					return false;
				}
				if (s.startsWith("</b>", i)) {
					int exists = stack.search("<b>");
					if (exists == -1) {
						return false;
					} else { // If we know there exists a <b>, pop until it's found
						String top = stack.pop();
						int num = 0;
						while(!top.equals("<b>")){
							num++;
							top = stack.pop();
						}
						// After popping until a <b> is found, push all the <i>'s back on
						for(int j = 0; j < num; j++){
							stack.push("<i>");
						}
					}
				} else {
					assert s.startsWith("</i>", i);
					int exists = stack.search("<i>");
					if (exists == -1) {
						return false;
					} else { // If we know there exists a <b>, pop until it's found
						String top = stack.pop();
						int num = 0;
						while(!top.equals("<i>")){
							num++;
							top = stack.pop();
						}
						// After popping until a <i> is found, push all the <b>'s back on
						for(int j = 0; j < num; j++){
							stack.push("<b>");
						}
					}
				}
			}
		}
		return stack.empty();
	}
}
