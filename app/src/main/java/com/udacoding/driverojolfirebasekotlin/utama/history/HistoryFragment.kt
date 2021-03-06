package com.udacoding.driverojolfirebasekotlin.utama.history


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.udacoding.driverojolfirebasekotlin.R
import com.udacoding.driverojolfirebasekotlin.utama.history.adapter.Historydapter
import com.udacoding.driverojolfirebasekotlin.utama.home.model.Booking
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.support.v4.toast
import java.lang.IllegalStateException


class HistoryFragment : Fragment() {

    var auth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Order")
        val query = reference.orderByChild("uid").equalTo(uid)
        query.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                toast("gagal")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val data = ArrayList<Booking>()
                for (issue in p0.children){
                    val booking = issue.getValue(Booking::class.java)
                    data.add(booking ?: Booking())
                    showdata(data)
                }
            }
        })
    }

    private fun showdata(data: ArrayList<Booking>) {
        try {


            recyclerview.adapter = Historydapter(data, object : Historydapter.onClickItem {
                override fun Click(Item: Booking) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
            recyclerview.layoutManager = LinearLayoutManager(context)
        }catch (e:IllegalStateException){}
    }

}
