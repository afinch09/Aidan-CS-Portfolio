import javax.swing.JFrame;

public class GOLRunner {
	
	public static void main(String[] args) {
	    JFrame f = new JFrame("Conway's Game of Life"); 
	    GOLPanel p = new GOLPanel();
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.add(p);
	    f.pack();
	    f.setVisible(true);
	    p.setFocusable(true);
	    p.requestFocusInWindow();
	    p.run();
	}
}