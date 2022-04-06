package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lacty.manager.ip.databinding.ActivityUserAddBinding;

public class UserAdd extends AppCompatActivity {
    ActivityUserAddBinding binding;
    private String group = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        binding = ActivityUserAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);

        binding.radioButtonUseraddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.radioButtonUseraddUser.setChecked(true);
                binding.radioButtonUseraddAdmin.setChecked(false);
                group="User";
            }
        });

        binding.radioButtonUseraddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.radioButtonUseraddUser.setChecked(false);
                binding.radioButtonUseraddAdmin.setChecked(true);
                group="Admin";
            }
        });

        binding.imageButtonUseraddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();

                String id = binding.editTextUseraddID.getText().toString();
                String username = binding.editTextUseraddUsername.getText().toString();
                String personname = binding.editTextUseraddPersonname.getText().toString();
                String password=binding.editTextUseraddPassword.getText().toString();
                if (!id.trim().isEmpty()) {
                    if (!username.trim().isEmpty()) {
                        if (!personname.isEmpty()) {
                            if(!password.isEmpty()){
                                String insert = "INSERT INTO NguoiDung (MaNguoiDung,TenDangNhap,TenNhom,TenNguoiDung,MatKhau) VALUES ('"+ id + "','" + username + "','" + group+ "',N'" + personname + "','" + getMD5(password) + "')";
                                int result = connect.execUpdateQuery(insert);
                                if (result > 0) {
                                    binding.editTextUseraddID.setText("");
                                    binding.editTextUseraddUsername.setText("");
                                    binding.editTextUseraddPersonname.setText("");
                                    binding.editTextUseraddPassword.setText("");
                                    // xoay nut save
                                    v.startAnimation(animationrotate);
                                    Toast.makeText(UserAdd.this, "Add success!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(UserAdd.this, "Password is empty!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(UserAdd.this, "Person name is empty!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserAdd.this, "Username is empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserAdd.this, "ID is empty!", Toast.LENGTH_SHORT).show();
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

    // *********************************************************************************
    public String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}