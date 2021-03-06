package multi.android.gotcha.sale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multi.android.gotcha.DB.Task;
import multi.android.gotcha.DB.mysaleVO;
import multi.android.gotcha.Home;
import multi.android.gotcha.MainActivity;
import multi.android.gotcha.R;

public class mysaleAdapter extends ArrayAdapter<mysaleVO> {
    private Context context;
    private List<mysaleVO> list;
    private ListView listView;

    class ViewHolder{
        public TextView mySaleName;
        public Button btnDelete;
    }
    public mysaleAdapter(Context context, List<mysaleVO> list, ListView listView){
        super(context,0,list);
        this.context = context;
        this.list = list;
        this.listView = listView;
    }
    public View getView(int position, View convertView, ViewGroup parentViewGroup) {
        View rowView = convertView;
        ViewHolder viewHolder;
        String Status;

        if (rowView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.mysale_row, parentViewGroup, false);
            viewHolder = new ViewHolder();

            viewHolder.mySaleName = rowView.findViewById(R.id.mySaleName);
            viewHolder.btnDelete = rowView.findViewById(R.id.mySaleDelete);

            rowView.setTag(viewHolder);
            Status = "created";
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
            Status = "reused";
        }
        final mysaleVO mysaleItems = (mysaleVO)list.get(position);
        viewHolder.btnDelete.setTag(position);
        viewHolder.mySaleName.setText(mysaleItems.getBrand() + "/" + mysaleItems.getModel() + "  " + mysaleItems.getCarNumber());

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(),R.style.Theme_AppCompat_Light_Dialog);
                alertDialog.setTitle(mysaleItems.getCarNumber()).setMessage(" -> 삭제하기").setCancelable(
                        true).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("method", "myListDelete");
                                map.put("carNumber",mysaleItems.getCarNumber());
                                Task myListDelete = new Task();
                                myListDelete.execute(map);
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                getContext().startActivity(intent);
                            }
                        }).setNeutralButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int id) {
                                Toast.makeText(getContext(),"취소",Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });
        return rowView;
    }
}
