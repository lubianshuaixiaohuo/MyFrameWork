/*
 * Copyright (C) 2013 KeSiCloud
 * FileName: NetUtil
 * Describe: It is a utility tool class for network. it includes some functions
 * of NetWork in order to easy to use.
 * Author: XiuMingHui
 */
package com.baseframe.utils;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;

import java.util.Locale;

public class ConnectionUtil
{
    public static enum ConnectionStatus
    {
        Connection_Null, Connection_Disabled, 
        Connection_WiFi, Connection_2G_Wap,
        Connection_3G_Wap, Connection_2G_Net, Connection_3G_Net,
        Connection_Lost, Connection_Fine
    }

    public static ConnectionStatus m_ConnectionStatus = ConnectionStatus.Connection_Null;

    public static Uri PREFERRED_APN_URI  = Uri.parse ("content://telephony/carriers/preferapn");

    public static final String CTWAP              = "ctwap";
    public static final String CTNET              = "ctnet";
    public static final String CMWAP              = "cmwap";
    public static final String CMNET              = "cmnet";
    public static final String NET_3G             = "3gnet";
    public static final String WAP_3G             = "3gwap";
    public static final String UNIWAP             = "uniwap";
    public static final String UNINET             = "uninet";

    public static final int        TYPE_CT_WAP        = 5;
    public static final int        TYPE_CT_NET        = 6;
    public static final int        TYPE_CT_WAP_2G     = 7;
    public static final int        TYPE_CT_NET_2G     = 8;

    public static final int        TYPE_CM_WAP        = 9;
    public static final int        TYPE_CM_NET        = 10;
    public static final int        TYPE_CM_WAP_2G     = 11;
    public static final int        TYPE_CM_NET_2G     = 12;

    public static final int        TYPE_CU_WAP        = 13;
    public static final int        TYPE_CU_NET        = 14;
    public static final int        TYPE_CU_WAP_2G     = 15;
    public static final int        TYPE_CU_NET_2G     = 16;

    public static final int        TYPE_OTHER         = 17;

