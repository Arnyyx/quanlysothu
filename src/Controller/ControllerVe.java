/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.ModelVe;
import Utils.IntegerDocumentFilter;
import Utils.MyFileChooser;
import Utils.XuLyFileExcel;
import View.ViewVe;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class ControllerVe {

    private ViewVe view;
    private ModelVe modelVe = new ModelVe();
//    String apiString = "https://quan-ly-tu.onrender.com/vethamquan/";
    String apiString = "http://localhost:8000/vethamquan/";

    public ControllerVe() {
    }

    public ControllerVe(ViewVe viewVe) {
        view = viewVe;
        fillData("");

        view.getBtnAdd().addActionListener((e) -> {
            modelVe = getVeFromTextField();
            if (modelVe == null) {
                return;
            }

            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn thêm vé mới? ", "Thêm vé mới?", JOptionPane.WARNING_MESSAGE);
            if (dialogResult == JOptionPane.YES_OPTION) {
                themVe(modelVe);
            }

            fillData(view.getTfTimKiem().getText());
            reset();
        });
        view.getBtnDelete().addActionListener((e) -> {
            xoaVe();
        });
        view.getTable().addMouseListener(xuLyCLickTableVe());
        view.getBtnLoad().addActionListener((e) -> {
            loadVe();
        });

        view.getBtnSave().addActionListener((e) -> {
            luuVe();
        });
        view.getBtnTimKiem().addActionListener((e) -> {
            timKiemVe();
        });
        view.getBtnXuat().addActionListener((e) -> {
            new XuLyFileExcel().xuatExcel(view.getTable());
            fillData(view.getTfTimKiem().getText());
            reset();
        });
        view.getBtnNhap().addActionListener((e) -> {
            nhapExcel();
        });
    }

    private MouseListener xuLyCLickTableVe() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = view.getTable();
                int row = table.getSelectedRow();
                if (row > -1) {

                    getVe(Integer.parseInt(table.getValueAt(row, 0).toString()), new OnGetVeListener() {
                        @Override
                        public void onSuccess(ModelVe modelVe) {
                            view.getTfID().setText(modelVe.getIdVe() + "");
                            view.getTfIDNhanVien().setSelectedItem(modelVe.getIDNhanVien());
                            view.getTfTen().setText(modelVe.getTenVe());
                            view.getTfLoai().setSelectedItem(modelVe.getLoaiVe());
                            view.getTfGia().setText(modelVe.getGiaVe() + "");
                            view.getTfSoLuong().setText(modelVe.getSoLuong() + "");
                        }

                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onFailure() {
                        }

                    });

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
    }

    public void nhapExcel() throws NumberFormatException {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Dữ liệu cũ sẽ bị xoá, tiếp tục?", "Nhập dữ liệu", JOptionPane.WARNING_MESSAGE);
        if (dialogResult != JOptionPane.YES_OPTION) {
            return;
        }

        JTable table = view.getTable();
        new XuLyFileExcel().nhapExcel(table);

        int row = view.getTable().getRowCount();
        for (int i = 0; i < row; i++) {
            int idNhanVien = Integer.parseInt(table.getValueAt(i, 1).toString());
            String tenVe = table.getValueAt(i, 2).toString();
            String loaiVe = table.getValueAt(i, 3).toString();
            int giaVe = Integer.parseInt(table.getValueAt(i, 4).toString());
            int soLuong = Integer.parseInt(table.getValueAt(i, 5).toString());

            themVe(new ModelVe(idNhanVien, tenVe, loaiVe, giaVe, soLuong));
        }
        fillData(view.getTfTimKiem().getText());
        reset();
    }

    public void loadVe() {
        fillData("");
        reset();
        view.getTfTimKiem().setText("");
    }

    public void xoaVe() {
        if (multirowSelected().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy chọn một vé");
            return;
        }

        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn xoá (các) vé đã chọn?", "Xoá vé?", JOptionPane.WARNING_MESSAGE);
        if (dialogResult == JOptionPane.YES_OPTION) {
            for (Integer id : multirowSelected()) {
                try {
                    String apiUrl = apiString + id;

                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("DELETE");

                    connection.getResponseCode();

                    fillData(view.getTfTimKiem().getText());
                    connection.disconnect();
                } catch (IOException ex) {
                    System.out.println("Controller.ControllerVe.xoaVe()" + ex);
                }

            }
        }
        reset();
    }

    public void luuVe() {
        try {
            modelVe = getVeFromTextField();
            if (modelVe == null) {
                return;
            }

            int id = Integer.parseInt(view.getTfID().getText());

            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn sửa vé có ID = " + id + "?", "Xoá vé?", JOptionPane.WARNING_MESSAGE);
            if (dialogResult == JOptionPane.YES_OPTION) {
                modelVe.setIdVe(id);
                Gson gson = new Gson();
                String json = gson.toJson(modelVe);

                String apiUrl = apiString + id;
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = json.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                fillData(view.getTfTimKiem().getText());

                connection.getResponseCode();
                connection.disconnect();
            }
        } catch (HeadlessException | IOException | NumberFormatException ex) {
            System.out.println("Controller.ControllerVe.luuVe()" + ex);
        }
    }

    public void timKiemVe() {
        fillData(view.getTfTimKiem().getText().toLowerCase());
        reset();
    }

    public void reset() {
        try {
            view.getTfID().setText("");
            view.getTfIDNhanVien().setSelectedItem("");
            view.getTfTen().setText("");
            view.getTfLoai().setSelectedItem("Người lớn");
            IntegerDocumentFilter.clearTextField(view.getTfGia());
            IntegerDocumentFilter.clearTextField(view.getTfSoLuong());

        } catch (Exception e) {
            System.out.println("Controller.ControllerVe.reset()" + e);
        }

    }

    public ModelVe getVeFromTextField() {
        String idNhanVienString = view.getTfIDNhanVien().getSelectedItem().toString().trim();
        String tenVe = view.getTfTen().getText().trim();
        String loaiVe = view.getTfLoai().getSelectedItem().toString().trim();
        String giaVeString = view.getTfGia().getText().trim();
        String soLuongString = view.getTfSoLuong().getText().trim();

        if (idNhanVienString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập ID Nhân viên");
            return null;
        }
        if (tenVe.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập tên vé");
            return null;
        } else if (loaiVe.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập chọn loại vé");
            return null;
        } else if (giaVeString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập giá vé");
            return null;
        } else if (soLuongString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập số lượng");
            return null;
        }

        int IDNhanVien = Integer.parseInt(idNhanVienString);
        int giaVe = Integer.parseInt(giaVeString);
        int soLuong = Integer.parseInt(soLuongString);

        return new ModelVe(IDNhanVien, tenVe, loaiVe, giaVe, soLuong);

    }

    public List<Integer> multirowSelected() {
        List<Integer> selectedIDs = new ArrayList<>();
        int[] selectedRows = view.getTable().getSelectedRows();
        for (int row : selectedRows) {
            Integer id = (Integer) view.getTable().getValueAt(row, 0);
            selectedIDs.add(id);
        }
        return selectedIDs;
    }

    private void fillData(String searchString) {
        DefaultTableModel tableModel = view.getTableModel();
        JTable table = view.getTable();
        tableModel.setRowCount(0);

        getListVe(new OnGetListVeListener() {
            @Override
            public void onSuccess(ArrayList<ModelVe> modelVes) {
                if (!searchString.equals("")) {
                    for (ModelVe modelVe : modelVes) {
                        if (modelVe.toString().contains(searchString)) {
                            tableModel.addRow(new Object[]{
                                modelVe.getIdVe(),
                                modelVe.getIDNhanVien(),
                                modelVe.getTenVe(),
                                modelVe.getLoaiVe(),
                                modelVe.getGiaVe(),
                                modelVe.getSoLuong()
                            });
                        }
                    }
                } else {
                    for (ModelVe modelVe : modelVes) {
                        tableModel.addRow(new Object[]{
                            modelVe.getIdVe(),
                            modelVe.getIDNhanVien(),
                            modelVe.getTenVe(),
                            modelVe.getLoaiVe(),
                            modelVe.getGiaVe(),
                            modelVe.getSoLuong()
                        });
                    }
                }

                view.setLabelSLVe("Tổng số vé: " + modelVes.size());
                table.setModel(tableModel);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFailure() {
            }
        });

    }

    public void themVe(ModelVe modelVe) {
        try {

            Gson gson = new Gson();
            String json = gson.toJson(modelVe);

            String apiUrl = apiString;
            URL url = new URL(apiUrl);
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
            System.out.println("Controller.ControllerVe.themVe()" + ex);
        }
    }

    public void getVe(int id, final OnGetVeListener listener) {
        try {
            listener.onStart();
            String apiUrl = apiString + id;

            URL url = new URL(apiUrl);
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
            modelVe = gson.fromJson(responseBuilder.toString(), ModelVe.class);

            connection.getResponseCode();
            connection.disconnect();
            listener.onSuccess(modelVe);
        } catch (IOException ex) {
            System.out.println("Controller.ControllerVe.getVe()" + ex);
            listener.onFailure();
        }
    }

    public void getListVe(final OnGetListVeListener listener) {
        try {
            listener.onStart();
            String apiUrl = apiString;
            URL url = new URL(apiUrl);
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
            ModelVe[] modelsArray = gson.fromJson(responseBuilder.toString(), ModelVe[].class);

            ArrayList<ModelVe> dongvats = new ArrayList<>(Arrays.asList(modelsArray));

            connection.disconnect();
            listener.onSuccess(dongvats);
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Controller.ControllerVe.getListVe()" + e);
            JOptionPane.showMessageDialog(view, "Không thể kế nối đến máy chủ");
            listener.onFailure();
        }
    }

    public interface OnGetListVeListener {

        void onSuccess(ArrayList<ModelVe> modelVes);

        void onStart();

        void onFailure();
    }

    public interface OnGetVeListener {

        void onSuccess(ModelVe modelVe);

        void onStart();

        void onFailure();
    }
}
