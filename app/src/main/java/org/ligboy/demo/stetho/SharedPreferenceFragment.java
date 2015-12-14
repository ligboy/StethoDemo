package org.ligboy.demo.stetho;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class SharedPreferenceFragment extends Fragment
        implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener, AdapterView.OnItemClickListener {

    private AppCompatEditText mKeyEdit;
    private AppCompatEditText mValueEdit;
    private AppCompatButton mSaveButton;
    private ListViewCompat mListView;

    private SharedPreferenceAdapter mAdapter;

    private ArrayList<Map.Entry<String, ?>> mSharedPreferenceList = new ArrayList<>();

    public SharedPreferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared_preference, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mKeyEdit = (AppCompatEditText) view.findViewById(R.id.key);
        mValueEdit = (AppCompatEditText) view.findViewById(R.id.value);
        mSaveButton = (AppCompatButton) view.findViewById(R.id.save);
        mListView = (ListViewCompat) view.findViewById(R.id.list);

        mAdapter = new SharedPreferenceAdapter(getContext(), mSharedPreferenceList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mSaveButton.setOnClickListener(this);

        load();

        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroyView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map.Entry<String, ?> item = mAdapter.getItem(position);
        if (item != null) {
            mKeyEdit.setText(item.getKey());
            if (item.getValue() != null) {
                mValueEdit.setText(item.getValue().toString());
            } else {
                mValueEdit.setText(null);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            String key = mKeyEdit.getText().toString();
            String value = mValueEdit.getText().toString();
            if (key.length() > 0 && value.length() > 0) {
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getContext());
                sharedPreferences.edit().putString(key, value).apply();
                mKeyEdit.setText(null);
                mValueEdit.setText(null);
            } else {
                Toast.makeText(getContext(), "Key & Value can't be null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void load() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        Map<String, ?> allSharedPreferences = sharedPreferences.getAll();
        Set<? extends Map.Entry<String, ?>> entries = allSharedPreferences.entrySet();
        mSharedPreferenceList.clear();
        mSharedPreferenceList.addAll(entries);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        load();
    }

    private static class SharedPreferenceAdapter extends ArrayAdapter<Map.Entry<String, ?>> {

        public SharedPreferenceAdapter(Context context, List<Map.Entry<String, ?>> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_shared_preference, parent, false);
                viewHolder = new ViewHolder(view);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Map.Entry<String, ?> item = getItem(position);
            viewHolder.keyText.setText(item.getKey());
            if (item.getValue() != null) {
                viewHolder.valueText.setText(item.getValue().toString());
            } else {
                viewHolder.valueText.setText(null);
            }
            return view;
        }

        private class ViewHolder {
            public AppCompatTextView keyText;
            public AppCompatTextView valueText;

            public ViewHolder(View view) {
                keyText = (AppCompatTextView) view.findViewById(R.id.key);
                valueText = (AppCompatTextView) view.findViewById(R.id.value);
                view.setTag(this);
            }
        }
    }
}
