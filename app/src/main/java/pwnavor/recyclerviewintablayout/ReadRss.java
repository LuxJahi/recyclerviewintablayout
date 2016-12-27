package pwnavor.recyclerviewintablayout;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Jahi on 12/16/2016.
 */

public class ReadRss extends AsyncTask<Void, Void, Void> {


    BlankFragment context;
    String address="https://www.sciencemag.org/rss/news_current.xml";
    //ProgressDialog progressDialog;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    URL url;
    public ReadRss(BlankFragment context, RecyclerView recyclerView){
        this.recyclerView=recyclerView;
        this.context=context;
       // progressDialog = new ProgressDialog(context);
       // progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        //progressDialog.show();
        super.onPreExecute();
    }



    @Override
    public void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //progressDialog.dismiss();


    }

    @Override
    protected Void doInBackground(Void... voids) {
        ProcessXml(Getdata());
        return null;
    }


    public ArrayList<String> ProcessXml(Document data) {
        ArrayList<String>titles = null;
        if (data!=null) {
            titles=new ArrayList<>();
            feedItems=new ArrayList<>();
            Element root=data.getDocumentElement();
            Node channel=root.getChildNodes().item(1);
            NodeList items=channel.getChildNodes();
            for (int i=0;i<items.getLength();i++){
                Node currentchild=items.item(i);
                if (currentchild.getNodeName().equalsIgnoreCase("item")){
                    FeedItem item=new FeedItem();
                    NodeList itemchilds=currentchild.getChildNodes();
                    for (int j=0;j<itemchilds.getLength();j++){
                        Node current=itemchilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(current.getTextContent());
                            titles.add(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("media:thumbnail")){
                            String url=current.getAttributes().item(0).getTextContent();
                            item.setThumbnailUrl(url);
                        }

                    }
                    feedItems.add(item);
                    Log.d("hello", item.getTitle());


                }

            }
        }
        Log.d("titles", String.valueOf(titles));
        return titles;


    }

    public Document Getdata(){
        try{
            url=new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream=connection.getInputStream();
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }



}
