package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.ResultSet;

import lacty.manager.ip.databinding.ActivityManagerIpBinding;

public class ManagerIP extends AppCompatActivity {
    ActivityManagerIpBinding binding;
    private String text_seclect = ""; // chuoi select tim kiem
    private View select_view = null;
    private TableRow row_select = null; // dong duoc chon dung de xoa
    private TextView select_view_id = null;
    private TextView select_view_name = null;
    private TextView select_view_department = null;
    private TextView select_view_ip = null;
    private TextView select_view_machinetype = null;
    private TextView select_view_email = null;
    private TextView select_view_os = null;
    private TextView select_view_office = null;
    private TextView select_view_building = null;
    private TextView select_view_note = null;
    private String userlogin = ""; // user dang dang nhap
    private String grouplogin = ""; // group login=Admin thi moi cho Add, Edit, delete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_ip);

        binding = ActivityManagerIpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        userlogin = intent.getStringExtra("userlogin");
        grouplogin = intent.getStringExtra("grouplogin");
    }

    // tao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_managerip, menu);
        if (!grouplogin.equals("Admin")) {
            menu.removeItem(R.id.menuitemmanageripAdd);
            menu.removeItem(R.id.menuitemmanageripEdit);
            menu.removeItem(R.id.menuitemmanageripDelete);
            menu.removeItem(R.id.menuitemmanageripRefresh);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // bat su kien nhan nut quay lai
            finish();
        } else if (item.getItemId() == R.id.menuitemmanageripGetip) {
            // Get IP
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), GetIP.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuitemmanageripSearch) {
            // Search
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ManageripSearch.class);
            startActivityForResult(intent, 20);
        } else if (item.getItemId() == R.id.menuitemmanageripDelete) {
            // Delete
            try {
                if (row_select != null) {
                    ConnectSQL connect = new ConnectSQL();
                    connect.setConnection();
                    connect.setStatement();
                    String delete = "DELETE DanhSachIP WHERE IP='" + select_view_ip.getText().toString() + "'";
                    int result = connect.execUpdateQuery(delete);
                    connect.closeStatement();
                    connect.closeConnection();
                    if (result > 0) {
                        binding.tableManagerIP.removeView(row_select);
                        row_select = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (item.getItemId() == R.id.menuitemmanageripAdd) {
            // Add
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ManageripAdd.class);
            intent.putExtra("userlogin", userlogin);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuitemmanageripEdit) {
            // Edit
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ManageripEdit.class);
            intent.putExtra("userlogin", userlogin);
            intent.putExtra("id", select_view_id.getText().toString());
            intent.putExtra("name", select_view_name.getText().toString());
            intent.putExtra("department", select_view_department.getText().toString());
            intent.putExtra("ip", select_view_ip.getText().toString());
            intent.putExtra("machinetype", select_view_machinetype.getText().toString());
            intent.putExtra("email", select_view_email.getText().toString());
            intent.putExtra("os", select_view_os.getText().toString());
            intent.putExtra("office", select_view_office.getText().toString());
            intent.putExtra("building", select_view_building.getText().toString());
            intent.putExtra("note", select_view_note.getText().toString());
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuitemmanageripRefresh) {
            // Refresh
            setDataTable(text_seclect);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 20) {
            // nhan gia tri tu activity search ip
            text_seclect = data.getStringExtra("select");
            setDataTable(text_seclect);
        }
    }

    // ham xoa du lieu bang table - count -1 dong, khong xoa dong tieu de
    public void clearTable() {
        int countrow = binding.tableManagerIP.getChildCount();
        binding.tableManagerIP.removeViews(1, countrow - 1);
    }

    public void setDataTable(String select) {
        clearTable();
        // Xử lý dữ liệu tìm kiếm, hiển thị lên Table
        try {
            ConnectSQL connect = new ConnectSQL();
            connect.setConnection();
            connect.setStatement();
            ResultSet result = connect.getStatement().executeQuery(select);
            int stt = 1;
            // doi dp to px
            int dp35 = (int) convertDpToPx(getApplicationContext(), 35);
            while (result.next()) {
                // định dạng lại ngày cập nhật thành dạng dd/MM/yyyy
                String ngaycapnhat = result.getString(11);
                try {
                    ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
                            + ngaycapnhat.substring(0, 4);
                } catch (IndexOutOfBoundsException ie) {
                    ngaycapnhat = "";
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
                textview7.setText(result.getString(6));
                textview7.setPadding(5, 0, 5, 0);
                textview7.setHeight(dp35);
                textview7.setBackground(getDrawable(R.drawable.cell_border));
                textview7.setTextSize(20);
                textview7.setGravity(Gravity.CENTER_VERTICAL);
                textview7.setTypeface(null, Typeface.NORMAL);

                TextView textview8 = new TextView(this);
                textview8.setText(result.getString(7));
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

                TextView textview10 = new TextView(this);
                textview10.setText(result.getString(9));
                textview10.setPadding(5, 0, 5, 0);
                textview10.setHeight(dp35);
                textview10.setBackground(getDrawable(R.drawable.cell_border));
                textview10.setTextSize(20);
                textview10.setGravity(Gravity.CENTER_VERTICAL);
                textview10.setTypeface(null, Typeface.NORMAL);

                TextView textview11 = new TextView(this);
                textview11.setText(result.getString(10));
                textview11.setPadding(5, 0, 5, 0);
                textview11.setHeight(dp35);
                // textview11.setBackground(getDrawable(R.drawable.cell_border));
                textview11.setTextSize(20);
                textview11.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                textview11.setGravity(Gravity.START | Gravity.TOP);
                textview11.setTypeface(null, Typeface.NORMAL);

                TextView textview12 = new TextView(this);
                textview12.setText(ngaycapnhat);
                textview12.setPadding(5, 0, 5, 0);
                textview12.setHeight(dp35);
                textview12.setBackground(getDrawable(R.drawable.cell_border));
                textview12.setTextSize(20);
                textview12.setGravity(Gravity.CENTER_VERTICAL);
                textview12.setTypeface(null, Typeface.NORMAL);

                TextView textview13 = new TextView(this);
                textview13.setText(result.getString(12));
                textview13.setPadding(5, 0, 5, 0);
                textview13.setHeight(dp35);
                textview13.setBackground(getDrawable(R.drawable.cell_border));
                textview13.setTextSize(20);
                textview13.setGravity(Gravity.CENTER_VERTICAL);
                textview13.setTypeface(null, Typeface.NORMAL);

                tr.addView(textview);
                tr.addView(textview2);
                tr.addView(textview3);
                tr.addView(textview4);
                tr.addView(textview5);
                tr.addView(textview6);
                tr.addView(textview7);
                tr.addView(textview8);
                tr.addView(textview9);
                tr.addView(textview10);
                tr.addView(textview11);
                tr.addView(textview12);
                tr.addView(textview13);

                tr.setClickable(true);
                textview.setOnClickListener(onClickListenerData(textview, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview2.setOnClickListener(onClickListenerData(textview2, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview3.setOnClickListener(onClickListenerData(textview3, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview4.setOnClickListener(onClickListenerData(textview4, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview5.setOnClickListener(onClickListenerData(textview5, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview6.setOnClickListener(onClickListenerData(textview6, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview7.setOnClickListener(onClickListenerData(textview7, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview8.setOnClickListener(onClickListenerData(textview8, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview9.setOnClickListener(onClickListenerData(textview9, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview10.setOnClickListener(onClickListenerData(textview10, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview11.setOnClickListener(onClickListenerData(textview11, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview12.setOnClickListener(onClickListenerData(textview12, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));
                textview13.setOnClickListener(onClickListenerData(textview13, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11, tr));

                binding.tableManagerIP.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                stt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener onClickListenerData(View view, TextView view_id, TextView view_name, TextView view_departmemnt, TextView view_ip, TextView view_machinetype, TextView view_email, TextView view_os, TextView view_office, TextView view_building, TextView view_note, TableRow row) {
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_view != null) {
                    select_view.setBackground(getDrawable(R.drawable.cell_border));
                }
                v.setBackground(getDrawable(R.drawable.select_row));
                select_view_id = view_id;
                select_view_name = view_name;
                select_view_department = view_departmemnt;
                select_view_ip = view_ip;
                select_view_machinetype = view_machinetype;
                select_view_email = view_email;
                select_view_os = view_os;
                select_view_office = view_office;
                select_view_building = view_building;
                select_view_note = view_note;
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