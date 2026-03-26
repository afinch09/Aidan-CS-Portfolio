import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.util.Scanner;

public class FractalPanel extends JPanel implements MouseListener{

	private final int WIDTH = 600;
    private final int HEIGHT = 400; 
	View v;
	Pallete p = new Pallete(1);
	private boolean isJulia = false;
	private Complex juliaC;

	int pressed_x;
	int pressed_y;

	public FractalPanel(int w, int h) {
		v = new View(WIDTH, HEIGHT);
		setPreferredSize(new Dimension(w, h));
		setBackground(Color.WHITE);
		addMouseListener(this);

		Scanner in = new Scanner(System.in);
		int choice = getValue(in, "Would you like to see the Mandelbrot set(0) or a julia set(1): ");
		if(choice == 1) isJulia = true;
		else isJulia = false;

		if(isJulia)choice = getValue(in, "Would you like to see a preset(0) or build your own c(1): ");
		if(isJulia && choice == 1) juliaC = getJuliaC(in);
		else if(isJulia && choice == 0) juliaC = getJuliaPreset(in);
		in.close();
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);

		if(!isJulia){
			for(int x = 0; x < WIDTH; x++){
				for(int y = 0; y < HEIGHT; y++){
					int count = Fractal.testMandelbrotPoint(v.translate(x, y));
					double norm = count / (double)Fractal.iterationLimit;
					g.setColor(p.getColor(norm));
					g.drawRect(x, y, 1, 1);
				}
			}
		}
		else if(isJulia){
			for(int x = 0; x < WIDTH; x++){
				for(int y = 0; y < HEIGHT; y++){
					int count = Fractal.testJuliaPoint(v.translate(x, y), juliaC);
					double norm = count / (double)Fractal.iterationLimit;
					g.setColor(p.getColor(norm));
					g.drawRect(x, y, 1, 1);
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) { }

	public void mousePressed(MouseEvent e) {
		pressed_x = e.getX();
		pressed_y = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
    	int rx = e.getX();
    	int ry = e.getY();
    	int w = getWidth();
    	int h = getHeight();
    	
    	int x0 = Math.min(pressed_x, rx);
    	int y0 = Math.min(pressed_y, ry);
    	int x1 = Math.max(pressed_x, rx);
    	int y1 = Math.max(pressed_y, ry);

    	double aspect = w / (double) h; 
    	int selW = x1 - x0;
    	int selH = y1 - y0;
    	double selAspect = selW / (double) selH;

    	if (selAspect > aspect) {
        	int targetH = (int)Math.round(selW / aspect);
        	int delta = targetH - selH;
        	y0 -= delta / 2;
        	y1 += delta - (delta / 2);
    	} else if (selAspect < aspect) {
        	int targetW = (int)Math.round(selH * aspect);
       		int delta = targetW - selW;
        	x0 -= delta / 2;
        	x1 += delta - (delta / 2);
    	}

    	Complex topLeft = v.translate(x0, y0);
    	Complex bottomRight = v.translate(x1, y1);

   		v.setComplexCorners(topLeft, bottomRight);
    	repaint();
	}

	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }

	public static int getValue(Scanner in, String prompt){
        int val = -1;
        boolean isValid = false;

        while(!isValid){
            try{
                System.out.print(prompt);
                val = Integer.valueOf(in.nextLine());
                if (val >= 0 && val <= 1) {
                    isValid = true;
                } else {
                    System.out.println("Please enter a number between 0 and 1.");
                }
            }
            catch(Exception e){
                System.err.println("An unexpected error occurred: " + e.getMessage());
                System.out.print("Please input an integer: ");
            }
        }

        return val;
    }

	public Complex getJuliaPreset(Scanner in){
        int val = -1;
        boolean isValid = false;

        while(!isValid){
            try{
                System.out.print("Would you like to see:\nSea horse pattern(1): \nSpiral pattern(2): \nFilled in spiral(3): \nDouady's rabbit(4): ");
                val = Integer.valueOf(in.nextLine());
                if (val >= 1 && val <= 4) {
                    isValid = true;
                } else {
                    System.out.println("Please enter a number between 0 and 1.");
                }
            }
            catch(Exception e){
                System.err.println("An unexpected error occurred: " + e.getMessage());
                System.out.print("Please input an integer: ");
            }
        }

		if(val == 1) return new Complex(-0.8, 0.156); // sea horse
		else if(val == 2) return new Complex(-0.7269, 0.1889); // spiral
		else if(val == 3) return new Complex(-0.4, 0.6); // filled in spiral
		else if(val == 4) return new Complex(0.355534, -0.337291); // douady's rabbit

        return new Complex();
    }

	public static Complex getJuliaC(Scanner in){
        double real = -99999;
		double imag = -99999;
        boolean isValid = false;

        while(!isValid){
            try{
                System.out.print("What is the real value of c: ");
                real = Double.valueOf(in.nextLine());
				isValid = true;
            }
            catch(Exception e){
                System.err.println("An unexpected error occurred: " + e.getMessage());
                System.out.print("Please input a double: ");
            }
        }

		isValid = false;
		while(!isValid){
            try{
                System.out.print("What is the imaginary value of c: ");
                imag = Double.valueOf(in.nextLine());
				isValid = true;
            }
            catch(Exception e){
                System.err.println("An unexpected error occurred: " + e.getMessage());
                System.out.print("Please input a double: ");
            }
        }

        return new Complex(real, imag);
    }
}