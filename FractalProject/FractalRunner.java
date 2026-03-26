import javax.swing.JFrame;

public class FractalRunner {
	
	public static void main(String[] args) {
	    JFrame f = new JFrame("Fractals"); 
	    FractalPanel p = new FractalPanel(600, 400);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.add(p);
	    f.pack();
	    f.setVisible(true);
		f.setResizable(false);
	    p.setFocusable(true);
	    p.requestFocusInWindow();
	}
}