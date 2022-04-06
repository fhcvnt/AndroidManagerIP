package lacty.manager.ip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lacty.manager.ip.databinding.ActivityManageripSearchBinding;

public class ManageripSearch extends AppCompatActivity {
    ActivityManageripSearchBinding binding;
    private String select = ""; // chuoi select dung de tra ve ket qua tim kiem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managerip_search);

        binding = ActivityManageripSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageViewManageripSearchSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
                Intent intent = new Intent();
                intent.putExtra("select", select);
                setResult(20, intent);
                finish();
            }
        });
    }

    private void search() {
        // text find
        String sothe = binding.editTextManageripSearchID.getText().toString().isEmpty() ? ""
                : " AND SoThe LIKE '%" + binding.editTextManageripSearchID.getText().toString() + "%'";
        String hoten = binding.editTextManageripSearchName.getText().toString().isEmpty() ? ""
                : " AND (HoTen LIKE N'%" + binding.editTextManageripSearchName.getText().toString()
                + "%' OR [dbo].[convertUnicodetoASCII](HoTen) LIKE '%" + binding.editTextManageripSearchName.getText().toString()
                + "%')";
        String donvi = binding.editTextManageripSearchDepartment.getText().toString().isEmpty() ? ""
                : " AND (DonVi.DonVi LIKE N'%" + binding.editTextManageripSearchDepartment.getText().toString()
                + "%' OR [dbo].[convertUnicodetoASCII](DonVi.DonVi) LIKE '%" + binding.editTextManageripSearchDepartment.getText().toString()
                + "%')";
        String toanha = binding.editTextManageripSearchBuilding.getText().toString().isEmpty() ? ""
                : " AND (Building.Building LIKE N'%" + binding.editTextManageripSearchBuilding.getText().toString()
                + "%' OR [dbo].[convertUnicodetoASCII](Building.Building) LIKE '%"
                + binding.editTextManageripSearchBuilding.getText().toString() + "%')";
        String email = binding.editTextManageripSearchEmail.getText().toString().isEmpty() ? ""
                : " AND Email LIKE '%" + binding.editTextManageripSearchEmail.getText().toString() + "%'";
        String ip = binding.editTextManageripSearchIP.getText().toString().isEmpty() ? "" : " AND IP LIKE '%" + binding.editTextManageripSearchIP.getText().toString() + "'";
        String os = binding.editTextManageripSearchOS.getText().toString().isEmpty() ? ""
                : " AND (HeDieuHanh.HeDieuHanh LIKE N'%" + binding.editTextManageripSearchOS.getText().toString()
                + "%' OR [dbo].[convertUnicodetoASCII](HeDieuHanh.HeDieuHanh) LIKE '%"
                + binding.editTextManageripSearchOS.getText().toString() + "%')";
        String office = binding.editTextManageripSearchOffice.getText().toString().isEmpty() ? ""
                : " AND (Office.Office LIKE N'%" + binding.editTextManageripSearchOffice.getText().toString()
                + "%' OR [dbo].[convertUnicodetoASCII](Office.Office) LIKE '%"
                + binding.editTextManageripSearchOffice.getText().toString() + "%')";
        String loaimay = binding.editTextManageripSearchMachinetype.getText().toString().isEmpty() ? ""
                : " AND (LoaiMay.LoaiMay LIKE N'%" + binding.editTextManageripSearchMachinetype.getText().toString()
                + "%' OR [dbo].[convertUnicodetoASCII](LoaiMay.LoaiMay) LIKE '%"
                + binding.editTextManageripSearchMachinetype.getText().toString() + "%')";

        // xử lý tìm theo lớp IP, nếu textIP nhận vào là lớp IP ví dụ: 30 thay vì nhận
        // được là 30.1 thì ta tìm theo lớp IP chứ không tìm theo IP
        int lopip = -1;
        try {
            lopip = Integer.parseInt(binding.editTextManageripSearchIP.getText().toString());
        } catch (NumberFormatException ne) {

        }
        if (lopip >= 0) {
            ip = binding.editTextManageripSearchIP.getText().toString().isEmpty() ? "" : " AND IP LIKE '%" + "192.168." + lopip + "." + "%'";
        }

        select = "SELECT SoThe,HoTen ,DonVi.DonVi,IP ,LoaiMay.LoaiMay,Email,HeDieuHanh.HeDieuHanh,Office.Office,Building.Building,GhiChu,NgayCapNhat,NguoiDung.TenNguoiDung FROM DanhSachIP LEFT JOIN DonVi ON DonVi.MaDonVi=DanhSachIP.MaDonVi LEFT JOIN dbo.LoaiMay ON LoaiMay.MaLoaiMay = DanhSachIP.MaLoaiMay LEFT JOIN dbo.HeDieuHanh ON HeDieuHanh.MaHeDieuHanh = DanhSachIP.MaHeDieuHanh LEFT JOIN dbo.Office ON Office.MaOffice = DanhSachIP.MaOffice LEFT JOIN Building ON DanhSachIp.MaBuilding=Building.MaBuilding LEFT JOIN NguoiDung ON DanhSachIP.UserUpdate=NguoiDung.MaNguoiDung WHERE 1=1"
                + sothe + hoten + donvi + toanha + email + ip + os + office + loaimay
                + " ORDER BY NgayCapNhat DESC" + "";
    }
}