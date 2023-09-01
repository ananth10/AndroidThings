package com.ananth.androidthings.foregroundservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.ananth.androidthings.R

class RunningService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Action.START.toString() -> start()
            Action.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Runner is active")
            .setContentText("Elapsed time: 00.50")
            .build()
        startForeground(1, notification)
    }

    enum class Action {
        START, STOP
    }
}