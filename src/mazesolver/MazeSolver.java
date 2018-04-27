package mazesolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import mazesolver.Models.Maze;
/**
 *
 * @author pj
 */
public class MazeSolver 
{
    private static int x=0;
    private static int y=0;
    private static int [][] inputMaze;
    private static Scanner scanner;
    
    public static void main(String[] args) throws FileNotFoundException 
    {
        
        //read in from file
        System.out.println("Enter file name with extension: ");
        // example file input: "/Users/pj/Desktop/Gentrack maze_for_candidates/small.txt"
        scanner= new Scanner(System.in);
        File file= new File(scanner.nextLine());
        scanner=new Scanner(file);
        
        //from file parse details of maze
        x= scanner.nextInt();
        y= scanner.nextInt();
     
        int startX=scanner.nextInt();
        int startY= scanner.nextInt();
        
        int endX= scanner.nextInt();
        int endY=scanner.nextInt();
    
        //create array for maze and build Maze from file
        inputMaze= new int[y][x];
        buildMaze();
        
        //initialise Maze object by passing array and positions
        Maze maze=new Maze(inputMaze,x,y,startX,startY,endX,endY);
        //call maze solver by passing start positions
        maze.startSolving(maze.getStartX(),maze.getStartY());
     
    }
    public static void printMaze()
    {
        for(int i=0;i<y;i++)
        {
            for(int j=0;j<x;j++)
            {
                if(inputMaze[i][j]==0)
                {
                     System.out.print("X");
                }
                else if( inputMaze[i][j]==1)
                {
                     System.out.print("#");
                }
                else if(inputMaze[i][j]==3)
                {
                     System.out.print("S");
                }
                else if(inputMaze[i][j]==4)
                {
                     System.out.print("E");
                }
            }
            System.out.println("");
        }
    }
    private static void buildMaze()
    {
        //read from file and build the maze 
        for(int i=0;i<y;i++)
        {
          
            for(int j=0;j<x;j++)
            {   
                String temp="";
                
                if(scanner.hasNext())
                {
                  temp=scanner.next();
                  if(temp.equals("0"))
                  {
                    inputMaze[i][j]=0;// passage
                  }
                  else if(temp.equals("1"))
                  {
                    inputMaze[i][j]=1;// wall
                  }
                }
            }
        }
    }
    
}
