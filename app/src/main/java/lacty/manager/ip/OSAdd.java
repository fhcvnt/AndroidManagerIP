package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import lacty.manager.ip.databinding.ActivityOsAddBinding;

public class OSAdd extends AppCompatActivity {
    ActivityOsAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_os_add);
        binding = ActivityOsAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);

        binding.imageButtonOSaddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();

                String id = binding.editTextOSaddID.getText().toString();
                String os = binding.editTextOSaddOS.getText().toString();
                if (!id.trim().isEmpty()) {
                    if (!os.trim().isEmpty()) {
                        String insert = "INSERT INTO HeDieuHanh ( MaHeDieuHanh, HeDieuHanh ) VALUES  ( '" + id + "',N'" + os + "' )";
                        int result = connect.execUpdateQuery(insert);
                        if (result > 0) {
                            binding.editTextOSaddID.setText("");
                            binding.editTextOSaddOS.setText("");
                            // xoay nut save
                            binding.imageButtonOSaddSave.startAnimation(animationrotate);
                            Toast.makeText(OSAdd.this, "Add success!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OSAdd.this, "Operator system is empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OSAdd.this, "ID is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // bat su kien nhan nut quay lai
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}