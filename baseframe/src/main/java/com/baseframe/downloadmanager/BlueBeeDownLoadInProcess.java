package com.baseframe.downloadmanager;


public abstract interface BlueBeeDownLoadInProcess
{
    public abstract void DownLoadProgress
            (final BlueBeeDownloadManager.DownLoadUpdateParm aDownLoadUpdateParm);
}
