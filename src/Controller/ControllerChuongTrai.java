/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.ModelChuongTrai;
import Utils.MyFileChooser;
import com.google.gson.Gson;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import Utils.XuLyFileExcel;
import View.ViewChuongTrai;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Duong
 */
public class ControllerChuongTrai {

    ViewChuongTrai view;
    private File fileImg = null;
    String apiString = Utils.Utility.apiString + "habitat/";

    public ArrayList<ModelChuongTrai> getListHabitat() {
        ArrayList<ModelChuongTrai> habitatArrayList = new ArrayList<>();
        try {
            URL url = new URL(apiString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            ModelChuongTrai[] habitatArray = gson.fromJson(response.toString(), ModelChuongTrai[].class);
            habitatArrayList = new ArrayList<>(Arrays.asList(habitatArray));

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return habitatArrayList;

    }

    private void fillData(String search) {
        DefaultTableModel tableModel = view.getTableModel();
        JTable table = view.getTable();
        ArrayList<String> loaiListRaw = new ArrayList<>();
        ArrayList<String> TrangThaiListRaw = new ArrayList<>();
        view.getTfName().removeAllItems();

        tableModel.setRowCount(0);
        ArrayList<ModelChuongTrai> listHabitat;
        listHabitat = getListHabitat();
        if (!search.equals("")) {
            for (ModelChuongTrai habitat : listHabitat) {
                loaiListRaw.add(habitat.getName());
                TrangThaiListRaw.add(habitat.getState());
                if (habitat.toString().contains(search)) {
                    tableModel.addRow(new Object[]{
                        habitat.getId(),
                        habitat.getName(),
                        habitat.getState(),
                        habitat.getArea(),
                        habitat.getQuantity_current(),
                        habitat.getQuantity(),
                        habitat.getImg()
                    });
                }
            }
        } else {
            for (ModelChuongTrai habitat : listHabitat) {
                loaiListRaw.add(habitat.getName());
                TrangThaiListRaw.add(habitat.getState());
                tableModel.addRow(new Object[]{
                    habitat.getId(),
                    habitat.getName(),
                    habitat.getState(),
                    habitat.getArea(),
                    habitat.getQuantity_current(),
                    habitat.getQuantity(),
                    habitat.getImg()
                });
            }
        }
        table.setModel(tableModel);

        HashSet<String> setWithoutDuplicates = new HashSet<>(loaiListRaw);
        HashSet<String> setWithoutDuplicates2 = new HashSet<>(TrangThaiListRaw);
        ArrayList<String> loaiList = new ArrayList<>(setWithoutDuplicates);
        ArrayList<String> TrangThaiList = new ArrayList<>(setWithoutDuplicates2);
        Collections.sort(loaiList);
        Collections.sort(TrangThaiList);

        Utils.Utility.hideColumn(table, 6);

        for (String loai : loaiList) {
            view.getTfName().addItem(loai);
        }
        for (String trangThai : TrangThaiList) {
            view.getTfState().addItem(trangThai);
        }

        view.getLbImg().setIcon(getImg("image/Habitat/home.png"));
    }

    public ControllerChuongTrai() {
    }

    public ControllerChuongTrai(ViewChuongTrai view) {
        this.view = view;
        fillData("");
        CLICKTABLE();
        BTNADD();
        BTNSAVE();
        BTNDELETE();
        BTNSEARCH();
        nhapExcel();
        xuatExcel();
        AddImg();
        BTNClear();
    }

    private void CLICKTABLE() {
        view.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int index = view.getTable().getSelectedRow();
                try {
                    URL url = new URL(apiString + view.getTable().getValueAt(index, 0));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    Gson gson = new Gson();
                    ModelChuongTrai habitatArray = gson.fromJson(response.toString(), ModelChuongTrai.class);

                    view.getTfID().setText(habitatArray.getId() + "");
                    view.getTfName().setSelectedItem(habitatArray.getName());
                    view.getTfState().setSelectedItem(habitatArray.getState());
                    view.getTfArea().setText(habitatArray.getArea() + "");
                    view.getTfQuantityCurrent().setText(habitatArray.getQuantity_current() + "");
                    view.getTfQuantity().setText(habitatArray.getQuantity() + "");
                    view.getLbImg().setIcon(getImg("image/Habitat/" + habitatArray.getImg()));

                    connection.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
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

        });
    }

    private void BTNADD() {
        view.getBtnAdd().addActionListener((e) -> {
            String name = view.getTfName().getSelectedItem().toString();
            String state = view.getTfState().getSelectedItem().toString();
            float area = Float.parseFloat(view.getTfArea().getText());
            int quantityCurrent = Integer.parseInt(view.getTfQuantityCurrent().getText());
            int quantity = Integer.parseInt(view.getTfQuantity().getText());
            String anh = "";
            if (fileImg != null) {
                anh = fileImg.getName();
            }
            ModelChuongTrai habitat = new ModelChuongTrai(name, state, area, quantityCurrent, quantity, anh);

            PostHabitat(habitat);
            SaveFileImg();
            fillData("");
            view.Clear();
        });
    }

    private void BTNClear() {
        view.getBtnLoad().addActionListener((e) -> {
            fillData("");
            view.Clear();
        });
    }

    private void BTNSAVE() {
        view.getBtnSave().addActionListener((e) -> {
            int id = Integer.parseInt(view.getTfID().getText());
            String name = view.getTfName().getSelectedItem().toString();
            String state = view.getTfState().getSelectedItem().toString();
            float area = Float.parseFloat(view.getTfArea().getText());
            int quantityCurrent = Integer.parseInt(view.getTfQuantityCurrent().getText());
            int quantity = Integer.parseInt(view.getTfQuantity().getText());

            String anh = "";
            if (fileImg != null) {
                anh = fileImg.getName();
            }
            ModelChuongTrai habitat = new ModelChuongTrai(name, state, area, quantityCurrent, quantity, anh);
            habitat.setId(id);
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(habitat);

            try {
                URL url = new URL(apiString + id);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                try (OutputStream outputStream = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    outputStream.write(input, 0, input.length);
                }

                connection.getResponseCode();
                connection.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            SaveFileImg();
            fillData("");
            view.Clear();
        });
    }

    private void BTNDELETE() {
        view.getBtnDelete().addActionListener((e) -> {
            int id = Integer.parseInt(view.getTfID().getText());

            RemoveHabitat(id);
            fillData("");
            view.Clear();
        });
    }

    private void BTNSEARCH() {
        view.getBtnTimKiem().addActionListener((e) -> {
            fillData(view.getTfSearch().getText().toLowerCase());
            view.Clear();
        });
    }

    private void nhapExcel() throws NumberFormatException {
        view.getBtnInput().addActionListener((e) -> {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Dữ liệu cũ sẽ bị xoá, tiếp tục?", "Nhập dữ liệu", JOptionPane.WARNING_MESSAGE);
            if (dialogResult != JOptionPane.YES_OPTION) {
                return;
            }

            ArrayList<ModelChuongTrai> listHabitat = getListHabitat();

            for (ModelChuongTrai habitatModel : listHabitat) {
                RemoveHabitat(habitatModel.getId());
            }

            JTable table = view.getTable();
            new XuLyFileExcel().nhapExcel(table);

            int row = view.getTable().getRowCount();
            for (int i = 0; i < row; i++) {
                String name = table.getValueAt(i, 1).toString();
                String state = table.getValueAt(i, 2).toString();
                float area = Float.parseFloat(table.getValueAt(i, 3).toString());
                int quantityCurrent = Integer.parseInt(table.getValueAt(i, 4).toString());
                int quantity = Integer.parseInt(table.getValueAt(i, 5).toString());
                String img = table.getValueAt(i, 6).toString();

                PostHabitat(new ModelChuongTrai(name, state, area, quantityCurrent, quantity, img));
            }
            fillData(view.getTfSearch().getText());
            view.Clear();
        });
    }

    private void xuatExcel() {
        view.getBtnOutput().addActionListener((e) -> {
            new XuLyFileExcel().xuatExcel(view.getTable());
            fillData(view.getTfSearch().getText());
        });
    }

    private void PostHabitat(ModelChuongTrai habitat) {
        try {
            URL url = new URL(apiString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            Gson gson = new Gson();
            String jsonInputString = gson.toJson(habitat);

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            connection.getResponseCode();
            connection.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void RemoveHabitat(int id) {
        try {
            ModelChuongTrai habitat = new ModelChuongTrai();
            habitat.setId(id);
            URL url = new URL(apiString + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);

            connection.getResponseCode();
            connection.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void AddImg() {
        view.getBtnImageChange().addActionListener((e) -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
                System.out.println("controller/ImageChangeButtonListener" + ex);
            }
            JFileChooser fileChooser = new MyFileChooser("image/Habitat/");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Tệp hình ảnh", "jpg", "png", "jpeg");
            fileChooser.setFileFilter(filter);
            int returnVal = fileChooser.showOpenDialog(view);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                fileImg = fileChooser.getSelectedFile();
                view.getLbImg().setIcon(getImg(fileImg.getPath()));
            }
        });
    }

    public ImageIcon getImg(String src) {
        BufferedImage img = null;
        File fileImg = new File(src);
        try {
            img = ImageIO.read(fileImg);
//            fileImg = new File(src);
        } catch (IOException e) {
            System.out.println("Loi khong co anh: " + e);
        }

        if (img != null) {
            Image dimg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            return new ImageIcon(dimg);
        }

        return null;
    }

    public void SaveFileImg() {
        if (fileImg != null) {
            BufferedImage bImage;
            try {
                File initialImage = new File(fileImg.getPath());
                bImage = ImageIO.read(initialImage);

                ImageIO.write(bImage, "png", new File("image/Habitat/" + fileImg.getName()));

            } catch (IOException e) {
                System.out.println("controller/luuFileAnh" + e.getMessage());
            }
        }

    }

}
