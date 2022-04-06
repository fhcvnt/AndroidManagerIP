package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import lacty.manager.ip.databinding.ActivityMachinetypeAddBinding;

public class MachineTypeAdd extends AppCompatActivity {
    ActivityMachinetypeAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machinetype_add);

        binding = ActivityMachinetypeAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);
        Animation animationtranslate = AnimationUtils.loadAnimation(this, R.anim.translate_back);

        binding.imageButtonMachinetypeaddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();

                String id = binding.editTextMachinetypeaddID.getText().toString();
                String machinetype = binding.editTextMachinetypeaddMachinetype.getText().toString();
                if (!id.trim().isEmpty()) {
                    if (!machinetype.trim().isEmpty()) {
                        String insert = "INSERT INTO LoaiMay ( MaLoaiMay, LoaiMay ) VALUES  ( '" + id + "',N'" + machinetype + "' )";
                        int result = connect.execUpdateQuery(insert);
                        if (result > 0) {
                            binding.editTextMachinetypeaddID.setText("");
                            binding.editTextMachinetypeaddMachinetype.setText("");
                            // xoay nut save
                            binding.imageButtonMachinetypeaddSave.startAnimation(animationrotate);
                            Toast.makeText(MachineTypeAdd.this, "Add success!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MachineTypeAdd.this, "Machine type is empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MachineTypeAdd.this, "ID is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            // bat su kien nhan nut quay lai
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
