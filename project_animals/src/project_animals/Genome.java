package project_animals;

import java.util.Arrays;
import java.util.Random;

public class Genome {
    static final int genomeLength = 32;
    private int[] genes = new int[genomeLength];
    private static Random rand = new Random();
    private int definingGene;

    public Genome(int[] arr){
        if(arr.length!=32) throw new RuntimeException("Genome must have "+genomeLength+"  genes!");
        for(int i=0;i<genomeLength;i++){
            genes[i]=arr[i];
        }
        Arrays.sort(this.genes);
        this.definingGene = getDefiningGeneHelper(this.genes);
    }


    public int getGene(int i){
        return genes[i];
    }

    public Genome combine(Genome other){
        int breakOne = rand.nextInt(genomeLength);
        int breakTwo = rand.nextInt(genomeLength);
        while(breakOne == breakTwo) breakTwo = rand.nextInt(genomeLength);

        if(breakOne>breakTwo){
            int tmp = breakOne;
            breakOne = breakTwo;
            breakTwo = tmp;
        }

        int k=0;
        int [] tmp_arr = new int[genomeLength];

        for(;k<=breakOne;k++)tmp_arr[k]=this.getGene(k);
        for(;k<=breakTwo;k++)tmp_arr[k]=other.getGene(k);
        for(;k<genomeLength;k++)tmp_arr[k]= this.getGene(k);

        return Genome.fromArray(tmp_arr);


    }

    public void mutate(){
        //random mutation
        if(rand.nextInt(100)==10){
            this.genes[rand.nextInt(32)] = rand.nextInt(8);
        }

        //adds copies od "lost" genes to genome
        for(int i=0; i<8;i++){
            final int tmp = i;
            if(! Arrays.stream(this.genes).anyMatch(x -> x == tmp)){
                this.genes[rand.nextInt(genomeLength)]=i;
            }
        }

        Arrays.sort(this.genes);
    }

    public static Genome fromArray(int[] arr){
        return new Genome(arr);
    }

    public static Genome radnomGenome(){
        int[] arr = new int[genomeLength];
        for(int i=0;i<genomeLength;i++){
            if(i<8){arr[i]=i;}
            else{arr[i] = rand.nextInt(8);}
        }
        Arrays.sort(arr);
        return Genome.fromArray(arr);
    }

    public int[] toArray(){
        return genes.clone();
    }

    @Override
    public String toString() {
        String str ="|";
        for(int i=0;i<this.genes.length;i++){
            str+=genes[i];
            str+="|";
        }
        str+="|";
        return str;
    }

    public int getDefiningGene(){
        return this.definingGene;
    }

    static private int getDefiningGeneHelper(int[] a)
    {
        int popular = a[0];
        int count = 1;
        int popularContender = -1;
        int countContender = 0;
        for(int i=1;i<a.length;i++){
            if(a[i]==popular){
                count++;
            }else if(a[i]==popularContender){
                countContender++;
                if(countContender>count){
                    popular = popularContender;
                    count = countContender;
                }
            }else{
                popularContender = a[i];
                countContender = 1;
            }
        }

        return popular;
    }
}
