package Model;

public class ModelTaiKhoan {

    private int ID;
    private String MaNV;
    private String TaiKhoan;
    private String MatKhau;
    private String Quyen;

    public ModelTaiKhoan() {
        
    }

    public ModelTaiKhoan(int ID, String MaNV, String TaiKhoan, String MatKhau, String Quyen) {
        this.ID = ID;
        this.MaNV = MaNV;
        this.TaiKhoan = TaiKhoan;
        this.MatKhau = MatKhau;
        this.Quyen = Quyen;
    }

    public int getID() {
        return ID;
    }

    public String getMaNV() {
        return MaNV;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public String getQuyen() {
        return Quyen;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public void setTaiKhoan(String TaiKhoan) {
        this.TaiKhoan = TaiKhoan;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public void setQuyen(String Quyen) {
        this.Quyen = Quyen;
    }

    
}
