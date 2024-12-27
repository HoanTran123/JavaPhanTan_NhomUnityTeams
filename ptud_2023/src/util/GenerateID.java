/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author LOC
 */
public class GenerateID {
    private static String lastDate = "";
    private static int counter;

    public GenerateID() {

    }

    public static String dateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String datePart = dateFormat.format(new Date());
        return datePart;
    }

    public static String sinhMa(String ten) {
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmss");
        String maPhatSinh = dateFormat.format(dateNow);
        // + random number 0- 1000
        int random = (int) (Math.random() * 1000);

        return ten + maPhatSinh + random;
    }
}
