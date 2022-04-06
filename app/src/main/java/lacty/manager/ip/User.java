package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

import lacty.manager.ip.databinding.ActivityUserBinding;

public class User extends AppCompatActivity {
    ActivityUserBinding binding;
    private View select_view = null;
    private TextView select_view_id = null;
    private TextView select_view_username = null;
    private TextView select_view_group = null;
    private TextView select_view_personname = null;
    private String textsearch = ""; // chuoi tim kiem
    private TableRow row_select = null; // dong duoc chon
    private String grouplogin = ""; // group login=Admin thi moi cho Add, Edit, delete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set background for title
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        Intent intent = getIntent();
        grouplogin = intent.getStringExtra("grouplogin");

        sqlSearch("");
    }

    // tao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        if (!grouplogin.equals("Admin")) {
            menu.removeItem(R.id.menuitemuserAdd);
            menu.removeItem(R.id.menuitemuserEdit);
            menu.removeItem(R.id.menuitemuserDelete);
            menu.removeItem(R.id.menuitemuserRefresh);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuitemuserAdd) {

            // Add
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), UserAdd.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.menuitemuserDelete) {

            // Delete
            if (select_view != null) {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();
                if (!select_view_id.getText().toString().trim().isEmpty()) {
                    String delete = "DELETE NguoiDung WHERE MaNguoiDung='" + select_view_id.getText().toString() + "'";
                    int result = connect.execUpdateQuery(delete);
                    if (result > 0) {
                        try {
                            // cap nhat lai bang du lieu sau khi xoa
                            binding.tableUser.removeView(row_select);
                            Toast.makeText(this, "Delele success!", Toast.LENGTH_SHORT).show();
                            select_view = null;
                        } catch (Exception ne) {
                            ne.printStackTrace();
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please, seclect row!", Toast.LENGTH_SHORT).show();
            }

        } else if (item.getItemId() == R.id.menuitemuserEdit) {

            // Edit
            if (select_view != null) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), UserEdit.class);
                intent.putExtra("id", select_view_id.getText().toString());
                intent.putExtra("username", select_view_username.getText().toString());
                intent.putExtra("group", select_view_group.getText().toString());
                intent.putExtra("personname", select_view_personname.getText().toString());
                startActivityForResult(intent, 110);
            }

        } else if (item.getItemId() == android.R.id.home) {

            // sự kiện nhấn nút quay lại trên thanh tiêu đề
            finish();
            return true;

        } else if (item.getItemId() == R.id.menuitemuserRefresh) {

            // Refresh
            clearTable();
            sqlSearch(textsearch);
        }

        return super.onOptionsItemSelected(item);
    }

    // nhan gia tri tra ve tu Edit User


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 110) {
            clearTable();
            sqlSearch(textsearch);
        }
    }

    // dp to px
    public static float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    // ham ket noi co so du lieu va lay du lieu cho table
    public void sqlSearch(String chuoi) {
        ConnectSQL connection = new ConnectSQL();
        connection.setConnection();
        if (connection.getConnection() != null) {
            connection.setStatement();
            try {
                // so thu tu
                String select = "SELECT MaNguoiDung,TenDangNhap,TenNhom,TenNguoiDung FROM NguoiDung ORDER BY MaNguoiDung ASC";
                ResultSet resultSet = connection.getStatement().executeQuery(select);

                // doi dp to px
                int dp35 = (int) convertDpToPx(getApplicationContext(), 35);
                while (resultSet.next()) {
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView textview = new TextView(this);
                    textview.setText(resultSet.getString(1));
                    textview.setPadding(5, 0, 5, 0);
                    textview.setHeight(dp35);
                    textview.setBackground(getDrawable(R.drawable.cell_border));
                    textview.setTextSize(20);
                    textview.setGravity(Gravity.CENTER_VERTICAL);
                    textview.setTypeface(null, Typeface.NORMAL);

                    TextView textview2 = new TextView(this);
                    textview2.setText(resultSet.getString(2));
                    textview2.setPadding(5, 0, 5, 0);
                    textview2.setHeight(dp35);
                    textview2.setBackground(getDrawable(R.drawable.cell_border));
                    textview2.setTextSize(20);
                    textview2.setGravity(Gravity.CENTER_VERTICAL);
                    textview2.setTypeface(null, Typeface.NORMAL);

                    TextView textview3 = new TextView(this);
                    textview3.setText(resultSet.getString(3));
                    textview3.setPadding(5, 0, 5, 0);
                    textview3.setHeight(dp35);
                    textview3.setBackground(getDrawable(R.drawable.cell_border));
                    textview3.setTextSize(20);
                    textview3.setGravity(Gravity.CENTER_VERTICAL);
                    textview3.setTypeface(null, Typeface.NORMAL);

                    TextView textview4 = new TextView(this);
                    textview4.setText(resultSet.getString(4));
                    textview4.setPadding(5, 0, 5, 0);
                    textview4.setHeight(dp35);
                    textview4.setBackground(getDrawable(R.drawable.cell_border));
                    textview4.setTextSize(20);
                    textview4.setGravity(Gravity.CENTER_VERTICAL);
                    textview4.setTypeface(null, Typeface.NORMAL);

                    tr.addView(textview);
                    tr.addView(textview2);
                    tr.addView(textview3);
                    tr.addView(textview4);

                    tr.setClickable(true);
                    textview.setOnClickListener(onClickListenerData(textview, textview, textview2, textview3, textview4, tr));
                    textview2.setOnClickListener(onClickListenerData(textview2, textview, textview2, textview3, textview4, tr));
                    textview3.setOnClickListener(onClickListenerData(textview3, textview, textview2, textview3, textview4, tr));
                    textview4.setOnClickListener(onClickListenerData(textview4, textview, textview2, textview3, textview4, tr));

                    binding.tableUser.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                }
                resultSet.close();
                connection.closeStatement();
                connection.closeStatement();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Connection is null", Toast.LENGTH_SHORT).show();
        }
    }

    // event click to row table
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            v.setBackground(getDrawable(R.drawable.select_row));
        }
    };

    private View.OnClickListener onClickListenerData(View view, TextView view_id, TextView view_username, TextView view_group, TextView view_personname, TableRow row) {
        View.OnClickListener click = new View.OnClickListener() {
            public void onClick(View v) {
                if (select_view != null) {
                    select_view.setBackground(getDrawable(R.drawable.cell_border));
                }
                v.setBackground(getDrawable(R.drawable.select_row));
                select_view_id = view_id;
                select_view_username = view_username;
                select_view_group = view_group;
                select_view_personname = view_personname;
                select_view = v;
                row_select = row;
            }
        };
        return click;
    }

    // ham xoa du lieu bang table - count -1 dong, khong xoa dong tieu de
    public void clearTable() {
        int countrow = binding.tableUser.getChildCount();
        binding.tableUser.removeViews(1, countrow - 1);
    }
}