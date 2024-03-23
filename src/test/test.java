package test;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import raven.swing.spinner.SpinnerProgress;

public class test extends JFrame {

    private JLabel label = new JLabel("THÔNG TIN ĐỘNG VẬT", SwingConstants.CENTER);

    private JLabel lbID = new JLabel("Mã:");
    private JLabel lbTen = new JLabel("Tên:");
    private JLabel lbLoai = new JLabel("Loài:");
    private JLabel lbTuoi = new JLabel("Tuổi:");
    private JLabel lbGioiTinh = new JLabel("Giới tính:");

    // Text field
    private JTextField tfID = new JTextField(20);
    private JTextField tfTen = new JTextField(20);
    private JTextField tfLoai = new JTextField(20);
    private JTextField tfTuoi = new JTextField(20);
    private JTextField tfGioiTinh = new JTextField(20);
    private JTextField tfTimKiem = new JTextField(20);

    // Button
    private JButton btnAdd = new JButton("Thêm");
    private JButton btnDelete = new JButton("Xoá");
    private JButton btnSave = new JButton("Lưu");
    private JButton btnTimKiem = new JButton("Tìm kiếm");
    private JButton btnNhap = new JButton("Nhập dữ liệu");
    private JButton btnXuat = new JButton("Xuất dữ liệu");
    private JButton btnLoad = new JButton("Làm mới");

    // Table
    private JTable table = new JTable();
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    public static void main(String[] args) {
        new test();
    }

    public test() {
//        FlatLightLaf.setup();
        setTitle("QUẢN LÝ SỞ THÚ");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new View.Menu(this);

        SpinnerProgress spinner = new SpinnerProgress();
        spinner.setIndeterminate(true);

        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel chứa thông tin động vật
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Thêm label "Quản lý động vật" vào panel, căn giữa
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Đặt chiều rộng của label bằng 2 cột
        gbc.anchor = GridBagConstraints.CENTER; // Đặt anchor để label căn giữa
        inputPanel.add(label, gbc);

        // Reset các giá trị của ràng buộc
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;

        // Thêm các label và text field vào panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lbID, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lbTen, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfTen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lbLoai, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfLoai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(lbTuoi, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfTuoi, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(lbGioiTinh, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfGioiTinh, gbc);

        // Panel chứa button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnTimKiem);
        buttonPanel.add(tfTimKiem);

        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel2.add(btnLoad);
        buttonPanel2.add(btnNhap);
        buttonPanel2.add(btnXuat);

        // Thêm các panel vào split pane
        splitPane.setLeftComponent(tablePanel);
        splitPane.setRightComponent(inputPanel);

        // Sắp xếp split pane
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(buttonPanel2, BorderLayout.NORTH);

        // Font chữ
        Font font = new Font("Arial", Font.PLAIN, 14);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        lbID.setFont(font);
        lbTen.setFont(font);
        lbLoai.setFont(font);
        lbTuoi.setFont(font);
        lbGioiTinh.setFont(font);
        tfID.setFont(font);
        tfTen.setFont(font);
        tfLoai.setFont(font);
        tfTuoi.setFont(font);
        tfGioiTinh.setFont(font);
        tfTimKiem.setFont(font);
        btnAdd.setFont(font);
        btnDelete.setFont(font);
        btnSave.setFont(font);
        btnTimKiem.setFont(font);
        btnNhap.setFont(font);
        btnXuat.setFont(font);
        btnLoad.setFont(font);

        btnLoad.add(spinner);
        
                setVisible(true);
    }
}
