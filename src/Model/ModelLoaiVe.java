/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ducanh
 */
public class ModelLoaiVe {
    
    private int IDLoaiVe;
    private String TenLoaiVe;
    private String KhuVuc;

    public ModelLoaiVe(String TenLoaiVe, String KhuVuc) {
        this.TenLoaiVe = TenLoaiVe;
        this.KhuVuc = KhuVuc;
    }

    public ModelLoaiVe() {
    }

    public int getIDLoaiVe() {
        return IDLoaiVe;
    }

    public void setIDLoaiVe(int IDLoaiVe) {
        this.IDLoaiVe = IDLoaiVe;
    }

    public String getTenLoaiVe() {
        return TenLoaiVe;
    }

    public void setTenLoaiVe(String TenLoaiVe) {
        this.TenLoaiVe = TenLoaiVe;
    }

    public String getKhuVuc() {
        return KhuVuc;
    }

    public void setKhuVuc(String KhuVuc) {
        this.KhuVuc = KhuVuc;
    }
}
