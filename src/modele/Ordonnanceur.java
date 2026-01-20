package modele;

import static java.lang.Thread.*;

public class Ordonnanceur extends Thread {

    private long sleepTime;
    private Runnable runnable;
    private boolean is_running = true;
    //private boolean start = false;
    public Ordonnanceur(long _sleepTime, Runnable _runnable) {
        sleepTime = _sleepTime;
        runnable = _runnable;

    }

    public boolean getIs_running(){

        return is_running;
    }

    public void setIsrunning(boolean n) {
        is_running = n;
    }

    public long getSleep(){

        return sleepTime;
    }

    public void setSleep(long n){
        sleepTime += n;
    }

    /*public boolean setStart(){

        return start = true;
    }*/


    

    public void run() {
            while (true) {
                if(is_running){
    
                    runnable.run();
    
                }
                try {
                    sleep(sleepTime);
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    
                
                }
    
                
            }
        }
        
    

   
}
