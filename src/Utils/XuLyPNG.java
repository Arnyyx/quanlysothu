/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.HeadlessException;
import javax.swing.JFileChooser;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author ACER
 */
public class XuLyPNG {

    public static void export(JFreeChart chart) {
        try {

            JFileChooser chooser = new MyFileChooser("export/");
            chooser.setDialogTitle("Lưu vào");
            FileNameExtensionFilter fnef = new FileNameExtensionFilter("PNG Files", "png");
            chooser.setFileFilter(fnef);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getPath();
                if (!path.contains(".png")) {
                    path += ".png";
                }
                FileOutputStream fileOut = new FileOutputStream(path);

                ChartUtilities.writeChartAsPNG(fileOut, chart, 612, 468);

                JOptionPane.showMessageDialog(null, "Xuất file thành công");
            }
        } catch (HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(null, "Xuất file thất bại");
        }
    }

}
