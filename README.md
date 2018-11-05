------
### CommonUtil 
#### Available Utils:
```
AnimationUtil
CollectionUtil
ColorUtil
DateUtil
FileUtil
LogUtil
MathUtil
SecurityUtil
ShareUtil
SystemUtil
ValidatorUtil
VoiceUtil
```
#### How to use
```
Date date3 = new Date(System.currentTimeMillis());
Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
calendar.setTime(date3);
GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
cal.setTime(date3);
	
LogUtil.w("CurrentDateTimeLocal = " + cal.getTime());
LogUtil.w("CurrentDateTimeUTC = " + DateUtil.convertToUTCDate(date3));
LogUtil.w("CurrentDateTimeUTCUsingMillisecond = " + DateUtil.convertMillisecondToUTC("dd-MMM-yyyy HH:mm:ss", calendar.getTime().getTime()));
```

#### Download
--------
Add the JitPack repository to your root build.gradle

```
	allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}
```
Add the Gradle dependency:
```
	dependencies {
		compile 'com.github.amitsahni:commonUtil:latestVersion'
	}
```
