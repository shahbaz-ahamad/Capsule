package com.example.capsule.datamodel


data class ParentItem(
    val title : String,
    val childItemList:ArrayList<ChildItem>,
    var isExpandable:Boolean=false
)
