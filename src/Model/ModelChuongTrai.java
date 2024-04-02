/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Duong
 */
public class ModelChuongTrai {
    private String Name,Img;
    private int ID,QuantityCurrent,Quantity;
    private float Area;

    public ModelChuongTrai() {
    }

    public ModelChuongTrai(String name, float area, int quantity_current, int quantity, String img) {
        this.Name = name;
        this.Img = img;
        this.QuantityCurrent = quantity_current;
        this.Quantity = quantity;
        this.Area = area;
    }

    @Override
    public String toString() {
        return (Name+ID+Area+Quantity).toLowerCase();
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }
    
    public int getId() {
        return ID;
    }

    public void setId(int Id) {
        this.ID = Id;
    }

    public int getQuantity_current() {
        return QuantityCurrent;
    }

    public void setQuantity_current(int Quantity_current) {
        this.QuantityCurrent = Quantity_current;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public float getArea() {
        return Area;
    }

    public void setArea(float Area) {
        this.Area = Area;
    }

}
