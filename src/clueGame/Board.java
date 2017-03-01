package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import clueGame.BoardCell;

public class Board {
		// variable used for singleton pattern
		private static Board theInstance = new Board();
		// ctor is private to ensure only one can be created
		private String layout;
		private String legend;
		private Board() {}
		private final int MAX_BOAR_SIZE = 50;
		private BoardCell[][] grid;
		private int numRows,numCols;
		Map<Character, String> legendMap;
		
		// this method returns the only Board
		public static Board getInstance() {
			return theInstance;
		}
		public void setConfigFiles(String x, String y){
			layout= x;
			legend = y;
			
			
		}
		
		public void initialize(){
			
			//Create Legend
			FileReader reader = null;
			try {
				reader = new FileReader(legend);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scanner in = new Scanner(reader);
			legendMap = new HashMap<Character, String>();
			while(in.hasNextLine()){
				String line = in.nextLine();
				int tracker = 3;
				while(line.charAt(tracker)!=',') tracker++;
				legendMap.put(line.charAt(0), line.substring(3,tracker));
			}
			
			//Create Layout
			try {
				reader = new FileReader(layout);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			grid = new BoardCell[MAX_BOAR_SIZE][MAX_BOAR_SIZE];
			in = new Scanner(reader);
			numRows=0;
			String line="";
			int tracker = 0;
			while(in.hasNextLine()){
				line = in.nextLine();
				numCols = 0;
				char last = ',';
				for(int i = 0; i<line.length();i++){
					if(last == ','){
						grid[numCols][numRows] = new BoardCell(numCols,numRows,line.charAt(i));
						numCols++;
					}
					else if(line.charAt(i)!=','){
						System.out.println("did it");
						if(grid[numCols-1][numRows]!=null){
							switch(line.charAt(i)){
							case 'D':
								grid[numCols-1][numRows].setToDoorway(DoorDirection.DOWN);
								break;
							case 'U':
								grid[numCols-1][numRows].setToDoorway(DoorDirection.UP);
								break;
							case 'R':
								grid[numCols-1][numRows].setToDoorway(DoorDirection.RIGHT);
								break;
							case 'L':
								grid[numCols-1][numRows].setToDoorway(DoorDirection.LEFT);
								break;
							default:
								grid[numCols-1][numRows].setToDoorway(DoorDirection.NONE);
								break;
							}
						}
						
					}
					last = line.charAt(i);
				}
				numRows++;
				
			}
			
			
		}
		public Map<Character, String> getLegend(){
			return legendMap;
		}
		public int getNumRows(){
			return numRows;
		}
		public int getNumColumns(){
			return numCols;
		}
		public BoardCell getCellAt(int x, int y){
			return grid[y][x];
		}
}
