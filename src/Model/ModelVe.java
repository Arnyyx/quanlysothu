/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class ModelVe {

    private int IdVe;
    private int IDNhanVien;
    private String TenVe;
    private String LoaiVe;
    private int GiaVe;
    private int SoLuong;

    public ModelVe() {
    }

    public ModelVe(int IDNhanVien, String TenVe, String LoaiVe, int GiaVe, int SoLuong) {
        this.IDNhanVien = IDNhanVien;
        this.TenVe = TenVe;
        this.LoaiVe = LoaiVe;
        this.GiaVe = GiaVe;
        this.SoLuong = SoLuong;
    }

    @Override
    public String toString() {
        return (IdVe + IDNhanVien + TenVe + LoaiVe + GiaVe + SoLuong).toLowerCase();
    }

    public int getIdVe() {
        return IdVe;
    }

    public void setIdVe(int IdVe) {
        this.IdVe = IdVe;
    }

    public String getTenVe() {
        return TenVe;
    }

    public void setTenVe(String TenVe) {
        this.TenVe = TenVe;
    }

    public String getLoaiVe() {
        return LoaiVe;
    }

    public void setLoaiVe(String LoaiVe) {
        this.LoaiVe = LoaiVe;
    }

    public int getGiaVe() {
        return GiaVe;
    }

    public void setGiaVe(int GiaVe) {
        this.GiaVe = GiaVe;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public int getIDNhanVien() {
        return IDNhanVien;
    }

    public void setIDNhanVien(int IDNhanVien) {
        this.IDNhanVien = IDNhanVien;
    }

}
