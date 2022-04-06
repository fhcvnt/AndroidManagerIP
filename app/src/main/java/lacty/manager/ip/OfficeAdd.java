package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import lacty.manager.ip.databinding.ActivityOfficeAddBinding;

public class OfficeAdd extends AppCompatActivity {
    ActivityOfficeAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_add);

        binding = ActivityOfficeAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);

        binding.imageButtonOfficeaddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();

                String id = binding.editTextOfficeaddID.getText().toString();
                String office = binding.editTextOfficeaddOffice.getText().toString();
                if (!id.trim().isEmpty()) {
                    if (!office.trim().isEmpty()) {
                        String insert = "INSERT INTO Office ( MaOffice, Office ) VALUES  ( " + id + ",N'" + office + "' )";
                        int result = connect.execUpdateQuery(insert);
                        if (result > 0) {
                            binding.editTextOfficeaddID.setText("");
                            binding.editTextOfficeaddOffice.setText("");
                            // xoay nut save
                            binding.imageButtonOfficeaddSave.startAnimation(animationrotate);
                            Toast.makeText(OfficeAdd.this, "Add success!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OfficeAdd.this, "Office is empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OfficeAdd.this, "ID is empty!", Toast.LENGTH_SHORT).show();
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