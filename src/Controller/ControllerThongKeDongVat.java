/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.ModelDongVat;
import View.ViewThongKeDongVat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ACER
 */
public class ControllerThongKeDongVat {

    ViewThongKeDongVat view;
    ControllerDongVat controllerDongVat;
    ArrayList<ModelDongVat> listDongVat;
    JFreeChart chart;

    public ControllerThongKeDongVat() {
        view = new ViewThongKeDongVat();
        controllerDongVat = new ControllerDongVat();

        controllerDongVat.getListDongVat(new ControllerDongVat.OnGetListDongVatListener() {
            @Override
            public void onSuccess(ArrayList<ModelDongVat> dongVats) {
                listDongVat = dongVats;
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFailure() {
            }
        });

        view.getBtnThongKeTheoLoai().addActionListener((e) -> {
            thongKeTheoLoai();
            view.getBtnXuat().setEnabled(true);
        });
        view.getBtnThongKeTheoTrangThai().addActionListener((e) -> {
            thongKeTheoTrangThai();
            view.getBtnXuat().setEnabled(true);
        });
        view.getBtnThongKeTheoTuoi().addActionListener((e) -> {
            thongKeTheoTuoi();
            view.getBtnXuat().setEnabled(true);
        });
        view.getBtnXuat().addActionListener((e) -> {
            Utils.XuLyPDF.export(chart);
        });
    }

    public void thongKeTheoLoai() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Integer> thongKeLoaiDongVat = new HashMap<>();
        for (ModelDongVat dongVat : listDongVat) {
            String loaiDongVat = dongVat.getLoaiDongVat();
            if (thongKeLoaiDongVat.containsKey(loaiDongVat)) {
                thongKeLoaiDongVat.put(loaiDongVat, thongKeLoaiDongVat.get(loaiDongVat) + 1);
            } else {
                thongKeLoaiDongVat.put(loaiDongVat, 1);
            }
        }

        for (Map.Entry<String, Integer> entry : thongKeLoaiDongVat.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        chart = ChartFactory.createPieChart("Biểu đồ cơ cấu loài trong sở thú", dataset, false, true, true);
        view.getLabel().setText("Tổng số động vật: " + listDongVat.size());
        view.getBieuDoPanel().setChart(chart);
        view.repaint();

    }

    public void thongKeTheoTrangThai() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        Map<String, Integer> thongKeTrangThaiDongVat = new HashMap<>();
        for (ModelDongVat dongVat : listDongVat) {
            String trangThaiDongVat = dongVat.getTrangThaiDongVat();
            if (thongKeTrangThaiDongVat.containsKey(trangThaiDongVat)) {
                thongKeTrangThaiDongVat.put(trangThaiDongVat, thongKeTrangThaiDongVat.get(trangThaiDongVat) + 1);
            } else {
                thongKeTrangThaiDongVat.put(trangThaiDongVat, 1);
            }
        }

        for (Map.Entry<String, Integer> entry : thongKeTrangThaiDongVat.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        chart = ChartFactory.createPieChart("Biểu đồ cơ cấu sức khoẻ động vật ", dataset, false, true, true);
        view.getLabel().setText("Tổng số động vật: " + listDongVat.size());
        view.getBieuDoPanel().setChart(chart);
        view.repaint();
    }

    public void thongKeTheoTuoi() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<Integer, Integer> thongKeTuoiDongVat = new HashMap<>();
        for (ModelDongVat dongVat : listDongVat) {
            int tuoiDongVat = dongVat.getTuoiDongVat();
            if (thongKeTuoiDongVat.containsKey(tuoiDongVat)) {
                thongKeTuoiDongVat.put(tuoiDongVat, thongKeTuoiDongVat.get(tuoiDongVat) + 1);
            } else {
                thongKeTuoiDongVat.put(tuoiDongVat, 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : thongKeTuoiDongVat.entrySet()) {
            dataset.setValue(entry.getValue(), "Tuổi", entry.getKey());
        }

         chart = ChartFactory.createBarChart(
                "Biểu đồ thể hiện độ tuổi động vật",
                "Tuổi", "Con",
                dataset, PlotOrientation.VERTICAL, false, false, false);

        view.getLabel().setText("Tổng số động vật: " + listDongVat.size());
        view.getBieuDoPanel().setChart(chart);
        view.repaint();
    }

}
