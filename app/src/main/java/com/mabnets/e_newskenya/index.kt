package com.mabnets.e_newskenya

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.firebase.messaging.FirebaseMessaging
import com.mabnets.e_newskenya.databinding.ActivityIndexBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class index : AppCompatActivity() {
    val TOPIC="Alertz"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityIndexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIndexBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarIndex.toolbar)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        MobileAds.initialize(this) { }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_index)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.contacts
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val url = intent.getStringExtra("url")
        if (url != null) {
            val intent = Intent(this, Webactivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
        }
    }

   /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.index, menu)
        return true
    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_index)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}