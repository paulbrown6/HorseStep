package logistic;

import score.Score;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Paul Brown on 10.11.2016.
 */
public class Logistic {

    public ArrayList<Integer> nextStep(int ind, ArrayList<Integer> oldstep){
        ArrayList<Integer> arr = new ArrayList<Integer>();
        int[][] arrint = new int[8][8];
        int h = 1;
        int x = 0;
        int y = 0;
        for(int i = 0; i < 8; i++){
            for (int k = 0; k < 8; k++){
                arrint[i][k] = h;
                if (h == ind){
                    x = i;
                    y = k;
                }
                h++;
            }
        }

        for (int i = 0; i < 8; i++){
            int a = 0;
            int b = 0;
            if (i == 0){
                b = y - 1;
                a = x - 2;
            }
            if (i == 1){
                b = y + 1;
                a = x - 2;
            }
            if (i == 2){
                b = y + 2;
                a = x - 1;
            }
            if (i == 3){
                b = y + 2;
                a = x + 1;
            }
            if (i == 4){
                b = y + 1;
                a = x + 2;
            }
            if (i == 5){
                b = y - 1;
                a = x + 2;
            }
            if (i == 6){
                b = y - 2;
                a = x + 1;
            }
            if (i == 7){
                b = y - 2;
                a = x - 1;
            }
            if (b >= 0 && b <= 7 && a >= 0 && a <= 7) {
                int in = arrint[a][b];
                boolean df = true;
                for (int oldf: oldstep) {
                    if (in == oldf) {
                        System.out.println("добавить " + in + " нельзя");
                        df = false;
                    }
                }
                if (df) {
                    arr.add(in);
                }
            }

        }
        return arr;
    }

    public ArrayList<Score> scores(){
        ArrayList<Score> sc = new ArrayList<Score>();
        try {
            File file = new File("resources/text/Score.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s;
            while (!(s = reader.readLine()).equals("")){
                sc.add(new Score(s.split(" - ")[0], Integer.parseInt(s.split(" - ")[1])));
            }
            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        for (Score s: sc){
            String q = s.getName();
            if (q.length() < 7){
                int n = 7 - q.length();
                String e = q;
                for (int i = 0; i < n; i++){
                    e = e + " ";
                }
                q = e;
            }
            if (q.length() > 7){
                char[] ch = new char[q.length()];
                String w = "";
                for(int i = 0; i < 7; i++){
                    w = w + ch[i];
                }
                q = w;
            }
            s.setName(q);
        }
        return sc;
    }

    public void scoreAdd(Score sc){
        try {
            File file = new File("resources/text/Score.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
            writer.write("\n" + sc.getName() + " - " + sc.getScore());
            writer.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
