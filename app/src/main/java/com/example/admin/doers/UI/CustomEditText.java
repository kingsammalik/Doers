package com.example.admin.doers.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEditText extends AppCompatEditText {


    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init()
    {


    }

    public void setTypeface(Typeface typeface,int style)
    {

      if(style==Typeface.BOLD)
      {
         super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),""));

      }
      else {

          super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),""));
      }

    }
}
