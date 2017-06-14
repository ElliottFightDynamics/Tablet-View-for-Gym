package com.efd.striketectablet.activity.credential;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.efd.striketectablet.R;

public class TermsofuseActivity extends AppCompatActivity {

    ImageView backBtn;
    TextView agreementLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_termsofuse);

        initViews();
    }

    private void initViews(){
        backBtn = (ImageView)findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        agreementLink = (TextView) findViewById(R.id.agreement_content0);
        agreementLink.setMovementMethod(LinkMovementMethod.getInstance());
        agreementLink.setLinksClickable(true);
        agreementLink.setLinkTextColor(getResources().getColor(R.color.punches_text_color));
    }


}
