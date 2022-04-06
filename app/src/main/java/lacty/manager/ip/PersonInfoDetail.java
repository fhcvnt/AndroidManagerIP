package lacty.manager.ip;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lacty.manager.ip.databinding.ActivityPersoninfoDetailBinding;

public class PersonInfoDetail extends AppCompatActivity {
    ActivityPersoninfoDetailBinding binding;
    private Bitmap imagesave;
    private String idsave=""; // id dung de luu hinh, vd 21608.jpg

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo_detail);

        binding = ActivityPersoninfoDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // kiem tra cap quyen luu hinh anh
        checkAndRequestPermissions();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        idsave=id;

        String select = "SELECT Person.Person_ID,Person.Person_Name,Department.Department_Name,CASE Person.Gender WHEN 0 THEN N'Nữ' ELSE N'Nam' END AS Gender,Person.Birthday,Detail.Mobilephone_Number,CASE Person.Person_Status WHEN 0 THEN N'Đã nghỉ' ELSE N'Đang làm' END AS Status,Person.Person_Image FROM SV4.HRIS.dbo.Data_Person AS Person,SV4.HRIS.dbo.Data_Department AS Department,SV4.HRIS.dbo.Data_Person_Detail AS Detail WHERE Person.Department_Serial_Key=Department.Department_Serial_Key AND Person.Person_Serial_Key=Detail.Person_Serial_Key AND Person.Person_ID='" + id + "' AND Person.Person_Name=N'" + name + "'";
        try {
            ConnectSQL connect = new ConnectSQL();
            connect.setConnection();
            connect.setStatement();
            ResultSet result = connect.getStatement().executeQuery(select);
            while (result.next()) {
                // doi gia tri cho cot sinh nhat Birthday dd/MM/yyyy
                String birthdaytext = "";
                try {
                    birthdaytext = result.getString(5);
                    birthdaytext = birthdaytext.substring(8, 10) + "/" + birthdaytext.substring(5, 7) + "/"
                            + birthdaytext.substring(0, 4);
                } catch (Exception w) {
                    w.printStackTrace();
                }

                binding.textViewPersonInfoDetailID2.setText(result.getString(1));
                binding.textViewPersonInfoDetailName2.setText(result.getString(2));
                binding.textViewPersonInfoDetailDepartment2.setText(result.getString(3));
                binding.textViewPersonInfoDetailGender2.setText(result.getString(4));
                binding.textViewPersonInfoDetailBirthday2.setText(birthdaytext);
                binding.textViewPersonInfoDetailPhone2.setText(result.getString(6));
                binding.textViewPersonInfoDetailStatus2.setText(result.getString(7));

                // lay anh gan len textview
                try {
                    InputStream inputStream = result.getBinaryStream(8);
                    imagesave = BitmapFactory.decodeStream(inputStream);
                    binding.imageViewPersonInfoDetailImage.setImageBitmap(imagesave);
                } catch (Exception ex) {
                }
                break;
            }
            result.close();
            connect.closeStatement();
            connect.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.imageViewPersonInfoDetailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitmap(imagesave,idsave,getApplicationContext());
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

    private static void SaveImage(Bitmap finalBitmap, String filename, Context context) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/HRpicture");
        myDir.mkdirs();

        String fname = "image-" + filename + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Toast.makeText(context, "Save success!", Toast.LENGTH_SHORT).show();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ham luu hinh
    public void saveBitmap(Bitmap bitmap, String name,Context context) {
        try {
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/HRpictures/");
            if (!folder.exists()) {
                // tao thu muc, create folder
                if(!folder.mkdirs()){
                    Toast.makeText(context, "Thư mục HRpictures không được tạo!", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception fe) {
        }
        File imagePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/HRpictures/" + name + ".jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Toast.makeText(context, "Save success!", Toast.LENGTH_SHORT).show();
            fos.flush();
            fos.close();
        } catch (Exception e) {
        }

    }

    // hien thi hop thoai cap quyen cho ung dung de truy cap vao bo nho de luu tru ngoai
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
}