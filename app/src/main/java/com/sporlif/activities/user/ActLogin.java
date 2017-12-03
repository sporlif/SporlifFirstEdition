package com.sporlif.activities.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sporlif.R;

public class ActLogin extends Activity {

    private EditText actLoginEtUserEmail;
    private EditText actLoginEtPass;
    private TextView actLoginTxtRecoverPass;
    private TextView actLoginTxtSignIn;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.act_login);

        launchWidgets();
        launchEvents();


    }

    public void launchWidgets() {

        actLoginEtUserEmail = (EditText) findViewById(R.id.actLoginEtUserEmail);
        actLoginEtPass = (EditText) findViewById(R.id.actLoginEtPass);
        actLoginTxtRecoverPass = (TextView) findViewById(R.id.actLoginTxtRecoverPass);
        actLoginTxtSignIn = (TextView) findViewById(R.id.actLoginTxtSignIn);

    }

    public void launchEvents() {

        actLoginTxtSignIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                SpannableString spanString = new SpannableString(((TextView) v).getText().toString());

                if(event.getAction() == MotionEvent.ACTION_UP){
                    spanString.setSpan(new StyleSpan(Typeface.NORMAL), 0, spanString.length(), 0);
                    ((TextView) v).setText(spanString);
                }else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
                    ((TextView) v).setText(spanString);
                }

                return false;
            }
        });

        actLoginTxtRecoverPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActLogin.this, ActRecoverPass.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in_right,R.anim.anim_out_left);
            }
        });

        actLoginTxtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActLogin.this, ActRegist.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in_right,R.anim.anim_out_left);
            }
        });

    }

}

