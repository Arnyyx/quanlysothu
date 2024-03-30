/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.ModelLoaiVe;
import View.ViewLoaiVe;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class ControllerLoaiVe {

    private ViewLoaiVe view;
    private ModelLoaiVe loaive = new ModelLoaiVe();
    private File fileAnhLoaiVe = null;
    String apiString = Utils.Utility.apiString + "loaive/";
    boolean isDuplicate = false;

    public ControllerLoaiVe() {
    }

    public ControllerLoaiVe(ViewLoaiVe viewLoaiVe) {
        view = viewLoaiVe;
        fillData("");

        view.getBtnAdd().addActionListener((e) -> {
            loaive = getLoaiVeFromTextField();
            if (loaive == null) {
                return;
            }
            if (isDuplicateTenLoaiVe(loaive)) {
                JOptionPane.showMessageDialog(null, "Loại vé đã tồn tại");
                return;
            }
            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn thêm loại vé mới? ", "Thêm loại vé mới?", JOptionPane.WARNING_MESSAGE);
            if (dialogResult != JOptionPane.YES_OPTION) {
                return;
            }

            themLoaiVe(loaive);
            fillData("");
            reset();
        });
        view.getBtnDelete().addActionListener((e) -> {
            xoaLoaiVe();
        });
        view.getTable().addMouseListener(xuLyCLickTableLoaiVe());
        view.getBtnLoad().addActionListener((e) -> {
            loadLoaiVe();
        });
        view.getBtnSave().addActionListener((e) -> {
            if (view.getTfID().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Hãy chọn một loại vé");
                return;
            }
//            if (!loaive.getTenLoaiVe().equals(getLoaiVeFromTextField().getTenLoaiVe())) {
//                JOptionPane.showMessageDialog(null, "Loại Vé đã tồn tại");
//                return;
//            }
            luuLoaiVe();
        });
        view.getBtnTimKiem().addActionListener((e) -> {
            timKiemLoaiVe();
        });
    }

    private MouseListener xuLyCLickTableLoaiVe() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JTable table = view.getTable();
                int row = table.getSelectedRow();
                if (row > -1) {

                    getLoaiVe(Integer.parseInt(table.getValueAt(row, 0).toString()), new OnGetLoaiVeListener() {
                        @Override
                        public void onSuccess(ModelLoaiVe loaive) {
                            view.getTfID().setText(loaive.getIDLoaiVe() + "");
                            view.getTfName().setText(loaive.getTenLoaiVe());
                            view.getTfArea().setText(loaive.getKhuVuc() + "");
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

    public void loadLoaiVe() {
        fillData("");
        reset();
//        view.getTfTimKiem().setText("");
    }

    public void xoaLoaiVe() {
        if (multirowSelected().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy chọn một loài loaị vé");
            return;
        }

        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn xoá (các) loaị vé đã chọn?", "Xoá loaị vé?", JOptionPane.WARNING_MESSAGE);
        if (dialogResult == JOptionPane.YES_OPTION) {
            for (Integer id : multirowSelected()) {
                try {
                    String apiUrl = apiString + id;

                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("DELETE");

                    connection.getResponseCode();

                    fillData("");
                    connection.disconnect();
                } catch (IOException ex) {
                    System.out.println("Controller.ControllerLoaiVe.xoaLoaiVe() " + ex);
                }
            }
            reset();
        }
    }

    public void luuLoaiVe() {
        try {
            loaive = getLoaiVeFromTextField();
            if (loaive == null) {
                return;
            }

            int id = Integer.parseInt(view.getTfID().getText());

            int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc là muốn sửa loaị vé có ID = " + id + "?", "Sửa loaị vé?", JOptionPane.WARNING_MESSAGE);
            if (dialogResult == JOptionPane.YES_OPTION) {
                loaive.setIDLoaiVe(id);
                Gson gson = new Gson();
                String json = gson.toJson(loaive);

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

                fillData("");

                fileAnhLoaiVe = null;
                connection.disconnect();
            }
        } catch (HeadlessException | IOException | NumberFormatException ex) {
            System.out.println("controller/SaveButtonListener" + ex);
        }
    }

    public void timKiemLoaiVe() {
        fillData("");
        reset();
    }

    public ImageIcon getAnhLoaiVe(String src) {// 
        BufferedImage img = null;
        File fileImg = new File(src);

        try {
            img = ImageIO.read(fileImg);
            fileAnhLoaiVe = new File(src);
        } catch (IOException e) {

        }

        if (img != null) {
            Image dimg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            return new ImageIcon(dimg);
        }
        return null;
    }

    public void reset() {
        try {
            isDuplicate = false;
            view.getTfID().setText("");
            view.getTfName().setText("");
            view.getTfArea().setText("");

            fileAnhLoaiVe = null;
        } catch (Exception e) {
            System.out.println("controller/resset" + e);
        }

    }

    public ModelLoaiVe getLoaiVeFromTextField() {
        String ten = view.getTfName().getText().trim();
        String khuvuc = view.getTfArea().getText().trim();
        int age;

        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập tên vé");
            return null;
        } else if (khuvuc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hãy nhập chọn khu vực");
            return null;
        }
        return new ModelLoaiVe(ten, khuvuc);

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

        tableModel.setRowCount(0);

        getListLoaiVe(new OnGetListLoaiVeListener() {
            @Override
            public void onSuccess(ArrayList<ModelLoaiVe> loaives) {
                for (ModelLoaiVe modelLoaiVe : loaives) {
                    if (modelLoaiVe.toString().contains(searchString)) {
                        tableModel.addRow(new Object[]{
                            modelLoaiVe.getIDLoaiVe(),
                            modelLoaiVe.getTenLoaiVe(),
                            modelLoaiVe.getKhuVuc(),});
                    }
                }

//                view.setLabelSLLoaiVe("Tổng số loaị vé: " + loaives.size());
                table.setModel(tableModel);

                HashSet<String> setWithoutDuplicates = new HashSet<>(loaiListRaw);
                ArrayList<String> loaiList = new ArrayList<>(setWithoutDuplicates);
                Collections.sort(loaiList);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFailure() {
            }
        });
    }

    public void themLoaiVe(ModelLoaiVe loaive) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(loaive);

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
            System.out.println("Controller.ControllerLoaiVe.themLoaiVe() " + ex);
        }
    }

    public void getLoaiVe(int id, final OnGetLoaiVeListener listener) {
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
            loaive = gson.fromJson(responseBuilder.toString(), ModelLoaiVe.class);

            connection.getResponseCode();
            connection.disconnect();
            listener.onSuccess(loaive);
        } catch (IOException ex) {
            System.out.println("Controller.ControllerLoaiVe.getLoaiVe()" + ex);
            listener.onFailure();
        }
    }

    public void getListLoaiVe(final OnGetListLoaiVeListener listener) {
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
            ModelLoaiVe[] modelsArray = gson.fromJson(responseBuilder.toString(), ModelLoaiVe[].class);
            ArrayList<ModelLoaiVe> dongvats = new ArrayList<>(Arrays.asList(modelsArray));

            connection.disconnect();
            listener.onSuccess(dongvats);
        } catch (JsonSyntaxException | IOException e) {
            System.out.println("Controller.ControllerLoaiVe.getListLoaiVe()" + e);
            JOptionPane.showMessageDialog(view, "Không thể kế nối đến máy chủ");
            listener.onFailure();
        }
    }

    public interface OnGetListLoaiVeListener {

        void onSuccess(ArrayList<ModelLoaiVe> loaives);

        void onStart();

        void onFailure();
    }

    public interface OnGetLoaiVeListener {

        void onSuccess(ModelLoaiVe loaive);

        void onStart();

        void onFailure();
    }

    public boolean isDuplicateTenLoaiVe(ModelLoaiVe loaiveCheck) {
        isDuplicate = false;
        getListLoaiVe(new OnGetListLoaiVeListener() {
            @Override
            public void onSuccess(ArrayList<ModelLoaiVe> loaives) {
                for (ModelLoaiVe loaive : loaives) {
                    if (loaive.getTenLoaiVe().equals(loaiveCheck.getTenLoaiVe())) {
                        isDuplicate = true;
                        break;
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

        return isDuplicate; // Trả về giá trị trùng lặp
    }
}
