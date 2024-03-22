/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author ACER
 */
public class ViewThongKeDongVat extends JFrame {

    private JButton btnThongKeTheoLoai = new JButton("Thống kê theo loài");
    private JButton btnThongKeTheoTuoi = new JButton("Thống kê theo tuổi");
    private JButton btnThongKeTheoTrangThai = new JButton("Thống kê theo trạng thái");
    private JLabel label = new JLabel("Hãy chọn một loại biểu đồ");
    private ChartPanel bieuDoPanel = new ChartPanel(null);

    public ViewThongKeDongVat() {
        setTitle("Thống kê động vật");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Menu(this);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(label);
        panel.add(btnThongKeTheoLoai);
        panel.add(btnThongKeTheoTuoi);
        panel.add(btnThongKeTheoTrangThai);

        bieuDoPanel.setBackground(Color.WHITE);
        bieuDoPanel.setPreferredSize(new Dimension(200, 200));

        mainPanel.add(bieuDoPanel, BorderLayout.CENTER);
        mainPanel.add(panel, BorderLayout.NORTH);

        add(mainPanel);

        Font font = new Font("Arial", Font.PLAIN, 14);
        label.setFont(font);
        btnThongKeTheoLoai.setFont(font);
        btnThongKeTheoTuoi.setFont(font);
        btnThongKeTheoTrangThai.setFont(font);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton getBtnThongKeTheoLoai() {
        return btnThongKeTheoLoai;
    }

    public JButton getBtnThongKeTheoTuoi() {
        return btnThongKeTheoTuoi;
    }

    public JButton getBtnThongKeTheoTrangThai() {
        return btnThongKeTheoTrangThai;
    }

    public JLabel getLabel() {
        return label;
    }

    public ChartPanel getBieuDoPanel() {
        return bieuDoPanel;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }
    
    
}
