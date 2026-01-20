package modele;

import java.util.Random;

public class Case {
    private static final Random rnd = new Random();

    Environnement e;
    private boolean etat_suivant;
    private boolean state;


    public Case(Environnement _e){
     this.e = _e;    
    }

    public boolean getState() {

        return state;
    }


    
    public void rndState() {
        state = rnd.nextBoolean();
    }
    
    public void setState(boolean n) {
        state = n;
    } 

    public void nextState() {

        int vivant = 0;

        for (Direction dir : Direction.values()) {
            Case voisin = e.getCase(this, dir); 
            if (voisin != null && voisin.getState()) {
            vivant++;
            }
        }

        if (state) { 
            etat_suivant = vivant == 2 || vivant == 3; 
        } else { 
        etat_suivant = vivant == 3; 
        }


        
    }


    public void update(){

        state = etat_suivant;
        //System.out.println("up");
   }

    
    
    

    

}
