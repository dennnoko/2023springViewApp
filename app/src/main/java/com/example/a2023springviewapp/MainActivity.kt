package com.example.a2023springviewapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.kotlin.delete

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MemoListAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MemoListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.memo_list)
        recyclerView.adapter = adapter

        val editText = findViewById<EditText>(R.id.memo_edit_text)
        val addButton = findViewById<Button>(R.id.add_button)
        val deleteButton = findViewById<Button>(R.id.delete_button)

        val realm = Realm .getDefaultInstance()

        addButton.setOnClickListener {
            val text = editText.text.toString()
            if(text.isEmpty()) {
                return@setOnClickListener
            }
            //Realmのトランザクション
            realm.executeTransactionAsync {
                //Memoのオブジェクトを作成
                val memo = it.createObject(Memo::class.java)
                //Memoに入力してあったtextを代入
                memo.name = text
                //上書きする
                it.copyFromRealm(memo)
            }
            //テキストを空にする
            editText.text.clear()
        }

        deleteButton.setOnClickListener {
            realm.executeTransactionAsync {
                it.deleteAll()
            }
        }

        //DBに変更があった時に通知が来る
        realm.addChangeListener {
            //変更があった時にリストをアップデートする
            val memoList = it.where(Memo::class.java).findAll().map { it.name }
            //UIスレッドで更新する
            recyclerView.post {
                adapter.updateMemoList(memoList)
            }
        }
        //初回表示時にリストを表示
        realm.executeTransactionAsync {
            val memoList = it.where(Memo::class.java).findAll().map { it.name }
            //UIスレッドで更新する
            recyclerView.post {
                adapter.updateMemoList(memoList)
            }
        }
    }
}