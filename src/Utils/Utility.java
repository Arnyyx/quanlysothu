/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.ArrayList;
import Model.ModelDongVat;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Font;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author ACER
 */
public class Utility {

    public static String apiString = "https://quan-ly-tu.onrender.com/";
//    public static String apiString = "http://localhost:8000/";

    public static void changLNF(String nameLNF) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (nameLNF.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println("Utils.Utility.changLNF()" + ex);
        }
    }

    public static Font getFont() {
        return UIManager.getFont("large.font");
    }

    public static ArrayList getArray(String json) {
        ArrayList<ModelDongVat> list = new ArrayList<>();
        Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            ModelDongVat some = gson.fromJson(entry.getValue().getAsJsonObject(), ModelDongVat.class);
            list.add(some);
        }
        return list;
    }

    public static void hideColumn(JTable table, int columnIndex) {
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn column = columnModel.getColumn(columnIndex);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setResizable(false);
    }

}
