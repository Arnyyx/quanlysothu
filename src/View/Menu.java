package View;

import Controller.ControllerDongVat;
import Controller.ControllerThongKeDongVat;
import Controller.ControllerVe;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu {

    public Menu(JFrame frame) {
        // Tạo menu bar
        Font font = Utils.Utility.getFont();
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Tạo menu
        JMenu quanLy = new JMenu("Quản lý");
        JMenuItem quanLyDongVat = new JMenuItem("Quản lý động vật");
        JMenuItem quanLyVe = new JMenuItem("Quản lý vé");

        quanLy.add(quanLyDongVat);
        quanLy.add(quanLyVe);
        menuBar.add(quanLy);

        JMenu thongKe = new JMenu("Thống kê");
        JMenuItem thongKeDongVat = new JMenuItem("Thống kê động vật");
        menuBar.add(thongKe);
        thongKe.add(thongKeDongVat);

        quanLyDongVat.addActionListener((e) -> {
            frame.dispose();
            ViewDongVat v = new ViewDongVat();
            ControllerDongVat c = new ControllerDongVat(v);
        });
        quanLyVe.addActionListener((e) -> {
            frame.dispose();
            ViewVe v = new ViewVe();
            ControllerVe c = new ControllerVe(v);
        });

        thongKeDongVat.addActionListener((e) -> {
            frame.dispose();
            new ControllerThongKeDongVat();
        });

        quanLy.setFont(font);
        quanLyVe.setFont(font);
        thongKe.setFont(font);
        quanLyDongVat.setFont(font);
        thongKeDongVat.setFont(font);
    }
}
