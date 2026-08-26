class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        String ans = "";
        int minLen = Integer.MAX_VALUE;

        for (int i = 0; i < s.length(); i++) {
            int ones = 0;

            for (int j = i; j < s.length(); j++) {
                if (s.charAt(j) == '1') {
                    ones++;
                }

                if (ones == k) {
                    String sub = s.substring(i, j + 1);

                    if (sub.length() < minLen) {
                        minLen = sub.length();
                        ans = sub;
                    } else if (sub.length() == minLen &&
                               (ans.isEmpty() || sub.compareTo(ans) < 0)) {
                        ans = sub;
                    }
                } else if (ones > k) {
                    break;
                }
            }
        }

        return ans;
    }
}