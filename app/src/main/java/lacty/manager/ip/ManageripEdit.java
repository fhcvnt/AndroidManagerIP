package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

import lacty.manager.ip.databinding.ActivityManageripEditBinding;

public class ManageripEdit extends AppCompatActivity {
    ActivityManageripEditBinding binding;
    ConnectSQL connect;
    private String userlogin = ""; // user dang dang nhap
    private String mabuilding = "";
    private String madonvi = "";
    private String maos = "";
    private String maoffice = "";
    private String maloaimay = "";
    private String lopipbuilding = "";
    private String id_edit = "";
    ArrayList<String> arrayListbuilding;
    ArrayList<String> arrayListos;
    ArrayList<String> arrayListoffice;
    ArrayList<String> arrayListmachinetype;
    private String nameedit = "";
    private String departmentedit = "";
    private String ipedit = "";
    private String machinetypeedit = "";
    private String emailedit = "";
    private String osedit = "";
    private String officeedit = "";
    private String buildingedit = "";
    private String noteedit = "";
    private int countedit = 0; // truong hop moi mo khong cho thay doi gia tri IP khi spinner ip duoc chon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managerip_add);

        binding = ActivityManageripEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao rotate xoay
        Animation animationrotate = AnimationUtils.loadAnimation(this, R.anim.rotate_view_360);

        // tao nu quay lai tren tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // gan gia tri ban dau
        Intent intent = getIntent();
        userlogin = intent.getStringExtra("userlogin");
        id_edit = intent.getStringExtra("id");
        nameedit = intent.getStringExtra("name");
        departmentedit = intent.getStringExtra("department");
        ipedit = intent.getStringExtra("ip");
        machinetypeedit = intent.getStringExtra("machinetype");
        emailedit = intent.getStringExtra("email");
        osedit = intent.getStringExtra("os");
        officeedit = intent.getStringExtra("office");
        buildingedit = intent.getStringExtra("building");
        noteedit = intent.getStringExtra("note");

        binding.editTextManageripEditID.setText(id_edit);
        binding.editTextManageripEditName.setText(nameedit);
        binding.editTextManageripEditDepartment.setText(departmentedit);
        binding.editTextManageripEditEmail.setText(emailedit);
        binding.editTextManageripEditNote.setText(noteedit);
        binding.editTextManageripEditIP.setText(ipedit);

        // tinh ma don vi luc ban dau
        try {
            connect = new ConnectSQL();
            connect.setConnection();
            connect.setStatement();

            String select = "SELECT Person.Person_Name,Department.Department_Name,Person.Department_Serial_Key FROM SV4.HRIS.dbo.Data_person AS Person,SV4.HRIS.dbo.Data_Department AS Department WHERE Person.Department_Serial_Key=Department.Department_Serial_Key AND Person.Person_Status=1 AND (Person.Person_ID='" + binding.editTextManageripEditID.getText().toString() + "' OR Person.Person_ID='" + binding.editTextManageripEditID.getText().toString() + "' OR Person.Person_ID='" + binding.editTextManageripEditID.getText().toString() + "' OR Person.Person_ID='" + binding.editTextManageripEditID.getText().toString() + "')";
            ResultSet result = connect.getStatement().executeQuery(select);
            int dem = 0;
            while (result.next()) {
                binding.editTextManageripEditName.setText(result.getString(1));
                binding.editTextManageripEditDepartment.setText(result.getString(2));
                madonvi = result.getString(3);
                dem = 1;
            }
            if (dem == 0) {
                binding.editTextManageripEditName.setText("");
                binding.editTextManageripEditDepartment.setText("");
            }
            result.close();
            connect.closeStatement();
            connect.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        connect = new ConnectSQL();
        connect.setConnection();
        arrayListbuilding = new ArrayList<>();
        arrayListos = new ArrayList<>();
        arrayListoffice = new ArrayList<>();
        arrayListmachinetype = new ArrayList<>();
        if (connect.getConnection() != null) {
            // lay du lieu co spinner Building
            String select = "SELECT Building FROM Building ORDER BY Building ASC";
            arrayListbuilding = connect.getArraySeclect(select);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayListbuilding);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            binding.spinnerManageripEditBuilding.setAdapter(adapter);

            // lay du lieu co spinner OS
            select = "SELECT HeDieuHanh FROM HeDieuHanh ORDER BY HeDieuHanh ASC";
            arrayListos = connect.getArraySeclect(select);
            ArrayAdapter<String> adapteros = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayListos);
            adapteros.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            binding.spinnerManageripEditOS.setAdapter(adapteros);

