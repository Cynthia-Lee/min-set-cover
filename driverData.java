import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import java.text.SimpleDateFormat;
import java.util.Calendar;

// import java.util.HashSet;
// import java.util.Set;

public class driverData {
    public static void main(String[] args) throws Exception {
        // process the data
        // first line will contain the size of the universal set
        // second line will contain the number of subsets
        // each line after that will contain the associated subset

        // File file1 = new File("./s-X-12-6.txt");
        File file1 = new File("./s-rg-63-25.txt");
        // File file1 = new File("./test.txt");
        ArrayList<ArrayList<Integer>> subsets = new ArrayList<>(); // arraylist of all the subests
        int universal;
        int numSubsets;

        try (BufferedReader br = new BufferedReader(new FileReader(file1))) {
            String line;
            universal = Integer.parseInt(br.readLine()); // first line
            numSubsets = Integer.parseInt(br.readLine()); // second line
            while ((line = br.readLine()) != null) {
                // process the line for each individual subset
                ArrayList<Integer> subset = new ArrayList<>();
                for (String s : line.split("\\s+")) { // String[] subsetArray = line.split("\\s+");
                    if (s.isEmpty()) {
                        
                    } else {
                        subset.add((Integer.parseInt(s)));
                    }
                }
                subsets.add(subset); 
            }
        }

        System.out.println(file1.getName());

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println( sdf.format(cal.getTime()) );

        // ArrayList<ArrayList<Integer>> result = new ArrayList<>(); // result
        // boolean[] a = new boolean[numSubsets]; // current combination
        // subsets = data... the subsets given (ints given)
        // universal = numbers needed to be covered
        ArrayList<ArrayList<Integer>> start = new ArrayList<>(); // result
        minSetCover f1 = new minSetCover(universal);
        f1.backtrack(start, subsets);
        System.out.println("Minimum set cover is:");
        System.out.println(Arrays.toString(f1.currSol.toArray()));
        System.out.println("Size of MSC is: " + f1.currSol.size());
        System.out.println();

        cal = Calendar.getInstance();
        SimpleDateFormat endTime = new SimpleDateFormat("HH:mm:ss");
        System.out.println( endTime.format(cal.getTime()) );

        // long difference = endTime.getTime() - sdf.getTime(); 
    }
}


class minSetCover {
    ArrayList<ArrayList<Integer>> currSol = new ArrayList<>();
    ArrayList<ArrayList<Integer>> a = new ArrayList<>();
    boolean[] check;
    int universal;
    
    public minSetCover(int universal) {
        this.check = new boolean[universal];
        this.universal = universal;
    }

    // BACKTRACK
    int test;

    public void backtrack(ArrayList<ArrayList<Integer>> a, ArrayList<ArrayList<Integer>> data) {
        // a = current combination
        // universal, need numbers 1 - universal
        // check is the universal cover check

        ArrayList<ArrayList<Integer>> candidates = new ArrayList<>(); // candidates of subsets
        int i; // counter

        this.check = new boolean[this.universal]; // reset the check
        // update the check array
        for (int w = 0; w < a.size(); w++) { // each subset
            ArrayList<Integer> currSubset = a.get(w);
            for (int j = 0; j < currSubset.size(); j++) { // inside the subset
                int num = currSubset.get(j); // each number in the subset
                // update the boolean[] check array
                this.check[num-1] = true;
            }
        }

        // System.out.println("testing " + Arrays.toString(a.toArray()));
        // System.out.println(++this.test); // 127
        if (isASolution(this.check)) {
            // System.out.println("...testing " + Arrays.toString(a.toArray()));
            // System.out.println(Arrays.toString(this.check));
            System.out.println(++this.test); // 64
            processSolution(a);
        } else { 
            // CONSTRUCT CANDIDTATES

            if (!currSol.isEmpty() && (a.size() > this.currSol.size())) {
                return; // candidates is an empty array
            }
            
            // get rid of last seen
            int number = 0;
            if (!a.isEmpty()) {
                number = data.indexOf(a.get(a.size()-1));
                // System.out.println("HELLO JO " + number);
            }

            for (int w = number; w < data.size(); w++) { // each subset
                ArrayList<Integer> currSubset = data.get(w);

                if (!a.contains(data.get(w))) { // if it is not in the subset already
                    for (int j = 0; j < currSubset.size(); j++) { // inside the subset
                        int num = currSubset.get(j); // each number in the subset
                        if (this.check[num-1] == false) {
                            candidates.add(currSubset);
                            break;
                        }
                    } // done with the subset
                }
            }

            // System.out.println("a is " + Arrays.toString(a.toArray()));
            // System.out.println("candidates are " + Arrays.toString(candidates.toArray()));
            // System.out.println("out " + this.k);

            for (i = 0; i < candidates.size(); i++) {
                // a.add(candidates.get(i)); // add subset to current combination

                // make move
                a.add(candidates.get(i));

                // System.out.println("a is " + Arrays.toString(a.toArray()));
                // System.out.println("check is " + Arrays.toString(this.check));

                // backtrack
                backtrack(a, data);

                // unmake move
                a.remove(a.size()-1);
            }
        }
    }
    

    public boolean isASolution(boolean[] b) { // is a set cover
        for (int i = 0; i < b.length; i++) {
            if (b[i] == false) {
                return false;
            }
        }
        return true;
    }

    public void processSolution(ArrayList<ArrayList<Integer>> a) { // print out

        if (this.currSol.isEmpty() || (a.size() < this.currSol.size())) {
            this.currSol = new ArrayList(a); // clone
        }
    }

    /*
    public boolean andCheck(ArrayList<boolean[]> a) {
        // like bitwise and
        // and each boolean array. and each subset

    }
    */
}