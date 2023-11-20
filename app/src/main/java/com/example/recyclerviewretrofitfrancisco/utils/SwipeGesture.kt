package com.example.recyclerviewretrofitfrancisco.utils

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import android.content.Context

import androidx.core.content.ContextCompat
import com.example.recyclerviewretrofitfrancisco.R

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator



//Si quieres hacer swipe a la derecha hay que añadir al lado del item touch helper que hay un or itemtouch helper right
abstract class SwipeGesture(context: Context) : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){

    val deleteColor = ContextCompat.getColor(context, R.color.red)

    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {return false}

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addSwipeLeftBackgroundColor(deleteColor)
            .addSwipeLeftActionIcon(R.drawable.trash_small)
            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}