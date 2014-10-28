package com.epam.dziashko.aliaksei.materialdemo.adapter;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Base adapter used across app.
 *
 * @param <T> item type
 */
public abstract class BaseArrayAdapter<T> extends ArrayAdapter<T> implements IBaseAdapter<T>{

	private Comparator<? super T> sortComparator;
    private Function<T, Boolean> mFilter;
    /**
     * Lock used to modify the content of . Any write operation
     * performed on the array should be synchronized on this lock. This lock is also
     * used by the filter (see {@link #getFilter()} to make a synchronized copy of
     * the original array of data.
     */
    private final Object mLock = new Object();

    private List<T> mOriginalValues = new ArrayList<T>();

    public BaseArrayAdapter(Context context) {
		super(context, 0);
	}

    public BaseArrayAdapter(Context context, List<T> items) {
        this(context);
        setDropDownViewResource(getDropDownLayoutRes());
        setItems(items);
    }

    protected int getDropDownLayoutRes() {
        return 0;
    }

    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
	public View getView(int position, View convertView, ViewGroup parent) {
		T model =  getItem(position);

		View view = convertView;
		if (view == null) {
			view = newView(model, parent);
		}
        bindView(view, model);
		return view;
	}

    public abstract View newView(T model, ViewGroup viewGroup);

    public abstract void bindView(View view, T model);

	/**
	 * Sets the list of data mItems of type .
	 *
	 * NOTE: mItems are sorted if sortBy field was specified before.
	 * NOTE: Change notifications fired at end.
	 *
	 * {@link #setSortBy(java.util.Comparator<? super T>)}
	 *
	 * @param rawList the new mItems
	 */
    @Override
    public void setItems(List<T> rawList)
    {
        mOriginalValues.clear();
        mOriginalValues.addAll(rawList);

        if (sortComparator!=null){
            Collections.sort(mOriginalValues, sortComparator);
        }

        applyFilter();

    }

    protected void setItemsInternal(List<T> rawList)
    {
        setNotifyOnChange(false);

        clear();

        if (rawList!=null) {
            addAll(rawList);
        }

        notifyDataSetChanged();

    }

    @Override public void addAll(T... items) {
        if (Build.VERSION.SDK_INT >= 11) {
            super.addAll(items);
        } else {
            for (T item : items) {
                super.add(item);
            }
        }
    }

    @Override public void addAll(Collection<? extends T> collection) {
        if (Build.VERSION.SDK_INT >= 11) {
            super.addAll(collection);
        } else {
            for (T item : collection) {
                super.add(item);
            }
        }
    }

	/**
	 * Sets the sort by field.
	 *
	 * @param c the new sort by field
	 */
	public void setSortBy(Comparator<? super T> c) {
		this.sortComparator = c;
		// calculate direction
		if (sortComparator!=null){
            Collections.sort(mOriginalValues, sortComparator);
			sort(sortComparator);
		} else {
			notifyDataSetChanged();
		}
	}

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                FilterResults results = new FilterResults();
                ArrayList<T> newValues;
                if (mFilter == null) {

                    synchronized (mLock) {
                        newValues = new ArrayList<T>(mOriginalValues);
                    }

                } else {

                    ArrayList<T> values;
                    synchronized (mLock) {
                        values = new ArrayList<T>(mOriginalValues);
                    }

                    final int count = values.size();
                    newValues = new ArrayList<T>();

                    for (int i = 0; i < count; i++) {
                        final T value = values.get(i);
                        if (mFilter.apply(value)) {
                            newValues.add(value);
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

		        setItemsInternal(results.values instanceof List ? (List<T>) results.values : null);

            }
        };
    }

    public void setFilter(Function<T, Boolean> filter) {
        mFilter = filter;

        applyFilter();
    }

    private void applyFilter() {
        if (mFilter==null){
            setItemsInternal(mOriginalValues);
        } else{
            getFilter().filter("");
        }

    }
}
