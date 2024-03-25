package Controller;

import Model.ModelTaiKhoan;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ControllerDangNhap {

//    public static ModelTaiKhoan taiKhoanLogin = null;
    private final ControllerTaiKhoan ctk = new ControllerTaiKhoan();

    public ModelTaiKhoan getTaiKhoanDangNhap(String user, String password) {

        if (user.equals("")) {
            JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu đang trống");
            return null;
        }
        if (password.equals("")) {
            JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu đang trống");
            return null;
        }

        ArrayList<ModelTaiKhoan> listTK = ctk.getDanhSachTaiKhoan();
        for (ModelTaiKhoan modelTaiKhoan : listTK) {
            if (modelTaiKhoan.getTaiKhoan().equals(user) && modelTaiKhoan.getMatKhau().equals(password)) {
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công rồi nè");
                return modelTaiKhoan;
            }
        }
        JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không chính xác");
        return null;
    }
}
