package test.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.util.categories.DateUtil;
import com.util.categories.LogUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        long milli = 1518900350000L;
        Date date3 = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTime(date3);
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        cal.setTime(date3);
        System.out.println(cal.getTime());
        LogUtil.w("CurrentDateLocal = " + cal.getTime());
        LogUtil.w("CurrentDateUTC = " + DateUtil.convertToUTCDate(date3));
        LogUtil.w("Date = " + DateUtil.convertMillisecondToUTC("dd-MM-yyyy", calendar.getTime().getTime()));
        String date = DateUtil.formatDate(calendar.getTime().getTime(), "dd-MM-yyyy HH:mm:ss");
        date3 = DateUtil.parseDate(date, "dd-MM-yyyy HH:mm:ss");
        Locale locale = new Locale("ar");
        date = DateUtil.formatDate(date3.getTime(), "dd-MMM-yyyy HH:mm:ss", TimeZone.getDefault(), locale);
        LogUtil.w("CurrentDateUTC = " + date3.toString());
        LogUtil.w("CurrentDateUTC = " + date);
        date3 = DateUtil.parseDate(date, "dd-MMM-yyyy HH:mm:ss", locale);
        LogUtil.w("CurrentDateUTC = " + date3.toString());

    }
}
