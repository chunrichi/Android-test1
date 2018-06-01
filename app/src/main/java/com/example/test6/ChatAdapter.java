package com.example.test6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private List<ChatMessage> mData;
    private Context mContext;                  //运行上下文
    private LayoutInflater inflater;           //视图容器
    int Speaker;
    final int TYPE_SEND = 0;
    final int TYPE_ACCEPT = 1;

    public ChatAdapter(Context mContext, List<ChatMessage> mData) {
        this.mData = mData;
        this.mContext = mContext;
        //创建视图容器并设置上下文
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        Speaker = mData.get(position).getSpeaker();
        if (Speaker == 0)
            return TYPE_SEND;
        else
            return TYPE_ACCEPT;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


        @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            ChatMessage chatMessage = mData.get(position);
            if (convertView == null) {
                ViewHolder viewHolder = null;
                // 通过ItemType加载不同的布局
                if (getItemViewType(position) == 0) {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_chat_send, null);
                    viewHolder = new ViewHolder();
                    viewHolder.tv = (TextView) convertView
                            .findViewById(R.id.tv);
                    viewHolder.date = (TextView)convertView
                            .findViewById(R.id.chat_time);
                } else {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.item_chat_accept, null);
                    viewHolder = new ViewHolder();
                    viewHolder.tv = (TextView) convertView
                            .findViewById(R.id.tv);
                    viewHolder.date = (TextView)convertView
                            .findViewById(R.id.chat_time);
                }
                convertView.setTag(viewHolder);
            }
            // 设置数据
            ViewHolder vh = (ViewHolder) convertView.getTag();
            vh.date.setText(chatMessage.getData());
            vh.tv.setText(chatMessage.getaSpeak());
            return convertView;
        }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewAccepter viewAccepter = null;
//        ViewSender viewSender = null;
//        int type = getItemViewType(Speaker);
//        if (convertView == null) {
//            inflater = LayoutInflater.from(mContext);
//            switch (type) {
//                case TYPE_SEND:
//                    convertView = inflater.inflate(R.layout.item_chat_send,
//                            parent, false);
//                    viewSender = new ViewSender();
//                    viewSender.tv = (TextView) convertView.findViewById(R.id.tv);
//                    viewSender.iv_user = (ImageView)convertView.findViewById(R.id.iv_user);
//                    convertView.setTag(viewSender);
//                    break;
//                case TYPE_ACCEPT:
//                    convertView = inflater.inflate(R.layout.item_chat_accept,
//                            parent, false);
//                    viewAccepter = new ViewAccepter();
//                    viewAccepter.tv = (TextView) convertView.findViewById(R.id.tv);
//                    viewAccepter.iv_user = (ImageView)convertView.findViewById(R.id.iv_user);
//                    convertView.setTag(viewAccepter);
//                    break;
//            }
//        } else {
//            Log.e("I", "IM here");
////            switch (type) {
////                case TYPE_SEND:
////                    viewSender = (ViewSender) convertView.getTag();
////                    break;
////                case TYPE_ACCEPT:
////                    viewAccepter = (ViewAccepter) convertView.getTag();
////                    break;
////
////            }
//        }
//        // 设置资源
//        switch (type) {
//            case TYPE_SEND:
//                String message = (String) mData.get(position).getaSpeak();
//                viewSender.tv.setText(message);
//                break;
//            case TYPE_ACCEPT:
//                String message2 = (String) mData.get(position).getaSpeak();
//                viewAccepter.tv.setText(message2);
//                break;
//        }
//        return convertView;
//    }

    public class ViewSender{
        ImageView iv_user;
        TextView tv;
    }

    public class ViewAccepter{
        ImageView iv_user;
        TextView tv;
    }

    private class ViewHolder {
        private TextView date;
        private ImageView iv_user;
        private TextView tv;
    }
}