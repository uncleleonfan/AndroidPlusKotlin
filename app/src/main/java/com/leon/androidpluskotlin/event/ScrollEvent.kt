package com.leon.androidpluskotlin.event

class ScrollEvent(val direction: Direction) {

    enum class Direction {
        UP, DOWN
    }

}
