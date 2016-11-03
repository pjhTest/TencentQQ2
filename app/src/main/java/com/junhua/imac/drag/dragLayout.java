package com.junhua.imac.drag;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import static android.content.ContentValues.TAG;

/**
 * Created by imac on 2016/11/2.
 */

public class dragLayout extends FrameLayout {

    private ViewDragHelper mDragHelper;
    private ViewGroup mMainContent;
    private ViewGroup mLeftContent;
    private int mWidth;
    private int mHeight;
    private int mRange;
    private ViewGroup mRightContent;


    public  dragLayout(Context context){
        this(context,null);
    }
    public  dragLayout(Context context, AttributeSet attrs){
        this(context, attrs,0);
    }
    public  dragLayout(Context context,AttributeSet attrs,int defStyle){
        super(context, attrs, defStyle);
        //初始化操作(通过静态方法)
        mDragHelper =  ViewDragHelper.create(this,mCallback);
    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

       //重写事件

        //根据返回值决定当前child是否可以拖拽
        //child当前被拖拽的view
        //pointerID 区分多点触摸的ID
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //return true;只能拖拽主界面
            Log.d(TAG,"tryCaptureView"+child);
           // return child==mMainContent;
            return true;
        }
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            Log.d(TAG,"onViewCaptured");
            //当onViewCaptured被捕获时调用
            super.onViewCaptured(capturedChild, activePointerId);
        }
        @Override
        public int getViewHorizontalDragRange(View child) {
            //返回拖拽的范围，不对拖拽进行真的限制，仅仅决定了动画
            return mRange;
        }
            //根据建议值修正将要移动到的（横向）位置---------------------------
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //child:当前拖拽的view
            //left 新的位置，dx位置变化量
            //left = oldleft + dx
            //return super.clampViewPositionHorizontal(child, left, dx);
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //return super.clampViewPositionVertical(child, top, dy);
            return top;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }


        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }
    };

    //传递触摸事件

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //return super.onInterceptTouchEvent(ev);
        //传递给mDragHelper
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回true，持续接受事件
        return true;
    }
    protected  void  onFinishInflate(){
        super.onFinishInflate();
        //容错性检查（至少有两个view，子view必须是viewGroup的子类）
        if (getChildCount() < 2) {
            throw new IllegalStateException("至少有俩个子view");
        }
        if(!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup) ){
            throw new IllegalArgumentException("子view必须是viewgroup的子类");
        }
        mLeftContent = (ViewGroup)getChildAt(0);
        mMainContent = (ViewGroup)getChildAt(1);
        mRightContent = (ViewGroup)getChildAt(2);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //当尺寸有变化的时候调用

        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mRange = (int) ( mWidth * 0.6f );
    }
}
