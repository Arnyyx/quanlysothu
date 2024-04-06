/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.ModelChuongTrai;
import Model.ModelMoiTruong;
import Model.ModelNhanVien;
import java.util.Date;
import com.google.gson.Gson;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Utils.XuLyFileExcel;
import View.ViewMoiTruong;
import java.io.File;

/**
 *
 * @author Duong
 */
public class ControllerMoiTruong {

    String nameHabitatCurrent="";
    ViewMoiTruong view;
    ControllerChuongTrai conHabitat = new ControllerChuongTrai();
    ControllerNhanVien conNhanvien = new ControllerNhanVien();
    String apiString = Utils.Utility.apiString + "environment/";
    
    public ControllerMoiTruong(ViewMoiTruong view, String name) {
        this.view = view;
        view.Clear();
        nameHabitatCurrent=name;
        fillData("");
        CLICKTABLE();
        BTNADD();
        BTNSAVE();
        BTNDELETE();
        BTNSEARCH();
        nhapExcel();
        xuatExcel();
    }

    public ArrayList<ModelMoiTruong> getListEnv() {
        ArrayList<ModelMoiTruong> envArrayList = new ArrayList<>();
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
            ModelMoiTruong[] envArray = gson.fromJson(response.toString(), ModelMoiTruong[].class);
            envArrayList = new ArrayList<>(Arrays.asList(envArray));

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return envArrayList;

    }

    private void fillData(String search) {
        DefaultTableModel tableModel = view.getTableModel();
        JTable table = view.getTable();
        view.getTfStaff().removeAllItems();
        view.getTfIDHabitat().setText(nameHabitatCurrent);

        tableModel.setRowCount(0);
        ArrayList<ModelMoiTruong> listenv;
        ArrayList<ModelChuongTrai> listhabitat = conHabitat.getListHabitat();
        ArrayList<ModelNhanVien> listnhanvien = conNhanvien.getDanhSachNhanVien();

        listenv = getListEnv();
        for (ModelMoiTruong env : listenv) {
            if(nameHabitatCurrent.equals(env.getIDHabitat())){
                if (env.toString().contains(search)) {
                tableModel.addRow(new Object[]{
                    env.getID(),
                    env.getIDHabitat(),
                    env.getStaff(),
                    env.getDate(),
                    env.getIDHabitat(),
                    env.getState(),
                    env.getDescription(),
                });
            }
            }
        }

        for (ModelNhanVien nhanvien : listnhanvien) {
            view.getTfStaff().addItem(nhanvien.getMaNV());
        }

        table.setModel(tableModel);
      
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
                    ModelMoiTruong stateArr = gson.fromJson(response.toString(), ModelMoiTruong.class);

                    view.getTfID().setText(stateArr.getID() + "");
                    view.getTfIDHabitat().setText(stateArr.getIDHabitat());
                    view.getTfStaff().setSelectedItem(stateArr.getStaff());
                    view.getTfDate().setDate(stateArr.getDate());
                    view.getTfState().setSelectedItem(stateArr.getState());
                    view.getTfDescription().setText(stateArr.getDescription() + "");

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
            String habitat = view.getTfIDHabitat().getText();
            String staff = view.getTfStaff().getSelectedItem().toString();
            Date date = view.getTfDate().getDate();
            String state = view.getTfState().getSelectedItem().toString();
            String description = view.getTfDescription().getText();

            ModelMoiTruong env = new ModelMoiTruong(habitat, staff, date , state, description);

            PostEnv(env);
            fillData("");
            view.Clear();
        });
    }

    private void BTNSAVE() {
        view.getBtnSave().addActionListener((e) -> {
            int id = Integer.parseInt(view.getTfID().getText());
            String habitat = view.getTfIDHabitat().getText();
            String staff = view.getTfStaff().getSelectedItem().toString();
            Date date = view.getTfDate().getDate();
            String state = view.getTfState().getSelectedItem().toString();
            String description = view.getTfDescription().getText();

            ModelMoiTruong env = new ModelMoiTruong(habitat, staff, date , state, description);
            env.setID(id);
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(env);

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

            fillData("");
            view.Clear();
        });
    }

    private void BTNDELETE() {
        view.getBtnDelete().addActionListener((e) -> {
            int id = Integer.parseInt(view.getTfID().getText());

            RemoveEnv(id);
            fillData("");
            view.Clear();
        });
    }

    private void BTNSEARCH() {
        view.getBtnSearch().addActionListener((e) -> {
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

            ArrayList<ModelMoiTruong> listEnv = getListEnv();

            for (ModelMoiTruong envModel : listEnv) {
                RemoveEnv(envModel.getID());
            }

            JTable table = view.getTable();
            new XuLyFileExcel().nhapExcel(table);

            int row = view.getTable().getRowCount();
            for (int i = 0; i < row; i++) {
                String habitat = table.getValueAt(i, 1).toString();
                String staff = table.getValueAt(i, 2).toString();
                Date date = (Date) table.getValueAt(i, 3);
                String state = table.getValueAt(i, 4).toString();
                String description = table.getValueAt(i, 5).toString();

                PostEnv(new ModelMoiTruong(habitat, staff, date , state, description));
            }
            fillData(view.getTfSearch().getText());
            view.Clear();
        });
    }

    private void xuatExcel() {
        view.getBtnOutPut().addActionListener((e) -> {
            new XuLyFileExcel().xuatExcel(view.getTable());
            fillData(view.getTfSearch().getText());
        });
    }

    private void PostEnv(ModelMoiTruong env) {
        try {
            URL url = new URL(apiString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            Gson gson = new Gson();
            String jsonInputString = gson.toJson(env);

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

    private void RemoveEnv(int id) {
        try {
            ModelMoiTruong moiTruong = new ModelMoiTruong();
            moiTruong.setID(id);
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

//    public static void main(String[] args) {
//        ViewMoiTruong v = new ViewMoiTruong();
//        ControllerMoiTruong a = new ControllerMoiTruong(v);
//    }
}
