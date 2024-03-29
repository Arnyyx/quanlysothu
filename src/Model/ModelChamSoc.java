/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class ModelChamSoc {

    int IDChamSoc;
    String TenDongVat, LoaiChamSoc;
    Date NgayChamSoc;
    String TenNhanVien, KetQua;

    public ModelChamSoc() {
    }

    public ModelChamSoc(String TenDongVat, String LoaiChamSoc, Date NgayChamSoc, String TenNhanVien, String KetQua) {
        this.TenDongVat = TenDongVat;
        this.LoaiChamSoc = LoaiChamSoc;
        this.NgayChamSoc = NgayChamSoc;
        this.TenNhanVien = TenNhanVien;
        this.KetQua = KetQua;
    }

    @Override
    public String toString() {
        return (IDChamSoc + TenDongVat + LoaiChamSoc + Utils.Utility.sdf.format(NgayChamSoc) + TenNhanVien + KetQua).toLowerCase();
    }

    public int getIDChamSoc() {
        return IDChamSoc;
    }

    public void setIDChamSoc(int IDChamSoc) {
        this.IDChamSoc = IDChamSoc;
    }

    public String getTenDongVat() {
        return TenDongVat;
    }

    public void setTenDongVat(String TenDongVat) {
        this.TenDongVat = TenDongVat;
    }

    public String getLoaiChamSoc() {
        return LoaiChamSoc;
    }

    public void setLoaiChamSoc(String LoaiChamSoc) {
        this.LoaiChamSoc = LoaiChamSoc;
    }

    public Date getNgayChamSoc() {
        return NgayChamSoc;
    }

    public void setNgayChamSoc(Date NgayChamSoc) {
        this.NgayChamSoc = NgayChamSoc;
    }

    public String getTenNhanVien() {
        return TenNhanVien;
    }

    public void setTenNhanVien(String TenNhanVien) {
        this.TenNhanVien = TenNhanVien;
    }

    public String getKetQua() {
        return KetQua;
    }

    public void setKetQua(String KetQua) {
        this.KetQua = KetQua;
    }

}
