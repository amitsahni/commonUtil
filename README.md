------
### CommonUtil 
#### Available Utils:

* [AnimationUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/AnimationUtil.java)
* [CollectionUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/CollectionUtil.java)
* [ColorUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/ColorUtil.java)
* [DateUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/DateUtil.java)
* [FileUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/FileUtil.java)
* [LogUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/LogUtil.java)
* [MathUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/MathUtil.java)
* [SecurityUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/SecurityUtil.java)
* [ShareUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/ShareUtil.java)
* [SystemUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/SystemUtil.java)
* [ValidatorUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/ValidatorUtil.java)
* [VoiceUtil](https://github.com/amitsahni/commonUtil/blob/dev/util/src/main/java/com/util/categories/VoiceUtil.java)

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
		compile 'com.github.amitsahni:commonUtil:{latest version}'
	}
```
