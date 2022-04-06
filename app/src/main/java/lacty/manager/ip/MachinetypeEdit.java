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

import lacty.manager.ip.databinding.ActivityMachinetypeEditBinding;

public class MachinetypeEdit extends AppCompatActivity {
    ActivityMachinetypeEditBinding binding;
    String edit_id = "";
    String edit_machinetype = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machinetype_edit);

        binding = ActivityMachinetypeEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        try {
            edit_id = intent.getStringExtra("id");
            edit_machinetype = intent.getStringExtra("machinetype");
            binding.editTextMachinetypeeditID.setText(edit_id);
            binding.editTextMachinetypeeditMachinetype.setText(edit_machinetype);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);

        binding.imageButtonMachinetypeeditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();

                String id = binding.editTextMachinetypeeditID.getText().toString();
                String machinetype = binding.editTextMachinetypeeditMachinetype.getText().toString();
                if (!id.trim().isEmpty()) {
                    if (!machinetype.trim().isEmpty()) {
                        String update = "UPDATE LoaiMay SET MaLoaiMay='" + id + "',LoaiMay=N'" + machinetype + "' WHERE MaLoaiMay='" + edit_id + "'";
                        int result = connect.execUpdateQuery(update);
                        if (result > 0) {
                            // xoay nut save
                            binding.imageButtonMachinetypeeditSave.startAnimation(animationrotate);
                            Toast.makeText(MachinetypeEdit.this, "Edit success!", Toast.LENGTH_SHORT).show();
                            Intent intentback = new Intent();
                            setResult(2, intentback);
                            finish();
                        }
                    } else {
                        Toast.makeText(MachinetypeEdit.this, "Machine type is empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MachinetypeEdit.this, "ID is empty!", Toast.LENGTH_SHORT).show();
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