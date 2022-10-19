package com.vtorushin.feature.dishlist.utils

import android.content.res.Resources
import com.vtorushin.core.model.Meal
import java.text.FieldPosition
import java.util.concurrent.atomic.AtomicInteger

fun Map<String, List<Meal>>.combineMealsIntoSingleList() = run {
    buildList {
        values.forEach {
            addAll(it)
        }
    }
}

fun Map<String, List<Meal>>.toSegmentList() = run {
    val sum = AtomicInteger(0)
    values.map {
        sum.set(sum.get() + it.size)
        sum.get()
    }
}

fun Map<String, List<Meal>>.toSegmentMap() = run {
    buildMap {
        var summ = 0
        this@toSegmentMap.forEach {
            summ += 1
            put(it.key, summ)
        }
    }
}

fun Map<String, List<Meal>>.findStartOfTheSegment(segmentNumber: Int) = run {
    toSegmentList()[segmentNumber] - values.toList()[segmentNumber].size
}