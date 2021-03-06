package lacty.manager.ip;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;

import lacty.manager.ip.databinding.ActivityMachineTypeBinding;

public class MachineType extends AppCompatActivity {
    ActivityMachineTypeBinding binding;

    private View select_view = null;
    private TextView select_view_id = null;
    private TextView select_view_machinetype = null;
    private String textsearch = ""; // chuoi tim kiem
    private TableRow row_select = null; // dong duoc chon
    private  String grouplogin=""; // group login=Admin thi moi cho Add, Edit, delete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_type);

        binding = ActivityMachineTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        grouplogin = intent.getStringExtra("grouplogin");

        sqlSearch("");
    }

    // tao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_machinetype, menu);
        if (!grouplogin.equals("Admin")) {
            menu.removeItem(R.id.menuitemmachinetypeAdd);
            menu.removeItem(R.id.menuitemmachinetypeEdit);
            menu.removeItem(R.id.menuitemmachinetypeDelete);
            menu.removeItem(R.id.menuitemmachinetypeRefresh);
        }

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuitemmachinetypeSearch).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        // bat su kien thay doi du lieu tim kiem
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // delete data old
                clearTable();
                // result search
                sqlSearch(query);
                textsearch = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty() || newText == null) {
                    // delete data old
                    clearTable();
                    // result search
                    sqlSearch("");
                    textsearch = "";
                } else {
                    // delete data old
                    clearTable();
                    // result search
                    sqlSearch(newText);
                    textsearch = newText;
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuitemmachinetypeAdd) {

            // Add
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MachineTypeAdd.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuitemmachinetypeDelete) {

            // Delete
            ConnectSQL connect = new ConnectSQL();
            connect.setConnection();
            if (!select_view_id.getText().toString().trim().isEmpty()) {
                String delete = "DELETE LoaiMay WHERE MaLoaiMay='" + select_view_id.getText().toString() + "'";
                int result = connect.execUpdateQuery(delete);
                if (result > 0) {
                    try {
                        // cap nhat lai bang du lieu sau khi xoa
                        binding.tableMachinetype.removeView(row_select);
                        Toast.makeText(this, "Delele success!", Toast.LENGTH_SHORT).show();
                        select_view = null;
                    } catch (Exception ne) {
                        ne.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(this, "Please, seclect row!", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.menuitemmachinetypeEdit) {

            // Edit
            if (select_view != null) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MachinetypeEdit.class);
                intent.putExtra("id", select_view_id.getText().toString());
                intent.putExtra("machinetype", select_view_machinetype.getText().toString());
                startActivityForResult(intent, 2);
            }
        } else if (item.getItemId() == R.id.menuitemmachinetypeRefresh) {

            // Refresh
            clearTable();
            sqlSearch(textsearch);
        } else if (item.getItemId() == android.R.id.home) {
            // s??? ki???n nh???n n??t quay l???i tr??n thanh ti??u ?????
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // nhan gia tri tra ve tu cua so Edit machine type


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            clearTable();
            sqlSearch(textsearch);
        }
        ;
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
                int stt = 1;
                String select = "SELECT MaLoaiMay,LoaiMay FROM LoaiMay WHERE LoaiMay LIKE N'%" + chuoi + "%' OR dbo.convertUnicodetoASCII(LoaiMay) LIKE N'%" + chuoi + "%' ORDER BY LoaiMay ASC";
                ResultSet resultSet = connection.getStatement().executeQuery(select);

                // doi dp to px
                int dp35 = (int) convertDpToPx(getApplicationContext(), 35);
                while (resultSet.next()) {
                    TableRow tr = new TableRow(this);
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView textview = new TextView(this);
                    textview.setText("" + stt);
                    textview.setPadding(5, 0, 5, 0);
                    textview.setHeight(dp35);
                    textview.setBackground(getDrawable(R.drawable.cell_border));
                    textview.setTextSize(20);
                    textview.setGravity(Gravity.CENTER_VERTICAL);
                    textview.setTypeface(null, Typeface.NORMAL);

                    TextView textview2 = new TextView(this);
                    textview2.setText(resultSet.getString(1));
                    textview2.setPadding(5, 0, 5, 0);
                    textview2.setHeight(dp35);
                    textview2.setBackground(getDrawable(R.drawable.cell_border));
                    textview2.setTextSize(20);
                    textview2.setGravity(Gravity.CENTER_VERTICAL);
                    textview2.setTypeface(null, Typeface.NORMAL);

                    TextView textview3 = new TextView(this);
                    textview3.setText(resultSet.getString(2));
                    textview3.setPadding(5, 0, 5, 0);
                    textview3.setHeight(dp35);
                    textview3.setBackground(getDrawable(R.drawable.cell_border));
                    textview3.setTextSize(20);
                    textview3.setGravity(Gravity.CENTER_VERTICAL);
                    textview3.setTypeface(null, Typeface.NORMAL);

                    tr.addView(textview);
                    tr.addView(textview2);
                    tr.addView(textview3);

                    tr.setClickable(true);
                    textview.setOnClickListener(onClickListenerData(textview, textview2, textview3, tr));
                    textview2.setOnClickListener(onClickListenerData(textview2, textview2, textview3, tr));
                    textview3.setOnClickListener(onClickListenerData(textview3, textview2, textview3, tr));

                    binding.tableMachinetype.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    stt++;
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

    private View.OnClickListener onClickListenerData(View view, TextView view_id, TextView view_location, TableRow row) {
        View.OnClickListener click = new View.OnClickListener() {
            public void onClick(View v) {
                if (select_view != null) {
                    select_view.setBackground(getDrawable(R.drawable.cell_border));
                }
                v.setBackground(getDrawable(R.drawable.select_row));
                select_view_id = view_id;
                select_view_machinetype = view_location;
                select_view = v;
                row_select = row;
            }
        };
        return click;
    }

    // ham xoa du lieu bang table - count -1 dong, khong xoa dong tieu de
    public void clearTable() {
        int countrow = binding.tableMachinetype.getChildCount();
        binding.tableMachinetype.removeViews(1, countrow - 1);
    }
}