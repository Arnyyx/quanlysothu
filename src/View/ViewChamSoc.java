package View;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class ViewChamSoc extends JFrame {

    private JLabel label = new JLabel("CHĂM SÓC ĐỘNG VẬT", SwingConstants.CENTER);
    private JLabel labelSLChamSoc = new JLabel();

    private String[] lableStrings = {"Mã", "Tên động vật", "Loại chăm sóc", "Ngày chăm sóc", "Tên nhân viên", "Kết quả"};

    private JLabel lbID = new JLabel(lableStrings[0]);
    private JLabel lbTenDongVat = new JLabel(lableStrings[1]);
    private JLabel lbLoaiChamSoc = new JLabel(lableStrings[2]);
    private JLabel lbNgayChamSoc = new JLabel(lableStrings[3]);
    private JLabel lbTenNhanVien = new JLabel(lableStrings[4]);
    private JLabel lbKetQua = new JLabel(lableStrings[5]);

    // Text field
    private JTextField tfID = new JTextField(20);
    private JComboBox<String> tfTenDongVat = new JComboBox();
    private JComboBox<String> tfLoaiChamSoc = new JComboBox();
    private JDateChooser tfNgayChamSoc = new JDateChooser();
    private JTextField tfTenNhanVien = new JTextField(20);
    private JTextField tfKetQua = new JTextField(20);
    private JTextField tfTimKiem = new JTextField(20);
    // Button
    private JButton btnAdd = new JButton("Thêm");
    private JButton btnDelete = new JButton("Xoá");
    private JButton btnSave = new JButton("Lưu");
    private JButton btnSearch = new JButton("Tìm kiếm");
    private JButton btnNhap = new JButton("Nhập dữ liệu");
    private JButton btnXuat = new JButton("Xuất dữ liệu");
    private JButton btnLoad = new JButton("Làm mới");

    // Table
    private JTable table = new JTable();
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ViewChamSoc() {
        setTitle("Chăm sóc động vật");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Menu(this);
        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel chứa thông tin động vật
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        table.getTableHeader().setReorderingAllowed(false);
        tfID.setEditable(false);
        tfLoaiChamSoc.setEditable(true);
        tfNgayChamSoc.setDateFormatString("dd/MM/yyyy");
        tfNgayChamSoc.setMaxSelectableDate(Date.from(Instant.now()));
        JTextFieldDateEditor editor = (JTextFieldDateEditor) tfNgayChamSoc.getDateEditor();
        editor.setEditable(false);
        table.setAutoCreateRowSorter(true);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(label, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lbID, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lbTenDongVat, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfTenDongVat, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lbLoaiChamSoc, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfLoaiChamSoc, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(lbNgayChamSoc, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfNgayChamSoc, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(lbTenNhanVien, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfTenNhanVien, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(lbKetQua, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfKetQua, gbc);

        JPanel buttonPanelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelSouth.add(labelSLChamSoc);
        buttonPanelSouth.add(btnAdd);
        buttonPanelSouth.add(btnDelete);
        buttonPanelSouth.add(btnSave);
        buttonPanelSouth.add(btnNhap);
        buttonPanelSouth.add(btnXuat);

        JPanel buttonPanelNorth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelNorth.add(btnLoad);
        buttonPanelNorth.add(btnSearch);
        buttonPanelNorth.add(tfTimKiem);

        // Thêm các panel vào split pane
        splitPane.setLeftComponent(tablePanel);
        splitPane.setRightComponent(inputPanel);
        splitPane.setResizeWeight(0.7);

        // Sắp xếp split pane
        add(buttonPanelNorth, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanelSouth, BorderLayout.SOUTH);

//         Font chữ
//        Font font = new Font("Arial", Font.PLAIN, 14);
        Font font = UIManager.getFont("large.font");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        lbID.setFont(font);
        lbTenDongVat.setFont(font);
        lbLoaiChamSoc.setFont(font);
        lbNgayChamSoc.setFont(font);
        lbTenNhanVien.setFont(font);
        lbKetQua.setFont(font);

        tfID.setFont(font);
        tfTenDongVat.setFont(font);
        tfLoaiChamSoc.setFont(font);
        tfNgayChamSoc.setFont(font);
        tfTenNhanVien.setFont(font);
        tfKetQua.setFont(font);

        tfTimKiem.setFont(font);
        btnAdd.setFont(font);
        btnDelete.setFont(font);
        btnSave.setFont(font);
        btnSearch.setFont(font);
        btnNhap.setFont(font);
        btnXuat.setFont(font);
        btnLoad.setFont(font);
        labelSLChamSoc.setFont(font);

        tableModel.addColumn(lableStrings[0]);
        tableModel.addColumn(lableStrings[1]);
        tableModel.addColumn(lableStrings[2]);
        tableModel.addColumn(lableStrings[3]);
        tableModel.addColumn(lableStrings[4]);
        tableModel.addColumn(lableStrings[5]);
        table.setFont(font);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTextField getTfID() {
        return tfID;
    }

    public JComboBox<String> getTfTenDongVat() {
        return tfTenDongVat;
    }

    public JComboBox<String> getTfLoaiChamSoc() {
        return tfLoaiChamSoc;
    }

    public JDateChooser getTfNgayChamSoc() {
        return tfNgayChamSoc;
    }

    public JTextField getTfKetQua() {
        return tfKetQua;
    }

    public JTextField getTfTimKiem() {
        return tfTimKiem;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnNhap() {
        return btnNhap;
    }

    public JButton getBtnXuat() {
        return btnXuat;
    }

    public JButton getBtnLoad() {
        return btnLoad;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getTfTenNhanVien() {
        return tfTenNhanVien;
    }

    public void setLabelSLChamSoc(String text) {
        this.labelSLChamSoc.setText(text);
    }

}
