package test.util;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.util.categories.DateUtil;
import com.util.categories.LogUtil;
import com.util.categories.ValidatorUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    int rotation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        long milli = 1518900350000L;
        Date date3 = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTime(date3);
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        cal.setTime(date3);
        LogUtil.w("CurrentDateTimeLocal = " + cal.getTime());
        LogUtil.w("CurrentDateTimeUTC = " + DateUtil.convertToUTCDate(date3));
        LogUtil.w("CurrentDateTimeUTCUsingMillisecond = " + DateUtil.convertMillisecondToUTC("dd-MMM-yyyy HH:mm:ss", calendar.getTime().getTime()));
        String date = DateUtil.formatDate(calendar.getTime().getTime(), "dd-MM-yyyy HH:mm:ss");
        date3 = DateUtil.parseDate(date, "dd-MM-yyyy HH:mm:ss");
        Locale locale = new Locale("ar");
        date = DateUtil.formatDate(date3.getTime(), "dd-MMM-yyyy HH:mm:ss", TimeZone.getDefault(), locale);
        LogUtil.w("CurrentDateArabicLocal = " + date);
        date = DateUtil.formatDate(date3.getTime(), "dd-MMM-yyyy HH:mm:ss", TimeZone.getTimeZone("GMT"), locale);
        LogUtil.w("CurrentDateArabicUTC = " + date);

        date = DateUtil.formatDate(DateUtil.convertToUTCDate(date3).getTime(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LogUtil.w("CurrentDateUTC = " + date);
        date3 = DateUtil.parseDate(date, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", TimeZone.getTimeZone("GMT"));
        LogUtil.w("CurrentDateLocal = " + date3);
        View view = findViewById(R.id.mini_progress);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                rotation += 90;
                ViewCompat.animate(v)
                        .rotation(rotation)
                        .start();

            }
        });

        ValidatorUtil.isJSONValid("{message:'This is a message', version: {num: 10}}");


    }
}
