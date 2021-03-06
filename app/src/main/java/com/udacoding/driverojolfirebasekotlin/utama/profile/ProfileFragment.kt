package com.udacoding.driverojolfirebasekotlin.utama.profile


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.udacoding.driverojolfirebasekotlin.R
import com.udacoding.driverojolfirebasekotlin.login.LoginActivity
import com.udacoding.driverojolfirebasekotlin.signup.model.User
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.lang.IllegalStateException


class ProfileFragment : Fragment() {

    var auth: FirebaseAuth? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //inisialisasi auth firebase get uid from user
        val auth = FirebaseAuth.getInstance()
        var uid = auth.currentUser?.uid


        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Driver")

        var query = reference.orderByChild("uid").equalTo(uid)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                toast("koneksi lost")
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (issue in p0.children) {
                    val datauser = issue.getValue(User::class.java)

                    shodata(datauser)
                }
            }
        })
    }

    private fun shodata(datauser: User?) {
        try {
            profileName.text = datauser?.name
            profilEmail.text = datauser?.email
            profilhp.text = datauser?.hp

            profileSignout.onClick {
                auth?.signOut()
                startActivity<LoginActivity>()
            }
        }catch (e :IllegalStateException ){

        }

    }


}
