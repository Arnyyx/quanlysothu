/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.ModelChamSoc;
import Model.ModelDongVat;
import Utils.XuLyFileExcel;
import View.ViewChamSoc;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class ControllerChamSoc {

    private ViewChamSoc view;
    private ModelChamSoc chamsoc = new ModelChamSoc();
    String apiString = Utils.Utility.apiString + "chamsoc/";
    SimpleDateFormat sdf = Utils.Utility.sdf;

    public ControllerChamSoc() {
    }

    public ControllerChamSoc(ViewChamSoc viewChamSoc) {
        view = viewChamSoc;
        fillData("");

        view.getBtnAdd().addActionListener((e) -> {
            chamsoc = getChamSocFromTextField();
            if (chamsoc == null) {
                return;
            }

            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn thêm bản ghi mới? ", "Thêm bản ghi mới?", JOptionPane.WARNING_MESSAGE);
            if (dialogResult == JOptionPane.YES_OPTION) {
                themChamSoc(chamsoc);
            }

            fillData(view.getTfTimKiem().getText());
            reset();
        });
        view.getBtnDelete().addActionListener((e) -> {
            xoaChamSoc();
        });
        view.getTable().addMouseListener(xuLyCLickTableDongVat());
        view.getBtnLoad().addActionListener((e) -> {
            loadChamSoc();
        });

        view.getBtnSave().addActionListener((e) -> {
            if (view.getTfID().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Hãy chọn một bản ghi");
                return;
            }
            luuChamSoc();
        });
        view.getBtnSearch().addActionListener((e) -> {
            timKiemChamSoc();
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

    private MouseListener xuLyCLickTableDongVat() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = view.getTable();
                int row = table.getSelectedRow();
                if (row > -1) {

                    getChamSoc(Integer.parseInt(table.getValueAt(row, 0).toString()), new OnGetChamSocListener() {
                        @Override
                        public void onSuccess(ModelChamSoc chamSoc) {
                            view.getTfID().setText(chamSoc.getIDChamSoc() + "");
                            view.getTfTenDongVat().setSelectedItem(chamSoc.getTenDongVat());
                            view.getTfLoaiChamSoc().setSelectedItem(chamSoc.getLoaiChamSoc());
                            view.getTfNgayChamSoc().setDate(chamSoc.getNgayChamSoc());
                            view.getTfTenNhanVien().setText(chamSoc.getTenNhanVien());
                            view.getTfKetQua().setText(chamSoc.getKetQua());
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

        getListChamSoc(new OnGetListChamSocListener() {
            @Override
            public void onSuccess(ArrayList<ModelChamSoc> chamSocs) {

                for (ModelChamSoc chamSoc : chamSocs) {
                    try {
                        String apiUrl = apiString + chamSoc.getIDChamSoc();
                        URL url = new URL(apiUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("DELETE");

                        connection.getResponseCode();

                        fillData(view.getTfTimKiem().getText());
                        connection.disconnect();
                    } catch (IOException ex) {
                        System.out.println(".onSuccess()" + ex);
                    }
                }
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFailure() {
            }
        });

        JTable table = view.getTable();
        new XuLyFileExcel().nhapExcel(table);

        int row = view.getTable().getRowCount();
        for (int i = 0; i < row; i++) {
            String ten = table.getValueAt(i, 1).toString();
            String loaiChamSoc = table.getValueAt(i, 2).toString();
            String ngayChamSoc = table.getValueAt(i, 3).toString();
            String tenNhanVien = table.getValueAt(i, 4).toString();
            String ketQua = table.getValueAt(i, 5).toString();

            Date date = null;
            try {
                date = sdf.parse(ngayChamSoc);
            } catch (ParseException e) {
                System.out.println("Controller.ControllerChamSoc.nhapExcel()" + e);
            }

            themChamSoc(new ModelChamSoc(ten, loaiChamSoc, date, tenNhanVien, ketQua));
        }
        fillData(view.getTfTimKiem().getText());
        reset();
    }

    public void loadChamSoc() {
        fillData("");
        reset();
        view.getTfTimKiem().setText("");
    }

    public void xoaChamSoc() {
        if (multirowSelected().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy chọn một bản ghi");
            return;
        }

        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn xoá (các) bản ghi đã chọn?", "Xoá bản ghi?", JOptionPane.WARNING_MESSAGE);
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
                    System.out.println("Controller.ControllerChamSoc.xoaChamSoc()" + ex);
                }
            }
        }
        reset();
    }

    public void luuChamSoc() {
        try {
            chamsoc = getChamSocFromTextField();
            if (chamsoc == null) {
                return;
            }

            int id = Integer.parseInt(view.getTfID().getText());

            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn sửa bản ghi có ID = " + id + "?", "Sửa bản ghi?", JOptionPane.WARNING_MESSAGE);
            if (dialogResult == JOptionPane.YES_OPTION) {
                chamsoc.setIDChamSoc(id);
                Gson gson = new Gson();
                String json = gson.toJson(chamsoc);

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

                connection.getResponseCode();

                fillData(view.getTfTimKiem().getText());
                connection.disconnect();
            }
        } catch (HeadlessException | IOException | NumberFormatException ex) {
            System.out.println("Controller.ControllerChamSoc.luuDongVat()" + ex);
        }
    }

    public void timKiemChamSoc() {
        fillData(view.getTfTimKiem().getText().toLowerCase());
        reset();
    }

    public void reset() {
        try {
            view.getTfID().setText("");
            view.getTfTenNhanVien().setText("");
            view.getTfKetQua().setText("");

        } catch (Exception e) {
            System.out.println("Controller.ControllerChamSoc.reset()" + e);
        }

    }

    public ModelChamSoc getChamSocFromTextField() {
        String ten = view.getTfTenDongVat().getSelectedItem().toString().trim();
        String loai = view.getTfLoaiChamSoc().getSelectedItem().toString().trim();
        Date ngay = view.getTfNgayChamSoc().getDate();
        String tenNhanVien = view.getTfTenNhanVien().getText().trim();
        String ketQua = view.getTfKetQua().getText().trim();

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập tên động vật");
            return null;
        } else if (loai.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập loại chăm sóc");
            return null;
        } else if (ngay.toString().equals("")) {
            JOptionPane.showMessageDialog(null, "Hãy chọn ngày chăm sóc");
            return null;
        } else if (tenNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập tên nhân viên");
            return null;
        } else if (ketQua.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập kết quả");
            return null;
        }

        return new ModelChamSoc(ten, loai, ngay, tenNhanVien, ketQua);
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
        ArrayList<String> loaiListRaw = new ArrayList<>();
        ControllerDongVat controllerDongVat = new ControllerDongVat();

        view.getTfTenDongVat().removeAllItems();
        view.getTfLoaiChamSoc().removeAllItems();

        tableModel.setRowCount(0);

        controllerDongVat.getListDongVat(new ControllerDongVat.OnGetListDongVatListener() {
            @Override
            public void onSuccess(ArrayList<ModelDongVat> dongVats) {
                for (ModelDongVat dongVat : dongVats) {
                    view.getTfTenDongVat().addItem(dongVat.getTenDongVat());
                }
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFailure() {
            }
        });

        getListChamSoc(new OnGetListChamSocListener() {
            @Override
            public void onSuccess(ArrayList<ModelChamSoc> chamSocs) {
                for (ModelChamSoc modelChamSoc : chamSocs) {
                    loaiListRaw.add(modelChamSoc.getLoaiChamSoc());
                    if (modelChamSoc.toString().contains(searchString)) {
                        tableModel.addRow(new Object[]{
                            modelChamSoc.getIDChamSoc(),
                            modelChamSoc.getTenDongVat(),
                            modelChamSoc.getLoaiChamSoc(),
                            sdf.format(modelChamSoc.getNgayChamSoc()),
                            modelChamSoc.getTenNhanVien(),
                            modelChamSoc.getKetQua()
                        });
                    }
                }

                view.setLabelSLChamSoc("Tổng số bản ghi: " + chamSocs.size());
                table.setModel(tableModel);

                HashSet<String> setWithoutDuplicates = new HashSet<>(loaiListRaw);
                ArrayList<String> loaiList = new ArrayList<>(setWithoutDuplicates);
                Collections.sort(loaiList);

                for (String loai : loaiList) {
                    view.getTfLoaiChamSoc().addItem(loai);
                }
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFailure() {
            }
        });
        view.getTfNgayChamSoc().setDate(Date.from(Instant.now()));
    }

    public void themChamSoc(ModelChamSoc chamSoc) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(chamSoc);

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
            System.out.println("Controller.ControllerChamSoc.themDongVat()" + ex);
        }
    }

    public void getChamSoc(int id, final OnGetChamSocListener listener) {
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
            chamsoc = gson.fromJson(responseBuilder.toString(), ModelChamSoc.class);

            connection.getResponseCode();
            connection.disconnect();
            listener.onSuccess(chamsoc);
        } catch (IOException ex) {
            System.out.println("Controller.ControllerDongVat.getDongVat()" + ex);
            listener.onFailure();
        }
    }

    public void getListChamSoc(final OnGetListChamSocListener listener) {
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
            ModelChamSoc[] modelsArray = gson.fromJson(responseBuilder.toString(), ModelChamSoc[].class);
            ArrayList<ModelChamSoc> chamSocs = new ArrayList<>(Arrays.asList(modelsArray));

            connection.disconnect();
            listener.onSuccess(chamSocs);
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Controller.ControllerChamSoc.getListChamSoc()" + e);
            JOptionPane.showMessageDialog(view, "Không thể kế nối đến máy chủ");
            listener.onFailure();
        }
    }

    public interface OnGetListChamSocListener {

        void onSuccess(ArrayList<ModelChamSoc> chamSocs);

        void onStart();

        void onFailure();
    }

    public interface OnGetChamSocListener {

        void onSuccess(ModelChamSoc chamSocs);

        void onStart();

        void onFailure();
    }

}
