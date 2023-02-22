package com.example.a2023springviewapp

import io.realm.RealmObject

open class Memo(
    open var name: String = ""
) : RealmObject()