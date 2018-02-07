package test.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.util.categories.DateUtil;
import com.util.categories.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        LogUtil.w("Date = " + cal.getTime());
        LogUtil.w("Date = " + DateUtil.convertToUTCDate(date3));
        SimpleDateFormat sdf = DateUtil.getDateFormat("dd-MM-yyyy HH:mm:ss");
        String text = sdf.format(new Date(DateUtil.convertToUTCDate(date3).getTime()));
        LogUtil.w("UTCDate = " + text);
        LogUtil.w("CurrentUTC = " + DateUtil.getCurrentUTC());
        LogUtil.w("Date = " + DateUtil.convertMillisecondToUTC("dd-MM-yyyy", calendar.getTime().getTime()));


    }
}
