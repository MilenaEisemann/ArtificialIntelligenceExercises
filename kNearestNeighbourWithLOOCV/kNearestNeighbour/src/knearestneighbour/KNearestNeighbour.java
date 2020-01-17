/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package knearestneighbour;
/**
 *
 * @author Milena
 * This code uses the data in app1data.txt and app1test.txt to perform the KnearestAlgorithm using crossvalidation
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class KNearestNeighbour {
   public static int k = 5;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<DataObject> dataObjs= new ArrayList<>();
        ArrayList<DataObject> testObjs = new ArrayList<>();
        ArrayList<DataObject> crossvalidationObjs = new ArrayList<>();
        
        //to test with one single dataObject
        //at the moment still used for generating length for AttributArrays
        //examplary data: {17,2,0,0,1,0,1,0,1,0,0,37.9,36.9,7500,0}
        // last value represents classification of the data 
        // other values are attributes
        double[] testAttributes = new double[]{17,2,0,0,1,0,1,0,1,0,0,37.9,36.9,7500,0};
        
        double[] minAttributs = new double[testAttributes.length];
        double[] maxAttributs = new double[testAttributes.length];
        double[] rangeAttributs = new double[testAttributes.length];
        
        int classifiedCorrectly = 0;
        int classifiedIncorrectly = 0;
        
        // read data from file into objects
        BufferedReader reader;
        
        
        //read testData
        try {
            reader = new BufferedReader(new FileReader("src/data/app1test.txt"));
            String line = reader.readLine();
            while (line != null){
                String[] parts = line.split(",");
                String[] attributPart = Arrays.copyOfRange(parts, 0, parts.length-1);
                double classType = Double.parseDouble(parts[parts.length-1]);           //classification is saved in last value
                    
                double[] attributs = new double[attributPart.length];      
                
                for(int i = 0; i < attributs.length; i++){
                    attributs[i] = Double.parseDouble(attributPart[i]);
                }
                testObjs.add(new DataObject(attributs, classType));
                line = reader.readLine();
            }
        } catch (IOException e){
            e.printStackTrace();   
        }  
        
        //read actual data
        try {
            reader = new BufferedReader(new FileReader("src/data/app1data.txt"));
            String line = reader.readLine();
            while (line != null){
                String[] parts = line.split(",");
                String[] attributPart = Arrays.copyOfRange(parts, 0, parts.length-1);
                double classType = Double.parseDouble(parts[parts.length-1]);
                
                double[] attributs = new double[attributPart.length];      
                
                for(int i = 0; i < attributs.length; i++){
                    attributs[i] = Double.parseDouble(attributPart[i]);
                    
                    //update min/max values of attribute
                    if (attributs[i] < minAttributs[i]){
                        minAttributs[i] = attributs[i];
                    } else if (attributs[i] > maxAttributs[i]){
                        maxAttributs[i] = attributs[i];
                    } 
                }
                dataObjs.add(new DataObject(attributs, classType));
                line = reader.readLine();
            }
        } catch (IOException e){
            e.printStackTrace();   
        }  
        
        
        //save range of Attributes for normalization
        for (int i = 0; i < rangeAttributs.length; i++){
            rangeAttributs[i] = maxAttributs[i] - minAttributs[i];
        }
        
        //use both datasets for crossvalidation
        crossvalidationObjs.addAll(dataObjs);
        crossvalidationObjs.addAll(testObjs);
        
        int optimalK = crossValidation(crossvalidationObjs);
        System.out.println("optimal number of neighbours is: " + optimalK);
        
        //train final model with optimalGamma and all of X = crossvalidationObjs
        for(DataObject data : testObjs){
            if (kNearest(data, crossvalidationObjs, optimalK) == data.classType){
                classifiedCorrectly++;
            } else {
                classifiedIncorrectly++;
            }
        }
      
        System.out.println("classified correctly: " + classifiedCorrectly);
        System.out.println("classified incorrectly: " + classifiedIncorrectly);
    }
    
    //perform leave-one-out crossvalidation
    public static int crossValidation(ArrayList<DataObject> X){
        int gammaMin = 1;
        int gammaMax = X.size() - 1; 
        
        //ArrayList<DataObject> coppiedX = X;
        ArrayList<DataObject> coppiedX = new ArrayList<>(X);
        
        DataObject curTestData;
        
        float medianOfErrors[] = new float[gammaMax];
        
        for (int curGamma = gammaMin; curGamma < gammaMax; curGamma++){
            int sumOfErrors = 0;
            
            for (int i = 0; i < coppiedX.size(); i++){
                curTestData = coppiedX.remove(i);
                if (kNearest(curTestData, coppiedX, curGamma) != curTestData.classType){
                    sumOfErrors++;
                } 
                coppiedX = new ArrayList<>(X);
            }
            medianOfErrors[curGamma] = 1/(float)coppiedX.size() * sumOfErrors;
        }
        
        //find optimal gamma
        int optimalGamma = -1;
        float smallestMedianOfErrors = 999;
        for (int i = 1; i < medianOfErrors.length; i++){
            if (medianOfErrors[i] < smallestMedianOfErrors){
                smallestMedianOfErrors = medianOfErrors[i];
                optimalGamma = i;
            }
        }  
        return optimalGamma;
    }
    
    
    public static ArrayList<DataObject> excludeFromGroup(ArrayList<ArrayList<DataObject>> group, int i){
        ArrayList<DataObject> newList = new ArrayList<>();
        for (int j = 0; j < group.size(); j++){
            if (j != i){
                for (DataObject data : group.get(j)){
                    newList.add(data);
                }
            }
        }
        return newList;
        
    }
    
    //transform to values between 0 to 1
    public static double[] normalizeAttributes(double[] attributs, double[] min, double[] range){
        double[] normalizedAtts = new double[attributs.length];
        for (int i = 0; i < attributs.length; i++){
            normalizedAtts[i] = (attributs[i] - min[i])/range[i];
        }
        return normalizedAtts;
    }
    
    public static int kNearest(DataObject object, ArrayList<DataObject> dataObjs, int k){
        int counterClass0 = 0;
        int counterClass1 = 0;
        
        int[] indices = new int [k]; //contains index for each of k nearest dataObjs
        double[] minDists = new double[k];
        
        //initalize minDist so it is not initialized with 0 autimatically
        for (int i = 0; i < k; i++){
            minDists[i] = Double.MAX_VALUE; 
        }
        
        for (int i = 0; i < dataObjs.size(); i++){
            double dist = calculateDistance(object, dataObjs.get(i));
            
            double maxInMinDists = Double.MIN_VALUE;
            int indexMax = 0;
            
            //find biggest dist in k minDists
            for (int j = 0; j < k; j++){
                if (maxInMinDists < minDists[j]){
                    maxInMinDists = minDists[j];
                    indexMax = j;
                }
            }
            
            //check if new dist is smaller than largest in minDists
            if (minDists[indexMax] > dist){
                minDists[indexMax] = dist; //replace distance
                indices [indexMax] = i;
            }
        }
        
        for (int i = 0; i < k; i++){
            if (dataObjs.get(indices[i]).classType == 0){
                counterClass0++;
            } else {
                counterClass1++;
            }
        }
        
        //classify object
        if (counterClass0 > counterClass1){
            return 0;
        } else if (counterClass1 > counterClass0){
            return 1;
        } else {    //if counterClass0 == counterClass1, decide randomly
            Random r = new Random();
            return r.nextInt(2);
        }
    }
    
    //calculate euclidian distance between two dataObjects
    public static double calculateDistance(DataObject object, DataObject objectToCompare){
        double Sum = 0.0f;
        for(int i = 0; i < object.attributes.length; i++) {
           Sum = Sum + Math.pow((object.attributes[i]- objectToCompare.attributes[i]),2.0);
        }
        return Math.sqrt(Sum);
    } 
}
