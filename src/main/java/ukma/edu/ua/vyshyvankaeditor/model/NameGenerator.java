package ukma.edu.ua.vyshyvankaeditor.model;

import javafx.scene.paint.Color;

public class NameGenerator {
    private static final String[] PATTERN = {
            // рядки 0-20; орнамент імені
            "................#................", // 0
            "...............#*#...............", // 1
            "..............#***#..............", // 2
            "............*.**.**.*............", // 3
            "..............*.*.*..............", // 4
            "...........*.*#***#*.*...........", // 5
            "..........*.*.##*##.*.*..........", // 6
            "..........**.*#*#*#*.**..........", // 7
            "........#*.###..*..###.*#........", // 8
            ".......#**.*#*.....*#*.**#.......", // 9
            "......#**.***#*...*#***.**#......", // 10
            ".......#**.*#*.....*#*.**#.......", // 11
            "........#*.###..*..###.*#........", // 12
            "..........**.*#*#*#*.**..........", // 13
            "..........*.*.##*##.*.*..........", // 14
            "...........*.*#***#*.*...........", // 15
            "..............*.*.*..............", // 16
            "............*.**.**.*............", // 17
            "..............#***#..............", // 18
            "...............#*#...............", // 19
            "................#................", // 20
            "",
            "",
            // 21-22 відступ
            // рядки 21-27; підпис
            " *   * *   * *  *  **   **   ** ", // 23
            " ** ** *  ** * *  *  * *  * *  * ", // 24
            " * * * * * * **   *  * *  * **** ", // 25
            " *   * **  * * *  *  * *  * *  *  ", // 26
            " *   * *   * *  *  **  *  * *  *   "  // 27
    };

    public static void applyNamePattern(GridData model) {
        int startX = (model.getWidth() - PATTERN[0].length()) / 2;
        int startY = (model.getHeight() - PATTERN[0].length()) / 2;

        for (int row = 0; row < PATTERN.length; row++) {
            String line = PATTERN[row];
            for (int col = 0; col < line.length(); col++) {
                char ch = line.charAt(col);
                int realY = startY + row;

                GridCellType type = GridCellType.CROSS_STITCH;
                if (row >= PATTERN.length - 5) {
                    type = GridCellType.FILLED_SQUARE;
                }

                if (ch == '#') {
                    model.setCell(startX + col, realY, Color.RED, type);
                } else if (ch == '*') {
                    model.setCell(startX + col, realY, Color.BLACK, type);
                }
            }
        }
    }
}