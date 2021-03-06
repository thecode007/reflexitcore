package com.reflex.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.reflex.R;
import com.reflex.core.model.Recipe;
import java.util.ArrayList;

public class RecipeAdapter  extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

private ArrayList<Recipe> mDataset;
private Context context;
    RecipeAdapter(Context context, ArrayList<Recipe> mDataset) {
        this.mDataset = mDataset;
        this.context = context;
    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
static class MyViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    TextView labelAppName;
    TextView labelDescription;
    ImageView appImage;
    ImageView targetAppImage;
    Switch aSwitch;


    MyViewHolder(View view) {
        super(view);
        labelAppName = view.findViewById(R.id.label_app_name);
        labelDescription = view.findViewById(R.id.label_recipe_description);
        appImage = view.findViewById(R.id.image_app);
        targetAppImage = view.findViewById(R.id.image_target_app);
        aSwitch = view.findViewById(R.id.switch_active);
    }
}


    public Context getContext() {
        return context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recipe, parent, false);
        RecipeAdapter.MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Recipe recipe =mDataset.get(position);
        holder.labelAppName.setText(recipe.appName);
        holder.labelDescription.setText(recipe.description);
        holder.appImage.setImageResource(recipe.appImageResource);
        holder.targetAppImage.setImageResource(recipe.targetAppImageResource);
        holder.aSwitch.setChecked(mDataset.get(position).isActive);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void deleteItem(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }
}
