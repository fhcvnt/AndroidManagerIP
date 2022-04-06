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

import lacty.manager.ip.databinding.ActivityBuildingEditBinding;

public class BuildingEdit extends AppCompatActivity {
    ActivityBuildingEditBinding binding;
    private String idedit = "";// id edit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_edit);

        binding = ActivityBuildingEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);

        // gan gia tri ban dau
        Intent intent = getIntent();
        idedit=intent.getStringExtra("id");
        binding.editTextBuildingeditID.setText(idedit);
        binding.editTextBuildingeditBuilding.setText(intent.getStringExtra("building"));
        binding.editTextNumberBuildingeditIPClass.setText(intent.getStringExtra("ipclass"));

        binding.imageButtonBuildingeditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();

                String id = binding.editTextBuildingeditID.getText().toString();
                String building = binding.editTextBuildingeditBuilding.getText().toString();
                String ipclass = binding.editTextNumberBuildingeditIPClass.getText().toString();
                if (!id.trim().isEmpty()) {
                    if (!building.trim().isEmpty()) {
                        if (!ipclass.isEmpty()) {
                            String insert = "UPDATE Building SET MaBuilding='"+id+"', Building=N'"+building+"', LopIP="+ipclass+" WHERE MaBuilding='"+idedit+"'";
                            int result = connect.execUpdateQuery(insert);
                            if (result > 0) {
                                // xoay nut save
                                binding.imageButtonBuildingeditSave.startAnimation(animationrotate);
                                Toast.makeText(BuildingEdit.this, "Edit success!", Toast.LENGTH_SHORT).show();
                                Intent intentback=new Intent();
                                setResult(60,intentback);
                                finish();
                            }
                        } else {
                            Toast.makeText(BuildingEdit.this, "IP class is empty!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(BuildingEdit.this, "Building is empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BuildingEdit.this, "ID is empty!", Toast.LENGTH_SHORT).show();
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