
package modele;

import java.util.Observable;
import java.util.HashMap;

public class Environnement extends Observable implements Runnable {
    private Case[][] tab;
    HashMap<Case, Point> Map = new HashMap<Case, Point>();


    class Point {

        private  int X;
        private  int Y;

        public Point(int x, int y){
            this.X = x;
            this.Y = y;
        } 

       
    }


    public Environnement(int _sizeX, int _sizeY) {

        sizeX = _sizeX;
        sizeY = _sizeY;
       

        tab = new Case[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tab[i][j] = new Case(this); 
                
                Map.put(tab[i][j], new Point(i,j));

            }
        }
    }

    private int sizeX, sizeY;

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }


    public boolean getState(int x, int y) {
        return tab[x][y].getState();
    }

    public Case getCase(Case source, Direction dir) {
        
        Point pn = Map.get(source);

        if (pn == null) {
            System.out.println("not found " + source);
            return null;
        }
        
        int newX = pn.X;
        int newY = pn.Y;
        
        switch (dir) {

            case h:
                newY += 1;
                break;

            case gh:
                newY += 1;
                newX -= 1;
                break;

            case hd:
                newY += 1;
                newX += 1;
                break;

            case d:
                newX += 1;
                break;

            case g:
                newX -= 1;
                break;

            case b:
                newY -= 1;
                break;

            case bg:
                newY -= 1;
                newX -= 1;
                break;

            case db:
                newY -= 1;
                newX += 1;
                break;

            default:
                throw new AssertionError();
        }

        if (newX < 0 || newY < 0 || newX >= sizeX || newY >= sizeY){

           // System.out.println("out of bound"+ newX + ","+ newY);
            return null;

        } 
        return tab[newX][newY];
        
    }


   

    public void setState(int x, int y, boolean state) {
        if (x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
            tab[x][y].setState(state);
            setChanged();
            notifyObservers(); 
        }
    }

    public void rndState() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tab[i][j].rndState();
            }
        }

    }



    public void nextState(){
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tab[i][j].nextState();
            }
        }
    }


    public void update(){
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                 tab[i][j].update();
            }
        }
       
    }



    public void clear(){

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                tab[i][j].setState(false);
            }
        }
        
    }




    @Override
    public void run() {
        //System.out.println("Environnement running");  
        nextState();
        update();   

        setChanged();

        notifyObservers();
        
    }
}
