package View;

import Controller.ControllerChamSoc;
import Controller.ControllerChuongTrai;
import Controller.ControllerDongVat;
import Controller.ControllerNCC;
import Controller.ControllerNhanVien;
import Controller.ControllerTaiKhoan;
import Controller.ControllerThongKeDongVat;
import Controller.ControllerVe;
import Controller.Controllerthucan;
import Controller.ControllerLoaiVe;

import Main.Main;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu {

    public static String quyen = null;

    public Menu(JFrame frame) {
        Font font = Utils.Utility.getFont();

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Tạo menu
        JMenu quanLy = new JMenu("Quản lý");
        JMenuItem quanLyTaiKhoan = new JMenuItem("Quản lý tài khoản");
        JMenuItem quanLyNhanVien = new JMenuItem("Quản lý nhân viên");
        JMenuItem quanLyDongVat = new JMenuItem("Quản lý động vật");
        JMenuItem chamSocDongVat = new JMenuItem("Chăm sóc động vật");
        JMenuItem quanLyVe = new JMenuItem("Quản lý vé");
        JMenuItem quanLyLoaiVe = new JMenuItem("Quản lý loại vé");
        JMenuItem quanLyChuongTrai = new JMenuItem("Quản lý chuồng trại");
        JMenuItem quanLyThucAn = new JMenuItem("Quản lý thức ăn");
        JMenuItem quanLyNCC = new JMenuItem("Quản lý nhà cung cấp");

        JMenuItem dangXuat = new JMenuItem("Đăng xuất");

        if (quyen.equals("QL")) {
            quanLy.add(quanLyTaiKhoan);
            quanLy.add(quanLyNhanVien);
        }

        quanLy.add(quanLyDongVat);
        quanLy.add(chamSocDongVat);
        quanLy.add(quanLyVe);
        quanLy.add(quanLyLoaiVe);

        quanLy.add(quanLyChuongTrai);
        quanLy.add(quanLyThucAn);
        quanLy.add(quanLyNCC);
        quanLy.add(dangXuat);
        menuBar.add(quanLy);

        JMenu thongKe = new JMenu("Thống kê");
        JMenuItem thongKeDongVat = new JMenuItem("Thống kê động vật");
        menuBar.add(thongKe);
        thongKe.add(thongKeDongVat);

        quanLyTaiKhoan.addActionListener((e) -> {
            frame.dispose();
            ViewTaiKhoan v = new ViewTaiKhoan();
            new ControllerTaiKhoan(v);
        });
        quanLyNhanVien.addActionListener((e) -> {
            frame.dispose();
            ViewNhanVien v = new ViewNhanVien();
            new ControllerNhanVien(v);
        });
        quanLyDongVat.addActionListener((e) -> {
            frame.dispose();
            ViewDongVat v = new ViewDongVat();
            new ControllerDongVat(v);
        });
        chamSocDongVat.addActionListener((e) -> {
            frame.dispose();
            ViewChamSoc v = new ViewChamSoc();
            new ControllerChamSoc(v);
        });
        quanLyVe.addActionListener((e) -> {
            frame.dispose();
            ViewVe v = new ViewVe();
            new ControllerVe(v);
        });
        quanLyLoaiVe.addActionListener((e) -> {
            frame.dispose();
            ViewLoaiVe v = new ViewLoaiVe();
            new ControllerLoaiVe(v);
        });
        quanLyChuongTrai.addActionListener((e) -> {
            frame.dispose();
            ViewChuongTrai v = new ViewChuongTrai();
            new ControllerChuongTrai(v);
        });
        quanLyThucAn.addActionListener((e) -> {
            frame.dispose();
            ViewThucAn view = new ViewThucAn();
            new Controllerthucan(view);
        });
        quanLyNCC.addActionListener((e) -> {
            frame.dispose();
            ViewNCC view = new ViewNCC();
            new ControllerNCC(view);
        });
        dangXuat.addActionListener((e) -> {
            frame.dispose();
            new ViewDangNhap();
        });

        thongKeDongVat.addActionListener((e) -> {
            frame.dispose();
            new ControllerThongKeDongVat();
        });

        quanLy.setFont(font);
        quanLyTaiKhoan.setFont(font);
        quanLyNhanVien.setFont(font);
        quanLyDongVat.setFont(font);
        chamSocDongVat.setFont(font);
        quanLyVe.setFont(font);
        quanLyLoaiVe.setFont(font);
        quanLyChuongTrai.setFont(font);
        quanLyThucAn.setFont(font);
        quanLyNCC.setFont(font);
        dangXuat.setFont(font);

        thongKe.setFont(font);
        thongKeDongVat.setFont(font);
    }
}
