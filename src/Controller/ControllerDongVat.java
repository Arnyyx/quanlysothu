/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.ModelDongVat;
import Utils.IntegerDocumentFilter;
import Utils.MyFileChooser;
import Utils.XuLyFileExcel;
import View.ViewDongVat;
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
public class ControllerDongVat {

    private ViewDongVat view;
    private ModelDongVat dongVat = new ModelDongVat();
    private File fileAnhDongVat = null;
//    String apiString = "https://quan-ly-tu.onrender.com/dongVat/";
    String apiString = "http://localhost:8000/dongvat/";

    public ControllerDongVat() {
    }

    public ControllerDongVat(ViewDongVat viewDongVat) {
        view = viewDongVat;
        fillData("");

        view.getBtnAdd().addActionListener((e) -> {
            dongVat = getDongVatFromTextField();
            if (dongVat == null) {
                return;
            }
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn thêm động vật mới? ", "Thêm động vật mới?", JOptionPane.WARNING_MESSAGE);
            if (dialogResult == JOptionPane.YES_OPTION) {
                themDongVat(dongVat);
            }

            luuFileAnh();
            fillData(view.getTfTimKiem().getText());
            reset();
        });
        view.getBtnDelete().addActionListener((e) -> {
            xoaDongVat();
        });
        view.getTable().addMouseListener(xuLyCLickTableDongVat());
        view.getBtnLoad().addActionListener((e) -> {
            loadDongVat();
        });
        view.getBtnImageChange().addActionListener((e) -> {
            themAnhDongVat();
        });
        view.getBtnSave().addActionListener((e) -> {
            luuDongVat();
        });
        view.getBtnTimKiem().addActionListener((e) -> {
            timKiemDongVat();
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

                    getDongVat(Integer.parseInt(table.getValueAt(row, 0).toString()), new OnGetDongVatListener() {
                        @Override
                        public void onSuccess(ModelDongVat dongVat) {
                            view.getTfID().setText(dongVat.getIDDongVat() + "");
                            view.getTfTen().setText(dongVat.getTenDongVat());
                            view.getTfLoai().setSelectedItem(dongVat.getLoaiDongVat());
                            view.getTfTuoi().setText(dongVat.getTuoiDongVat() + "");
                            view.getTfGioiTinh().setText(dongVat.getGioiTinhDongVat());
                            view.getTfTrangThai().setSelectedItem(dongVat.getTrangThaiDongVat());
                            view.getLbAnhDongVat().setIcon(getAnhDongVat("image/DongVat/" + dongVat.getHinhAnhDongVat()));
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
            String ten = table.getValueAt(i, 1).toString();
            String loai = table.getValueAt(i, 2).toString();
            int tuoi = Integer.parseInt(table.getValueAt(i, 3).toString());
            String gioiTinh = table.getValueAt(i, 4).toString();
            String trangThai = table.getValueAt(i, 5).toString();
            String hinhAnh = table.getValueAt(i, 6).toString();

            themDongVat(new ModelDongVat(ten, loai, tuoi, gioiTinh, trangThai, hinhAnh));
        }
        fillData(view.getTfTimKiem().getText());
        reset();
    }

    public void themAnhDongVat() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            System.out.println("controller/ImageChangeButtonListener" + ex);
        }
        JFileChooser fileChooser = new MyFileChooser("image/DongVat/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Tệp hình ảnh", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(view);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileAnhDongVat = fileChooser.getSelectedFile();
            view.getLbAnhDongVat().setIcon(getAnhDongVat(fileAnhDongVat.getPath()));
        }
    }

    public void loadDongVat() {
        fillData("");
        reset();
        view.getTfTimKiem().setText("");
    }

    public void xoaDongVat() {
        if (multirowSelected().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy chọn một loài động vật");
            return;
        }

        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn xoá (các) động vật đã chọn?", "Xoá động vật?", JOptionPane.WARNING_MESSAGE);
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
                    System.out.println("Controller.ControllerDongVat.xoaDongVat() " + ex);
                }

            }
        }
        reset();
    }

    public void luuDongVat() {
        try {
            dongVat = getDongVatFromTextField();
            if (dongVat == null) {
                return;
            }

            int id = Integer.parseInt(view.getTfID().getText());

            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn sửa động vật có ID = " + id + "?", "Xoá động vật?", JOptionPane.WARNING_MESSAGE);
            if (dialogResult == JOptionPane.YES_OPTION) {
                dongVat.setIDDongVat(id);
                Gson gson = new Gson();
                String json = gson.toJson(dongVat);

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

                dongVat.setHinhAnhDongVat(fileAnhDongVat.getName());
                luuFileAnh();
                view.getLbAnhDongVat().setIcon(getAnhDongVat("image/DongVat/" + fileAnhDongVat.getName()));
                connection.disconnect();
            }
        } catch (HeadlessException | IOException | NumberFormatException ex) {
            System.out.println("controller/SaveButtonListener" + ex);
        }
        fileAnhDongVat = null;
    }

    public void timKiemDongVat() {
        fillData(view.getTfTimKiem().getText().toLowerCase());
        reset();
    }

    public ImageIcon getAnhDongVat(String src) {
//        src = src.trim().equals("") ? "image/DongVat/default.jpg" : src;
        //Xử lý ảnh
        BufferedImage img = null;
        File fileImg = new File(src);

//        if (!fileImg.exists()) {
//            src = "default.jpg";
//            fileImg = new File("image/DongVat/" + src);
//        }
        try {
            img = ImageIO.read(fileImg);
            fileAnhDongVat = new File(src);
        } catch (IOException e) {
//            System.out.println("Loi khong co anh: " + e);
//            fileAnhDongVat = new File("image/DongVat/default.jpg");
        }

        if (img != null) {
            Image dimg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            return new ImageIcon(dimg);
        }
        return null;
    }

    public void reset() {
        try {
            view.getTfID().setText("");
            view.getTfTen().setText("");
            view.getTfLoai().setSelectedItem("");
            IntegerDocumentFilter.clearTextField(view.getTfTuoi());
            view.getTfGioiTinh().setText("");
            view.getTfTrangThai().setSelectedItem("Khoẻ mạnh");

            fileAnhDongVat = null;
            view.getLbAnhDongVat().setIcon(getAnhDongVat(""));
        } catch (Exception e) {
            System.out.println("controller/resset" + e);
        }

    }

    public ModelDongVat getDongVatFromTextField() {
        String ten = view.getTfTen().getText().trim();
        String loai = view.getTfLoai().getSelectedItem().toString().trim();
        String tuoi = view.getTfTuoi().getText().trim();
        String gioiTinh = view.getTfGioiTinh().getText().trim();
        String trangThai = view.getTfTrangThai().getSelectedItem().toString().trim();
        int age;

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập tên động vật");
            return null;
        } else if (loai.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập loài động vật");
            return null;
        } else if (tuoi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập tuổi động vật");
            return null;
        } else if (gioiTinh.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập giới tính động vật");
            return null;
        } else if (trangThai.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập trạng thái động vật");
            return null;
        }

        try {
            age = Integer.parseInt(tuoi);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Hãy nhập tuổi đúng định dạng");
            return null;
        }

        String anh = "";
        if (fileAnhDongVat != null) {
            anh = fileAnhDongVat.getName();
        }
        return new ModelDongVat(ten, loai, age, gioiTinh, trangThai, anh);

    }

    public void luuFileAnh() {
        if (fileAnhDongVat != null) {
            BufferedImage bImage;
            try {
                File initialImage = new File(fileAnhDongVat.getPath());
                bImage = ImageIO.read(initialImage);

                ImageIO.write(bImage, "png", new File("image/DongVat/" + fileAnhDongVat.getName()));

            } catch (IOException e) {
                System.out.println("controller/luuFileAnh" + e.getMessage());
            }
        }

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
        view.getTfLoai().removeAllItems();

        tableModel.setRowCount(0);

        getListDongVat(new OnGetListDongVatListener() {
            @Override
            public void onSuccess(ArrayList<ModelDongVat> dongVats) {
                if (!searchString.equals("")) {
                    for (ModelDongVat modelDongVat : dongVats) {
                        loaiListRaw.add(modelDongVat.getLoaiDongVat());
                        if (modelDongVat.toString().contains(searchString)) {
                            tableModel.addRow(new Object[]{
                                modelDongVat.getIDDongVat(),
                                modelDongVat.getTenDongVat(),
                                modelDongVat.getLoaiDongVat(),
                                modelDongVat.getTuoiDongVat(),
                                modelDongVat.getGioiTinhDongVat(),
                                modelDongVat.getTrangThaiDongVat(),
                                modelDongVat.getHinhAnhDongVat()
                            });
                        }
                    }
                } else {
                    for (ModelDongVat modelDongVat : dongVats) {
                        loaiListRaw.add(modelDongVat.getLoaiDongVat());
                        tableModel.addRow(new Object[]{
                            modelDongVat.getIDDongVat(),
                            modelDongVat.getTenDongVat(),
                            modelDongVat.getLoaiDongVat(),
                            modelDongVat.getTuoiDongVat(),
                            modelDongVat.getGioiTinhDongVat(),
                            modelDongVat.getTrangThaiDongVat(),
                            modelDongVat.getHinhAnhDongVat()
                        });
                    }
                }

                view.setLabelSLDongVat("Tổng số động vật: " + dongVats.size());
                table.setModel(tableModel);
                Utils.Utility.hideColumn(table, 6);

                HashSet<String> setWithoutDuplicates = new HashSet<>(loaiListRaw);
                ArrayList<String> loaiList = new ArrayList<>(setWithoutDuplicates);
                Collections.sort(loaiList);

                for (String loai : loaiList) {
                    view.getTfLoai().addItem(loai);
                }
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFailure() {
            }
        });

    }

    public void themDongVat(ModelDongVat dongVat) {
        try {

            Gson gson = new Gson();
            String json = gson.toJson(dongVat);

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
            System.out.println("Controller.ControllerDongVat.themDongVat() " + ex);
        }
    }

    public void getDongVat(int id, final OnGetDongVatListener listener) {
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
            dongVat = gson.fromJson(responseBuilder.toString(), ModelDongVat.class);

            connection.getResponseCode();
            connection.disconnect();
            listener.onSuccess(dongVat);
        } catch (IOException ex) {
            System.out.println("Controller.ControllerDongVat.getDongVat()" + ex);
            listener.onFailure();
        }
    }

    public void getListDongVat(final OnGetListDongVatListener listener) {
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
            ModelDongVat[] modelsArray = gson.fromJson(responseBuilder.toString(), ModelDongVat[].class);
            ArrayList<ModelDongVat> dongvats = new ArrayList<>(Arrays.asList(modelsArray));
            connection.disconnect();
            listener.onSuccess(dongvats);
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Controller.ControllerDongVat.getListDongVat()" + e);
            JOptionPane.showMessageDialog(view, "Không thể kế nối đến máy chủ");
            listener.onFailure();
        }
    }

    public interface OnGetListDongVatListener {

        void onSuccess(ArrayList<ModelDongVat> dongVats);

        void onStart();

        void onFailure();
    }

    public interface OnGetDongVatListener {

        void onSuccess(ModelDongVat dongVat);

        void onStart();

        void onFailure();
    }
}