            // lay du lieu co spinner Office
            select = "SELECT Office FROM Office ORDER BY Office ASC";
            arrayListoffice = connect.getArraySeclect(select);
            ArrayAdapter<String> adapteroffice = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayListoffice);
            adapteroffice.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            binding.spinnerManageripEditOffice.setAdapter(adapteroffice);

            // lay du lieu co spinner Machine type
            select = "SELECT LoaiMay FROM LoaiMay ORDER BY LoaiMay ASC";
            arrayListmachinetype = connect.getArraySeclect(select);
            ArrayAdapter<String> adaptermachinetype = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayListmachinetype);
            adaptermachinetype.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            binding.spinnerManageripEditMachinetype.setAdapter(adaptermachinetype);
        }
        try {
            connect.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // gan cac gia tri spinner con lai
        for (int i = 0; i < arrayListbuilding.size(); i++) {
            if (arrayListbuilding.get(i).equals(buildingedit)) {
                binding.spinnerManageripEditBuilding.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < arrayListos.size(); i++) {
            if (arrayListos.get(i).equals(osedit)) {
                binding.spinnerManageripEditOS.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < arrayListoffice.size(); i++) {
            if (arrayListoffice.get(i).equals(officeedit)) {
                binding.spinnerManageripEditOffice.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < arrayListmachinetype.size(); i++) {
            if (arrayListmachinetype.get(i).equals(machinetypeedit)) {
                binding.spinnerManageripEditMachinetype.setSelection(i);
                break;
            }
        }

        // bat su kien thay doi building
        binding.spinnerManageripEditBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = "SELECT LopIP,MaBuilding FROM Building WHERE Building=N'" + arrayListbuilding.get(position).toString() + "'";
                try {
                    connect = new ConnectSQL();
                    connect.setConnection();
                    connect.setStatement();
                    ResultSet result = connect.getStatement().executeQuery(select);
                    while (result.next()) {
                        lopipbuilding = result.getString(1);
                        if (countedit > 0) {
                            binding.editTextManageripEditIP.setText("192.168." + result.getString(1) + ".");
                        } else {
                            binding.editTextManageripEditIP.setText(ipedit);
                            countedit = 1;
                        }
                        mabuilding = result.getString(2);
                    }
                    result.close();
                    connect.closeStatement();
                    connect.closeConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // bat su kien thay doi he dieu hanh
        binding.spinnerManageripEditOS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = "SELECT MaHeDieuHanh FROM HeDieuHanh WHERE HeDieuHanh=N'" + arrayListos.get(position).toString() + "'";
                try {
                    connect = new ConnectSQL();
                    connect.setConnection();
                    connect.setStatement();
                    ResultSet result = connect.getStatement().executeQuery(select);
                    while (result.next()) {
                        maos = result.getString(1);
                    }
                    result.close();
                    connect.closeStatement();
                    connect.closeConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // bat su kien thay doi office
        binding.spinnerManageripEditOffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = "SELECT MaOffice FROM Office WHERE Office=N'" + arrayListoffice.get(position).toString() + "'";
                try {
                    connect = new ConnectSQL();
                    connect.setConnection();
                    connect.setStatement();
                    ResultSet result = connect.getStatement().executeQuery(select);
                    while (result.next()) {
                        maoffice = result.getString(1);
                    }
                    result.close();
                    connect.closeStatement();
                    connect.closeConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // bat su kien thay doi Loai may
        binding.spinnerManageripEditMachinetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = "SELECT MaLoaiMay FROM LoaiMay WHERE LoaiMay=N'" + arrayListmachinetype.get(position).toString() + "'";
                try {
                    connect = new ConnectSQL();
                    connect.setConnection();
                    connect.setStatement();
                    ResultSet result = connect.getStatement().executeQuery(select);
                    while (result.next()) {
                        maloaimay = result.getString(1);
                    }
                    result.close();
                    connect.closeStatement();
                    connect.closeConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // bat su kien thay doi du lieu tren text ID - so the
        binding.editTextManageripEditID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    connect = new ConnectSQL();
                    connect.setConnection();
                    connect.setStatement();

                    String select = "SELECT Person.Person_Name,Department.Department_Name,Person.Department_Serial_Key FROM SV4.HRIS.dbo.Data_person AS Person,SV4.HRIS.dbo.Data_Department AS Department WHERE Person.Department_Serial_Key=Department.Department_Serial_Key AND Person.Person_Status=1 AND (Person.Person_ID='" + binding.editTextManageripEditID.getText().toString() + "' OR Person.Person_ID='" + binding.editTextManageripEditID.getText().toString() + "' OR Person.Person_ID='" + binding.editTextManageripEditID.getText().toString() + "' OR Person.Person_ID='" + binding.editTextManageripEditID.getText().toString() + "')";
                    ResultSet result = connect.getStatement().executeQuery(select);
                    int dem = 0;
                    while (result.next()) {
                        binding.editTextManageripEditName.setText(result.getString(1));
                        binding.editTextManageripEditDepartment.setText(result.getString(2));
                        madonvi = result.getString(3);
                        dem = 1;
                    }
                    if (dem == 0) {
                        binding.editTextManageripEditName.setText("");
                        binding.editTextManageripEditDepartment.setText("");
                    }
                    result.close();
                    connect.closeStatement();
                    connect.closeConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // button luu
        binding.imageViewManageripEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.editTextManageripEditID.getText().toString().isEmpty() && !binding.editTextManageripEditName.getText().toString().isEmpty() && !binding.editTextManageripEditIP.getText().toString().isEmpty()) {
                    if (validate(binding.editTextManageripEditIP.getText().toString())) {
                        try {
                            String lopipofip = binding.editTextManageripEditIP.getText().toString();
                            lopipofip = lopipofip.substring(0, lopipofip.lastIndexOf("."));

                            if (lopipofip.equals("192.168." + lopipbuilding)) {
                                try {
                                    ConnectSQL connectdonvi = new ConnectSQL();
                                    connectdonvi.setConnection();
                                    String insertdonvi = "INSERT INTO DonVi(MaDonVi,DonVi) VALUES ('" + madonvi + "',N'" + binding.editTextManageripEditDepartment.getText().toString() + "')";
                                    connectdonvi.execUpdateQuery(insertdonvi);
                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }
                                String updateip = "UPDATE DanhSachIP SET SoThe='" + binding.editTextManageripEditID.getText().toString() + "',HoTen=N'" + binding.editTextManageripEditName.getText().toString() + "',MaDonVi='" + madonvi + "',IP='" + binding.editTextManageripEditIP.getText().toString() + "',MaLoaiMay='" + maloaimay + "',Email='" + binding.editTextManageripEditEmail.getText().toString() + "',MaHeDieuHanh='" + maos + "',MaOffice='" + maoffice + "',MaBuilding='" + mabuilding + "',GhiChu=N'" + binding.editTextManageripEditNote.getText().toString() + "',NgayCapNhat=GETDATE(),UserUpdate='" + userlogin + "' WHERE SoThe='" + id_edit + "'";

                                connect = new ConnectSQL();
                                connect.setConnection();
                                int result = connect.execUpdateQuery(updateip);
                                if (result > 0) {
                                    try {
                                        binding.editTextManageripEditID.setText("");
                                        binding.editTextManageripEditName.setText("");
                                        binding.editTextManageripEditDepartment.setText("");
                                        binding.editTextManageripEditEmail.setText("");
                                        binding.editTextManageripEditNote.setText("");
                                        binding.editTextManageripEditIP.setText("");
                                        Toast.makeText(ManageripEdit.this, "Save success!", Toast.LENGTH_SHORT).show();
                                        binding.imageViewManageripEditSave.setAnimation(animationrotate);
                                        finish();
                                    } catch (Exception ex) {
                                    }
                                }
                            } else {
                                Toast.makeText(ManageripEdit.this, "IP error!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            Toast.makeText(ManageripEdit.this, "Save failed!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ManageripEdit.this, "Wrong ip format!", Toast.LENGTH_SHORT).show();
                    }
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

    // Kiểm tra định dạng IP
    public static boolean validate(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(PATTERN);
    }
}