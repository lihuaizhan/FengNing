package com.example.tps900.tps900.updata;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.tps900.tps900.R;
import com.example.tps900.tps900.Utlis.BaseHelper;
import com.example.tps900.tps900.Utlis.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadVersionService extends Service {

    private static final int NOTIFY_DOW_ID = 0;
    private static final int NOTIFY_OK_ID = 1;

    private Context mContext = this;
    private boolean cancelled;
    private int progress;
    private NotificationManager mNotificationManager;
    private Notification.Builder mNotification;
    private DownloadBinder binder = new DownloadBinder();

    // UpdateVesion updateVersion = null;

    private String serverUrl = ""; // 安装包下载地址
    private final String fileName = "环企手持逸云版"; // 显示的文件名称
    private final String apkName = "环企手持逸云版.apk";// 下载文件的名称

    private int fileSize; // 文件大小
    private int readSize; // 读取长度
    private int downSize; // 已下载大小
    private File downFile; // 下载的文件

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    // 更新进度
                    RemoteViews contentView = mNotification.build().contentView;
                    contentView.setTextViewText(R.id.rate, (readSize < 0 ? 0
                            : readSize) + "b/s   " + msg.arg1 + "%");
                    contentView.setProgressBar(R.id.progress, 100, msg.arg1, false);

                    // 更新UI
                    mNotificationManager.notify(NOTIFY_DOW_ID, mNotification.build());
                    break;
                case 1:
                    mNotificationManager.cancel(NOTIFY_DOW_ID);
                    createNotification(NOTIFY_OK_ID);

				/* 打开文件进行安装 */
                    openFile(downFile);
                    break;
                case 2:
                    mNotificationManager.cancel(NOTIFY_DOW_ID);
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private Handler handMessage = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(mContext, "服务器连接失败，请稍后再试！", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case 1:
                    Toast.makeText(mContext, "服务器端文件不存在，下载失败！", Toast.LENGTH_SHORT)
                            .show();
                    break;
                default:
                    break;
            }

            handler.sendEmptyMessage(2);
        }
    };

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (null == intent) {
            return;
        }
        serverUrl = intent.getStringExtra("updateURL");
        Log.e("文件是----------", serverUrl);
        mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        cancelled = true;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // 返回自定义的DownloadBinder实例
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelled = true; // 取消下载线程
    }

    /**
     * 创建通知
     */
    @SuppressWarnings("deprecation")
    private void createNotification(int notifyId) {
        switch (notifyId) {
            case NOTIFY_DOW_ID:
                mNotification = new Notification.Builder(this)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.appname).  setTicker(fileName + "开始下载");

                // 放置在"正在运行"栏目中
                mNotification.build().flags = Notification.FLAG_ONGOING_EVENT;

                RemoteViews contentView = new RemoteViews(
                        mContext.getPackageName(),
                        R.layout.download_notification_layout);
                contentView.setTextViewText(R.id.fileName, "正在下载" + fileName);

                // 指定个性化视图
                mNotification.setContent(contentView);

                PendingIntent contentIntent = PendingIntent.getActivity(mContext,
                        0, new Intent(), 0);
                // 指定内容意图
                mNotification.setContentIntent(contentIntent);
                Log.e("走了开始下载", "指定内容意图");
                break;
            case NOTIFY_OK_ID:
                mNotification = new Notification.Builder(this)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.appname).setTicker("下载完毕");
                // 放置在"通知形"栏目中
                mNotification.build().flags = Notification.FLAG_AUTO_CANCEL;

                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);

                // 调用getMIMEType()来取得MimeType
                String type = getMIMEType(downFile);
                // 设定intent的file与MimeType
                intent.setDataAndType(Uri.fromFile(downFile), type);
                PendingIntent contentInten = PendingIntent.getActivity(mContext,
                        0, intent, 0);
                mNotification.setContentIntent(contentInten)
                        .setTicker(fileName)
                        .build();
                stopSelf();// 停掉服务自身
                Log.e("走了开始下载", "下载完毕");
                cancelled = true;
                break;
            default:
                break;
        }

        // 最后别忘了通知一下,否则不会更新
        mNotificationManager.notify(notifyId, mNotification.build());
    }

    /**
     * 下载模块
     */
    private void startDownload() {

        // 初始化数据
        fileSize = 0;
        readSize = 0;
        downSize = 0;
        progress = 0;

        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL myURL = new URL(serverUrl); // 取得URL
            URLConnection conn = myURL.openConnection(); // 建立联机
            conn.connect();
            fileSize = conn.getContentLength(); // 获取文件长度
            is = conn.getInputStream(); // InputStream 下载文件
            if (is == null) {
                throw new RuntimeException("stream is null");
            }

            String downloadPath = FileUtil.getDirPath("download")
                    + File.separator;
            Log.e("downloadPath", downloadPath);
            if (Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED)) {// 判断是否有sd卡
                downFile = new File(downloadPath + apkName);
            } else {
                // 未挂载sd卡，保存至手机内置存储空间
                String apkPath = downloadPath + apkName;
                // 修改apk权限
                BaseHelper.chmod("777", apkPath);
                downFile = new File(apkPath);
            }
            // 将文件写入临时盘
            fos = new FileOutputStream(downFile);
            byte[] buf = new byte[1024 * 1024];
            while (!cancelled && (readSize = is.read(buf)) > 0) {
                fos.write(buf, 0, readSize);
                downSize += readSize;

                sendMessage(0);
            }

            if (cancelled) {
                handler.sendEmptyMessage(2);
                downFile.delete();
            } else {
                handler.sendEmptyMessage(1);
            }
        } catch (MalformedURLException e) {
            handMessage.sendEmptyMessage(0);
        } catch (IOException e) {
            Log.e("异常----->", e.getMessage().toString());
            handMessage.sendEmptyMessage(1);
        } catch (Exception e) {
            handMessage.sendEmptyMessage(0);
        } finally {
            try {
                if (null != fos) {
                    fos.close();
                }
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(int what) {
        int num = (int) ((double) downSize / (double) fileSize * 100);

        if (num > progress + 1) {
            progress = num;

            Message msg0 = handler.obtainMessage();
            msg0.what = what;
            msg0.arg1 = progress;
            handler.sendMessage(msg0);
        }
    }

    // 在手机上打开文件的method
    private void openFile(File f) {
        mNotificationManager.cancel(NOTIFY_OK_ID);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);

        // 调用getMIMEType()来取得MimeType
        String type = getMIMEType(f);
        // 设定intent的file与MimeType
        intent.setDataAndType(Uri.fromFile(f), type);
        startActivity(intent);
    }

    // 判断文件MimeType的method
    private String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        // 取得扩展名
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();

        // 按扩展名的类型决定MimeType
        if ("m4a".equals(end) || "mp3".equals(end) || "mid".equals(end)
                || "xmf".equals(end) || "ogg".equals(end) || "wav".equals(end)) {
            type = "audio";
        } else if ("3gp".equals(end) || "mp4".equals(end)) {
            type = "video";
        } else if ("jpg".equals(end) || "gif".equals(end) || "png".equals(end)
                || "jpeg".equals(end) || "bmp".equals(end)) {
            type = "image";
        } else if ("apk".equals(end)) {
            // android.permission.INSTALL_PACKAGES
            type = "application/vnd.android.package-archive";
        } else {
            type = "*";
        }
        // 如果无法直接打开，就跳出软件清单给使用者选择
        if (!"apk".equals(end)) {
            type += "/*";
        }

        return type;
    }

    /**
     * DownloadBinder中定义了一些实用的方法
     *
     * @author user
     */
    public class DownloadBinder extends Binder {
        /**
         * 开始
         */
        public void start() {
            cancelled = false;
            new Thread() {
                @Override
                public void run() {
                    createNotification(NOTIFY_DOW_ID);
                    startDownload(); // 下载
                    cancelled = true;
                }

                ;
            }.start();
        }

        /**
         * 获取进度
         *
         * @return
         */
        public int getProgress() {
            return progress;
        }

        /**
         * 取消下载
         */
        public void cancel() {
            cancelled = true;
        }

        /**
         * 是否已被取消
         *
         * @return
         */
        public boolean isCancelled() {
            return cancelled;
        }

    }

}
