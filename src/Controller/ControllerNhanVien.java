/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Utils.XuLyFileExcel;
import Model.ModelNhanVien;
import Model.ModelTaiKhoan;
import View.ViewNhanVien;
import com.google.gson.Gson;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
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

public class ControllerNhanVien {

    private ViewNhanVien view;
    private ArrayList<ModelNhanVien> listNhanVien;
    String apiString = Utils.Utility.apiString + "nhanvien/";

    public ControllerNhanVien(ViewNhanVien view) {
        this.view = view;
        addEventsNhanVien();
        loadDatatableNhanVien();
    }

    public ControllerNhanVien() {
    }

    public ArrayList<ModelNhanVien> getDanhSachNhanVien() {
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
            ModelNhanVien[] modelsArray = gson.fromJson(responseBuilder.toString(), ModelNhanVien[].class);
            listNhanVien = new ArrayList<>(Arrays.asList(modelsArray));

            connection.disconnect();
            return listNhanVien;

        } catch (HeadlessException | IOException | NumberFormatException ex) {
//
        }
        return listNhanVien;
    }

    public ModelNhanVien getNhanVien(int id) {
        ModelNhanVien modelNhanVien = new ModelNhanVien();
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
            modelNhanVien = gson.fromJson(responseBuilder.toString(), ModelNhanVien.class);
            connection.disconnect();
            return modelNhanVien;
        } catch (IOException ex) {
//            System.out.println("Controller.ControllerNhanVien.xoaNhanVien() " + ex);
        }
        return modelNhanVien;
    }

    public boolean themNhanVien(String ten, String ma, String cv, String sex, String ns, String sdt, String add, String email, String nvl) {

        ten = ten.trim();
        ma = ma.trim();
        cv = cv.trim();
        sex = sex.trim();
        ns = ns.trim();
        int Sdt = Integer.parseInt(sdt);
        add = add.trim();
        email = email.trim();
        nvl = nvl.trim();

        if (ma.equals("")) {
            JOptionPane.showMessageDialog(null, "Mã nhân viên đang trống");
            return false;
        }
        if (ten.equals("")) {
            JOptionPane.showMessageDialog(null, "Tên nhân viên đang trống");
            return false;
        }

        ModelNhanVien nv = new ModelNhanVien();

        nv.setTenNV(ten);
        nv.setMaNV(ma);
        nv.setChucVu(cv);
        nv.setGioiTinh(sex);
        nv.setNgaySinh(ns);
        nv.setSDT(Sdt);
        nv.setDiaChi(add);
        nv.setEmail(email);
        nv.setNgayVaoLam(nvl);

        if (KiemTraTrung(-1, ma) == true) {
            try {
                Gson gson = new Gson();
                String json = gson.toJson(nv);

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

                connection.disconnect();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Thêm nhân viên không thành công rùi huhuhu");
            }
            JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công rùi nè");
            loadDatatableNhanVien();
            return false;
        }
        return false;
    }

    public boolean suaNhanVien(String id, String ten, String ma, String cv, String sex, String ns, String sdt, String add, String email, String nvl) {

        int Id = Integer.parseInt(id);
        ten = ten.trim();
        ma = ma.trim();
        cv = cv.trim();
        sex = sex.trim();
        ns = ns.trim();
        int Sdt = Integer.parseInt(sdt);
        add = add.trim();
        email = email.trim();
        nvl = nvl.trim();

        if (sdt.length() > 10) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ, cụ thể là quá dài nha");
        }

        if (ten.equals("")) {
            JOptionPane.showMessageDialog(null, "Tên nhân viên đang trống");
            return false;
        }
        if (ma.equals("")) {
            JOptionPane.showMessageDialog(null, "Mã nhân viên đang trống");
            return false;
        }

        ModelNhanVien nv = new ModelNhanVien();
        nv.setID(Id);
        nv.setTenNV(ten);
        nv.setMaNV(ma);
        nv.setChucVu(cv);
        nv.setGioiTinh(sex);
        nv.setNgaySinh(ns);
        nv.setSDT(Sdt);
        nv.setDiaChi(add);
        nv.setEmail(email);
        nv.setNgayVaoLam(nvl);

        if (KiemTraTrung(Id, ma) == true) {
            try {
                Gson gson = new Gson();
                String json = gson.toJson(nv);

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
                JOptionPane.showMessageDialog(null, "Sửa thông tin nhân viên không thành công rùi huhuhu");
            }
            JOptionPane.showMessageDialog(null, "Sửa thông tin nhân viên thành công rùi nè");
            loadDatatableNhanVien();
            return false;
        }
        return false;
    }

    public boolean KiemTraTrung(int Id, String ma) {

        for (ModelNhanVien modelNhanVien : getDanhSachNhanVien()) {
            if (modelNhanVien.getMaNV().equals(ma) && modelNhanVien.getID() == (Id)) {
                return true;
            } else if (modelNhanVien.getMaNV().equals(ma) && modelNhanVien.getID() != (Id)) {
                JOptionPane.showMessageDialog(null, "Mã nhân viên đã tồn tại");
                return false;
            }
        }
        return true;
    }

    public ArrayList<ModelNhanVien> timNhanVien(String tuKhoa) {
        tuKhoa = tuKhoa.toLowerCase();
        ArrayList<ModelNhanVien> dsnv = new ArrayList<>();
        for (ModelNhanVien nv : listNhanVien) {
            if (nv.getTenNV().toLowerCase().contains(tuKhoa) || nv.getMaNV().toLowerCase().contains(tuKhoa) || nv.getChucVu().toLowerCase().contains(tuKhoa)) {
                dsnv.add(nv);
            }
        }
        return dsnv;
    }

    public boolean xoaNhanVien(String id) {
        int Id = Integer.parseInt(id);

        try {
            URL url = new URL(apiString + Id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            connection.getResponseCode();
            connection.disconnect();
        } catch (IOException ex) {
//            System.out.println("Controller.ControllerDongVat.xoaDongVat() " + ex);
            JOptionPane.showMessageDialog(null, "Xóa tài khoản không thành công rùi huhuhu");
        }
        JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công rùi nè");
        loadDatatableNhanVien();
        return false;
    }

    private void addEventsNhanVien() {

        view.getTable().getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                int row = view.getTable().getSelectedRow();
                if (row > -1) {

                    view.getTxtID().setText(view.getTable().getValueAt(row, 0) + "");
                    view.getTxtTenNV().setText(view.getTable().getValueAt(row, 1) + "");
                    view.getTxtMaNV().setText(view.getTable().getValueAt(row, 2) + "");
                    String cv = (String) view.getTable().getValueAt(row, 3);
                    String gioitinh = (String) view.getTable().getValueAt(row, 4);
                    view.getTxtNgaySinh().setText(view.getTable().getValueAt(row, 5) + "");
                    view.getTxtSDT().setText(view.getTable().getValueAt(row, 6) + "");
                    view.getTxtDiaChi().setText(view.getTable().getValueAt(row, 7) + "");
                    view.getTxtEmail().setText(view.getTable().getValueAt(row, 8) + "");
                    view.getTxtNgayVaoLam().setText(view.getTable().getValueAt(row, 9) + "");

                    for (int i = 0; i < view.getCmbChucVu().getItemCount(); i++) {
                        if (view.getCmbChucVu().getItemAt(i).equals(cv)) {
                            view.getCmbChucVu().setSelectedIndex(i);
                            break;
                        }
                    }

                    for (int i = 0; i < view.getCmbGioiTinh().getItemCount(); i++) {
                        if (view.getCmbGioiTinh().getItemAt(i).equals(gioitinh)) {
                            view.getCmbGioiTinh().setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        view.getBtnTimKiem().addActionListener((ActionEvent e) -> {
            xuLyTimKiemNhanVien();
        });

        view.getBtnThem().addActionListener((ActionEvent e) -> {
            xuLyThemNhanVien();
        });

        view.getBtnSua().addActionListener((ActionEvent e) -> {
            xuLySuaNhanVien();
        });

        view.getBtnXoa().addActionListener((ActionEvent e) -> {
            xuLyXoaNhanVien();
        });
        view.getBtnHuy().addActionListener((ActionEvent e) -> {
            xuLybtnHuy();
        });

        view.getBtnXuatExcel().addActionListener((ActionEvent e) -> {
            xuLyXuatExcel();
        });

        view.getBtnNhapExcel().addActionListener((ActionEvent e) -> {
            xuLyNhapExcel();
        });
    }

    private void xuLyTimKiemNhanVien() {
        ArrayList<ModelNhanVien> dsnv = timNhanVien(view.getTxtTimKiem().getText());
        view.getDtmNhanVien().setRowCount(0);
        for (ModelNhanVien nv : dsnv) {
            Vector vec = new Vector();
            vec.add(nv.getID());
            vec.add(nv.getTenNV());
            vec.add(nv.getMaNV());
            vec.add(nv.getChucVu());
            vec.add(nv.getGioiTinh());
            vec.add(nv.getNgaySinh());
            vec.add(nv.getSDT());
            vec.add(nv.getDiaChi());
            vec.add(nv.getEmail());
            vec.add(nv.getNgayVaoLam());
            view.getDtmNhanVien().addRow(vec);
        }
    }

    private void xuLyThemNhanVien() {
        String ten = view.getTxtTenNV().getText();
        String ma = view.getTxtMaNV().getText();
        String cv = String.valueOf(view.getCmbChucVu().getSelectedItem());
        String sex = String.valueOf(view.getCmbGioiTinh().getSelectedItem());
        String ns = view.getTxtNgaySinh().getText();
        String sdt = view.getTxtSDT().getText();
        String add = view.getTxtDiaChi().getText();
        String email = view.getTxtEmail().getText();
        String nvl = view.getTxtNgayVaoLam().getText();

        if (themNhanVien(ten, ma, cv, sex, ns, sdt, add, email, nvl)) {
            getDanhSachNhanVien();
        }
        loadDatatableNhanVien();
    }

    private void xuLySuaNhanVien() {
        String id = view.getTxtID().getText();
        String ten = view.getTxtTenNV().getText();
        String ma = view.getTxtMaNV().getText();
        String cv = String.valueOf(view.getCmbChucVu().getSelectedItem());
        String sex = String.valueOf(view.getCmbGioiTinh().getSelectedItem());
        String ns = view.getTxtNgaySinh().getText();
        String sdt = view.getTxtSDT().getText();
        String add = view.getTxtDiaChi().getText();
        String email = view.getTxtEmail().getText();
        String nvl = view.getTxtNgayVaoLam().getText();

        if (suaNhanVien(id, ten, ma, cv, sex, ns, sdt, add, email, nvl)) {
            getDanhSachNhanVien();
        }
        loadDatatableNhanVien();
    }

    private void xuLyXoaNhanVien() {
        String id = view.getTxtID().getText();
        boolean flag = xoaNhanVien(id);
        if (flag) {
            getDanhSachNhanVien();
        }
        loadDatatableNhanVien();
    }

    private void xuLybtnHuy() {
        view.getTxtID().setText("");
        view.getTxtTenNV().setText("");
        view.getTxtMaNV().setText("");
        view.getCmbChucVu().setSelectedItem("Nhân viên");
        view.getCmbGioiTinh().setSelectedItem("Nam");
        view.getTxtNgaySinh().setText("");
        view.getTxtSDT().setText("");
        view.getTxtDiaChi().setText("");
        view.getTxtEmail().setText("");
        view.getTxtNgayVaoLam().setText("");
        view.getTxtTimKiem().setText("");
        loadDatatableNhanVien();
    }

    private void xuLyXuatExcel() {
        XuLyFileExcel xuatExcel = new XuLyFileExcel();
        xuatExcel.xuatExcel(view.getTable());
    }

    private void xuLyNhapExcel() {

        XuLyFileExcel nhapExcel = new XuLyFileExcel();
        nhapExcel.nhapExcel(view.getTable());

        int row = view.getTable().getRowCount();
        for (int i = 0; i < row; i++) {
        }
    }

    public void loadDatatableNhanVien() {

        view.getDtmNhanVien().setRowCount(0);
        DefaultTableModel dtm = view.getDtmNhanVien();

        ArrayList<ModelNhanVien> dsnv = getDanhSachNhanVien();

        for (ModelNhanVien nv : dsnv) {
            Vector vec = new Vector();
            vec.add(nv.getID());
            vec.add(nv.getTenNV());
            vec.add(nv.getMaNV());
            vec.add(nv.getChucVu());
            vec.add(nv.getGioiTinh());
            vec.add(nv.getNgaySinh());
            vec.add(nv.getSDT());
            vec.add(nv.getDiaChi());
            vec.add(nv.getEmail());
            vec.add(nv.getNgayVaoLam());

            dtm.addRow(vec);
        }
        view.getTable().setModel(dtm);
    }
}
