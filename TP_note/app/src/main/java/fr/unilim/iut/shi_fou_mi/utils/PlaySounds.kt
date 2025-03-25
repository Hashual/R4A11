package fr.unilim.iut.shi_fou_mi.utils

import android.content.Context
import android.media.MediaPlayer
import fr.unilim.iut.shi_fou_mi.R

fun playNewTopPlayerSound(context: Context) {
    val mediaPlayer = MediaPlayer.create(context, R.raw.langue_belle_mere)
    mediaPlayer.start()
    mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
}