package com.softclads.Gagron.Dialoge;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import com.softclads.Gagron.R;

public class Terms_Dialoge  extends Dialog {
    public Activity c;
    public Dialog d;
    public Terms_Dialoge(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        d = this;
        setContentView(R.layout.terms_dialoge);

    }
}
