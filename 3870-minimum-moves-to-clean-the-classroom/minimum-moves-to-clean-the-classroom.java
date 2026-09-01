class Solution {
    static class State {
        int r, c, mask, energy, steps;

        State(int r, int c, int mask, int energy, int steps) {
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.energy = energy;
            this.steps = steps;
        }
    }

    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int sr = 0, sc = 0;

        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        int litterCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                } else if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        int targetMask = (1 << litterCount) - 1;

        Queue<State> q = new ArrayDeque<>();

        boolean[][][][] visited =
                new boolean[m][n][1 << litterCount][energy + 1];

        visited[sr][sc][0][energy] = true;
        q.offer(new State(sr, sc, 0, energy, 0));

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!q.isEmpty()) {
            State cur = q.poll();

            if (cur.mask == targetMask) {
                return cur.steps;
            }

            for (int k = 0; k < 4; k++) {
                int nr = cur.r + dr[k];
                int nc = cur.c + dc[k];

                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                char cell = classroom[nr].charAt(nc);

                if (cell == 'X') {
                    continue;
                }

                if (cur.energy == 0) {
                    continue;
                }

                int nextEnergy = cur.energy - 1;
                int nextMask = cur.mask;

                if (cell == 'L') {
                    nextMask |= (1 << litterId[nr][nc]);
                }

                if (cell == 'R') {
                    nextEnergy = energy;
                }

                if (nextEnergy == 0 && cell != 'R') {
                    // Can't continue from here later,
                    // but this state itself is still valid.
                }

                if (!visited[nr][nc][nextMask][nextEnergy]) {
                    visited[nr][nc][nextMask][nextEnergy] = true;
                    q.offer(new State(
                            nr, nc,
                            nextMask,
                            nextEnergy,
                            cur.steps + 1
                    ));
                }
            }
        }

        return -1;
    }
}