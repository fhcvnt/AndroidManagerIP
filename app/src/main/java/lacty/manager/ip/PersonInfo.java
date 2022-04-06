package lacty.manager.ip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.ResultSet;

import lacty.manager.ip.databinding.ActivityPersonInfoBinding;

public class PersonInfo extends AppCompatActivity {
    ActivityPersonInfoBinding binding;
    private String text_select = "";// cau lenh select sql
    private View select_view = null;
    private TextView select_view_id = null;
    private TextView select_view_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        binding = ActivityPersonInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tao nut quay lai tren thanh tieu de
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            // nhan gia tri tiem kiem tu cua Person Info Search
            clearTable();
            text_select = data.getStringExtra("select");
            try {
                ConnectSQL connect = new ConnectSQL();
                connect.setConnection();
                connect.setStatement();
                ResultSet result = connect.getStatement().executeQuery(text_select);
                int stt = 0;
                // doi dp to px
                int dp35 = (int) convertDpToPx(getApplicationContext(), 35);
                while (result.next()) {

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

                    // doi gia tri cho cot gender
                    String gendertext = "";
                    try {
                        gendertext = result.getString(4);
                        gendertext = gendertext.equals("0") ? "Nữ" : "Nam";
                    } catch (Exception e) {
                        gendertext = "";
                        e.printStackTrace();
                    }
                    TextView textview5 = new TextView(this);
                    textview5.setText(gendertext);
                    textview5.setPadding(5, 0, 5, 0);
                    textview5.setHeight(dp35);
                    textview5.setBackground(getDrawable(R.drawable.cell_border));
                    textview5.setTextSize(20);
                    textview5.setGravity(Gravity.CENTER_VERTICAL);
                    textview5.setTypeface(null, Typeface.NORMAL);

                    // doi gia tri cho cot sinh nhat Birthday dd/MM/yyyy
                    String birthdaytext = "";
                    try {
                        birthdaytext = result.getString(5);
                        birthdaytext = birthdaytext.substring(8, 10) + "/" + birthdaytext.substring(5, 7) + "/"
                                + birthdaytext.substring(0, 4);
                    } catch (Exception w) {
                        w.printStackTrace();
                    }
                    TextView textview6 = new TextView(this);
                    textview6.setText(birthdaytext);
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

                    // dinh dang lai ngay vao cong ty dd/MM/yyyy
                    String ngayvaocongty = result.getString(8);
                    try {
                        ngayvaocongty = ngayvaocongty.substring(8, 10) + "/" + ngayvaocongty.substring(5, 7)
                                + "/" + ngayvaocongty.substring(0, 4);
                    } catch (Exception ie) {
                        ngayvaocongty = "";
                        ie.printStackTrace();
                    }
                    TextView textview9 = new TextView(this);
                    textview9.setText(ngayvaocongty);
                    textview9.setPadding(5, 0, 5, 0);
                    textview9.setHeight(dp35);
                    textview9.setBackground(getDrawable(R.drawable.cell_border));
                    textview9.setTextSize(20);
                    textview9.setGravity(Gravity.CENTER_VERTICAL);
                    textview9.setTypeface(null, Typeface.NORMAL);

                    // doi gia tri cho status
                    String statustext = "";
                    try {
                        statustext = result.getString(9);
                        statustext = statustext.equals("1") ? "Đang làm" : "Đã nghỉ";
                    } catch (Exception e) {
                        statustext = "";
                    }
                    TextView textview10 = new TextView(this);
                    textview10.setText(statustext);
                    textview10.setPadding(5, 0, 5, 0);
                    textview10.setHeight(dp35);
                    textview10.setBackground(getDrawable(R.drawable.cell_border));
                    textview10.setTextSize(20);
                    textview10.setGravity(Gravity.CENTER_VERTICAL);
                    textview10.setTypeface(null, Typeface.NORMAL);

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

                    tr.setClickable(true);
                    textview.setOnClickListener(onClickListenerData(textview, textview2, textview3));
                    textview2.setOnClickListener(onClickListenerData(textview2, textview2, textview3));
                    textview3.setOnClickListener(onClickListenerData(textview3, textview2, textview3));
                    textview4.setOnClickListener(onClickListenerData(textview4, textview2, textview3));
                    textview5.setOnClickListener(onClickListenerData(textview5, textview2, textview3));
                    textview6.setOnClickListener(onClickListenerData(textview6, textview2, textview3));
                    textview7.setOnClickListener(onClickListenerData(textview7, textview2, textview3));
                    textview8.setOnClickListener(onClickListenerData(textview8, textview2, textview3));
                    textview9.setOnClickListener(onClickListenerData(textview9, textview2, textview3));
                    textview10.setOnClickListener(onClickListenerData(textview10, textview2, textview3));

                    textview.setOnLongClickListener(onLongClickListenerDetail(textview, textview2, textview3));
                    textview2.setOnLongClickListener(onLongClickListenerDetail(textview2, textview2, textview3));
                    textview3.setOnLongClickListener(onLongClickListenerDetail(textview3, textview2, textview3));
                    textview4.setOnLongClickListener(onLongClickListenerDetail(textview4, textview2, textview3));
                    textview5.setOnLongClickListener(onLongClickListenerDetail(textview5, textview2, textview3));
                    textview6.setOnLongClickListener(onLongClickListenerDetail(textview6, textview2, textview3));
                    textview7.setOnLongClickListener(onLongClickListenerDetail(textview7, textview2, textview3));
                    textview8.setOnLongClickListener(onLongClickListenerDetail(textview8, textview2, textview3));
                    textview9.setOnLongClickListener(onLongClickListenerDetail(textview9, textview2, textview3));
                    textview10.setOnLongClickListener(onLongClickListenerDetail(textview10, textview2, textview3));

                    binding.tablePersionInfo.addView(tr, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    stt++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // tao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_personinfo, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // su kien nhan nut quay lai
            finish();
        } else if (item.getItemId() == R.id.menuitemPersoninfoSearch) {
            // su kien nhan nut search tren thanh tieu de
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), PersonInfoSearch.class);
            startActivityForResult(intent, 10);
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClickListenerData(View view, TextView view_id, TextView view_name) {
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_view != null) {
                    select_view.setBackground(getDrawable(R.drawable.cell_border));
                }
                v.setBackground(getDrawable(R.drawable.select_row));
                select_view_id = view_id;
                select_view_name = view_name;
                select_view = v;
            }
        };
        return click;
    }

    // su kien long lick thi mo thong tin chi tiet
    private View.OnLongClickListener onLongClickListenerDetail(View view, TextView view_id, TextView view_name) {
        View.OnLongClickListener click = new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(),PersonInfoDetail.class);
                intent.putExtra("id",view_id.getText().toString());
                intent.putExtra("name",view_name.getText().toString());
                startActivity(intent);
                return false;
            }
        };
        return click;
    }

    // ham xoa du lieu bang table - count -1 dong, khong xoa dong tieu de
    public void clearTable() {
        int countrow = binding.tablePersionInfo.getChildCount();
        binding.tablePersionInfo.removeViews(1, countrow - 1);
    }

    // dp to px
    public static float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}