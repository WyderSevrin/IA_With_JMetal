import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * capteur avec un rayon de 150
 * plateau de taille 600x700
 */
public class SensorDeployment extends Problem {

    private double rayonCapteur = 80;
    private String file = "cibles3.txt";  //TODO -------------------------------------> : Le chemin du fichier contenant les cibles

    public SensorDeployment(String solutionType, Integer numberOfVariables){
        numberOfVariables_ = numberOfVariables;
        numberOfObjectives_ = 2;
        numberOfConstraints_ = 0;
        problemName_ = "SensorDeployment";

        if(solutionType.compareTo("Real") == 0){
            solutionType_ = new RealSolutionType(this);
        }else{
            System.exit(-1);
        }

        /**
            C'etait écrit au tableau avant le début du cours
            Permet d'initialiser les coordonées des capteurs avec longeur max = 700 et hauteur max de 600
            On gros on est sur un tableau de 700x600
         */
        upperLimit_ = new double[numberOfVariables_];
        lowerLimit_ = new double[numberOfVariables_];
        for(int i = 0; i < numberOfVariables_; i++){
            lowerLimit_[i] = 0;
            if(i%2 == 0){
                upperLimit_[i] = 700;
            }else{
                upperLimit_[i] = 600;
            }
        }
    }

    @Override
    public void evaluate(Solution solution) throws JMException {
        Variable[] decisionVariables = solution.getDecisionVariables();

        //On extrait les coordonées des capteurs pour les gérer plus simplement
        double [] x = new double[numberOfVariables_/2];
        double [] y = new double[numberOfVariables_/2];


        int counterForX =0;
        int counterForY =0;
        for(int i = 0; i < numberOfVariables_; i++){
            lowerLimit_[i] = 0;
            if(i%2 == 0){
                x[counterForX] = decisionVariables[i].getValue();
                counterForX++;
            }else{
                y[counterForY] = decisionVariables[i].getValue();
                counterForY++;
            }
        }

        //recherche de la proximité
        int nbCible = 5; //TODO -----------------------------------------------------------------> : Rendre ça dynamique
        int[] T = new int[nbCible];

        for(int i = 0; i < nbCible; i++){
            T[i] = 0; //initialisation
        }

        //On charge les coordonnées des cibles
        Double[] cibles = new Double[nbCible*2];
        try {
            cibles = loadTargets(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * nbCible --> nombre de cible
         * cibles --> tableau contenant les cibles (1 dimension [x1,y1,x2,y2,x3,y3,xn,yn])
         * T --> cibles couvert à maximiser
         */
        //Objectif 1
        for(int i = 0; i < cibles.length-2 ; i=i+2){
            double distance =0;
            if(i%2 == 0){
                for(int j =0; j< x.length; j++){
                    distance = sqrt(pow(cibles[i] - x[j],2) + pow(cibles[i+1] - y[j],2));
                    if(distance < rayonCapteur){
                        T[i/2]++;
                    }
                }

            }
        }

        double nbCiblesCouvert=0; //f1
        for (int i =0; i<nbCible;i++){
            if(T[i]>0){
                nbCiblesCouvert++;
            }
        }

        //Objectif 2 --> a Maximiser
        double minimumOfT = 0; //f2
        for(int i = 0; i<nbCible;i++){
            if(minimumOfT<T[i]){
                minimumOfT = T[i];
            }
        }

        double f1 = nbCiblesCouvert;
        double f2 = minimumOfT;

        solution.setObjective(0,-f1);
        solution.setObjective(1,-f2);
        //System.out.println("f1 : "+f1 +"    f2: "+f2);
        String txt = f1 +" "+(f2)+"\n";
        try {
            Files.write(Paths.get("pareto.txt"), txt.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     * LA bonne fonction avec les coord sur un tableau dim 1 mais pas tester encore
     * @param file
     * @return
     * @throws IOException
     */
    public static Double[] loadTargets(String file) throws IOException {
        int nbrLine = 0;
        BufferedReader br2 = new BufferedReader(new FileReader(file));
        while(br2.readLine() != null)
            nbrLine++;
        br2.close();

        Double[] matrice=new Double[nbrLine*2];
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            int counter=0;
            while ((line = br.readLine()) != null) {
                String[] val = line.split(";");
                matrice[counter] = Double.parseDouble(val[0]);
                counter++;
                matrice[counter] = Double.parseDouble(val[1]);
                counter++;

            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return matrice;
    }

}
