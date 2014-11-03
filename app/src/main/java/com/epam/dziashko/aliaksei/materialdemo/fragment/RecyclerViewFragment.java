package com.epam.dziashko.aliaksei.materialdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.dziashko.aliaksei.materialdemo.DetailActivity;
import com.epam.dziashko.aliaksei.materialdemo.R;
import com.epam.dziashko.aliaksei.materialdemo.data.Data;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.recycler, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AwesomeAdapter(getActivity(), Data.getKittiesList(50));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.add(0, 112, 1, "Linear");
        menu.add(0, 113, 1, "Grid");
        menu.add(0, 114, 1, "Staggered");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 112:
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView.setLayoutManager(mLayoutManager);
                return true;
            case 113:
                mLayoutManager = new GridLayoutManager(getActivity(), 3);
                mRecyclerView.setLayoutManager(mLayoutManager);
                return true;
            case 114:
                mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(mLayoutManager);
                return true;
            default:
                break;
        }

        return false;
    }

    public static class AwesomeAdapter extends RecyclerView.Adapter<AwesomeAdapter.ViewHolder> {

        private List<String> mDataset;
        private Context mContext;


        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ImageView mImageView;
            public TextView mTextViewTitle, mTextViewBody;
            public ViewHolder(View view) {
                super(view);
                mImageView = (ImageView) view.findViewById(R.id.image);
                mTextViewBody = (TextView) view.findViewById(R.id.text1);
                mTextViewTitle = (TextView) view.findViewById(R.id.text2);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public AwesomeAdapter(Context context, List<String> myDataset) {
            mDataset = myDataset;
            mContext = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public AwesomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            // set the view's size, margins, paddings and layout parameters

            /*Resources r = getContext().getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, r.getDisplayMetrics());
            v.setPadding(0,0,0,px);*/

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }


        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final String model = getItem(position);

            Log.d("CAT", model);
            final ImageView imageView = holder.mImageView;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_DATA, model);
                    String transitionName = mContext.getString(R.string.tranzition_robo);

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((android.app.Activity) mContext, v, transitionName);

                    ActivityCompat.startActivity((android.app.Activity) mContext, intent, options.toBundle());
                }
            });

            Picasso.with(getContext())
                    .load(model)
                    .transform(GridFragment.PaletteTransformation.instance())
                    .into(imageView, new Callback.EmptyCallback() {
                        @Override public void onSuccess() {
                            /*Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap(); // Ew!
                            Palette palette = GridFragment.PaletteTransformation.getPalette(bitmap);


                            Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                            Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                            Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                            Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                            Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                            Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();*/


                            /*holder.itemView.findViewById(R.id.vibrant).setBackgroundColor(vibrantSwatch != null ? vibrantSwatch.getRgb() : 0);
                            holder.itemView.findViewById(R.id.vibrantD).setBackgroundColor(darkVibrantSwatch != null ? darkVibrantSwatch.getRgb() : 0);
                            holder.itemView.findViewById(R.id.vibrantL).setBackgroundColor(lightVibrantSwatch != null ? lightVibrantSwatch.getRgb() : 0);
                            holder.itemView.findViewById(R.id.muted).setBackgroundColor(mutedSwatch != null ? mutedSwatch.getRgb() : 0);
                            holder.itemView.findViewById(R.id.mutedD).setBackgroundColor(darkMutedSwatch != null ? darkMutedSwatch.getRgb() : 0);
                            holder.itemView.findViewById(R.id.mutedL).setBackgroundColor(lightMutedSwatch != null ? lightMutedSwatch.getRgb() : 0);

                            if (lightVibrantSwatch != null) {
                                int bgColor = lightVibrantSwatch.getRgb();
                                holder.itemView.findViewById(R.id.card_view).setBackgroundColor(bgColor);
                            }
                            if (vibrantSwatch != null) {
                                int titleColor = vibrantSwatch.getRgb();
                                holder.mTextViewTitle.setTextColor(titleColor);
                            }
                            if (darkVibrantSwatch != null) {
                                int bodyColor = darkVibrantSwatch.getRgb();
                                holder.mTextViewBody.setTextColor(bodyColor);
                            }*/
                        }
                    });
        }

        private String getItem(int position) {
            return mDataset.get(position);
        }

        private Context getContext() {
            return mContext;
        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
