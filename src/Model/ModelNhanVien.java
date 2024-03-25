/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
/**
 *
 * @author datbe
 */
public class ModelNhanVien {
    
    private int ID;
    private String TenNV,MaNV,ChucVu,GioiTinh,NgaySinh;
    private int SDT;
    private String DiaChi,Email,NgayVaoLam;
    
    public ModelNhanVien() {
        
    }

    public ModelNhanVien(int ID, String TenNV, String MaNV, String ChucVu, String GioiTinh, String NgaySinh, int SDT, String DiaChi, String Email, String NgayVaoLam) {
        this.ID = ID;
        this.TenNV = TenNV;
        this.MaNV = MaNV;
        this.ChucVu = ChucVu;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.SDT = SDT;
        this.DiaChi = DiaChi;
        this.Email = Email;
        this.NgayVaoLam = NgayVaoLam;
    }

    public int getID() {
        return ID;
    }

    public String getTenNV() {
        return TenNV;
    }

    public String getMaNV() {
        return MaNV;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public int getSDT() {
        return SDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getEmail() {
        return Email;
    }

    public String getNgayVaoLam() {
        return NgayVaoLam;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setNgayVaoLam(String NgayVaoLam) {
        this.NgayVaoLam = NgayVaoLam;
    }

}
