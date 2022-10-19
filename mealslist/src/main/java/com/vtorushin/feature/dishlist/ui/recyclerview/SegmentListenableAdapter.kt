package com.vtorushin.feature.dishlist.ui.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//This adapter provides an event callback when segments are reached while scrolling
abstract class SegmentListenableAdapter<VH : RecyclerView.ViewHolder>(
    private val onNewSegmentReached: (segmentNumber: Int) -> Unit
) : RecyclerView.Adapter<VH>() {
    abstract var segmentsEndsPositionsList: List<Int>
    private var activeSegmentNumber: Int = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //Scroll up
                if (dy < 0 && layoutManager.findFirstCompletelyVisibleItemPosition() != -1) {
                    if (activeSegmentNumber > 0
                        && layoutManager.findFirstCompletelyVisibleItemPosition() < segmentsEndsPositionsList[activeSegmentNumber - 1]) {
                        activeSegmentNumber--
                        onNewSegmentReached(activeSegmentNumber)
                    }
                }
                //Scroll down
                if (dy > 0 && layoutManager.findLastCompletelyVisibleItemPosition() != -1) {
                    if (activeSegmentNumber <= segmentsEndsPositionsList.size
                        && layoutManager.findFirstCompletelyVisibleItemPosition() > segmentsEndsPositionsList[activeSegmentNumber]) {
                        activeSegmentNumber++
                        onNewSegmentReached(activeSegmentNumber)
                    }
                }
            }
        })
    }
}