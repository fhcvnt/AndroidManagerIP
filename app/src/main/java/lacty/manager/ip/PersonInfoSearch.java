package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import lacty.manager.ip.databinding.ActivityPersoninfoSearchBinding;

public class PersonInfoSearch extends AppCompatActivity {
    ActivityPersoninfoSearchBinding binding;
    private String selectsql = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo_search);

        binding = ActivityPersoninfoSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConnectSQL connect = new ConnectSQL();
        connect.setConnection();
        ArrayList<String> arrayList = new ArrayList<>();
        if (connect.getConnection() != null) {
            // lay du lieu co spinner Department
            String select = "SELECT Department_Name FROM SV4.HRIS.dbo.Data_Department WHERE Department_Name NOT LIKE N'Z%' ORDER BY Department_Name ASC";
            arrayList = connect.getArraySeclect(select);
            arrayList.add(0, "");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            binding.spinnerPersonInfoSearchDepartment.setAdapter(adapter);
        }
        try {
            connect.closeStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // lay du lieu cho spinner Gender
        String[] gender = {"", "Nam", "Nữ"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        binding.spinnerPersonInfoSearchGender.setAdapter(adapter);

        // lay du lieu cho spinner Status
        String[] status = {"", "Đang làm", "Đã nghỉ"};
        ArrayAdapter<String> adapterstatus = new ArrayAdapter<String>(this, R.layout.spinner_item, status);
        adapterstatus.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        binding.spinnerPersonInfoSearchStatus.setAdapter(adapterstatus);

        ArrayList<String> ArrayListdepartment = arrayList;
        binding.imageViewPersonInfoSearchSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // text find
                String sothe = binding.editTextPersonInfoSearchID.getText().toString().isEmpty() ? ""
                        : " AND DataPerson.Person_ID LIKE '%" + binding.editTextPersonInfoSearchID.getText().toString() + "%'";
                String hoten = binding.editTextPersonInfoSearchName.getText().toString().isEmpty() ? ""
                        : " AND DataPerson.Person_Name LIKE N'%" + binding.editTextPersonInfoSearchName.getText().toString() + "%'";
                String gender = "";
                if (binding.spinnerPersonInfoSearchGender.getSelectedItemPosition() == 1) {
                    // nam
                    gender = "1";
                } else if (binding.spinnerPersonInfoSearchGender.getSelectedItemPosition() == 2) {
                    // nu
                    gender = "0";
                } else {
                    gender = "";
                }
                String gioitinh = gender.isEmpty() ? "" : " AND DataPerson.Gender='" + gender + "'";
                String donvi = ArrayListdepartment.get(binding.spinnerPersonInfoSearchDepartment.getSelectedItemPosition()).isEmpty() ? ""
                        : " AND DataDepartment.Department_Name=N'" + ArrayListdepartment.get(binding.spinnerPersonInfoSearchDepartment.getSelectedItemPosition()) + "'";

                String status = "";
                if (binding.spinnerPersonInfoSearchStatus.getSelectedItemPosition() == 1) {
                    // dang lam
                    status = "1";
                } else if (binding.spinnerPersonInfoSearchStatus.getSelectedItemPosition() == 2) {
                    // da nghi
                    status = "0";
                } else {
                    status = "";
                }
                String trangthai = binding.spinnerPersonInfoSearchStatus.getSelectedItemPosition() == 0 ? ""
                        : " AND DataPerson.Person_Status='" + status + "'";

                selectsql = "SELECT DataPerson.Person_ID,DataPerson.Person_Name,DataDepartment.Department_Name,DataPerson.Gender,DataPerson.Birthday,DataPersonDetail.Mobilephone_Number,DataPerson.Home_Address,DataPerson.Date_Come_In,DataPerson.Person_Status FROM SV4.HRIS.dbo.Data_Person AS DataPerson,SV4.HRIS.dbo.Data_Department AS DataDepartment,SV4.HRIS.dbo.Data_Person_Detail AS DataPersonDetail WHERE DataPerson.Department_Serial_Key=DataDepartment.Department_Serial_Key AND DataPerson.Person_ID=DataPersonDetail.Person_ID"
                        + sothe + hoten + gioitinh + donvi + trangthai
                        + " ORDER BY DataPerson.Person_ID ASC";
                Intent intent = new Intent();
                intent.putExtra("select", selectsql);
                setResult(10, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // su kien nhan vao nut quay lai
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}