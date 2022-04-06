package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import lacty.manager.ip.databinding.ActivityBuildingAddBinding;

public class BuildingAdd extends AppCompatActivity {
ActivityBuildingAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_add);

        binding=ActivityBuildingAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);

        binding.imageButtonBuildingaddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();

                String id = binding.editTextBuildingaddID.getText().toString();
                String Building = binding.editTextBuildingaddBuilding.getText().toString();
                String ipclass=binding.editTextNumberBuildingaddIPClass.getText().toString();
                if (!id.trim().isEmpty()) {
                    if (!Building.trim().isEmpty()) {
                       if(!ipclass.isEmpty()){
                           String insert = "INSERT INTO Building ( MaBuilding, Building, LopIP ) VALUES  ( '" + id + "',N'" + Building + "',"+ipclass+" )";
                           int result = connect.execUpdateQuery(insert);
                           if (result > 0) {
                               binding.editTextBuildingaddID.setText("");
                               binding.editTextBuildingaddBuilding.setText("");
                               binding.editTextNumberBuildingaddIPClass.setText("");
                               // xoay nut save
                               binding.imageButtonBuildingaddSave.startAnimation(animationrotate);
                               Toast.makeText(BuildingAdd.this, "Add success!", Toast.LENGTH_SHORT).show();
                           }
                       }else{
                           Toast.makeText(BuildingAdd.this, "IP class is empty!", Toast.LENGTH_SHORT).show();
                       }
                    } else {
                        Toast.makeText(BuildingAdd.this, "Building is empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BuildingAdd.this, "ID is empty!", Toast.LENGTH_SHORT).show();
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