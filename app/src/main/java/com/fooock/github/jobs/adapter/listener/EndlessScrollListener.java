package com.fooock.github.jobs.adapter.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 *
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    // Adapted from:
    // http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview

    private static final int CURRENT_PAGE = 0;
    private static final int VISIBLE_THRESHOLD = 1;

    private boolean loading = true;

    private int prevTotal = 0;
    private int actualPage = CURRENT_PAGE;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        // If dy is < 0 the scroll is up
        if (dy < 0) return;

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        final int childCount = recyclerView.getChildCount();
        final int itemCount = layoutManager.getItemCount();
        final int visibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (itemCount > prevTotal) {
                loading = false;
                prevTotal = itemCount;
            }
        }

        final int lastElements = itemCount - childCount;
        if (!loading && lastElements <= (visibleItemPosition + VISIBLE_THRESHOLD)) {
            actualPage += 1;
            onUpdateMore(actualPage);
            loading = true;
        }
    }

    public abstract void onUpdateMore(int page);
}
