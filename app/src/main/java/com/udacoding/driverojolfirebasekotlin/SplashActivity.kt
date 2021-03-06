package com.udacoding.driverojolfirebasekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.udacoding.driverojolfirebasekotlin.login.LoginActivity
import com.udacoding.driverojolfirebasekotlin.utama.HomeActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {


    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        auth = FirebaseAuth.getInstance()

        Handler().postDelayed(Runnable {

            if(auth?.currentUser?.displayName != null){
                startActivity<HomeActivity>()
            }
            else startActivity<LoginActivity>()
        },2000)
    }
}
