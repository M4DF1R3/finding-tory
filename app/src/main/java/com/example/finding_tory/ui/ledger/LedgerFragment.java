package com.example.finding_tory.ui.ledger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.finding_tory.Inventory;
import com.example.finding_tory.InventoryViewActivity;
import com.example.finding_tory.Item;
import com.example.finding_tory.LedgerAdapter;
import com.example.finding_tory.databinding.FragmentLedgerBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

/**
 * Displays the Ledger: a list of all the signed-in user's Inventories.
 *  Serves as the homepage after signing in.
 */
public class LedgerFragment extends Fragment {

    private FragmentLedgerBinding binding;

    // TODO use a Ledger instead of an ArrayList of Inventories
    private ArrayList<Inventory> inventories;
    private ListView ledgerListView;
    private LedgerAdapter ledgerAdapter;

    private FloatingActionButton addInvButton;

    /**
     * Maps the ledger adapter to the underlying list of inventories, and sets
     *  any click listeners for each list element/the add button.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the view that is created.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLedgerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // create a mock inventory with mock items to populate the list
        Inventory mockInventory = new Inventory("Mock Inventory");
        ArrayList<String> tags1 = new ArrayList<String>();
        tags1.add("testtag1");
        tags1.add("testtag1.1");
        ArrayList<String> tags2 = new ArrayList<String>();
        tags2.add("testtag2");
        tags2.add("testtag2.2");
        mockInventory.addItem(new Item(new Date(2023, 1, 24), "Item1", "make1", "model1", 10.01f, "1", "no comment", tags1));
        mockInventory.addItem(new Item(new Date(2023, 1, 25), "Item2", "make2", "model2", 20.02f, "2", "No comment", tags2));
        mockInventory.addItem(new Item(new Date(2023, 2, 27), "Item3", "make1", "model3", 30.03f, "3", "no Comment", tags2));
        mockInventory.addItem(new Item(new Date(2022, 9, 13), "Item4", "make4", "model1", 400.40f, "4", "no commenT", tags1));

        inventories = new ArrayList<>();
        inventories.add(mockInventory);

        // map the listview to the ledger's list of items via custom ledger adapter
        ledgerListView = binding.ledgerListview;
        ledgerAdapter = new LedgerAdapter(root.getContext(), inventories);
        ledgerListView.setAdapter(ledgerAdapter);

        // cache the add button
        addInvButton = binding.addInventoryButton;

        // launch a new InventoryViewActivity when any of the listed inventories are clicked
        ledgerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), InventoryViewActivity.class);
                intent.putExtra("inventory", inventories.get(position));
                getActivity().startActivity(intent);  // launch the InventoryViewActivity
            }
        });

        // allow new inventories to be added
        binding.addInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO allow creation of new, unrelated inventories
                Snackbar.make(view, "Create an inventory (Coming soon!)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return root;
    }


    /**
     * Cleanup when this fragment's life cycle is over.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}