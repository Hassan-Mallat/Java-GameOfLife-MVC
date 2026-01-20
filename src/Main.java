

import javax.swing.SwingUtilities;
import modele.Environnement;
import modele.Ordonnanceur;
import vue_controleur.FenetrePrincipale;

/**
 *
 * @author frederic
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable(){
			public void run(){

				Environnement e = new Environnement(40, 40);
				System.out.println("done");

				Ordonnanceur o = new Ordonnanceur(500, e);


				FenetrePrincipale fenetre = new FenetrePrincipale(e,o);


				e.addObserver(fenetre);
				fenetre.setVisible(true);

				

				o.start();

				
			}
		});
		
		
		
    }

}
