/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author paterne
 */
public class Table {
    String tableName;
    DataOperations operation = new DataOperations();
    ArrayList<String> valiables = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();
    
    public void save(){
        if(values.size()==valiables.size() ){
            
            operation.insert(tableName, valiables, values);
            System.out.println("well inserted");
        }
        else{
            System.out.print("fail to insert data in headmaster table");
        }
    }
    
    public static void select(){
        
    }
    
}
