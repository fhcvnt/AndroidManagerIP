package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.ResultSet;

import lacty.manager.ip.databinding.ActivityInternetBinding;

public class Internet extends AppCompatActivity {
    ActivityInternetBinding binding;
    private String text_seclect = ""; // chuoi select tim kiem
    private View select_view = null;
    private TableRow row_select = null; // dong duoc chon dung de xoa
    private TextView select_view_id = null;
    private TextView select_view_name = null;
    private TextView select_view_department = null;
    private TextView select_view_ip = null;
    private TextView select_view_website = null;
    private TextView select_view_recommendeddate = null;
    private TextView select_view_dateupdate = null;
    private TextView select_view_personupdate = null;
    private TextView select_view_building = null;
    private TextView select_view_note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        binding = ActivityInternetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // tao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuitemsearchSearch).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        // bat su kien thay doi du lieu tim kiem
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sqlSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty() || newText == null) {
                    // result search
                    sqlSearch("");
                } else {
                    // result search
                    sqlSearch(newText);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // bat su kien nhan nut quay lai
            finish();
        } else if (item.getItemId() == R.id.menuitemsearchSearch) {
            // Search
            sqlSearch("");
        }

        return super.onOptionsItemSelected(item);
    }

    // ham xoa du lieu bang table - count -1 dong, khong xoa dong tieu de
    public void clearTable() {
        int countrow = binding.tableInternet.getChildCount();
        binding.tableInternet.removeViews(1, countrow - 1);
    }

    public void sqlSearch(String chuoi) {
        clearTable();
        // Xử lý dữ liệu tìm kiếm, hiển thị lên Table
        try {
            ConnectSQL connect = new ConnectSQL();
            connect.setConnection();
            connect.setStatement();
            String select = "";

            String ip = chuoi.isEmpty() ? "" : " OR #DSIP.IP LIKE '%" + chuoi + "'";

            // xử lý tìm theo lớp IP, nếu textIP nhận vào là lớp IP ví dụ: 30 thay vì nhận
            // được là 30.1 thì ta tìm theo lớp IP chứ không tìm theo IP
            int lopip = -1;
            try {
                lopip = Integer.parseInt(chuoi);
            } catch (NumberFormatException ne) {

            }
            if (lopip >= 0) {
                ip = chuoi.isEmpty() ? ""
                        : " OR #DSIP.IP LIKE '%" + "192.168." + lopip + "." + "%'";
            }

            // text find
            String chuoitimkiem = chuoi.isEmpty() ? ""
                    : " AND (Internet.SoThe LIKE '%" + chuoi + "%'" + " OR (#DSIP.HoTen LIKE N'%" + chuoi
                    + "%' OR [dbo].[convertUnicodetoASCII](#DSIP.HoTen) LIKE '%" + chuoi
                    + "%')" + " OR (#DSIP.DonVi LIKE N'%" + chuoi
                    + "%' OR [dbo].[convertUnicodetoASCII](#DSIP.DonVi) LIKE '%" + chuoi
                    + "%')" + " OR Internet.Website LIKE N'%" + chuoi + "%'" + ip + ")";

            // sử dụng bảng tạm và left join
            select = "SELECT DanhSachIP.SoThe,DanhSachIP.HoTen,Donvi.DonVi,DanhSachIP.IP INTO #DSIP FROM DanhSachIP,DonVi WHERE DanhSachIP.MaDonVi=DonVi.MaDonVi";
            select = select + "\n"
                    + "SELECT Internet.SoThe,#DSIP.HoTen,#DSIP.DonVi,#DSIP.IP,Internet.Website,Internet.NgayDeNghi,Internet.NgayCapNhat,NguoiDung.TenNguoiDung FROM Internet LEFT JOIN #DSIP ON Internet.SoThe = #DSIP.SoThe LEFT JOIN NguoiDung ON Internet.UserUpdate=NguoiDung.MaNguoiDung WHERE 1=1"
                    + chuoitimkiem + " ORDER BY Internet.NgayCapNhat DESC";

            ResultSet result = connect.getStatement().executeQuery(select);
            int stt = 1;
            // doi dp to px
            int dp35 = (int) convertDpToPx(getApplicationContext(), 35);
            while (result.next()) {
                // định dạng lại ngày cập nhật thành dạng dd/MM/yyyy
                String ngaycapnhat = result.getString(7);
                try {
                    ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
                            + ngaycapnhat.substring(0, 4);
                } catch (Exception ie) {
                    ngaycapnhat = "";
                }

                // định dạng lại ngày đề nghị thành dạng dd/MM/yyyy
                String ngaydenghi = result.getString(6);
                try {
                    ngaydenghi = ngaydenghi.substring(8, 10) + "/" + ngaydenghi.substring(5, 7) + "/"
                            + ngaydenghi.substring(0, 4);
                } catch (Exception ie) {
                    ngaydenghi = "";
                }

                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.setBackground(getDrawable(R.drawable.cell_border));

                TextView textview = new TextView(this);
                textview.setText("" + stt);
                textview.setPadding(5, 0, 5, 0);
                textview.setHeight(dp35);
                textview.setBackground(getDrawable(R.drawable.cell_border));
                textview.setTextSize(20);
                textview.setGravity(Gravity.CENTER_VERTICAL);
                textview.setTypeface(null, Typeface.NORMAL);

                TextView textview2 = new TextView(this);
                textview2.setText(result.getString(1));
                textview2.setPadding(5, 0, 5, 0);
                textview2.setHeight(dp35);
                textview2.setBackground(getDrawable(R.drawable.cell_border));
                textview2.setTextSize(20);
                textview2.setGravity(Gravity.CENTER_VERTICAL);
                textview2.setTypeface(null, Typeface.NORMAL);

                TextView textview3 = new TextView(this);
                textview3.setText(result.getString(2));
                textview3.setPadding(5, 0, 5, 0);
                textview3.setHeight(dp35);
                textview3.setBackground(getDrawable(R.drawable.cell_border));
                textview3.setTextSize(20);
                textview3.setGravity(Gravity.CENTER_VERTICAL);
                textview3.setTypeface(null, Typeface.NORMAL);

                TextView textview4 = new TextView(this);
                textview4.setText(result.getString(3));
                textview4.setPadding(5, 0, 5, 0);
                textview4.setHeight(dp35);
                textview4.setBackground(getDrawable(R.drawable.cell_border));
                textview4.setTextSize(20);
                textview4.setGravity(Gravity.CENTER_VERTICAL);
                textview4.setTypeface(null, Typeface.NORMAL);

                TextView textview5 = new TextView(this);
                textview5.setText(result.getString(4));
                textview5.setPadding(5, 0, 5, 0);
                textview5.setHeight(dp35);
                textview5.setBackground(getDrawable(R.drawable.cell_border));
                textview5.setTextSize(20);
                textview5.setGravity(Gravity.CENTER_VERTICAL);
                textview5.setTypeface(null, Typeface.NORMAL);

                TextView textview6 = new TextView(this);
                textview6.setText(result.getString(5));
                textview6.setPadding(5, 0, 5, 0);
                textview6.setHeight(dp35);
                textview6.setBackground(getDrawable(R.drawable.cell_border));
                textview6.setTextSize(20);
                textview6.setGravity(Gravity.CENTER_VERTICAL);
                textview6.setTypeface(null, Typeface.NORMAL);

                TextView textview7 = new TextView(this);
                textview7.setText(ngaydenghi);
                textview7.setPadding(5, 0, 5, 0);
                textview7.setHeight(dp35);
                textview7.setBackground(getDrawable(R.drawable.cell_border));
                textview7.setTextSize(20);
                textview7.setGravity(Gravity.CENTER_VERTICAL);
                textview7.setTypeface(null, Typeface.NORMAL);

                TextView textview8 = new TextView(this);
                textview8.setText(ngaycapnhat);
                textview8.setPadding(5, 0, 5, 0);
                textview8.setHeight(dp35);
                textview8.setBackground(getDrawable(R.drawable.cell_border));
                textview8.setTextSize(20);
                textview8.setGravity(Gravity.CENTER_VERTICAL);
                textview8.setTypeface(null, Typeface.NORMAL);

                TextView textview9 = new TextView(this);
                textview9.setText(result.getString(8));
                textview9.setPadding(5, 0, 5, 0);
                textview9.setHeight(dp35);
                textview9.setBackground(getDrawable(R.drawable.cell_border));
                textview9.setTextSize(20);
                textview9.setGravity(Gravity.CENTER_VERTICAL);
                textview9.setTypeface(null, Typeface.NORMAL);

                tr.addView(textview);
                tr.addView(textview2);
                tr.addView(textview3);
                tr.addView(textview4);
                tr.addView(textview5);
                tr.addView(textview6);
                tr.addView(textview7);
                tr.addView(textview8);
                tr.addView(textview9);

                tr.setClickable(true);
                textview.setOnClickListener(onClickListenerData(textview, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, tr));
                textview2.setOnClickListener(onClickListenerData(textview2, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, tr));
                textview3.setOnClickListener(onClickListenerData(textview3, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, tr));
                textview4.setOnClickListener(onClickListenerData(textview4, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, tr));
                textview5.setOnClickListener(onClickListenerData(textview5, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, tr));
                textview6.setOnClickListener(onClickListenerData(textview6, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, tr));
                textview7.setOnClickListener(onClickListenerData(textview7, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, tr));
                textview8.setOnClickListener(onClickListenerData(textview8, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, tr));
                textview9.setOnClickListener(onClickListenerData(textview9, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, tr));

                binding.tableInternet.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                stt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener onClickListenerData(View view, TextView view_id, TextView view_name, TextView view_department, TextView view_ip, TextView view_website, TextView view_recommendeddate, TextView view_dateupdate, TextView view_personupdate, TableRow row) {
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_view != null) {
                    select_view.setBackground(getDrawable(R.drawable.cell_border));
                }
                v.setBackground(getDrawable(R.drawable.select_row));
                select_view_id = view_id;
                select_view_name = view_name;
                select_view_department = view_department;
                select_view_ip = view_ip;
                select_view_website = view_website;
                select_view_recommendeddate = view_recommendeddate;
                select_view_dateupdate = view_dateupdate;
                select_view_personupdate = view_personupdate;
                row_select = row;
                select_view = v;
            }
        };
        return click;
    }

    // dp to px
    public static float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}