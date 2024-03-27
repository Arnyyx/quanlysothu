package Controller;

import Model.ModelNCC;
import Model.ModelThucAn;
import View.ViewNCC;
import View.ViewThucAn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
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
    private Connection connection;
    private DefaultTableModel tableModel; // Added table model as a class member

    public ControllerNCC(ViewNCC view) {
        this.view = view;
        this.connection = getcon();
        tableModel = new DefaultTableModel(); // Initialize table model
        view.getTable().setModel(tableModel); // Set table model to the JTable in the view

        // Add button event handling
        JButton addButton = view.getAddButton();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addncc();
            }
        });
//         Sự kiện cho button "Update"
        JButton updateButton = view.getUpdateButton();
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatencc();
            }
        });
// Add the event handling for the searchButton
        JButton searchButton = view.getSearchButton();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = view.getSearchField().getText(); // Assuming name is used for search

                if (!name.isEmpty()) {
                    searchByName(name);
                } else {
                    System.out.println("Please enter a name to search.");
                }
            }
        });
        JTable table = view.getTable();

        // Xử lý sự kiện khi người dùng chọn một hàng trong JTable
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();

                    // Lấy dữ liệu từ hàng được chọn và đưa vào các JTextField tương ứng
                    view.getIdField().setText(table.getValueAt(selectedRow, 0).toString());
                    view.getNameField().setText(table.getValueAt(selectedRow, 1).toString());
                    view.getFoodTypeField().setText(table.getValueAt(selectedRow, 2).toString());
                    view.getLocationField().setText(table.getValueAt(selectedRow, 3).toString());

                }
            }
        });

        JButton importButton = view.getImportButton();
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });
        JButton deleteButton = view.getDeleteButton();
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    int idColumn = 0; // Assume ID is in the first column
                    String ID = view.getTable().getValueAt(selectedRow, idColumn).toString();

                    deletethucan(ID);
                } else {
                    System.out.println("Vui lòng chọn một hàng để xóa.");
                }
            }
        });

        getcon();
        fillData("");
    }

    public Connection getcon() {
        String url = "jdbc:mysql://localhost:3306/quanlysothu";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            // Handle exception
            ex.printStackTrace();
            return null;
        } catch (SQLException ex) {
            // Handle exception
            ex.printStackTrace();
            return null;
        }
    }

    public void fillData(String search) {
        JTable table = view.getTable();
        ArrayList<ModelNCC> nccList = getFoodListFromDatabase(search);
        tableModel = constructTableModel(nccList);
        table.setModel(tableModel);
    }

    private ArrayList<ModelNCC> getFoodListFromDatabase(String search) {
        ArrayList<ModelNCC> list = new ArrayList<ModelNCC>();
        try {
            String sql = "SELECT * FROM ncc";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ModelNCC ncc = new ModelNCC(
                        resultSet.getInt("IdNCC"),
                        resultSet.getString("TenNCC"),
                        resultSet.getString("LoaiThucAN"),
                        resultSet.getString("Vitri")
                );
                list.add(ncc);
            }
            return list;
            // Update the table with search results
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private DefaultTableModel constructTableModel(ArrayList<ModelNCC> nccList) {
        String[] columns = {"ID", "Name", "Food Type", "Location "};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (ModelNCC ncc : nccList) {
            Object[] rowData = {
                ncc.getIdNCC(),
                ncc.getTenNCC(),
                ncc.getLoaiThucAn(),
                ncc.getViTri(),};
            tableModel.addRow(rowData);
        }

        return tableModel;
    }

    private void addncc() {
        // Retrieve data including image path from text fields
        String ID = view.getIdField().getText();
        String name = view.getNameField().getText();
        String type = view.getFoodTypeField().getText();
        String location = view.getLocationField().getText();

        if (!name.isEmpty() && !type.isEmpty()
                && !location.isEmpty()) {
            try {
                String sql = "INSERT INTO ncc (TenNCC, LoaiThucAN, ViTri) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, type);
                statement.setString(3, location); // Save image path to the database

                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("A new ncc item was inserted successfully!");
                    fillData(""); // Refresh the table with updated data
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle any database-related exceptions here
            }
        } else {
            System.out.println("Please fill in all the fields.");
        }
    }

    private void updatencc() {
        try {
            // Kiểm tra xem tất cả các trường có được điền đầy đủ hay không
            String ID = view.getIdField().getText();
            String name = view.getNameField().getText();
            String type = view.getFoodTypeField().getText();
            String location = view.getLocationField().getText();

            if (!ID.isEmpty() && !name.isEmpty() && !type.isEmpty() && !location.isEmpty()) {
                String sql = "UPDATE ncc SET TenNCC=?, LoaiThucAN=?, ViTri=? WHERE IdNCC=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, type);
                statement.setString(3, location);
                statement.setString(4, ID); // Thêm ID vào lệnh SQL

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Food item updated successfully!");
                    fillData(""); // Làm mới bảng với dữ liệu đã cập nhật
                }
            } else {
                System.out.println("Please fill in all the fields.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Xử lý các ngoại lệ liên quan đến cơ sở dữ liệu ở đây
        }
    }

    private void searchByName(String name) {
        try {
            // Perform the search based on the provided name
            String sql = "SELECT * FROM ncc WHERE TenNCC LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%"); // Search for partial matches

            ResultSet resultSet = statement.executeQuery();
            ArrayList<ModelNCC> searchResults = new ArrayList<ModelNCC>();

            while (resultSet.next()) {
                ModelNCC ncc = new ModelNCC(
                        resultSet.getInt("IdNCC"),
                        resultSet.getString("TenNCC"),
                        resultSet.getString("LoaiThucAN"),
                        resultSet.getString("ViTri")
                );
                searchResults.add(ncc);
            }

            // Update the table with search results
            tableModel = constructTableModel(searchResults);
            view.getTable().setModel(tableModel);
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle any database-related exceptions here
        }
    }

// Hàm xóa thức ăn từ cơ sở dữ liệu
    private void deletethucan(String ID) {
        try {
            String sql = "DELETE FROM ncc WHERE IdNCC=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Thức ăn đã được xóa thành công!");
                fillData(""); // Làm mới bảng với dữ liệu đã cập nhật
            } else {
                System.out.println("Không có mục nào được xóa. Vui lòng kiểm tra lại ID.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Xử lý các ngoại lệ liên quan đến cơ sở dữ liệu ở đây
        }
    }

    // Method to export data to an Excel file
    private void exportToExcel() {
        try {
            String excelFilePath = "‪ncc.xlsx"; // Example file path
            Workbook workbook = new XSSFWorkbook();

            // Tạo một trang mới
            Sheet sheet = workbook.createSheet("Sheet 1");

            JTable table = view.getTable();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(model.getColumnName(col));
            }

            for (int row = 0; row < model.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1);

                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = excelRow.createCell(col);
                    cell.setCellValue(String.valueOf(model.getValueAt(row, col)));
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }

            System.out.println("Dữ liệu đã được xuất thành công vào tệp Excel!");
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi khi ghi dữ liệu ra tệp Excel. Vui lòng kiểm tra và thử lại.");
            e.printStackTrace();
        }
    }
}
