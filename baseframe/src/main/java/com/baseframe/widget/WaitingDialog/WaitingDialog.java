package com.baseframe.widget.WaitingDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baseframe.R;


public class WaitingDialog extends Dialog
{
    private boolean mIsCanCancle = true;
    private Context mContext;
    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event )
    {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(m_WaitDialogBack!=null)
                m_WaitDialogBack.BackEvent();
            else
            {
//                if(mIsCanCancle)
                    this.dismiss();
//                else
//                    return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void setCancelable( boolean flag )
    {
        // TODO Auto-generated method stub
        mIsCanCancle = flag;
        super.setCancelable(flag);
    }

    @Override
    public void show()
    {Window aDialogWindow = this.getWindow();
        WindowManager.LayoutParams aWLp = aDialogWindow.getAttributes();
        aDialogWindow.setGravity(Gravity.CENTER);


        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.color_bg_black_10));
        aDialogWindow.setBackgroundDrawable(dw);


        WindowManager m = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics aDm = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(aDm);
////        aWLp.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
////        aWLp.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
//
        aWLp.height = aDm.heightPixels;
        aWLp.width = aDm.widthPixels;

//        aWLp.alpha=0.1f;

//        aWLp.height = m.getDefaultDisplay().getHeight();
//        aWLp.width = m.getDefaultDisplay().getWidth();

        aDialogWindow.setAttributes(aWLp);
        // TODO Auto-generated method stub
        if(m_WaitingImage.getVisibility() == View.VISIBLE)
            m_AnimationDrawable.start();
        super.show();
    }

    public void showEx()
    {
        // TODO Auto-generated method stub
        if(m_WaitingImage.getVisibility() == View.VISIBLE)
            m_AnimationDrawable.start();

        if( m_waiting_Layout != null )
            m_waiting_Layout.setVisibility(View.GONE);
        super.show();
    }

    @Override
    public void dismiss()
    {
        // TODO Auto-generated method stub
        m_AnimationDrawable.stop();
        super.dismiss();
        if( m_waiting_Layout != null )
            m_waiting_Layout.setVisibility(View.VISIBLE);
    }

    private AnimationDrawable m_AnimationDrawable;
    private ImageView m_WaitingImage;
    private TextView m_WaitingImageText;
    
    private TextView m_WaitingTitle;
    private TextView m_WaitingMessage;
    private NumberProgressBar m_WaitingProgressBar;
    
    private View m_Btn_Layout;
    private Button m_Waiting_Left_Btn;
    private Button m_Waiting_Right_Btn;
    
    private View m_Btn_Single_Layout;
    private Button m_Waiting_Center_Btn;
    
    private WaitDialogOnClickListener m_WaitDialogOnClickListener;
    private WaitDialogBack     m_WaitDialogBack;
    private int               m_EventId;
    
    private View m_Image_Layout;
    
    private View m_Message_Layout;

    private LinearLayout m_waiting_Layout;
    
    View m_View;
    
    public static enum WaitDialogType
    {
        Dialog_Null,
        Dialog_OnlyWaiting,
        Dialog_ProgressBar,
        Dialog_WaitingText,
        Dialog_OnleyText,
        Dialog_TextBtn,
        Dialog_TextBtnProgressBar
    }
    
    public WaitingDialog (Context context, WaitDialogType aType, String aMessage, String aTitle, int aEventID, String aLeftBtn, String aCenterBtn, String aRightBtn)
    {
        super(context, R.style.WaitDialog);

        mContext = context;
        // TODO Auto-generated constructor stub
        m_EventId = aEventID;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        m_View = inflater.inflate(R.layout.waiting, null);
        m_waiting_Layout = (LinearLayout)m_View.findViewById(R.id.waiting_layout);
        m_Image_Layout = m_View.findViewById(R.id.wait_image_layout);
        m_WaitingImage = (ImageView) m_View.findViewById(R.id.wait_image);
        m_WaitingImageText = (TextView) m_View.findViewById(R.id.wait_image_text);
        
        m_AnimationDrawable = (AnimationDrawable) m_WaitingImage.getBackground();
        
        m_WaitingMessage = (TextView) m_View.findViewById(R.id.wait_text);
        m_WaitingTitle = (TextView) m_View.findViewById(R.id.wait_text2);
        
        m_Btn_Layout = m_View.findViewById(R.id.wait_btn_layout);
        
        m_Btn_Single_Layout = m_View.findViewById(R.id.wait_btn_layout2);
        
        
        m_WaitingProgressBar = (NumberProgressBar)m_View.findViewById(R.id.wait_numberbar);

        m_Message_Layout = m_View.findViewById(R.id.wait_text_layout);
        
        
        if(aType == null)
            aType = WaitDialogType.Dialog_OnlyWaiting;
        
        switch(aType)
        {
        case Dialog_OnlyWaiting:
            
            if(m_Image_Layout!=null)
            {
                m_Image_Layout.setVisibility(View.VISIBLE);
            }
            
            if(m_Message_Layout!=null)
            {
                m_Message_Layout.setVisibility(View.GONE);
            }
            
            if(m_Btn_Layout!=null)
                m_Btn_Layout.setVisibility(View.GONE);
            
            m_WaitingProgressBar.setVisibility(View.GONE);
            
            break;
        case Dialog_WaitingText:
            
            if(m_Image_Layout!=null)
            {
                m_Image_Layout.setVisibility(View.VISIBLE);
                m_WaitingImageText.setText(aMessage);
            }
            
            if(m_Message_Layout!=null)
            {
                m_Message_Layout.setVisibility(View.GONE);
            }
            
            if(m_Btn_Layout!=null)
                m_Btn_Layout.setVisibility(View.GONE);
            
            m_WaitingProgressBar.setVisibility(View.GONE);
            
            break;
        case Dialog_ProgressBar:
            if(m_Image_Layout!=null)
                m_Image_Layout.setVisibility(View.GONE);
            
            if(m_Message_Layout!=null)
            {
                m_Message_Layout.setVisibility(View.VISIBLE);
                if(aMessage!=null)
                {
                    m_WaitingMessage.setText(aMessage);
                    m_WaitingMessage.setVisibility(View.VISIBLE);
                }
                else
                {
                    m_WaitingMessage.setVisibility(View.GONE);
                }
                if(aTitle!=null)
                {
                    m_WaitingTitle.setVisibility(View.VISIBLE);
                    m_WaitingTitle.setText(aTitle);
                }
                else
                {
                    m_WaitingTitle.setVisibility(View.GONE);
                }
            }
            if(m_WaitingProgressBar!=null)
                m_WaitingProgressBar.setVisibility(View.VISIBLE);
            break;
        case Dialog_OnleyText:
            if(m_Image_Layout!=null)
                m_Image_Layout.setVisibility(View.GONE);
            if(m_Message_Layout!=null)
            {
                m_Message_Layout.setVisibility(View.VISIBLE);
                if(aMessage!=null)
                {
                    m_WaitingMessage.setText(aMessage);
                    m_WaitingMessage.setVisibility(View.VISIBLE);
                }
                else
                {
                    m_WaitingMessage.setVisibility(View.GONE);
                }
                if(aTitle!=null)
                {
                    m_WaitingTitle.setVisibility(View.VISIBLE);
                    m_WaitingTitle.setText(aTitle);
                }
                else
                {
                    m_WaitingTitle.setVisibility(View.GONE);
                }
            }
            if(m_Btn_Layout!=null)
                m_Btn_Layout.setVisibility(View.GONE);
            m_WaitingProgressBar.setVisibility(View.GONE);
            break;
        case Dialog_TextBtn:
            if(m_Image_Layout!=null)
                m_Image_Layout.setVisibility(View.GONE);
            if(m_Message_Layout!=null)
            {
                m_Message_Layout.setVisibility(View.VISIBLE);
                if(aMessage!=null)
                {
                    m_WaitingMessage.setText(aMessage);
                    m_WaitingMessage.setVisibility(View.VISIBLE);
                }
                else
                {
                    m_WaitingMessage.setVisibility(View.GONE);
                }
                if(aTitle!=null)
                {
                    m_WaitingTitle.setVisibility(View.VISIBLE);
                    m_WaitingTitle.setText(aTitle);
                }
                else
                {
                    m_WaitingTitle.setVisibility(View.GONE);
                }
            }
            if(aCenterBtn!=null)
            {
                if(m_Btn_Single_Layout!=null)
                    m_Btn_Single_Layout.setVisibility(View.VISIBLE);
                if(m_Btn_Layout!=null)
                    m_Btn_Layout.setVisibility(View.GONE);
                
                m_Waiting_Center_Btn = (Button)m_View.findViewById(R.id.wait_center_btn);
                if(m_Waiting_Center_Btn!=null)
                {
                    m_Waiting_Center_Btn.setText(aCenterBtn);
                    m_Waiting_Center_Btn.setOnClickListener(new View.OnClickListener()
                    {
                        
                        @Override
                        public void onClick( View arg0 )
                        {
                            // TODO Auto-generated method stub
                            dismiss();
                            if(m_WaitDialogOnClickListener!=null)
                            {
                                m_WaitDialogOnClickListener.OnClick(3, m_EventId);
                            }
                        }
                    });
                }
            }
            else
            {
                if(m_Btn_Layout!=null)
                    m_Btn_Layout.setVisibility(View.VISIBLE);
                if(m_Btn_Single_Layout!=null)
                    m_Btn_Single_Layout.setVisibility(View.GONE);
                m_Waiting_Left_Btn = (Button)m_View.findViewById(R.id.wait_left_btn);
                if(m_Waiting_Left_Btn!=null)
                {
                    if(aLeftBtn!=null)
                        m_Waiting_Left_Btn.setText(aLeftBtn);
                    m_Waiting_Left_Btn.setOnClickListener(new View.OnClickListener()
                    {
                        
                        @Override
                        public void onClick( View arg0 )
                        {
                            // TODO Auto-generated method stub
                            dismiss();
                            if(m_WaitDialogOnClickListener!=null)
                            {
                                m_WaitDialogOnClickListener.OnClick(1, m_EventId);
                            }
                        }
                    });
                }
                m_Waiting_Right_Btn = (Button)m_View.findViewById(R.id.wait_right_btn);
                if(m_Waiting_Right_Btn!=null)
                {
                    if(aRightBtn!=null)
                        m_Waiting_Right_Btn.setText(aRightBtn);
                    
                    m_Waiting_Right_Btn.setOnClickListener(new View.OnClickListener()
                    {
                        
                        @Override
                        public void onClick( View arg0 )
                        {
                            // TODO Auto-generated method stub
                            dismiss();
                            if(m_WaitDialogOnClickListener!=null)
                            {
                                m_WaitDialogOnClickListener.OnClick(2, m_EventId);
                            }
                        }
                    });
                }
            }
            
            
            break;
        case Dialog_TextBtnProgressBar:
            if(m_Image_Layout!=null)
                m_Image_Layout.setVisibility(View.GONE);
            
            if(m_Message_Layout!=null)
            {
                m_Message_Layout.setVisibility(View.VISIBLE);
                if(aMessage!=null)
                {
                    m_WaitingMessage.setText(aMessage);
                    m_WaitingMessage.setVisibility(View.VISIBLE);
                }
                else
                {
                    m_WaitingMessage.setVisibility(View.GONE);
                }
                if(aTitle!=null)
                {
                    m_WaitingTitle.setVisibility(View.VISIBLE);
                    m_WaitingTitle.setText(aTitle);
                }
                else
                {
                    m_WaitingTitle.setVisibility(View.GONE);
                }
           }
            
            if(m_Btn_Layout!=null)
                m_Btn_Layout.setVisibility(View.VISIBLE);
            
            m_WaitingProgressBar.setVisibility(View.VISIBLE);
            
            m_Waiting_Left_Btn = (Button)m_View.findViewById(R.id.wait_left_btn);
            if(m_Waiting_Left_Btn!=null)
                m_Waiting_Left_Btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick( View arg0 )
                    {
                        // TODO Auto-generated method stub
                        if(m_WaitDialogOnClickListener!=null)
                        {
                            m_WaitDialogOnClickListener.OnClick(1, m_EventId);
                        }
                    }
                });
            m_Waiting_Right_Btn = (Button)m_View.findViewById(R.id.wait_right_btn);
            if(m_Waiting_Right_Btn!=null)
                m_Waiting_Right_Btn.setOnClickListener(new View.OnClickListener()
                {
                    
                    @Override
                    public void onClick( View arg0 )
                    {
                        // TODO Auto-generated method stub
                        if(m_WaitDialogOnClickListener!=null)
                        {
                            m_WaitDialogOnClickListener.OnClick(2, m_EventId);
                        }
                    }
                });
            break;
        default:
            break;
        }
        
        

        
        this.setCancelable(false);
        setContentView(m_View);
        
    }

    public void setProgress(int aValue)
    {
        if(m_WaitingProgressBar!=null)
        {
            m_WaitingProgressBar.setProgress(aValue);
        }
    }
   
    public void setText(final String aMG)
    {
        if(m_WaitingMessage!=null && aMG!=null)
        {
            m_WaitingMessage.setVisibility(View.VISIBLE);
            m_WaitingMessage.setText(aMG);
        }
    }
    
    public void setTitleMg(final String aMG)
    {
        if(m_WaitingTitle!=null && aMG!=null)
        {
            m_WaitingTitle.setVisibility(View.VISIBLE);
            m_WaitingTitle.setText(aMG);
        }
    }
    
    public abstract interface WaitDialogOnClickListener
    {
        public abstract void OnClick(int type, int eventId);
    }
    
    public void setOnClickListener(WaitDialogOnClickListener aL)
    {
        m_WaitDialogOnClickListener = aL;
    }
    
    public abstract interface WaitDialogBack
    {
        public abstract void BackEvent();
    }
    
    public void setBacklistener(WaitDialogBack aLs)
    {
        m_WaitDialogBack = aLs;
    }
}