package Controller;

import Model.ModelThucAn;
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

public class Controllerthucan {

    private ViewThucAn view;
    private Connection connection;
    private DefaultTableModel tableModel; // Added table model as a class member

    public Controllerthucan(ViewThucAn view) {
        this.view = view;
        this.connection = getcon();
        tableModel = new DefaultTableModel(); // Initialize table model
        view.getTable().setModel(tableModel); // Set table model to the JTable in the view

        // Add button event handling
        JButton addButton = view.getAddButton();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addthucan();
            }
        });
//         Sự kiện cho button "Update"
        JButton updateButton = view.getUpdateButton();
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatethucan();
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
                    view.getTypeField().setText(table.getValueAt(selectedRow, 2).toString());
                    view.getAnimalIdField().setText(table.getValueAt(selectedRow, 3).toString());
                    view.getQuantityField().setText(table.getValueAt(selectedRow, 4).toString());
                    view.getExpiryField().setText(table.getValueAt(selectedRow, 5).toString());
                    view.getSupplierField().setText(table.getValueAt(selectedRow, 6).toString());
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

        JButton statisticsButton = view.getStatisticsButton();
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticsByQuantity();
            }
        });

// Thêm sự kiện cho nút "Quay lại"
        // Thêm sự kiện cho nút "Quay lại"
        JButton backButton = view.getBackButton(); // Get the "Quay lại" button from the UI
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillData(""); // Reset the table data to its original state
            }
        });

        getcon();
        fillData("");
    }

    public Connection getcon() {
        String url = "jdbc:mysql://localhost:3306/quanlysothu";
        String user = "root";
        String password = "123456";
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
        ArrayList<ModelThucAn> foodList = getFoodListFromDatabase(search);
        tableModel = constructTableModel(foodList);
        table.setModel(tableModel);
    }

    private ArrayList<ModelThucAn> getFoodListFromDatabase(String search) {
        ArrayList<ModelThucAn> list = new ArrayList<ModelThucAn>();
        try {
            String sql = "SELECT * FROM thucan";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ModelThucAn food = new ModelThucAn(
                        resultSet.getInt("IDThucAn"),
                        resultSet.getString("TenThucAn"),
                        resultSet.getString("LoaiThucAn"),
                        resultSet.getInt("IDDongVat"),
                        resultSet.getInt("SoLuong"),
                        resultSet.getString("HanThucAN"),
                        resultSet.getInt("IdNCC")
                );
                list.add(food);
            }
            return list;
            // Update the table with search results
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private DefaultTableModel constructTableModel(ArrayList<ModelThucAn> foodList) {
        String[] columns = {"ID", "Name", "Type", "Animal ID", "Quantity", "Expiry Date", "Supplier ID"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (ModelThucAn food : foodList) {
            Object[] rowData = {
                food.getIdThucAn(),
                food.getTenThucAn(),
                food.getLoaiThucAn(),
                food.getIdDongVat(),
                food.getSoLuong(),
                food.getHanSuDung(),
                food.getIdNCC()
            };
            tableModel.addRow(rowData);
        }

        return tableModel;
    }

    private void addthucan() {
        // Nhận dữ liệu bao gồm đường dẫn hình ảnh từ các trường văn bản
        String ID = view.getIdField().getText();
        String name = view.getNameField().getText();
        String type = view.getTypeField().getText();
        String animalId = view.getAnimalIdField().getText();
        String quantity = view.getQuantityField().getText();
        String expiry = view.getExpiryField().getText();
        String supplier = view.getSupplierField().getText();

        if (!name.isEmpty() && !type.isEmpty() && !animalId.isEmpty()
                && !quantity.isEmpty() && !expiry.isEmpty() && !supplier.isEmpty()) {
            try {
                String sql = "INSERT INTO thucan (TenThucAn, LoaiThucAn, IdDongVat, SoLuong, HanThucAn, IdNCC) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, type);
                statement.setString(3, animalId);
                statement.setString(4, quantity);
                statement.setString(5, expiry);
                statement.setString(6, supplier); // Sửa lỗi index 7 thành 6

                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("A new food item was inserted successfully!");
                    fillData(""); // Làm mới bảng với dữ liệu đã cập nhật
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Xử lý bất kỳ ngoại lệ liên quan đến cơ sở dữ liệu ở đây
            }
        } else {
            System.out.println("Please fill in all the fields.");
        }
    }

    private void updatethucan() {
        JTable table = view.getTable();
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // Lấy dữ liệu từ hàng được chọn và đưa vào các trường nhập liệu để chỉnh sửa
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    view.getIdField().setText(table.getValueAt(selectedRow, 0).toString());
                    view.getNameField().setText(table.getValueAt(selectedRow, 1).toString());
                    view.getTypeField().setText(table.getValueAt(selectedRow, 2).toString());
                    view.getAnimalIdField().setText(table.getValueAt(selectedRow, 3).toString());
                    view.getQuantityField().setText(table.getValueAt(selectedRow, 4).toString());
                    view.getExpiryField().setText(table.getValueAt(selectedRow, 5).toString());
                    view.getSupplierField().setText(table.getValueAt(selectedRow, 7).toString());

                    // Cập nhật trạng thái của các button (nếu cần)
                    // Ví dụ: Ẩn/Hiển thị button theo trạng thái dữ liệu được chọn
                }
            }
        });

        try {
            // Kiểm tra xem tất cả các trường có được điền đầy đủ hay không
            String ID = view.getIdField().getText();
            String name = view.getNameField().getText();
            String type = view.getTypeField().getText();
            String animalId = view.getAnimalIdField().getText();
            String quantity = view.getQuantityField().getText();
            String expiry = view.getExpiryField().getText();
            String supplier = view.getSupplierField().getText();

            if (!ID.isEmpty() && !name.isEmpty() && !type.isEmpty() && !animalId.isEmpty()
                    && !quantity.isEmpty() && !expiry.isEmpty() && !supplier.isEmpty()) {
                String sql = "UPDATE thucan SET TenThucAn=?, LoaiThucAn=?,IdDongVat=?, SoLuong=?, HanThucAn=?, IdNCC=? WHERE IDThucAn=?";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, name);
                statement.setString(2, type);
                statement.setString(3, animalId);
                statement.setString(4, quantity);
                statement.setString(5, expiry);
                statement.setString(6, supplier);
                statement.setString(7, ID);

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Food item updated successfully!");
                    fillData(""); // Refresh the table with updated data
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
            String sql = "SELECT * FROM thucan WHERE TenThucAn LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%"); // Search for partial matches

            ResultSet resultSet = statement.executeQuery();
            ArrayList<ModelThucAn> searchResults = new ArrayList<ModelThucAn>();

            while (resultSet.next()) {
                ModelThucAn food = new ModelThucAn(
                        resultSet.getInt("IDThucAn"),
                        resultSet.getString("TenThucAn"),
                        resultSet.getString("LoaiThucAn"),
                        resultSet.getInt("IDDongVat"),
                        resultSet.getInt("SoLuong"),
                        resultSet.getString("HanThucAN"),
                        resultSet.getInt("IdNCC")
                );
                searchResults.add(food);
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
            String sql = "DELETE FROM thucan WHERE IDThucAn=?";
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
            String excelFilePath = "‪thucan.xlsx"; // Example file path
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

    private void statisticsByQuantity() {
        try {
            String sql = "SELECT TenThucAn, SUM(SoLuong) AS TotalQuantity FROM thucan GROUP BY TenThucAn";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel statisticsTableModel = new DefaultTableModel(new String[]{"Name", "Total Quantity"}, 0);

            while (resultSet.next()) {
                String tenThucAn = resultSet.getString("TenThucAn");
                int totalQuantity = resultSet.getInt("TotalQuantity");
                statisticsTableModel.addRow(new Object[]{tenThucAn, totalQuantity});
            }

            // Update the JTable in the view with the statistical data
            view.getTable().setModel(statisticsTableModel);

        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle any exceptions related to SQL queries here
        }
    }
}
