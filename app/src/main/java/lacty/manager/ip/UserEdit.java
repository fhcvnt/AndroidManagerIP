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

import java.math.BigInteger;
import java.security.MessageDigest;

import lacty.manager.ip.databinding.ActivityUserEditBinding;

public class UserEdit extends AppCompatActivity {
    ActivityUserEditBinding binding;
    private String group = "User";
    private String idedit = "";
    private String usernameedit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        binding = ActivityUserEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);

        // lay gia tri ban dau
        Intent intent = getIntent();
        idedit = intent.getStringExtra("id");
        group = intent.getStringExtra("group");
        usernameedit=intent.getStringExtra("username");
        binding.editTextUsereditID.setText(idedit);
        binding.editTextUsereditUsername.setText(usernameedit);
        binding.editTextUsereditPersonname.setText(intent.getStringExtra("personname"));
        if (intent.getStringExtra("group").equals("User")) {
            binding.radioButtonUsereditUser.setChecked(true);
            binding.radioButtonUsereditAdmin.setChecked(false);
            group = "User";
        } else {
            binding.radioButtonUsereditUser.setChecked(false);
            binding.radioButtonUsereditAdmin.setChecked(true);
            group = "Admin";
        }

        binding.radioButtonUsereditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.radioButtonUsereditUser.setChecked(true);
                binding.radioButtonUsereditAdmin.setChecked(false);
                group = "User";
            }
        });

        binding.radioButtonUsereditAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.radioButtonUsereditUser.setChecked(false);
                binding.radioButtonUsereditAdmin.setChecked(true);
                group = "Admin";
            }
        });

        binding.imageButtonUsereditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();

                String id = binding.editTextUsereditID.getText().toString();
                String username = binding.editTextUsereditUsername.getText().toString();
                String personname = binding.editTextUsereditPersonname.getText().toString();
                String password = binding.editTextUsereditPassword.getText().toString();
                if (!id.trim().isEmpty()) {
                    if (!username.trim().isEmpty()) {
                        if (!personname.isEmpty()) {
                            String pass = binding.editTextUsereditPassword.getText().toString().isEmpty() ? ""
                                    : ",MatKhau='" + getMD5(binding.editTextUsereditPassword.getText().toString()) + "'";
                                String update = "UPDATE NguoiDung SET MaNguoiDung='" + binding.editTextUsereditID.getText().toString() + "',TenNhom='"+ group + "',TenNguoiDung=N'" + binding.editTextUsereditPersonname.getText().toString() + "'" + pass+ " WHERE TenDangNhap='" + usernameedit + "'";
                                int result = connect.execUpdateQuery(update);
                                if (result > 0) {
                                    binding.editTextUsereditID.setText("");
                                    binding.editTextUsereditUsername.setText("");
                                    binding.editTextUsereditPersonname.setText("");
                                    binding.editTextUsereditPassword.setText("");
                                    // xoay nut save
                                    v.startAnimation(animationrotate);
                                    Toast.makeText(UserEdit.this, "Add success!", Toast.LENGTH_SHORT).show();
                                }
                        } else {
                            Toast.makeText(UserEdit.this, "Person name is empty!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserEdit.this, "Username is empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserEdit.this, "ID is empty!", Toast.LENGTH_SHORT).show();
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