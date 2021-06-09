package com.example.rewindtask1;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   private List<ImagesResponse> imagesResponsesList = new ArrayList<>();
   GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.grid_view);

        getAllImages();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String message ="Name : "+imagesResponsesList.get(position).getName();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void getAllImages(){
        Call<List<ImagesResponse>> imagesResponse =ApiClient.getInterface().getAllImages();
        imagesResponse.enqueue(new Callback<List<ImagesResponse>>() {
            @Override
            public void onResponse(Call<List<ImagesResponse>> call, Response<List<ImagesResponse>> response) {

                if(response.isSuccessful()){

                    String message ="request successful..";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                    imagesResponsesList =response.body();
                    CustomAdapter customAdapter =new CustomAdapter(imagesResponsesList,MainActivity.this);

                    gridView.setAdapter(customAdapter);


                }else {
                    String message ="an error try agin....";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<List<ImagesResponse>> call, Throwable t) {

                String message =t.getLocalizedMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });

    }


    public class CustomAdapter extends BaseAdapter{

        private  List<ImagesResponse> imagesResponses;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(List<ImagesResponse> imagesResponses, Context context) {
            this.imagesResponses = imagesResponses;
            this.context = context;
            this.layoutInflater=(LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagesResponses.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            if(view==null){

                view = layoutInflater.inflate(R.layout.row_grid_items,parent,false);
            }

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView textView = view.findViewById(R.id.textView);

            textView.setText(imagesResponsesList.get(position).getName());
            GlideApp.get(context)
                    .load(imagesResponsesList
                    .get(position).getUrl().into(imageView));


            return view;
        }
    }


}