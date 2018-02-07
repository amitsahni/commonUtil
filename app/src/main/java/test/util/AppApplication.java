package test.util;

import android.support.multidex.MultiDexApplication;

import net.danlew.android.joda.JodaTimeAndroid;

//import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by clickapps on 6/2/18.
 */

public class AppApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
