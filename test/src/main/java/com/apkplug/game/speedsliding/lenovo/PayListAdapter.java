package com.apkplug.game.speedsliding.lenovo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qinfeng on 16/3/10.
 */
public class PayListAdapter extends BaseAdapter {

    private final Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDatas;

    public PayListAdapter(Context context, List<String> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = data;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_pay_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtViewName = (TextView) convertView.findViewById(R.id.payName);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.payIcon);
            viewHolder.txtViewDesc = (TextView) convertView.findViewById(R.id.payDesc);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String payId = mDatas.get(position);
        String name = "支付宝";
        if (payId.equals("alipay"))
        {

        }else if (payId.equals("bdwallet")){
            name = "百度钱包";

        }else if (payId.equals("wechat")){
            name = "微信支付";

        }else if (payId.equals("unionpay")){
            name = "银联支付";

        }



        viewHolder.txtViewName.setText(name);
        viewHolder.txtViewDesc.setText(payId);

        int resID = mContext.getResources().getIdentifier(payId, "drawable", mContext.getPackageName());
        viewHolder.imageView.setImageResource(resID);
        return convertView;
    }

    static class ViewHolder {
        TextView txtViewName;
        TextView txtViewDesc;
        ImageView imageView;
    }
}