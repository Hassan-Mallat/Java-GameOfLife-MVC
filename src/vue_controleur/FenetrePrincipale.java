package vue_controleur;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


//import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;
import modele.Environnement;
import modele.Ordonnanceur;
import modele.Point;


/**
 *
 * @author frederic
 */
public class FenetrePrincipale extends JFrame implements Observer {

    private JPanel[][] tab;
    Environnement env;
    private Ordonnanceur o ;
    private HashMap<Point, Color> caseColors; // pour stocker les couleurs des cases vivantes
    private String motif_selectionne = "None"; // stocker les motifs selectionnees
    private int previewX = -1, previewY = -1; // tracker les coord prevues, -1 pour "no preview" state
                                              // -1 for representing an invalid or non existing

    



    public FenetrePrincipale(Environnement _env, Ordonnanceur _o) {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        caseColors = new HashMap<>();
        env = _env;
        build();
        o = _o;
        
    }

   

    public void build() {
        
        setTitle("Jeu de la Vie");
        setSize(800, 700);
        
        // Panneau principal
        JPanel pan = new JPanel(new BorderLayout());
        
        
        // Panneau central
        JComponent pan1 = new JPanel (new GridLayout(env.getSizeX(),env.getSizeY()));
        tab = new JPanel[env.getSizeX()][env.getSizeY()];


        Border blackline = BorderFactory.createLineBorder(Color.black,1);
        pan1.setBorder(blackline);
        for(int i = 0; i<env.getSizeX();i++){
            for (int j = 0; j < env.getSizeY(); j++) {

                tab[i][j] = new JPanel();


                
                int a = i;
                int b = j;
                // mouvemenet de la souris prevue
                tab[i][j].addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) { // une methode pour récupérer les mouvements de la souris
                        if (!motif_selectionne.equals("None")) { // assurer que l'utilisateur à bien choisit un motif
                                                                          
                            previewX = a;
                            previewY = b;
                            update(null, null); // Refresh pour afficher la prevue (preview)
                        }
                    }
                });

                //Click 
                tab[i][j].addMouseListener(new MouseAdapter() { 

                    @Override

                    public void mouseClicked(MouseEvent e) { //récupere l'event click

                        if (!motif_selectionne.equals("None")) {
                            PoseMotif(a, b); // met le motif sur les coordonees de la case
                            previewX = -1;
                            previewY = -1;
                            motif_selectionne = "None"; // Reset apres placement
                        } else {
                            boolean currentState = env.getState(a, b); 
                            env.setState(a, b, !currentState); //change l'état de la case après le click
                        }
                        update(null, null);

                    }
                });


