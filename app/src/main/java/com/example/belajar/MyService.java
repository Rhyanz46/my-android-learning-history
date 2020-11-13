package com.example.belajar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyService extends Service implements Runnable {

    private static final int NOTIFICATION_ID = 1;

    private boolean mRunning = false;
    private Thread mThread;

    private Socket mSocket;
    private InputStream mInputStream;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setNotificationMessage("Service created");

        if (mThread == null) {
            mRunning = true;

            mThread = new Thread(this);
            mThread.start();
        }
    }

    @Override
    public void run() {
        try {
            while (mRunning) {
                try {
                    setNotificationMessage("Connecting");

                    mSocket = new Socket();
                    mSocket.connect(new InetSocketAddress("192.168.56.1", 9899));

                    mInputStream = mSocket.getInputStream();

                    setNotificationMessage("Connected");

                    for (int c = mInputStream.read(); c > -1; c = mInputStream.read()) {
                        setNotificationMessage("Connected: " + (char) c);
                    }
                } catch (UnknownHostException ignored) {
                    setNotificationMessage("Unknown host");
                } catch (IOException ignored) {
                    setNotificationMessage("Disconnected");
                    close();
                }

                try {
                    // Reconnect delay
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        } finally {
            // Will eventually call onDestroy()
            stopSelf();
        }
    }

    private void setNotificationMessage(CharSequence message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentTitle("Connection status");
        builder.setContentText(message);

        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        nm.notify(NOTIFICATION_ID, builder.build());
    }

    private void close() {
        if (mInputStream != null) {
            try {
                mInputStream.close();
                mInputStream = null;
            } catch (IOException ignored) {
            }
        }

        if (mSocket != null) {
            try {
                mSocket.close();
                mSocket = null;
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mThread != null) {
            mRunning = false;

            close();

            while (true) {
                try {
                    mThread.interrupt();
                    mThread.join();
                    mThread = null;
                    break;
                } catch (InterruptedException ignored) {
                }
            }
        }

        setNotificationMessage("Service destroyed");

        super.onDestroy();
    }
}