import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class GOLPanel extends JPanel implements KeyListener, MouseListener{
    int fps = 2;
    final static int width = 500;
    final static int height = 500;
    final static int BOX_SIZE = 10;

    final static int BOARD_ROWS = height / 10;
	final static int BOARD_COLUMNS = width / 10;
    public int[][] cells;
    public boolean isPaused;
    public int roundsSurvived = 0;
    public int aliveCells;
    public int isRand = 0;

	public GOLPanel() {
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.WHITE);

        Scanner in = new Scanner(System.in);
        int choice = getValue(in, "Would you like to generate a random board?\nYes(1): \nNo(0): ");
        boolean isValidInput = false;
        int randChoice;

        if(choice == 1){
            randChoice = getValue(in, "Do you want a random board from an LCG or Java's Built in: \nLCG(0): \nJava's built in random(1): ");
            if(randChoice == 0){
                cells = makeRandomBoard(BOARD_ROWS, BOARD_COLUMNS);
                isPaused = false;
                isRand = 1;
            } else if(randChoice == 1){      
                cells = makeRandomBoard(BOARD_ROWS, BOARD_COLUMNS);
                isRand = 2;
                isPaused = false;
            }
        } else if(choice == 0){
            cells = new int[BOARD_ROWS][BOARD_COLUMNS];
            isPaused = true;
        } else{
            while(!isValidInput){
                choice = getValue(in, "Please input a valid choice\nYes(1): \nNo(2): ");
            }
        }

        addKeyListener(this);
        addMouseListener(this);

        System.out.println("Game succesfully set up\nSpace to start/stop\n1 to get next generation manually\nLeft and right to speed up and slow done\nPress s to save\nPress l to load\nGood luck!");
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		g.setColor(Color.BLACK);
        int width = this.getWidth();
        int height = this.getHeight();
		drawGrid(g, width, height);
		drawBoard(g, cells);
	}

	public void run() {
        while (true) {
            if (!isPaused) {
                cells = nextGeneration(cells);
                if (isAlive(cells)) {
                    roundsSurvived++;
                    aliveCells = countAlive(cells);
                } else {
                    System.out.println("Game over! You survived " + roundsSurvived + " rounds.");
                    roundsSurvived = 0;
                    isPaused = true;
                }
            }
            delay(1000 / fps);
            repaint();
        }
    }

	public void delay(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

    public void drawGrid(Graphics g, int width, int height){
        for(int i = 0; i <= height / BOX_SIZE; i++){
            g.drawLine(0, i * BOX_SIZE, width, i * BOX_SIZE);
        }

        for(int i = 0; i <= width / BOX_SIZE; i++){
            g.drawLine(i * BOX_SIZE, 0, i * BOX_SIZE, height);
        }
    }

	public void drawBoard(Graphics g, int[][] board){
		for(int r = 0; r < board.length; r++){
			for(int c = 0; c < board[0].length; c++){
				if(board[r][c] >= 1){
                    int temp = getColor(board[r][c]);
                    g.setColor(new Color(temp, temp, temp));			
                    g.fillRect(c * BOX_SIZE, r * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				}
			}
		}
	}

	public int[][] nextGeneration(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        int[][] newGen = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int neighborCount = 0;

                for (int nr = -1; nr <= 1; nr++) {
                    for (int nc = -1; nc <= 1; nc++) {
                        if (nr == 0 && nc == 0) continue;

                        int wrappedR = (r + nr + BOARD_ROWS) % rows;
                        int wrappedC = (c + nc + BOARD_COLUMNS) % cols;

                        if (board[wrappedR][wrappedC] >= 1) {
                            neighborCount++;
                        }
                    }
                }

                // Apply Game of Life rules
                if (board[r][c] >= 1) {
                    // Cell is alive
                    if (neighborCount < 2 || neighborCount > 3) {
                        newGen[r][c] = 0; // dies
                    } else {
                        newGen[r][c] = board[r][c] + 1; // survives, cell age increases
                        if(newGen[r][c] > 15) newGen[r][c] = 15;
                    }
                } else {
                    // Cell is dead
                    if (neighborCount == 3) {
                        newGen[r][c] = 1; // becomes alive
                    }
                }
            }
        }
        return newGen;
    }

    public int getColor(int cellAge){
        int color;
        if(cellAge < 15){
            color = cellAge * 10;
        } else {
            color = 150;
        }

        return color;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(isPaused) isPaused = false;
            else{
                isPaused = true;
                printStats();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_1){
            cells = nextGeneration(cells);
            if (isAlive(cells)) {
                roundsSurvived++;
                aliveCells = countAlive(cells);
            } else {
                System.out.println("Game over! You survived " + roundsSurvived + " rounds.");
                roundsSurvived = 0;
                isPaused = true;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_S) {
            saveGameState(cells, "gol.cfg", false);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if(fps > 1) fps -= 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            fps += 1;
        }

        if (e.getKeyCode() == KeyEvent.VK_L) {
            cells = readGameCFG("gol.cfg");
        }
    }

    public void mousePressed(MouseEvent e) {
        int pressX = e.getX();
        int pressY = e.getY();
        
        if(pressX <= width && pressY <= width){
            if(cells[pressY / BOX_SIZE][pressX / BOX_SIZE] == 0) cells[pressY / BOX_SIZE][pressX / BOX_SIZE] = 1;
            else if (cells[pressY / BOX_SIZE][pressX / BOX_SIZE] >= 1) cells[pressY / BOX_SIZE][pressX / BOX_SIZE] = 0;
        }
    }   

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public int getLCG(int range){
        int a = 1664525;
        int c = 101390422;
        int seed = ((int)System.nanoTime()) / 1000;
        int r = Math.abs(((a * seed + c) % range));
        return r;
    }

    public int[][] makeRandomBoardLCG(int rows, int columns){
        int[][] board = new int[rows][columns];
        int temp;

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                temp = getLCG(2);
                board[r][c] = temp; 
            }
        }

        return board;
    }

    public int[][] makeRandomBoard(int rows, int columns){
        int[][] board = new int[rows][columns];
        int temp;

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                temp = (int)(Math.random() * 2);
                board[r][c] = temp; 
            }
        }

        return board;
    }

    public int getValue(Scanner in, String prompt){
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

    public boolean isAlive(int[][] board){
        int aliveCount = 0;
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[r].length; c++){
                if(board[r][c] >= 1) aliveCount++;
            }
        }

        if(aliveCount > 0){
            return true;
        } else{
            return false;
        }
    }

    public void printStats(){
        System.out.println("You have survived " + roundsSurvived + " rounds");
        System.out.println("You have " + aliveCells + " cells alive");
    }

    public int countAlive(int[][] board){
        int aliveCount = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if(board[r][c] >= 1) aliveCount++;
            }
        }

        return aliveCount;
    }

    public void saveGameState(int[][] board, String filename, boolean append) {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, append)))) {
            out.println(String.valueOf(fps) + "," + String.valueOf(isRand) + "," + String.valueOf(aliveCells) + "," + String.valueOf(roundsSurvived));
            
            for(int r = 0; r < BOARD_ROWS; r++) {
                for(int c = 0; c < BOARD_COLUMNS; c++) {
                    out.printf("%02d", board[r][c]);
                }
                out.println();
            }

        }
        catch(IOException e) {
            System.out.println(e);
        }
    }

    public int[][] readGameCFG(String filename) {
        int counter = 0;
        int[][] board = new int[BOARD_ROWS][BOARD_COLUMNS];
        try (Scanner fileIn = new Scanner(new File(filename))) {
            while (fileIn.hasNext()) {
                String line = fileIn.nextLine();
                if(counter == 0) {
                    String[] tokens = line.split(",");

                    fps = Integer.valueOf(tokens[0]);
                    aliveCells = Integer.valueOf(tokens[2]);
                    roundsSurvived = Integer.valueOf(tokens[3]);
                } else {
                    for(int i = 0; i < BOARD_COLUMNS * 2; i += 2) {
                        board[counter - 1][i / 2] = Integer.valueOf(line.substring(i, i + 2));
                    }
                }

                counter++;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }

        return board;
    }
}