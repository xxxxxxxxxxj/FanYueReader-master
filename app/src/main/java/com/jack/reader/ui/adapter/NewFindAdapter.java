package com.jack.reader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jack.reader.R;
import com.jack.reader.bean.support.FindBean;
import com.jack.reader.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 山鸡 on 2017/9/9.
 */

public class NewFindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private onItemClickListenr mOnItemClickListenr;
    private static final int TYPE_BANNER = 0;
    private static final int TYPE_BOOKRACK_DES = 1;
    private Context context;
    private LayoutInflater inflater;
    private List<FindBean> list;
    private List<String> mImages = new ArrayList<>();


    public NewFindAdapter(Context context, List<FindBean> list,onItemClickListenr mOnItemClickListenr) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.mOnItemClickListenr = mOnItemClickListenr;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_BANNER:
                return new BannerViewHolder(inflater.inflate(R.layout.item_home_banner, parent, false));
            case TYPE_BOOKRACK_DES:
                return new BookDetailViewHolder(inflater.inflate(R.layout.item_find, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder) {
            setBannerItemValues((BannerViewHolder) holder, position);
        } else if (holder instanceof BookDetailViewHolder) {
            setbookItemValues((BookDetailViewHolder)holder,position);
        }
    }


    private void setbookItemValues(final BookDetailViewHolder holder, final int position){
        holder.tvTitle.setText(list.get(position-1).getTitle());

        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListenr.onItemClick(position-1,holder.rl_item);
            }
        });
    }
    //设置banner
    private void setBannerItemValues(final BannerViewHolder holder, int position) {
//        if (mImages.size() == 0 && list.size() >= 4) {
////            for (int i = 0; i < 4; i++) {
////                MovieInfo info = list.get(i);
////                mImages.add(info.getMovie_picture_url());
////            }
//        }
        mImages.clear();
        mImages.add("http://121.42.174.147:8080/movieImages/71.jpg");
        mImages.add("http://121.42.174.147:8080/movieImages/99.jpg");
        mImages.add("http://121.42.174.147:8080/movieImages/102.jpg");

        holder.mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        holder.mBanner.setImageLoader(new GlideImageLoader());
        holder.mBanner.setImages(mImages);
        holder.mBanner.setBannerAnimation(Transformer.DepthPage);
        holder.mBanner.isAutoPlay(true);
        holder.mBanner.setDelayTime(1500);
        holder.mBanner.setIndicatorGravity(BannerConfig.CENTER);
        holder.mBanner.start();
        holder.mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//            Intent intent = new Intent(context, MovieDetailActivity.class);
//            intent.putExtra("movie", list.get(position));
//            context.startActivity(intent);
            }
        });
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private Banner mBanner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            mBanner = (Banner) itemView.findViewById(R.id.banner_home);
        }
    }


    public class BookDetailViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private RelativeLayout rl_item;
        private TextView tvTitle;

        public BookDetailViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else{
            return TYPE_BOOKRACK_DES;
        }
    }

    public interface onItemClickListenr{
        void onItemClick(int position,View view);
    }
}
