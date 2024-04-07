package Controller;

import Model.ModelNCC;
import Model.ModelThucAn;
import View.ViewNCC;
import View.ViewThucAn;
import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ControllerNCC {

    private ViewNCC view;
    private DefaultTableModel tableModel; // Added table model as a class member
    String apiString = "http://localhost:8000/ncc"; // Update with your API string

    public ControllerNCC(ViewNCC view) {
        this.view = view;
        fillData("");
        CLICKTABLE();
        BTNADD();
        BTNUPDATE();
        BTNDELETE();
        BTNSEARCH();
        XuatExcel();
        BTNBACK();
    }

    public ArrayList<ModelNCC> getListNCC(String search) {
        ArrayList<ModelNCC> nccArrayList = new ArrayList<>();
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

            ModelNCC[] nccArray = gson.fromJson(response.toString(), ModelNCC[].class);
            nccArrayList = new ArrayList<>(Arrays.asList(nccArray));

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Đã xảy ra lỗi khi truy cập dữ liệu NCC: " + e.getMessage());
        }

        return nccArrayList;
    }

    public void fillData(String search) {
        DefaultTableModel tableModel = view.getModel();
        JTable table = view.getTable();
        tableModel.setRowCount(0);

        ArrayList<ModelNCC> listncc;
        listncc = getListNCC("");
        if (!search.equals("")) {
            for (ModelNCC ncc : listncc) {
                if (ncc.getTenNCC().toLowerCase().contains(search.toLowerCase())) {
                    tableModel.addRow(new Object[]{
                        ncc.getIdNCC(),
                        ncc.getTenNCC(),
                        ncc.getLoaiThucAn(),
                        ncc.getViTri()

                    });
                }
            }
        } else {
            for (ModelNCC ncc : listncc) {
                tableModel.addRow(new Object[]{
                    ncc.getIdNCC(),
                    ncc.getTenNCC(),
                    ncc.getLoaiThucAn(),
                    ncc.getViTri()

                });
            }
        }
        table.setModel(tableModel);

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
                    ModelNCC nccArray = gson.fromJson(response.toString(), ModelNCC.class);

                    view.getIdField().setText(nccArray.getIdNCC() + "");
                    view.getNameField().setText(nccArray.getTenNCC() + "");
                    view.getFoodTypeField().setText(nccArray.getLoaiThucAn());
                    view.getLocationField().setText(nccArray.getViTri() + "");

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
        if (view.getNameField().getText().isEmpty() || view.getFoodTypeField().getText().isEmpty() || view.getLocationField().getText().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đủ thông tin trong các trường");
        } else {
            try {
                String tenncc = view.getNameField().getText();
                String loaiThucan = view.getFoodTypeField().getText();
                String viTri = view.getLocationField().getText();

                // Kiểm tra trùng tên nhà cung cấp
                boolean exists = checkDuplicateNCCName(tenncc);
                if (exists) {
                    JOptionPane.showMessageDialog(view, "Nhà cung cấp đã tồn tại. Vui lòng nhập tên nhà cung cấp khác.");
                } else {
                    ModelNCC ncc = new ModelNCC(tenncc, loaiThucan, viTri);
                    Postncc(ncc);
                    view.clear();
                    fillData("");
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    });
}

private boolean checkDuplicateNCCName(String tenncc) {
    try {
        URL url = new URL(apiString + '/' + URLEncoder.encode(tenncc, StandardCharsets.UTF_8));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = in.readLine();
            in.close();
            
            // Parse the response and determine if the NCC name is a duplicate
            return response != null;
        }
    } catch (IOException e) {
        e.printStackTrace();
      JOptionPane.showMessageDialog(view, "Đã xảy ra lỗi khi kiểm tra trùng tên nhà cung cấp: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    return false; // Return false if there's an error or no duplicate check result
}


   private void BTNUPDATE() {
    view.getUpdateButton().addActionListener((e) -> {
        int idncc = Integer.parseInt(view.getIdField().getText());
        String tenncc = view.getNameField().getText();
        String loaithucan = view.getFoodTypeField().getText();
        String vitri = view.getLocationField().getText();

        ModelNCC ncc = new ModelNCC(tenncc, loaithucan, vitri);
        ncc.setIdNCC(idncc);
        Gson gson = new Gson();
        String jsonInputString = gson.toJson(ncc);
        
        try {
            URL url = new URL(apiString + '/' + idncc);
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
            view.clear();
            fillData("");

        });
    }
    private void BTNDELETE() {
    view.getDeleteButton().addActionListener((e) -> {
        int idncc = Integer.parseInt(view.getIdField().getText());

        int choice = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            Removencc(idncc);
            JOptionPane.showMessageDialog(view, "Nhà cung cấp đã được xóa thành công.");
            view.clear();
            fillData("");
        }
    });
}


    private void BTNBACK() {
        view.getBackButton().addActionListener((e) -> {
            fillData(""); // Cập nhật dữ liệu khi quay lại bảng chính
        });
    }

    private void BTNSEARCH() {
        view.getSearchButton().addActionListener((e) -> {
            String searchTerm = view.getSearchField().getText().toLowerCase(); // Get the search keyword from the search field
            fillData(searchTerm);
        });
    }

    private void Postncc(ModelNCC ncc) {
        try {
            URL url = new URL(apiString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            Gson gson = new Gson();
            String jsonInputString = gson.toJson(ncc);

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

    private void Removencc(int idncc) {
        try {
            ModelNCC ncc = new ModelNCC();
            ncc.setIdNCC(idncc);
            URL url = new URL(apiString + '/' + idncc);
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
            String excelFilePath = "ncc.xlsx"; // Đường dẫn và tên tệp Excel

            Workbook workbook = new XSSFWorkbook(); // Tạo một Workbook mới

            Sheet sheet = workbook.createSheet("NCCData"); // Tạo một trang mới trong Workbook

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
//    public static void main(String[] args) {
//        ViewNCC view = new ViewNCC();
//        ControllerNCC control = new ControllerNCC(view);
//        control.fillData(""); // Thêm tham số vào fillData
//    }
}
