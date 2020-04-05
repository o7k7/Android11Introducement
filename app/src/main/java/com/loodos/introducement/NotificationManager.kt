package com.loodos.introducement

/**
 * Created by orhunkupeli on 19.03.2020
 */
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Person
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import androidx.annotation.WorkerThread

private const val CHANNEL_MESSAGING = "incoming_message"

private const val NOTIFICATION_CONTENT = 1

private const val NOTIFICATION_BUBBLE = 2

class NotificationManager(private val context: Context) {
    private val notificationManager =
        context.getSystemService(NotificationManager::class.java) ?: throw IllegalStateException()

    fun setUpNotificationChannel() {
        if (notificationManager.getNotificationChannel(CHANNEL_MESSAGING) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_MESSAGING,
                    "Notification Name",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Description of the notification"
                }
            )
        }
    }

    @WorkerThread
    fun showNotification(byUser: Boolean) {
        val person = getPerson("John Doe")
        val builder = Notification.Builder(context, CHANNEL_MESSAGING)
            .setBubbleMetadata( // Bubble API
                Notification.BubbleMetadata.Builder()
                    .setIntent( // Bubble onClick intent
                        PendingIntent.getActivity(
                            context,
                            NOTIFICATION_BUBBLE,
                            Intent(context, MainActivity::class.java)
                                .setAction(Intent.ACTION_VIEW)
                                .setData(getURI("https://google.com/")),
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                    )
                    .setDesiredHeight(250)
                    .setIcon(Icon.createWithResource(context, R.drawable.air_conditioner))
                    .apply {
                        if (byUser) {
                            setAutoExpandBubble(true)
                            setSuppressNotification(true)
                        }
                    }
                    .build()
            )
            .setContentTitle("New Message") // To display general notification
            .setSmallIcon(R.drawable.ic_air_conditioner_grey600_18dp)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .addPerson(person)
            .setShowWhen(true)

        if (byUser) {
            builder.setStyle(
                Notification.MessagingStyle(person)
                    .addMessage(
                        "Message",
                        System.currentTimeMillis(),
                        person
                    )
                    .setGroupConversation(false)
            ).setContentText("Content Text")
        }
        notificationManager.notify(1, builder.build())
    }

    private fun getPerson(name: String) = Person.Builder()
        .setName(name)
        .setImportant(true)
        .setBot(true)
        .build()

    private fun getURI(url: String) = Uri.parse(url)
}