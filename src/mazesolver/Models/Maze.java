/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazesolver.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author pj
 */

public class Maze 
{
    private int maze[][];
    private final int rows;
    private final int columns;
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private ArrayList<String> history=new ArrayList();//keep track of order of visits
    private HashMap<String,Integer> visitedPositions= new HashMap();// list of all visited positions
    private HashMap<String,Integer> currentPath= new HashMap();// store current path
    
    public Maze(int[][] inputMaze,int columns, int rows, int startX, int startY, int endX, int endY)
    {
        maze=inputMaze;
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;
        this.rows=rows;
        this.columns=columns;
    }
    public void printSolvedMaze()
    {
        maze[startY][startX]=3;//set start point
        maze[endY][endX]=4;//set end point
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++)
            {
                if(maze[i][j]==0)
                {
                  
                    if(!currentPath.containsKey(String.valueOf(i)+"+"+String.valueOf(j))) // if path is not in our solution
                    {
                         System.out.print(" ");
                    }
                    else if(visitedPositions.containsKey(String.valueOf(i)+"+"+String.valueOf(j)))// if path IS in solution
                    {
                        System.out.print("X");
                    }
                    else
                    {
                        System.out.print(" ");
                    }
                }
                else if( maze[i][j]==1)
                {
                     System.out.print("#");// represents wall
                }
                else if(maze[i][j]==3)
                {
                     System.out.print("S");// represents starting position
                }
                else if(maze[i][j]==4)
                {
                     System.out.print("E");// represents end position
                }
            }
            System.out.println("");// new line 
        }
    }
    public void solveMaze(int y, int x)
    {  //[y][x]
       int currentX=x;
       int currentY=y;
       boolean finished=false;

       while(!finished)
       {
           /**use a while loop until finished or decided unsolvable
            * The algorithm checks N,E,S,W in all directions from the current position to see if there is a passage
            * that has not been to and we got that. If we reach a dead end, the we go back until we find another position
            * we have not been to. Keep doing this until we reach our end position
           */
       
           if(currentX==endX&&currentY==endY)
           {
     
               printSolvedMaze(); // print if reached end position
               finished=true;
               break;
             
           }
           //check if position looking for is within the maze and is not in visitedPositions map
           else if(currentX+1>=0&&currentX+1<maze[1].length&&maze[currentY][currentX+1]==0&&!visitedPositions.containsKey(String.valueOf(currentY)+"+"+String.valueOf(currentX+1)))//check EAST of point, X CHANGES, Y STAYS SAME
           {
          
                visitedPositions.put(String.valueOf(currentY)+"+"+String.valueOf(currentX+1),1); //all visited positions
                currentPath.put(String.valueOf(currentY)+"+"+String.valueOf(currentX+1),1); // current path of solution
                history.add(String.valueOf(currentY)+"+"+String.valueOf(currentX+1)); // USED during backtrack to get to previous position
           
                currentX=currentX+1;
           }
            else if(currentY+1>=0&&currentY+1<maze[0].length&&maze[currentY+1][currentX]==0&&!visitedPositions.containsKey(String.valueOf(currentY+1)+"+"+String.valueOf(currentX)))//check SOUTH of point, X STAYS SAME, Y CHANGES
           {
              
               visitedPositions.put(String.valueOf(currentY+1)+"+"+String.valueOf(currentX),1);
               currentPath.put(String.valueOf(currentY+1)+"+"+String.valueOf(currentX),1);
               history.add(String.valueOf(currentY+1)+"+"+String.valueOf(currentX));
            
               currentY=currentY+1;
           }
            else if(currentX-1>=0&&currentX-1<maze[1].length&&maze[currentY][currentX-1]==0&&!visitedPositions.containsKey(String.valueOf(currentY)+"+"+String.valueOf(currentX-1)))//check WEST of point, X CHANGES, Y STAYS SAME
           {
         
               visitedPositions.put(String.valueOf(currentY)+"+"+String.valueOf(currentX-1),1);
               currentPath.put(String.valueOf(currentY)+"+"+String.valueOf(currentX-1),1);
               history.add(String.valueOf(currentY)+"+"+String.valueOf(currentX-1));
               currentX=currentX-1;
           }
           else if(currentY-1>=0&&currentY-1<maze[0].length&&maze[currentY-1][currentX]==0&&!visitedPositions.containsKey(String.valueOf(currentY-1)+"+"+String.valueOf(currentX)))//checking NORTH of point, X STAYS SAME, Y CHANGES
           {
               visitedPositions.put(String.valueOf(currentY-1)+"+"+String.valueOf(currentX),1);
               currentPath.put(String.valueOf(currentY-1)+"+"+String.valueOf(currentX),1);
               history.add(String.valueOf(currentY-1)+"+"+String.valueOf(currentX));
            
               currentY=currentY-1;
           }

           else
           { 
                   //if we reached a WALL or Position already been to
                   try
                   {        
                        currentPath.remove(history.get(history.size()-1));// remove current position from solution by getting the last position from history arrayList
                        history.remove(history.size()-1);// remove last element from arrayList
                    
                        // position is stored of form 1+1 meaning position [1,1]
                        String position=history.get(history.size()-1);
                        String[]temp=position.split("\\+");
                        //set the previous position as the new starting point and go back
                        currentY=Integer.parseInt(temp[0]);currentX=Integer.parseInt(temp[1]);
                       
                   }
                   catch(Exception e)
                   {
                       //cannot be solved
                       System.out.println("Unsolvable");
                       System.out.println(e);
                       break;
                   }
           } 
       }
           
    }
    public int getEndX()
    {
        return endX;
    }
    public int getEndY()
    {
        return endY;
    }
    public int getStartX()
    {
        return startX;
    }
    public int getStartY()
    {
        return startY;
    }
    public void setMaze(int[][] temp)
    {
        maze=temp;
    }
    public void startSolving(int y, int x)
    {
       // add the starting positions and call method to solve
       visitedPositions.put(String.valueOf(y)+"+"+String.valueOf(x),0);
       history.add(String.valueOf(y)+"+"+String.valueOf(x));
       
       solveMaze(y,x);
    }
}
