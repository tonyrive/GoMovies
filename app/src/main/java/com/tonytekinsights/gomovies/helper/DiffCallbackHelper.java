package com.tonytekinsights.gomovies.helper;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class DiffCallbackHelper<T> extends DiffUtil.Callback {
    private final List<T> oldPosts;
    private final List<T> newPosts;

    public DiffCallbackHelper(List<T> oldPosts, List<T> newPosts) {
        this.oldPosts = oldPosts;
        this.newPosts = newPosts;
    }

    @Override
    public int getOldListSize() {
        return oldPosts.size();
    }

    @Override
    public int getNewListSize() {
        return newPosts.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // TODO we may need to implement the Comparable interface in the models
        // that need to get tested
        //
        //return oldPosts.get(oldItemPosition).id == newPosts.get(newItemPosition).id;
        return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
    }

    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
