/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Controller.ControllerDongVat;
import View.ViewDongVat;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class Main {

    public static void main(String[] args) {
        FlatLightLaf.setup();

        ViewDongVat v = new ViewDongVat();
        ControllerDongVat c = new ControllerDongVat(v);
    }

    public static boolean checkConnect() {
        try {
            String apiUrl = "http://localhost:8000/";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.disconnect();
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến máy chủ");
            return false;
        }
    }
}
