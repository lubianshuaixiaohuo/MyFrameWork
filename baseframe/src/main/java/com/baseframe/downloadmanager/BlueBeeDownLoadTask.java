package com.baseframe.downloadmanager;

import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


//import android.util.Log;

public class BlueBeeDownLoadTask implements Runnable
{
    public static class DownLoadTaskParms
    {
        // -- some variables.
        public String m_Url;
        public String m_SavePath;
        public String m_FileName;
        public boolean      m_NeedCache;
        public boolean      m_NeedReturn;
        public boolean      m_stop;
        public Object m_Return;
        public Object m_UIObject;
        public BlueBeeDownloadManager.DownLoadType m_DownLoadType;
        
        //-- set Obs
        public BlueBeeDownLoadFinished m_FinishOB;
        public BlueBeeDownLoadInProcess m_ProcessOB;
        
        // --define event for itself
        public int          m_What;
        public int          m_MatchArg;
        public String m_MatchArg2;
        // -----
    }

    public DownLoadTaskParms              m_DownLoadTaskParms;

    public BlueBeeDownLoadTask(final DownLoadTaskParms aDownLoadTaskParms)
    {
        m_DownLoadTaskParms = aDownLoadTaskParms;
    }

    private int GetContentLength()
    {
        int aTotSize = 0;
        try
        {
            //Log.e("info","sssssssssssssssssssssss");
            URL url = new URL(m_DownLoadTaskParms.m_Url);
            HttpURLConnection httpurl = (HttpURLConnection) url
                    .openConnection();
            httpurl.setConnectTimeout(5000);
            httpurl.setRequestMethod("GET");
            httpurl.setRequestProperty("Accept", "*/*");
            httpurl.setRequestProperty("Accept-Language", "zh-CN");
            httpurl.setRequestProperty("Charset", "UTF-8");
            httpurl.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; "
                            + ".NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; "
                            + ".NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            httpurl.setRequestProperty("Connection", "Keep-Alive");
//            httpurl.setRequestProperty("Accept-Encoding", "identity");

            httpurl.connect();
            if (httpurl.getResponseCode() == 200)
            {
                aTotSize = httpurl.getContentLength();
                if (aTotSize <= 0)
                {
                    return -1;
                }
            }
            else
            {

                return -1;
            }
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
        return aTotSize;
    }

    @Override
    public void run()
    {
        //降低后台线程的优先级，有效避免ANR
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        BlueBeeDownloadManager.DownloadReult aResult = new BlueBeeDownloadManager.DownloadReult ();

        // --Http operations
        try
        {
            if (BlueBeeDownloadManager.m_Stop||m_DownLoadTaskParms.m_stop)
            {
                aResult.m_ResultCode = BlueBeeDownloadManager.DownLoadResultCode.DRC_STOP;
                aResult.m_What = m_DownLoadTaskParms.m_What;
                aResult.m_MatchArg = m_DownLoadTaskParms.m_MatchArg;
                aResult.m_MatchArg2 = m_DownLoadTaskParms.m_MatchArg2;
                aResult.m_ResultInfo = "The Task was canceled!";
                return;
            }

            File aDir = new File(m_DownLoadTaskParms.m_SavePath);
            if (!aDir.exists())
            {
                aDir.mkdirs();
            }

            // if it is picture,it's name will be converted.
            String aTempFileName = null;
            if (m_DownLoadTaskParms.m_DownLoadType == BlueBeeDownloadManager.DownLoadType.DLT_PICTURE)
            {
               /* aTempFileName = UriUtil
                        .getNameFromUri (m_DownLoadTaskParms.m_Url);*/

            }
            else
            {
                aTempFileName = m_DownLoadTaskParms.m_FileName;
            }

            // firstly,we should find it in local.
            File aFileName = new File(m_DownLoadTaskParms.m_SavePath,
                    aTempFileName);
            File atempFileName = new File(m_DownLoadTaskParms.m_SavePath,
                    aTempFileName + ".tmp");

            if (aFileName.exists())
            {
                // already exist
                if (m_DownLoadTaskParms.m_NeedCache)
                {
                    aResult.m_ResultCode = BlueBeeDownloadManager.DownLoadResultCode.DRC_ALREADY_EXIST;
                    aResult.m_What = m_DownLoadTaskParms.m_What;
                    aResult.m_MatchArg = m_DownLoadTaskParms.m_MatchArg;
                    aResult.m_MatchArg2 = m_DownLoadTaskParms.m_MatchArg2;
                    aResult.m_ResultInfo = "The file already exist";
                    aResult.m_UIObject = m_DownLoadTaskParms.m_UIObject;

                    // if download type is picture,we should load it and return
                    // it.
                    if (m_DownLoadTaskParms.m_DownLoadType ==
                            BlueBeeDownloadManager.DownLoadType.DLT_PICTURE)
                    {
                        FileInputStream is = new FileInputStream(aFileName);
                        Drawable drawable = Drawable.createFromStream (is, "");

                        if (drawable != null)
                        {
                            aResult.m_ResultObject = drawable;
                        }
                    }
                    // notify the observer.
                    if (m_DownLoadTaskParms.m_FinishOB != null)
                        m_DownLoadTaskParms.m_FinishOB.DownLoadFinished(aResult);
                    return;
                }
                else
                {
                    aFileName.delete();
                }
            }
            // -- start download.
            long aCurrentSize = 0;
            // if the temp file exist,it means we should continue to down load
            // it.
            if (!atempFileName.exists())
            {
                atempFileName.createNewFile();
            }
            else
                aCurrentSize = atempFileName.length();
             //Log.e("info","长度"+GetContentLength());
            int aTotSize = GetContentLength();
            URL url = new URL(m_DownLoadTaskParms.m_Url);
            HttpURLConnection httpurl = (HttpURLConnection) url
                    .openConnection();
            httpurl.setConnectTimeout(5000);
            httpurl.setRequestMethod("GET");
            httpurl.setRequestProperty("Accept", "*/*");
            httpurl.setRequestProperty("Accept-Language", "zh-CN");
            httpurl.setRequestProperty("Charset", "UTF-8");
//            httpurl.setRequestProperty("Accept-Encoding", "identity");
            httpurl.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; "
                            + ".NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; "
                            + ".NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");

            httpurl.setRequestProperty("Connection", "Keep-Alive");

            if(aTotSize == -1 /*|| aCurrentSize>aTotSize*/ )
            {
                aResult.m_ResultCode = BlueBeeDownloadManager.DownLoadResultCode.DRC_CONNTECTIONFILED;
                aResult.m_ResultInfo = "The Total Size < CurrentSize Wrong !!";
                aResult.m_What = m_DownLoadTaskParms.m_What;
                aResult.m_MatchArg = m_DownLoadTaskParms.m_MatchArg;
                aResult.m_MatchArg2 = m_DownLoadTaskParms.m_MatchArg2;
                aResult.m_UIObject = m_DownLoadTaskParms.m_UIObject;
                aResult.m_ResultInfo = "file size is -1 from server";
                // notify the observer.
                if (m_DownLoadTaskParms.m_FinishOB != null)
                    m_DownLoadTaskParms.m_FinishOB.DownLoadFinished(aResult);
                return;
            }
            //------
            if( aCurrentSize>aTotSize)
            {
                atempFileName.delete ();
                aCurrentSize = 0;
                atempFileName.createNewFile();
            }
            //------
            if (aCurrentSize != 0)
            {
                httpurl.setRequestProperty("Range", "bytes=" + aCurrentSize
                        + "-" + aTotSize);
            }
            httpurl.connect();

            int httpcode = httpurl.getResponseCode();
            //Log.e("info","返回码"+httpurl.getResponseCode());
            if (httpcode == 200 || httpcode == 206)
            {
                InputStream inStream = httpurl.getInputStream();
              //  BufferedInputStream inStream = new BufferedInputStream(httpurl.getInputStream());
                byte[] buffer = new byte[1024];
                int offset = 0;

                RandomAccessFile threadfile = new RandomAccessFile(
                        atempFileName, "rwd");

                if (aCurrentSize != 0)
                    threadfile.seek(aCurrentSize);

                while ((offset = inStream.read(buffer, 0, 1024)) != -1)
                {
                    //Log.e("info","---"+httpurl.getContentLength());
                    if (BlueBeeDownloadManager.m_Stop||m_DownLoadTaskParms.m_stop)
                    {
                        aResult.m_ResultCode = BlueBeeDownloadManager.DownLoadResultCode.DRC_PAUSE;
                        aResult.m_What = m_DownLoadTaskParms.m_What;
                        aResult.m_MatchArg = m_DownLoadTaskParms.m_MatchArg;
                        aResult.m_MatchArg2 = m_DownLoadTaskParms.m_MatchArg2;
                        aResult.m_ResultInfo = "The Task was canceled!";

                        if (m_DownLoadTaskParms.m_FinishOB != null)
                            m_DownLoadTaskParms.m_FinishOB.DownLoadFinished(aResult);
                        
                        threadfile.close();
                        return;
                    }

                    threadfile.write(buffer, 0, offset);
                    aCurrentSize += offset;

                    if (m_DownLoadTaskParms.m_ProcessOB != null)
                    {
                        BlueBeeDownloadManager.DownLoadUpdateParm aTempDLP =
                                new BlueBeeDownloadManager.DownLoadUpdateParm ();
                        aTempDLP.m_TotSize = aTotSize;
                        aTempDLP.m_ProgressValue = aCurrentSize;
                        aTempDLP.m_What = m_DownLoadTaskParms.m_What;
                        aTempDLP.m_MatchArg = m_DownLoadTaskParms.m_MatchArg;
                        aTempDLP.m_MatchArg2 = m_DownLoadTaskParms.m_MatchArg2;
                        m_DownLoadTaskParms.m_ProcessOB.DownLoadProgress(aTempDLP);
                    }

                }
                threadfile.close();
                inStream.close();
            }
            
            
            else
            {
                aResult.m_ResultCode = BlueBeeDownloadManager.DownLoadResultCode.DRC_CONNTECTIONFILED;
                aResult.m_ResultInfo = "The response was Wrong + : " + httpcode;
                aResult.m_What = m_DownLoadTaskParms.m_What;
                aResult.m_MatchArg = m_DownLoadTaskParms.m_MatchArg;
                aResult.m_MatchArg2 = m_DownLoadTaskParms.m_MatchArg2;
                aResult.m_UIObject = m_DownLoadTaskParms.m_UIObject;
                
                // notify the observer.
                if (m_DownLoadTaskParms.m_FinishOB != null)
                    m_DownLoadTaskParms.m_FinishOB.DownLoadFinished(aResult);
                
                return;
            }

            if (aTotSize == aCurrentSize)
            {
                atempFileName.renameTo(aFileName);
            }
            
            // deal with image logic
            if (m_DownLoadTaskParms.m_DownLoadType == BlueBeeDownloadManager.DownLoadType.DLT_PICTURE)
            {
                FileInputStream is = new FileInputStream(aFileName);
                Drawable drawable = Drawable.createFromStream (is, "");
                if (drawable != null)
                {
                    aResult.m_ResultObject = drawable;
                }
            }
            
            aResult.m_ResultCode = BlueBeeDownloadManager.DownLoadResultCode.DRC_FINISH;
            aResult.m_ResultInfo = "The task was complete !!";
            aResult.m_What = m_DownLoadTaskParms.m_What;
            aResult.m_MatchArg = m_DownLoadTaskParms.m_MatchArg;
            aResult.m_MatchArg2 = m_DownLoadTaskParms.m_MatchArg2;
            aResult.m_UIObject = m_DownLoadTaskParms.m_UIObject;
            
            if( m_DownLoadTaskParms.m_FinishOB != null)
            {
                m_DownLoadTaskParms.m_FinishOB.DownLoadFinished(aResult);
            }
        }
        
        catch (Exception e)
        {

            e.printStackTrace();
        }
    }
    
    public abstract interface DownLoadFinish
    {
        public void DownLoadFinished(final BlueBeeDownloadManager.DownloadReult aDownloadReult);
    }

}
