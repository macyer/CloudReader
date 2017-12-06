package com.lvshou.cloudreaadercopy.weiget.ninegrid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lvshou.cloudreaadercopy.R;
import com.lvshou.cloudreaadercopy.utils.StatusBarUtil;
import com.lvshou.cloudreaadercopy.weiget.ninegrid.model.ImageModel;
import com.lvshou.cloudreaadercopy.weiget.ninegrid.model.ModelUtil;

import java.util.ArrayList;
import java.util.List;

public class NineImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerview);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(ModelUtil.getImages());
        recyclerView.setAdapter(adapter);
    }

    protected void setStatusBarTranslucent(boolean isLightStatusBar) {
        StatusBarUtil.setStatusBarTranslucent(this, isLightStatusBar);
    }
    
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private List<ImageModel> list;

        RecyclerViewAdapter(List<ImageModel> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_nineimageview, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            ImageModel entity = list.get(position);

            viewHolder.tvTitle.setText(entity.getTitle());

            ArrayList<ImageAttr> imageAttrs = new ArrayList<>();
            for (String url : entity.getImages()) {
                ImageAttr attr = new ImageAttr();
                attr.url = url;
                imageAttrs.add(attr);
            }
            if (viewHolder.nineImageView.getAdapter() != null) {
                viewHolder.nineImageView.setAdapter(viewHolder.nineImageView.getAdapter());
            } else {
                viewHolder.nineImageView.setAdapter(new NineImageViewEventAdapter(viewHolder.nineImageView.getContext(), imageAttrs));
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            NineImageView nineImageView;

            ViewHolder(View view) {
                super(view);
                tvTitle = (TextView) view.findViewById(R.id.tv_title);
                nineImageView = (NineImageView) view.findViewById(R.id.nineImageView);
            }
        }
    }
}
