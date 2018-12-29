package openfoodfacts.github.scrachx.openfood.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import openfoodfacts.github.scrachx.openfood.R;
import openfoodfacts.github.scrachx.openfood.fragments.EditDietFragment;
import openfoodfacts.github.scrachx.openfood.fragments.DietsFragment;
import openfoodfacts.github.scrachx.openfood.models.Diet;
import openfoodfacts.github.scrachx.openfood.repositories.DietRepository;
import openfoodfacts.github.scrachx.openfood.repositories.IDietRepository;

public class DietsAdapter extends RecyclerView.Adapter<DietsAdapter.ViewHolder> {

    private List<Diet> mDiets;
    private final DietsFragment.ClickListener listener;


    public DietsAdapter(List<Diet> diets, DietsFragment.ClickListener listener) {
        mDiets = diets;
        this.listener = listener;
    }

    public void setDiets(List<Diet> diets) {
        mDiets = diets;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTextView;
        //public Button messageButton;
        public Switch dietEnabledSwitch;
        private WeakReference<DietsFragment.ClickListener> listenerRef;

        public ViewHolder(View itemView, DietsFragment.ClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.diet_name);
            dietEnabledSwitch = itemView.findViewById(R.id.diet_enabled);
            //messageButton = itemView.findViewById(R.id.delete_button);
            listenerRef = new WeakReference<>(listener);
            //itemView.setOnClickListener(this);
            nameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("INFO", "DietsHolder_onClick : Identifiant de la vue cliquée : " + view.getId());
            listenerRef.get().onPositionClicked(getAdapterPosition(), view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View dietView = inflater.inflate(R.layout.item_diet, parent, false);
        return new ViewHolder(dietView, listener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Diet diet = mDiets.get(position);
        TextView textView = holder.nameTextView;
        textView.setText(diet.getTag().substring(diet.getTag().indexOf(":") + 1));
        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO", "Click sur le Régime " + diet.getTag());
                Fragment fragment = new EditDietFragment();
                FragmentTransaction transaction =   .getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment );
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });*/
        Switch mSwitch = holder.dietEnabledSwitch;
        mSwitch.setChecked(diet.getEnabled());
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("INFO", "Changement de switch passage à " + isChecked + " pour " + diet.getTag());
                IDietRepository dietRepository = DietRepository.getInstance();
                dietRepository.setDietEnabled(diet.getTag(), isChecked);
            }
        });
        Log.i("INFO", "setTag avec : " + textView.getText());
        textView.setTag(textView.getText());
/*
        Button button = holder.messageButton;
        button.setText(R.string.delete_txt);
        button.setOnClickListener(v -> {
            mDietNames.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            //mProductRepository.setAllergenEnabled(dietName.getDietTag(), false);
        });
*/
    }

    @Override
    public int getItemCount() {
        if (mDiets == null) {
            mDiets = new ArrayList<Diet>();
        }
        return mDiets.size();
    }

    /**
     * Modification d'un régime
     * Modify a diet.
     */
    @OnClick
    public void onClick() {
        Log.i("INFO", "Début de onClick de DietsAdapter");
    }

    void openFragmentEditDietForModification () {
        Log.i("INFO", "Début de openFragmentEditDietForModification de FragmentDiets");
        Fragment fragment = new EditDietFragment();
        FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment );
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

}