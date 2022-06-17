package Agents;

import java.util.*;

public class Position {

    private int x;
    private int y;
    private Random rand;

    public Position(){
        this.x = rand.nextInt(101);
        this.y = rand.nextInt(101);
    }

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
            return this.x;
        }

    public int gety(){
            return this.y;
        }

    public double getDistance(Position p){
        double distance = 0;
        int otherX = p.getX();
        int otherY = p.gety();
        int xDiff = this.x - otherX;
        int yDiff = this.y - otherY;

        distance = Math.sqrt(Math.pow(xDiff,2) - Math.pow(yDiff,2));
        return distance;
    }

}