    public static void checkNetworkType( Context mContext )
    {
        try
        {
            final ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo aMobNetInfo = connectivityManager.getActiveNetworkInfo();
            if (aMobNetInfo == null || !aMobNetInfo.isAvailable())
            {
                m_ConnectionStatus = ConnectionStatus.Connection_Disabled;
            }
            else
            {
                // NetworkInfo不为null开始判断是网络类型
                int netType = aMobNetInfo.getType();
                if (netType == ConnectivityManager.TYPE_WIFI)
                {
                    // wifi net处理
                    m_ConnectionStatus = ConnectionStatus.Connection_WiFi;
                }
                else if (netType == ConnectivityManager.TYPE_MOBILE)
                {
                    // 注意二：
                    // 判断是否电信wap:
                    // 不要通过getExtraInfo获取接入点名称来判断类型，
                    // 因为通过目前电信多种机型测试发现接入点名称大都为#777或者null，
                    // 电信机器wap接入点中要比移动联通wap接入点多设置一个用户名和密码,
                    // 所以可以通过这个进行判断！

                    boolean is3G = isFastMobileNetwork(mContext);

                    /*
                     * final Cursor c = mContext.getContentResolver().query(
                     * PREFERRED_APN_URI, null, null, null, null);
                     */
                    /*
                     * if (c != null)
                     * {
                     * c.moveToFirst();
                     * final String user = c.getString(c
                     * .getColumnIndex("user"));
                     * if (!TextUtils.isEmpty(user))
                     * {
                     * if (user.startsWith(CTWAP))
                     * {
                     * m_ConnectionStatus = is3G ?ConnectionStatus.Connection_3G_Wap :
                     * ConnectionStatus.Connection_2G_Wap;
                     * }
                     * else if (user.startsWith(CTNET))
                     * {
                     * m_ConnectionStatus =is3G ?ConnectionStatus.Connection_3G_Net :
                     * ConnectionStatus.Connection_2G_Net;
                     * }
                     * }
                     * }
                     * c.close();
                     */

                    // 注意三：
                    // 判断是移动联通wap:
                    // 其实还有一种方法通过getString(c.getColumnIndex("proxy")获取代理ip
                    // 来判断接入点，10.0.0.172就是移动联通wap，10.0.0.200就是电信wap，但在
                    // 实际开发中并不是所有机器都能获取到接入点代理信息，例如魅族M9 （2.2）等...
                    // 所以采用getExtraInfo获取接入点名字进行判断

                    String netMode = aMobNetInfo.getExtraInfo();
                    if (netMode != null)
                    {
                        // 通过apn名称判断是否是联通和移动wap
                        netMode = netMode.toLowerCase(Locale.US);

                        if (netMode.equals(CMWAP))
                        {
                            m_ConnectionStatus = is3G ? ConnectionStatus.Connection_3G_Wap
                                    : ConnectionStatus.Connection_2G_Wap;
                        }
                        else if (netMode.equals(CMNET))
                        {
                            m_ConnectionStatus = is3G ? ConnectionStatus.Connection_3G_Net
                                    : ConnectionStatus.Connection_2G_Net;
                        }
                        else if (netMode.equals(NET_3G) || netMode.equals(UNINET))
                        {
                            m_ConnectionStatus = is3G ? ConnectionStatus.Connection_3G_Net
                                    : ConnectionStatus.Connection_2G_Net;
                        }
                        else if (netMode.equals(WAP_3G) || netMode.equals(UNIWAP))
                        {
                            m_ConnectionStatus = is3G ? ConnectionStatus.Connection_3G_Wap
                                    : ConnectionStatus.Connection_2G_Wap;
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            m_ConnectionStatus = ConnectionStatus.Connection_Null;
        }
    }

    private static boolean isFastMobileNetwork( Context context )
    {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType())
        {
        case TelephonyManager.NETWORK_TYPE_1xRTT:
            return false; // ~ 50-100 kbps
        case TelephonyManager.NETWORK_TYPE_CDMA:
            return false; // ~ 14-64 kbps
        case TelephonyManager.NETWORK_TYPE_EDGE:
            return false; // ~ 50-100 kbps
        case TelephonyManager.NETWORK_TYPE_EVDO_0:
            return true; // ~ 400-1000 kbps
        case TelephonyManager.NETWORK_TYPE_EVDO_A:
            return true; // ~ 600-1400 kbps
        case TelephonyManager.NETWORK_TYPE_GPRS:
            return false; // ~ 100 kbps
        case TelephonyManager.NETWORK_TYPE_HSDPA:
            return true; // ~ 2-14 Mbps
        case TelephonyManager.NETWORK_TYPE_HSPA:
            return true; // ~ 700-1700 kbps
        case TelephonyManager.NETWORK_TYPE_HSUPA:
            return true; // ~ 1-23 Mbps
        case TelephonyManager.NETWORK_TYPE_UMTS:
            return true; // ~ 400-7000 kbps
            // case TelephonyManager.NETWORK_TYPE_EHRPD:
            // return true; // ~ 1-2 Mbps
        case TelephonyManager.NETWORK_TYPE_EVDO_B:
            return true; // ~ 5 Mbps
            // case TelephonyManager.NETWORK_TYPE_HSPAP:
            // return true; // ~ 10-20 Mbps
        case TelephonyManager.NETWORK_TYPE_IDEN:
            return false; // ~25 kbps
            // case TelephonyManager.NETWORK_TYPE_LTE:
            // return true; // ~ 10+ Mbps
        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
            return false;
        default:
            return false;

        }
    }

    /**
     * Verify the connection availability.
     *
     * @parameter a context.
     */
    public static boolean checkNet( Context aContext )
    {
        try
        {
            ConnectivityManager localConnectivityManager = (ConnectivityManager) aContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (localConnectivityManager != null)
            {
                NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
                if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
                {
                    NetworkInfo.State aState = localNetworkInfo.getState();
                    if (aState == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            logUtil.d ("network is not available !!!");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verify the WiFi availability.
     *
     * @parameter a context.
     */
    public static boolean isWiFiActive( Context aContext )
    {
        ConnectivityManager localConnectivityManager = (ConnectivityManager) aContext
                .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mWifi = localConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi != null && mWifi.isConnected())
            return true;
        return false;
    }

    public static final boolean isOPen( final Context context )
    {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network)
        {
            return true;
        }
        return false;
    }

    /**
     * 强制帮用户打开GPS
     * 
     * @param context
     */
    public static final void openGPS( Context context )
    {
        Intent GPSIntent = new Intent();
        GPSIntent
                .setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse ("custom:3"));
        try
        {
            PendingIntent.getBroadcast (context, 0, GPSIntent, 0).send();
        }
        catch (CanceledException e)
        {
            e.printStackTrace();
        }
    }

}
