package com.example.ins_02.firechat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ins-02 on 29/1/18.
 */

public  class  RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private MainActivity.ClickListener clickListener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener)
    {
        this.clickListener=clickListener;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childViewUnder=recyclerView.findChildViewUnder(e.getX(),e.getY());

                if (childViewUnder!=null&&clickListener!=null)
                {
                    clickListener.onLongclick(childViewUnder,recyclerView.getChildAdapterPosition(childViewUnder));
                }

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){
            clickListener.onclick(child,rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
