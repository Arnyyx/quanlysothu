package Controller;

import Model.ModelDongVat;
import Model.ModelThucAn;
import Model.ModelNCC;

import View.ViewThucAn;
import com.google.gson.Gson;
import com.mysql.cj.xdevapi.Statement;
import com.sun.jdi.connect.spi.Connection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Controllerthucan {

    private ViewThucAn view;
    private DefaultTableModel tableModel;
    String apiString = "http://localhost:8000/thucan"; // Update with your API string

    public ArrayList<ModelThucAn> getListFood() {
        ArrayList<ModelThucAn> foodArrayList = new ArrayList<>();
        ArrayList<ModelNCC> nccArrayList = new ArrayList<>();
        ArrayList<ModelDongVat> dongvatArrayList = new ArrayList<>();
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
            ModelThucAn[] foodArray = gson.fromJson(response.toString(), ModelThucAn[].class);
            foodArrayList = new ArrayList<>(Arrays.asList(foodArray));

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL("http://localhost:8000/ncc");
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
            ModelNCC[] nccArray = gson.fromJson(response.toString(), ModelNCC[].class);
            nccArrayList = new ArrayList<>(Arrays.asList(nccArray));
            ArrayList<Integer> nccListRaw = new ArrayList<>();
            for (ModelNCC ncc : nccArrayList) {
                nccListRaw.add(ncc.getIdNCC());
            }
            HashSet<Integer> setWithoutDuplicates = new HashSet<>(nccListRaw);
            ArrayList<Integer> nccList = new ArrayList<>(setWithoutDuplicates);
            Collections.sort(nccList);

            for (int ncc : nccList) {
                view.getSupplierField().addItem(ncc);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL("http://localhost:8000/dongvat");
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
            ModelDongVat[] dongvatArray = gson.fromJson(response.toString(), ModelDongVat[].class);

            Set<Integer> dongvatSet = new HashSet<>();
            for (ModelDongVat dongvat : dongvatArray) {
                dongvatSet.add(dongvat.getIDDongVat());
            }

            List<Integer> dongvatList = new ArrayList<>(dongvatSet);
            Collections.sort(dongvatList);

            // Thêm các đối tượng ModelDongVat vào view.getSupplierField() thích hợp
            for (int dongvatID : dongvatList) {
                ModelDongVat dongvat = new ModelDongVat(); // Tạo một đối tượng ModelDongVat mới
                dongvat.setIDDongVat(dongvatID); // Đặt ID cho đối tượng ModelDongVat
                view.getAnimalIdField().addItem(dongvat.getIDDongVat());
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foodArrayList;

    }

    private void fillData(String search) {
        DefaultTableModel tableModel = view.getModel();
        JTable table = view.getTable();
        tableModel.setRowCount(0);

        ArrayList<ModelThucAn> listfood;
        listfood = getListFood();
        if (!search.equals("")) {
            for (ModelThucAn food : listfood) {
//                loaiListRaw.add(food.getTenThucAn());
//                TrangThaiListRaw.add(habitat.getState());
                if (food.getTenThucAn().toLowerCase().contains(search.toLowerCase())) {
                    tableModel.addRow(new Object[]{
                        food.getIdThucAn(),
                        food.getTenThucAn(),
                        food.getLoaiThucAn(),
                        food.getIdDongVat(),
                        food.getSoLuong(),
                        food.getHanSuDung(),
                        food.getIdNCC()

                    });
                }
            }
        } else {
            for (ModelThucAn food : listfood) {
//                loaiListRaw.add(habitat.getName());
//                TrangThaiListRaw.add(habitat.getState());
                tableModel.addRow(new Object[]{
                    food.getIdThucAn(),
                    food.getTenThucAn(),
                    food.getLoaiThucAn(),
                    food.getIdDongVat(),
                    food.getSoLuong(),
                    food.getHanSuDung(),
                    food.getIdNCC()

                });
            }
        }
        table.setModel(tableModel);

    }

    public Controllerthucan() {
    }

    public Controllerthucan(ViewThucAn view) {
        this.view = view;
        fillData("");
        CLICKTABLE();
        BTNADD();
        BTNUPDATE();
        BTNDELETE();
        BTNSEARCH();
        XuatExcel();
        BTNBACK();
        BTNStatistics();
    }

    private void CLICKTABLE() {
        view.getTable().addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                int index = view.getTable().getSelectedRow();
                try {
                    URL url = new URL(apiString + '/' + view.getTable().getValueAt(index, 0));
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
                    ModelThucAn foodArray = gson.fromJson(response.toString(), ModelThucAn.class);

                    view.getIdField().setText(foodArray.getIdThucAn() + "");
                    view.getNameField().setText(foodArray.getTenThucAn());
                    view.getTypeField().setText(foodArray.getLoaiThucAn());
                    view.getAnimalIdField().setSelectedItem(foodArray.getIdDongVat() + "");
                    view.getQuantityField().setText(foodArray.getSoLuong() + "");
                    view.getExpiryField().setText(foodArray.getHanSuDung() + "");
                    view.getSupplierField().setSelectedItem(foodArray.getIdNCC() + "");

                    connection.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

        });
    }

    private void BTNADD() {
        view.getAddButton().addActionListener((e) -> {
            if (view.getNameField().getText().isEmpty() || view.getTypeField().getText().isEmpty() || view.getAnimalIdField().getSelectedItem() == null || view.getQuantityField().getText().isEmpty() || view.getExpiryField().getText().isEmpty() || view.getSupplierField().getSelectedItem() == null) {
                // Hiển thị thông báo yêu cầu nhập đủ thông tin trên giao diện người dùng
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đủ thông tin vào các trường");

                // Hoặc bạn có thể thiết lập thông báo lỗi vào từng trường nhập liệu cụ thể để hướng dẫn người dùng
            } else {
                try {
                    String name = view.getNameField().getText();
                    String loai = view.getTypeField().getText();
                    int iddongvat = Integer.parseInt(view.getAnimalIdField().getSelectedItem().toString());
                    int soluong = Integer.parseInt(view.getQuantityField().getText());
                    String hsd = view.getExpiryField().getText();
                    int idncc = Integer.parseInt(view.getSupplierField().getSelectedItem().toString());

                    ModelThucAn food = new ModelThucAn(name, loai, iddongvat, soluong, hsd, idncc);

                    Postfood(food);
                    view.clear();
                    fillData("");
                } catch (NumberFormatException ex) {
                    // Xử lý ngoại lệ NumberFormatException ở đây (ví dụ: hiển thị thông báo lỗi cho người dùng)
                }
            }
        });
    }

    private void BTNUPDATE() {
        view.getUpdateButton().addActionListener((e) -> {
            int idthucan = Integer.parseInt(view.getIdField().getText());
            String name = view.getNameField().getText();
            String loai = view.getTypeField().getText();
            int iddongvat = Integer.parseInt(view.getAnimalIdField().getSelectedItem().toString());
            int soluong = Integer.parseInt(view.getQuantityField().getText());
            String hsd = view.getExpiryField().getText();
            int idncc = Integer.parseInt(view.getSupplierField().getSelectedItem().toString());

            ModelThucAn food = new ModelThucAn(name, loai, iddongvat, soluong, hsd, idncc);
            food.setIdThucAn(idthucan);
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(food);

            try {
                URL url = new URL(apiString + '/' + idthucan);
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
                System.out.println("Controller.Controllerthucan.BTNUPDATE()" + ex);
            }
            view.clear();
            fillData("");

        });
    }

    private void BTNDELETE() {
        view.getDeleteButton().addActionListener((e) -> {
            int idthucan = Integer.parseInt(view.getIdField().getText());

            int choice = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                Removefood(idthucan);
                JOptionPane.showMessageDialog(view, "Thức ăn đã được xóa thành công.");
                view.clear();
                fillData("");
            }
        });
    }

    private void BTNSEARCH() {
        view.getSearchButton().addActionListener((e) -> {
            String searchTerm = view.getSearchField().getText().toLowerCase(); // Get the search keyword from the search field
            fillData(searchTerm);
        });
    }

    private void BTNBACK() {
        view.getBackButton().addActionListener((e) -> {
            fillData(""); // Cập nhật dữ liệu khi quay lại bảng chính
        });
    }

    private void Postfood(ModelThucAn food) {
        try {
            URL url = new URL(apiString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            Gson gson = new Gson();
            String jsonInputString = gson.toJson(food);

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

    private void Removefood(int idthucan) {
        try {
            ModelThucAn food = new ModelThucAn();
            food.setIdThucAn(idthucan);
            URL url = new URL(apiString + '/' + idthucan);
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

    private void XuatExcel() {
        try {
            String excelFilePath = "thucan.xlsx"; // Đường dẫn và tên tệp Excel

            Workbook workbook = new XSSFWorkbook(); // Tạo một Workbook mới

            Sheet sheet = workbook.createSheet("ThucAnData"); // Tạo một trang mới trong Workbook

            DefaultTableModel model = view.getModel();
            JTable table = view.getTable();

            // Tạo dòng tiêu đề trong tệp Excel
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(model.getColumnName(i));
            }

            // Đưa dữ liệu từ JTable vào tệp Excel
            for (int row = 0; row < model.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = excelRow.createCell(col);
                    cell.setCellValue(String.valueOf(model.getValueAt(row, col)));
                }
            }

            // Ghi dữ liệu vào tệp Excel
            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
                System.out.println("Dữ liệu đã được xuất thành công vào tệp Excel!");
            } catch (IOException e) {
                System.out.println("Đã xảy ra lỗi khi ghi dữ liệu ra tệp Excel. Vui lòng kiểm tra và thử lại.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi xuất Excel.");
            e.printStackTrace();
        }
    }

    private void BTNStatistics() {
        view.getStatisticsButton().addActionListener((e) -> {
            ArrayList<ModelThucAn> foodList = getListFood();
            DefaultTableModel statisticsTableModel = new DefaultTableModel(new String[]{"Tên", "Số lượng"}, 0);
            for (ModelThucAn food : foodList) {
                String tenThucAn = food.getTenThucAn();
                int totalQuantity = food.getSoLuong();
                statisticsTableModel.addRow(new Object[]{tenThucAn, totalQuantity});
            }

            // Update the JTable in the view with the statistical data
            view.getTable().setModel(statisticsTableModel);
        });
    }
}
