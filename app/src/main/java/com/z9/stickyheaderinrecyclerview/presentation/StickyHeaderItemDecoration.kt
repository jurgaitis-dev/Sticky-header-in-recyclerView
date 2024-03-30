package com.z9.stickyheaderinrecyclerview.presentation

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class StickyHeaderItemDecoration(
    private val headerViewType: Int,
) : RecyclerView.ItemDecoration() {

    private var currentHeaderIndex = RecyclerView.NO_POSITION
    private var currentHeaderView: View? = null

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val topChild = parent.findChildViewUnder(
            parent.paddingLeft.toFloat(),
            parent.paddingTop.toFloat()
        ) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val headerView = getHeaderView(topChildPosition, parent)
        currentHeaderView = headerView
        val contactPoint = headerView.bottom + parent.paddingTop
        val childInContact = getChildInContact(parent, contactPoint) ?: return
        val childInContactPosition = parent.getChildAdapterPosition(childInContact)

        if (parent.adapter?.getItemViewType(childInContactPosition) == headerViewType) {
            moveHeader(c, headerView, childInContact, parent.paddingTop)
            return
        }

        drawHeader(c, headerView)
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View, paddingTop: Int) {
        c.save()
        c.clipRect(0, paddingTop, c.width, paddingTop + currentHeader.height)
        c.translate(0f, (nextHeader.top - currentHeader.height).toFloat() + paddingTop)
        currentHeader.draw(c)
        c.restore()
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    private fun getHeaderView(topChildPosition: Int, parent: RecyclerView): View {
        val headerPosition = getHeaderPositionForItem(topChildPosition, parent)
        if (currentHeaderView != null && headerPosition == currentHeaderIndex) {
            return currentHeaderView!!
        }
        val headerType = parent.adapter?.getItemViewType(headerPosition)
        var headerHolder = parent.adapter?.createViewHolder(parent, headerType!!)
        if (headerHolder != null) {
            parent.adapter?.onBindViewHolder(headerHolder, headerPosition)
            headerHolder = fixLayoutSize(parent, headerHolder)
        }

        return headerHolder?.itemView!!
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val rect = Rect()
            parent.getDecoratedBoundsWithMargins(child, rect)
            if (rect.bottom > contactPoint && rect.top <= contactPoint) {
                childInContact = child
                break
            }
        }
        return childInContact
    }

    private fun getHeaderPositionForItem(itemPosition: Int, parent: RecyclerView): Int {
        var headerPosition = RecyclerView.NO_POSITION
        var currentPosition = itemPosition
        do {
            if (parent.adapter?.getItemViewType(currentPosition) == headerViewType) {
                headerPosition = currentPosition
                break
            }
            currentPosition -= 1
        } while (currentPosition >= 0)
        return headerPosition
    }

    private fun fixLayoutSize(parent: ViewGroup, viewHolder: ViewHolder): ViewHolder {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec =
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            viewHolder.itemView.layoutParams.width
        )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            viewHolder.itemView.layoutParams.height
        )

        viewHolder.itemView.measure(childWidthSpec, childHeightSpec)
        viewHolder.itemView.layout(
            0,
            0,
            viewHolder.itemView.measuredWidth,
            viewHolder.itemView.measuredHeight
        )

        return viewHolder
    }
}