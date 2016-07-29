package com.baseframe.downloadmanager;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BlueBeeDownloadManager
{
    private static BlueBeeDownloadManager m_XiNiaoDownloadManager;

    private ExecutorService m_NewService;
    private final int                       THREAD_COUNT    = 3;

    private int                             m_ThreadSize;
    private final int                       TERMINATIONTIME = 60;

    public static boolean                   m_Pause;
    public static boolean                   m_Stop;


    private BlueBeeDownloadManager()
    {

        int aProcessorCount = Runtime.getRuntime ().availableProcessors();
        m_ThreadSize = aProcessorCount * THREAD_COUNT;


        m_NewService = Executors.newFixedThreadPool (m_ThreadSize);
    }

    public static BlueBeeDownloadManager GetInstance()
    {
        if (m_XiNiaoDownloadManager == null)
        {
            m_XiNiaoDownloadManager = new BlueBeeDownloadManager();
        }

        return m_XiNiaoDownloadManager;
    }

    public void Destroy()
    {
        m_Stop = true;
        try
        {
            m_NewService.shutdownNow();
            m_NewService.awaitTermination(TERMINATIONTIME, TimeUnit.SECONDS);
        }
        catch (Exception ie)
        {
            // (Re-)Cancel if current thread also interrupted
            m_NewService.shutdownNow();
            Thread.currentThread ().interrupt();
        }

        finally
        {
            m_XiNiaoDownloadManager = null;
        }
    }

    public void SubmitDownloadTask(
            final BlueBeeDownLoadTask.DownLoadTaskParms aDownLoadTaskParms )
    {
        BlueBeeDownLoadTask aTempTask = new BlueBeeDownLoadTask(
                aDownLoadTaskParms);
        
        m_NewService.submit(aTempTask);
    }

    // ------------------------------------------------------------------------------
    public static enum DownLoadType
    {
        DLT_PICTURE, DLT_PACKAGE
    }

    public static enum DownLoadOperation
    {
        DLO_NORMAL, DLO_COVER
    }

    public static enum DownLoadResultCode
    {
        DRC_FINISH, DRC_PAUSE, DRC_STOP, DRC_ALREADY_EXIST, DRC_TIMEOUT, DownLoadResultCode, DRC_CONNTECTIONFILED
    }

    public static enum DownLoadEvent
    {
        DEV_HOMEPAGEUPDATEPRO, DEV_APPUPDATEPRO, DEV_IconDownload
    }

    public static class DownloadReult
    {
        public DownLoadResultCode m_ResultCode;
        public String m_ResultInfo;
        public Object m_ResultObject;
        public Object m_UIObject;
        public int                m_What;
        public int                m_MatchArg;
        public String m_MatchArg2;
    }

    public static class DownLoadUpdateParm
    {
        public int    m_What;
        public int    m_MatchArg;
        public String m_MatchArg2;
        public long   m_ProgressValue;
        public int    m_TotSize;
    }

}
