package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import lacty.manager.ip.databinding.ActivityOsEditBinding;

public class OSEdit extends AppCompatActivity {
ActivityOsEditBinding binding;
    String edit_id = "";
    String edit_os = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_os_edit);

        binding=ActivityOsEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        try {
            edit_id = intent.getStringExtra("id");
            edit_os = intent.getStringExtra("os");
            binding.editTextOSeditID.setText(edit_id);
            binding.editTextOSeditOS.setText(edit_os);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);

        binding.imageButtonOSeditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();

                String id = binding.editTextOSeditID.getText().toString();
                String os = binding.editTextOSeditOS.getText().toString();
                if (!id.trim().isEmpty()) {
                    if (!os.trim().isEmpty()) {
                        String update = "UPDATE HeDieuHanh SET MaHeDieuHanh='" + id + "',HeDieuHanh=N'" + os + "' WHERE MaHeDieuHanh='" + edit_id + "'";
                        int result = connect.execUpdateQuery(update);
                        if (result > 0) {
                            // xoay nut save
                            binding.imageButtonOSeditSave.startAnimation(animationrotate);
                            Toast.makeText(OSEdit.this, "Edit success!", Toast.LENGTH_SHORT).show();
                            Intent intentback = new Intent();
                            setResult(90, intentback);
                            finish();
                        }
                    } else {
                        Toast.makeText(OSEdit.this, "Operator system is empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OSEdit.this, "ID is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // bat su kien click nut quay lai
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}