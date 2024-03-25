/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author datbe
 */
import Model.ModelTaiKhoan;
import View.ViewTaiKhoan;
import com.google.gson.Gson;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class ControllerTaiKhoan {

    private ViewTaiKhoan view;

//    private DBTaiKhoan tkDB = new DBTaiKhoan();
    private ArrayList<ModelTaiKhoan> listTaiKhoan;
    String apiString = Utils.Utility.apiString + "taikhoan/";

    public ControllerTaiKhoan() {
    }

    public ControllerTaiKhoan(ViewTaiKhoan view) {
        this.view = view;
        loadDatatableTaiKhoan();
        addEventsTaiKhoan();
    }

//    public void docDanhSach() {
//        this.listTaiKhoan = tkDB.getDanhSachTaiKhoan();
//    }
//    public ArrayList<ModelTaiKhoan> getDanhSachTaiKhoan() {
//        if (this.listTaiKhoan == null)
//            docDanhSach();
//        return this.listTaiKhoan;
//    }
//    public boolean kiemTraTrungTenDangNhap(String tenDangNhap) {
//        return tkDB.kiemTraTrungTenDangNhap(tenDangNhap);
//    }
    private void addEventsTaiKhoan() {

        view.getTable().getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                int row = view.getTable().getSelectedRow();
                if (row > -1) {

                    view.getTxtID().setText(view.getTable().getValueAt(row, 0) + "");
                    view.getTxtMaNV().setText(view.getTable().getValueAt(row, 1) + "");
                    view.getTxtTaiKhoan().setText(view.getTable().getValueAt(row, 2) + "");
                    view.getTxtMatKhau().setText(view.getTable().getValueAt(row, 3) + "");
                    String quyen = (String) view.getTable().getValueAt(row, 4);

                    for (int i = 0; i < view.getCmbQuyen().getItemCount(); i++) {
                        if (view.getCmbQuyen().getItemAt(i).equals(quyen)) {
                            view.getCmbQuyen().setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        view.getBtnTimKiem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTimKiemTaiKhoan();
            }
        });

        view.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyThemTaiKhoan();
            }
        });

        view.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLySuaTaiKhoan();
            }
        });

        view.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyXoaTaiKhoan();
            }
        });
        view.getBtnHuy().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLybtnHuy();
            }
        });
    }

    private void xuLyXoaTaiKhoan() {
        String id = view.getTxtID().getText();
        boolean flag = xoaTaiKhoan(id);
        if (flag) {
            getDanhSachTaiKhoan();
        }
        loadDatatableTaiKhoan();
    }

    private void xuLySuaTaiKhoan() {

        String id = view.getTxtID().getText();
        String ma = view.getTxtMaNV().getText();
        String tk = view.getTxtTaiKhoan().getText();
        String mk = view.getTxtMatKhau().getText();
        String quyen = String.valueOf(view.getCmbQuyen().getSelectedItem());

        if (suaTaiKhoan(id, ma, tk, mk, quyen)) {
            getDanhSachTaiKhoan();
        }
        loadDatatableTaiKhoan();
    }

    private void xuLyThemTaiKhoan() {
        String ma = view.getTxtMaNV().getText();
        String tk = view.getTxtTaiKhoan().getText();
        String mk = view.getTxtMatKhau().getText();
        String quyen = String.valueOf(view.getCmbQuyen().getSelectedItem());

        if (themTaiKhoan(ma, tk, mk, quyen)) {
            getDanhSachTaiKhoan();
        }
        loadDatatableTaiKhoan();
    }

    private void xuLybtnHuy() {
        view.getTxtID().setText("");
        view.getTxtMaNV().setText("");
        view.getTxtTaiKhoan().setText("");
        view.getTxtMatKhau().setText("");
        view.getCmbQuyen().setSelectedItem("NV");
        view.getTxtTimKiem().setText("");
        loadDatatableTaiKhoan();
    }

    private void xuLyTimKiemTaiKhoan() {
        ArrayList<ModelTaiKhoan> dstk = timTaiKhoan(view.getTxtTimKiem().getText());
        view.getDtmTaiKhoan().setRowCount(0);
        for (ModelTaiKhoan tk : dstk) {
            Vector vec = new Vector();
            vec.add(tk.getID());
            vec.add(tk.getMaNV());
            vec.add(tk.getTaiKhoan());
            vec.add(tk.getMatKhau());
            vec.add(tk.getQuyen());
            view.getDtmTaiKhoan().addRow(vec);
        }
    }

    private void loadDatatableTaiKhoan() {

        view.getDtmTaiKhoan().setRowCount(0);
        DefaultTableModel dtm = view.getDtmTaiKhoan();

        ArrayList<ModelTaiKhoan> dstk = getDanhSachTaiKhoan();

        for (ModelTaiKhoan nv : dstk) {
            Vector vec = new Vector();
            vec.add(nv.getID());
            vec.add(nv.getMaNV());
            vec.add(nv.getTaiKhoan());
            vec.add(nv.getMatKhau());
            vec.add(nv.getQuyen());
            dtm.addRow(vec);
        }
        view.getTable().setModel(dtm);
    }

    public boolean themTaiKhoan(String ma, String tk, String mk, String quyen) {

        ma = ma.trim();
        tk = tk.trim();
        mk = mk.trim();
        quyen = quyen.trim();

        if (tk.equals("")) {
            JOptionPane.showMessageDialog(null, "Tài khoản đang trống");
            return false;
        }
        if (mk.equals("")) {
            JOptionPane.showMessageDialog(null, "Mật khẩu đang trống");
            return false;
        }
//        if (kiemTraTrungTenDangNhap(tk)) {
//            JOptionPane.showMessageDialog(null, "Tài khoản đã tồn tại");
//            return false;
//        }

        ModelTaiKhoan tkdn = new ModelTaiKhoan();
        tkdn.setMaNV(ma);
        tkdn.setTaiKhoan(tk);
        tkdn.setMatKhau(mk);
        tkdn.setQuyen(quyen);

        try {

            Gson gson = new Gson();
            String json = gson.toJson(tkdn);

            URL url = new URL(apiString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            connection.getResponseCode();

//            System.out.println(a);
//            System.out.println("a");
            connection.disconnect();
        } catch (IOException ex) {
//            return false;
            JOptionPane.showMessageDialog(null, "Thêm tài khoản không nổi luôn á");
        }
//        
//        boolean flag = tkDB.themTaiKhoan(ma,tk,mk,quyen);
//        if (!flag) {
//            JOptionPane.showMessageDialog(null, "Thêm tài khoản không thành công");
//        } else {
//            JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công");
//        }
        JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công rồi nè");
        return false;
    }

    public boolean suaTaiKhoan(String id, String ma, String tk, String mk, String quyen) {

        int Id = Integer.parseInt(id);
        ma = ma.trim();
        tk = tk.trim();
        mk = mk.trim();
        quyen = quyen.trim();
        if (tk.equals("")) {
            JOptionPane.showMessageDialog(null, "Tài khoản đang trống");
            return false;
        }
        if (mk.equals("")) {
            JOptionPane.showMessageDialog(null, "Mật khẩu đang trống");
            return false;
        }
        ModelTaiKhoan mdtk = new ModelTaiKhoan();

        mdtk.setID(Id);
        mdtk.setMaNV(ma);
        mdtk.setTaiKhoan(tk);
        mdtk.setMatKhau(mk);
        mdtk.setQuyen(quyen);

        try {
            Gson gson = new Gson();
            String json = gson.toJson(mdtk);

            URL url = new URL(apiString + Id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            connection.getResponseCode();
            connection.disconnect();
        } catch (IOException ex) {
            //            System.out.println("Controller.ControllerNhanVien.xoaNhanVien() " + ex);
            JOptionPane.showMessageDialog(null, "Sửa tài khoản không thành công huhuhu");
        }
        JOptionPane.showMessageDialog(null, "Sửa tài khoản thành công rồi nè");
//        boolean flag = tkDB.suaTaiKhoan(mdtk);
//        if (!flag) {
//            JOptionPane.showMessageDialog(null, "Cập nhật thông tin tài khoản không thành công");
//        } else {
//            JOptionPane.showMessageDialog(null, "Cập nhật thông tin tài khoản thành công");
//        }
        return false;
    }

    public boolean xoaTaiKhoan(String id) {

        int Id = Integer.parseInt(id);

        try {
            URL url = new URL(apiString + Id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            connection.getResponseCode();

            connection.disconnect();
        } catch (IOException ex) {
//            System.out.println("Controller.ControllerDongVat.xoaDongVat() " + ex);
            JOptionPane.showMessageDialog(null, "Xóa tài khoản không được luôn á");
        }
        JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công rồi nè");
        return false;
    }

    public ArrayList<ModelTaiKhoan> timTaiKhoan(String tuKhoa) {
        tuKhoa = tuKhoa.toLowerCase();
        ArrayList<ModelTaiKhoan> dstk = new ArrayList<>();
        for (ModelTaiKhoan tk : listTaiKhoan) {
            if (tk.getMaNV().toLowerCase().contains(tuKhoa) || tk.getTaiKhoan().toLowerCase().contains(tuKhoa) || tk.getQuyen().toLowerCase().contains(tuKhoa)) {
                dstk.add(tk);
            }
        }
        return dstk;
    }

    public ArrayList<ModelTaiKhoan> getDanhSachTaiKhoan() {
        try {
            URL url = new URL(apiString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            StringBuilder responseBuilder;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
            }
            Gson gson = new Gson();
            ModelTaiKhoan[] modelsArray = gson.fromJson(responseBuilder.toString(), ModelTaiKhoan[].class);
            listTaiKhoan = new ArrayList<>(Arrays.asList(modelsArray));
            connection.disconnect();

            return listTaiKhoan;

        } catch (HeadlessException | IOException | NumberFormatException ex) {

        }
        return null;
    }

    public ModelTaiKhoan getTaiKhoan(int id) {
        ModelTaiKhoan modelTaiKhoan = new ModelTaiKhoan();
        try {
            URL url = new URL(apiString + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            StringBuilder responseBuilder;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
            }
            Gson gson = new Gson();
            modelTaiKhoan = gson.fromJson(responseBuilder.toString(), ModelTaiKhoan.class);
            connection.disconnect();
            return modelTaiKhoan;
        } catch (IOException ex) {
//            System.out.println("Controller.ControllerNhanVien.xoaNhanVien() " + ex);
        }
        return modelTaiKhoan;
    }

//    public void kiemTraTrungTenDangNhap(String tk) {
//        ModelTaiKhoan modelTaiKhoan = new ModelTaiKhoan();
//        try {
//            URL url = new URL(apiString + id);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            StringBuilder responseBuilder;
//            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//                responseBuilder = new StringBuilder();
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    responseBuilder.append(inputLine);
//                }
//            }
//            Gson gson = new Gson();
//            modelTaiKhoan = gson.fromJson(responseBuilder.toString(), ModelTaiKhoan.class);
//            connection.disconnect();
////            return modelTaiKhoan;
//        }catch(IOException ex){
////            System.out.println("Controller.ControllerNhanVien.xoaNhanVien() " + ex);
//        }
//    }
//    public int getTrangThai(String maNV) {
//        int ma = Integer.parseInt(maNV);
//        return taiKhoanDAO.getTrangThai(ma);
//    }
}
