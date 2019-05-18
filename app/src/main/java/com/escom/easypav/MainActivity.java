package com.escom.easypav;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.escom.easypav.entities.Project;
import com.escom.easypav.utils.IProjectDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity implements IProjectDialog {


    @BindView(R.id.fab)
    ExtendedFloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    List<Project> projects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        fab.setOnClickListener(view -> {
            ProjectDialog.newInstance().openDialog();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogConfirm(String name, String type) {
        Project project1 = new Project(name, type);
        projects.add(project1);
    }

    /**
     * Custom Dialog Class. In progress.
     */
    public static class ProjectDialog extends AppCompatDialogFragment {

        @BindView(R.id.et_name)
        TextInputEditText name;
        @BindView(R.id.tl_name)
        TextInputLayout tlName;
        @BindView(R.id.rb_type)
        RadioGroup rbType;
        @BindString(R.string.rb_rigido)
        String rigido;
        @BindString(R.string.rb_flexible)
        String flexible;

        IProjectDialog dialogListener;

        public static ProjectDialog newInstance() {
            return new ProjectDialog();
        }

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);

            try {
                dialogListener = (IProjectDialog) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + "must implement IProjectDialog");
            }
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflaterCompat = getActivity().getLayoutInflater();
            View view = inflaterCompat.inflate(R.layout.input_dialog, null);

            ButterKnife.bind(this, view);

            builder.setView(view)
                    .setTitle("Project")
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .setPositiveButton("Confirm", ((dialog, which) -> {
                        String name = this.name.getText().toString();
                        String type = null;

                        if (rbType.getCheckedRadioButtonId() == R.id.rb_rigido) {
                            type = rigido;
                        }
                        if (rbType.getCheckedRadioButtonId() == R.id.rb_flexible) {
                            type = flexible;
                        }

                        if (!name.isEmpty() || name.length() > 2) {
                            dialogListener.onDialogConfirm(name, type);
                        } else {
                            tlName.setError("At least three characters");
                        }
                    }));

            return builder.create();
        }

        public void openDialog() {
            ProjectDialog projectDialog = ProjectDialog.newInstance();
            projectDialog.show(getFragmentManager(), "Project Dialog");
        }

        @OnTextChanged(R.id.tl_name)
        public void textChanged(CharSequence charSequence) {

            String name = this.name.getText().toString();

            if (String.valueOf(charSequence).hashCode() == name.hashCode()) {
                tlName.setError(null);
            }
        }
    }
}
