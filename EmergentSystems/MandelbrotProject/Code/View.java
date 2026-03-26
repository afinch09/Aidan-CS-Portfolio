public class View {
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;

    private double real_m = 0.005;
    private double real_b = -2;
    private double imag_m = -0.005;
    private double imag_b = 1;

    public View(int width, int height) {
        this.SCREEN_HEIGHT = height;
        this.SCREEN_WIDTH = width;
    }

    public Complex translate(int x, int y) {
        return new Complex((x * real_m) + real_b, (y * imag_m) + imag_b);
    }

    public void setComplexCorners(Complex topLeft, Complex bottomRight) {
        real_b = topLeft.getReal();
        real_m = (bottomRight.getReal() - topLeft.getReal()) / SCREEN_WIDTH;

        imag_b = topLeft.getImaginary();
        imag_m = (bottomRight.getImaginary() - topLeft.getImaginary()) / SCREEN_HEIGHT;
    }
    
}
