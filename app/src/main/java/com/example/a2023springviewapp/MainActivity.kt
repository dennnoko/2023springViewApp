package com.example.a2023springviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MemoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MemoListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.memo_list)
        recyclerView.adapter = adapter
    }
}