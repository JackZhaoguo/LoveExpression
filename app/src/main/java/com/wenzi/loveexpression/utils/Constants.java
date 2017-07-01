package com.wenzi.loveexpression.utils;


public class Constants {
    public static final String CHECK_ACTIVE_VIEW_UPDATE_URL = "https://upush.meizu.com/pluginupgrade/check";
    public static final String UPGRADE_ACTIVE_JAR_FILE_NAME = "NewActiveView.jar";
    public static final String UPGRADE_ACTIVE_JAR_TEMP_FILE_NAME = "new_active_view_temp.jar";
    public static final String REQUEST_MODEL_NAME = "All";

    //根据是否为国际版本选择服务器域名
    public static final String JSON_KEY_DEVICE_TYPE = "deviceType";
    public static final String JSON_KEY_FW = "firmware";
    public static final String JSON_KEY_SYSTEM_V = "sysVer";
    public static final String JSON_KEY_IMEI = "imei";
    public static final String JSON_KEY_DEVICE_ID = "deviceId";
    public static final String JSON_KEY_SN = "sn";
    public static final String JSON_KEY_SERVICE = "services";
    public static final String JSON_KEY_SERVICE_NAME = "serviceName";
    public static final String JSON_KEY_VERSION = "version";
    public static final String JSON_KEY_VERSION_CODE = "versionCode";
    public static final String JSON_KEY_TARGET_APP_NAME = "targetAppName";

    public static final String MD5_SIGN_KEY_STRING = "2635881a7ab0593849fe89e685fc56cd";

    public static final String PARAM_APPS = "apps";
    public static final String PARAM_SIGN = "sign";
    public static final String PARAM_UNIT_TYPE = "unitType";

    public static final int RESPONSE_CODE_SUCCESS = 200;

    //产品类型
    public static final int UNIT_TYPE_PHONE = 0;
    public static final int UNIT_TYPE_TV = 1;
    public static final int UNIT_TYPE_TABLET = 2;

    //设备设别符
    public static final String DEVCIE_TABLET = "tablet";
    public static final String DEVICE_TV = "box";
    //无效的VersionConde
    public static final int INVALID_VERSION_CODE = -1;

    public static final String ACTIVE_PACKAGE_NAME = "com.meizu.flyme.activeview";
    public static final int ACTIVE_PLUGIN_TYPE = 1;//插件类型 类型:0表示so,1表示jar,2表示apk,3表示rar,4表示dex,5表示zip,6表示otherr


    public static final String JSON_FILE = "data.json";
    public static final String ACTIVE_CACHE_DIRECTORY = "activecache";

    // if use local data.json and resources, they must be put in to "assets/activeres/" directory.  ex. assets/activeres/data.json, assets/activeres/images/bg_0.png.
    public static final String ACTIVE_DIRECTORY_IN_ASSETS_PREFIXX = "assets://";

    public static final String ACTIVE_VIEW_IMPLEMENT_CLASS = "com.meizu.flyme.activeview.views.ActiveViewImpl";
    public static final String ACTIVE_VIEW_VERSION_CLASS = "com.meizu.flyme.activeview.version.Version";
    public static final String ACTIVE_VIEW_VERSION_FILD = "VERSION";

    public static final String UTF_8_CODE = "UTF-8";
    public static final String LIMIT_DATE = "limit_date";
    //24*60*60*1000*3
    public static final int THREE_DAY_TIME = 259200000;

    // md5 sign
    public static final String MD5SUM = "md5sum";
    public static final String MD5SUM_TMP = "md5sum_tmp";

    // networking
    public static final String USER_AGENT_MEIZU = "MEIZU";
    public static final int RESPONSE_CODE_OK = 200;
    public static final int RESPONSE_CODE_PARTIAL = 206;

    public static final int RESPONSE_CODE_RELOCATE = 301;
    public static final int RESPONSE_CODE_TEMP_RELOCATION = 302;
    public static final int RESPONSE_CODE_OVER_RANGE = 416;
    // timeout 20s
    public static final int HTTP_CONNECT_TIMEOUT = 20000;

    //定时器超时时间,应用可以根据的自己的业务情况进行设置，这个值是全局统一使用
    public static  int FILE_TASK_TIMEOUT = 10000;
    public static  int UPGRADE_TASK_TIMEOUT = 5000;
    //下载过程中，尝试2次，如果2次都下载失败，不再尝试
    public static final int HTTP_TRY_CONNECT_COUNT = 2;

}