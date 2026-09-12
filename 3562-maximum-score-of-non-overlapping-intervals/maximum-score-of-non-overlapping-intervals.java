import java.util.*;

class Solution {

    static class State {
        long weight;
        int[] ids;

        State(long weight, int[] ids) {
            this.weight = weight;
            this.ids = ids;
        }
    }

    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        Interval[] arr = new Interval[n];
        for (int i = 0; i < n; i++) {
            List<Integer> cur = intervals.get(i);
            arr[i] = new Interval(
                cur.get(0),
                cur.get(1),
                cur.get(2),
                i
            );
        }

        Arrays.sort(arr, (a, b) -> {
            if (a.l != b.l) return Integer.compare(a.l, b.l);
            return Integer.compare(a.r, b.r);
        });

        int[] starts = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = arr[i].l;
        }

        int[] next = new int[n];
        for (int i = 0; i < n; i++) {
            next[i] = upperBound(starts, arr[i].r);
        }

        State[][] dp = new State[n + 1][5];

        for (int k = 0; k <= 4; k++) {
            dp[n][k] = new State(0L, new int[0]);
        }

        for (int i = n - 1; i >= 0; i--) {
            dp[i][0] = new State(0L, new int[0]);

            for (int k = 1; k <= 4; k++) {
                State skip = dp[i + 1][k];

                State nxt = dp[next[i]][k - 1];
                long takeWeight = arr[i].w + nxt.weight;

                int[] takeIds = insertSorted(arr[i].idx, nxt.ids);
                State take = new State(takeWeight, takeIds);

                dp[i][k] = better(take, skip);
            }
        }

        return dp[0][4].ids;
    }

    private int upperBound(int[] starts, int target) {
        int l = 0, r = starts.length;

        while (l < r) {
            int m = (l + r) >>> 1;

            if (starts[m] <= target) {
                l = m + 1;
            } else {
                r = m;
            }
        }

        return l;
    }

    private State better(State a, State b) {
        if (a.weight != b.weight) {
            return a.weight > b.weight ? a : b;
        }

        int cmp = lexCompare(a.ids, b.ids);
        return cmp <= 0 ? a : b;
    }

    private int lexCompare(int[] a, int[] b) {
        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {
            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }

        return Integer.compare(a.length, b.length);
    }

    private int[] insertSorted(int val, int[] arr) {
        int[] res = new int[arr.length + 1];

        int i = 0, j = 0;
        boolean inserted = false;

        while (i < arr.length) {
            if (!inserted && val < arr[i]) {
                res[j++] = val;
                inserted = true;
            } else {
                res[j++] = arr[i++];
            }
        }

        if (!inserted) {
            res[j] = val;
        }

        return res;
    }
}