import java.awt.Color;

public class Pallete {
    private final int colorScheme;

    public Pallete(int scheme) {
        this.colorScheme = scheme;
    }

    public Color getColor(double normedN) {
        if(colorScheme == 1) return pattern1(normedN);
        else if(colorScheme == 2) return pattern2(normedN);

        return Color.PINK;
    }

    private Color pattern1(double normedN) {
        if (normedN < 0)return Color.BLACK;
        int red = 220 - Math.abs((int)(200 * Math.pow(Math.max(-1, Math.min(Math.tan(Math.pow(normedN * 5, 0.75)) + 0.1, 1)), 2)));
        int green = Math.abs((int)(200 * Math.pow(Math.max(-1, Math.min(Math.sin(Math.pow(normedN * 8, 0.75)) + 0.01, 1)), 2)));
        int blue = 255 - Math.abs((int)(200 * Math.pow(Math.max(-1, Math.min(Math.cos(Math.pow(normedN * 2, 0.75)) - 0.02, 1)), 2)));

        return new Color(red, green, blue);
    }

    private Color pattern2(double normedN) {
        if (normedN < 0)return Color.BLACK;
        int red = (int)(255 * Math.abs(Math.max(Math.min(1, Math.atan(normedN * 5)), -1)));
        int green = (int)(255 * Math.abs(Math.max(Math.min(1, Math.asin(normedN)), -1)));
        int blue = (int)(255 * Math.abs(Math.max(Math.min(1, Math.acos(normedN * 3)), -1)));

        return new Color(red, green, blue);
    }
}
