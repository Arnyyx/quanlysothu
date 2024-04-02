/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Duong
 */
public class ModelMoiTruong {

    private int ID;
    private String IDHabitat, State, Description, Staff;
    private Date Date;

    public ModelMoiTruong(String IDHabitat, String Staff, Date Date, String State, String Description) {
        this.IDHabitat = IDHabitat;
        this.Staff = Staff;
        this.Date = Date;
        this.State = State;
        this.Description = Description;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIDHabitat() {
        return IDHabitat;
    }

    public void setIDHabitat(String IDHabitat) {
        this.IDHabitat = IDHabitat;
    }

    public String getStaff() {
        return Staff;
    }

    public void setStaff(String Staff) {
        this.Staff = Staff;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public ModelMoiTruong() {
    }

}
