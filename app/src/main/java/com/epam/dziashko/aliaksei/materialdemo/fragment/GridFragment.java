package com.epam.dziashko.aliaksei.materialdemo.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.dziashko.aliaksei.materialdemo.DetailActivity;
import com.epam.dziashko.aliaksei.materialdemo.R;
import com.epam.dziashko.aliaksei.materialdemo.adapter.BaseArrayAdapter;
import com.epam.dziashko.aliaksei.materialdemo.data.Data;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Map;
import java.util.WeakHashMap;

public class GridFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static class PaletteTransformation implements Transformation {

        private static final PaletteTransformation INSTANCE = new PaletteTransformation();
        private static final Map<Bitmap, Palette> CACHE = new WeakHashMap<Bitmap, Palette>();

        public static PaletteTransformation instance() {
            return INSTANCE;
        }

        public static Palette getPalette(Bitmap bitmap) {
            return CACHE.get(bitmap);
        }

        private PaletteTransformation() {
        }

        @Override public Bitmap transform(Bitmap source) {
            Palette palette = Palette.generate(source);
            CACHE.put(source, palette);
            return source;
        }

        @Override public String key() {
            return "";
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid, container, false);
        return view;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        GridView gridView = (GridView) view.findViewById(R.id.grid);
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(new BaseArrayAdapter<String>(getActivity(), Data.getImageList(50)) {
            @Override public View newView(String model, ViewGroup viewGroup) {
                return View.inflate(getContext(), R.layout.grid_item, null);
            }

            @Override public void bindView(final View view, String model) {

                final ImageView imageView = (ImageView) view.findViewById(R.id.image);

                Picasso.with(getContext())
                        .load(model)
                        .fit().centerCrop()
                        .transform(PaletteTransformation.instance())
                        .into(imageView, new Callback.EmptyCallback() {
                            @Override public void onSuccess() {

                                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap(); // Ew!

                                Palette palette = PaletteTransformation.getPalette(bitmap);


                                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                                Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                                Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                                Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                                Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                                Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();


                                view.findViewById(R.id.vibrant).setBackgroundColor(vibrantSwatch != null ? vibrantSwatch.getRgb() : 0);
                                view.findViewById(R.id.vibrantD).setBackgroundColor(darkVibrantSwatch != null ? darkVibrantSwatch.getRgb() : 0);
                                view.findViewById(R.id.vibrantL).setBackgroundColor(lightVibrantSwatch != null ? lightVibrantSwatch.getRgb() : 0);
                                view.findViewById(R.id.muted).setBackgroundColor(mutedSwatch != null ? mutedSwatch.getRgb() : 0);
                                view.findViewById(R.id.mutedD).setBackgroundColor(darkMutedSwatch != null ? darkMutedSwatch.getRgb() : 0);
                                view.findViewById(R.id.mutedL).setBackgroundColor(lightMutedSwatch != null ? lightMutedSwatch.getRgb() : 0);

                                if (lightVibrantSwatch != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    int bgColor = lightVibrantSwatch.getRgb();
                                    ((CardView) view.findViewById(R.id.card_view)).setBackgroundColor(bgColor);// Oh.. java.lang.ClassCastException: android.graphics.drawable.ColorDrawable cannot be cast to android.support.v7.widget.RoundRectDrawableWithShadow
                                }
                                if (vibrantSwatch != null) {
                                    int titleColor = vibrantSwatch.getRgb();
                                    ((TextView) view.findViewById(R.id.text2)).setTextColor(titleColor);
                                }
                                if (darkVibrantSwatch != null) {
                                    int bodyColor = darkVibrantSwatch.getRgb();
                                    ((TextView) view.findViewById(R.id.text1)).setTextColor(bodyColor);
                                }

                            }
                        });
            }
        });
    }

    @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = (String) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("data", url);

        String transitionName = getString(R.string.tranzition_robo);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, transitionName);

        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }


}
