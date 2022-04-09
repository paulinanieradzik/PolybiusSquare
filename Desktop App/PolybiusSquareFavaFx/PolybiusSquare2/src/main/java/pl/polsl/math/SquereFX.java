/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.math;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class which is uded to display content in TableView
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
public class SquereFX {
    
    private SimpleStringProperty first;
    private SimpleStringProperty second;
    private SimpleStringProperty third;
    private SimpleStringProperty fourth;
    private SimpleStringProperty fifth;
    private SimpleStringProperty sixth;
    
    public SquereFX(String first, String second, String third, String fourth, String fifth, String sixth) {
        this.first = new SimpleStringProperty(first);
        this.second = new SimpleStringProperty(second);
        this.third = new SimpleStringProperty(third);
        this.fourth = new SimpleStringProperty(fourth);
        this.fifth = new SimpleStringProperty(fifth);
        this.sixth = new SimpleStringProperty(sixth);
    }
    
    public void setFirst(String first){
        this.first = new SimpleStringProperty(first);
    }
    public void setSecond(String second){
        this.second = new SimpleStringProperty(second);
    }
    public void setThird(String third){
        this.third = new SimpleStringProperty(third);
    }
    public void setFourth(String fourth){
        this.fourth = new SimpleStringProperty(fourth);
    }
    public void setFifth(String fifth){
        this.fifth = new SimpleStringProperty(fifth);
    }
    public void setSixth(String sixth){
        this.sixth = new SimpleStringProperty(sixth);
    }
     public String getFirst(){
        return first.get();
    }
    public String getSecond(){
        return second.get();
    }
    public String getThird(){
        return third.get();
    }
    public String getFourth(){
        return fourth.get();
    }
    public String getFifth(){
        return fifth.get();
    }
    public String getSixth(){
        return sixth.get();
    }
    

}