                pan1.add(tab[i][j]);
            }

        }

        
        /***  Panneau pour les boutons ****/
        JPanel pan2 = new JPanel(new FlowLayout());
        JPanel pan3 = new JPanel();
        pan3.setLayout(new BoxLayout(pan3, BoxLayout.Y_AXIS));

        JButton b1 = new JButton("Pause");
        JButton b2 = new JButton("Resume");
        JButton b3 = new JButton("+");
        JButton b4 = new JButton("-");
        //JButton b5 = new JButton("Play");
        JButton b6 = new JButton("Clear");
        b6.setMaximumSize(new Dimension(150,50));
        JButton b7 = new JButton("Random Color");
        b7.setMaximumSize(new Dimension(150,50));
        JButton b8 = new JButton("Glider");
        b8.setMaximumSize(new Dimension(150,80));

        JButton b9 = new JButton("Blinker");
        b9.setMaximumSize(new Dimension(150,80));

        JButton b10 = new JButton("Block");
        b10.setMaximumSize(new Dimension(150,80));

        JButton b11 = new JButton("Crosshair");
        b11.setMaximumSize(new Dimension(150,80));








        /*****Ajoute le bouton sur le panneau*****/
        pan2.add(b1);
        pan2.add(b2);
        pan2.add(b3);
        pan2.add(b4);
        
        //pan3.add(b5);
        pan3.add(b6);
        pan3.add(Box.createVerticalStrut(20));
        pan3.add(b7);
        pan3.add(Box.createVerticalStrut(30));
        pan3.add(b8);
        pan3.add(Box.createVerticalStrut(20));
        pan3.add(b9);
        pan3.add(Box.createVerticalStrut(20));
        pan3.add(b10);
        pan3.add(Box.createVerticalStrut(20));
        pan3.add(b11);






        /****Action des boutons******/

        //bouton pause
        b1.addActionListener(e -> {
            if (o.isAlive()) {

                o.setIsrunning(false);
            }
        });
        //bouton resume
        b2.addActionListener(e -> {
            
            o.setIsrunning(true);
        });

        //bouton + (vitesse)
        b3.addActionListener(e -> { 
            if(o.getSleep() > 0){   
            o.setSleep(-100);}
        });
        

        //bouton - (vitesse)
        b4.addActionListener(e -> {
            
            o.setSleep(100);
        });
        

        //bouton pour réinitialiser l'environnement
        b6.addActionListener(e -> {
            
            env.clear();
        });
        
        //bouton pour mettre les color en random
        b7.addActionListener(e -> {
            
            RandomColors();
        });

        //bouton pour placer un glider
        b8.addActionListener(e -> motif_selectionne = "Glider");
        //bouton pour placer un blinker
        b9.addActionListener(e -> motif_selectionne = "Blinker");
        //bouton pour placer un bloc
        b10.addActionListener(e -> motif_selectionne = "Bloc");
        b11.addActionListener(e -> motif_selectionne = "Crosshair");



        
        //b8.setBackground(new java.awt.Color(232, 60, 94)); si on veut changer la couler des buttons :)
        
        pan.add(pan1, BorderLayout.CENTER);
        pan.add(pan3, BorderLayout.EAST);
        pan.add(pan2, BorderLayout.SOUTH);

        
        setContentPane(pan);
        

        
        // Ajout Menu
        JMenuBar jm = new JMenuBar();
        JMenu m = new JMenu("Fichier");
        JMenuItem mi = new JMenuItem("Charger");
        m.add(mi);
        jm.add(m);
        setJMenuBar(jm);


        
        
    }

    private void PoseMotif(int x, int y) { // dessine un motif

        if (motif_selectionne.equals("Glider")) {
            if (x + 2 < env.getSizeX() && y + 2 < env.getSizeY()) {
                env.setState(x, y + 1, true);
                env.setState(x + 1, y + 2, true);
                env.setState(x + 2, y, true);
                env.setState(x + 2, y + 1, true);
                env.setState(x + 2, y + 2, true);
            }

        } else if (motif_selectionne.equals("Blinker")) {
            if (x >= 0 && x + 2 < env.getSizeX() && y >= 0 && y < env.getSizeY()) {
                env.setState(x, y, true);
                env.setState(x + 1, y, true);
                env.setState(x + 2, y, true);
            }
        }
        else if (motif_selectionne.equals("Bloc")) {
            if (x >= 0 && x + 2 < env.getSizeX() && y >= 0 && y < env.getSizeY()) {
                env.setState(x, y, true);
                env.setState(x + 1, y, true);
                env.setState(x+1, y+1, true);
                env.setState(x , y + 1, true);
            }
        }
        else if (motif_selectionne.equals("Crosshair")) {
            if (x >= 0 && x + 2 < env.getSizeX() && y >= 0 && y < env.getSizeY()) {
                env.setState(x, y, true);
                env.setState(x + 1, y, true);
                env.setState(x+2, y, true);
                env.setState(x+1 , y + 1, true);
            }
        }

        
    }

    private void previewPattern(Graphics g, int x, int y) { //met en surbrillance les case avant de poser un motif
        if (motif_selectionne.equals("Glider") && x + 2 < env.getSizeX() && y + 2 < env.getSizeY()) {
            highlightCell(x, y + 1);
            highlightCell(x + 1, y + 2);
            highlightCell(x + 2, y);
            highlightCell(x + 2, y + 1);
            highlightCell(x + 2, y + 2);

        } else if (motif_selectionne.equals("Blinker") && x >= 0 && x + 2 < env.getSizeX() && y >= 0 && y < env.getSizeY()) {
            highlightCell(x, y);
            highlightCell(x + 1, y);
            highlightCell(x + 2, y);
        }
        else if (motif_selectionne.equals("Bloc") && x >= 0 && x + 2 < env.getSizeX() && y >= 0 && y < env.getSizeY()) {
            highlightCell(x, y);
            highlightCell(x + 1, y);
            highlightCell(x+1, y+1);
            highlightCell(x , y + 1);
        }
        else if (motif_selectionne.equals("Crosshair") && x >= 0 && x + 2 < env.getSizeX() && y >= 0 && y < env.getSizeY()) {
            highlightCell(x, y);
            highlightCell(x + 1, y);
            highlightCell(x+2, y);
            highlightCell(x+1 , y + 1);
        }

    }

    private void highlightCell(int x, int y) { // definit la couleur de la surbrillance
        if (x >= 0 && x < env.getSizeX() && y >= 0 && y < env.getSizeY()) {
            tab[x][y].setBackground(Color.red); 
        }
    }






    public void RandomColors() {//methode qui met des couleur aléatoires pour les cases
        Random random = new Random();
        for (int i = 0; i < env.getSizeX(); i++) {
            for (int j = 0; j < env.getSizeY(); j++) {
                if (env.getState(i, j)) { 
                    Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                    caseColors.put(new Point(i, j), randomColor);       
                } 
            }
        }
        update(null, null); 

    }


   


    @Override
    public void update(Observable o, Object arg) {
        // raffraîchissement de la vue
        //System.out.println("Update called");
        Border blackline = BorderFactory.createLineBorder(Color.black,1); // montrer les borders des cases
        

        for(int i = 0; i<env.getSizeX();i++){
            for (int j = 0; j < env.getSizeY(); j++) {

                if (env.getState(i, j)) {
                    Color couleur = caseColors.getOrDefault(new Point(i, j), Color.white); 
                    tab[i][j].setBorder(blackline);
                    tab[i][j].setBackground(couleur);
                    

                } else {

                    
                    tab[i][j].setBorder(blackline);
                    tab[i][j].setBackground(Color.BLACK);

                }

               // System.out.println("hello");
               
                
                
            }

        }
        if (previewX != -1 && previewY != -1) {
        previewPattern(getGraphics(), previewX, previewY);
        }

    }
}
