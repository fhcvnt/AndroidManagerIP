package lacty.manager.ip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import lacty.manager.ip.databinding.ActivityMainSuggestionBinding;

public class MainSuggestion extends AppCompatActivity {
    ActivityMainSuggestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_suggestion);

        binding = ActivityMainSuggestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation animationalpha = AnimationUtils.loadAnimation(this, R.anim.alpha_button);

        // Internet
        binding.buttonMainSuggestionInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Internet.class);
                startActivity(intent);
            }
        });

        // Software
        binding.buttonMainSuggestionSoftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Software.class);
                startActivity(intent);
            }
        });

        // exit
        binding.buttonMainSuggestionExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationalpha);
                finish();
            }
        });
    }
}