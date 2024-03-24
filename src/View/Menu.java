package View;

import Controller.ControllerChuongTrai;
import Controller.ControllerDongVat;
import Controller.ControllerNCC;
import Controller.ControllerThongKeDongVat;
import Controller.ControllerVe;
import Controller.Controllerthucan;
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
        JMenuItem quanLyChuongTrai = new JMenuItem("Quản lý chuồng trại");
        JMenuItem quanLyThucAn = new JMenuItem("Quản lý thức ăn");
        JMenuItem quanLyNCC = new JMenuItem("Quản lý nhà cung cấp");

        quanLy.add(quanLyDongVat);
        quanLy.add(quanLyVe);
        quanLy.add(quanLyChuongTrai);
        quanLy.add(quanLyThucAn);
        quanLy.add(quanLyNCC);
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
        quanLyChuongTrai.addActionListener((e) -> {
            frame.dispose();
            ViewChuongTrai v = new ViewChuongTrai();
            ControllerChuongTrai c = new ControllerChuongTrai(v);
        });
        quanLyThucAn.addActionListener((e) -> {
            frame.dispose();
            ViewThucAn view = new ViewThucAn();
            Controllerthucan control = new Controllerthucan(view);
        });
        quanLyNCC.addActionListener((e) -> {
            frame.dispose();
            ViewNCC view = new ViewNCC();
            ControllerNCC control = new ControllerNCC(view);
        });

        thongKeDongVat.addActionListener((e) -> {
            frame.dispose();
            new ControllerThongKeDongVat();
        });

        quanLy.setFont(font);
        quanLyDongVat.setFont(font);
        quanLyVe.setFont(font);
        quanLyChuongTrai.setFont(font);
        quanLyThucAn.setFont(font);
        quanLyNCC.setFont(font);

        thongKe.setFont(font);
        thongKeDongVat.setFont(font);
    }
}
