package com.example.gopal.bakingapp.ui;

/**
 * Created by Gopal on 2/12/2019.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.gopal.bakingapp.R;

public class MainActivity extends AppCompatActivity {
  public static final String CARD_VIEW_ID = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView cardView1 = findViewById(R.id.card_view_1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(CARD_VIEW_ID, 1);
                startActivity(intent);
            }
        });
        CardView cardView2 = findViewById(R.id.card_view_2);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(CARD_VIEW_ID, 2);
                startActivity(intent);
            }
        });
        CardView cardView3 = findViewById(R.id.card_view_3);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(CARD_VIEW_ID, 3);
                startActivity(intent);
            }
        });
        CardView cardView4 = findViewById(R.id.card_view_4);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(CARD_VIEW_ID, 4);
                startActivity(intent);
            }
        });
    }
}
