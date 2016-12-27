package pwnavor.recyclerviewintablayout;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class BlankFragment extends Fragment {
    ArrayList<FeedItem>feedItems;
    ArrayList<String>titles;



    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        BlankFragment context = this;


        ReadRss readRss = new ReadRss(context, rv);
        readRss.execute();



        titles = readRss.ProcessXml(readRss.Getdata());


        String[] display_titles = (String[]) titles.toArray();











        //Document data = readRss.Getdata();
        //ArrayList<FeedItem> feedItems = readRss.ProcessXml(data);



        MyAdapter adapter = new MyAdapter(display_titles);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }


}