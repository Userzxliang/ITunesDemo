package com.lxy.songsearch.ext

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun FragmentActivity.extLaunchWhenState(
    state: Lifecycle.State = Lifecycle.State.STARTED, block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launch {
    repeatOnLifecycle(state, block)
}

fun FragmentActivity.extToastShow(
    text: String, duration: Int = Toast.LENGTH_SHORT
) = Toast.makeText(this, text, duration)

