package com.programmerbaper.jabarjakartatravel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.programmerbaper.jabarjakartatravel.R;
import com.programmerbaper.jabarjakartatravel.entities.Trayek;

public class CaraBayarActivity extends AppCompatActivity {

    private String[] mTextBCAs = new String[10];
    private String[] mTextMandiris = new String[10];

    private TextView mHeader, mBank1, mBank2, mBank3, mBank4,
            mBank5, mBank6, mBank7, mBank8, mBank9;

    private boolean mShorcut = false;
    private int mKode;
    private int mHarga;
    private int mJumlah ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cara_bayar);

        setTitle("Cara Bayar");


        Bundle bundle = getIntent().getExtras();
        mShorcut = bundle.getBoolean("shortcut");
        mKode = bundle.getInt("kode");
        mHarga = bundle.getInt("harga");
        mJumlah = bundle.getInt("jumlah") ;


        bindingTexts();
        setBCA();
        CardView cardHolder = findViewById(R.id.cardHolder);

        if (!mShorcut) {
            TextView id = findViewById(R.id.id);
            id.setText(IsiDataActivity.generateKode(mKode));
            TextView nominal = findViewById(R.id.nominal);
            nominal.setText((Trayek.formatter((mJumlah * mHarga + mKode) + "")));
        } else {
            cardHolder.setVisibility(View.GONE);
        }


        final RadioButton radioBCA = findViewById(R.id.radio_bca);
        final RadioButton radioMandiri = findViewById(R.id.radio_mandiri);

        radioBCA.setChecked(true);

        radioBCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioBCA.setChecked(true);
                radioMandiri.setChecked(false);
                setBCA();

            }
        });

        radioMandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioBCA.setChecked(false);
                radioMandiri.setChecked(true);
                setMandiri();
            }
        });

        Button selanjutnya = findViewById(R.id.selanjutnya);
        selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CaraBayarActivity.this, MainActivity.class);
                intent.putExtra("shortcut", mShorcut);
                startActivity(intent);
            }
        });

    }


    private void bindingTexts() {


        mHeader = findViewById(R.id.header);
        mBank1 = findViewById(R.id.bank_1);
        mBank2 = findViewById(R.id.bank_2);
        mBank3 = findViewById(R.id.bank_3);
        mBank4 = findViewById(R.id.bank_4);
        mBank5 = findViewById(R.id.bank_5);
        mBank6 = findViewById(R.id.bank_6);
        mBank7 = findViewById(R.id.bank_7);
        mBank8 = findViewById(R.id.bank_8);
        mBank9 = findViewById(R.id.bank_9);

        mTextBCAs[0] = getString(R.string.header_bca);
        mTextBCAs[1] = getString(R.string.bca_1);
        mTextBCAs[2] = getString(R.string.bca_2);

        if (!mShorcut) {
            mTextBCAs[3] = getString(R.string.bca_3);
            mTextBCAs[4] = getString(R.string.bca_4);
        } else {
            mTextBCAs[3] = getString(R.string.bca_3_shortcut);
            mTextBCAs[4] = getString(R.string.bca_4_shortcut);
        }

        mTextBCAs[5] = getString(R.string.bca_5);
        mTextBCAs[6] = getString(R.string.bca_6);
        mTextBCAs[7] = getString(R.string.bca_7);
        mTextBCAs[8] = getString(R.string.bca_8);
        mTextBCAs[9] = getString(R.string.bca_9);

        mTextMandiris[0] = getString(R.string.header_mandiri);
        mTextMandiris[1] = getString(R.string.mandiri_1);
        mTextMandiris[2] = getString(R.string.mandiri_2);
        mTextMandiris[3] = getString(R.string.mandiri_3);
        mTextMandiris[4] = getString(R.string.mandiri_4);
        mTextMandiris[5] = getString(R.string.mandiri_5);
        mTextMandiris[6] = getString(R.string.mandiri_6);

        if (!mShorcut) {
            mTextMandiris[7] = getString(R.string.mandiri_7);
            mTextMandiris[8] = getString(R.string.mandiri_8);
        } else {
            mTextMandiris[7] = getString(R.string.mandiri_7_shortcut);
            mTextMandiris[8] = getString(R.string.mandiri_8_shortcut);
        }

        mTextMandiris[9] = getString(R.string.mandiri_9);
    }

    private void setBCA() {

        mBank7.setVisibility(View.GONE);
        mBank8.setVisibility(View.GONE);
        mBank9.setVisibility(View.GONE);

        mHeader.setText(mTextBCAs[0]);
        mBank1.setText(mTextBCAs[1]);
        mBank2.setText(mTextBCAs[2]);
        mBank3.setText(mTextBCAs[3]);
        mBank4.setText(mTextBCAs[4]);
        mBank5.setText(mTextBCAs[5]);
        mBank6.setText(mTextBCAs[6]);


    }

    private void setMandiri() {

        mBank7.setVisibility(View.VISIBLE);
        mBank8.setVisibility(View.VISIBLE);
        mBank9.setVisibility(View.VISIBLE);

        mHeader.setText(mTextMandiris[0]);
        mBank1.setText(mTextMandiris[1]);
        mBank2.setText(mTextMandiris[2]);
        mBank3.setText(mTextMandiris[3]);
        mBank4.setText(mTextMandiris[4]);
        mBank5.setText(mTextMandiris[5]);
        mBank6.setText(mTextMandiris[6]);
        mBank7.setText(mTextMandiris[7]);
        mBank8.setText(mTextMandiris[8]);
        mBank9.setText(mTextMandiris[9]);
    }
}
