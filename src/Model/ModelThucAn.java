package Model;

import java.util.ArrayList;
import java.util.Iterator;

public class ModelThucAn {
    private int idThucAn;
    private String tenThucAn;
    private String loaiThucAn;
  
    private int idDongVat;
    private int soLuong;
    private String hanSuDung;
    private int idNCC;

    public ModelThucAn(int idThucAn, String tenThucAn, String loaiThucAn, int idDongVat, int soLuong, String hanSuDung, int idNCC) {
        this.idThucAn = idThucAn;
        this.tenThucAn = tenThucAn;
        this.loaiThucAn = loaiThucAn;
        
        this.idDongVat = idDongVat;
        this.soLuong = soLuong;
        this.hanSuDung = hanSuDung;
        this.idNCC = idNCC;
    }

    public int getIdThucAn() {
        return idThucAn;
    }

    public void setIdThucAn(int idThucAn) {
        this.idThucAn = idThucAn;
    }

    public String getTenThucAn() {
        return tenThucAn;
    }

    public void setTenThucAn(String tenThucAn) {
        this.tenThucAn = tenThucAn;
    }

    public String getLoaiThucAn() {
        return loaiThucAn;
    }

    public void setLoaiThucAn(String loaiThucAn) {
        this.loaiThucAn = loaiThucAn;
    }


    public int getIdDongVat() {
        return idDongVat;
    }

    public void setIdDongVat(int idDongVat) {
        this.idDongVat = idDongVat;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(String hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public int getIdNCC() {
        return idNCC;
    }

    public void setIdNCC(int idNCC) {
        this.idNCC = idNCC;
    }

    public void addFood(ModelThucAn food, ArrayList<ModelThucAn> foodList) {
        foodList.add(food);
    }

    public void updateFood(int id, ModelThucAn updatedFood, ArrayList<ModelThucAn> foodList) {
        for (ModelThucAn food : foodList) {
            if (food.getIdThucAn() == id) {
                foodList.set(foodList.indexOf(food), updatedFood);
                break;
            }
        }
    }

    public void deleteFood(int id, ArrayList<ModelThucAn> foodList) {
        for (Iterator<ModelThucAn> iterator = foodList.iterator(); iterator.hasNext(); ) {
            ModelThucAn food = iterator.next();
            if (food.getIdThucAn() == id) {
                iterator.remove();
            }
        }
    }

    // Add other relevant methods as needed
}
