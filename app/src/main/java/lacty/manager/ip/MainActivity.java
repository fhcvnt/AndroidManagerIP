package lacty.manager.ip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import lacty.manager.ip.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private String userlogin = ""; // user dang dang nhap
    private String grouplogin = "Admin"; // group login=Admin thi moi cho Add, Edit, delete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation animationalpha = AnimationUtils.loadAnimation(this, R.anim.alpha_button);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Manager IP
        binding.buttonMainIPList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationalpha);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ManagerIP.class);
                intent.putExtra("userlogin", userlogin);
                intent.putExtra("grouplogin", grouplogin);
                startActivity(intent);
            }
        });

        // User
        binding.buttonMainUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationalpha);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), User.class);
                intent.putExtra("grouplogin", grouplogin);
                startActivity(intent);
            }
        });

        // person info
        binding.buttonMainPersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationalpha);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), PersonInfo.class);
                startActivity(intent);
            }
        });

        // Department
        binding.buttonMainDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationalpha);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Department.class);
                startActivity(intent);
            }
        });

        // Building
        binding.buttonMainBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationalpha);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Building.class);
                intent.putExtra("grouplogin", grouplogin);
                startActivity(intent);
            }
        });

        // machine type
        binding.buttonMainMachineType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationalpha);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MachineType.class);
                intent.putExtra("grouplogin", grouplogin);
                startActivity(intent);
            }
        });

        // OS
        binding.buttonMainOperatorSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationalpha);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), OS.class);
                intent.putExtra("grouplogin", grouplogin);
                startActivity(intent);
            }
        });

        // Office
        binding.buttonMainOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationalpha);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Office.class);
                intent.putExtra("grouplogin", grouplogin);
                startActivity(intent);
            }
        });

        // exit
        binding.buttonMainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationalpha);
                finish();
            }
        });
    }
}