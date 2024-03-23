/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Controller.ControllerDongVat;
import View.ViewDongVat;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import raven.swing.spinner.SpinnerProgress;

/**
 *
 * @author ACER
 */
public class Main {

    public static void main(String[] args) {
        FlatLightLaf.setup();

        if (!isApiAvailable(Utils.Utility.apiString)) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến máy chủ");
        } else {
            ViewDongVat v = new ViewDongVat();
            ControllerDongVat c = new ControllerDongVat(v);
        }
    }

    public static boolean isApiAvailable(String apiUrl) {
        try {
//            JOptionPane.showMessageDialog(null, "Đang tải");
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");

            int responseCode = connection.getResponseCode();

            connection.disconnect();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false;
        }
    }
}
