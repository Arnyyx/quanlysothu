/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ACER
 */
public class ModelDongVat {

    private int IDDongVat;
    private String TenDongVat;
    private String TenChuong;
    private String LoaiDongVat;
    private int TuoiDongVat;
    private String GioiTinhDongVat;
    private String TrangThaiDongVat;
    private String HinhAnhDongVat;

    public ModelDongVat(String TenDongVat, String TenChuong, String LoaiDongVat, int TuoiDongVat, String GioiTinhDongVat, String TrangThaiDongVat, String HinhAnhDongVat) {
        this.TenDongVat = TenDongVat;
        this.TenChuong = TenChuong;
        this.LoaiDongVat = LoaiDongVat;
        this.TuoiDongVat = TuoiDongVat;
        this.GioiTinhDongVat = GioiTinhDongVat;
        this.TrangThaiDongVat = TrangThaiDongVat;
        this.HinhAnhDongVat = HinhAnhDongVat;
    }

    public ModelDongVat() {
    }

    @Override
    public String toString() {
        return (IDDongVat + TenDongVat + TenChuong + LoaiDongVat + TuoiDongVat + GioiTinhDongVat + TrangThaiDongVat).toLowerCase();
    }

    public String getTenChuong() {
        return TenChuong;
    }

    public void setTenChuong(String TenChuong) {
        this.TenChuong = TenChuong;
    }

    public void setIDDongVat(int IDDongVat) {
        this.IDDongVat = IDDongVat;
    }

    public int getIDDongVat() {
        return IDDongVat;
    }

    public String getTenDongVat() {
        return TenDongVat;
    }

    public void setTenDongVat(String TenDongVat) {
        this.TenDongVat = TenDongVat;
    }

    public String getLoaiDongVat() {
        return LoaiDongVat;
    }

    public void setLoaiDongVat(String LoaiDongVat) {
        this.LoaiDongVat = LoaiDongVat;
    }

    public int getTuoiDongVat() {
        return TuoiDongVat;
    }

    public void setTuoiDongVat(int tuoiDongVat) {
        this.TuoiDongVat = tuoiDongVat;
    }

    public String getGioiTinhDongVat() {
        return GioiTinhDongVat;
    }

    public void setGioiTinhDongVat(String GioiTinhDongVat) {
        this.GioiTinhDongVat = GioiTinhDongVat;
    }

    public String getHinhAnhDongVat() {
        return HinhAnhDongVat;
    }

    public void setHinhAnhDongVat(String HinhAnhDongVat) {
        this.HinhAnhDongVat = HinhAnhDongVat;
    }

    public String getTrangThaiDongVat() {
        return TrangThaiDongVat;
    }

    public void setTrangThaiDongVat(String TrangThaiDongVat) {
        this.TrangThaiDongVat = TrangThaiDongVat;
    }

}
