public class Fractal {
    public static int iterationLimit = 1000;
    public static int testMandelbrotPoint(Complex c) {
        Complex z = new Complex();
        int res = 0;
        while(z.abs() <= 2){
            z = z.square().add(c);
            res++;
            if(res > iterationLimit) return -1;
        }

        return res;
    }

    public static int testJuliaPoint(Complex z1, Complex c) {
        Complex z = z1.square().add(c);
        int res = 1;
        while(z.abs() <= 2){
            z = z.square().add(c);
            res++;
            if(res > iterationLimit) return -1;
        }

        return res;
    }
}