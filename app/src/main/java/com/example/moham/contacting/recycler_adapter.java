package com.example.moham.contacting;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by moham on 6/17/2016.
 */
public class recycler_adapter extends RecyclerView.Adapter<recycler_adapter.recycler_hoder> {
    ArrayList<ContacterModel> contacterModels;
    MainActivity context;
    DBController dbController;
    DeleteAlarm_class deleteAlarmClass;

    public recycler_adapter(MainActivity context, ArrayList<ContacterModel> models) {
        this.context = context;
        this.contacterModels = models;
        dbController = new DBController(context);
        deleteAlarmClass = new DeleteAlarm_class(context);

    }

    @Override
    public recycler_hoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new recycler_hoder(v, context, contacterModels);
    }

    @Override
    public void onBindViewHolder(recycler_hoder holder, int position) {
        ContacterModel model = contacterModels.get(position);
        holder.name_txtview.setText(model.getName());
        holder.phone_txtview.setText(model.getPhone());
        holder.Remind_txtview.setText(model.getRemindMe());
        holder.id_textview.setText(model.getId() + "");
        if (!model.isWill_notify()) {
            holder.willNotify_textview.setVisibility(View.GONE);
        } else {
            holder.willNotify_textview.setVisibility(View.VISIBLE);
        }
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(model.getImage(), 0, model.getImage().length);
            holder.imageView.setImageBitmap(bitmap);
        } catch (Exception x) {

        }
        if (!context.is_in_action_mod) {
            holder.checkBox.setVisibility(View.GONE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setChecked(model.is_selected());
        holder.checkBox.setTag(model);
    }

    @Override
    public int getItemCount() {
        return contacterModels.size();
    }

    public static class recycler_hoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name_txtview, phone_txtview, Remind_txtview, id_textview, willNotify_textview;
        ImageView imageView;
        CheckBox checkBox;
        CardView cardView;
        MainActivity context;
        ArrayList<ContacterModel> contacterModels;

        public recycler_hoder(View itemView, MainActivity context, ArrayList<ContacterModel> models) {
            super(itemView);
            this.context = context;
            this.contacterModels = models;
            imageView = (ImageView) itemView.findViewById(R.id.imgview_id);
            name_txtview = (TextView) itemView.findViewById(R.id.name_textview_id);
            phone_txtview = (TextView) itemView.findViewById(R.id.phone_textview_id);
            Remind_txtview = (TextView) itemView.findViewById(R.id.bithday_textview_id);
            id_textview = (TextView) itemView.findViewById(R.id.id_textview_id);
            willNotify_textview = (TextView) itemView.findViewById(R.id.willnotify_textview_id);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            checkBox.setOnClickListener(this);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() != R.id.checkbox_id) {
                new AlertDialog.Builder(context)
                        .setTitle("choose")
                        .setItems(context.getResources().getStringArray(R.array.dialogItems), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int adapterPosition = getAdapterPosition();
                                DeleteAlarm_class alarmManager = new DeleteAlarm_class(context);
                                switch (which) {
                                    case 0:
                                        String facebook_usernae = contacterModels.get(adapterPosition).getFacebook_usernae();
                                        String url = "http://www.facebook.com/" + facebook_usernae;
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        context.startActivity(i);
                                        break;
                                    case 1:
                                        DBController dbController = new DBController(context);
                                        dbController.delete(contacterModels.get(adapterPosition).getId());
                                        alarmManager.delete_alarm(contacterModels.get(adapterPosition).getId());
                                        contacterModels.remove(getAdapterPosition());
                                        context.adapter.notifyDataSetChanged();
                                        break;
                                    case 2:
                                        int id = contacterModels.get(adapterPosition).getId();
                                        String remindMe = contacterModels.get(adapterPosition).getRemindMe();
                                        DateModel dateModel = new DateModel(remindMe);
                                        Intent intent = new Intent(context, AlarmManager_Activity.class);
                                        intent.putExtra("date", dateModel);
                                        intent.putExtra("request_code", id);
                                        context.startActivity(intent);
                                        break;
                                }
                            }
                        })
                        .show();
            } else {
                CheckBox checkBox = (CheckBox) v;
                ContacterModel tag = (ContacterModel) checkBox.getTag();
                tag.setIs_selected(checkBox.isChecked());
                context.prepare_selection(v, getAdapterPosition());
            }
        }
    }

    public void update_adapter(ArrayList<ContacterModel> list) {

        for (ContacterModel m : list) {
            delete_element(m);
        }
        context.onResume();
    }

    public void delete_element(ContacterModel model) {
        contacterModels.remove(model);
        dbController.delete(model.getId());
        deleteAlarmClass.delete_alarm(model.getId());
        notifyDataSetChanged();
    }

    public void add_element(ArrayList<ContacterModel> my_people_arraylist) {

        contacterModels = dbController.get_all_people();
        notifyDataSetChanged();

    }


}
