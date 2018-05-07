package com.leon.androidpluskotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import java.util.*


open class FlowLayout : ViewGroup {

    private val mLines = ArrayList<Line>() // 用来记录描述有多少行View
    private var mHorizontalSpace = 10
    private var mVerticalSpace = 10

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    fun setSpace(horizontalSpace: Int, verticalSpace: Int) {
        this.mHorizontalSpace = horizontalSpace
        this.mVerticalSpace = verticalSpace
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 清空
        mLines.clear()
        var currentLine: Line? = null
        //获取父容器对FlowLayout期望的宽度
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        // 获取行最大的宽度
        val maxLineWidth = width - paddingLeft - paddingRight

        // 测量孩子，并将孩子添加到它所属的Line中
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            // 如果孩子不可见
            if (view.visibility == View.GONE) {
                continue
            }
            // 测量孩子
            measureChild(view, widthMeasureSpec, heightMeasureSpec)
            // 往lines添加孩子
            if (currentLine == null) {// 说明还没有开始添加孩子
                currentLine = Line(maxLineWidth, mHorizontalSpace)
                // 添加到 Lines中
                mLines.add(currentLine)
                //添加一个孩子
                currentLine.addView(view)
            } else {
                // 行不为空,行中有孩子了
                val canAdd = currentLine.canAdd(view)
                if (canAdd) {// 可以添加
                    currentLine.addView(view)
                } else {// 不可以添加,装不下去，需要新建行
                    currentLine = Line(maxLineWidth, mHorizontalSpace)
                    // 添加到lines中
                    mLines.add(currentLine)
                    // 将view添加到line
                    currentLine.addView(view)
                }
            }
        }

        var allHeight = 0f
        //计算所有行高和间距的和
        for (i in mLines.indices) {
            allHeight += mLines[i].mHeight
            // 如果不是一行，则需要加间距
            if (i != 0) {
                allHeight += mVerticalSpace.toFloat()
            }
        }
        //计算FlowLayout的高度
        val measuredHeight = (allHeight + paddingTop.toFloat() + paddingBottom.toFloat() + 0.5f).toInt()
        //设置FlowLayout的宽高
        setMeasuredDimension(width, measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val paddingLeft = paddingLeft
        var offsetTop = paddingTop
        //布局每一行
        for (i in mLines.indices) {
            val line = mLines[i]
            line.layout(paddingLeft, offsetTop)
            //增加竖直方向的偏移量
            offsetTop += (line.mHeight + mVerticalSpace).toInt()
        }
    }

    /**
     * FlowLayout每一行的数据结构
     */
    inner class Line(maxWidth: Int, horizontalSpace: Int) {
        // 属性
        val mViewsInLine = ArrayList<View>()    // 用来记录每一行有几个View
        val mMaxWidth: Float                            // 行最大的宽度
        var mUsedWidth: Float = 0.toFloat()                        // 已经使用了多少宽度
        var mHeight: Float = 0.toFloat()                            // 行的高度
        private val mMarginLeft: Float = 0.toFloat()
        private val mMarginRight: Float = 0.toFloat()
        private val mMarginTop: Float = 0.toFloat()
        private val mMarginBottom: Float = 0.toFloat()
        private val mHorizontalSpace: Float                    // View和view之间的水平间距

        init {
            this.mMaxWidth = maxWidth.toFloat()
            this.mHorizontalSpace = horizontalSpace.toFloat()
        }

        /**
         * 往一行里面添加view，记录属性的变化
         */
        fun addView(view: View) {
            // 加载View的方法
            val size = mViewsInLine.size
            // 获取添加view的宽和高
            val viewWidth = view.measuredWidth
            val viewHeight = view.measuredHeight
            // size == 0 表示还没有添加View
            if (size == 0) {
                //如果添加view的宽度大于最大宽度，那么已经使用的宽度就赋值为最大宽度
                if (viewWidth > mMaxWidth) {
                    mUsedWidth = mMaxWidth
                } else {//如果添加view的宽度小于或者等于最大宽度，那么已经使用的宽度就赋值添加view的宽度
                    mUsedWidth = viewWidth.toFloat()
                }
                mHeight = viewHeight.toFloat()//
            } else { // size不为0表示一行里面已经添加了一个view
                //已经使用的宽度就增加一个view的宽度和水平间隔
                mUsedWidth += viewWidth + mHorizontalSpace
                //行的高度去所有view中最大的高度
                mHeight = if (mHeight < viewHeight) viewHeight.toFloat() else mHeight
            }

            // 将View记录到集合中
            mViewsInLine.add(view)
        }

        /**
         * 用来判断是否可以将View添加到行中
         *
         * @param view 要添加到行里面的view
         * @return true 表示可以添加，false 表示不可以添加
         */
        fun canAdd(view: View): Boolean {
            // 判断是否能添加View
            val size = mViewsInLine.size
            //如果该行没有添加过view则返回true
            if (size == 0) {
                return true
            }

            val viewWidth = view.measuredWidth

            // 预计使用的宽度
            val planWidth = mUsedWidth + mHorizontalSpace + viewWidth.toFloat()
            //预计宽度小于等于最大宽度表示可以添加
            return planWidth <= mMaxWidth
        }

        /**
         * 给孩子布局
         *
         * @param offsetLeft 孩子左边位置的偏移量
         * @param offsetTop 孩子顶部位置的偏移量
         */
        fun layout(offsetLeft: Int, offsetTop: Int) {
            var currentLeft = offsetLeft

            val size = mViewsInLine.size
            var extra = 0f
            var widthAvg = 0f
            if (mUsedWidth < mMaxWidth) {// 判断已经使用的宽度是否小于最大的宽度
                extra = mMaxWidth - mUsedWidth//计算出剩余的宽度
                widthAvg = extra / size//将剩余宽度平均分配到一行的所有的view
            }

            for (i in 0 until size) {
                val view = mViewsInLine[i]
                var viewWidth = view.measuredWidth
                var viewHeight = view.measuredHeight

                // 判断是否有富余
                if (widthAvg != 0f) {
                    // 改变宽度
                    val newWidth = (viewWidth.toFloat() + widthAvg + 0.5f).toInt()
                    val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(newWidth, View.MeasureSpec.EXACTLY)
                    val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(viewHeight, View.MeasureSpec.EXACTLY)
                    //重新的测量孩子
                    view.measure(widthMeasureSpec, heightMeasureSpec)
                    viewWidth = view.measuredWidth
                    viewHeight = view.measuredHeight
                }

                // 布局
                val left = currentLeft
                //为了使view能够在竖直方向摆在一行的中间，计算top
                val top = (offsetTop.toFloat() + (mHeight - viewHeight) / 2 + 0.5f).toInt()
                val right = left + viewWidth
                val bottom = top + viewHeight
                //布局一个view
                view.layout(left, top, right, bottom)
                //调整水平方向的偏移量，布局下一个view
                currentLeft += (viewWidth + mHorizontalSpace).toInt()
            }
        }
    }

}
