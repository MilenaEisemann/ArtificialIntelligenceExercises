/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knearestneighbour;

/**
 *
 * @author Milena
 */
public class DataObject{
    public double[] attributes;     //values for all attribute that are used to categorize the dataObject
    public double classType;        //classification of this dataObject
    
    
    public DataObject(double[] attributes, double classType){
        this.attributes = attributes;
        this.classType = classType;
    }

}
