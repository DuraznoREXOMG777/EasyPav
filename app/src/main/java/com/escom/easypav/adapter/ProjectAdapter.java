package com.escom.easypav.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.escom.easypav.R;
import com.escom.easypav.entities.Project;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private List<Project> projectList;

    public ProjectAdapter(List<Project> projectList) {
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        holder.name.setText(projectList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.prj_name)
        TextView name;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
