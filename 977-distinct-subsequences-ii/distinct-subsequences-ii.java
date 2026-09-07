class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;

        long[] last = new long[26];
        long dp = 1; // empty subsequence

        for (char ch : s.toCharArray()) {
            long newDp = (2 * dp % MOD - last[ch - 'a'] + MOD) % MOD;

            last[ch - 'a'] = dp;
            dp = newDp;
        }

        return (int) ((dp - 1 + MOD) % MOD);
    }
}