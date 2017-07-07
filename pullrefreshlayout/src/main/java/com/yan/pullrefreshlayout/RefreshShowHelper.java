package com.yan.pullrefreshlayout;

import android.support.annotation.IntDef;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yan on 2017/7/7.
 * refresh show helper
 */

public class RefreshShowHelper {
    /**
     * @ShowState
     */
    @IntDef({STATE_FOLLOW, STATE_PLACEHOLDER_FOLLOW, STATE_PLACEHOLDER_CENTER, STATE_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowState {
    }

    public static final int STATE_FOLLOW = 0;
    public static final int STATE_PLACEHOLDER_FOLLOW = 1;
    public static final int STATE_PLACEHOLDER_CENTER = 2;
    public static final int STATE_CENTER = Gravity.CENTER_VERTICAL;

    private PullRefreshLayout pullRefreshLayout;

    private int headerShowState = STATE_FOLLOW;
    private int footerShowState = STATE_FOLLOW;

    RefreshShowHelper(PullRefreshLayout pullRefreshLayout) {
        this.pullRefreshLayout = pullRefreshLayout;
    }

    void setRefreshShowGravity(int headerShowGravity, int footerShowGravity) {
        this.headerShowState = headerShowGravity;
        this.footerShowState = footerShowGravity;
        dellRefreshHeaderShow();
        dellRefreshFooterShow();
    }

    void dellRefreshHeaderShow() {
        if (pullRefreshLayout.headerView != null) {
            pullRefreshLayout.headerView.setLayoutParams(getLayoutParams(headerShowState == STATE_FOLLOW ? Gravity.BOTTOM : headerShowState));
        }
    }

    void dellRefreshFooterShow() {
        if (pullRefreshLayout.footerView != null) {
            pullRefreshLayout.footerView.setLayoutParams(getLayoutParams(footerShowState == STATE_FOLLOW ? Gravity.TOP : footerShowState));
        }
    }

    private FrameLayout.LayoutParams getLayoutParams(int gravity) {
        FrameLayout.LayoutParams centerLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        centerLayoutParams.gravity = gravity;
        return centerLayoutParams;
    }

    private void resetLayoutParamsGravity(View refreshView, int gravity) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) refreshView.getLayoutParams();
        if (layoutParams.gravity != gravity) {
            layoutParams.gravity = gravity;
            refreshView.setLayoutParams(layoutParams);
        }
    }

    float headerOffsetRatio(float ratio) {
        switch (headerShowState) {
            case STATE_PLACEHOLDER_FOLLOW:
                resetLayoutParamsGravity(pullRefreshLayout.headerView, ratio <= 1 ? Gravity.TOP : Gravity.BOTTOM);
                break;
            case STATE_PLACEHOLDER_CENTER:
                resetLayoutParamsGravity(pullRefreshLayout.headerView, ratio <= 1 ? Gravity.TOP : Gravity.CENTER_VERTICAL);
                break;
        }
        return ratio;
    }

    float footerOffsetRatio(float ratio) {
        switch (footerShowState) {
            case STATE_PLACEHOLDER_FOLLOW:
                resetLayoutParamsGravity(pullRefreshLayout.footerView, ratio > -1 ? Gravity.BOTTOM : Gravity.TOP);
                break;
            case STATE_PLACEHOLDER_CENTER:
                resetLayoutParamsGravity(pullRefreshLayout.footerView, ratio > -1 ? Gravity.BOTTOM : Gravity.CENTER_VERTICAL);
                break;
        }
        return ratio;
    }

}
