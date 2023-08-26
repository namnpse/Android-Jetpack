package com.namnp.androidjetpack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.namnp.androidjetpack.recycler_view.diff_util.RecyclerViewUsingDiffUtilActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val intent = Intent(this, TwoWayBindingActivity::class.java)
//        val intent = Intent(this, NavigationActivity::class.java)
//        val intent = Intent(this, CoroutineActivity::class.java)
//        val intent = Intent(this, RoomActivity::class.java)
//        val intent = Intent(this, WorkManagerActivity::class.java)
//        val intent = Intent(this, DependencyInjectionActivity::class.java)
//        val intent = Intent(this, ViewBindingActivity::class.java)
//        val intent = Intent(this, JetpackComposeActivity::class.java)
//        val intent = Intent(this, ComposeStateActivity::class.java)
//        val intent = Intent(this, ComposeBestPracticeActivity::class.java)
//        val intent = Intent(this, CalculationActivity::class.java)
//        val intent = Intent(this, NotificationActivity::class.java)
//        val intent = Intent(this, OnboardingActivity::class.java)
//        val intent = Intent(this, SharingDataActivity::class.java)
//        val intent = Intent(this, LazyInjectionActivity::class.java)
        val intent = Intent(this, RecyclerViewUsingDiffUtilActivity::class.java)
        startActivity(intent)
    }
}