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

import lacty.manager.ip.databinding.ActivityOfficeEditBinding;

public class OfficeEdit extends AppCompatActivity {
    ActivityOfficeEditBinding binding;
    String edit_id = "";
    String edit_office = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_edit);

        binding = ActivityOfficeEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        try {
            edit_id = intent.getStringExtra("id");
            edit_office = intent.getStringExtra("office");
            binding.editTextOfficeeditID.setText(edit_id);
            binding.editTextOfficeeditOffice.setText(edit_office);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);

        binding.imageButtonOfficeeditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();

                String id = binding.editTextOfficeeditID.getText().toString();
                String office = binding.editTextOfficeeditOffice.getText().toString();
                if (!id.trim().isEmpty()) {
                    if (!office.trim().isEmpty()) {
                        String update = "UPDATE Office SET MaOffice=" + id + ",Office=N'" + office + "' WHERE MaOffice=" + edit_id ;
                        int result = connect.execUpdateQuery(update);
                        if (result > 0) {
                            // xoay nut save
                            binding.imageButtonOfficeeditSave.startAnimation(animationrotate);
                            Toast.makeText(OfficeEdit.this, "Edit success!", Toast.LENGTH_SHORT).show();
                            Intent intentback = new Intent();
                            setResult(100, intentback);
                            finish();
                        }
                    } else {
                        Toast.makeText(OfficeEdit.this, "Office is empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OfficeEdit.this, "ID is empty!", Toast.LENGTH_SHORT).show();
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