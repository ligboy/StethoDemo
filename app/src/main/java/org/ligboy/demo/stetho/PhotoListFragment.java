package org.ligboy.demo.stetho;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import org.ligboy.demo.stetho.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends Fragment {

    private static final String SAVE_LIST = "list";
    private ListView mListView;
    private PhotoListAdapter mAdapter;
    private ArrayList<Photo> mPhotoList = new ArrayList<>();

    public PhotoListFragment() {
        // Required empty public constructor
        mPhotoList.add(new Photo("台湾美女", "http://photo.880sy.com/4/2893/110761.jpg"));
        mPhotoList.add(new Photo("青春期美女赵奕欢", "http://admin.anzow.com/picture/2012815/2012081534555862.jpg"));
        mPhotoList.add(new Photo("美女学生会副主席", "http://news.xinhuanet.com/fortune/2014-09/25/127032307_14116116674681n.jpg"));
        mPhotoList.add(new Photo("美女学生会副主席", "http://news.xinhuanet.com/fortune/2014-09/25/127032307_14116116674671n.jpg"));
        mPhotoList.add(new Photo("美女学生会副主席", "http://news.xinhuanet.com/fortune/2014-09/25/127032307_14116116674701n.jpg"));
        mPhotoList.add(new Photo("美女学生会副主席", "http://news.xinhuanet.com/fortune/2014-09/25/127032307_14116116674771n.jpg"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
        mListView = (ListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new PhotoListAdapter(getContext(), mPhotoList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_LIST, mPhotoList);
    }

    private static final class PhotoListAdapter extends ArrayAdapter<Photo> {

        public PhotoListAdapter(Context context, List<Photo> objects) {
            super(context, 0, objects);
        }

        public PhotoListAdapter(Context context, Photo[] objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_photo, parent, false);
                viewHolder = new ViewHolder(view);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.bind(position);
            return view;
        }

        private final class ViewHolder {
            private AppCompatImageView mPhotoImageView;
            private AppCompatTextView mTitleTextView;
            public ViewHolder(View view) {
                mPhotoImageView = (AppCompatImageView) view.findViewById(R.id.photo);
                mTitleTextView = (AppCompatTextView) view.findViewById(R.id.title);
                view.setTag(this);
            }

            public void bind(int position) {
                Photo item = getItem(position);
                if (item != null) {
                    if (!TextUtils.isEmpty(item.getTitle())) {
                        mTitleTextView.setText(item.getTitle());
                    } else {
                        mTitleTextView.setText(null);
                    }
                    if (!TextUtils.isEmpty(item.getUrl())) {
                        Glide.with(getContext()).load(item.getUrl()).crossFade()
                                .placeholder(R.drawable.ic_photo_grey_600_24dp).into(mPhotoImageView);
                    } else {
                        mPhotoImageView.setImageResource(R.drawable.ic_photo_grey_600_24dp);
                    }

                } else {
                    mPhotoImageView.setImageResource(R.drawable.ic_photo_grey_600_24dp);
                    mTitleTextView.setText(null);
                }
            }
        }
    }
}
