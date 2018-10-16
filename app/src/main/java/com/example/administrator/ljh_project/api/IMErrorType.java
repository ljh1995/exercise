package com.example.administrator.ljh_project.api;

import android.content.Context;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.utils.NetWorkHelper;


public enum IMErrorType {
    ErrorSuccess,
    ErrorApiForbidden,
    ErrorNoOnceAndNext,
    ErrorLoginFailure,
    ErrorCommentFailure,
    ErrorGetTopicListFailure,
    ErrorGetNotificationFailure,
    ErrorCreateNewFailure,
    ErrorFavNodeFailure,
    ErrorFavTopicFailure,
    ErrorGetProfileFailure;

    public static String errorMessage(Context cxt, IMErrorType type) {
        boolean isNetAvailable = NetWorkHelper.isNetAvailable(cxt);
        if (!isNetAvailable)
            return cxt.getResources().getString(R.string.error_network_disconnect);

        switch (type) {
            case ErrorApiForbidden:
                return cxt.getResources().getString(R.string.error_network_exception);

            case ErrorNoOnceAndNext:
                return cxt.getResources().getString(R.string.error_obtain_once);

            case ErrorLoginFailure:
                return cxt.getResources().getString(R.string.error_login);

            case ErrorCommentFailure:
                return cxt.getResources().getString(R.string.error_reply);

            case ErrorGetNotificationFailure:
                return cxt.getResources().getString(R.string.error_reply);

            case ErrorCreateNewFailure:
                return cxt.getResources().getString(R.string.error_create_topic);

            case ErrorFavNodeFailure:
                return cxt.getResources().getString(R.string.error_fav_nodes);

            case ErrorFavTopicFailure:
                return cxt.getResources().getString(R.string.error_fav_topic);

            case ErrorGetProfileFailure:
                return cxt.getResources().getString(R.string.error_get_profile);

            default:
                return cxt.getResources().getString(R.string.error_unknown);
        }

    }
}
