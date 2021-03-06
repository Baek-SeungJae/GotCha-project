package multi.android.gotcha.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import multi.android.gotcha.DB.CarVO;
import multi.android.gotcha.R;

public class CarAdapter extends ArrayAdapter<CarVO> {
    private Context context;
    private int resId;//view에 대한 리소스
    private ArrayList<CarVO> data; //핸들링할 데이터
    private HashMap<Integer,MyMemento> userStateValue = new HashMap<>();
    private int value;

    public CarAdapter(@NonNull Context context, int ResourceId, @NonNull ArrayList<CarVO> objects) {
        super(context, ResourceId, objects);
        this.context = context;
        this.resId = ResourceId;
        this.data = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            value++;
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId,null);
            ViewHolder itemView = new ViewHolder(convertView);
            convertView.setTag(itemView);
            convertView.setTag(R.string.app_name,value);
           /* Log.d("firstjob","최초작업==>"+data.get(position).modelName
                    +"=========>convertView::::::::"+convertView+"::::"+value);*/
        }

        ViewHolder itemView = (ViewHolder)convertView.getTag();
        CarVO carVO = data.get(position);


        if(carVO!=null) {
            ImageView imageView = itemView.myImg;
            TextView brandView = itemView.brandView;
            TextView modelView = itemView.modelView;
            TextView fuelView = itemView.fuelView;
            TextView colorView = itemView.colorView;
            TextView yearView = itemView.yearView;
            TextView priceView = itemView.priceView;

            brandView.setText(carVO.getBrand());
            modelView.setText(carVO.getModel());
            fuelView.setText(carVO.getFuel());
            colorView.setText(carVO.getColor());
            yearView.setText(carVO.getYear());
            priceView.setText(carVO.getPrice());
            String link = "http://" + context.getString(R.string.ip) + ":8088/DBServer/images/"+carVO.getImage();
            Glide.with(getContext()).load(link).into(imageView);


            MyMemento state = userStateValue.get(position);
            if(state ==null){
                //한 번도 저장한 적이 없는 경우
                Log.d("check","널"+position);
                //chkVal.setChecked(false);
            }else{
                Log.d("check","널아님"+position);
                //chkVal.setChecked(state.check);
            }
            Log.d("getView",carVO.toString()+"*****************"+
                    convertView.getTag(R.string.app_name)+"******************"+
                    convertView);
            /*chkVal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("onCheckedChanged",isChecked+":"+position);
                    boolean data = chkVal.isChecked();
                    MyMemento state1 = new MyMemento();
                    state.check = data;
                    userStateValue.put(position,state);
                }
            });*/
        }
        return  convertView;
    }

    class MyMemento{
        boolean check;
    }

    class ViewHolder{
        ImageView myImg;
        TextView brandView;
        TextView modelView;
        TextView fuelView;
        TextView yearView;
        TextView colorView;
        TextView priceView;
        //CheckBox chkVal;
        ViewHolder(View parentView){
            Log.d("viewholder","ViewHolder호출");
            this.myImg = parentView.findViewById(R.id.myImg);
            this.brandView = parentView.findViewById(R.id.brand);
            this.modelView = parentView.findViewById(R.id.model);
            this.fuelView = parentView.findViewById(R.id.fuel);
            this.yearView = parentView.findViewById(R.id.year);
            this.colorView = parentView.findViewById(R.id.color);
            this.priceView = parentView.findViewById(R.id.price);
        }
    }
}
