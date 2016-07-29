package com.baseframe.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.baseframe.R;

/**
 * Created by hujin on 2015/12/7.
 */
public class UpdateDialog extends Dialog {

    private View mContentView;
    private TextView mTitle, mContent;
    private Button mConfirm, mCancel;
    private UpdateBack m_UpdatelogBack;
    private UpdateOnClickListener m_UpdateOnClickListene;
    private int m_EventId;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (m_UpdatelogBack != null) {
                    m_UpdatelogBack.BackEvent();
                } else {
                    this.dismiss();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public UpdateDialog(Context context, String title, String content, int aEventID) {
        this(context, R.style.account_bottom_dialog);
        this.m_EventId = aEventID;
        init(context, title, content);

    }


    public UpdateDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER_VERTICAL;
        getWindow().setAttributes(params);
        setCancelable(false);
    }

    private void init(Context context, String title, String content) {
        View view = View.inflate(context, R.layout.umeng_update_dialog_new, null);
        init(context, view, title, content);
    }

    private void init(Context context, View contentView, String title, String content) {
        mContentView = contentView;
        setContentView(mContentView);
        mTitle = (TextView) mContentView.findViewById(R.id.update_dialog_title);
        mContent = (TextView) mContentView.findViewById(R.id.update_dialog_content);
        mConfirm = (Button) mContentView.findViewById(R.id.update_dialog_confirm);
        mCancel = (Button) mContentView.findViewById(R.id.update_dialog_cancel);
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            mContent.setText(content);

        }
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_UpdateOnClickListene != null) {
                    m_UpdateOnClickListene.OnClick(1, m_EventId);
                }
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_UpdateOnClickListene != null) {
                    m_UpdateOnClickListene.OnClick(2, m_EventId);
                }
            }
        });

    }

    public Button getConfirm() {
        return mConfirm;
    }

    public Button getCancel() {
        return mCancel;
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    @Override
    public void show() {
        super.show();
        mContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismiss() {
        Log.e("info", "点击");
        super.dismiss();
        mContentView.setVisibility(View.GONE);
    }

    public interface UpdateBack {
        public abstract void BackEvent();
    }

    public void setBacklistener(UpdateBack aLs) {
        m_UpdatelogBack = aLs;
    }

    public abstract interface UpdateOnClickListener {
        public abstract void OnClick(int type, int eventId);
    }

    public void setOnClickListener(UpdateOnClickListener uLs) {
        m_UpdateOnClickListene = uLs;
    }

}
