package com.slusarzparadowski.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.slusarzparadowski.dialog.CategoryDialog;
import com.slusarzparadowski.dialog.ElementDialog;
import com.slusarzparadowski.dialog.InternetAccessDialog;
import com.slusarzparadowski.homebudget.ElementActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Element;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Model;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-03-31.
 */
public class ExpandableItemListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {

    public Context context;
    private Activity activity;
    private LayoutInflater vi;
    ArrayList<Category> list;
    final String type;

    private static final int GROUP_ITEM_RESOURCE = R.layout.group_item;
    private static final int CHILD_ITEM_RESOURCE = R.layout.child_item;

    public ExpandableItemListAdapter(Context context, Activity activity, ArrayList<Category> list, String type) {
        this.context = context;
        this.activity = activity;
        this.type = type;
        this.list = list;

        //TODO: dodanie do listy add category i add element
        if (!this.list.contains(new Category(-1, context.getString(R.string.add_category), "ADD")))
            this.list.add(new Category(-1, context.getString(R.string.add_category), "ADD"));

        for(Category c : this.list){
            if (!c.getElementList().contains(new Element(-1, context.getString(R.string.add_element))))
                c.getElementList().add(new Element(-1, context.getString(R.string.add_element)));
        }
        vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public BaseExpandableListAdapter getExpandableItemListAdapter(){
        return this;
    }

    public Element getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getElementList().get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getElementList().size();
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        final Element child = getChild(groupPosition, childPosition);
        if (child != null) {
            v = vi.inflate(CHILD_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);
            holder.getTextName().setText(Html.fromHtml(child.toString()));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: ExpandableListAdapter onClick
                    // check if group item is last
                    // if is show propt_category dialog and create new category
                    if(child.getId() == -1){
                        Log.i(getClass().getSimpleName(), "getChildView onClick add element");
                        Intent intent = new Intent(activity, ElementActivity.class);
                        activity.startActivityForResult(intent, 1);
                    }
                    else{
                        new ElementDialog(activity, LayoutInflater.from(context).inflate(R.layout.prompts_element, null)).buildDialog().show();
                    }
                    Log.i(getClass().getSimpleName(), "getChildView onClick");
                }
            });
        }
        return v;
    }

    public String getGroup(int groupPosition) {
        return "group-" + groupPosition;
    }

    public int getGroupCount() {
        return list.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(final int groupPosition, final boolean isExpanded, final View convertView, final ViewGroup parent) {
        View v = convertView;
        final Category group;
        long group_id = getGroupId(groupPosition);
        group = this.list.get((int)group_id);
        if (group != null) {
            v = vi.inflate(GROUP_ITEM_RESOURCE, null);
            ViewHolder holder = new ViewHolder(v);
            holder.getTextName().setText(Html.fromHtml(group.getName()));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(group.getId() == -1){
                        // TODO: ExpandableListAdapter onClick
                        Log.i(getClass().getSimpleName(), "getGroupView onClick add category");
                        new CategoryDialog(activity, LayoutInflater.from(context).inflate(R.layout.prompts_category, null), list, type).buildDialog().show();
                    }
                    else{
                        if(isExpanded) ((ExpandableListView) parent).collapseGroup(groupPosition);
                        else ((ExpandableListView) parent).expandGroup(groupPosition);
                    }
                    Log.i(getClass().getSimpleName(), "getGroupView onClick");
                }
            });
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // TODO: ExpandableListAdapter onLongClick
                    Log.i(getClass().getSimpleName(), "getGroupView onLongClicky");
                    // ask update or delete
                    return false;
                }
            });
        }
        return v;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public boolean hasStableIds() {
        return true;
    }

    public ArrayList<Category> getArrayListCategory(){
        if (this.list.contains(new Category(-1, context.getString(R.string.add_category), "ADD")))
            this.list.remove(new Category(-1, context.getString(R.string.add_category), "ADD"));

        for(Category c : this.list){
            if (!c.getElementList().contains(new Element(-1, context.getString(R.string.add_element))))
                c.getElementList().remove(new Element(-1, context.getString(R.string.add_element)));
        }
        return this.list;
    }

    public class ViewHolder {

        private TextView textName;

        public ViewHolder(View v) {
            this.textName = ((TextView)v.findViewById(R.id.childName));
        }

        public TextView getTextName() {
            return textName;
        }

        public void setTextName(TextView textName) {
            this.textName = textName;
        }

    }
}
