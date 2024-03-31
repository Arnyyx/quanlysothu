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
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author ACER
 */
public class Utility {

//    public static String apiString = "https://quan-ly-tu.onrender.com/";
    public static String apiString = "http://localhost:8000/";

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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

    public static Date addInstant(Date date) {
        //Add hours to date
        Instant instant = Instant.now();
        Date currentDate = Date.from(instant);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, currentDate.getHours());
        calendar.add(Calendar.MINUTE, currentDate.getMinutes());
        calendar.add(Calendar.SECOND, currentDate.getSeconds());

        Date finalDate = calendar.getTime();

        return finalDate;
    }

}
