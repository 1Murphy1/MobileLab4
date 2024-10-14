package com.example.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), CellClickListener {

    data class ColorData(val colorName: String, val colorHex: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rView)

        val colorList = arrayListOf(
            ColorData("WHITE", 0xFFFFFFFF.toInt()),
            ColorData("BLACK", 0xFF000000.toInt()),
            ColorData("BLUE", 0xFF0000FF.toInt()),
            ColorData("RED", 0xFFFF0000.toInt()),
            ColorData("MAGENTA", 0xFFFF00FF.toInt())
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(this, colorList, this)
    }

    override fun onCellClickListener(colorData: ColorData) {
        Toast.makeText( this,"IT'S ${colorData.colorName}", Toast.LENGTH_SHORT).show()
    }
}
