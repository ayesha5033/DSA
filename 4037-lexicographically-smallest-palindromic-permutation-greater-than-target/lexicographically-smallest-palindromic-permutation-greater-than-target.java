class Solution {
    int n;
    int halfLen;
    int[] halfCnt;
    char mid = 0;
    String target;

    public String lexPalindromicPermutation(String s, String target) {
        this.target = target;
        n = s.length();

        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int odd = 0;
        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1) {
                odd++;
                mid = (char) ('a' + i);
            }
        }

        if (odd > 1) return "";

        halfLen = n / 2;
        halfCnt = new int[26];

        for (int i = 0; i < 26; i++) {
            halfCnt[i] = freq[i] / 2;
        }

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {
            boolean found = false;

            for (int ch = 0; ch < 26; ch++) {
                if (halfCnt[ch] == 0) continue;

                halfCnt[ch]--;
                left.append((char) ('a' + ch));

                if (canStillBeGreater(left)) {
                    found = true;
                    break;
                }

                left.deleteCharAt(left.length() - 1);
                halfCnt[ch]++;
            }

            if (!found) return "";
        }

        String ans = buildPalindrome(left.toString());

        return ans.compareTo(target) > 0 ? ans : "";
    }

    private boolean canStillBeGreater(StringBuilder prefix) {
        StringBuilder half = new StringBuilder(prefix);

        // Build the lexicographically largest completion.
        for (int c = 25; c >= 0; c--) {
            for (int k = 0; k < halfCnt[c]; k++) {
                half.append((char) ('a' + c));
            }
        }

        String maxPal = buildPalindrome(half.toString());

        return maxPal.compareTo(target) > 0;
    }

    private String buildPalindrome(String left) {
        StringBuilder sb = new StringBuilder();

        sb.append(left);

        if ((n & 1) == 1) {
            sb.append(mid);
        }

        sb.append(new StringBuilder(left).reverse());

        return sb.toString();
    }
}