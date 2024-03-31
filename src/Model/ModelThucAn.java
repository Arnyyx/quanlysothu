package Model;

import java.util.ArrayList;
import java.util.Iterator;

public class ModelThucAn {

    private int IDThucAN;
    private String TenThucAn;
    private String LoaiThucAn;

    private int IdDongVat;
    private int SoLuong;
    private String HanThucAn;
    private int IdNCC;

    public ModelThucAn(String tenThucAn, String loaiThucAn, int idDongVat, int soLuong, String hanSuDung, int idNCC) {
        this.TenThucAn = tenThucAn;
        this.LoaiThucAn = loaiThucAn;
        this.IdDongVat = idDongVat;
        this.SoLuong = soLuong;
        this.HanThucAn = hanSuDung;
        this.IdNCC = idNCC;
    }

    public ModelThucAn() {

    }

    public int getIdThucAn() {
        return IDThucAN;
    }

    public void setIdThucAn(int idThucAn) {
        this.IDThucAN = idThucAn;
    }

    public String getTenThucAn() {
        return TenThucAn;
    }

    public void setTenThucAn(String tenThucAn) {
        this.TenThucAn = tenThucAn;
    }

    public String getLoaiThucAn() {
        return LoaiThucAn;
    }

    public void setLoaiThucAn(String loaiThucAn) {
        this.LoaiThucAn = loaiThucAn;
    }

    public int getIdDongVat() {
        return IdDongVat;
    }

    public void setIdDongVat(int idDongVat) {
        this.IdDongVat = idDongVat;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        this.SoLuong = soLuong;
    }

    public String getHanSuDung() {
        return HanThucAn;
    }

    public void setHanSuDung(String hanSuDung) {
        this.HanThucAn = hanSuDung;
    }

    public int getIdNCC() {
        return IdNCC;
    }

    public void setIdNCC(int idNCC) {
        this.IdNCC = idNCC;
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
        for (Iterator<ModelThucAn> iterator = foodList.iterator(); iterator.hasNext();) {
            ModelThucAn food = iterator.next();
            if (food.getIdThucAn() == id) {
                iterator.remove();
            }
        }
    }

    // Add other relevant methods as needed
}
