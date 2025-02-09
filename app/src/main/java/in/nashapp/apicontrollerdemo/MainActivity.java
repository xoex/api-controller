package in.nashapp.apicontrollerdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.HashMap;
import in.nashapp.apicontroller.ApiController;

public class MainActivity extends AppCompatActivity {
    ApiController APIc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        APIc = new ApiController(this);
        final String names[] = {"Get Request","Post Request","Post File + Variables","Download","Download with Notify"};
        ListView listview = (ListView) findViewById(R.id.listview);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, names);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    new Thread( new Runnable() {
                        @Override
                        public void run() {
                            //Network Operation
                            HashMap<String,String> params = new HashMap<String, String>();
                            params.put("get_request1", "1");
                            params.put("get_request2", "2");
                            params.put("get_request3", "3");
                            final String result  = APIc.GetRequest("http://nashapp.in/request.php",params);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    //UI Operation using network data
                                    alertDialog("Result", result);
                                }
                            });
                        }
                    }).start();
                }else if(position==1){
                    new Thread( new Runnable() {
                        @Override
                        public void run() {
                            //Network Operation
                            HashMap<String,String> params = new HashMap<String, String>();
                            params.put("post_request1", "1");
                            params.put("post_request2", "2");
                            params.put("post_request3", "3");
                            final String result  = APIc.PostRequest("http://nashapp.in/request.php",params);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    //UI Operation using network data
                                    alertDialog("Result", result);
                                }
                            });
                        }
                    }).start();
                }else if(position==2){
                    new Thread( new Runnable() {
                        @Override
                        public void run() {
                            //Network Operation
                            HashMap<String,String> params = new HashMap<String, String>();
                            params.put("post_message1", "1");
                            params.put("post_message2", "2");
                            params.put("post_message3", "3");
                            params.put("post_file", "file:///storage/emulated/0/Pictures/Screenshots/Screenshot_2016-04-01-22-13-25.png");
                            params.put("post_file1", "file:///storage/emulated/0/Pictures/Screenshots/Screenshot_2016-04-01-22-13-25.png");
                            params.put("post_message4", "4");
                            params.put("post_message5", "5");
                            params.put("post_message6", "6");
                            final String result  = APIc.PostRequest("http://nashapp.in/request.php",params);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    //UI Operation using network data
                                    alertDialog("Result", result);
                                }
                            });
                        }
                    }).start();

                }else if(position==3){
                    new Thread( new Runnable() {
                        @Override
                        public void run() {
                            final String result  = APIc.DownloadFile("http://nashapp.in/socialsignin.png", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/socialsignin.png");
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.parse("file://" + result), "image/*");
                                    startActivity(intent);
                                    //alertDialog("Result", result);
                                }
                            });
                        }
                    }).start();

                }
                else if(position==4){
                    new Thread( new Runnable() {
                        @Override
                        public void run() {
                            final String result  = APIc.DownloadFileNotify("http://nashapp.in/test.txt", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test.txt");
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    //Intent intent = new Intent();
                                    //intent.setAction(Intent.ACTION_VIEW);
                                    //intent.setDataAndType(Uri.parse("file://" + result), "image/*");
                                    //startActivity(intent);
                                    alertDialog("Result", result);
                                }
                            });
                        }
                    }).start();

                }




            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void alertDialog(String title,String Message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(Message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
