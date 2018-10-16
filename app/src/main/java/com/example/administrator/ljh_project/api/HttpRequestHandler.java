package com.example.administrator.ljh_project.api;

/**
 * Created Administrator on 2018\1\3.
 */
public interface HttpRequestHandler<E> {
    public void onSuccess(E data);

    public void onSuccess(E data, int totalPages, int currentPage);

    public void onFailure(String error);
}
