/*
 * Copyright (C) 2013 KeSiCloud
 * FileName: LogUtil
 * Describe: It is a utility tool class for log. you can view the tracks both in
 * "console" and "file".
 * Author: XiuMingHui
 */
package com.baseframe.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * need permission:
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 **/

public class logUtil
{
    // switch of debug
    public static final boolean LOG_OPEN_DEBUG = true;
    // switch of print
    public static final boolean LOG_OPEN_FILE  = false;
    // file path
    public static final String PROJCET_NAME   = "BlueBee";
    public static String tag            = "BlueBee";

    private static final String PATH_ROOT      = Environment
                                                       .getExternalStorageDirectory()
                                                       + "/" + PROJCET_NAME;
    private static final String PATH_LOG_INFO  = PATH_ROOT + "info/";

    // for beginning of functions
    public static void funStart ()
    {
        if (LOG_OPEN_DEBUG)
        {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            Log.d(tag, "--" + traceElement + "-- start -->");
    }
    }

    // for ending of functions
    public static void funEnd ()
    {
        if (LOG_OPEN_DEBUG)
        {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            Log.d(tag, "<--" + traceElement + "-- End --");
        }
    }

    // Debug message
    public static void d( final String message )
    {
        if (message != null && message != null)
        {
            if (LOG_OPEN_DEBUG)
            {
                Log.d(tag, "--" + message + "-->");
            }
            if (LOG_OPEN_FILE)
            {
                print(PATH_LOG_INFO, tag, message);
            }
        }
    }
    
    public static void e( final String message )
    {
        if (message != null && message != null)
        {
            if (LOG_OPEN_DEBUG)
            {
                Log.e (tag, "--" + message + "-->");
            }
            if (LOG_OPEN_FILE)
            {
                print(PATH_LOG_INFO, tag, message);
            }
        }
    }

    // file log
    public static void print(final String path, final String tag,
                             final String msg )
    {
        if(!LOG_OPEN_FILE)
        {
            return;
        }
        
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("",
                Locale.SIMPLIFIED_CHINESE);
        dateFormat.applyPattern("yyyy");

        StringBuffer apath = new StringBuffer(path);
        apath.append(dateFormat.format(date));
        apath.append("/");

        dateFormat.applyPattern("MM");

        apath.append(dateFormat.format(date));
        apath.append("/");
        dateFormat.applyPattern("dd");

        apath.append(dateFormat.format(date));
        apath.append(".log");
        dateFormat.applyPattern("[yyyy-MM-dd HH:mm:ss]");

        String time = dateFormat.format(date);
        File file = new File(apath.toString());

        if (!file.exists())
            createDipPath(apath.toString());

        BufferedWriter out = null;
        try
        {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(time + " " + tag + " " + msg + "\r\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    // create path for file in order to write logs here.
    public static void createDipPath( String file )
    {
        String parentFile = file.substring(0, file.lastIndexOf("/"));
        File afile = new File(file);
        File parent = new File(parentFile);
        if (!afile.exists())
        {
            parent.mkdirs();
            try
            {
                afile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void d(String tag, String msg )
    {
        if (LOG_OPEN_DEBUG)
            android.util.Log.d(tag, msg);
    }

    public static void e(String tag, String msg )
    {
        if (LOG_OPEN_DEBUG)
            android.util.Log.e(tag, msg);
    }

    public static void d(String tag, Object obj )
    {
        if (LOG_OPEN_DEBUG)
        {
            String msg = obj.toString();
            android.util.Log.d(tag, msg);
        }
    }
}
