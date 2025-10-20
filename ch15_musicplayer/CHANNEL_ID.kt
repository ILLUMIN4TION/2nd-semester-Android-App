val CHANNEL_ID = "ForegroundChannel"



    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }

    override fun onCreate() {
        createNotificationChannel()
    }

    fun createNotificationChannel(){
        Log.e("ForegroundService","createNotificationChannel() called")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)

            val manager = getSystemService(
                NotificationManager::class.java
            )
            Log.e("ForegroundService","createNotificationChannel() called")
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("ForegroundService","onStartCommand() called")

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()

        startForeground(1, notification)

        return   super.onStartCommand(intent, flags, startId)
    }