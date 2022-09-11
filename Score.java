
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Score {

    private ArrayList<String> scoresWithName;
    private ArrayList<Integer> scores;
    private ArrayList<String> scoresWithNameWithRank;



    public Score(){
        scores = new ArrayList<>(10);
        scoresWithName = new ArrayList<>(10);
    }


    // cette fonction sert à ajouter une score dans notre scoreList.
    public ArrayList<String> updateScore(int score, String nom) {


        // si nom est vide, alors, on change rien.
        if (nom == null) {
            return new ArrayList<>();
        }

        else {
            if (scores.size() == 0) {
                scores.add(score);
                scoresWithName.add(nom + "-" + score);
            }
            else if (scores.size() == 1) {
                if (score > scores.get(0)) {
                    scores.add(0, score);
                    scoresWithName.add(0, nom + "-" + score);
                } else {
                    scores.add(1, score);
                    scoresWithName.add(1, nom + "-" + score);
                }

            }


            else if (scores.size() < 10) {

                if (score > scores.get(0)) {
                    scores.add(0, score);
                    scoresWithName.add(0, nom + "-" + score);
                } else if (score < scores.get(scores.size() - 1)) {
                    scores.add(score);
                    scoresWithName.add(nom + "-" + score);
                }

                // sinon, on l'insert à un bon position.
                else {
                    for (int i = 0; i < scores.size() - 1; i++) {
                        if (score < scores.get(i) && score >= scores.get(i + 1)) {
                            scores.add(i+1, score);
                            scoresWithName.add(i+1, nom + "-" + score);
                            break;
                        }
                    }
                }
            }

            // le cas ou scores.size==  10
            else {

                if (score > scores.get(0)) scores.add(0, score);
                scoresWithName.add(0, nom + "-" + score);

                if (score < scores.get(9)) return getScoresWithNameWithRank();
                    // si le score est plus petite que la dernier score, on fait rien.

                    // sinon, on l'insert dans sa bonne position.
                else {
                    for (int i = 0; i < scores.size() - 1; i++) {
                        if (score < scores.get(i) && score >= scores.get(i + 1)) {
                            scores.add(i+1, score);
                            scoresWithName.add(i+1, nom + "-" + score);
                            // et on retire la dernier score.
                            scores.remove(10);
                            scoresWithName.remove(10);
                            break;
                        }
                    }
                }
            }

            return getScoresWithNameWithRank();
        }}


    public ArrayList<String> getScoresWithNameWithRank(){
        scoresWithNameWithRank = new ArrayList<>();
        for (int i = 0; i < scoresWithName.size(); i++) {
            scoresWithNameWithRank.add(i, "#" + (i + 1) + "-" + scoresWithName.get(i));

        }
        return scoresWithNameWithRank;

    }


    public void writeRecords() {

        ArrayList<String> scoresToWrite = getScoresWithNameWithRank();
        try {
            FileWriter fw = new FileWriter("BestScore.txt");
            BufferedWriter writer = new BufferedWriter(fw);
            for (String record : scoresToWrite){
                writer.append(record);
            }

            writer.close();
        } catch (IOException ex) {
            System.out.println("Erreur à l'écriture du fichier");
        }
    }

    public ArrayList<String> readRecords(){

        ArrayList<String> records = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new FileReader("high-scores.txt"));
            while (scan.hasNext()) {

                String line = scan.nextLine();

                records.add(line);
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("Erreur à l'ouverture du fichier");
        }
        return records;
    }




}
